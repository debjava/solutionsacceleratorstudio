/*******************************************************************************
 * @rrs_start_copyright
 * 
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved. This
 * software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Red Rabbit Software. ©
 * 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights
 * reserved. Red Rabbit Software - Development Program 5 of 15 $Id:
 * SolutionAdapterReceiver.java,v 1.1.2.3 2006/05/19 10:55:36 deba Exp $
 * $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/server/adapterreceiver/Attic/SolutionAdapterReceiver.java,v $
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

package com.rrs.corona.server.adapterreceiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.jboss.logging.Logger;
import com.rrs.corona.common.CommonConstants;
import com.rrs.corona.core.message.CoronaMessageHandler;
import com.rrs.corona.core.message.CoronaMessagePayload;
import com.rrs.corona.core.message.CoronaMessageProperties;
import com.rrs.corona.core.message.CoronaMessageType;
import com.rrs.corona.core.message.impl.CoronaMessageTypeImpl;
import com.rrs.corona.core.transport.CoronaTransportFactory;
import com.rrs.corona.core.transport.TransportConfig;
import com.rrs.corona.core.transport.impl.CoronaTransportFactoryImpl;
import com.rrs.corona.core.transport.impl.JMSTransportConfig;
import com.rrs.corona.core.transport.subscriber.TransportSubscriberController;
// import com.rrs.corona.server.sas.BDMSASBridge;
// import com.rrs.corona.server.sas.BDMSASBridgeMBean;
import com.rrs.corona.messages.CoronaMessageTypes;
import com.rrs.corona.server.sas.Constants;
import com.rrs.corona.service.CoronaPlatformServiceMBeanSupport;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import corona.license.util.Codec;
import corona.service.ServiceConfig;

/**
 * @author Debadatta Mishra
 * 
 */
