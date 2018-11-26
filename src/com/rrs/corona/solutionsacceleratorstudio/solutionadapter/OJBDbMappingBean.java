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
 *$Id: OJBDbMappingBean.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/OJBDbMappingBean.java,v $
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
package com.rrs.corona.solutionsacceleratorstudio.solutionadapter;
/**
 * 
 * @author Debadatta Mishra
 *
 */
public class OJBDbMappingBean
{
	/**
	 * Jcd_alias for database configuration in the OJB Mapping file
	 */
	String jcd_alias = "\""+"default"+"\"";
	/**
	 * default_connection for database configuration in the OJB Mapping file
	 */
	String default_connection = "\""+"true"+"\"";
	/**
	 * platform for database configuration in the OJB Mapping file
	 */
	String platform = "\""+"oracle9i"+"\"";
	/**
	 * jdbc_level for database configuration in the OJB Mapping file
	 */
	String jdbc_level = "\"3.0\"";
	/**
	 * drabase driver for database configuration in the OJB Mapping file
	 */
	String driver = "\"oracle.jdbc.driver.OracleDriver\"" ;
	/**
	 * protocol for database configuration in the OJB Mapping file
	 */
	String protocol = null;
	/**
	 * subprotocol for database configuration in the OJB Mapping file
	 */
	String subProtocol = null;
	/**
	 * dbAlias for database configuration in the OJB Mapping file
	 */
	String dbAlias = null;
	/**
	 * database user name for database configuration in the OJB Mapping file
	 */
	String userName = null;
	/**
	 * database password for database configuration in the OJB Mapping file
	 */
	String password = null;
	/**
	 * batch_mode for database configuration in the OJB Mapping file
	 */
	String batch_mode = " \"false\" ";
	/**
	 * useAutoCommit for database configuration in the OJB Mapping file
	 */
	String useAutoCommit = " \"1\" ";
	/**
	 * ignoreAutoCommitException for database configuration in the OJB Mapping file
	 */
	String ignoreAutoCommitException = " \"false\" ";
	
	/**
	 * @return Batch_mode
	 */
	public String getBatch_mode() 
	{
		return batch_mode;
	}
	/**
	 * @param batch_mode of type String
	 */
	public void setBatch_mode(String batch_mode) 
	{
		this.batch_mode = batch_mode;
	}
	/**
	 * @return DbAlias
	 */
	public String getDbAlias() 
	{
		return dbAlias;
	}
	/**
	 * @param dbAlias of type String
	 */
	public void setDbAlias(String dbAlias) 
	{
		this.dbAlias = dbAlias;
	}
	/**
	 * @return DefaultConnection
	 */
	public String getDefault_connection() 
	{
		return default_connection;
	}
	public void setDefault_connection(String default_connection) 
	{
		this.default_connection = default_connection;
	}
	/**
	 * @return Driver
	 */
	public String getDriver() 
	{
		return driver;
	}
	/**
	 * @param driver
	 */
	public void setDriver(String driver) 
	{
		this.driver = driver;
	}
	/**
	 * @return IgnoreAutoCommitException
	 */
	public String getIgnoreAutoCommitException() 
	{
		return ignoreAutoCommitException;
	}
	/**
	 * @param ignoreAutoCommitException
	 */
	public void setIgnoreAutoCommitException(String ignoreAutoCommitException) 
	{
		this.ignoreAutoCommitException = ignoreAutoCommitException;
	}
	/**
	 * @return Jcd_alias
	 */
	public String getJcd_alias() 
	{
		return jcd_alias;
	}
	/**
	 * @param jcd_alias
	 */
	public void setJcd_alias(String jcd_alias) 
	{
		this.jcd_alias = jcd_alias;
	}
	/**
	 * @return Jdbc_level
	 */
	public String getJdbc_level() 
	{
		return jdbc_level;
	}
	/**
	 * @param jdbc_level
	 */
	public void setJdbc_level(String jdbc_level) 
	{
		this.jdbc_level = jdbc_level;
	}
	/**
	 * @return password
	 */
	public String getPassword() 
	{
		return password;
	}
	/**
	 * @param password
	 */
	public void setPassword(String password) 
	{
		this.password = password;
	}
	/**
	 * @return Platform
	 */
	public String getPlatform() 
	{
		return platform;
	}
	/**
	 * @param platform
	 */
	public void setPlatform(String platform) 
	{
		this.platform = platform;
	}
	/**
	 * @return Protocol
	 */
	public String getProtocol() 
	{
		return protocol;
	}
	/**
	 * @param protocol
	 */
	public void setProtocol(String protocol) 
	{
		this.protocol = protocol;
	}
	/**
	 * @return SubProtocol
	 */
	public String getSubProtocol() 
	{
		return subProtocol;
	}
	/**
	 * @param subProtocol
	 */
	public void setSubProtocol(String subProtocol) 
	{
		this.subProtocol = subProtocol;
	}
	/**
	 * @return UseAutoCommit
	 */
	public String getUseAutoCommit() 
	{
		return useAutoCommit;
	}
	/**
	 * @param useAutoCommit
	 */
	public void setUseAutoCommit(String useAutoCommit) 
	{
		this.useAutoCommit = useAutoCommit;
	}
	/**
	 * @return UserName
	 */
	public String getUserName() 
	{
		return userName;
	}
	/**
	 * @param userName
	 */
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}
	
}
