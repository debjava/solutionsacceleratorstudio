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
 * 
 */
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import com.rrs.corona.Sas.DataSourceList;
import com.rrs.corona.Sas.SolutionAdapter;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.datasource.DatabaseManager;
import com.rrs.corona.solutionsacceleratorstudio.datasource.DbmsInfo;

public class RefreshDs
{
	/**
	 * Logger to log
	 */
	protected Logger				logger				= Logger
																.getLogger( "RefreshDs.class" );
	/**
	 * String variable for dataSource name
	 */
	private transient String		dataSourceName;												// for
	// dataSouce
	// Name
	/**
	 * String variables for database user name
	 */
	private transient String		username;														// For
	// database
	// user
	// name
	/**
	 * String variables for database URl
	 */
	private transient String		url;															// for
	// database
	// url
	/**
	 * String variables for database password
	 * 
	 */
	private transient String		password;														// for
	// database
	// password
	/**
	 * SQL Connection
	 */
	private transient Connection	connection			= null;									// for
	// SQL
	// connection
	/**
	 * String variable for XML file name
	 */
	private transient String		dataSourceFileName	= SASConstants.DATASOURCE_FILENAME;		// File

	// name
	// of
	// the
	// XML
	// file
	// containing
	// data

	/**
	 * Default Constructor
	 * 
	 */
	public RefreshDs( )
	{
	}

	/**
	 * Method to refresh a selected dataSource This method is used when new
	 * tables are added to a particular dataSource, it will fetch the updated
	 * tables and its associated information
	 * 
	 * @param dsName
	 *            of type String
	 * @param table
	 *            of type TreeObject
	 */
	public void refreshDs( final String dsName, final TreeObject table )
	{// Method to fetch the updated tables and fields from the database
		final DbmsInfo dbInfo = new DbmsInfo( );
		final DsReader dsreader = new DsReader( );// Object to parse the XML
		// file
		SolutionAdapter solutionAdapter = ( SolutionAdapter ) dsreader.getSA( );
		final List dataSourceList = solutionAdapter.getDataSource( );
		for( int i = 0; i < dataSourceList.size( ); i++ )
		{
			DataSourceList dataSource = ( DataSourceList ) dataSourceList
					.get( i );
			dataSourceName = dataSource.getDataSourceName( );
			if( dataSourceName.equals( dsName ) )
			{
				username = dataSource.getUserName( );
				url = dataSource.getURL( );
				password = dataSource.getPassword( );
				dbInfo.setDataSourceName( dataSourceName );
				dbInfo.setDbUser( username );
				dbInfo.setDbUrl( url );
				dbInfo.setDbPassword( password );
				updateDataSourceInfo( dbInfo, table );// Method to update the
				// XML file with updated
				// information from the
				// database
			}
			else
			{
				// do nothing;
			}
		}
	}

	/**
	 * Method to update the XML file with updated information from the database
	 * 
	 * @param dbInfo
	 * @param table
	 */
	public void updateDataSourceInfo( final DbmsInfo dbInfo,
			final TreeObject table )
	{// Method to write to the XML file when new table or fields are added to
		// a database
		logger.info( " The dataSource should be deleted first .....  "
				+ dbInfo.getDataSourceName( ) );
		deleteDatabaseInfo( dbInfo );// first it will delete the existing or
		// selected dataSource information
		fetchDatabaseInfo( dbInfo );// again it will fetch the database
		// information and store in the xml file
		showUpdatedDsInfoInTreeView( dbInfo, table );
	}

	/**
	 * Method to delete a dataSource information from the XML file
	 * 
	 * @param dbInfo
	 *            of type DbmsInfo
	 */
	public void deleteDatabaseInfo( final DbmsInfo dbInfo )
	{// Method used to delete the dataSource Information
		new DsDelete( ).deleteDataSource( dbInfo.getDataSourceName( ) );
	}

	/**
	 * Method to fetch databse Connection and to update the XML file
	 * 
	 * @param dbInfo
	 *            of type DbmsInfo
	 */
	public void fetchDatabaseInfo( final DbmsInfo dbInfo )
	{// Method to update the database information
		try
		{
			connection = new DatabaseManager( ).getDatabaseConnection( dbInfo );
			updateDsInfoXMLFile( connection, dbInfo );
		}

		catch( SQLException se )
		{
			final String errMsg = "Exception thrown in Method "
					+ "**fetchDatabaseInfo()** in Class [ RefreshDs.java ]";
			SolutionsacceleratorstudioPlugin.getDefault( )
					.logError( errMsg, se );
			se.printStackTrace( );
		}
	}

