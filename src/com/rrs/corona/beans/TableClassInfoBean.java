
/******************************************************************************
 * @rrs_start_copyright
 *
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved.
 * This software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of
 * the license agreement you entered into with Red Rabbit Software.
 © 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights reserved.
 Red Rabbit Software - Development Program 5 of 15
 *$Id: TableClassInfoBean.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/beans/TableClassInfoBean.java,v $
 * @rrs_end_copyright
 ******************************************************************************/
/******************************************************************************
 * @rrs_start_disclaimer
 *
 * The contents of this file are subject to the Red Rabbit Software Inc. Corona License
 * ("License"); You may not use this file except in compliance with the License.
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE RED RABBIT SOFTWARE INC. OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * @rrs_end_disclaimer
 ******************************************************************************/
package com.rrs.corona.beans;

public class TableClassInfoBean
{
	String clsName;
	String clsFieldName;
	String clsFieldType;
	String tableName;
	String tableFieldName;
	String primaryKey;
	/**
	 * @return Returns the primaryKey.
	 */
	public String getPrimaryKey( )
	{
		return primaryKey;
	}
	/**
	 * @param primaryKey The primaryKey to set.
	 */
	public void setPrimaryKey( String primaryKey )
	{
		this.primaryKey = primaryKey;
	}
	/**
	 * @return Returns the clsFieldName.
	 */
	public String getClsFieldName( )
	{
		return clsFieldName;
	}
	/**
	 * @param clsFieldName The clsFieldName to set.
	 */
	public void setClsFieldName( String clsFieldName )
	{
		this.clsFieldName = clsFieldName;
	}
	/**
	 * @return Returns the clsFieldType.
	 */
	public String getClsFieldType( )
	{
		return clsFieldType;
	}
	/**
	 * @param clsFieldType The clsFieldType to set.
	 */
	public void setClsFieldType( String clsFieldType )
	{
		this.clsFieldType = clsFieldType;
	}
	/**
	 * @return Returns the clsName.
	 */
	public String getClsName( )
	{
		return clsName;
	}
	/**
	 * @param clsName The clsName to set.
	 */
	public void setClsName( String clsName )
	{
		this.clsName = clsName;
	}
	/**
	 * @return Returns the tableFieldName.
	 */
	public String getTableFieldName( )
	{
		return tableFieldName;
	}
	/**
	 * @param tableFieldName The tableFieldName to set.
	 */
	public void setTableFieldName( String tableFieldName )
	{
		this.tableFieldName = tableFieldName;
	}
	/**
	 * @return Returns the tableName.
	 */
	public String getTableName( )
	{
		return tableName;
	}
	/**
	 * @param tableName The tableName to set.
	 */
	public void setTableName( String tableName )
	{
		this.tableName = tableName;
	}
	

}
