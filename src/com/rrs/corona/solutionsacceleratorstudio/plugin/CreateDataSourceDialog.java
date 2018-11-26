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
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import com.rrs.corona.common.CommonConstants;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import com.rrs.corona.solutionsacceleratorstudio.datasource.DatabaseManager;
import com.rrs.corona.solutionsacceleratorstudio.datasource.DbmsInfo;

public class CreateDataSourceDialog extends TitleAreaDialog // implements
// Listener
{
	/**
	 * Push button for ok button
	 * 
	 * @see org.eclipse.swt.widgets.Button
	 */
	transient private Button	okButton			= null;					// ok
	// button
	/**
	 * push button for cancel button
	 * 
	 * @see org.eclipse.swt.widgets.Button
	 */
	transient private Button	cancelButton		= null;
	/**
	 * It is a boolean value for checking during the execution of the
	 * application
	 */
	boolean						testFlag			= false;
	/**
	 * text field for dataSource name
	 */
	transient private Text		dsName				= null;					// name
	// of
	// the
	// dataSource
	// text
	// field
	/**
	 * field for entering the URL for the database connection
	 */
	transient private Text		urlText				= null;					// name
	// of
	// the
	// database
	// url
	// text
	// field
	/**
	 * text field for entering User name for the database
	 */
	transient private Text		userText			= null;					// name
	// of
	// the
	// user
	// text
	// field
	/**
	 * text field for entering pasword for database connection
	 */
	transient private Text		passwordText		= null;					// name
	// of
	// the
	// password
	// text
	// field
	/**
	 * combo box for several database type
	 */
	transient public Combo		databaseType		= null;					// name
	// of
	// the
	// database
	// type
	// combo
	/**
	 * combo box for driver type
	 */
	transient public Combo		driverType			= null;					// name
	// of
	// the
	// driver
	// type
	// combo
	/**
	 * User Interface text field for IP address for the database connection
	 */
	transient private Text		IPText				= null;					// name
	// of
	// the
	// IP
	// address
	// of
	// the
	// database
	/**
	 * User Interface text field for the database port ie default 1521 for
	 * Oracle database connection
	 */
	transient public Text		portText			= null;					// name
	// of
	// the
	// port
	/**
	 * text field for database name
	 */
	transient private Text		dbNameText			= null;					// text
	// field
	// for
	// database
	// name
	/**
	 * Boolean variable to test the database connection
	 */
	boolean						checkConnection		= false;					// to
	// check
	// database
	// connection
	/**
	 * SQL connection for database connection
	 */
	Connection					connection			= null;					// SQL
	// connection
	/**
	 * push button for testing database connection
	 */
	public Button				testButton			= null;
	/**
	 * Boolean variable for testing the existence of duplicate dataSource name
	 */
	boolean						duplicateDsName		= false;					// to
	// check
	// duplicate
	// DataSource
	// name
	/**
	 * DatabaseManager object for database related activities
	 */
	DatabaseManager				dbManager			= new DatabaseManager( );	// Object
	// for
	// database
	// connectivity
	/**
	 * It is the name of the XML file which stores the database specific
	 * information
	 */
	private transient String	dataSourceFileName	= SASConstants.SAS_XML;	// file
	// path
	// of
	// the
	// XML
	// file
	// where
	// database
	// information
	// are
	// stored
	/**
	 * File path of the dataSource xml file
	 */
	private transient String	dataSourceFilePath	= SASConstants.SAS_ROOT;	// File
	// path
	// of
	// the
	// xml
	// file
	// containing
	// database
	// information
	/**
	 * This arraylist maintains the list of the driver list
	 */
	private transient ArrayList	oracleDriverList	= new ArrayList( );
	/**
	 * ArrrayList containing the list of Drivers for DB2
	 */
	private transient ArrayList	db2DriverList		= new ArrayList( );
	/**
	 * ArrayList containing the List of Drivers for SAP
	 */
	private transient ArrayList	sapDriverList		= new ArrayList( );
	private transient ArrayList	databaseDrivers		= new ArrayList( );
	private transient ArrayList	mySQLDrivers		= new ArrayList( );

	/**
	 * Method to create an instance of the title area dialog
	 * 
	 * @param parentShell
	 */
	public CreateDataSourceDialog( final Shell parentShell )
	{
		super( parentShell );
	}

	/**
	 * Method to create window widget
	 */
	public void create( )
	{
		super.create( );
		setTitle( SASConstants.CREATE_DS_DLG_SET_TITLE );// To set the title
															// of
		setMessage( SASConstants.CREATE_DS_DLG_SET_MESSAGE );// An
																// information
	}

	/**
	 * Creates and returns the upper part of the dialog
	 * 
	 * @return parent of type Composite
	 */
	public Control createDialogArea( final Composite parent )
	{
		try
		{
			parent.getShell( )
					.setText( SASConstants.CREATE_DS_DLG_SHELL_SET_TEXT );// Shell
			createWorkArea( parent );// Method to create the dialog box
			return parent;
		}
		catch( Exception ex )
		{
			ex.printStackTrace( );
			final String Error = "Exception thrown in createDialogArea() "
					+ "in class CreateDataSourceDialog.java";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( Error, ex );
		}
		return parent;
	}

	/**
	 * Adds button to the button bar of the dialog box
	 */
	public void createButtonsForButtonBar( final Composite parent )
	{// Method to create buttons in the dialog box
		testFlag = false;
		try
		{
			testButton = createButton( parent, 555,
										SASConstants.CREATE_DS_DLG_TEST_BUTTON,
										false );
			testButton
					.setToolTipText( SASConstants.CREATE_DS_DLG_TEST_BTN_TOOLTIP );
			testButton.setEnabled( true );
			okButton = createButton( parent, 999, SASConstants.DIALOG_OK_BTN,
										true );
			okButton.setEnabled( false );
			cancelButton = createButton( parent, 999,
											SASConstants.DIALOG_CANCEL_BTN,
											true );
			/**
			 * SelectionListener for test button
			 */
			testButton.addSelectionListener( new SelectionAdapter( ) // test
					// button
					// action
					{
						public void widgetSelected( SelectionEvent e )
						{
							doTestButton( parent );// functionality for test
							// button
						}
					} );

			okButton.addSelectionListener( new SelectionAdapter( ) // SelectionListener
					// for OK
					// button
					{
						public void widgetSelected( SelectionEvent e )
						{
							// action logic for okButton
							doOKButton( parent );// functionality for ok
							// button
						}
					} );

			cancelButton.addSelectionListener( new SelectionAdapter( ) {
				public void widgetSelected( SelectionEvent e )
				{
					parent.getShell( ).close( );
				}
			} );
			testFlag = true;
		}
		catch( Exception e )
		{
			testFlag = false;
			e.printStackTrace( );
			final String Error = "Exception thrown in createButtonsForButtonBar() "
					+ "in class CreateDataSourceDialog.java";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( Error, e );
		}
	}

	/**
	 * Working area and logic for UserInterface designa and business logic
	 * 
	 * @param parent
	 *            of type Composite
	 */
	private void createWorkArea( final Composite parent )
	{// This method is used to create the UI controls on the container
		Composite area = new Composite( parent, SWT.NULL );// First main
		// composite
		Composite firstGroup = new Composite( area, SWT.NONE ); // Acomposite
		// within the
		// main
		// Composite
		GridData gdData1 = new GridData( GridData.FILL_HORIZONTAL );// To show
		// data in a
		// grid
		firstGroup.setLayout( new GridLayout( 3, true ) ); // lay out divided
		// into 3 parts
		GridData gridData = new GridData( ); // to show in a grid
		createLabel( firstGroup, SASConstants.CREATE_DS_DLG_DS_LABEL, gridData );
		createDataSourceText( firstGroup, gdData1 ); // method to create a
		// text field for
		// dataSource
		// createLabel( firstGroup, "Database Type", gridData );// Method to
		// create a
		// label for
		// database type
		createLabel( firstGroup, SASConstants.CREATE_DS_DLG_DBTYPE_LABEL,
						gridData );//
		// Method to create a label for database type
		createDatabaseTypeCombo( firstGroup, gdData1 );// method to create
		// database type Combo
		// box
		// createLabel( firstGroup, "Jdbc Driver URL", gridData );// Method to
		// create a
		// label for
		// JDBC Driver
		// URL
		createLabel( firstGroup, SASConstants.CREATE_DS_DLG_DRV_LABEL, gridData );//
		// Method to create a label for JDBC Driver URL
		createDriverCombo( firstGroup, gdData1 ); // method to create Driver
		// Combo Box for Drivers
		createLabel( firstGroup, "IP Address", gridData );// Method to create
		// a label for IP
		// address for
		// database server
		// createLabel(firstGroup,"IP Address",gridData);//Method to create a
		// label for IP address for database server
		createIPText( firstGroup, gdData1 );// Method to create a text field for
		// the IP address for the database
		// server
		// createLabel( firstGroup, "Port", gridData );// Method to create a
		// label
		// for database port number
		createLabel( firstGroup, SASConstants.CREATE_DS_DLG_PORT_LABEL,
						gridData );//
		// Method to create a label for database port number
		createPortText( firstGroup, gdData1 );// Method to create a text field
		// for database port number
		// createLabel( firstGroup, "Database Name", gridData );// Method to
		// create a
		// label for
		// Database Name
		createLabel( firstGroup, SASConstants.CREATE_DS_DLG_DB_NAME_LABEL,
						gridData );//
		// Method to create a label for Database Name
		createDatabaseNameText( firstGroup, gdData1 );// Method to create text
		// field for database
		// name
		// createLabel( firstGroup, "User Name", gridData );// Method to create
		// label for
		// database User
		// name
		createLabel( firstGroup, SASConstants.CREATE_DS_DLG_USER_NAME_LABEL,
						gridData );//
		// Method to create label for database User name
		createUserText( firstGroup, gdData1 );// Method to create text field
		// for database user
		// createLabel( firstGroup, "Password", gridData );// Method to create
		// label for database
		// password
		createLabel( firstGroup, SASConstants.CREATE_DS_DLG_PWD_LABEL, gridData );//
		// Method to create label for database password
		createPasswordText( firstGroup, gdData1 );// Method to create a text
		// field for database
		// password
		firstGroup.setBounds( 25, 25, 380, 210 );// Layout of the dialog
		// box,to change the size of
		// the dialog box, the
		// numeric values of the
		// setBounds methods can be
		// changed
		db2DriverList.add( "" );// for db2 jdbc:db2
		sapDriverList.add( "" );// for jdbc:sapdb
		mySQLDrivers.add( "my sql driver" );
	}

	/**
	 * *********************************Dialog box functionality
	 * below*******************************************
	 */
	/**
	 * Creates a lebel in the dialog box
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param text
	 *            of type String
	 * @param gridData
	 *            of type GridData
	 */
	private void createLabel( final Composite firstGroup, final String text,
			final GridData gridData )
	{// Method to create a label
		final Label labelName = new Label( firstGroup, SWT.LEFT );
		labelName.setText( text ); // Label Name of the test field to be
		// displayed
		labelName.setLayoutData( gridData );
	}

	/**
	 * Creates a dataSource text field for creating a new dataSource
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type GridData
	 */
	private void createDataSourceText( final Composite firstGroup,
			GridData gdData1 )
	{// This method is used to create a text field for DataSource
		dsName = new Text( firstGroup, SWT.BORDER ); // Creates a text box
		validateDataSourceText( dsName );
		gdData1 = new GridData( GridData.FILL_HORIZONTAL );// It should be
		// initialized first
		gdData1.horizontalSpan = 2;
		dsName.setLayoutData( gdData1 );
		final ArrayList datasourceNames = new DsReader( ).getDataSourceNames( );
		final ArrayList dsNames = convertToLowerCase( datasourceNames );
		/**
		 * ModifyListener for dsName
		 */
		dsName.addModifyListener( new ModifyListener( )// Modify listener for
				// dataSource name
				{
					public void modifyText( ModifyEvent e ) // Text listener, if
					// the user writes a
					// duplicate
					// dataSource name,
					// this method will
					// react
					{
						boolean validDs = false;
						final File dbmsFile = new File( dataSourceFilePath
								+ dataSourceFileName );
						if( !dbmsFile.exists( ) )
						{
							// do nothing
						}
						else
						{
							if( dsNames.contains( dsName.getText( )
									.toLowerCase( ) ) )
							{
								// setMessage(
								// "Data Source name already exists, give
								// different name",
								// IMessageProvider.ERROR );
								setMessage(
											SASConstants.CREATE_DS_DLG_DS_EROOR_MSG,
											IMessageProvider.ERROR );
								dsName.setFocus( );
								okButton.setEnabled( false );
								validDs = true;
							}
							else
							{
								// setMessage( "Create a Data Source by entering
								// the database details " );
								setMessage( SASConstants.CREATE_DS_DLG_SET_MESSAGE );
								validDs = false;
							}
						}
					}
				} );
	}

	/**
	 * Method to convert names of an arraylist to lower case
	 * 
	 * @param dsNames
	 *            of type ArrayList containing dataSourceNames
	 * @return an ArrayList containing names in lower case
	 */
	private ArrayList convertToLowerCase( final ArrayList dsNames )
	{// This method is used to convert the names
		// of an arraylist in to lower case
		ArrayList convertedList = new ArrayList( );
		for( int i = 0; i < dsNames.size( ); i++ )
		{
			final String tempString = ( String ) dsNames.get( i );
			convertedList.add( tempString.toLowerCase( ) );
		}
		return convertedList;
	}

	/**
	 * Creates a Combo box for type of databases
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type GridData
	 */
	private void createDatabaseTypeCombo( final Composite firstGroup,
			GridData gdData1 )
	{// creates a combo box for database type
		databaseType = new Combo( firstGroup, SWT.BORDER | SWT.READ_ONLY );
		gdData1 = new GridData( GridData.FILL_HORIZONTAL );
		gdData1.horizontalSpan = 2;
		databaseType.setLayoutData( gdData1 );
		// databaseType.add( "Oracle" );
		databaseType.add( SASConstants.CREATE_DS_DLG_ORACLE_STRING );
		// databaseType.add( "My SQL" ); // for DB2
		// databaseType.add( "SQLServer" ); // For SQL Server
		// databaseType.add( "Apache Derby" );//For Apache Derby
		databaseType.select( 0 );// Default selection of database type Combo
		// box

		databaseType.addModifyListener( new ModifyListener( ) // implementation
				// of Listener
				{
					public void modifyText( ModifyEvent e )
					{
						// if( databaseType.getText( ).equals( "Oracle" ) )//
						// When
						// Oracle
						// is
						// selected
						if( databaseType
								.getText( )
								.equals(
											SASConstants.CREATE_DS_DLG_ORACLE_STRING ) )//
						// When Oracle is selected
						{
							databaseDrivers = oracleDriverList;// database
							// driver is an
							// arrayList
							// which
							// maintains the
							// list of
							// driver for a
							// specific
							// database
						}
						else if( databaseType.getText( ).equals( "DB2" ) )// When
						// DB2
						// is
						// selected
						// else
						// if(databaseType.getText().equals(SASConstants.CREATE_DS_DLG_DB2_STRING))//
						// When DB2 is selected
						{
							databaseDrivers = db2DriverList;// database driver
							// is an arrayList
							// which maintains
							// the list of
							// driver for a
							// specific database
						}
						else if( databaseType.getText( )
								.equalsIgnoreCase( "MYSQL" ) )
						{
							databaseDrivers = mySQLDrivers;
						}
						else if( databaseType.getText( ).equals( "SAP" ) )// when
						// SAP
						// is
						// selected
						// else
						// if(databaseType.getText().equals(SASConstants.CREATE_DS_DLG_SAP_STRING))//when
						// SAP is selected
						{
							databaseDrivers = sapDriverList;// database driver
							// is an arrayList
							// which maintains
							// the list of
							// driver for a
							// specific database
						}
						driverType.removeAll( );

						for( int i = 0; i < databaseDrivers.size( ); i++ )
						{
							driverType
									.add( ( String ) databaseDrivers.get( i ) );
						}
						// driverType.select(0);
					}
				} );
	}

	/**
	 * Creates a Combo box for Type of Drivers
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type GridData
	 */
	private void createDriverCombo( final Composite firstGroup, GridData gdData1 )
	{// to create Combo box for list of Drivers
		driverType = new Combo( firstGroup, SWT.BORDER | SWT.READ_ONLY );
		gdData1 = new GridData( GridData.FILL_HORIZONTAL );
		gdData1.horizontalSpan = 2;
		driverType.setLayoutData( gdData1 );
		// drivers for Oracle
		addDriversToDriverTypeCombo( );
	}

	/**
	 * Method to add the list of Oracle drivers to DriverType Combo box
	 * 
	 */
	private void addDriversToDriverTypeCombo( )
	{// method to add drivers to Combo box
		// oracleDriverList.add( "jdbc:oracle:thin" );// oracleDriverList is an
		// ArrayList which contains
		// the list of drivers
		oracleDriverList.add( SASConstants.CREATE_DS_DLG_JDBC_DRV_URL );// oracleDriverList
		// is an ArrayList which contains the list of drivers
		for( int i = 0; i < oracleDriverList.size( ); i++ )
		{
			driverType.add( ( String ) oracleDriverList.get( i ) );
		}
		driverType.select( 0 );// default selection of the Driver type Combo
		// box
	}

	/**
	 * Creates a text field for IP address of the database server
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type GridData
	 */
	private void createIPText( final Composite firstGroup, GridData gdData1 )
	{// method to create Text box for IP address of the database server
		IPText = new Text( firstGroup, SWT.BORDER );
		gdData1 = new GridData( GridData.FILL_HORIZONTAL );
		gdData1.horizontalSpan = 2;
		IPText.setLayoutData( gdData1 );
	}

	/**
	 * Creates a text field for Port for database
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type GridData
	 */
	private void createPortText( final Composite firstGroup, GridData gdData1 )
	{// Method used to create a text field for Database PORT
		portText = new Text( firstGroup, SWT.BORDER );
		gdData1 = new GridData( GridData.FILL_HORIZONTAL );
		gdData1.horizontalSpan = 2;
		portText.setLayoutData( gdData1 );
		// portText.setText( "1521" );
		portText.setText( SASConstants.CREATE_DS_DLG_PORT );
	}

	/**
	 * Creates a text field for Database name
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type GridData
	 */
	private void createDatabaseNameText( final Composite firstGroup,
			GridData gdData1 )
	{// Method to create a text field for Database name
		dbNameText = new Text( firstGroup, SWT.BORDER );
		gdData1 = new GridData( GridData.FILL_HORIZONTAL );
		gdData1.horizontalSpan = 2;
		dbNameText.setLayoutData( gdData1 );
	}

	/**
	 * Creates a text field for URL of the database
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type GridData
	 */
	private void createURLText( final Composite firstGroup, GridData gdData1 )
	{// Method to create text field for databse URL
		urlText = new Text( firstGroup, SWT.BORDER );
		gdData1 = new GridData( GridData.FILL_HORIZONTAL );
		gdData1.horizontalSpan = 2;
		urlText.setLayoutData( gdData1 );
		validateURLText( urlText );
	}

	/**
	 * Creates a text field for User name for the database
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type GridData
	 */
	private void createUserText( final Composite firstGroup, GridData gdData1 )
	{// Method to create Database USER
		userText = new Text( firstGroup, SWT.BORDER );
		gdData1 = new GridData( GridData.FILL_HORIZONTAL );
		gdData1.horizontalSpan = 2;
		userText.setLayoutData( gdData1 );
	}

	/**
	 * Creates a text field for database password
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type GridData
	 */
	private void createPasswordText( final Composite firstGroup,
			GridData gdData1 )
	{// Method to create database Password
		passwordText = new Text( firstGroup, SWT.BORDER );
		passwordText.setEchoChar( '*' );// password will be displayed in the
		// form of ***********
		gdData1 = new GridData( GridData.FILL_HORIZONTAL );
		gdData1.horizontalSpan = 2;
		passwordText.setLayoutData( gdData1 );
	}

	/**
	 * Method to check the database connection
	 * 
	 * @param parent
	 *            of type Composite
	 */
	private void checkConnection( final Composite parent )
	{// method to check connection
		final DbmsInfo dbInfo = new DbmsInfo( );
		final String dbURL = driverType.getText( ) + ":@" + IPText.getText( )
				+ ":" + portText.getText( ) + ":" + dbNameText.getText( );
		// dbInfo.setDbUrl(urlText.getText().toString());
		dbInfo.setDbUrl( dbURL );
		dbInfo.setDbUser( userText.getText( ).toString( ) );
		dbInfo.setDbPassword( passwordText.getText( ).toString( ) );
		try
		{
			connection = dbManager.getDatabaseConnection( dbInfo );
			if( connection != null )
			{
				// MessageDialog.openInformation( parent.getShell( ), "Success",
				// "Connection test successful" );
				MessageDialog
						.openInformation(
											parent.getShell( ),
											"Success",
											SASConstants.CREATE_DS_DLG_CON_SUCCESS_INFO );
				dbManager.close( connection );
				connection = null;
			}
		}
		catch( SQLException e1 )
		{
			// MessageDialog.openError( parent.getShell( ), "Warning",
			// "Connection test unsuccessful" );
			MessageDialog
					.openError( parent.getShell( ), "Warning",
								SASConstants.CREATE_DS_DLG_CON_ERROR_INFO );
			final String Error = "Exception thrown in checkConnection() "
					+ "in class CreateDataSourceDialog.java";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( Error, e1 );

		}
	}

	/**
	 * Method used to display the dataSources
	 * 
	 * @param parent
	 *            of type Composite
	 */
	private void displayDataSource( Composite parent )
	{// method to display the list of available dataSources
		final DbmsInfo dbInfo = new DbmsInfo( );// java bean to store database
		// information
		dbInfo.setDbUrl( urlText.getText( ).toString( ) );
		dbInfo.setDbUser( userText.getText( ).toString( ) );
		dbInfo.setDbPassword( passwordText.getText( ).toString( ) );
		try
		{
			connection = dbManager.getDatabaseConnection( dbInfo );
		}
		catch( SQLException e1 )
		{
			MessageDialog.openError( parent.getShell( ), "Warning",
										"Connection Unsuccessful" );
			final String Error = "Exception thrown in displayDataSource() "
					+ "in class CreateDataSourceDialog.java";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( Error, e1 );
		}
		final String dataname = dsName.getText( ).toString( );
		IStructuredSelection select = ( IStructuredSelection ) DatabaseViewer.viewer
				.getSelection( );
		final TreeParent invisibleParent = ( TreeParent ) select
				.getFirstElement( );
		final TreeParent newdataSrc = new TreeParent( dataname );
		invisibleParent.addChild( newdataSrc );
		DatabaseViewer.viewer.refresh( invisibleParent, false );
		parent.getShell( ).close( );
	}

	/**
	 * Method to validate text field for database URL
	 * 
	 * @param urlText
	 *            of type Text
	 */
	private void validateURLText( final Text urlText ) // validation for
	// URLText field
	{
		urlText.addModifyListener( new ModifyListener( ) // implementation of
				// Listener
				{
					public void modifyText( ModifyEvent e )
					{
						if( urlText.getText( ) == ""
								|| urlText.getText( ) == null
								|| urlText.getText( ).contains( " " ) )
						{
							// testButton.setEnabled(false);
						}
						else
						{
							testButton.setEnabled( true ); // if some
							// characters are
							// entered in the
							// text field, OK
							// btn will be
							// enabled.
						}
					}
				} );
	}

	/**
	 * Method for validation for text field for DataSource
	 * 
	 * @param dsName
	 *            of type Text
	 */
	private void validateDataSourceText( final Text dsName )
	{ // validation for DataSourceField text
		dsName.addModifyListener( new ModifyListener( ) // implementation of
				// Listener
				{
					public void modifyText( ModifyEvent e )
					{
						if( dsName.getText( ) == ""
								|| dsName.getText( ) == null
								|| dsName.getText( ).contains( " " ) )
						{
							okButton.setEnabled( false );
						}
						else
						{
							okButton.setEnabled( true ); // if some
							// characters are
							// entered in the
							// text field, OK
							// btn will be
							// enabled.
						}
					}
				} );
	}

	/**
	 * Method for validation for other fields in the dialog box
	 * 
	 * @return a boolean value of true if other text fields are validated
	 *         completely, otherwise false
	 */

	private boolean validateOtherTextFields( )// validations for other text
	// fields
	{
		boolean fieldsFlag = false;
		final String tmp_userText = userText.getText( );
		// String tmp_urlText = urlText.getText();
		final String tmp_Ipadrs = IPText.getText( );
		final String tmp_Port = portText.getText( );
		final String tmp_dbName = dbNameText.getText( );
		if( tmp_userText == "" || tmp_userText == null
				|| tmp_userText.contains( " " ) || tmp_Ipadrs == ""
				|| tmp_Ipadrs == null || tmp_Ipadrs.contains( " " )
				|| tmp_Port == "" || tmp_Port == null
				|| tmp_Port.contains( " " ) || tmp_dbName == ""
				|| tmp_dbName == null || tmp_dbName.contains( " " ) )
		{
			fieldsFlag = false;
			// testButton.setEnabled(false);
		}
		else
		{
			fieldsFlag = true;
			// testButton.setEnabled(true);
		}
		return fieldsFlag;
	}

	/**
	 * Method to populate the database details in the XML file
	 * 
	 * @param parent
	 *            of type Composite
	 */

	private void populateData( final Composite parent )
	{// populate the required data into an xml file
		final DbmsInfo dbInfo = new DbmsInfo( );
		final String dbURL = driverType.getText( ) + ":@" + IPText.getText( )
				+ ":" + portText.getText( ) + ":" + dbNameText.getText( );
		dbInfo.setDataSourceName( dsName.getText( ).toString( ) );
		// dbInfo.setDbUrl(urlText.getText().toString());
		dbInfo.setDbUrl( dbURL );
		dbInfo.setDbUser( userText.getText( ).toString( ) );
		dbInfo.setDbPassword( passwordText.getText( ).toString( ) );
		DataWriter dataWriter = new DataWriter( );

		try
		{
			connection = dbManager.getDatabaseConnection( dbInfo );
		}
		catch( SQLException e1 )
		{
			MessageDialog.openError( parent.getShell( ), "Warning",
										"Connection Unsuccessful" );
			final String Error = "Exception thrown in populateData() in class "
					+ "CreateDataSourceDialog.java";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( Error, e1 );
			parent.getShell( ).close( );
			// MessageDialog.openError(parent.getShell(),"Warning",SASConstants.CREATE_DS_DLG_CON_ERROR_INFO);
		}
		final File dbmsFile = new File( dataSourceFilePath + dataSourceFileName );
		try
		{
			if( !dbmsFile.exists( ) )
			{
				dataWriter.writeToXmlFile( connection, dbInfo );
			}
			else
			{
				dataWriter.readAndwrite( connection, dbInfo ); // first it will
				// read and then
				// append the
				// contents to
				// the existing
				// xml file
			}

		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
		parent.getShell( ).close( );
	}

	/**
	 * Method to check the duplicate dataSource name
	 * 
	 * @param parent
	 *            of type Composite
	 * @return true if a DataSource already exists, otherwise false
	 */
	private boolean checkForDuplicateDsName( ) // checking for duplicate data
	// source name in the xml file
	{
		boolean validDs = false;
		final ArrayList dsNames = new DsReader( ).getDataSourceNames( );
		for( int i = 0; i < dsNames.size( ); i++ )
		{
			final String tempDsname = ( String ) dsNames.get( i );
			if( dsName.getText( ).equalsIgnoreCase( tempDsname ) )
			{
				setMessage( "Data Source name already exists, give different name" );
				// setMessage(SASConstants.CREATE_DS_DLG_DS_EROOR_MSG);
				// dsName.setText("");
				dsName.setFocus( );
				okButton.setEnabled( false );
				validDs = true;
			}
			else
			{
				validDs = false;
			}
		}
		return validDs;
	}

	/**
	 * Method to validate Combo box for Database type
	 * 
	 * @return true if Combo box is validated completely,otherwise false
	 */
	private boolean validateDatabaseTypeCombo( )
	{// Method to validate a Database type Combo box
		boolean textFlag = false;
		final String tmp_dbType = databaseType.getText( );
		if( tmp_dbType == "" || tmp_dbType == null || tmp_dbType.contains( " " ) )
		{
			textFlag = false;
		}
		else
		{
			textFlag = true;
		}
		return textFlag;
	}

	/**
	 * Method for implementation of test button functionality
	 * 
	 * @param parent
	 *            of type Composite
	 */
	private void doTestButton( final Composite parent )
	{// Action for pressing the Test button
		if( !validateDatabaseTypeCombo( ) )// validation for database type
		// Combo box
		{
			MessageDialog.openInformation( parent.getShell( ), "Message",
											"Please select a database type" );
			// MessageDialog.openInformation(parent.getShell(),"Message",CREATE_DS_DLG_OPENINFO);
		}
		else if( !validateOtherTextFields( ) )// validation for other fields
		{
			MessageDialog
					.openInformation( parent.getShell( ), "Message",
										"Please enter the valid information "
												+ "in the following text boxes" );
			// MessageDialog.openInformation(parent.getShell(),"Message",SASConstants.CREATE_DS_DLG_OPENINFO_TEXTFLD);
		}
		else
		{
			checkConnection( parent ); // for checking successful database
			// connection
		}
	}

	/**
	 * Method for implementation of Ok button functionality
	 * 
	 * @param parent
	 *            of type Composite
	 */
	private void doOKButton( final Composite parent )
	{// Action for pressing the OK button
		
		//setMessage("Please wait a while ",IMessageProvider.WARNING);
				
		duplicateDsName = checkForDuplicateDsName( ); // checking for
		// duplicate datasource
		// name if exists
		try
		{
			if( duplicateDsName == true )
			{
				// do nothing
			}
			else if( !validateDatabaseTypeCombo( ) )// validation for database
			// type Combo
			{
				MessageDialog
						.openInformation( parent.getShell( ), "Message",
											"Please select a database type" );
				// MessageDialog.openInformation(parent.getShell(),"Message",SASConstants.CREATE_DS_DLG_OPENINFO_DBTYPE_COMBO);
			}
			else if( !validateOtherTextFields( ) )// Validation for other
			// fields
			{
				MessageDialog
						.openInformation( parent.getShell( ), "Message",
											"Please enter the valid information in the following text boxes" );
				// MessageDialog.openInformation(parent.getShell(),"Message",SASConstants.CREATE_DS_DLG_OPENINFO_TEXTFLD);
			}
			else
			{
				populateData( parent ); // populating the all database
				// information details to an xml file.
			}
		}
		catch( Exception e )
		{
			MessageDialog.openError( parent.getShell( ), "Error",
										"Unable to connect to the database" );
		}
	}

	/**
	 * *************************** Accessor and Mutator methods for JUnit test
	 * cases ******************************
	 */
	/**
	 * accessor methods for Port Text
	 */
	public Text getPortText( )
	{
		return portText;
	}

	/**
	 * 
	 * @param portText
	 */
	public void setPortText( Text portText )
	{
		this.portText = portText;
	}

	/**
	 * Accessor methods to get DataSource name
	 * 
	 * @return
	 */
	public Text getDsName( )
	{
		return dsName;
	}

	/**
	 * 
	 * @param dsName
	 */
	public void setDsName( Text dsName )
	{
		this.dsName = dsName;
	}

	/**
	 * Accessor methods to retieve password
	 * 
	 * @return
	 */
	public Text getPasswordText( )
	{
		return passwordText;
	}

	/**
	 * 
	 * @param passwordText
	 */
	public void setPasswordText( Text passwordText )
	{
		this.passwordText = passwordText;
	}

	/**
	 * Accessor methods to to get URL of the database
	 * 
	 * @return database url
	 */

	public Text getUrlText( )
	{
		return urlText;
	}

	/**
	 * 
	 * @param urlText
	 */
	public void setUrlText( Text urlText )
	{
		this.urlText = urlText;
	}

	/**
	 * Accessor methods to get User name
	 * 
	 * @return userText
	 */
	public Text getUserText( )
	{
		return userText;
	}

	/**
	 * 
	 * @param userText
	 */
	public void setUserText( Text userText )
	{
		this.userText = userText;
	}

	/**
	 * 
	 * @return database type
	 */
	public Combo getDatabaseType( )
	{
		return databaseType;
	}

	/**
	 * 
	 * @param databaseType
	 */
	public void setDatabaseType( Combo databaseType )
	{
		this.databaseType = databaseType;
	}

	/**
	 * 
	 * @return IP address of the database server
	 */
	public Text getIPText( )
	{
		return IPText;
	}

	/**
	 * 
	 * @param text
	 */
	public void setIPText( Text text )
	{
		IPText = text;
	}

	/**
	 * 
	 * @return database name
	 */
	public Text getDbNameText( )
	{
		return dbNameText;
	}

	/**
	 * 
	 * @param dbNameText
	 */
	public void setDbNameText( Text dbNameText )
	{
		this.dbNameText = dbNameText;
	}

}
