package com.rrs.corona.solutionsacceleratorstudio.plugin;

import java.util.logging.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import junit.framework.TestCase;

public class DeleteDataSourceDialogTest extends TestCase 
{
	Logger logger = Logger.getLogger("CreateDataSourceDialogTest.class");
	//private static String testcount="sas_CDSD000";
	Shell shell;
	static DeleteDataSourceDialog deleteDsDialog = null;
	Composite area =null;	
	Composite firstGroup = null; // 1st group	
	GridData gdData1 = null;// It should be initialized first
	GridData gridData = null;// to show in a grid	
	Display display ;
	
	public DeleteDataSourceDialogTest() 
	{
		
	}
	
	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.plugin.DeleteDataSourceDialog.createURLText(Composite, GridData)'
	 */
	
	public void testCreateURLText() 
	{
		display = new Display();
		shell = new Shell(display);
		deleteDsDialog = new DeleteDataSourceDialog(shell);
		area = new Composite(shell, SWT.NULL);	
		firstGroup = new Composite(area, SWT.NONE); // 1st group	
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be initialized first
		deleteDsDialog.createURLText(firstGroup,gdData1);
		assertEquals(deleteDsDialog.getUrlText().getParent().getParent(),firstGroup.getParent());
	}
	
	public void testCheckURLTextValue()
	{
		assertEquals(deleteDsDialog.getUrlText().getText(),"");
		deleteDsDialog.getUrlText().setText("XXXXXX");
		assertEquals(deleteDsDialog.getUrlText().getText(),"XXXXXX");
	}
	
	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.plugin.DeleteDataSourceDialog.createDataSourceCombo(Composite, GridData)'
	 */
	
	public void testcreateDataSourceCombo() 
	{
		area = new Composite(deleteDsDialog.getUrlText().getShell(), SWT.NULL);	
		firstGroup = new Composite(area, SWT.NONE); // 1st group	
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be initialized first
		gridData = new GridData();
		deleteDsDialog.createDataSourceCombo(firstGroup,gdData1);
		assertEquals(deleteDsDialog.getDsName().getParent().getParent(),firstGroup.getParent());
	}
	/**
	 * To check the initial and the first value of the DataSource Combo box
	 *
	 */
	public void testCheckDataSourceComboValue()
	{
		assertEquals(deleteDsDialog.getDsName().getText(),""); // Initial test for Combo
		assertEquals(deleteDsDialog.getDsName().getItem(0),"ChicagoServer");//First value of the ComboBox
	}
	
	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.plugin.DeleteDataSourceDialog.createUserText(Composite, GridData)'
	 */
	
	public void testCreateUserText() 
	{
		area = new Composite(deleteDsDialog.getUrlText().getShell(), SWT.NULL);	
		firstGroup = new Composite(area, SWT.NONE); // 1st group	
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be initialized first
		gridData = new GridData();
		deleteDsDialog.createUserText(firstGroup,gdData1);
		assertEquals(deleteDsDialog.getUserText().getParent().getParent(),firstGroup.getParent());
	}
	
	public void testCheckUserTextValue()
	{
		assertEquals(deleteDsDialog.getUserText().getText(),"");
		deleteDsDialog.getUserText().setText("AAAAA");
		assertEquals(deleteDsDialog.getUserText().getText(),"AAAAA");
	}
	
	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.plugin.DeleteDataSourceDialog.createPasswordText(Composite, GridData)'
	 */
	
	public void testCreatePasswordText() 
	{
		area = new Composite(deleteDsDialog.getUrlText().getShell(), SWT.NULL);	
		firstGroup = new Composite(area, SWT.NONE); // 1st group	
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be initialized first
		gridData = new GridData();
		deleteDsDialog.createPasswordText(firstGroup,gdData1);
		assertEquals(deleteDsDialog.getPasswordText().getParent().getParent(),firstGroup.getParent());
	}
	
	public void testCheckPasswordTextValue()
	{
		assertEquals(deleteDsDialog.getPasswordText().getText(),"");
		deleteDsDialog.getPasswordText().setText("PPPPPPPP");
		assertEquals(deleteDsDialog.getPasswordText().getText(),"PPPPPPPP");
	}
/*	
	public void testOkButton()
	{
		deleteDsDialog.createButtonsForButtonBar(firstGroup);
		logger.info("OK Button :::: "+deleteDsDialog.getOkButton().getEnabled());
	}*/
	
}
