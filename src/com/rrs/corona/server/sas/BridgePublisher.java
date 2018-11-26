/*******************************************************************************
 * @rrs_start_copyright
 * 
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved. This
 * software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Red Rabbit Software. ©
 * 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights
 * reserved. Red Rabbit Software - Development Program 5 of 15
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
package com.rrs.corona.server.sas;
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
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;

import org.jboss.logging.Logger;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
/**
 * Created on December 1, 2005
 * @author Sreehari
 * 
 * This class <code>RouterPublisher</code> is used to communicate with <br>
 * <code>HotwireStudio</code>. This is used to publish Project files and
 *  Hotmap files to HotwireStudio.
 */
public final class BridgePublisher
{

    /* ********************************************************************************* */
    /* *********************************Instance Fields********************************* */ 
    /* ********************************************************************************* */
    
    //~Private Fields
	
    /**
	 * BDMHWSBridge
	 */
	private BDMSASBridge bridge = null;
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
	 * Jaxb Instance
	 */
	private final static String JAXB_INT_s  = "com.rrs.corona.metricevent";
	/**
     *  Message property
     */
    private static final String              MESSAGE_TYPE_s          = "MessageType";  
    /**
     *  Name of the file which is publishing
     */
    private static final String              FILE_NAME_s             = "fileName";
    /**
     *  If message property is PROJECT_FILE_s, then it indicates that we are publishing Project
     */
    private static final String              PROJECT_FILE_s          = "ProjectFile";
    
    /* *********************************Logger*************************************/ 
    /**
	 * Logger for this Class for displaying messages and Errors
	 */
	transient private final Logger			logger_		 			 = Logger.getLogger(BridgePublisher.class );
	
    /* **********************************************************************************/
    /* *********************************Constructors*************************************/ 
    /* **********************************************************************************/
    /**
     * Default Constuctor
     */
    public BridgePublisher( final BDMSASBridge bridge)
    {
    	this.bridge = bridge;
    	if(!isRegistered)
    	{
    		logger_.info("Registering Bridge Publisher");
    		tFactory_ = CoronaTransportFactoryImpl.getInstance();
    		initializePublisherSubsystemAndPublish(bridge.getHwsTopic(),bridge.getBridgeIP(),bridge.getBridgePort());
    	}
    }
    /**
     * Constructor which Initiates the establishment of connection with topic and subscription.
     * This is used to publish project files to HotwireStudio
     * @param atmcfiles      :atomic files under the project
     * @param projectName    : name of the project
     * @param atomicfolder   : name of the atomic folder under project 
     * @param bridge
     */
    public BridgePublisher()//final File[] atmcfiles, final  String projectName,  final String atomicfolder
    {
    //	publishAtomicMetrics(atmcfiles , projectName , atomicfolder,"INFO_PUB_BHB_AMLIST_MSG","");
    }
   
    /* **********************************************************************************/
    /* *********************************Public methods**********************************/
    /* **********************************************************************************/
    
    //~ NO PUBLIC METHODS
    