public class SolutionAdapterReceiver extends CoronaPlatformServiceMBeanSupport
		implements SolutionAdapterReceiverMBean, CoronaMessageHandler {
	/**
	 * CoronaTransportFactory
	 */
	private CoronaTransportFactory tFactory_ = null;

	/**
	 * Type of transport System is "JMS"
	 */
	private String targetTransportSubsystemName_ = "JMS";

	/**
	 * Stores the value of Connection Factory
	 */
	private String connFact_ = null;

	/**
	 * Stores the ip address of CMS
	 */
	private String receiverIP_ = null;

	/**
	 * Stores port number of CMS
	 */
	private String receiverPort_ = null;

	/**
	 * Stores the topic name.
	 */
	private String receiverTopic_ = null;

	/**
	 * Path of the directory where jar file will be deployed
	 */
	private String deployPath_ = null;

	/**
	 * Logger for this Class for displaying messages and Errors
	 */
	transient private final Logger logger_ = Logger
			.getLogger(SolutionAdapterReceiver.class);

	/**
	 * Default Constructor
	 */
	public SolutionAdapterReceiver() {

	}

	/**
	 * Method to start the service
	 * 
	 * @see org.jboss.system.ServiceMBeanSupport#startService()
	 */
	public void startService() {// This method starts the service
		logger_.info("Starting the SAS Receiver...................");
		initializeSubscriberSubsystemAndSubscribe();
	}

	/**
	 * @see com.rrs.corona.server.adapterreceiver.SolutionAdapterReceiver.initCoronaService(ServiceConfig)
	 */
	public void initCoronaService(final ServiceConfig serviceConfig) {// This
		// method
		// initializes
		// the
		// required
		// variables
		// Get the default values for the fields from the configuration object
		// provided by the corona kernel
		connFact_ = (String) serviceConfig
				.getInitParameter("ServiceProperties.ConnFactory");

		receiverIP_ = (String) serviceConfig
				.getInitParameter("ServiceProperties.ESBIP");

		receiverPort_ = (String) serviceConfig
				.getInitParameter("ServiceProperties.ESBPort");

		receiverTopic_ = (String) serviceConfig
				.getInitParameter("ServiceProperties.SolutionadapterTopic");
		deployPath_ = CommonConstants.CMS_CORONA + SASConstants.JAR_DEPLOY_PATH;
	}

	/**
	 * Method to initialize the JMS subscriber system
	 */
	public void initializeSubscriberSubsystemAndSubscribe() {// This method
		// is used to
		// initialize
		// the JMS
		// Subscriber
		// system
		tFactory_ = CoronaTransportFactoryImpl.getInstance();
		receiverTopic_ = "topic/" + receiverTopic_;
		logger_.info(" Topic Name :::: " + receiverTopic_);
		logger_.info("Init subscribers for target transport subsystem: "
				+ targetTransportSubsystemName_);
		final TransportSubscriberController tSubController = tFactory_
				.getTransportSubscriberController(targetTransportSubsystemName_);
		final TransportConfig tConfig = new JMSTransportConfig();
		// Setting the properties
		tConfig.setProperty(JMSTransportConfig.CONNECTION_FACTORY_PROP_s,
				Constants.UIL2_CONNECTION_FACTORY_s);
		tConfig.setProperty(
				JMSTransportConfig.JAVA_NAMING_FACTORY_INITIAL_PROP_s,
				Constants.INITIAL_CONTEXT_FACTORY_VALUE_s);
		tConfig.setProperty(JMSTransportConfig.JAVA_NAMING_FACTORY_URL_PROP_s,
				Constants.URL_PKG_PREFIXES_VALUE_s);
		tConfig.setProperty(JMSTransportConfig.JAVA_NAMING_PROVIDER_URL_PROP_s,
				receiverIP_ + ":" + receiverPort_);
		tConfig.setProperty(JMSTransportConfig.TOPIC_NAME_s, receiverTopic_);
		ArrayList list = new ArrayList();
		list.add(new CoronaMessageTypeImpl(
				CoronaMessageTypes.INFO_PUB_SAS_JAR_MSG));
		tSubController.addCoronaMessageHandler(this, tConfig, list, null);
	}

	/**
	 * This is overriden method of CoronaPlatformServiceMBeanSupport which calls
	 * <code></code>
	 * 
	 * @see com.rrs.corona.server.adapterreceiver.SolutionAdapterReceiver.startCoronaService
	 */
	public void startCoronaService() {// This method is used to start the
		// Corona service
		logger_.info("Starting the SASReceiver ");
		initializeSubscriberSubsystemAndSubscribe();
	}

	/**
	 * This is overriden method of CoronaPlatformServiceMBeanSupport which calls
	 * <code></code>
	 * 
	 * @see com.rrs.corona.server.adapterreceiver.SolutionAdapterReceiver.stopCoronaService
	 */
	public void stopCoronaService() {// This method is used to stop the
		// Corona service
		//
	}

	/*
	 * @see com.rrs.corona.core.message.CoronaMessageHandler#processCoronaMessage(com.rrs.corona.core.message.CoronaMessageType,
	 *      com.rrs.corona.core.message.CoronaMessageProperties,
	 *      com.rrs.corona.core.message.CoronaMessagePayload)
	 */
	public void processCoronaMessage(CoronaMessageType mType,
			CoronaMessageProperties mProps, CoronaMessagePayload mPayload) {// This
		// method
		// is
		// used
		// to
		// process
		// the
		// message
		// obtained
		// from
		// the
		// system
		// After receiing the message ie jar file,the jar file is
		// deployed to the CMS Server
		String receivedJarFileName = mProps.getProperty("JarFileName");
		Codec codec = new Codec();
		byte[] b2 = codec.decode(mPayload.getPayload().toString().getBytes());
		String deployDir = System.getenv(SASConstants.JBOSS_HOME)
				+ SASConstants.BACK_SLASH_s + deployPath_;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(new File(
					deployDir + receivedJarFileName));
			fileOutputStream.write(b2);
			logger_
					.info("********************************************************");
			logger_.info("A file named ::: " + receivedJarFileName
					+ "  Received ......................................");
			logger_
					.info("*********************************************************");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
