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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import com.rrs.corona.Sas.DataSourceList;
import com.rrs.corona.Sas.FieldList;
import com.rrs.corona.Sas.ObjectFactory;
import com.rrs.corona.Sas.SolutionAdapter;
import com.rrs.corona.Sas.TableList;
import com.rrs.corona.solutionsacceleratorstudio.datasource.DatabaseManager;
import com.rrs.corona.solutionsacceleratorstudio.datasource.DbmsInfo;
import com.rrs.corona.solutionsacceleratorstudio.datasource.FieldInfo;
import com.rrs.corona.solutionsacceleratorstudio.datasource.TableInfo;
import com.rrs.corona.common.CommonConstants;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;

public class DataWriter
{
	/**
	 * DatabaseManager object is used to perform database specific tasks
	 */
	private transient DatabaseManager	dbManager			= new DatabaseManager( );		// Object
	// for
	// database
	// connectivity
	/**
	 * File path of the XML file
	 */
	private transient String			dataSourceFilePath	= SASConstants.SAS_ROOT;		// file
	// path
	// of
	// the
	// XML
	// file
	// containg
	// database
	// information
	/**
	 * File name of the XML file which contains database details
	 */
	private transient String			dataSourceFilename	= SASConstants.SAS_XML;		// file
	// name
	// of
	// the
	// XML
	// file
	// containing
	// database
	// informaion
	/**
	 * JAXB context for SAS schema
	 */
	private transient String			jaxBContext			= SASConstants.SASJAXB_CONTEXT;

	/**
	 * Method to write the database details to a new XML file
	 * 
	 * @param connection
	 *            of type SQL Connection
	 * @param dbInfo
	 *            of type DbmsInfo
	 */
	public void writeToXmlFile( final Connection connection,
			final DbmsInfo dbInfo )
	{// This method will write to the XML file for the first time
		try
		{
			new File( dataSourceFilePath ).mkdirs( );
			ObjectFactory objectFactory = new ObjectFactory( );
			SolutionAdapter solutionAdapter = objectFactory
					.createSolutionAdapter( );
			populateDbInfoToFile( connection, dbInfo, objectFactory,
									solutionAdapter );// method to populate
			// the database data to
			// XML file
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in Method "
					+ "**writeToXmlFile()** in class **DataWriter.java**";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			e.printStackTrace( );
		}
	}

	/**
	 * Method to update an laready existing XML file
	 * 
	 * @param connection
	 *            of type SQL Connection
	 * @param dbInfo
	 *            of type DbmsInfo
	 */
	public void readAndwrite( final Connection connection, final DbmsInfo dbInfo )
	{// This method will write to the XML file , if the XML file already
		// exists
		// This method reads the existing XML file containg data and update the new
		// data
		try
		{
			final JAXBContext jaxbContext = JAXBContext
					.newInstance( jaxBContext );
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller( );
			// Sas sas = (Sas)unMarshaller.unmarshal(new
			// FileInputStream(dataSourceFilename));
			SolutionAdapter solutionAdapter = ( SolutionAdapter ) unMarshaller
					.unmarshal( new FileInputStream( dataSourceFilePath
							+ dataSourceFilename ) );
			ObjectFactory objectFactory = new ObjectFactory( );
			populateDbInfoToFile( connection, dbInfo, objectFactory,
									solutionAdapter );// Method to populate
			// database details to
			// the XML file
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in Method "
					+ "**readAndwrite()** in class **DataWriter.java**";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			e.printStackTrace( );
		}
	}

	/**
	 * Method to populate the database details to the XML file
	 * 
	 * @param connection
	 * @param dbInfo
	 * @param objectFactory
	 * @param sas
	 */
	public void populateDbInfoToFile( final Connection connection,
			final DbmsInfo dbInfo, final ObjectFactory objectFactory,
			final SolutionAdapter sas )
	{// This is the actual method that will write the database information to
		// the XML file
		// Method to populate the database information to the XML file
		try
		{
			DataSourceList dataSourceList = objectFactory
					.createDataSourceList( ); // to get first tag
			dataSourceList.setDataSourceName( dbInfo.getDataSourceName( ) ); // writing
			// data
			// source
			// to
			// the
			// xml
			// file
			dataSourceList.setURL( dbInfo.getDbUrl( ) );
			dataSourceList.setUserName( dbInfo.getDbUser( ) );
			dataSourceList.setPassword( dbInfo.getDbPassword( ) );
			dataSourceList.setSelectedDataSource( "false" );// default value
			final ArrayList tableNamesList = dbManager
					.getAllTableNames( connection );
			// populating the table information to the XML file
			for( int i = 0; i < tableNamesList.size( ); i++ )
			{
				// TableList is a list of database tables, it is from schema
				final TableList tabList = objectFactory.createTableList( ); // get
				// a
				// table
				// tag
				Object obj = ( Object ) tableNamesList.get( i );
				final TableInfo ti1 = ( TableInfo ) obj;// TableInfo is a simple
				// java bean
				final String TableName = ti1.getTableName( );
				tabList.setTableName( TableName );
				tabList.setHideTable( "true" );
				final ArrayList fieldInfoList = dbManager
						.getEachTableInfo( connection, TableName );
				// Populating the field information to the XML file
				for( Iterator iter = fieldInfoList.iterator( ); iter.hasNext( ); )
				{
					// FieldList is a list of database table fields, it is from
					// schema
					final FieldList fieldList = objectFactory.createFieldList( ); // to
					// get
					// field
					// tag
					Object obj1 = ( Object ) iter.next( );
					FieldInfo fild = ( FieldInfo ) obj1;// FieldInfo is a simple
					// java bean
					fieldList.setFieldName( fild.getFieldName( ) );
					fieldList.setFieldType( fild.getFieldType( ) );

					if( fild.getPkKeyFieldName( ) )
					{
						fieldList.setHideField( "true" );// for setting
						// primary key
					}
					else
					{
						fieldList.setHideField( "false" );// for setting
						// primary key
					}

					/*
					 * if(fild.getPkKeyFieldName().equals("") ||
					 * fild.getPkKeyFieldName()==null) {
					 * fieldList.setHideField("false");//for setting primary key }
					 * else { fieldList.setHideField("true");//for setting
					 * primary key }
					 */

					tabList.getField( ).add( fieldList );
				}
				dataSourceList.getTable( ).add( tabList );
			}
			sas.getDataSource( ).add( dataSourceList );

			final JAXBContext jaxbContext = JAXBContext
					.newInstance( jaxBContext );
			final FileOutputStream fileOutputStream = new FileOutputStream(
																			dataSourceFilePath
																					+ dataSourceFilename ); // temporary
			// hardcoding
			// for
			// writing
			// to a
			// file
			final Marshaller marshaller = jaxbContext.createMarshaller( );
			marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT,
									Boolean.TRUE );
			marshaller.marshal( sas, fileOutputStream );
			fileOutputStream.close( );
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in Method "
					+ "**populateDbInfoToFile()** in class **DataWriter.java**";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			e.printStackTrace( );
		}
	}

}
