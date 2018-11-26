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
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import com.rrs.corona.Sas.DataSourceList;
import com.rrs.corona.Sas.FieldList;
import com.rrs.corona.Sas.SolutionAdapter;
import com.rrs.corona.Sas.TableList;
import com.rrs.corona.common.CommonConstants;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.datasource.DbmsInfo;

public class DsReader
{
	/**
	 * File path of the dataSource xml file
	 */
	private transient String	dataSourceFilePath			= SASConstants.SAS_ROOT;		// File
	// path
	// of
	// the
	// xml
	// file
	// containing
	// database
	// information
	/**
	 * File name of the XML file
	 */
	private transient String	dataSourceFilename			= SASConstants.SAS_XML;		// File
	// name
	// of
	// the
	// XML
	// file
	/**
	 * SAS jaxbContext
	 */
	private transient String	jaxBContext					= SASConstants.SASJAXB_CONTEXT;
	/**
	 * List of dataSourceNames
	 */
	private transient ArrayList	dataSourceNames				= new ArrayList( );			// An
	// arraylist
	// containing
	// list
	// of
	// dataSource
	// names
	/**
	 * ArrayList containing the list of table names
	 */
	private transient ArrayList	dataSourceTableNames		= null;						// ArrayList
	// containg
	// list
	// of
	// table
	// names
	/**
	 * ArrayList containing dataSource table Field Information
	 */
	private transient ArrayList	dataSourcetableFieldsInfo	= null;						// ArrayList

	// containing
	// field
	// information

	/**
	 * Method to obtain a list of DataSourceNames
	 * 
	 * @return a an ArrayList of DataSource names
	 */
	public ArrayList getDataSourceNames( )
	{// Method to get the list of available dataSource names from the XML
		// file
		try
		{
			final File dbmsFile = new File( dataSourceFilePath
					+ dataSourceFilename );
			if( !dbmsFile.exists( ) )
			{
				// do nothing
			}
			else
			{
				JAXBContext jaxbCtx = JAXBContext.newInstance( jaxBContext );
				Unmarshaller unmarshal = jaxbCtx.createUnmarshaller( );
				// Sas sas = (Sas)unmarshal.unmarshal(new
				// FileInputStream(dataSourceFilename));
				SolutionAdapter sas = ( SolutionAdapter ) unmarshal
						.unmarshal( new FileInputStream( dataSourceFilePath
								+ dataSourceFilename ) );// for modified
				// schema
				final List dataSourceList = sas.getDataSource( );

				for( int i = 0; i < dataSourceList.size( ); i++ )
				{
					DataSourceList dataSource = ( DataSourceList ) dataSourceList
							.get( i );
					String currentDsName = dataSource.getDataSourceName( );
					dataSourceNames.add( currentDsName );
				}
			}
			return dataSourceNames;
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in Method "
					+ "**getDataSourceNames()** in class **DsReader.java**";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			e.printStackTrace( );
		}
		return dataSourceNames;
	}

	/**
	 * Method to obtain a list all dataSource information
	 * 
	 * @return an ArrayList of object containing dataSource Information
	 */
	public ArrayList getDsInfo( )
	{// This method is used to retrieve all information
		// like dataSource name, URl,userName,password etc
		// for all the avilable dataSources
		final ArrayList dsInfoBeanList = new ArrayList( );
		try
		{
			File dbmsFile = new File( dataSourceFilePath + dataSourceFilename );
			if( !dbmsFile.exists( ) )
			{
				// do nothing
			}
			else
			{
				JAXBContext jaxbCtx = JAXBContext.newInstance( jaxBContext );
				Unmarshaller unmarshal = jaxbCtx.createUnmarshaller( );
				SolutionAdapter sas = ( SolutionAdapter ) unmarshal
						.unmarshal( new FileInputStream( dataSourceFilePath
								+ dataSourceFilename ) );// for modified
				// schema
				List dataSourceList = sas.getDataSource( );

				for( int i = 0; i < dataSourceList.size( ); i++ )
				{
					DataSourceList dataSource = ( DataSourceList ) dataSourceList
							.get( i );
					String currentDsName = dataSource.getDataSourceName( );
					DbmsInfo dbmsInfo = new DbmsInfo( );
					dbmsInfo.setDataSourceName( currentDsName );
					dbmsInfo.setDbUrl( dataSource.getURL( ) );
					dbmsInfo.setDbUser( dataSource.getUserName( ) );
					dbmsInfo.setDbPassword( dataSource.getPassword( ) );
					dsInfoBeanList.add( dbmsInfo );
				}
			}
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in Method "
					+ "**getDsInfo()** in class DsReader**";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			e.printStackTrace( );
		}

		return dsInfoBeanList;
	}

	/**
	 * Method to obtain a SolutionAdapter object
	 * 
	 * @return SolutionAdapter object
	 */
	public SolutionAdapter getSA( )
	{// This method returns an object of type SolutionAdapter
		try
		{
			JAXBContext jaxbCtx = JAXBContext.newInstance( jaxBContext );
			Unmarshaller unmarshal = jaxbCtx.createUnmarshaller( );
			SolutionAdapter solutionAdapter = ( SolutionAdapter ) unmarshal
					.unmarshal( new FileInputStream( dataSourceFilePath
							+ dataSourceFilename ) );// for modified schema
			return solutionAdapter;
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in Method "
					+ "**getSA()** in class **[DsReader.java]**";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			e.printStackTrace( );
		}
		return null;
	}

