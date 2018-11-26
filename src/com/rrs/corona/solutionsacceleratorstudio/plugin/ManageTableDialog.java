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
import java.util.HashSet;
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
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ManageTableDialog extends TitleAreaDialog 
{
	/**
	 * Logger for logging
	 */
	Logger logger = Logger.getLogger("ManageTableDialog.class");
	/**
	 * Push button for Ok button
	 */
	transient private Button okButton = null;//for Ok button
	/**
	 * Push button for Cancel button
	 */
	transient private Button cancelButton =  null;//for Cancel button
	/**
	 * List box to show the list of tables in a dataSource
	 */
	List totalTableList ; // for Table List box
	/**
	 * List box for showing the tables selected by the user
	 */
	List selectedTableList ; // for selected Data Source List box
	/**
	 * Push button for right arrow button
	 */
	Button rightArrowButton; // for right arrow button
	/**
	 * Push button for left arrow button
	 */
	Button leftArrowButton;// For left arrow button
	/**
	 * Check box for matchcase
	 */
	Button matchButtonTableList = null;// for Match Case button for Total DataSource list
	/**
	 * Check box for match wholeword
	 */
	Button matchWholewordButtonTableList = null;// for Match whole word button for Total tables list
	/**
	 * Text field for filtering the tables
	 */
	Text tableFilterText;// text field for table filter
	/**
	 * ArrayList containing the tables selected by the user
	 */
	ArrayList selectedTableItemList1 = new ArrayList();// This arraylist contains the list of tables selected by the user
	/**
	 * ArrayList containing the list of total table names
	 */
	static ArrayList totalTableitemList = new ArrayList();// This arrayList contains the list of total table names
	/**
	 * Boolean varibale to check the occurance of duplicate table names selected by the user
	 */
	boolean checkDuplicate = false;// boolean variable to check the duplicate table item
	/**
	 * Array containing the list of total table names selected by the user
	 */
	String selectedTablenameList[] ;// Array of total selected table names
	/**
	 * DataSource name from the database viewer ie from the tree viewer
	 */
	String viewerDataSourceName = DatabaseViewer.dataSourceName;// tree viewer for dataSource name
	/**
	 * ArrayList containg the tableItems
	 */
	ArrayList tableItemList = new ArrayList();//ArrayList containing the list of table items
	/**
	 * An Arraylist used to populate the selectedTableList
	 */
	static ArrayList selectedTableItemList = new ArrayList();//This arrayList is used to track the items in the selectedtableList
	/**
	 * Constructor to create an instance of the title area dialog
	 * @param parentShell of type Shell
	 */
	
	public ManageTableDialog(Shell parentShell) 
	{
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Method to create window widget
	 */
	public void create()
	{
		super.create();
		setTitle("Solution Accelerator Studio");//Titla of the dialog box
		setMessage("Select tables from list");//Message below the title area
	}
	/**
	 * Creates and returns the upper part of the dialog
	 * @return parent of type Composite
	 */
	public Control createDialogArea(Composite parent)
	{
		parent.getShell().setText("Manage table");//Dialog shell message
		try
		{		
			createWorkArea(parent);//Method to create the dialog box and its associated contols on the container of the dialog box
			return parent;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return parent;
	}
	/**
	 * method to create buttons for the button bar of the dialog box
	 */
	protected void createButtonsForButtonBar(final Composite parent)
	{//This method is used to create Ok/Cancel buttons for button bar of the dialog box
		//testFlag =false;
		try
		{
			okButton = createButton(parent,999,"OK",true);
			okButton.setEnabled(true);	
			okButton.setFocus();
			cancelButton = createButton(parent,999,"Cancel",true);
			okButton.addSelectionListener(new SelectionAdapter()
					{
				public void widgetSelected(SelectionEvent e) 
				{
					showAllTableInformationInTree(parent);//Method to show all the table in the tree viewer
				}
					});
			cancelButton.addSelectionListener(new SelectionAdapter()//Listener for closing the dialog box when cancel button is selected
					{
				public void widgetSelected(SelectionEvent e) 
				{
					parent.getShell().close();
				}		
					});
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Method to create a dialog box and the various controls on the container
	 * @param parent of type Composite
	 */
	private void createWorkArea(Composite parent)//Method to create a dialog box and its associated controls
	{
		Composite area = new Composite(parent, SWT.NULL);				
		Composite firstGroup1 = new Composite(area, SWT.NONE); // 1st group
		firstGroup1.setBounds(10,10,430,200);
		createTotalTableLabel(firstGroup1,"Table list");// Method to create a label for Table List
		createSelectedTableLabel(firstGroup1,"Selected table list");// Method to create a Label for selected Table
		createTotalTableList(firstGroup1);// Method to create a list box for Total Tables
		createSelectedTableList(firstGroup1);//Method to create selected table list
		createRightArrowButton(firstGroup1);//Method to create Right arrow button
		createLeftArrowButton(firstGroup1);//Method to create left arrow button
		createTotalTableFilterLabel(firstGroup1,"Table filter");//Method to create a filter text for table
		createTableFilterText(firstGroup1);
		createMatchCaseButtonTableList(firstGroup1);//For Match case
		createMatchWholeWordButtonTableList(firstGroup1);// For Match whole word
	}
	/**
	 * Method used to create a label
	 * @param firstGroup of type Composite
	 * @param text of type String
	 */
	public void createTotalTableLabel(Composite firstGroup,String text)
	{//This method is used to create a label for total tables in a particular dataSource
		Label firstLabel = new Label(firstGroup, SWT.LEFT);	// It should be initialized first, First Label	
		firstLabel.setText(text); // Label Name of the test field to be displayed		
		firstLabel.setBounds(20,10,100,20);
	}
	/**
	 * Method to create a label for selected table
	 * @param firstGroup of type Composite
	 * @param text of type String
	 */
	public void createSelectedTableLabel(Composite firstGroup,String text)
	{//Method for selected table label
		Label firstLabel = new Label(firstGroup, SWT.LEFT);	// It should be initialized first, First Label	
		firstLabel.setText(text); // Label Name of the test field to be displayed		
		firstLabel.setBounds(293,10,130,20);
	}
	/**
	 * Method to create a label for table filter
	 * @param firstGroup of type Composite
	 * @param text of type String
	 */
	public void createTotalTableFilterLabel(Composite firstGroup,String text)
	{//Method for table filter label
		Label firstLabel = new Label(firstGroup, SWT.LEFT);	// It should be initialized first, First Label	
		firstLabel.setText(text); // Label Name of the test field to be displayed		
		firstLabel.setBounds(20,120,100,20);
	}
	/**
	 * Method to create a List box showing the list of tables
	 * @param firstGroup of type Composite
	 * @param gridData of type GridData
	 */
	public void createTotalTableList(Composite firstGroup)
	{//Method to create a lIst box for total tables
		totalTableList = new List(firstGroup,SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL); // for total no of table list
		totalTableList.setBounds(20,30,100,80);
		populateTableList();//Method to populate the total tables from a particular database to the list box
	}
	/**
	 * Method to create right arrow button
	 * @param firstGroup of type Composite
	 * 
	 */
	public void createRightArrowButton(final Composite firstGroup)
	{//Method for right arrow button
		rightArrowButton = new Button(firstGroup,SWT.PUSH);
		rightArrowButton.setText(">>");
		rightArrowButton.setBounds(180,50,50,20);
		
		rightArrowButton.addSelectionListener(new SelectionAdapter()
				{
			public void widgetSelected(SelectionEvent e) 
			{
				// action logic for button
				String selectedTablenameList[] = totalTableList.getSelection();
				
				for(int i=0;i<selectedTablenameList.length;i++)
				{
					String selectedItem = selectedTablenameList[i];					
					selectedTableItemList1.add(selectedItem);	
					//new enhancement
					selectedTableItemList.add(selectedItem);
					//upto
					checkforText();
					checkDuplicate = checkForDuplicates(selectedTableItemList,selectedItem);//Method to check duplcate items
					if(checkDuplicate == false)
					{
						MessageDialog.openWarning(firstGroup.getShell(),"Warning","Selected list already contains this table, please select a different table name");
						selectedTableItemList.remove(selectedItem);
					}					
					else
					{
						selectedTableList.add(selectedItem);
						okButton.setEnabled(true);
					}
					checkforText();
				}
			}
				});
	}
	/**
	 * Method to create a left arrow button
	 * @param firstGroup of type Composite
	 * 
	 */
	public void createLeftArrowButton(Composite firstGroup)
	{// Method for left arrow button
		leftArrowButton = new Button(firstGroup,SWT.PUSH);
		leftArrowButton.setText("<<");
		leftArrowButton.setBounds(180,80,50,20);
		
		leftArrowButton.addSelectionListener(new SelectionAdapter()
				{
			public void widgetSelected(SelectionEvent e) 
			{
				// action logic for button
				selectedTablenameList = selectedTableList.getSelection();
				for(int i=0;i<selectedTablenameList.length;i++)
				{
					String selectedItem = selectedTablenameList[i];
					selectedTableList.remove(selectedItem);
					selectedTableItemList1.remove(selectedItem); ///
					//new enhancement
					selectedTableItemList.remove(selectedItem);//This item should be removed from this arrayalist
					checkforText(); // to enable or disable the okButton
				}
			}
				});
	}
	/**
	 * Method to create a list box showing the list of tables selected by the user
	 * @param firstGroup of type Composite
	 * 
	 */
	public void createSelectedTableList(Composite firstGroup)
	{//Method to create selected table List box
		selectedTableList = new List(firstGroup,SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL); // for selected Datasource List
		selectedTableList.setBounds(293,30,100,80);
		selectedTableList.removeAll();
		populateSelectedtableList();
	}
	/**
	 * method to create a text field for entering the table filter
	 * @param firstGroup of type Composite
	 * 
	 */
	public void createTableFilterText(Composite firstGroup)
	{//Method to create a Text field for table filter
		tableFilterText = new Text(firstGroup, SWT.BORDER);
		tableFilterText.setBounds(20,140,100,18);
		tableFilterText.addModifyListener(new ModifyListener()
				{
			public void modifyText(ModifyEvent e) 
			{
				String filterString = tableFilterText.getText().toString();
				
				if( matchButtonTableList.getSelection() )//When match button is selected
				{
					if(matchWholewordButtonTableList.getSelection())// when both are selected
					{
						logger.info("Both are selected");
						totalTableList.removeAll();
						populateTableListWithMatchCase(filterString);//populate the tables in the total table list according to the user condition
					}
					else
					{
						logger.info("Match case selected only");// only match case is selected
						totalTableList.removeAll();
						populateTableListWithMatchCase(filterString);//populate the tables in the total table list according to the user condition
					}
				}
				else
				{
					if(matchWholewordButtonTableList.getSelection())// when match whole word is selected
					{
						logger.info("Whole word selected only" );
						totalTableList.removeAll();
						populateTableListWithWholeword(filterString);//populate the tables in the total table list according to the user condition
					}
					else//when nothing is selected
					{
						logger.info("Nothing is  selected");
						populateTableListWithoutCase(filterString.toLowerCase());//populate the tables in the total table list according to the user condition
					}
				}
			}
			
				});
	}
	/**
	 * Method to create a check box for MatchCase
	 * @param firstGroup of type Composite
	 * 
	 */
	public void createMatchCaseButtonTableList(final Composite firstGroup)
	{//Method to create a check box for match case
		matchButtonTableList = new Button(firstGroup,SWT.CHECK);
		matchButtonTableList.setText("Match Case");
		matchButtonTableList.setBounds(20,160,100,20);
		matchButtonTableList.addSelectionListener(new SelectionAdapter()
				{
			public void widgetSelected(SelectionEvent e) 
			{
				String filterString = tableFilterText.getText();
				
				if( matchButtonTableList.getSelection() )//When match case is selected
				{
					if(matchWholewordButtonTableList.getSelection())// when both are selected
					{
						logger.info("Both are selected");
						totalTableList.removeAll();
						populateTableListWithMatchCase(filterString);//populate the tables in the total table list according to the user condition
					}
					else
					{
						logger.info("Match case selected only");// only match case is selected
						totalTableList.removeAll();
						populateTableListWithMatchCase(filterString);//populate the tables in the total table list according to the user condition
					}
				}
				else
				{
					if(matchWholewordButtonTableList.getSelection())// when match whole word is selected
					{
						logger.info("Whole word selected only" );
						totalTableList.removeAll();
						populateTableListWithWholeword(filterString);//populate the tables in the total table list according to the user condition
					}
					else//when nothing is selected
					{
						logger.info("Nothing is  selected");
						populateTableListWithoutCase(filterString.toLowerCase());//populate the tables in the total table list according to the user condition
					}
				}
				
			}
				});
		
	}	
	
	public void createMatchWholeWordButtonTableList(final Composite firstGroup)
	{// Method to create check box for match whole word
		matchWholewordButtonTableList = new Button(firstGroup,SWT.CHECK);
		matchWholewordButtonTableList.setText("Match whole word");
		matchWholewordButtonTableList.setBounds(20,180,130,20);//20,180,100,20
		
		matchWholewordButtonTableList.addSelectionListener(new SelectionAdapter()
				{
			public void widgetSelected(SelectionEvent e) 
			{
				// action logic for button
				String filterString = tableFilterText.getText();
				
				if( matchWholewordButtonTableList.getSelection())//When match whole word button is selected
				{
					if(matchButtonTableList.getSelection())//If both are selected
					{
						logger.info("Both selected");
						totalTableList.removeAll();
						populateTableListWithMatchCase(filterString);//Method to populate the tables to the list box with both conditions selected
					}
					else
					{
						logger.info("Whole word selected only");
						totalTableList.removeAll();
						populateTableListWithWholeword(filterString);// Method to populate to the list box if only whole word is selected
					}
				}
				else
				{
					if(matchButtonTableList.getSelection())//If only match case is selected
					{
						logger.info("Match case selected only");
						totalTableList.removeAll();
						populateTableListWithMatchCase(filterString);//Populate to the list box when mactch case is selected
					}
					else
					{
						logger.info("Nothing is  selected");
						populateTableListWithoutCase(filterString.toLowerCase());//Populate to the list box when nothing is selected
					}
				}	
				
			}
				});
		
	}
	/**
	 * Method to populate the total tables from the XML file to the total table list 
	 *
	 */
	public void populateTableList()
	{//The tables from a particular dataSource from the XML file will be visible in the total list box
		DsReader dsreader = new DsReader();//Object to read or parse from the XML file
		ArrayList dataSourcenamesList =  dsreader.getDataSourceNames();//Arraylist containing the list of dataSource names
		for(int i=0;i<dataSourcenamesList.size();i++)
		{
			String dsname = (String)dataSourcenamesList.get(i);
			
			if((DatabaseViewer.dataSourceName).equals(dsname))
			{
				ArrayList totalTableNames = dsreader.getTotalTableNamesOfOneDS(dsname);//Arraylist containing the list of tables
				
				for(int j=0;j<totalTableNames.size();j++)
				{
					Object object1 = (Object)totalTableNames.get(j);
					DsTableInfo tabInfo = (DsTableInfo)object1;																		
					String tab_Name = tabInfo.getDsTableName();
					totalTableList.add((String)tab_Name);
					tableItemList.add((String)tab_Name);
				}
			}
			else
			{
				//do nothing
			}
		}	
		
	}
	/**
	 * Method to make the Ok button enable or disable 
	 *
	 */
	public void checkforText()//If the selected list box does not contain any tables, the ok button will be set disable otherwise will be set enabled
	{
		if(selectedTableList.getItemCount()>0)
		{
			okButton.setEnabled(true);
		}
		else
		{
			okButton.setEnabled(false);
		}
	}
	/**
	 * Method to check the duplicate items
	 * @param selectedDsnameList of type ArrayList
	 * @param item of type String
	 * @return true if duplicate is found otherwise false
	 */
	public boolean checkForDuplicates(ArrayList selectedDsnameList,String item)
	{//Method to check duplicate table names in the selected table list
		boolean returnFlag = false;
		HashSet newHashSet = new HashSet();
		for(int i=0;i<selectedDsnameList.size();i++)
		{
			newHashSet.add(selectedDsnameList.get(i));
		}
		
		if(newHashSet.size()< selectedDsnameList.size() )
		{
			returnFlag = false;			
		}
		else if(newHashSet.size()== selectedDsnameList.size())
		{
			returnFlag = true;
			okButton.setEnabled(true);
		}
		else
		{
			returnFlag = true;
			okButton.setEnabled(true);
		}
		
		return returnFlag;
	}
	/**
	 * Method to create a progress bar
	 * @param shell of type Shell
	 * @return an object of type ProgressBar
	 */
	private ProgressBar createProgBar(Shell shell)
	{//Method to create a progress bar while loading the tables in the tree viewer		
		ProgressBar pb = new ProgressBar(shell, SWT.CENTER);
		try
		{
			shell.setLayout(new GridLayout(1, false));
			pb.setMinimum(0);
			GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
			gd1.verticalSpan = 12;
			pb.setLayoutData(gd1);
			shell.setSize(650,150);
		}
		catch(Exception ex)
		{
		}
		return pb;
	}
	/**
	 * Method to show the table in the tree viewer
	 * @param parent of Type Composite
	 */
	public void showAllTableInformationInTree(final Composite parent)
	{//Method to show table information in the tree view
		IStructuredSelection select = (IStructuredSelection) DatabaseViewer.viewer.getSelection();
		TreeObject table = (TreeObject) select.getFirstElement();// first element will be the dataSource
		logger.info("table :"+table);
		TreeParent parentitem = (TreeParent) table;
		TreeObject obj[] = parentitem.getChildren();
		for(int size = 0 ; size < obj.length ; size ++)
		{
			parentitem.removeChild(obj[size]);
		}
		DatabaseViewer.viewer.refresh();
		
		int level = 1;
		parent.getShell().close();
		Shell shell = new Shell(new Shell().getDisplay(),SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		ProgressBar pb = createProgBar(shell);
		Label progBtn = new Label(shell,SWT.NONE | SWT.CENTER); 
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.verticalSpan = 4;
		progBtn.setLayoutData(gd);
		shell.open();
		level=1;
		String showDsname = viewerDataSourceName;
		TreeParent treeObject = (TreeParent) table;
		ArrayList totalTableNames = selectedTableItemList ; //selectedTableItemList1;
		pb.setMaximum(totalTableNames.size());
		shell.setText("Loading "+showDsname);
		
		for(int j=0;j<totalTableNames.size();j++)
		{
			String tab_Name = (String)totalTableNames.get(j);
			TreeParent treeParent2 = new TreeParent(tab_Name);
			treeObject.addChild(treeParent2);
			treeParent2.setType("table");
			ArrayList totalFieldNames_OneTab = new DsReader().getTotalFieldNamesFromOneTab(showDsname,tab_Name);
			progBtn.setAlignment(SWT.LEFT);
			progBtn.setText("Loading tables " + tab_Name);
			pb.setSelection(level);
			level = level+1;
			
			for(int k=0;k<totalFieldNames_OneTab.size();k++)
			{
				Object fieldObject = (Object)totalFieldNames_OneTab.get(k);
				DsFieldInfo dsfieldInfo =(DsFieldInfo) fieldObject ;
				String showFieldNType = dsfieldInfo.getDsFieldName()+"   ::   "+"< "+dsfieldInfo.getDsFieldType()+" >";
				TreeParent fieldObject11 = new TreeParent(showFieldNType);
				fieldObject11.setDataType(dsfieldInfo.getDsFieldType());
				fieldObject11.setDataSourceName(showDsname);//setting dataSource name
				fieldObject11.setTableName(treeParent2.getName());//setting table name
				fieldObject11.setPrimaryKey(dsfieldInfo.isPrimaryKey());//setting primary key
				treeParent2.addChild(fieldObject11);
				fieldObject11.setType("field");
				
				treeObject.setDataSourceName(dsfieldInfo.getDataSourceName());//for setting dataSource name
				treeObject.setDbPassword(dsfieldInfo.getDataSourcePassword());//for setting db password
				treeObject.setDbURL(dsfieldInfo.getDataSourceURL());// for setting dataSource URl
				treeObject.setDbUserName(dsfieldInfo.getDataSourceUserName());//for setting username
				treeObject.setType("group");
			}
		}
		DatabaseViewer.viewer.refresh(treeObject,true);//refreshing the database viewer
		shell.close();
	}
	/**
	 * Method to show the tables selected by the user in the list box when no condition is applied
	 * @param filterString
	 */
	public void populateTableListWithoutCase(String filterString)
	{//Method to populate the table information without any case
		totalTableList.removeAll();
		ArrayList totalTableNames = tableItemList;
		
		for(int i=0;i<totalTableNames.size();i++)
		{
			String dsname = (String)totalTableNames.get(i);
			String temp_dsName = dsname.toLowerCase();
			if(temp_dsName.contains(filterString))
			{
				totalTableList.add((String)dsname);
			}
		}
	}
	/**
	 * Method to show the table in the list box 
	 * @param filterString of Type String
	 */
	
	public void populateTableListWithWholewordCase2(String filterString)
	{//Method to populate the table information with the condition
		//whole word
		totalTableList.removeAll();
		ArrayList totalTableNames = tableItemList;
		
		for(int i=0;i<totalTableNames.size();i++)
		{
			String dsname = (String)totalTableNames.get(i);
			String temp_dsName = dsname.toLowerCase();
			if(temp_dsName.equals(filterString))
			{
				totalTableList.add((String)dsname);
				
			}
		}
	}
	/**
	 * Method to show the tables in the list box when match case is selected
	 * @param filterString
	 */
	public void populateTableListWithMatchCase(String filterString) 
	{// It will work when apply filter button is pressed
		ArrayList totalTableNames = tableItemList;
		
		for(int i=0;i<totalTableNames.size();i++)
		{
			String dsname = (String)totalTableNames.get(i);
			if(dsname.contains(filterString))
			{
				totalTableList.add((String)dsname);
			}
		}
	}
	/**
	 * Method to show the tables in the list box when match whole is selected
	 * @param filterString
	 */
	public void populateTableListWithWholeword(String filterString)
	{//Method to populate the tables when match whole word is selected
		ArrayList totalTableNames = tableItemList;
		for(int i=0;i<totalTableNames.size();i++)
		{
			String dsname = (String)totalTableNames.get(i);
			if(dsname.equalsIgnoreCase(filterString))
			{
				totalTableList.add((String)dsname);
			}
		}	
	}
	
	public void populateSelectedtableList()
	{//This method is used to populated the selected table list
		//First of all it will show how many tables are present in the tree viewer
		selectedTableList.removeAll();
		selectedTableItemList.clear();
		IStructuredSelection select = (IStructuredSelection) DatabaseViewer.viewer.getSelection();
		TreeObject table = (TreeObject) select.getFirstElement();
		TreeParent treeParent = (TreeParent) table;
		TreeObject[] treeObj = (TreeObject[])treeParent.getChildren();
		for(int i=0;i<treeObj.length;i++)
		{
			selectedTableItemList.add(treeObj[i].getName());
		}
		for(int j=0;j<selectedTableItemList.size();j++)
		{
			selectedTableList.add((String)selectedTableItemList.get(j));
		}
	}
	
}
