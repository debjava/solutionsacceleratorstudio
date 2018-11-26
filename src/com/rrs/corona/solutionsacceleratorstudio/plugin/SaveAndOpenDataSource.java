/*******************************************************************************
 * @rrs_start_copyright
 * 
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved. This
 * software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Red Rabbit Software. ©
 * 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights
 * reserved. Red Rabbit Software - Development Program 5 of 15 $Id:
 * SaveAndOpenDataSource.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $
 * $Source:
 * /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/plugin/SaveAndOpenDataSource.java,v $
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

import java.util.Iterator;
import java.util.List;

import com.rrs.corona.sasadapter.DataSourceDetails;
import com.rrs.corona.sasadapter.DataSourceType;
import com.rrs.corona.sasadapter.FieldList;
import com.rrs.corona.sasadapter.ObjectFactory;
import com.rrs.corona.sasadapter.TableList;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SolutionAdapterView;

/**
 * This class is used to save the data source information for a particular
 * solutionsadapter in an xml file
 * 
 * @author Maharajan
 * 
 */
public class SaveAndOpenDataSource extends DatabaseViewer
{
	/**
	 * This method is used to save the datasource inforamtion and populate the
	 * schema this schema will responsible for storing the database information
	 * in an xml file
	 * 
	 * @param dataSourceObj
	 *            This is of type array of TreeObject this will contain the
	 *            datasource in the tree
	 * @param dataSourceType
	 *            This is of type DataSourceType will be used to store the
	 *            datasource information
	 */
	public void saveDatasourceToFile( TreeObject[] dataSourceObj,
			final DataSourceType dataSourceType )
	{
		try
		{
			for( int dscount = 0; dscount < dataSourceObj.length; dscount++ )
			{
				TreeParent dataSourceItem = ( TreeParent ) dataSourceObj[dscount];
				ObjectFactory objFact = new ObjectFactory( );
				DataSourceDetails dsDetails = objFact.createDataSourceDetails( );
				dsDetails.setDataSourceName( dataSourceItem.getName( ) );
				dsDetails.setURL( dataSourceItem.getDbURL( ) );
				dsDetails.setUserName( dataSourceItem.getDbUserName( ) );
				dsDetails.setPassword( dataSourceItem.getDbPassword( ) );
				TreeObject[] tableObj = dataSourceItem.getChildren( );
				saveTableList( tableObj, dataSourceType );
				dataSourceType.setDataSource( dsDetails );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}

	/**
	 * This method is used to store the table list present under the datasource
	 * in the tree will be populated in the schema object and will be saved in
	 * the xml file
	 * 
	 * @param tableObj
	 *            This is of type array of TreeObject will contains the tables
	 * @param dataSourceType
	 *            This is of type DataSourceType this will store the
	 *            informations about the tables
	 * @throws Exception
	 *             It will throws JAXBException
	 */
	private void saveTableList( TreeObject[] tableObj,
			final DataSourceType dataSourceType ) throws Exception
	{
		for( int tcount = 0; tcount < tableObj.length; tcount++ )
		{
			TreeParent tableItem = ( TreeParent ) tableObj[tcount];
			ObjectFactory objFact = new ObjectFactory( );
			TableList tableList = objFact.createTableList( );
			tableList.setTableName( tableItem.getName( ) );
			tableList.setHideTable( "hide table" );
			TreeObject[] fieldObj = tableItem.getChildren( );
			saveFieldList( fieldObj, tableList );
			dataSourceType.getTable( ).add( tableList );
		}
	}

	/**
	 * This method is used to store the column information present under each
	 * table and also the mapping information with the dataobject will also be
	 * stored in the xml file
	 * 
	 * @param fieldObj
	 *            This is of type array of TreeObject which contains the fields
	 * @param tableList
	 *            This is of type TableList schema object used to store the
	 *            field information and also the mapping information in the xml
	 *            file
	 * @throws Exception
	 *             It will throws JAXBException
	 */
	private void saveFieldList( TreeObject[] fieldObj, final TableList tableList )
			throws Exception
	{
		for( int fcount = 0; fcount < fieldObj.length; fcount++ )
		{
			TreeParent fieldItem = ( TreeParent ) fieldObj[fcount];
			ObjectFactory objFact = new ObjectFactory( );
			FieldList fieldList = objFact.createFieldList( );
			fieldList.setFieldName( fieldItem.getName( ) );
			fieldList.setFieldType( fieldItem.getDataType( ) );
			fieldList
					.setPrimaryKey( String.valueOf( fieldItem.isPrimaryKey( ) ) );
			if( fieldItem.hasChildren( ) )
			{
				TreeObject[] mapField = fieldItem.getChildren( );
				fieldList.setDSDOMap( mapField[0].getName( ) );
			}
			tableList.getFields( ).add( fieldList );
		}
	}

	/**
	 * This method is used to open the datasource information from the xml file
	 * and populate the details in the tree. This tree will a data source tree
	 * item
	 * 
	 * @param dsNode
	 *            This is of type TreeParent which will have the child as data
	 *            source tree item
	 * @param dataSourceType
	 *            This is of type DataSourceType which will have the data source
	 *            information this is actually a schema object
	 */
	public void openDataSourceFromFile( final TreeParent dsNode,
			final DataSourceType dataSourceType )
	{
		DataSourceDetails dsDetails = dataSourceType.getDataSource( );
		TreeParent dsName = new TreeParent( dsDetails.getDataSourceName( ) );
		dsName.setDbURL( dsDetails.getURL( ) );
		dsName.setDbUserName( dsDetails.getUserName( ) );
		dsName.setDbPassword( dsDetails.getPassword( ) );
		dsName.setType( DatabaseViewer.DATASOURCE_s );
		dsNode.addChild( dsName );
		List tableList = dataSourceType.getTable( );
		openTableList( dsName, tableList );
		new SolutionAdapterView( )
				.addDataSourceToSA( dsName.getName( ), dsName );
	}

	/**
	 * This method is used to populate the table information in the tree from
	 * the xml file add those items under the newly created data source tree
	 * 
	 * @param dsName
	 *            This is of type TreeParent which will have the tables as
	 *            children
	 * @param listObj
	 *            This of type List which will have the list of TableList schema
	 *            objects
	 */
	private void openTableList( final TreeParent dsName, final List listObj )
	{
		for( Iterator iterList = listObj.iterator( ); iterList.hasNext( ); )
		{
			TableList tableList = ( TableList ) iterList.next( );
			TreeParent tableName = new TreeParent( tableList.getTableName( ) );
			tableName.setType( DatabaseViewer.TABLE_s );
			tableName.setDataSourceName( dsName.getName( ) );
			dsName.addChild( tableName );
			List fieldList = tableList.getFields( );
			openFieldList( tableName, fieldList );
		}
	}

	/**
	 * This method is used to populate the field information in the tree form
	 * the xml file add those items under the newly created table tree
	 * 
	 * @param tableName
	 *            This is of tyoe Treeparent which will have the fields as
	 *            children
	 * @param listObj
	 *            This of type List which will have the list of FieldList schema
	 *            objects
	 */
	private void openFieldList( final TreeParent tableName, final List listObj )
	{
		for( Iterator iterList = listObj.iterator( ); iterList.hasNext( ); )
		{
			FieldList fieldList = ( FieldList ) iterList.next( );
			TreeParent fieldName = new TreeParent( fieldList.getFieldName( ) );
			fieldName.setType( DatabaseViewer.FIELD_s );
			fieldName.setDataType( fieldList.getFieldType( ) );
			fieldName.setDataSourceName( tableName.getDataSourceName( ) );
			fieldName.setTableName( tableName.getName( ) );
			if( null != fieldList.getDSDOMap( ) )
			{
				TreeParent mapField = new TreeParent( fieldList.getDSDOMap( ) );
				mapField.setType( DatabaseViewer.MAPPING_s );
				fieldName.addChild( mapField );
			}
			tableName.addChild( fieldName );
		}
	}
}
