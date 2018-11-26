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
 * JARPublisher.java,v 1.1.2.2 2006/05/19 10:56:31 deba Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/publisher/Attic/JARPublisher.java,v $
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

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.rrs.corona.messages.CoronaMessageTypes;
import com.rrs.corona.metricevent.CompositeEvent;
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
// import com.rrs.corona.server.sas.BDMSASBridge;
import com.rrs.corona.server.sas.Constants;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.messagehandler.ConfigReader;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SolutionAdapterView;

import corona.license.util.Codec;

/**
 * This class is used to publish the created jar file
 * to the CMS server
 * @author Debadatta Mishra
 * 
 */
public class JARPublisher {
	/**
	 * indicates whether publisher is registered or not
	 */
	private static boolean isRegistered = false;

	/**
	 * Type of transport system is "JMS"
	 */
	private String targetTransportSubsystemName_ = "JMS";

	/**
	 * CoronaTransportFactory
	 */
	private CoronaTransportFactory tFactory_ = null;

	/**
	 * TransportPublisherController
	 */
	private static TransportPublisherController tPubController_s;

	/**
	 * TransportConfig
	 */
	private static TransportConfig tConfig_s;

	/**
	 * Logger to log the messages
	 */
	Logger logger = Logger.getLogger("JARpublisher.class");

	/**
	 * Constructor to 
	 * @param ipAdress of type String.Ip adress of the server
	 * @param portNo of type String. Port number of the Server
	 */
	public JARPublisher(final String ipAdress, final String portNo) {//Constructor which takes IPAddress and PortNo as parameter
		try {
			if (null == ConfigReader.SASJARPublisherTopic_s) {
				ConfigReader reader = new ConfigReader();
				reader.init();
				reader.startParsing();
			}
			final String topicName = ConfigReader.SASJARPublisherTopic_s;
			logger.info("IP Address in Constructor ::: " + ipAdress);
			initializePublisherSubsystemAndPublish("topic/" + topicName,
					ipAdress, portNo);
		} catch (Exception ex) {
			final String errMsg = "Exception thrown in **Constructor** in Class [ JARPublisher.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, ex);
			ex.printStackTrace();
			//			MessageBox box = new MessageBox(SolutionAdapterView.viewer_s.getControl().getShell());
			//			box.setMessage("exception occured in 11...");
			//			box.open();
		}
	}

	/**
	 * Method to initialize and publish
	 * @param topicName of type String. Topic name to which jar file will be published
	 * @param ipAdrs of type String.Ip address of the CMS server 
	 * @param port of type String. Port number of the Server
	 */
	private void initializePublisherSubsystemAndPublish(final String topicName,
			final String ipAdrs, final String port) {//This method is used to initialize and publish the message
		try {
			final String topic = topicName;
			//		MessageBox box = new MessageBox(SolutionAdapterView.viewer_s.getControl().getShell());
			//		//box.setMessage("exception occured in 33....");
			//		box.setMessage("********* topic "+ topic);
			//		box.open();

			tFactory_ = CoronaTransportFactoryImpl.getInstance();
			logger.info("Init publishers for target transport subsystem: "
					+ targetTransportSubsystemName_);
			tPubController_s = tFactory_
					.getTransportPublisherController(targetTransportSubsystemName_);
			// set up the configuration parameters for the transport subsystem (in
			// this case JMS)
			tConfig_s = new JMSTransportConfig();
			tConfig_s.setProperty(JMSTransportConfig.CONNECTION_FACTORY_PROP_s,
					Constants.UIL2_CONNECTION_FACTORY_s);
			tConfig_s.setProperty(
					JMSTransportConfig.JAVA_NAMING_FACTORY_INITIAL_PROP_s,
					Constants.INITIAL_CONTEXT_FACTORY_VALUE_s);
			tConfig_s.setProperty(
					JMSTransportConfig.JAVA_NAMING_FACTORY_URL_PROP_s,
					Constants.URL_PKG_PREFIXES_VALUE_s);
			tConfig_s.setProperty(
					JMSTransportConfig.JAVA_NAMING_PROVIDER_URL_PROP_s, ipAdrs
							+ ":" + port);
			tConfig_s.setProperty(JMSTransportConfig.TOPIC_NAME_s, topic);
			logger.info("IP Address :: " + ipAdrs);
			logger.info("BridgePublisher configarted to:  IP     [ " + ipAdrs
					+ " ]\n" + "                                 PORT  [ "
					+ port + " ]\n"
					+ "                                 TOPIC [ " + topic
					+ " ]");
			publishSolutionAdapterJar();
		} catch (Exception ex) {
			final String errMsg = "Exception thrown in Method "
					+ "initializePublisherSubsystemAndPublish()"
					+ "in class [ JARPublisher.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, ex);
			ex.printStackTrace();
			//			MessageBox box = new MessageBox(SolutionAdapterView.viewer_s.getControl().getShell());
			//			box.setMessage("exception occured in 22....");
			//			box.open();
		}
	}

