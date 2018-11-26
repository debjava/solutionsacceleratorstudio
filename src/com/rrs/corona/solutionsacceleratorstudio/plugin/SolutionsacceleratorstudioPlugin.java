/*******************************************************************************
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

package com.rrs.corona.solutionsacceleratorstudio.plugin;

/**
 * @author Debadatta Mishra
 */
import java.io.File;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;
import org.eclipse.jface.dialogs.MessageDialog;
import com.rrs.corona.common.CommonConstants;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.jboss.logging.Logger;

import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.license.LicenseFileIO;
import com.rrs.corona.solutionsacceleratorstudio.license.LicensePublisher;
import com.rrs.corona.solutionsacceleratorstudio.messagehandler.ConfigReader;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SolutionAdapterView;
import com.rrs.license.client.Validator;
import corona.license.util.LicenseConstants;

/**
 * The main plugin class to be used in the desktop.
 */
public class SolutionsacceleratorstudioPlugin extends AbstractUIPlugin
{
	private static String							sas_home			= SASConstants.CORONA_HOME_SAS;
	private static String							licensePath			= sas_home
																				+ "solutionsacceleratorstudio";
	private static String 							nativeLibPath		=sas_home+"/"+"lib"+"/"+"native";
	private static final String						LICENSE_FILE		= licensePath
																				+ SASConstants.BACK_SLASH_s
																				+ SASConstants.LICENSE_FILE_NAME;
	//temporary comments
	private static final String						license_dir_Name	= licensePath
																				+ SASConstants.BACK_SLASH_s
																				+ SASConstants.LICENSE_DIR_NAME;

	public static long								startUpTime;

	public static long								shutDownTime;

	public static String							productID			= new String( );

	public static boolean							licenseCheck		= false;

	// The shared instance.
	private static SolutionsacceleratorstudioPlugin	plugin;
	
	protected Logger logger = Logger.getLogger(SolutionsacceleratorstudioPlugin.class);

