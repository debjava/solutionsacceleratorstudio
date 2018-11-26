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
 * @author Debadatta Mishra
 */
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class CreateDataSourceDialogTest extends TestCase 
{
	Logger logger = Logger.getLogger("CreateDataSourceDialogTest.class");
	//private static String testcount="sas_CDSD000";
	Shell shell;
	static CreateDataSourceDialog createDataSrc = null;
	Composite area =null;	
	Composite firstGroup = null; // 1st group	
	GridData gdData1 = null;// It should be initialized first
	GridData gridData = null; // to show in a grid	
	Display display ;
	
	public CreateDataSourceDialogTest()
	{
		
	}
	
	public void testcreatePortText()
	{
		display = new Display();
		shell = new Shell(display);
		createDataSrc = new CreateDataSourceDialog(shell);
		area = new Composite(shell, SWT.NULL);	
		firstGroup = new Composite(area, SWT.NONE); // 1st group	
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be initialized first
		//firstGroup.setLayout(new GridLayout(3, true)); // lay out
		gridData = new GridData(); // t
		createDataSrc.createPortText(firstGroup,gdData1);
		assertEquals(createDataSrc.portText.getText().toString(),"1521");//This hardcoding is for JUnit test case
	}
	
	public void testcreateDatabaseTypeCombo()
	{
		area = new Composite(createDataSrc.getPortText().getShell(), SWT.NULL);	
		firstGroup = new Composite(area, SWT.NONE); // 1st group	
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be initialized first
		gridData = new GridData(); 
		createDataSrc.createDatabaseTypeCombo(firstGroup,gdData1);
		//logger.info("_______________ ::: "+createDataSrc.getDatabaseType().getParent());
		assertEquals(createDataSrc.getDatabaseType().getParent().getParent(),firstGroup.getParent());
	}
	
	public void testCheckDatabaseTypeComboValue()
	{
		assertEquals(createDataSrc.getDatabaseType().getItem(0),"Oracle");
	}
	
	public void testcreateIPText()
	{
		area = new Composite(createDataSrc.getPortText().getShell(), SWT.NULL);	
		firstGroup = new Composite(area, SWT.NONE); // 1st group	
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be initialized first
		gridData = new GridData(); 
		createDataSrc.createIPText(firstGroup,gdData1);
		assertEquals(createDataSrc.getIPText().getParent().getParent(),firstGroup.getParent());
	}
	
	public void testCheckIPTextValue()
	{
		assertEquals(createDataSrc.getIPText().getText(),"");
		createDataSrc.getIPText().setText("TestVal");
		assertEquals(createDataSrc.getIPText().getText(),"TestVal");
	}
	
	public void testcreateDatabaseNameText()
	{
		area = new Composite(createDataSrc.getPortText().getShell(), SWT.NULL);	
		firstGroup = new Composite(area, SWT.NONE); // 1st group	
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be initialized first
		gridData = new GridData(); 
		createDataSrc.createDatabaseNameText(firstGroup,gdData1);
		assertEquals(createDataSrc.getDbNameText().getParent().getParent(),firstGroup.getParent());
	}
	
	public void testCheckDatabaseNameText()
	{
		assertEquals(createDataSrc.getDbNameText().getText(),"");
		createDataSrc.getDbNameText().setText("Test Value");
		assertEquals(createDataSrc.getDbNameText().getText(),"Test Value");
	}
	
	public void testcreateUserText()
	{
		area = new Composite(createDataSrc.getPortText().getShell(), SWT.NULL);	
		firstGroup = new Composite(area, SWT.NONE); // 1st group	
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be initialized first
		gridData = new GridData(); 
		createDataSrc.createUserText(firstGroup,gdData1);
		assertEquals(createDataSrc.getUserText().getParent().getParent(),firstGroup.getParent());
	}
	
	public void testCheckUserText()
	{
		assertEquals(createDataSrc.getUserText().getText(),"");
		createDataSrc.getUserText().setText("New Test Value");
		assertEquals(createDataSrc.getUserText().getText(),"New Test Value");
	}
	
	public void testcreatePasswordText()
	{
		area = new Composite(createDataSrc.getPortText().getShell(), SWT.NULL);	
		firstGroup = new Composite(area, SWT.NONE); // 1st group	
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be initialized first
		gridData = new GridData(); 
		createDataSrc.createPasswordText(firstGroup,gdData1);
		assertEquals(createDataSrc.getPasswordText().getParent().getParent(),firstGroup.getParent());
	}
	
	public void testCheckPasswordText()
	{
		assertEquals(createDataSrc.getPasswordText().getText(),"");
		createDataSrc.getPasswordText().setText("New Test Value");
		assertEquals(createDataSrc.getPasswordText().getText(),"New Test Value");
	}
	public void testcreateDriverCombo()
	{
		area = new Composite(createDataSrc.getPortText().getShell(), SWT.NULL);	
		firstGroup = new Composite(area, SWT.NONE); // 1st group	
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be initialized first
		gridData = new GridData(); // t
		createDataSrc.createDriverCombo(firstGroup,gdData1);
		assertEquals(createDataSrc.driverType.getParent().getParent(),firstGroup.getParent());
	}
	
	public void testcreateDataSourceText()
	{
		area = new Composite(createDataSrc.getPortText().getShell(), SWT.NULL);	
		firstGroup = new Composite(area, SWT.NONE); // 1st group	
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be initialized first
		gridData = new GridData(); // t
		createDataSrc.createDataSourceText(firstGroup,gdData1);
		assertEquals(createDataSrc.getDsName().getParent().getParent(),firstGroup.getParent());
	}
	
	
	public void testcheckConnection()
	{
		
	}

}

