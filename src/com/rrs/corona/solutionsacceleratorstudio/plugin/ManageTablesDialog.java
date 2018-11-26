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
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ManageTablesDialog extends TitleAreaDialog
{
	/**
	 * Push button for Ok button
	 */
	transient private Button okButton = null;// for Ok button
	/**
	 * Push Button for Cancel button
	 */
	transient private Button cancelButton =  null;//for Cancel button
	Text commentText;
	boolean testFlag = false;
	Button addBtn ;
	/**
	 * Constructor for ManagesTablesDialog to create an instance of the title area dialog
	 * @param parentShell
	 */
	public ManageTablesDialog(Shell parentShell) 
	{
		super(parentShell);
	}
	/**
	 * Method to create window widget
	 */
	public void create()
	{
		super.create();
		setTitle("Solution Accelerator Studio");//Dialog box title
		//setMessage("Manage Tables");
	}
	/**
	 * Creates and returns the upper part of the dialog
	 * @return parent of type Composite
	 */
	public Control createDialogArea(Composite parent)
	{
		parent.getShell().setText("Manage Tables");//Shell message
		Composite area = new Composite(parent, SWT.NULL);   
		try
		{		
			Group group1 = new Group(area,SWT.NONE);
			createWorkArea(parent);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return parent;
	}
	/**
	 * Method to create Ok/Cancel buttons for the button bar of the dialog box
	 */
	protected void createButtonsForButtonBar(final Composite parent)//Method to create Ok/Cancel button
	{
		testFlag =false;
		
		try
		{
			okButton = createButton(parent,999,"Ok",true);
			okButton.setEnabled(false);			
			cancelButton = createButton(parent,999,"Cancel",true);
			okButton.addSelectionListener(new SelectionAdapter()
					{
				public void widgetSelected(SelectionEvent e) 
				{
					
				}
					});
			cancelButton.addSelectionListener(new SelectionAdapter()
					{
				
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
	/**
	 * Method to create a dialog box and its associated controls on the container of the dialog box
	 * @param parent
	 */
	private void createWorkArea(Composite parent)//Method to create a dialog box and its associated controls
	{
		Composite area = new Composite(parent, SWT.NULL);//First composite		
		Group firstGroup = new Group(area, SWT.NONE); // 1st group		
		firstGroup.setText("Tables");
		GridLayout newGrid = new GridLayout(3,true);//Lay out divides the container into 3 parts
		newGrid.verticalSpacing = 2;		
		firstGroup.setLayout(newGrid); // lay out
		GridData gridData = new GridData(); // to show in a grid
		createLabel(firstGroup,"Total Tables List",gridData);// method to create a label
		createLabel(firstGroup," ",gridData);//Method to create empty label for adjusment of controls
		createLabel(firstGroup,"Selected Tables List",gridData);//Method to create a label
		createTotalDsList(firstGroup,gridData);//Method to create a List box 
		GridData butonGrid = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		createRightArrowButton(firstGroup,butonGrid);//method to create right arrow button
		createSelectedDsList(firstGroup,gridData);		//Method to create a list box
		createLeftArrowButton(firstGroup,butonGrid);//Method to create leftArrow button
		firstGroup.setBounds(10, 20, 480, 150);
	}
	/**
	 * Method to create a label
	 * @param firstGroup
	 * @param text
	 * @param gridData
	 */
	public void createLabel(Group firstGroup,String text,GridData gridData)
	{
		Label firstLabel = new Label(firstGroup, SWT.LEFT_TO_RIGHT);	// It should be initialized first, First Label	
		firstLabel.setText(text); // Label Name of the test field to be displayed		
		firstLabel.setLayoutData(gridData); 		
	}
	/**
	 * Method to create a List box containg the list of dataSources
	 * @param firstGroup
	 * @param gridData
	 */
	public void createTotalDsList(Group firstGroup,GridData gridData)
	{
		List dsList = new List(firstGroup,SWT.BORDER); // for total no of datasource list
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalSpan = 2;
		dsList.setLayoutData(gridData);
	}
	/**
	 * Method to create a list box containg the list of selected items
	 * @param firstGroup
	 * @param gridData
	 */
	public void createSelectedDsList(Group firstGroup,GridData gridData)
	{
		List selectedDsList = new List(firstGroup,SWT.BORDER); // for selected Datasource List
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalSpan = 2;
		selectedDsList.setLayoutData(gridData);	
	}
	/**
	 * Method to create a push button for right arrow
	 * @param firstGroup
	 * @param butonGrid
	 */
	public void createRightArrowButton(Group firstGroup,GridData butonGrid)
	{
		Button rightArrowButton = new Button(firstGroup,SWT.PUSH);
		rightArrowButton.setText(">>");
		rightArrowButton.setLayoutData(butonGrid);
	}
	/**
	 * Method to create left arrow push button
	 * @param firstGroup
	 * @param butonGrid
	 */
	public void createLeftArrowButton(Group firstGroup,GridData butonGrid)
	{
		Button leftArrowButton = new Button(firstGroup,SWT.PUSH);
		leftArrowButton.setText("<<");
		leftArrowButton.setLayoutData(butonGrid);
	}
	
	
}
