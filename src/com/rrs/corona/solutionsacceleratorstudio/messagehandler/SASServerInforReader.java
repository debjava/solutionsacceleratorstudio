///*******************************************************************************
// * @rrs_start_copyright
// * 
// * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved. This
// * software is the confidential and proprietary information of Red Rabbit
// * Software, Inc. ("Confidential Information"). You shall not disclose such
// * Confidential Information and shall use it only in accordance with the terms
// * of the license agreement you entered into with Red Rabbit Software. ©
// * 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights
// * reserved. Red Rabbit Software - Development Program 5 of 15
// * 
// * $Id: SASServerInforReader.java,v 1.3 2006/08/03 09:12:17 redrabbit Exp $ $Source:
// * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/messagehandler/SASServerInforReader.java,v $
// * 
// * @rrs_end_copyright
// ******************************************************************************/
///*******************************************************************************
// * @rrs_start_disclaimer
// * 
// * The contents of this file are subject to the Red Rabbit Software Inc. Corona
// * License ("License"); You may not use this file except in compliance with the
// * License. THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
// * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
// * EVENT SHALL THE RED RABBIT SOFTWARE INC. OR ITS CONTRIBUTORS BE LIABLE FOR
// * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
// * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// * 
// * @rrs_end_disclaimer
// ******************************************************************************/
//
//package com.rrs.corona.solutionsacceleratorstudio.messagehandler;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Marshaller;
//import javax.xml.bind.Unmarshaller;
//
//import org.jboss.logging.Logger;
//
//import com.rrs.corona.ServerInfoConfig.CMSPublishConfig;
//import com.rrs.corona.ServerInfoConfig.ServerInfoType;
//import com.rrs.corona.ServerInfoConfig.ObjectFactory;
//import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
//import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;
//
//public class SASServerInforReader
//{
//	private static String	CONFIG_FILE_s	= SASConstants.SAS_SERVERINFO_CONFIG_FILE;
//	private String			server_			= "";
//	private String			serverIP_		= "";
//	private String			serverPort_		= "";
//	private String			ipAndport		= null;
//	private String[]		serverName		= null;
//	protected Logger		logger			= Logger
//													.getLogger( SASServerInforReader.class );
//
//	public String sasConfigReader( String serverName )
//	{
//		try
//		{
//			final String jaxbInt = SASConstants.BDMJAXB_CONTEXT;// "com.rrs.corona.bdmconfig";
//			final JAXBContext jax = JAXBContext.newInstance( jaxbInt );
//			final Unmarshaller unmars = jax.createUnmarshaller( );
//			StringBuilder configFile = new StringBuilder(
//															SASConstants.PROJECT_CONFIG_PATH );
//			System.out.println();
//			configFile.append( CONFIG_FILE_s );
//			final CMSPublishConfig pub = ( CMSPublishConfig ) unmars
//					.unmarshal( new File( configFile.toString( ) ) );
//			List servList = pub.getServerInfo( );
//			for( Iterator iter = servList.iterator( ); iter.hasNext( ); )
//			{
//				ServerInfoType servInf = ( ServerInfoType ) iter.next( );
//				if( serverName.equals( servInf.getName( ) ) )
//				{
//					server_ = servInf.getName( );
//					serverIP_ = servInf.getServerIP( );
//					serverPort_ = servInf.getServerPort( );
//					ipAndport = serverIP_ + ":" + serverPort_;
//					break;
//				}
//			}
//		}
//		catch( Exception e )
//		{
//			final String errMsg = "Exception thrown in Method " +
//					"**sasConfigReader()** in class [ SASServerInfoReader.java ]";
//			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
//			logger.info( e );
//		}
//		return ipAndport;
//	}
//
//	public String[] sasReaderServername( )
//	{
//		try
//		{
//			final String jaxbInt = SASConstants.BDMJAXB_CONTEXT;// "com.rrs.corona.bdmconfig";
//			final JAXBContext jax = JAXBContext.newInstance( jaxbInt );
//			final Unmarshaller unmars = jax.createUnmarshaller( );
//			StringBuilder configFile = new StringBuilder(
//															SASConstants.PROJECT_CONFIG_PATH );
//			configFile.append( CONFIG_FILE_s );
//			final CMSPublishConfig pub = ( CMSPublishConfig ) unmars
//					.unmarshal( new File( configFile.toString( ) ) );
//			List servList = pub.getServerInfo( );
//			serverName = new String[servList.size( )];
//			int i = 0;
//			for( Iterator iter = servList.iterator( ); iter.hasNext( ); )
//			{
//				ServerInfoType servInf = ( ServerInfoType ) iter.next( );
//				/*
//				 * if(!serverList_.contains(servInf.getName())) {
//				 * serverList_.add(servInf.getName()); server_ =
//				 * servInf.getName(); }
//				 */
//				server_ = servInf.getName( );
//				serverName[i++] = server_;
//				serverIP_ = servInf.getServerIP( );
//				serverPort_ = servInf.getServerPort( );
//				ipAndport = serverIP_ + ":" + serverPort_;
//			}
//		}
//		catch( Exception e )
//		{
//			final String errMsg = "Exception thrown in Method " +
//					"**sasReaderServername()** in class [ SASServerInfoReader.java ]";
//			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
//			e.printStackTrace( );
//		}
//		return serverName;
//	}
//
//	public void writeServerInfo( String serverName, String IPAddress,
//			String portNo )
//	{
//		try
//		{
//			final String jaxbInt = SASConstants.BDMJAXB_CONTEXT;
//			final JAXBContext jax = JAXBContext.newInstance( jaxbInt );
//			final Unmarshaller unmars = jax.createUnmarshaller( );
//			StringBuilder configFile = new StringBuilder(
//															SASConstants.PROJECT_CONFIG_PATH );
//			configFile.append( CONFIG_FILE_s );
//			final CMSPublishConfig pub = ( CMSPublishConfig ) unmars
//					.unmarshal( new File( configFile.toString( ) ) );
//			ObjectFactory objFact = new ObjectFactory( );
//			ServerInfoType serverInfo = objFact.createServerInfoType( );
//			serverInfo.setName( serverName );
//			serverInfo.setServerIP( IPAddress );
//			serverInfo.setServerPort( portNo );
//			pub.getServerInfo( ).add( serverInfo );
//			final JAXBContext jaxbContext = JAXBContext.newInstance( jaxbInt );
//			final FileOutputStream fileOutputStream = new FileOutputStream(
//																			new File(
//																						configFile
//																								.toString( ) ) ); // temporary
//			// hardcoding
//			// for
//			// writing
//			// to a
//			// file
//			final Marshaller marshaller = jaxbContext.createMarshaller( );
//			marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT,
//									Boolean.TRUE );
//			marshaller.marshal( pub, fileOutputStream );
//			fileOutputStream.close( );
//		}
//		catch( Exception e )
//		{
//			final String errMsg = "Exception thrown in Method " +
//					"**writeServerInfo()** in class [ SASServerInfoReader.java ]";
//			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
//			e.printStackTrace( );
//		}
//	}
//
//	public void removeServerInfo( String serverName )
//	{
//		try
//		{
//			final String jaxbInt = SASConstants.BDMJAXB_CONTEXT;
//			final JAXBContext jax = JAXBContext.newInstance( jaxbInt );
//			final Unmarshaller unmars = jax.createUnmarshaller( );
//			StringBuilder configFile = new StringBuilder(
//															SASConstants.PROJECT_CONFIG_PATH );
//			configFile.append( CONFIG_FILE_s );
//			final CMSPublishConfig pub = ( CMSPublishConfig ) unmars
//					.unmarshal( new File( configFile.toString( ) ) );
//			List info = pub.getServerInfo( );
//			int i = 0;
//			for( Iterator iter = info.iterator( ); iter.hasNext( ); i++ )
//			{
//				ServerInfoType servInf = ( ServerInfoType ) iter.next( );
//				if( servInf.getName( ).equals( serverName ) )
//				{
//					info.remove( i );
//					break;
//				}
//			}
//			final JAXBContext jaxbContext = JAXBContext.newInstance( jaxbInt );
//			final FileOutputStream fileOutputStream = new FileOutputStream(
//																			new File(
//																						configFile
//																								.toString( ) ) ); // temporary hardcoding for writing to a file
//			final Marshaller marshaller = jaxbContext.createMarshaller( );
//			marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT,
//									Boolean.TRUE );
//			marshaller.marshal( pub, fileOutputStream );
//			fileOutputStream.close( );
//		}
//		catch( Exception e )
//		{
//			final String errMsg = "Exception thrown in Method " +
//					"**removeServerInfo()** in class [ SASServerInfoReader.java ]";
//			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
//			e.printStackTrace( );
//		}
//	}
//}
