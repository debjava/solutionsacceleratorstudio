package com.rrs.corona.solutionsacceleratorstudio.plugin;

import java.util.logging.Logger;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import junit.framework.TestCase;

public class RenameDataSourceDialogTest extends TestCase 
{
	Logger logger = Logger.getLogger("CreateDataSourceDialogTest.class");
	//private static String testcount="sas_CDSD000";
	Shell shell;
	static RenameDataSourceDialog renameDsDialog = null;
	Composite area =null;	
	Composite firstGroup = null; // 1st group	
	GridData gdData1 = null;// It should be initialized first
	GridData gridData = null;// to show in a grid	
	Display display ;

	public RenameDataSourceDialogTest() 
	{
		
	}

	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.plugin.RenameDataSourceDialog.createDsText(Composite, GridData)'
	 */
	public void testCreateDsText() 
	{
		display = new Display();
		shell = new Shell(display);
		renameDsDialog = new RenameDataSourceDialog(shell);
		area = new Composite(shell, SWT.NULL);	
		firstGroup = new Composite(area, SWT.NONE); // 1st group	
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be initialized first
		renameDsDialog.createDsText(firstGroup,gdData1);
		assertEquals(renameDsDialog.getNewDsNameText().getParent().getParent(),firstGroup.getParent());
	}
	
	public void testCheckDsTextValue()
	{
		assertEquals(renameDsDialog.getNewDsNameText().getText(),"");
	}
	
	

	/*
	 * Test method for 'com.rrs.corona.solutionsacceleratorstudio.plugin.RenameDataSourceDialog.createPrevCombo(Composite, GridData)'
	 */
	/*public void testCreatePrevCombo() 
	{
		area = new Composite(renameDsDialog.getNewDsNameText().getShell(),SWT.NULL);
		firstGroup = new Composite(area,SWT.NONE);
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData = new GridData();
		
		//renameDsDialog.getPreviousDsName.setText(DatabaseViewer.renameDatasource);
		renameDsDialog.createPrevCombo(firstGroup,gdData1);
		
		//renameDsDialog.getPreviousDsName().setText(DatabaseViewer.renameDatasource);
		
		//assertEquals(renameDsDialog.getPreviousDsName().getParent().getParent(),firstGroup.getParent());
	}*/
	
	/*public void testCheckPrevDsComboValue()
	{
		//logger.info("PreviousDsComboValue :::::  "+renameDsDialog.getPreviousDsName().getText());
		try
		{
			assertEquals(renameDsDialog.getPreviousDsName().getText(),"");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}*/

}
