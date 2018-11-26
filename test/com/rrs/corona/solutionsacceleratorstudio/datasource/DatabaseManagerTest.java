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
package com.rrs.corona.solutionsacceleratorstudio.datasource;
/**
 * @author Debadatta Mishra
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import junit.framework.TestCase;

public class DatabaseManagerTest extends TestCase 
{
	DatabaseManager dbmanagerTestCase = null;
	DbmsInfo dbInfoTest = null;
	Connection connection = null;
	
	public DatabaseManagerTest() 
	{
		
	}
	
	protected void setUp() throws Exception 
	{
		super.setUp();
		dbmanagerTestCase = new DatabaseManager();
		dbInfoTest = new DbmsInfo();
		dbInfoTest.setDataSourceName("corona");
		dbInfoTest.setDbUrl("jdbc:oracle:thin:@10.0.0.101:1521:corona");
		dbInfoTest.setDbUser("ekmuser");
		dbInfoTest.setDbPassword("ekmuser");
	}
	
	protected void tearDown() throws Exception 
	{
		super.tearDown();
	}
	
	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.datasource.DatabaseManager.getDatabaseConnection(DbmsInfo)'
	 */
	public void testGetDatabaseConnectionDbmsInfo() 
	{
		try
		{
			connection = dbmanagerTestCase.getDatabaseConnection(dbInfoTest);
			assertTrue(connection != null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(connection != null)
			{
				try
				{
					dbmanagerTestCase.close(connection);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.datasource.DatabaseManager.checkDatabaseConnection(DbmsInfo)'
	 */
	public void testCheckDatabaseConnectionDbmsInfo() 
	{
		try
		{
			assertEquals(dbmanagerTestCase.checkDatabaseConnection(dbInfoTest),true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.datasource.DatabaseManager.getAllTableNames(Connection)'
	 */
	public void testGetAllTableNames() 
	{
		try
		{
			connection = dbmanagerTestCase.getDatabaseConnection(dbInfoTest);
			ArrayList tableNamesList = dbmanagerTestCase.getAllTableNames(connection);
			assertTrue(!tableNamesList.isEmpty());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(connection != null)
			{
				try
				{
					dbmanagerTestCase.close(connection);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.datasource.DatabaseManager.getEachTableInfo(String)'
	 */
	public void testGetEachTableInfoString() 
	{
		try
		{
			connection = dbmanagerTestCase.getDatabaseConnection(dbInfoTest);
			ArrayList tableNamesList = dbmanagerTestCase.getAllTableNames(connection);
			ArrayList fieldInfoList = dbmanagerTestCase.getEachTableInfo(connection,(String)tableNamesList.get(1));
			assertTrue(!fieldInfoList.isEmpty());
		}
		catch(Exception e)
		{
			
		}
	}
	
}
