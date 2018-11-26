/*******************************************************************************
 * 
 * Created on Jan 27, 2006
 * 
 * @rrs_start_copyright
 * 
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved. This
 * software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Red Rabbit Software.
 * 
 * 
 * @rrs_end_copyright
 ******************************************************************************/
/*******************************************************************************
 * @rrs_start_disclaimer
 * 
 * The contents of this file are subject to the Red Rabbit Software Inc. Corona
 * License ("License"); You may not use this file except in compliance with the
 * License. THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL THE RED RABBIT SOFTWARE INC. OR ITS CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * @rrs_end_disclaimer
 ******************************************************************************/

package com.rrs.corona.solutionsacceleratorstudio.publisher;

import org.jboss.logging.Logger;

import com.rrs.corona.core.message.CoronaMessageConstants;
import com.rrs.corona.core.message.CoronaMessagePayload;
import com.rrs.corona.core.message.CoronaMessageProperties;
import com.rrs.corona.core.message.CoronaMessageType;
import com.rrs.corona.core.message.impl.CoronaMessagePayloadImpl;
import com.rrs.corona.core.message.impl.CoronaMessagePropertiesImpl;
import com.rrs.corona.core.message.impl.CoronaMessageTypeImpl;
import com.rrs.corona.core.transport.CoronaTransportFactory;
import com.rrs.corona.core.transport.TransportConfig;
import com.rrs.corona.core.transport.impl.CoronaTransportFactoryImpl;
import com.rrs.corona.core.transport.impl.JMSTransportConfig;
import com.rrs.corona.core.transport.publisher.TransportPublisherController;
import com.rrs.corona.messages.CoronaMessageTypes;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

/**
 * <code></code>
 * 
 * @author Hari
 */
public class SASPublisher {
	private String targetTransportSubsystemName_ = "JMS";

	private CoronaTransportFactory tFactory_ = null;

	protected Logger logger = Logger.getLogger(SASPublisher.class);

	public SASPublisher() {
		System.out.println("default constructor");
	}

	public void initializePublisherAndPublish(String topicName, String ip,
			String port) {
		// System.out.println( "SAS Publisher " + ip + ":" + port );
		logger.info("In SASPublisher " + "IP ::: " + ip + " " + "PORT ::: "
				+ port);
		/*
		 * SolutionsacceleratorstudioPlugin.getDefault().logInfo( "In
		 * SASPublisher " + "IP ::: " + ip + " " + "PORT ::: " + port, null);
		 */
		// use the Corona Transport Factory to get access to the Corona
		// Transport Subsystem
		tFactory_ = CoronaTransportFactoryImpl.getInstance();
		// System.out.println("Init publishers for target transport subsystem: "
		// + targetTransportSubsystemName_);
		logger.info("Init publishers for target transport subsystem: "
				+ targetTransportSubsystemName_);
		CoronaMessagePayload cMsgPayload = null;
		TransportPublisherController tPubController = tFactory_
				.getTransportPublisherController(targetTransportSubsystemName_);
		// set up the configuration parameters for the transport subsystem (in
		// this case JMS)
		TransportConfig tConfig = new JMSTransportConfig();
		tConfig.setProperty(JMSTransportConfig.CONNECTION_FACTORY_PROP_s,
				SASConstants.UIL2_CONNECTION_FACTORY);
		tConfig.setProperty(
				JMSTransportConfig.JAVA_NAMING_FACTORY_INITIAL_PROP_s,
				SASConstants.INITIAL_CONTEXT_FACTORY_VALUE);
		tConfig.setProperty(JMSTransportConfig.JAVA_NAMING_FACTORY_URL_PROP_s,
				SASConstants.URL_PKG_PREFIXES);
		tConfig.setProperty(JMSTransportConfig.JAVA_NAMING_PROVIDER_URL_PROP_s,
				ip + ":" + port);
		tConfig.setProperty(JMSTransportConfig.TOPIC_NAME_s, "topic/"
				+ topicName);
		logger.info("In SASPublisher topic ::: " + topicName);
		/*
		 * SolutionsacceleratorstudioPlugin.getDefault().logInfo( "In
		 * SASPublisher topic ::: " + topicName, null);
		 */
		// Create the Corona Message
		cMsgPayload = new CoronaMessagePayloadImpl("");
		CoronaMessageProperties cMsgProps = new CoronaMessagePropertiesImpl();
		cMsgProps.setProperty(CoronaMessageConstants.SOURCE_OBJECT_ID_s,
				"SAS_ID");
		cMsgProps.setProperty(CoronaMessageConstants.SOURCE_OBJECT_TYPE_s,
				"SAS");
		cMsgProps.setProperty(CoronaMessageConstants.RECEIVER_OBJECT_ID_s,
				"BSB");
		cMsgProps.setProperty(CoronaMessageConstants.RECEIVER_OBJECT_TYPE_s,
				"BSB");
		System.out.println("Completed preparing message to publisher via "
				+ targetTransportSubsystemName_ + " transport subsystem");
		logger.info("Completed preparing message to publisher via "
				+targetTransportSubsystemName_ + " transport subsystem");
		// publish the TEST messages - these have the same payload and props,
		// but diff message-type fields

		CoronaMessageType cMsgType = new CoronaMessageTypeImpl(
				CoronaMessageTypes.INFO_REQ_SAS_CELIST_MSG);// "INFO_REQ_SAS_CELIST_MSG");

		cMsgProps.setProperty(CoronaMessageConstants.MESSAGE_TYPE_s, cMsgType
				.getType());
		tPubController.publishCoronaMessage(tConfig, cMsgType, cMsgProps,
				cMsgPayload);
		// System.out.println( "Message published successfully" );
		logger.info("Message published successfully");
		/*
		 * SolutionsacceleratorstudioPlugin.getDefault().logInfo( "Message
		 * published successfully", null);
		 */

	}
}
