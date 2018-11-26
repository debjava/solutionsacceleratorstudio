/******************************************************************************
*
* Created on Jan 27, 2006
*
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
package com.rrs.corona.server.sas;

import java.util.ArrayList;
import java.util.List;

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
import com.rrs.corona.messages.CoronaMessageTypes;
import com.rrs.corona.service.CoronaPlatformServiceMBeanSupport;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;

import corona.service.ServiceConfig;

import org.jboss.logging.Logger;


/**
 * Created on December 1, 2005
 * @author Hari
 * <code>BDMHWSBridge</code> 
 */
public final class BDMSASBridge extends CoronaPlatformServiceMBeanSupport implements BDMSASBridgeMBean,CoronaMessageHandler
{
	/* ***************************** Instance Fields ********************************* */
	//~ Private fields
	/**
	 *  CoronaTransportFactory
	 */
	private CoronaTransportFactory tFactory_ = null;
	/**
	 *  Type of transport System is "JMS"
	 */
	private String targetTransportSubsystemName_ = "JMS";
	/**
	 * Stores the value of Connection Factory 
	 */
	private  String							connFact_	    = null;
	/**
	 * Stores the ip address of CMS
	 */
	private String							bridgeIP_	    = null;
	/**
	 * Stores port number of CMS
	 */
	private String							bridgePort_	    = null;
	/**
	 * Stores the topic name. 
	 */
	private String							bridgeTopic_	= null;
	/**
	 * Stores the "" topic
	 */
	private String							hwsTopic_	    = null;
	/* **************************************Logger*********************************** */
	/**
	 * Logger for this Class for displaying messages and Errors
	 */
	transient private final Logger			logger_							= Logger.getLogger(BDMSASBridge.class );
	/**
	 * 
	 */
	private BridgeHelper bHelper_ = null;
	/**
	 * Default Constuctor.
	 * Reads Configaration file.
	 */
	public BDMSASBridge() 
    {
		logger_.info("Inside BDMSASBridge");
		//use the Corona Transport Factory to get access to the Corona Transport Subsystem
		tFactory_ = CoronaTransportFactoryImpl.getInstance();
	}
	/**
	 * Service method which will be called automatically
	 */
    public void startService()
    {
    	logger_.info(" Registering Bridge Listener");
    	initializeSubscriberSubsystemAndSubscribe();
    	new BridgePublisher(this);
		bHelper_ = new BridgeHelper();
	}
    public void initializeSubscriberSubsystemAndSubscribe()
	{
    	final String topicName = "topic/" + getBridgeTopic();
    	logger_.info("Init subscribers for target transport subsystem: "+targetTransportSubsystemName_);
		final TransportSubscriberController tSubController = tFactory_.getTransportSubscriberController(targetTransportSubsystemName_);
		final TransportConfig tConfig = new JMSTransportConfig();
		//Setting the properties
		tConfig.setProperty(JMSTransportConfig.CONNECTION_FACTORY_PROP_s, Constants.UIL2_CONNECTION_FACTORY_s);
		tConfig.setProperty(JMSTransportConfig.JAVA_NAMING_FACTORY_INITIAL_PROP_s, Constants.INITIAL_CONTEXT_FACTORY_VALUE_s);
		tConfig.setProperty(JMSTransportConfig.JAVA_NAMING_FACTORY_URL_PROP_s, Constants.URL_PKG_PREFIXES_VALUE_s);
		tConfig.setProperty(JMSTransportConfig.JAVA_NAMING_PROVIDER_URL_PROP_s, getBridgeIP()+ ":" + getBridgePort());
		tConfig.setProperty(JMSTransportConfig.TOPIC_NAME_s, topicName); 
		//Appling filter for the message handler
		//Creating the listener 
		List<CoronaMessageTypeImpl> msgList = new ArrayList<CoronaMessageTypeImpl>();
		msgList.add(new CoronaMessageTypeImpl(CoronaMessageTypes.INFO_REQ_SAS_CELIST_MSG));
		tSubController.addCoronaMessageHandler(this, tConfig, msgList, null);
		logger_.info("registered BDMSAS Bridge Listener for the topic["+getBridgeTopic()+"]");
	}
	/**
	 * This method is used initialize some of the fields of EWHService
	 */
	public void initCoronaService( final ServiceConfig serviceConfig )
	{
		// Get the default values for the fields from the configuration object
		// provided by the corona kernel
		connFact_ = ( String ) serviceConfig
			.getInitParameter( "ServiceProperties.ConnFactory" );

		bridgeIP_ = ( String ) serviceConfig
			.getInitParameter( "ServiceProperties.ESBIP" );

		bridgePort_ = ( String ) serviceConfig
			.getInitParameter( "ServiceProperties.ESBPort" );

		bridgeTopic_ = ( String ) serviceConfig
			.getInitParameter( "ServiceProperties.BridgeTopic" );
		hwsTopic_ = ( String ) serviceConfig
		.getInitParameter( "ServiceProperties.HWSTopic" );
	}

	/**
	 * This is overriden method of CoronaPlatformServiceMBeanSupport which calls
	 * <code></code>
	 */
	public void startCoronaService( )
	{
		logger_.info("Starting the BDMSAS Bridge");
		startService( );
	}

	/**
	 * This is overriden method of CoronaPlatformServiceMBeanSupport which calls
	 * <code></code>
	 */
	public void stopCoronaService( )
	{
		//
	}
	/**
	 * Callback method of Transporation Framework
	 */
	public void processCoronaMessage(final CoronaMessageType type, 
									 final CoronaMessageProperties props, 
									 final CoronaMessagePayload msgPayload) 
	{
		if("INFO_REQ_SAS_CELIST_MSG".equalsIgnoreCase(type.getType()))
		{
			logger_.info("Received JMS Message And Started Processing Corona Message");
			logger_.info("Request for all the projects");
			//Request for all projects
			bHelper_.publishProjects();
		}
	}
	/**
	 * Returns IP Address
	 * @return bridgeIP_
	 * @author Hari
	 */
	public String getBridgeIP() 
	{
		return bridgeIP_;
	}
	/**
	 * Returns Port number
	 * @return bridgePort_
	 * @author Hari
	 */
	public String getBridgePort() 
	{
		return bridgePort_;
	}
	/**
	 * Returns topicName
	 * @return connFact_
	 * @author Hari
	 */
	public String getBridgeTopic() 
	{
		return bridgeTopic_;
	}
	/**
	 * Returns ConnectionFactory
	 * @return connFact_
	 * @author Hari
	 */
	public String getConnFact() 
	{
		return connFact_;
	}
	/**
	 * Returns Topic
	 * @return hwsTopic_
	 * @author Hari
	 */
	public String getHwsTopic() 
	{
		return hwsTopic_;
	}
	
}
