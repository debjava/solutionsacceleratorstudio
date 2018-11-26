package com.rrs.corona.solutionsacceleratorstudio.dataobject;

import com.rrs.corona.solutionsacceleratorstudio.project.AtomicMetricBean;

import junit.framework.TestCase;

public class TestDataObjectOpen extends TestCase 
{
	AtomicMetricBean atomicBean = null;
	protected void setUp() throws Exception 
	{
		super.setUp();
		atomicBean = new AtomicMetricBean();
		atomicBean.setName("sample");
		atomicBean.setCategory("category");
		atomicBean.setCorelationID("corr");
		atomicBean.setData("123");
		atomicBean.setTimeStamp("time");
		atomicBean.setDescription("desc");
		atomicBean.setGuid("guid");
		atomicBean.setMetricID("metric");
		atomicBean.setType("Integer");
		atomicBean.setSessionID("sess");
		atomicBean.setTransactionID("trans");
		atomicBean.setProjectName("proje");
	}

	protected void tearDown() throws Exception 
	{
		super.tearDown();
	}

	public void saveDataObjectToFile()
	{
		
	}
}
