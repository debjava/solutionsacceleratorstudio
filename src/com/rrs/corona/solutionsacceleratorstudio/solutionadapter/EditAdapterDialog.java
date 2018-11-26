/******************************************************************************
 * @rrs_start_copyright
 *
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved.
 * This software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of
 * the license agreement you entered into with Red Rabbit Software.
 © 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights reserved.
 Red Rabbit Software - Development Program 5 of 15
 *$Id: EditAdapterDialog.java,v 1.2 2006/07/28 13:26:16 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/EditAdapterDialog.java,v $
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
package com.rrs.corona.solutionsacceleratorstudio.solutionadapter;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

public class EditAdapterDialog extends TitleAreaDialog {

	/**
	 * Push button for ok button
	 */
	transient private Button okButton = null;// ok button

	/**
	 * push button for cancel button
	 */
	transient private Button cancelButton = null;// for cancel button

	/**
	 * text field for CMS Server name
	 */
	transient private Text adapterName = null;

	/**
	 * text field for CMS Server name
	 */
	transient private Text newadapterName = null;

	/**
	 * Constructor for AddCMSServerDialog
	 * 
	 * @param parentShell
	 */
	public EditAdapterDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Method to create window widget
	 */
	public void create() {
		super.create();
		setTitle("Solution Accelerator Studio");// To set the title of the upper
												// most part of the dialog box
		setMessage("Edit Dialog for Solutions Adapter");// An information
														// provided to the user
														// in the just below the
														// title area of the
														// dialog box
	}

	/**
	 * Creates and returns the upper part of the dialog
	 * 
	 * @return parent of type Composite
	 */
	public Control createDialogArea(Composite parent) {
		try {
			parent.getShell().setText("EditAdapter");// Shell message
			// parent.getShell().setText(SASConstants.CREATE_DS_DLG_SHELL_SET_TEXT);//Shell
			// message
			createWorkArea(parent);// Method to create the dialog box and its
									// associated controls in the container
			return parent;
		} catch (Exception ex) {
			final String errMsg = "Exception thrown in "
					+ "**createDialogArea()** in class [ EditAdapterDialog.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, ex);
			ex.printStackTrace();
		}
		return parent;
	}

	/**
	 * Adds button to the button bar of the dialog box
	 */
	public void createButtonsForButtonBar(final Composite parent) {
		try {
			okButton = createButton(parent, 999, "OK", true);// for ok button
			okButton.setEnabled(true);
			cancelButton = createButton(parent, 999, "Cancel", true);
			okButton.addSelectionListener(new SelectionAdapter()// Listener for
																// ok button ie
																// when cancel
																// button is
																// pressed,the
																// dialog box
																// will be
																// closed
					{
						public void widgetSelected(SelectionEvent e) {
							SolutionAdapterView.adapterName_s = newadapterName
									.getText();
							/*
							 * SASServerInforReader addServer = new
							 * SASServerInforReader();
							 * addServer.writeServerInfo(adapterName.getText(),serverName.getText(),dataSource.getText());
							 */
							parent.getShell().close();
						}
					});
			cancelButton.addSelectionListener(new SelectionAdapter()// Listener
																	// for
																	// cancel
																	// button ie
																	// when
																	// cancel
																	// button is
																	// pressed,the
																	// dialog
																	// box will
																	// be closed
					{
						public void widgetSelected(SelectionEvent e) {
							parent.getShell().close();
						}
					});
		} catch (Exception e) {
			final String errMsg = "Exception thrown in "
					+ "**createButtonsForButtonBar()** in class [ EditAdapterDialog.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}

	}

	/**
	 * Working area and logic for UserInterface designa and business logic
	 * 
	 * @param parent
	 */
	private void createWorkArea(Composite parent) {
		Composite area = new Composite(parent, SWT.NULL);// First main
															// composite
		Group firstGroup = new Group(area, SWT.NONE); // A composite within
														// the main Composite
		GridData gdData1 = new GridData(GridData.FILL_HORIZONTAL);// To show
																	// data in a
																	// grid
		firstGroup.setLayout(new GridLayout(3, true)); // lay out divided into
														// 3 parts
		GridData gridData = new GridData(); // to show in a grid
		createLabel(firstGroup, "Adapter Name", gridData); // method create a
															// lebel for CMS
															// Server name
		createAdapterNameText(firstGroup, gdData1); // method to create a text
													// field for CMS Server
		createLabel(firstGroup, "New Adapter ", gridData); // method create a
															// lebel for CMS
															// Server name
		createNewAdapterNameText(firstGroup, gdData1); // method to create a
														// text field for CMS
														// Server
		firstGroup.setBounds(25, 25, 380, 120);// Layout of the dialog box
	}

	/**
	 * Method to create a label
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param text
	 *            of type String
	 * @param gridData
	 *            of type GridData
	 */
	public void createLabel(Composite firstGroup, String text, GridData gridData) {// This
																					// method
																					// is
																					// used
																					// to
																					// create
																					// a
																					// label
		Label labelName = new Label(firstGroup, SWT.LEFT);
		labelName.setText(text); // Label Name of the test field to be
									// displayed
		labelName.setLayoutData(gridData);
	}

	/**
	 * Creates a adapter text field adapter
	 * 
	 * @param firstGroup
	 * @param gdData1
	 */
	public void createAdapterNameText(Composite firstGroup, GridData gdData1) {// This
																				// method
																				// is
																				// used
																				// to
																				// create
																				// a
																				// text
																				// field
																				// for
																				// entering
																				// the
																				// CMS
																				// Server
																				// name
		adapterName = new Text(firstGroup, SWT.BORDER | SWT.READ_ONLY); // Creates
																		// a
																		// text
																		// box
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be
															// initialized first
		gdData1.horizontalSpan = 2;
		adapterName.setLayoutData(gdData1);
		adapterName.setText(SolutionAdapterView.adapterName_s);
	}

	/**
	 * Creates a adapter text field for creating a new adapter
	 * 
	 * @param firstGroup
	 * @param gdData1
	 */
	public void createNewAdapterNameText(Composite firstGroup, GridData gdData1) {// This
																					// method
																					// is
																					// used
																					// to
																					// create
																					// a
																					// text
																					// field
																					// for
																					// entering
																					// the
																					// CMS
																					// Server
																					// name
		newadapterName = new Text(firstGroup, SWT.BORDER); // Creates a text
															// box
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be
															// initialized first
		gdData1.horizontalSpan = 2;
		newadapterName.setLayoutData(gdData1);
		newadapterName.setFocus();
	}
	/**
	 * Method to create a text field for IP address of the CMS Server
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type GridData
	 */
	/*
	 * public void createServerName(Composite firstGroup,GridData gdData1)//
	 * method to create Text box for IP address of the database server {//This
	 * method is used to create a text field to enter the IP address of the CMS
	 * Server serverName = new Combo(firstGroup, SWT.BORDER); gdData1 = new
	 * GridData(GridData.FILL_HORIZONTAL); gdData1.horizontalSpan = 2;
	 * serverName.setLayoutData(gdData1);
	 * serverName.setText(SolutionAdapterView.serverName_s); }
	 */
	/**
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type GridData
	 */
	/*
	 * 
	 * public void createDataSource(Composite firstGroup,GridData gdData1) {
	 * dataSource = new Combo(firstGroup, SWT.BORDER); gdData1 = new
	 * GridData(GridData.FILL_HORIZONTAL); gdData1.horizontalSpan = 2;
	 * dataSource.setLayoutData(gdData1);
	 * dataSource.setText(SolutionAdapterView.dataSourceName_s); }
	 */
}
