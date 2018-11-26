
package com.rrs.corona.solutionsacceleratorstudio.dragdrop;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;

import com.rrs.corona.solutionsacceleratorstudio.AllTest;
import com.rrs.corona.solutionsacceleratorstudio.dragdrop.DatabaseBean;
import com.rrs.corona.solutionsacceleratorstudio.project.AtomicMetricBean;

public class TestDatabaseBean extends TestCase
{
	DatabaseBean			dataBean;
	AtomicMetricBean		atomicBean;
	private static String	fileID	= "AA";

	protected void setUp( ) throws Exception
	{
		super.setUp( );
		dataBean = new DatabaseBean( );
		atomicBean = new AtomicMetricBean( );
	}

	protected void tearDown( ) throws Exception
	{
		super.tearDown( );
	}

	public void testSetAtomicMetricBean( )
	{
		try
		{
			dataBean.setAtomicBean( atomicBean );
			assertNotNull( dataBean.getAtomicBean( ) );
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "001" + AllTest.PASS );
		}
		catch( Exception e )
		{
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "001" + AllTest.FAIL );
		}
	}

	public void testSetTableName( )
	{
		String table = "sampleTable";
		try
		{
			dataBean.setTableName( table );
			assertEquals( table, dataBean.getTableName( ) );
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "002" + AllTest.PASS );
		}
		catch( Exception e )
		{
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "002" + AllTest.FAIL );
		}
	}

	public void testSetFieldName( )
	{
		String field = "sampleField";
		try
		{
			dataBean.setFieldName( field );
			assertEquals( field, dataBean.getFieldName( ) );
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "003" + AllTest.PASS );
		}
		catch( Exception e )
		{
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "003" + AllTest.FAIL );
		}
	}

	public void testSetGroupName( )
	{
		String group = "sampleGroup";
		try
		{
			dataBean.setGroupName( group );
			assertEquals( group, dataBean.getGroupName( ) );
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "004" + AllTest.PASS );
		}
		catch( Exception e )
		{
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "004" + AllTest.FAIL );
		}
	}

	public void testSetMapping( )
	{
		String mapping = "sampleMapping";
		try
		{
			dataBean.setMappingType( mapping );
			assertEquals( mapping, dataBean.getMappingType( ) );
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "005" + AllTest.PASS );
		}
		catch( Exception e )
		{
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "005" + AllTest.FAIL );
		}
	}

	public void testSetTypeofData( )
	{
		String type = "sampleType";
		try
		{
			dataBean.setTypeofData( type );
			assertEquals( type, dataBean.getTypeofData( ) );
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "006" + AllTest.PASS );
		}
		catch( Exception e )
		{
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "006" + AllTest.FAIL );
		}
	}

	public void testSetAllTable( )
	{
		ArrayList allTab = new ArrayList( );
		try
		{
			dataBean.setAllTables( allTab );
			assertEquals( allTab, dataBean.getAllTables( ) );
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "007" + AllTest.PASS );
		}
		catch( Exception e )
		{
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "007" + AllTest.FAIL );
		}
	}

	public void testSetFieldType( )
	{
		HashMap allField = new HashMap( );
		try
		{
			dataBean.setFieldType( allField );
			assertEquals( allField, dataBean.getFieldType( ) );
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "008" + AllTest.PASS );
		}
		catch( Exception e )
		{
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "008" + AllTest.FAIL );
		}
	}

	public void testSetFieldMap( )
	{
		ArrayList allField = new ArrayList( );
		try
		{
			dataBean.setFieldMap( allField );
			assertEquals( allField, dataBean.getFieldMap( ) );
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "009" + AllTest.PASS );
		}
		catch( Exception e )
		{
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "009" + AllTest.FAIL );
		}
	}

	public void testSetdataSource( )
	{
		String datasource = "sampleDS";
		try
		{
			dataBean.setDataSourceName( datasource );
			assertEquals( datasource, dataBean.getDataSourceName( ) );
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "010" + AllTest.PASS );
		}
		catch( Exception e )
		{
			System.out.println( AllTest.JUNIT_SAS_HEADER
					+ TestDatabaseBean.fileID + "010" + AllTest.FAIL );
		}
	}
}
