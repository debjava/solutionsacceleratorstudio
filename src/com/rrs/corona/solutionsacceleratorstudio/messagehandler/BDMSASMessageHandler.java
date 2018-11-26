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

package com.rrs.corona.solutionsacceleratorstudio.messagehandler;

import java.io.File;
import java.io.FileOutputStream;
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
import com.rrs.corona.hotwirestudio.HWSConstants;
import com.rrs.corona.messages.CoronaMessageTypes;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;
import com.rrs.corona.solutionsacceleratorstudio.project.LoadProject;
import com.rrs.corona.solutionsacceleratorstudio.project.ProjectData;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * 
 * <code></code>
 * 
 * @author Hari
 */
public final class BDMSASMessageHandler implements CoronaMessageHandler
{
	/**
	 * CoronaTransportFactory
	 */
	private CoronaTransportFactory			tFactory_						= null;
	/**
	 * Type of Transport System is "JMS"
	 */
	private String							targetTransportSubsystemName_	= "JMS";
	/**
	 * ArrayList contains the names of the received projects.
	 */
	public transient static ArrayList		receivedProjects_s				= new ArrayList( );
	/**
	 * Constant: Name of the file
	 */
	private transient static final String	FILE_NAME_s						= "fileName";
	/**
	 * Constant: remaining atomic files to be received for a particular project
	 */
	private transient static final String	REMAINING_FILES_s				= "remaining";
	/**
	 * Constant: foldername property
	 */
	private transient static final String	FOLDER_s						= "folder";
	/**
	 * Constant: project
	 */
	private transient static final String	PROJECT_s						= "project";
	private transient static String			SERVERNAME_s					= "";
	private transient static String			ADAPTERNAME_s					= "";
	/**
	 * TransportSubscriberController
	 */
	private TransportSubscriberController	tSubController_					= null;

	/**
	 * 
	 * @param ipAdrs
	 * @param port
	 */
	public BDMSASMessageHandler( final String ipAdrs, final String port,
			final String serverName, final String adapterName )
	{
		SERVERNAME_s = serverName;
		ADAPTERNAME_s = adapterName;
		final String topicName = "topic/"
				+ ConfigReader.bdmbridgeResponseTopic_s;
		System.out.println( "Topicname is " + topicName );
		tFactory_ = CoronaTransportFactoryImpl.getInstance( );
		System.out.println( "Init subscribers for target transport subsystem: "
				+ targetTransportSubsystemName_ );
		tSubController_ = tFactory_
				.getTransportSubscriberController( targetTransportSubsystemName_ );
		TransportConfig tConfig = new JMSTransportConfig( );
		tConfig.setProperty( JMSTransportConfig.CONNECTION_FACTORY_PROP_s,
								SASConstants.UIL2_CONNECTION_FACTORY );// "UIL2ConnectionFactory");
		tConfig
				.setProperty(
								JMSTransportConfig.JAVA_NAMING_FACTORY_INITIAL_PROP_s,
								SASConstants.INITIAL_CONTEXT_FACTORY_VALUE );// "org.jnp.interfaces.NamingContextFactory");
		tConfig.setProperty( JMSTransportConfig.JAVA_NAMING_FACTORY_URL_PROP_s,
								SASConstants.URL_PKG_PREFIXES );// "org.jboss.naming:org.jnp.interfaces");
		System.out.println( "Ip : " + ipAdrs );
		System.out.println( port );
		tConfig
				.setProperty(
								JMSTransportConfig.JAVA_NAMING_PROVIDER_URL_PROP_s,
								ipAdrs + ":" + port );
		tConfig.setProperty( JMSTransportConfig.TOPIC_NAME_s, topicName );
		List<CoronaMessageTypeImpl> msgList = new ArrayList<CoronaMessageTypeImpl>( );
		msgList
				.add( new CoronaMessageTypeImpl(
													CoronaMessageTypes.INFO_PUB_BSB_CELIST_MSG ) );
		tSubController_.addCoronaMessageHandler( this, tConfig, msgList, null );
		System.out.println( "finished..." );
	}

