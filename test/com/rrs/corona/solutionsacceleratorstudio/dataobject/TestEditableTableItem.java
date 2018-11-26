package com.rrs.corona.solutionsacceleratorstudio.dataobject;

import junit.framework.TestCase;
import com.rrs.corona.solutionsacceleratorstudio.AllTest;
public class TestEditableTableItem extends TestCase 
{
	private static String fileID = "AC";
	public EditableTableItem editTable = null;
	String field = "Sample";
	String atomic = "Atomic";
	Integer IINteger = new Integer(1);
	Object obj = IINteger;
	
	protected void setUp() throws Exception 
	{
		super.setUp();
		editTable = new EditableTableItem(field,IINteger,atomic,obj);
	}

	protected void tearDown() throws Exception 
	{
		super.tearDown();
	}
	
	public void testFieldName()
	{
		try
		{
			assertEquals(field,editTable.fieldName);
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestEditableTableItem.fileID+"001"+AllTest.PASS);
		}
		catch(Exception e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestEditableTableItem.fileID+"001"+AllTest.FAIL);
		}
	}
	public void testFieldType()
	{
		try
		{
			assertEquals(IINteger,editTable.fieldType);
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestEditableTableItem.fileID+"002"+AllTest.PASS);
		}
		catch(Exception e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestEditableTableItem.fileID+"002"+AllTest.FAIL);
		}
	}
	public void testAtomic()
	{
		try
		{
			assertEquals(atomic,editTable.atomicMetricProperty);
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestEditableTableItem.fileID+"003"+AllTest.PASS);
		}
		catch(Exception e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestEditableTableItem.fileID+"003"+AllTest.FAIL);
		}
	}
	public void testObject()
	{
		try
		{
			assertEquals(obj,editTable.treeItem);
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestEditableTableItem.fileID+"004"+AllTest.PASS);
		}
		catch(Exception e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestEditableTableItem.fileID+"004"+AllTest.FAIL);
		}
	}
}
