/******************************************************************************
 * @rrs_start_copyright
 *
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved.
 * This software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of
 * the license agreement you entered into with Red Rabbit Software.
 *
 * 
 * @rrs_end_copyright
 ******************************************************************************/
/******************************************************************************
 * @rrs_start_disclaimer
 *
 * The contents of this file are subject to the Red Rabbit Software Inc. Corona License
 * ("License"); You may not use this file except in compliance with the License.
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE RED RABBIT SOFTWARE INC. OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * @rrs_end_disclaimer
 ******************************************************************************/

package com.rrs.corona.solutionsacceleratorstudio.license;

import java.util.Properties;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.messagehandler.ConfigReader;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

/**
 * @author Debadatta Mishra
 * 
 */
public class LicensePublisher {

	/**
	 * @see javax.jms.Topic
	 */
	transient private Topic topic = null;

	/**
	 * @see javax.jms.TopicConnection
	 */
	transient private TopicConnection connect = null;

	/**
	 * @see javax.jms.TopicSession
	 */
	transient private TopicSession session = null;

	/**
	 * @see javax.jms.TopicPublisher
	 */
	transient private TopicPublisher publisher = null;

	/**
	 * @see java.util.Properties
	 */
	private Properties env = null;

	/**
	 * @see javax.naming.InitialContext
	 */
	private InitialContext jndi = null;

	/**
	 * boolean variable to hold values ie true or false
	 */
	transient private boolean publishFlag = false;

	/**
	 * @see javax.jms.TopicSubscriber
	 */
	transient TopicSubscriber subscriber;

	/**
	 * String variable to hold prefix of topic
	 */
	private static final String topicPrefix = "topic/";

	/**
	 * String variable containing ":"
	 */
	private static final String colon = ":";

	/**
	 * set method sets the properties.It initializes the jndi properties. This
	 * JNDI properties is used while publishing and subscribing to topic.
	 * 
	 */
	public void set() {// This method is used to set the property
		final ClassLoader oldLoader = Thread.currentThread()
				.getContextClassLoader();
		final ClassLoader newLoader = this.getClass().getClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(newLoader);
			env = new Properties();
			env.setProperty(SASConstants.factInitial, SASConstants.nContex);
			env.setProperty(SASConstants.url, ConfigReader.licenseTopicIP_s
					+ colon + ConfigReader.licenseTopicPort_s);
			env.setProperty(SASConstants.urlPkg, SASConstants.jnpInter);
			jndi = new InitialContext(env);
		} catch (Exception ex) {
			final String errMsg = "Exception thrown in Method "
					+ "**set()** in class [ LicensePublisher ] ";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, ex);
			ex.printStackTrace();
		} finally {
			Thread.currentThread().setContextClassLoader(oldLoader);
		}
	}

	/**
	 * publishProj creates a Connection Factory.With JNDI it looks up for the
	 * topic at which the project has to be published.It creates connection to
	 * the topic and publishes the project.
	 * 
	 */
	public void publishPrj() {// This method is used to create a Connection
								// factory and to publish
		final ClassLoader oldLoader = Thread.currentThread()
				.getContextClassLoader();
		final ClassLoader newLoader = this.getClass().getClassLoader();
		try {
			publishFlag = false;
			Thread.currentThread().setContextClassLoader(newLoader);
			final Object tmp = jndi.lookup(ConfigReader.conFact_s);
			topic = (Topic) jndi.lookup(topicPrefix
					+ ConfigReader.licenseTopic_s);
			final TopicConnectionFactory tcf = (TopicConnectionFactory) tmp;
			connect = tcf.createTopicConnection();
			session = connect.createTopicSession(false,
					TopicSession.AUTO_ACKNOWLEDGE);
			publisher = session.createPublisher(topic);
			connect.start();

			final TextMessage txtMsg = session.createTextMessage();
			txtMsg.setText("license");
			txtMsg.setStringProperty("messageType", "INF_PUB_SAS_LICENSE_MSG");
			txtMsg.setStringProperty("acceptance", LicenseFileIO.license
					.getAccepted());
			txtMsg.setStringProperty("key", LicenseFileIO.license
					.getLicenseKey());
			txtMsg.setLongProperty("usageCount", LicenseFileIO.license
					.getUsageHistory().getTime().size());
			txtMsg.setLongProperty("totalUsage", Long
					.parseLong(LicenseFileIO.license.getTotalUsageTime()));

			publisher.publish(txtMsg);
			connect.stop();
			connect.close();
			publishFlag = true;
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**publishPrj()** in class [ LicensePublisher.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			publishFlag = false;
			e.printStackTrace();
		} finally {
			Thread.currentThread().setContextClassLoader(oldLoader);
		}
	}

	/**
	 * Method used to publish
	 */
	public void publish() {// This method is used to publish, it also call the
							// method publishPrj()
		try {
			set();
			publishPrj();
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**publish()** in class [ LicensePublisher.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

	/**
	 * @return a colon
	 */
	public static String getColon() {
		return colon;
	}

	/**
	 * @return the prefix of the Topic
	 */
	public static String getTopicPrefix() {
		return topicPrefix;
	}

	/**
	 * @return a TopicConnection
	 */
	public TopicConnection getConnect() {
		return connect;
	}

	/**
	 * 
	 * @param connect
	 *            of type TopicConnection
	 */
	public void setConnect(TopicConnection connect) {
		this.connect = connect;
	}

	/**
	 * @return a Properties
	 */
	public Properties getEnv() {
		return env;
	}

	/**
	 * @param env
	 *            of type Properties
	 * @see java.util.Properties
	 */
	public void setEnv(Properties env) {
		this.env = env;
	}

	/**
	 * @return InitialContext
	 * @see javax.naming.InitialContext
	 */
	public InitialContext getJndi() {
		return jndi;
	}

	/**
	 * @param jndi
	 *            of type InitialContext
	 * 
	 */
	public void setJndi(InitialContext jndi) {
		this.jndi = jndi;
	}

	/**
	 * @return TopicPublisher
	 */
	public TopicPublisher getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher
	 *            of type TopicPublisher
	 */
	public void setPublisher(TopicPublisher publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return TopicSession
	 */
	public TopicSession getSession() {
		return session;
	}

	/**
	 * @param session
	 *            of type TopicSession
	 */
	public void setSession(TopicSession session) {
		this.session = session;
	}

	/**
	 * @return TopicSubscriber
	 */
	public TopicSubscriber getSubscriber() {
		return subscriber;
	}

	/**
	 * @param subscriber
	 *            of type TopicSubscriber
	 */
	public void setSubscriber(TopicSubscriber subscriber) {
		this.subscriber = subscriber;
	}

	/**
	 * @return Topic
	 */
	public Topic getTopic() {
		return topic;
	}

	/**
	 * @param topic
	 *            of type Topic
	 */
	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	/**
	 * @return boolean
	 */
	public boolean isPublishFlag() {
		return publishFlag;
	}

	/**
	 * @param publishFlag
	 *            of type boolean
	 */
	public void setPublishFlag(boolean publishFlag) {
		this.publishFlag = publishFlag;
	}

}
