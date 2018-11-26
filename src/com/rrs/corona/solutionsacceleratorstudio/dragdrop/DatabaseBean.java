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
 * $Id: DatabaseBean.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/dragdrop/DatabaseBean.java,v $
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

package com.rrs.corona.solutionsacceleratorstudio.dragdrop;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is a bean class for transferring the datasource,tables and fields from
 * database viewer to dataobject viewer
 * 
 * @author Maharajan
 */

public class DatabaseBean
{
	/**
	 * This ArrayList will hold all the fields
	 */
	private ArrayList	fieldMap		= null;
	/**
	 * This HashMap will hold all field types
	 */
	private HashMap		fieldType		= null;
	/**
	 * This string will hold the field name
	 */
	private String		fieldName		= null;
	/**
	 * This boolean will hold wheather the field is primarykey or not
	 */
	private boolean		primaryKey		= false;
	/**
	 * This string will hold the table name
	 */
	private String		tableName		= null;
	/**
	 * This string will hold the type of the data to be transfer
	 */
	private String		typeofData		= null;
	/**
	 * This ArrayList contains all the tables in the data source
	 */
	private ArrayList	allTables		= null;
	/**
	 * This string will hold group name
	 */
	private String		groupName		= null;
	/**
	 * This string hold mapping type either atomic or field
	 */
	private String		mappingType		= null;
	/**
	 * This string will hold the data source name
	 */
	private String		dataSourceName	= null;
	/**
	 * This string will hold the project name
	 */
	private String		projectName	= null;
	/**
	 * This Object will hold the AtomicMetricBean object
	 */
	private Object		atomicBean		= null;

	/**
	 * This method is used to return the AtomicMetricBean as an Object
	 * 
	 * @return Object
	 */
	public Object getAtomicBean( )
	{
		return atomicBean;
	}

	/**
	 * This method is used to set the AtomicMetrciBean Object
	 * 
	 * @param atomicBean
	 *            Object of the bean
	 */
	public void setAtomicBean( Object atomicBean )
	{
		this.atomicBean = atomicBean;
	}

	/**
	 * This method is used to get the ArrayList of fields
	 * 
	 * @return
	 */
	public ArrayList getFieldMap( )
	{
		return fieldMap;
	}

	/**
	 * This method is used to set the ArrayList of fields
	 * 
	 * @param fieldMap
	 */
	public void setFieldMap( ArrayList fieldMap )
	{
		this.fieldMap = fieldMap;
	}

	/**
	 * This method is used to get the table name
	 * 
	 * @return This string will hold the table name
	 */
	public String getTableName( )
	{

		return tableName;
	}

	/**
	 * This method is used to set the table name
	 * 
	 * @param tableName
	 *            This string will hold the table name
	 */
	public void setTableName( String tableName )
	{
		this.tableName = tableName;
	}

	/**
	 * This HashMap will hold all the field type
	 * 
	 * @return This will contains HashMap object
	 */
	public HashMap getFieldType( )
	{
		return fieldType;
	}

	/**
	 * This method is used to set the field type in the HashMap
	 * 
	 * @param fieldType
	 *            This will hold the object of HashMap which contains all the
	 *            field types
	 */
	public void setFieldType( HashMap fieldType )
	{
		this.fieldType = fieldType;
	}

	/**
	 * This method is used to get the field name
	 * 
	 * @return This will hold the field name
	 */
	public String getFieldName( )
	{
		return fieldName;
	}

	/**
	 * This method is used to set the field name
	 * 
	 * @param fieldName
	 *            This string will hold the string name
	 */
	public void setFieldName( String fieldName )
	{
		this.fieldName = fieldName;
	}

	/**
	 * This method is used to get the type of data transfer like table,
	 * datasource or field etc.,
	 * 
	 * @return
	 */
	public String getTypeofData( )
	{
		return typeofData;
	}

	/**
	 * This method is used to set the type of the data transfer like table,
	 * datasource or field etc.,
	 * 
	 * @param typeofData
	 */
	public void setTypeofData( String typeofData )
	{
		this.typeofData = typeofData;
	}

	/**
	 * This method is used to get all the table list
	 * 
	 * @return This will hold the ArrayList of tables
	 */
	public ArrayList getAllTables( )
	{
		return allTables;
	}

	/**
	 * This method is used to set all the tables in an ArrayList
	 * 
	 * @param allTables
	 *            This will hold the ArrayList of table names
	 */
	public void setAllTables( ArrayList allTables )
	{
		this.allTables = allTables;
	}

	/**
	 * This method is used to get the group name
	 * 
	 * @return This will hold the name of the group
	 */
	public String getGroupName( )
	{
		return groupName;
	}

	/**
	 * This method is used to set the group name
	 * 
	 * @param groupName
	 *            This string will hold the name of the group
	 */
	public void setGroupName( String groupName )
	{
		this.groupName = groupName;
	}

	/**
	 * This method is used to get the mapping type. It would contain either
	 * "atomic" or "field" for mapping purpose
	 * 
	 * @return This string will hold the type of mapping
	 */
	public String getMappingType( )
	{
		return mappingType;
	}

	/**
	 * This method is sued to set the mapping type. It would contain either
	 * "atomic" or "field" for mapping purpose
	 * 
	 * @param mappingType
	 *            This string will hold the mapping type
	 */
	public void setMappingType( String mappingType )
	{
		this.mappingType = mappingType;
	}

	/**
	 * This method is used to get the data source name
	 * 
	 * @return This string will hold the data source name
	 */
	public String getDataSourceName( )
	{
		return dataSourceName;
	}

	/**
	 * This method is used to set the data source name
	 */
	public void setDataSourceName( String dataSourceName )
	{
		this.dataSourceName = dataSourceName;
	}
	
	/**
	 * This method is used to get the project name
	 * 
	 * @return This string will hold the project name
	 */
	public String getProjectName( )
	{
		return projectName;
	}

	/**
	 * This method is used to set the project name
	 */
	public void setProjectName( String projectName )
	{
		this.projectName = projectName;
	}

	/**
	 * @return Returns the primaryKey.
	 */
	public boolean isPrimaryKey( )
	{
		return primaryKey;
	}

	/**
	 * @param primaryKey The primaryKey to set.
	 */
	public void setPrimaryKey( boolean primaryKey )
	{
		this.primaryKey = primaryKey;
	}
}