	/**
	 * Method to update all the database information to the XML file
	 * 
	 * @param connection
	 *            of type SQL Connection
	 * @param dbInfo
	 *            of type DbmsInfo
	 */
	public void updateDsInfoXMLFile( final Connection connection,
			final DbmsInfo dbInfo )
	{// Updating the XML file
		DataWriter dataWriter = new DataWriter( );
		File dbmsFile = new File( dataSourceFileName );
		if( !dbmsFile.exists( ) )
		{
			dataWriter.writeToXmlFile( connection, dbInfo );
		}
		else
		{
			dataWriter.readAndwrite( connection, dbInfo ); // first it will
			// read and then
			// append the
			// contents to the
			// existing xml file
		}
	}

	/**
	 * Method to show the updated dataSource Information to the tree view
	 * 
	 * @param dbInfo
	 *            of type DbmsInfo
	 * @param table
	 *            of type TreeObject
	 */
	public void showUpdatedDsInfoInTreeView( final DbmsInfo dbInfo,
			final TreeObject table )
	{// Method to show the updated dataSource information in a tree view
		TreeParent parentitem = ( TreeParent ) table;
		TreeObject obj[] = parentitem.getChildren( );
		for( int size = 0; size < obj.length; size++ )
		{
			parentitem.removeChild( obj[size] );
		}
		DatabaseViewer.viewer.refresh( parentitem, true );

		int level = 1;// for progress bar
		Shell shell = new Shell( new Shell( ).getDisplay( ), SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL );
		ProgressBar pb = createProgBar( shell );
		Label progBtn = new Label( shell, SWT.NONE | SWT.CENTER );
		GridData gd = new GridData( GridData.FILL_HORIZONTAL );
		gd.verticalSpan = 4;
		progBtn.setLayoutData( gd );
		shell.open( );
		level = 1;// for progress bar
		ArrayList totalTableNames = new DsReader( )
				.getTotalTableNamesOfOneDS( dbInfo.getDataSourceName( ) );
		pb.setMaximum( totalTableNames.size( ) );
		shell.setText( "Updating  " + dbInfo.getDataSourceName( ) );

		for( int j = 0; j < totalTableNames.size( ); j++ )
		{
			String tab_Name = ( ( DsTableInfo ) totalTableNames.get( j ) )
					.getDsTableName( );
			TreeParent treeParent2 = new TreeParent( tab_Name );
			// new line added below
			treeParent2.setDataSourceName( dbInfo.getDataSourceName( ) );// for
			// setting
			// dataSource
			// name
			parentitem.addChild( treeParent2 );
			treeParent2.setType( "table" );
			final ArrayList totalFieldNames_OneTab = new DsReader( )
					.getTotalFieldNamesFromOneTab( dbInfo.getDataSourceName( ),
													tab_Name );
			progBtn.setAlignment( SWT.LEFT );
			progBtn.setText( "Loading tables " + tab_Name );
			pb.setSelection( level );
			level = level + 1;

			for( int k = 0; k < totalFieldNames_OneTab.size( ); k++ )
			{
				Object fieldObject = ( Object ) totalFieldNames_OneTab.get( k );
				final DsFieldInfo dsfieldInfo = ( DsFieldInfo ) fieldObject;
				final String showFieldNType = dsfieldInfo.getDsFieldName( )
						+ "   ::   " + "< " + dsfieldInfo.getDsFieldType( )
						+ " >";
				TreeParent fieldObject11 = new TreeParent( showFieldNType );
				fieldObject11.setDataType( dsfieldInfo.getDsFieldType( ) );
				fieldObject11.setDataSourceName( dbInfo.getDataSourceName( ) );
				fieldObject11.setTableName( treeParent2.getName( ) );
				treeParent2.addChild( fieldObject11 );
				fieldObject11.setType( "field" );
				fieldObject11.setPrimaryKey( dsfieldInfo.isPrimaryKey( ) );// for
				// setting
				// primary
				// key
				// following lines are used to set the required dataSource name
				// URL,userName and password for the tree object
				table.setDataSourceName( dsfieldInfo.getDataSourceName( ) );
				table.setDbURL( dsfieldInfo.getDataSourceURL( ) );
				table.setDbUserName( dsfieldInfo.getDataSourceUserName( ) );
				table.setDbPassword( dsfieldInfo.getDataSourcePassword( ) );
			}
		}
		DatabaseViewer.viewer.refresh( parentitem, true );
		shell.close( );
	}

	/**
	 * Method to create a progress bar while showing the updated information to
	 * the user
	 * 
	 * @param shell
	 *            of type Shell
	 * @return a progress bar
	 */
	private ProgressBar createProgBar( final Shell shell )
	{//Method to return a progress bar		
		ProgressBar pb = new ProgressBar( shell, SWT.CENTER );
		try
		{
			shell.setLayout( new GridLayout( 1, false ) );
			pb.setMinimum( 0 );
			GridData gd1 = new GridData( GridData.FILL_HORIZONTAL );
			gd1.verticalSpan = 12;
			pb.setLayoutData( gd1 );
			shell.setSize( 650, 150 );
		}
		catch( Exception ex )
		{
		}
		return pb;
	}

}