    /* **********************************************************************************/
    /* *********************************Private methods**********************************/ 
    /* **********************************************************************************/
   
    
	private void initializePublisherSubsystemAndPublish( final String topicName,
													     final String ipAdrs, 
													     final String port )
	{
		final String topic = "topic/"+topicName; 
		logger_.info("Init publishers for target transport subsystem: "+targetTransportSubsystemName_);
		tPubController_s = tFactory_.getTransportPublisherController(targetTransportSubsystemName_);
		//set up the configuration parameters for the transport subsystem (in this case JMS)		
		tConfig_s = new JMSTransportConfig();
		tConfig_s.setProperty(JMSTransportConfig.CONNECTION_FACTORY_PROP_s, Constants.UIL2_CONNECTION_FACTORY_s);
		tConfig_s.setProperty(JMSTransportConfig.JAVA_NAMING_FACTORY_INITIAL_PROP_s, Constants.INITIAL_CONTEXT_FACTORY_VALUE_s);
		tConfig_s.setProperty(JMSTransportConfig.JAVA_NAMING_FACTORY_URL_PROP_s, Constants.URL_PKG_PREFIXES_VALUE_s);
		tConfig_s.setProperty(JMSTransportConfig.JAVA_NAMING_PROVIDER_URL_PROP_s, ipAdrs + ":" + port);
		tConfig_s.setProperty(JMSTransportConfig.TOPIC_NAME_s, topic);
		logger_.info("BridgePublisher configarted to:  IP     [ " + ipAdrs+" ]\n"
		            + "                                 PORT  [ "  + port+" ]\n"
					+ "                                 TOPIC [ " + topic+" ]");
	}
	public void publishCompositeEvents( final File[] cefiles, 
									  final String project, 
								      final String folder )
	{
		//Create the Corona Message
		final CoronaMessageProperties cMsgProps = new CoronaMessagePropertiesImpl();
		cMsgProps.setProperty(CoronaMessageConstants.SOURCE_OBJECT_ID_s, "BSB");
		cMsgProps.setProperty(CoronaMessageConstants.SOURCE_OBJECT_TYPE_s, "BSB");
		cMsgProps.setProperty(CoronaMessageConstants.RECEIVER_OBJECT_ID_s, "SAS_ID");
		cMsgProps.setProperty(CoronaMessageConstants.RECEIVER_OBJECT_TYPE_s, "SAS");
		logger_.info("Completed preparing message to publisher via "+targetTransportSubsystemName_+" transport subsystem");
		try
    	{
	    	  final String  projProperty   = "project";
	    	  final String  folderProperty ="folder";
	    	  final String  noOfFiles      = "nooffiles";
	    	  final String  remaingFiles   = "remaining";
	    	  for ( int i = 0; i < cefiles.length; i++ )
	          {
	    		  if(cefiles[i].isDirectory())
	    		  {
	    			  continue;
	    		  }
	    		  
	  			  final JAXBContext jaxbCtx = JAXBContext.newInstance(JAXB_INT_s);
	  			  final Unmarshaller unMarshall = jaxbCtx.createUnmarshaller();
	  			  final CompositeEvent serverName = (CompositeEvent) unMarshall.unmarshal(new FileInputStream(cefiles[i]));
	  			  Marshaller marsh = jaxbCtx.createMarshaller();
	  			  marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
	  			  CharArrayWriter cWriter = new CharArrayWriter();
	  			  marsh.marshal(serverName,cWriter);
	  			  cMsgProps.setProperty(MESSAGE_TYPE_s,PROJECT_FILE_s);
	    		  cMsgProps.setProperty(projProperty,project);
	    		  cMsgProps.setProperty(folderProperty,folder); 
	    		  String noFiles = "["+cefiles.length+"]";
	    		  String remainFiles= "["+(cefiles.length-i)+"]";
	    		  cMsgProps.setProperty(noOfFiles,noFiles);
	    		  cMsgProps.setProperty(remaingFiles,remainFiles);
	    		  cMsgProps.setProperty(FILE_NAME_s,cefiles[i].getName());
	    		  CoronaMessageType cMsgType = new CoronaMessageTypeImpl(CoronaMessageTypes.INFO_PUB_BSB_CELIST_MSG);//"INFO_PUB_BSB_CELIST_MSG");
	    		  CoronaMessagePayload cMsgPayload = new CoronaMessagePayloadImpl(cWriter.toString());
	    		  cMsgProps.setProperty(CoronaMessageConstants.MESSAGE_TYPE_s, cMsgType.getType());
	    		  tPubController_s.publishCoronaMessage(tConfig_s, cMsgType, cMsgProps, cMsgPayload);
	          }
    	}
    	catch(Exception ex)
    	{
    		logger_.error("Exception in Setting message [or] publishing projectfiles");
    		ex.printStackTrace();
    	}
	}
}