	/**
	 * Method to get a list total table names
	 * 
	 * @param dsName
	 * @return a list of total tableNames present in one dataSource
	 */
	public ArrayList getTotalTableNamesOfOneDS( final String dsName )
	{// Method to get the list of table names from a particular dataSource
		// from the XML file
		dataSourceTableNames = new ArrayList( );
		try
		{
			JAXBContext jaxbCtx = JAXBContext.newInstance( jaxBContext );
			Unmarshaller unmarshal = jaxbCtx.createUnmarshaller( );
			SolutionAdapter solutionAdapter = getSA( );
			List dataSourceList = solutionAdapter.getDataSource( );// List of
			// DataSources

			for( int i = 0; i < dataSourceList.size( ); i++ )
			{
				DataSourceList dataSource = ( DataSourceList ) dataSourceList
						.get( i );
				final String tempDsName = dataSource.getDataSourceName( );
				if( tempDsName.equals( dsName ) )
				{
					List tableList = dataSource.getTable( );
					for( int j = 0; j < tableList.size( ); j++ )
					{
						TableList tab = ( TableList ) tableList.get( j );
						final String tabname = tab.getTableName( );
						DsTableInfo tabInfo = new DsTableInfo( );// DsTableInfo
						// a Java
						// bean
						tabInfo.setDataSourceName( dataSource
								.getDataSourceName( ) );
						tabInfo.setDataSourceURL( dataSource.getURL( ) );
						tabInfo
								.setDataSourceUserName( dataSource
										.getUserName( ) );
						tabInfo
								.setDataSourcePassword( dataSource
										.getPassword( ) );
						tabInfo.setDsTableName( tabname );
						dataSourceTableNames.add( tabInfo );
					}
				}
			}
			return dataSourceTableNames;
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
		return null;
	}

	/**
	 * Method to get a list of field names from one table
	 * 
	 * @param dsName
	 *            of type String
	 * @param TableName
	 *            of type String
	 * @return total field names list from one table
	 */
	public ArrayList getTotalFieldNamesFromOneTab( final String dsName,
			final String TableName )
	{// Method to get total field names and its associated information from
		// the XML file
		dataSourcetableFieldsInfo = new ArrayList( );
		try
		{
			JAXBContext jaxbCtx = JAXBContext.newInstance( jaxBContext );
			Unmarshaller unmarshal = jaxbCtx.createUnmarshaller( );
			SolutionAdapter solutionAdapter = getSA( );
			List dataSourceList = solutionAdapter.getDataSource( );

			for( int i = 0; i < dataSourceList.size( ); i++ )
			{
				DataSourceList dataSource = ( DataSourceList ) dataSourceList
						.get( i );
				final String tempDsName = dataSource.getDataSourceName( );
				if( tempDsName.equals( dsName ) )
				{
					List tableList = dataSource.getTable( );
					for( int j = 0; j < tableList.size( ); j++ )
					{
						TableList tab = ( TableList ) tableList.get( j );
						final String tabname = tab.getTableName( );
						if( tabname.equals( TableName ) )
						{
							List fieldList = tab.getField( );
							for( int k = 0; k < fieldList.size( ); k++ )
							{
								FieldList field = ( FieldList ) fieldList
										.get( k );
								final DsFieldInfo dsfieldInfo = new DsFieldInfo( );// DsFieldInfo
								// a
								// Java
								// Bean
								dsfieldInfo.setDataSourceName( dataSource
										.getDataSourceName( ) );
								dsfieldInfo.setDataSourceURL( dataSource
										.getURL( ) );
								dsfieldInfo.setDataSourceUserName( dataSource
										.getUserName( ) );
								dsfieldInfo.setDataSourcePassword( dataSource
										.getPassword( ) );
								dsfieldInfo.setDsFieldName( field
										.getFieldName( ) );
								dsfieldInfo.setDsFieldType( field
										.getFieldType( ) );

								if( field.getHideField( ).equals( "true" ) )
								{
									dsfieldInfo.setPrimaryKey( true );
								}
								else
								{
									dsfieldInfo.setPrimaryKey( false );
								}
								dataSourcetableFieldsInfo.add( dsfieldInfo );
							}
						}
					}
				}
				else
				{
					// do nothing
				}
			}
			return dataSourcetableFieldsInfo;
		}
		catch( Exception e )
		{
			final String errMsg = "exception thrown in Method "
					+ "**getTotalFieldNamesFromOneTab()** in class [DsReader.java]";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			e.printStackTrace( );
		}
		return null;
	}

	/**
	 * Method to provide the visibility of a particular DataSource name
	 * 
	 * @param DsName
	 * @return visibility of type String
	 */
	public String getDsVisibility( final String DsName )
	{// This method returns the visibility of a Particular dataSource name,
		// ie wheateher to show at startup or not
		String visibility = null;
		try
		{
			JAXBContext jaxbCtx = JAXBContext.newInstance( jaxBContext );
			Unmarshaller unmarshal = jaxbCtx.createUnmarshaller( );
			SolutionAdapter solutionAdapter = getSA( );
			List dataSourceList = solutionAdapter.getDataSource( );

			for( int i = 0; i < dataSourceList.size( ); i++ )
			{
				DataSourceList dataSource = ( DataSourceList ) dataSourceList
						.get( i );
				final String currentDsName = dataSource.getDataSourceName( );

				if( currentDsName.equals( DsName ) )
				{
					visibility = dataSource.getSelectedDataSource( );
					break;
				}
			}
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in method "
					+ "**getDsVisibility()** in class [Dsreader.java]";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			e.printStackTrace( );
			//No value set for visibility
		}
		return visibility;
	}

}