	/**
	 * Method used to publish the jar file
	 */
	public void publishSolutionAdapterJar() {//This method is used to publish the jar file
		final CoronaMessageProperties cMsgProps = new CoronaMessagePropertiesImpl();
		String filePath = null;
		String jarFile_Name = null;
		try {
			final String generatedFolderPath = new SolutionAdapterView()
					.getFolderStructure();// path of generated folder

			// structure
			//for temporary
			//			String jarFile_Name = generatedFolderPath.replace( '/', '_' )
			//					+ ".jar";

			filePath = SASConstants.SAS_ROOT + generatedFolderPath
					+ SASConstants.BACK_SLASH_s;// Full path
			logger.info("File path in JarPublisher.java >>>>>>>>" + filePath);
			//for testing
			final String jarFileName = getJarFile(filePath);
			jarFile_Name = jarFileName;
			//for testing

			FileInputStream fileInputStream = new FileInputStream(new File(
					filePath + SASConstants.BACK_SLASH_s + jarFile_Name));
			// Read Jar file
			tPubController_s = tFactory_
					.getTransportPublisherController(targetTransportSubsystemName_);
			CoronaMessageType cMsgType = new CoronaMessageTypeImpl(
					CoronaMessageTypes.INFO_PUB_BSB_CELIST_MSG);// "INFO_PUB_BSB_CELIST_MSG");
			FileChannel chan = fileInputStream.getChannel();
			long size = chan.size();
			byte[] b = new byte[(int) size];
			fileInputStream.read(b);

			Codec codec = new Codec();
			byte b1[] = codec.encode(b);
			String byteMsg = new String(b1);
			CoronaMessagePayload cMsgPayload = new CoronaMessagePayloadImpl(
					byteMsg);
			cMsgProps.setProperty(CoronaMessageConstants.MESSAGE_TYPE_s,
					cMsgType.getType());
			cMsgProps.setProperty("JarFileName", jarFile_Name);
			tPubController_s.publishCoronaMessage(tConfig_s,
					new CoronaMessageTypeImpl(
							CoronaMessageTypes.INFO_PUB_SAS_JAR_MSG),
					cMsgProps, cMsgPayload);
			logger.info("***************************************************"
					+ "************************************");
			logger.info("File ::: " + jarFile_Name
					+ " Published successfully........................");
			logger.info("************************************************"
					+ "***************************************");
		} catch (Exception ex) {
			//			MessageBox box = new MessageBox(SolutionAdapterView.viewer_s.getControl().getShell());
			//			//box.setMessage("exception occured in 33....");
			//			box.setMessage(ex.getMessage()+"JarFileName "+jarFile_Name+" FilePath  :::"+filePath);
			//			box.open();
			final String errMsg = "Exception thrown in Method "
					+ "**publishSolutionAdapterJar()** in class [ JarPublisher.java ] ";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, ex);
			ex.printStackTrace();
		}
	}

	private String getJarFile(final String filePath) {
		String jarFileName = null;
		File file1 = new File(filePath);
		File[] filesList = file1.listFiles();
		file_Loop: for (int i = 0; i < filesList.length; i++) {
			String fileName = filesList[i].getName();
			//			if(fileName.endsWith(".jar"))
			//			{
			//				jarFileName = fileName;
			//				break file_Loop;
			//			}
			if (filesList[i].isDirectory()) {
				continue file_Loop;
			} else {
				if (fileName.endsWith(".jar")) {
					jarFileName = fileName;
					break file_Loop;
				}
			}
		}
		return jarFileName;
	}
}
