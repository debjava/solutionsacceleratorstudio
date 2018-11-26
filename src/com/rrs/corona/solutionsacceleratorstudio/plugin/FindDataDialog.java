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
import java.util.ArrayList;
import java.util.logging.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class FindDataDialog extends TitleAreaDialog
{
	/**
	 * Push button for okButton
	 */
	transient private Button okButton = null;// for ok button
	/**
	 * Push button for Cancel button
	 */
	transient private Button cancelButton =  null;// for cancel button
	Text commentText;
	/**
	 * Boolean variable to check a certain condition
	 */
	boolean testFlag = false;
	/**
	 * Text field to enter the word to find
	 */
	transient private Text findText = null; // name of the text field to enter the word to find
	/**
	 * DsReader object to parse the xml file
	 */
	DsReader dataInfoReader = new DsReader();// object to read or parse the XML file
	Button addBtn ;
	/**
	 * Radio button for search down option
	 */
	Button searchDownButton = null;// search up button
	/**
	 * Radion button for sear up option
	 */
	Button searchUPButton = null;// search down button
	Button fieldButton = null;
	/**
	 * Check box for match case
	 */
	Button matchButton = null;// check box for match case button
	/**
	 * Check box for match whole word
	 */
	Button matchWholewordButton = null;// check box for match whole word
	Button searchUpButton = null;
	/**
	 * String containg the word to search
	 */
	String searchText = null;// String to search the word	
	boolean dataSourceFlag = false;
	boolean tableFlag = false;
	boolean fieldFlag = false;
	String testFindPath = null;
	/**
	 * ArrayList containg the list of found dataSource names
	 */
	ArrayList foundDataSourceNames = new ArrayList();// ArrayList containing the list of found dataSource names
	/**
	 * Arraylist containg the list of table names
	 */
	ArrayList foundTableNames = new ArrayList();// ArrayList containing the list of found table names
	/**
	 * ArrayList containing the list of field names
	 */
	ArrayList foundFieldNames = new ArrayList();//ArrayList containing the list of found field names
	/**
	 * Logger to log the message
	 */
	Logger logger = Logger.getLogger("FindDataDilog.class");
	/**
	 * Constructor for Find Dialog box
	 * @param parentShell
	 */
	public FindDataDialog(Shell parentShell) 
	{
		super(parentShell);
	}
	/**
	 * ethod to create window's widget
	 */
	public void create()
	{
		super.create();
		setTitle("Solution Accelerator Studio");
		setMessage("Enter the details to find");
	}
	/**
	 * Method to create and return the upper part of the dialog box
	 * @return parent ie a Composite
	 */
	public Control createDialogArea(Composite parent)
	{
		parent.getShell().setText("Find ");   
		
		try
		{		
			createWorkArea(parent);// Method to create the dialog box and to create the controls in the container
			return parent;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return parent;
	}
	/**
	 * Method to create buttons for button bar
	 * @return void
	 */
	protected void createButtonsForButtonBar(final Composite parent)//Method to create buttons for the dialog box
	{
		testFlag =false;
		try
		{
			okButton = createButton(parent,999,"OK",true);
			okButton.setEnabled(false);			
			cancelButton = createButton(parent,999,"Cancel",true);
			/**
			 * Method to add a listener to Ok button
			 */
			okButton.addSelectionListener(new SelectionAdapter()// Listener for ok button
					{
				public void widgetSelected(SelectionEvent e) 
				{
					refreshTree();// to refresh the tree view
					checkForAllCasesInFind(parent);	// To find the Item or word for all check conditions
					// It contains the business logic for FindDilog
					parent.getShell().close();// to close the dialog box					
				} // end of widget				
					});
			/**
			 * Method to add a listener to the cancel button
			 */
			cancelButton.addSelectionListener(new SelectionAdapter()// Listener for cancel button
					{
				/**
				 * Method invoked when a selection occurs in the control
				 */
				public void widgetSelected(SelectionEvent e) 
				{
					parent.getShell().close();	
				}						
					});
			testFlag = true;
		}catch(Exception e)
		{
			testFlag = false;
			e.printStackTrace();
		}
	}
	
	public void setListeners()
	{
		testFlag =false;
		commentText.addModifyListener( new ModifyListener(  )
				{
			public void modifyText( ModifyEvent event )
			{
				checkForText(  );
			}
				} );
		testFlag = true;
	}
	
	public void checkForText(  )
	{
		if ( ( commentText.getText(  ).toString(  ).length(  ) != 0 ) )
		{
			okButton.setEnabled( true );
		}
		else
		{
			okButton.setEnabled( false );
		}
	}
	/**
	 * Method to create a dialog box and to place all the required controlls in the dialog box container
	 * @param parent ie a Composite
	 */
	private void createWorkArea(Composite parent)//Method to create the dialog box and its associated UI controls on the container
	{
		Composite area = new Composite(parent, SWT.NULL);// First composite	
		Composite     firstGroup = new Composite(area, SWT.NONE); // 1st group	
		GridData gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be initialized first
		firstGroup.setLayout(new GridLayout(6, true)); // lay out
		GridData gridData = new GridData(); // to show in a grid		
		createLabel(firstGroup,"Find",gridData); // method create a label
		createFindDataText(firstGroup,gdData1);//Method to create the text field for finding a word
		createLabel(firstGroup," ",gridData); // empty label for space adjustment
		createLabel(firstGroup," ",gridData); // empty label for space adjustment
		createLabel(firstGroup," ",gridData); // empty label for space adjustment
		createLabel(firstGroup," ",gridData); // empty label for space adjustment
		createLabel(firstGroup," ",gridData); // empty label for space adjustment
		createLabel(firstGroup," ",gridData); // empty label for space adjustment
		createSearchDownButton(firstGroup,gdData1);// Method to create a radio button for search down button
		createSearchUPButton(firstGroup,gdData1);// Method to create a radio button for search up button
		createMatchCaseButton(firstGroup,gdData1);//Method to create Match case button
		createLabel(firstGroup," ",gridData); // empty label for space adjustment
		createLabel(firstGroup," ",gridData); // empty label for space adjustment
		createMatchWholeWordButton(firstGroup,gdData1);//method to create a match whole word button
		firstGroup.setBounds(25, 25, 380, 120);// Layout of the dialog box
	}
	/**
	 * Method to create a label
	 * @param firstGroup
	 * @param text
	 * @param gridData
	 */
	public void createLabel(Composite firstGroup,String text,GridData gridData)//Method to create a label
	{
		Label labelName = new Label(firstGroup, SWT.LEFT);	// It should be initialized first	
		labelName.setText(text); // Label Name of the test field to be displayed	
		labelName.setLayoutData(gridData); 
	}
	/**
	 * Method to create a text field for entering the word to find
	 * @param firstGroup
	 * @param gdData1
	 */
	public void createFindDataText(Composite firstGroup,GridData gdData1)//Method to create a text field for Find the word
	{
		findText = new Text(firstGroup, SWT.BORDER); // Creates a text box	
		findText.setFocus();
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be initialized first
		gdData1.horizontalSpan = 5;
		findText.setLayoutData(gdData1);		
		validateFindDataText(findText);// validation for find text field
		
		findText.addModifyListener(new ModifyListener()// Modify listener 
				{
			public void modifyText(ModifyEvent e) 
			{
				
			}
				});
		
	}
	/**
	 * Method to create a radio button for search down button
	 * @param firstGroup
	 * @param gdData1
	 */
	public void createSearchDownButton(Composite firstGroup,GridData gdData1)//Method to create a radio button for searchDown option
	{
		searchDownButton = new Button(firstGroup,SWT.RADIO);
		searchDownButton.setText("Search down");
		searchDownButton.setSelection(true);
		gdData1.horizontalSpan = 3;
		searchDownButton.setLayoutData(gdData1);
		/**
		 * Selection Listener for search down button
		 */
		searchDownButton.addSelectionListener(new SelectionAdapter()// when search down button is selected
				{
			public void widgetSelected(SelectionEvent e) 
			{
				searchText = findText.getText();
			}
				});
	}
	/**
	 * Method to create a radio button for search up option
	 * @param firstGroup
	 * @param gdData1
	 */
	public void createSearchUPButton(Composite firstGroup,GridData gdData1)//Method to create a radio button for serachUp option
	{
		searchUPButton = new Button(firstGroup,SWT.RADIO);
		searchUPButton.setText("Search up");
		searchUPButton.setLayoutData(gdData1);
		
		searchUPButton.addSelectionListener(new SelectionAdapter()// when searchUp button is selected
				{
			public void widgetSelected(SelectionEvent e) 
			{
				// action logic for button
				searchText = findText.getText();  
			}
				});
	}
	
	public void createFieldButton(Composite firstGroup,GridData gdData1)
	{
		fieldButton = new Button(firstGroup,SWT.RADIO);
		fieldButton.setText("Field");
		fieldButton.setLayoutData(gdData1);
	}
	/**
	 * Method to create a check box for match case
	 * @param firstGroup
	 * @param gdData1
	 */
	public void createMatchCaseButton(Composite firstGroup,GridData gdData1)//Method to create a check box for match case option
	{
		matchButton = new Button(firstGroup,SWT.CHECK);
		matchButton.setText("Match Case");
		matchButton.setLayoutData(gdData1);
		
		matchButton.addSelectionListener(new SelectionAdapter()// when match button is selected
				{
			public void widgetSelected(SelectionEvent e) 
			{
				// action logic for button				
			}
				});
	}
	/**
	 * Method to create a check box for match whole word
	 * @param firstGroup
	 * @param gdData1
	 */
	public void createMatchWholeWordButton(Composite firstGroup,GridData gdData1)//Method to create a check box for match whole word button
	{
		matchWholewordButton = new Button(firstGroup,SWT.CHECK);
		matchWholewordButton.setText("Match whole word");
		matchWholewordButton.setLayoutData(gdData1);
		
		matchWholewordButton.addSelectionListener(new SelectionAdapter()// when match whole is selected
				{
			public void widgetSelected(SelectionEvent e) 
			{
				
			}
			
				});
	}
	/**
	 * Method to validate the text field for find test field
	 * @param dsName
	 */
	public void validateFindDataText(final Text dsName) // for validating findText field
	{
		dsName.addModifyListener( new ModifyListener() // implementation of Listener
				{
			public void modifyText(ModifyEvent e) 
			{
				if(dsName.getText() == "" || dsName.getText() == null || dsName.getText().contains(" "))
				{
					okButton.setEnabled(false);
				}
				else
				{
					okButton.setEnabled(true); // if some characters are entered in the text field, OK btn will be enabled.
				}
			}			
				});
	}
	/***
	 * Method to find the entered word depending upon the cases selected
	 * @param parent
	 */
	// check for all cases in finding data
	public void checkForAllCasesInFind(final Composite parent)//Method to find the word, it contains the business logic to find a particular word
	{
		//parent.getShell().close();
		okButton.setEnabled(false);
		cancelButton.setEnabled(false);
		parent.setEnabled(false);		
		String textToFind = findText.getText();
		IStructuredSelection select = (IStructuredSelection) DatabaseViewer.viewer.getSelection();// user selecting the node
		TreeObject selectedItemTree = (TreeObject) select.getFirstElement();// selected tree item
		logger.info("    Type ..........  "+selectedItemTree.getType());// here type is group
		String selectedTreeObjectName =(String) selectedItemTree.getName();// what you select ie EKM
		ArrayList dataSourceNames = dataInfoReader.getDataSourceNames();// ArrayList containg the list of dataSource names
		
		if(searchUPButton.getSelection())// action performed, when searchUp button is selected
		{
			logger.info("Search Up button is selected .......");
			
			if( matchButton.getSelection() )// action when match button is selected
			{
				if(matchWholewordButton.getSelection())// when both(matchcase and matchwholeword button) are selected
				{
					logger.info("In searchUPButton ......Both are selected");
					searchText = findText.getText();
					logger.info(" In searchUPButton search String ::::  "+searchText);
					new FindItem().searchUpBothCases(textToFind,selectedItemTree,selectedTreeObjectName);
				}
				else
				{
					logger.info("In searchUPButton ......Match case selected only");// only match case is selected
					searchText = findText.getText();
					logger.info(" In searchUPButton search String ::::  "+searchText);
					new FindItem().searchUpWithCase(textToFind,selectedItemTree,selectedTreeObjectName);
				}
			}
			else
			{
				if(matchWholewordButton.getSelection())// action , when match whole word is selected
				{
					logger.info("In searchUPButton ......Whole word selected only" );
					searchText = findText.getText();
					logger.info(" In searchUPButton search String ::::  "+searchText);
					new FindItem().searchUpWithWholeWord(textToFind,selectedItemTree,selectedTreeObjectName);
				}
				else//when nothing is selected
				{
					logger.info("In searchUPButton ......Nothing is  selected");
					searchText = findText.getText();
					logger.info(" In searchUPButton search String ::::  "+searchText);
					new FindItem().simpleSearchUp(textToFind,selectedItemTree,selectedTreeObjectName);
				}
			}
			
			
		}// end of if(tableButton.getSelection), table button functionality ends here
		
		else if(searchDownButton.getSelection())// action performed , when data source button is selected
		{
			logger.info("Search Down button is selected .......");
			if( matchButton.getSelection() )// action when match button is selected
			{
				if(matchWholewordButton.getSelection())// when both(match case button and match whole word button) are selected
				{
					logger.info("In searchDownButton ......Both are selected");
					searchText = findText.getText();
					logger.info(" In searchDownButton search String ::::  "+searchText);
					new FindItem().searchDownBothCases(textToFind,selectedItemTree,selectedTreeObjectName);
				}
				else
				{
					logger.info("In searchDownButton ......Match case selected only");// only match case is selected
					searchText = findText.getText();
					logger.info(" In match case search String ::::  "+searchText);
					new FindItem().searchDownWithCase(textToFind,selectedItemTree,selectedTreeObjectName);
				}
			}
			else
			{
				if(matchWholewordButton.getSelection())// when match whole word is selected
				{
					logger.info("In searchDownButton ......Whole word selected only" );
					searchText = findText.getText();
					logger.info(" In searchDownButton search String ::::  "+searchText);
					new FindItem().searchDownWithWholeWord(textToFind,selectedItemTree,selectedTreeObjectName);
				}
				else//when nothing is selected
				{
					logger.info("In searchDownButton ......Nothing is  selected");
					searchText = findText.getText();
					logger.info(" In searchDownButton search String ::::  "+searchText);
					new FindItem().simpleSearchDown(textToFind,selectedItemTree,selectedTreeObjectName);
				}
			}
			
		}// end of DataSource button, dataSource button functionality ends here
		
	}
	/**
	 * Method to refresh the tree view
	 *
	 */
	public void refreshTree()//Method to refesh the tree viewer
	{
		IStructuredSelection select = (IStructuredSelection) DatabaseViewer.viewer.getSelection();
		TreeObject table = (TreeObject) select.getFirstElement();
		TreeParent tableParent = table.getParent();
		tableParent.getChildren();
		//DatabaseViewer.viewer.collapseAll();
		DatabaseViewer.viewer.refresh();
	}
	
	
}
