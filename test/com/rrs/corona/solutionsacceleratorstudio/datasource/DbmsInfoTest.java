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
import junit.framework.TestCase;

public class DbmsInfoTest extends TestCase
{
	DbmsInfo dbInfo = null;
	
	public DbmsInfoTest() 
	{
		
	}
	
	protected void setUp() throws Exception 
	{
		super.setUp();
		dbInfo = new DbmsInfo() ;
	}
	
	protected void tearDown() throws Exception 
	{
		super.tearDown();
	}
	
	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.datasource.DbmsInfo.getDbPassword()'
	 */
	public void testGetDbPassword() 
	{
		testSetDbPassword();
		assertEquals(dbInfo.getDbPassword().toString(),"xxxx");
	}
	
	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.datasource.DbmsInfo.setDbPassword(String)'
	 */
	public void testSetDbPassword() 
	{
		dbInfo.setDbPassword("xxxx");
	}
	
	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.datasource.DbmsInfo.getDbUrl()'
	 */
	public void testGetDbUrl() 
	{
		testSetDbUrl();
		assertEquals(dbInfo.getDbUrl(),"jdbc:oracle:thin");
	}
	
	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.datasource.DbmsInfo.setDbUrl(String)'
	 */
	public void testSetDbUrl() 
	{
		dbInfo.setDbUrl("jdbc:oracle:thin");
	}
	
	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.datasource.DbmsInfo.getDbUser()'
	 */
	public void testGetDbUser() 
	{
		testSetDbUser();
		assertEquals(dbInfo.getDbUser(),"Corona");
	}
	
	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.datasource.DbmsInfo.setDbUser(String)'
	 */
	public void testSetDbUser() 
	{
		dbInfo.setDbUser("Corona");
	}
	
	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.datasource.DbmsInfo.getDataSourceName()'
	 */
	public void testGetDataSourceName() 
	{
		testSetDataSourceName();
		assertEquals(dbInfo.getDataSourceName(),"CORONA");
	}
	
	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.datasource.DbmsInfo.setDataSourceName(String)'
	 */
	public void testSetDataSourceName() 
	{
		dbInfo.setDataSourceName("CORONA");
	}
	
}
