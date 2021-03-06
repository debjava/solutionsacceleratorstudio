/******************************************************************************
 * @rrs_start_copyright
 *
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved.
 * This software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of
 * the license agreement you entered into with Red Rabbit Software.
 *
 * 
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
package com.rrs.corona.solutionsacceleratorstudio.plugin;
/**
 * 
 * @author Debadatta Mishra
 *
 */
public class DsFieldInfo 
{
	/**
	 * DataSource name
	 */
	String dataSourceName;
	/**
	 * URL for the dataSource
	 */
	String dataSourceURL;
	/**
	 * User name for the dataSource
	 */
	String dataSourceUserName;
	/**
	 * password for the dataSource
	 */
	String dataSourcePassword;
	/**
	 * Table Field Name
	 */
	String DsFieldName;// Field name
	/**
	 * Field Type
	 */
	String DsFieldType;// Field type
	/**
	 * A field has primary or not
	 */
	boolean isPrimaryKey;
	/**
	 * @return Returns the dsFieldName.
	 */
	public String getDsFieldName() 
	{
		return DsFieldName;
	}
	/**
	 * @param dsFieldName The dsFieldName to set.
	 */
	public void setDsFieldName(String dsFieldName) 
	{
		DsFieldName = dsFieldName;
	}
	/**
	 * @return Returns the dsFieldType.
	 */
	public String getDsFieldType() 
	{
		return DsFieldType;
	}
	/**
	 * @param dsFieldType The dsFieldType to set.
	 */
	public void setDsFieldType(String dsFieldType) 
	{
		DsFieldType = dsFieldType;
	}
	/**
	 * @return Returns the dataSourceName.
	 */
	public String getDataSourceName( )
	{
		return dataSourceName;
	}
	/**
	 * @param dataSourceName The dataSourceName to set.
	 */
	public void setDataSourceName( String dataSourceName )
	{
		this.dataSourceName = dataSourceName;
	}
	/**
	 * @return Returns the dataSourcePassword.
	 */
	public String getDataSourcePassword( )
	{
		return dataSourcePassword;
	}
	/**
	 * @param dataSourcePassword The dataSourcePassword to set.
	 */
	public void setDataSourcePassword( String dataSourcePassword )
	{
		this.dataSourcePassword = dataSourcePassword;
	}
	/**
	 * @return Returns the dataSourceURL.
	 */
	public String getDataSourceURL( )
	{
		return dataSourceURL;
	}
	/**
	 * @param dataSourceURL The dataSourceURL to set.
	 */
	public void setDataSourceURL( String dataSourceURL )
	{
		this.dataSourceURL = dataSourceURL;
	}
	/**
	 * @return Returns the dataSourceUserName.
	 */
	public String getDataSourceUserName( )
	{
		return dataSourceUserName;
	}
	/**
	 * @param dataSourceUserName The dataSourceUserName to set.
	 */
	public void setDataSourceUserName( String dataSourceUserName )
	{
		this.dataSourceUserName = dataSourceUserName;
	}
	/**
	 * @return Returns the isPrimaryKey.
	 */
	public boolean isPrimaryKey( )
	{
		return isPrimaryKey;
	}
	/**
	 * @param isPrimaryKey The isPrimaryKey to set.
	 */
	public void setPrimaryKey( boolean isPrimaryKey )
	{
		this.isPrimaryKey = isPrimaryKey;
	}
	
}