	/**
	 * Method to Process the Received Message.
	 */
	public void processCoronaMessage( final CoronaMessageType type,
			final CoronaMessageProperties props,
			final CoronaMessagePayload payLoad )
	{
		ProjectData.getDisplay( ).syncExec( new Runnable( ) {
			public void run( )
			{
				try
				{
					System.out.println( "Received..........." );
					final String atomicData = ( String ) payLoad.getPayload( );
					String filename = null;
					FileOutputStream outStream = null;
					final String projectName = props.getProperty( PROJECT_s );
					final String noOfFilesRemaining = props
							.getProperty( REMAINING_FILES_s );
					String folderName = props.getProperty( FOLDER_s );
					filename = props.getProperty( FILE_NAME_s );
					if( ( folderName != null ) )
					{
						final StringBuilder projectFolder = new StringBuilder(
																				SASConstants.SAS_ROOT );
						projectFolder.append( ADAPTERNAME_s );
						projectFolder
								.append( SASConstants.SOLUTIONS_ADAPTER_PROJECT );
						projectFolder.append( projectName );
						projectFolder.append( HWSConstants.BACK_SLASH_s );
						StringBuilder atomicFolder = new StringBuilder(
																		projectFolder );
						atomicFolder.append( folderName );
						StringBuilder atomicNewFolder = new StringBuilder(
																			projectFolder );
						atomicNewFolder.append( folderName );
						atomicNewFolder.append( "_new" );
						StringBuilder temp = new StringBuilder( projectFolder );
						temp.append( folderName );
						temp.append( "_temp" );
						File file = new File( temp.toString( ) );
						if( !file.isDirectory( ) )
						{
							file.mkdirs( );
						}
						outStream = new FileOutputStream( temp
								+ HWSConstants.BACK_SLASH_s + filename );
						outStream.write( atomicData.getBytes( ) );
						outStream.flush( );
						outStream.close( );
						if( noOfFilesRemaining.equals( "[1]" ) )
						{
							deletefolder( new File( atomicNewFolder.toString( ) ) );
							if( !new File( atomicFolder.toString( ) ).exists( ) )
							{
								file.renameTo( new File( atomicFolder
										.toString( ) ) );
								if( !receivedProjects_s.contains( projectFolder
										.toString( ) ) )
								{
									System.out
											.println( "Received last Composite event" );
									receivedProjects_s.add( projectFolder
											.toString( ) );
									LoadProject load = new LoadProject( );
									String filepath = SASConstants.SAS_ROOT
											+ ADAPTERNAME_s
											+ SASConstants.SOLUTIONS_ADAPTER_PROJECT;
									load.loadNewProject( filepath, projectName );
								}
							}
							else
							{
								file.renameTo( new File( atomicNewFolder
										.toString( ) ) );

							}
						}

					}
				}
				catch( Exception e )
				{
					final String errMsg = "Exception thrown in Method "
							+ "**processCoronaMessage()** in class [ BDMSASMessageHandler.java ]";
					SolutionsacceleratorstudioPlugin.getDefault( )
							.logError( errMsg, e );
					e.printStackTrace( );
				}
			}
		} );
	}

	public void closeConnection( )
	{
		tSubController_.removeCoronaMessageHandler( this );
	}

	/**
	 * 
	 * @param file
	 * @return
	 * @author Hari
	 */
	private boolean deletefolder( File file )
	{
		try
		{
			boolean success = true;

			if( file.isDirectory( ) )
			{
				String[] child = file.list( );

				for( int i = 0; i < child.length; i++ )
				{
					success = deletefolder( new File( file, child[i] ) );

					if( !success )
					{
						return false;
					}
				}
			}

			return file.delete( );
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in Method "
					+ "**deleteFolder()** in class [ BDMASMessageHandler.java ]";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			return false;
		}
	}

	public static void deleteFromList( )
	{
		for( int i = 0; i < receivedProjects_s.size( ); i++ )
			receivedProjects_s.remove( i );
	}
}