	/**
	 * The constructor.
	 */
	public SolutionsacceleratorstudioPlugin( )
	{
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start( BundleContext context ) throws Exception
	{
		logger.info("Starting the SAS application");
		if( null == sas_home)
		{
			MessageDialog.openError(new Shell(),"SAS_oo1","Error in starting the application,set the environment variable \"CORONA_HOME_SAS\" ");
			System.exit(0);
		}
		super.start( context );
		getWorkbench( ).addWindowListener( new IWindowListener( ) {
			public void windowActivated( IWorkbenchWindow window )
			{

			}

			public void windowDeactivated( IWorkbenchWindow window )
			{

			}

			public void windowClosed( IWorkbenchWindow window )
			{
				try
				{
					//beginning of license
//					shutDownTime = System.currentTimeMillis( );
//					LicenseFileIO.getInstance( )
//							.save( new File( LICENSE_FILE ) );
//					ConfigReader cReader = new ConfigReader( );
//					cReader.init( );
//					cReader.startParsing( );
//					if( LicenseFileIO.license.getAccepted( )
//							.equals( SASConstants.TRUE ) )
//					{
//						LicensePublisher pub = new LicensePublisher( );
//						pub.publish( );
//					}
					//end of license part
				}
				catch( Exception ex )
				{
					window.getShell( ).close( );
				}

			}

			public void windowOpened( IWorkbenchWindow window )
			{
				startUpTime = System.currentTimeMillis( );
//				LicenseFileIO.getInstance( ).read( new File( LICENSE_FILE ) );
//				File fLicense = new File( license_dir_Name
//						+ SASConstants.BACK_SLASH_s + SASConstants.LICENSE_RRS );
//				File fPublic = new File( license_dir_Name
//						+ SASConstants.BACK_SLASH_s + SASConstants.PUBLIC_RRS );
//				File fPsn = new File( license_dir_Name
//						+ SASConstants.BACK_SLASH_s + SASConstants.PRJFILE );
//				// Beginning of license validation
//				Validator coronaValidator = new Validator( );
//				try
//				{
//					boolean validToRun = coronaValidator
//							.validate( fLicense, fPublic, fPsn,
//										LicenseConstants.SAS,nativeLibPath );
//					if( !validToRun )
//					{
//						MessageDialog dialog = new MessageDialog(
//																	SolutionAdapterView.fSite
//																			.getShell( ),
//																	"Warning",
//																	new Image(
//																				getWorkbench( )
//																						.getDisplay( ),
//																				SASConstants.LICENSE_IMG ),
//																	SASConstants.LICENSE_FAIL_MSG,
//																	MessageDialog.WARNING,
//																	new String[] { SASConstants.DIALOG_OK_BTN },
//																	0 );
//						dialog.open( );
//						System.exit( 0 );
//					}
//					else
//					{
//						productID = coronaValidator
//								.getProductSerialNumber( fPsn );
//					}
//				}
//				catch( Exception e )
//				{
//					
//					e.printStackTrace( );
//					System.exit( 0 );
//				}
//				// end of license validation
//				if( LicenseFileIO.license.getAccepted( )
//						.equals( SASConstants.FALSE ) )
//				{
//					final LicenseAgreementDialog dialog = new LicenseAgreementDialog( );
//					dialog.createSeperatorWindow( window.getShell( ) );
//				}
				//********** end of license part
			}
		} );
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop( BundleContext context ) throws Exception
	{
		super.stop( context );
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static SolutionsacceleratorstudioPlugin getDefault( )
	{
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path.
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor( String path )
	{
		return AbstractUIPlugin
				.imageDescriptorFromPlugin( "solutionsacceleratorstudio", path );
	}

	/**
	 * This class is used to log the exception and the stack trace thrown out of
	 * a method
	 * 
	 * @param message
	 *            of type String
	 * @param t
	 *            of type Throwable
	 */
	public void logError( String message, Throwable t )
	{
		getLog( ).log(
						new Status( IStatus.ERROR, getBundle( )
								.getSymbolicName( ), 0, message
								+ t.getMessage( ), t ) );
	}

	/**
	 * This method is used to log the information for debugging
	 * 
	 * @param message
	 *            of type String
	 * @param t
	 *            of type Throwable
	 */
	public void logInfo( final String message, Throwable t )
	{
		getLog( ).log(
						new Status( IStatus.INFO, getBundle( )
								.getSymbolicName( ), 0, message
								+ t.getMessage( ), t ) );
	}

	
	// Method for license aggreement
//	public void licenseValidator( Shell shell )
//	{
//		try
//		{
//			// System.out.println("started");
//			startUpTime = System.currentTimeMillis( );
//			// System.out.println(startUpTime);
//			LicenseFileIO.getInstance( ).read( new File( LICENSE_FILE ) );
//			File fLicense = new File( license_dir_Name
//					+ SASConstants.BACK_SLASH_s + SASConstants.LICENSE_RRS );
//			File fPublic = new File( license_dir_Name
//					+ SASConstants.BACK_SLASH_s + SASConstants.PUBLIC_RRS );
//			File fPsn = new File( license_dir_Name + SASConstants.BACK_SLASH_s
//					+ SASConstants.PRJFILE );
//			Validator coronaValidator = new Validator( );
//			try
//			{
//				boolean validToRun = coronaValidator
//						.validate( fLicense, fPublic, fPsn,
//									LicenseConstants.SAS );
//				if( !validToRun )
//				{
//					MessageDialog dialog = new MessageDialog(
//																shell,
//																"Warning",
//																new Image(
//																			shell
//																					.getDisplay( ),
//																			SASConstants.LICENSE_IMG ),
//																SASConstants.LICENSE_FAIL_MSG,
//																MessageDialog.WARNING,
//																new String[] { SASConstants.DIALOG_OK_BTN },
//																0 );
//					dialog.open( );
//					System.exit( 0 );
//				}
//				else
//				{
//					productID = coronaValidator.getProductSerialNumber( fPsn );
//				}
//			}
//			catch( Exception e )
//			{
//				System.exit( 0 );
//			}
//			if( LicenseFileIO.license.getAccepted( )
//					.equals( SASConstants.FALSE ) )
//			{
//				final LicenseAgreementDialog dialog = new LicenseAgreementDialog( );
//				dialog.createSeperatorWindow( shell );
//			}
//		}
//		catch( Exception e )
//		{
//			System.exit( 0 );
//		}
//
//	}
	
	
	
}
