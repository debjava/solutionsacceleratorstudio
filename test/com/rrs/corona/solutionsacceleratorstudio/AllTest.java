
package com.rrs.corona.solutionsacceleratorstudio;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.rrs.corona.common.CommonConstants;
import com.rrs.corona.solutionsacceleratorstudio.dataobject.TestDropIntoTree;
import com.rrs.corona.solutionsacceleratorstudio.dataobject.TestEditableTableItem;
import com.rrs.corona.solutionsacceleratorstudio.dragdrop.TestDatabaseBean;

public class AllTest extends TestCase
{
	public static String	JUNIT_SAS_HEADER	= "Testcase No: SAS_";
	public static String	PASS				= ": pass";
	public static String	FAIL				= ": fail *******";
	public static long		testcount			= 0;

	/**
	 * content: This method is used to suite all the test class
	 */
	public static Test suite( )
	{
		CommonConstants cConstants = new CommonConstants( );
		cConstants.setJunitFlag( true );

		TestSuite suite = new TestSuite(
											"Test for com.rrs.corona.solutionsacceleratorstudio" );
		// $JUnit-BEGIN$
		suite.addTestSuite( TestDatabaseBean.class );
		suite.addTestSuite( TestDropIntoTree.class );
		suite.addTestSuite( TestEditableTableItem.class );
	
		// $JUnit-END$
		return suite;
	}
}
