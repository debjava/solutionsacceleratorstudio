package com.rrs.corona.solutionsacceleratorstudio.dataobject;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import com.rrs.corona.solutionsacceleratorstudio.AllTest;
import com.rrs.corona.solutionsacceleratorstudio.dragdrop.DatabaseBean;
import com.rrs.corona.solutionsacceleratorstudio.project.AtomicMetricBean;

public class TestDropIntoTree extends TestCase 
{
	DropIntoTree intoTree;
	private static String fileID = "AB";
	protected void setUp() throws Exception
	{
		super.setUp();
		intoTree = new DropIntoTree();
	}

	protected void tearDown() throws Exception 
	{
		super.tearDown();
	}
	public void testdropAllTableInTree()
	{
		try{
			DataObjectView.invisibleRoot = new TreeParent("parent");
			TreeParent parent = new TreeParent("root");
			DataObjectView.invisibleRoot.addChild(parent);
			DatabaseBean beanObj = new DatabaseBean();
			beanObj.setGroupName("group");
			ArrayList list = new ArrayList();
			beanObj.setAllTables(list);
			assertTrue(intoTree.dropAllTableInTree(parent,beanObj));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"001"+AllTest.PASS);
		}catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"001"+AllTest.FAIL);
		}
	}
	public void testcheckForDuplicateField()
	{
		try{
			TreeParent parent = new TreeParent("root");
			assertTrue(intoTree.checkForDuplicateField(parent,"","",""));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"002"+AllTest.PASS);
		}catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"002"+AllTest.FAIL);
		}
	}
	public void testdropAtomicIntoTree()
	{
		
			DataObjectView.invisibleRoot = new TreeParent("parent");
			TreeParent parent = new TreeParent("root");
			DataObjectView.invisibleRoot.addChild(parent);
			TreeParent clazz  = new TreeParent("class");
			parent.addChild(clazz);
			TreeParent field = new TreeParent("field11");
			field.setMapping("");
			field.setDataSource("");
			field.setTableName("");
			field.setValue(DataObjectView.field_s);
			field.setType("int");
			clazz.addChild(field);
			AtomicMetricBean atomicBean = new AtomicMetricBean();
			atomicBean.setName("atomic11");
			atomicBean.setType("Integer");
			try{
			assertTrue(intoTree.dropAtomicIntoTree(field,atomicBean));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"003"+AllTest.PASS);
		}catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"003"+AllTest.FAIL);
		}
	}
	public void testcheckForNumberDataType()
	{
		try
		{
			assertTrue(intoTree.checkForNumberDataType("int"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"004"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"004"+AllTest.FAIL);
		}
		try
		{
			assertFalse(intoTree.checkForNumberDataType("error"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"005"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"005"+AllTest.FAIL);
		}
	}
	
	public void testcheckForAtomicType()
	{
		try
		{
			assertTrue(intoTree.checkForAtomicType("Integer","int"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"006"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"006"+AllTest.FAIL);
		}
		try
		{
			assertFalse(intoTree.checkForAtomicType("Integer","Date"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"007"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"007"+AllTest.FAIL);
		}
	}
	
	public void testcheckForDateDataType()
	{
		try
		{
			assertTrue(intoTree.checkForDateDataType("Date"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"008"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"008"+AllTest.FAIL);
		}
		try
		{
			assertFalse(intoTree.checkForDateDataType("util.Date"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"009"+AllTest.PASS);
		}
		catch(Exception e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"009"+AllTest.FAIL);
		}
	}
	
	public void testcheckForshort()
	{
		try
		{
			assertTrue(intoTree.checkForshort("short"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"010"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"010"+AllTest.FAIL);
		}
		try
		{
			assertTrue(intoTree.checkForshort("int"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"011"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"011"+AllTest.FAIL);
		}
		try
		{
			assertTrue(intoTree.checkForshort("float"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"012"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"012"+AllTest.FAIL);
		}
		try
		{
			assertTrue(intoTree.checkForshort("double"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"013"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"013"+AllTest.FAIL);
		}
		try
		{
			assertTrue(intoTree.checkForshort("long"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"014"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"014"+AllTest.FAIL);
		}
		try
		{
			assertFalse(intoTree.checkForshort("char"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"015"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"015"+AllTest.FAIL);
		}
	}
	
	public void testcheckForint()
	{
		try
		{
			assertTrue(intoTree.checkForint("int"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"016"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"016"+AllTest.FAIL);
		}
		try
		{
			assertTrue(intoTree.checkForint("float"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"017"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"017"+AllTest.FAIL);
		}
		try
		{
			assertTrue(intoTree.checkForint("long"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"018"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"018"+AllTest.FAIL);
		}
		try
		{
			assertTrue(intoTree.checkForint("double"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"019"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"019"+AllTest.FAIL);
		}
		try
		{
			assertFalse(intoTree.checkForint("short"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"020"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"020"+AllTest.FAIL);
		}
	}
	
	public void testcheckForfloat()
	{
		try
		{
			assertTrue(intoTree.checkForint("float"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"021"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"021"+AllTest.FAIL);
		}
		try
		{
			assertTrue(intoTree.checkForint("double"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"022"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"022"+AllTest.FAIL);
		}
		try
		{
			assertFalse(intoTree.checkForint("short"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"023"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"023"+AllTest.FAIL);
		}
	}
	
	public void testcheckForlong()
	{
		try
		{
			assertTrue(intoTree.checkForint("long"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"024"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"024"+AllTest.FAIL);
		}
		try
		{
			assertFalse(intoTree.checkForint("short"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"025"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"025"+AllTest.FAIL);
		}
	}
	
	public void testcheckFordouble()
	{
		try
		{
			assertTrue(intoTree.checkFordouble("double"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"026"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"026"+AllTest.FAIL);
		}
		try
		{
			assertFalse(intoTree.checkFordouble("short"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"027"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"027"+AllTest.FAIL);
		}
	}
	
	public void testcheckFordate()
	{
		try
		{
			assertTrue(intoTree.checkFordate("java.util.Date"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"028"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"028"+AllTest.FAIL);
		}
		try
		{
			assertFalse(intoTree.checkFordate("short"));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"029"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"029"+AllTest.FAIL);
		}
	}
	
	public void testdropTableAndFieldInTree()
	{
		TreeParent superParent = new TreeParent("group");
		DatabaseBean dataBean = new DatabaseBean();
		dataBean.setTableName("table");
		dataBean.setFieldMap(new ArrayList());
		dataBean.setFieldType(new HashMap());
		try
		{
			assertTrue(intoTree.dropTableAndFieldInTree(superParent,dataBean,new ArrayList(),new HashMap()));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"030"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"030"+AllTest.FAIL);
		}
	}
	
	public void testdropFieldInFieldTree()
	{
		TreeParent superParent = new TreeParent("group");
		superParent.setType("int");
		superParent.setMapping("");
		DatabaseBean dataBean = new DatabaseBean();
		dataBean.setTableName("table");
		dataBean.setFieldName("field ");
		ArrayList list = new ArrayList();
		list.add("field11");
		HashMap map = new HashMap();
		map.put("field11","number");
		dataBean.setFieldMap(new ArrayList());
		dataBean.setFieldType(new HashMap());
		dataBean.setMappingType(DataObjectView.mapFieldType_s);
		dataBean.setDataSourceName("");
		dataBean.setFieldMap(list);
		dataBean.setFieldType(map);
		try
		{
			assertTrue(intoTree.dropFieldInFieldTree(superParent,dataBean,list,map));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"031"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"031"+AllTest.FAIL);
		}
		try
		{
			TreeParent superParent1 = new TreeParent("group");
			superParent1.setType("int");
			superParent1.setMapping(DataObjectView.mapFieldType_s);
			assertFalse(intoTree.dropFieldInFieldTree(superParent1,dataBean,list,map));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"032"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"032"+AllTest.FAIL);
		}
	}
	public void testdropFieldInTree()
	{
		TreeParent superParent = new TreeParent("group");
		superParent.setType("int");
		superParent.setMapping("");
		DatabaseBean dataBean = new DatabaseBean();
		dataBean.setFieldName("field ");
		ArrayList list = new ArrayList();
		list.add("field11");
		HashMap map = new HashMap();
		map.put("field11","number");
		dataBean.setFieldMap(new ArrayList());
		dataBean.setFieldType(new HashMap());
		dataBean.setMappingType(DataObjectView.mapFieldType_s);
		dataBean.setDataSourceName("");
		dataBean.setFieldMap(list);
		dataBean.setFieldType(map);
		try
		{
			assertTrue(intoTree.dropFieldInTree(superParent,dataBean,list,map));
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"033"+AllTest.PASS);
		}
		catch(AssertionFailedError e)
		{
			System.out.println(AllTest.JUNIT_SAS_HEADER+TestDropIntoTree.fileID+"033"+AllTest.FAIL);
		}
	}
}
