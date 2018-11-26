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
 * $Id: ConfigReader.java,v 1.4 2006/08/02 12:11:33 redrabbit Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/messagehandler/ConfigReader.java,v $
 * 
 * @author Sreehari
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
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jboss.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

/**
 * 
 * This class reads the hotwirestudio.config file to get the initial parameters
 * for starting the Composite Event Manager. All the required parameters are
 * defined in this file.
 * 
 * @author Sreehari
 */
public class ConfigReader
{
	// ~ Static fields/initializers
	// ---------------------------------------------

	public transient static String			licenseTopic_s				= null;
	public transient static String			licenseTopicIP_s			= null;
	public transient static String			licenseTopicPort_s			= null;
	public transient static String			bdmbridgeRequestTopic_s		= null;
	public transient static String			bdmbridgeResponseTopic_s	= null;
	public transient static String			SASJARPublisherTopic_s		= null;
	public transient static int				parseOnce					= 0;
	public transient static boolean			bJunitFlag					= false;
	public transient static String			conFact_s					= null;

	// ~ Instance fields
	// --------------------------------------------------------

	public transient boolean				initExecSts					= false;
	public transient boolean				stParExecSts				= false;
	private transient String				confFName					= null;
	protected transient SAXParserFactory	spFactory_					= null;
	protected transient SAXParser			sParser_					= null;
	protected transient File				fileInStream_				= null;
	protected transient MyDefaultHandler	handle_						= null;

	private transient static final String	CONFIG_FILE_s				= SASConstants.SAS_CONFIG_FILE;
	protected Logger logger = Logger.getLogger(ConfigReader.class);

	// ~ Methods
	// ----------------------------------------------------------------

	/**
	 * 
	 * This method does initialization for SAX parsing
	 * 
	 */
	public void init( )
	{
		String confPath = SASConstants.PROJECT_CONFIG_PATH;
		//Refers to CORONA_HOME/config/corona
		try
		{
			confFName = confPath + CONFIG_FILE_s;//Refers to sas.config
			System.out.println("In config reader file path :::===>>> "+confFName);
			spFactory_ = SAXParserFactory.newInstance( );
			sParser_ = spFactory_.newSAXParser( );
			//System.out.println("In configReader Complete File name ::: "+confFName);
			logger.info("In configReader Complete File name ::: "+confFName);
			fileInStream_ = new File( confFName );
		}
		catch( SAXException ex )
		{
			final String errMsg = "Exception thrown in Method " +
					"**init()** in class [ ConfigReader.java ]";
			SolutionsacceleratorstudioPlugin.getDefault( )
					.logError( errMsg, ex );
			initExecSts = false;
			ex.printStackTrace( );
		}
		catch( ParserConfigurationException ex )
		{
			final String errMsg = "Exception thrown in Method " +
					"**init()** in class [ ConfigReader.java ]";
			SolutionsacceleratorstudioPlugin.getDefault( )
					.logError( errMsg, ex );
			initExecSts = false;
			ex.printStackTrace( );
		}
		initExecSts = true;
	}

	/**
	 * 
	 * This method registers the DefaultHandler class for parsing the
	 * corona.config file.
	 * 
	 */
	public void startParsing( )
	{
		if( initExecSts )
		{
			try
			{
				if( parseOnce == 0 )
				{
					if( null == fileInStream_ )
					{
						stParExecSts = false;
					}
					else
					{
						handle_ = new MyDefaultHandler( );
						sParser_.parse( fileInStream_, handle_ );
						parseOnce = 1;
						stParExecSts = true;
					}
				}
				else
				{
					stParExecSts = true;
				}
			}
			catch( SAXException ex )
			{
				final String errMsg = "Exception thrown in Method " +
						"**startParsing()** in class [ ConfigReader.java ]";
				SolutionsacceleratorstudioPlugin.getDefault( )
						.logError( errMsg, ex );
				stParExecSts = false;
				ex.printStackTrace( );
			}
			catch( IOException ex )
			{
				final String errMsg = "Exception thrown in Method " +
						"**startParsing()** in class [ ConfigReader.java ]";
				SolutionsacceleratorstudioPlugin.getDefault( )
						.logError( errMsg, ex );
				stParExecSts = false;
				ex.printStackTrace( );
			}
		}
		else
		{
			stParExecSts = false;
		}
	}

	// ~ Inner Classes
	// ----------------------------------------------------------

	class MyDefaultHandler extends DefaultHandler
	{
		// ~ Instance fields
		// ----------------------------------------------------

		transient String	TagName;
		transient String	data;
		transient String	topic;
		int					ceFileCount	= 0;

		// ~ Methods
		// ------------------------------------------------------------

		/**
		 * 
		 * This method gets the start Element of the XML file
		 * 
		 */
		public void startElement( final String uri, final String pfx,
				final String qName, final Attributes att )
		{
			TagName = qName;
		}

		public void endElement( final String uri, final String localname,
				final String qName )
		{
			TagName = qName;

			if( TagName.equals( "LicenseTopic" ) )
			{
				licenseTopic_s = data;
			}
			else if( TagName.equals( "LicenseTopicIP" ) )
			{
				licenseTopicIP_s = data;
			}
			else if( TagName.equals( "LicenseTopicPort" ) )
			{
				licenseTopicPort_s = data;
			}
			else if( TagName.equals( "BDMBridgeRequestTopic" ) )
			{
				bdmbridgeRequestTopic_s = data;
			}
			else if( TagName.equals( "BDMBridgeResponseTopic" ) )
			{
				bdmbridgeResponseTopic_s = data;
			}
			else if( TagName.equals( "ConnFactory" ) )
			{
				conFact_s = data;
			}
			else if( TagName.equals( "SASJARPublisher" ) )
			{
				SASJARPublisherTopic_s = data;
			}
			else
			{
				// ignore
			}
		}

		public void characters( final char[] chArr, final int base,
				final int length )
		{
			final char[] cArray = chArr;
			data = new String( cArray, base, length );
		}

		public void ignorableWhitespace( final char[] chArr, final int start,
				final int length )
		{
			System.out.println( "Ignorable white spaces"
					+ new String( chArr, start, length ) );
		}
	}
}
