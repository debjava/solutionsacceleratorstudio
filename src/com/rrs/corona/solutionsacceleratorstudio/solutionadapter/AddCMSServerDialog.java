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
 *$Id: AddCMSServerDialog.java,v 1.3 2006/08/07 04:49:42 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/AddCMSServerDialog.java,v $
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

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.rrs.corona.Sas.CMSServerList;
import com.rrs.corona.Sas.ObjectFactory;
import com.rrs.corona.Sas.SolutionAdapter;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

/**
 * This class is used to create the UserInterface the CMS Server dialog
 * 
 * @author Debadatta Mishra
 * 
 */
public class AddCMSServerDialog extends TitleAreaDialog {
	/**
	 * Push button for ok button
	 */
	transient private Button okButton = null;// ok button

	/**
	 * push button for cancel button
	 */
	transient private Button cancelButton = null;// for cancel button

	/**
	 * push button for testing database connection
	 */
	public Button testButton = null;// for test button to test the connection to
									// the CMS Server

	/**
	 * text field for CMS Server name
	 */
	transient private Text cmsName = null; // name of the CMS Server text field

	/**
	 * User Interface text field for IP address for the database connection
	 */
	transient private Text IPText = null;// name of the IP address of the CMS
											// Server

	/**
	 * User Interface text field for the database port ie default 1521 for
	 * Oracle database connection
	 */
	transient public Text portText = null;// name of the port

	/**
	 * Constructor for AddCMSServerDialog
	 * 
	 * @param parentShell
	 */
	public AddCMSServerDialog(Shell parentShell) {
		super(parentShell);
		SolutionAdapterView.serverName_s = null;
	}

	/**
	 * Method to create window widget
	 */
	public void create() {
		super.create();
		setTitle("Solution Accelerator Studio");// To set the title of the upper
												// most part of the dialog box
		setMessage("Create a CMS server by entering the server details ");// An
																			// information
																			// provided
																			// to
																			// the
																			// user
																			// in
																			// the
																			// just
																			// below
																			// the
																			// title
																			// area
																			// of
																			// the
																			// dialog
																			// box
	}

	/**
	 * Creates and returns the upper part of the dialog
	 * 
	 * @return parent of type Composite
	 */
	public Control createDialogArea(Composite parent) {
		try {
			parent.getShell().setText("Add CMS Server");// Shell message
			// parent.getShell().setText(SASConstants.CREATE_DS_DLG_SHELL_SET_TEXT);//Shell
			// message
			createWorkArea(parent);// Method to create the dialog box and its
									// associated controls in the container
			return parent;
		} catch (Exception ex) {
			final String errMsg = "Exception thrown in Method "
					+ "**createDialogArea()** in class [ AddCMSServerDialog ]";
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
			testButton = createButton(parent, 555, "Test Connection... ", false);// for
			//It is disabled temporarily, since there is no way to get the acknowledgment
			// for connection to the server
			testButton.setEnabled(false);//It is for temporary use
																					// test
																					// button
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
							ReadAndWriteXML writeObj = new ReadAndWriteXML();
							writeObj.populateCmsServerXML(cmsName.getText(),
									IPText.getText(), portText.getText());
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
			// TODO Auto-generated catch block
			final String errMsg = "Exception thrown in Method "
					+ "**createButtonsForButtonBar()** in class [ AddCMSServerDialog ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}

	}

	/**
	 * Working area and logic for UserInterface designa and business logic
	 * 
	 * @param parent
	 */
	private void createWorkArea(final Composite parent) {
		Composite area = new Composite(parent, SWT.NULL);// First main
															// composite
		Composite firstGroup = new Composite(area, SWT.NONE); // A composite
																// within the
																// main
																// Composite
		GridData gdData1 = new GridData(GridData.FILL_HORIZONTAL);// To show
																	// data in a
																	// grid
		firstGroup.setLayout(new GridLayout(3, true)); // lay out divided into
														// 3 parts
		GridData gridData = new GridData(); // to show in a grid
		createLabel(firstGroup, "CMS Server Name", gridData); // method create
																// a lebel for
																// CMS Server
																// name
		createCMSServerNameText(firstGroup, gdData1); // method to create a
														// text field for CMS
														// Server
		createLabel(firstGroup, "IP Address", gridData);// Method to create a
														// label for IP address
														// for CMS server
		createIPText(firstGroup, gdData1);// Method to create a text field for
											// the IP address for the CMS server
		createLabel(firstGroup, "Server Port", gridData);// Method to create
															// a label for
															// server port
															// number
		createPortText(firstGroup, gdData1);// Method to create a text field for
											// server port number
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
	private void createLabel(final Composite firstGroup, final String text,
			final GridData gridData) {// This method is used to create a label
		Label labelName = new Label(firstGroup, SWT.LEFT);
		labelName.setText(text); // Label Name of the test field to be
									// displayed
		labelName.setLayoutData(gridData);
	}

	/**
	 * Creates a dataSource text field for creating a new dataSource
	 * 
	 * @param firstGroup
	 * @param gdData1
	 */
	private void createCMSServerNameText(final Composite firstGroup,
			GridData gdData1) {// This method is used to create a text field
								// for entering the CMS Server name
		cmsName = new Text(firstGroup, SWT.BORDER); // Creates a text box
		// validateDataSourceText(dsName);
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be
															// initialized first
		gdData1.horizontalSpan = 2;
		cmsName.setLayoutData(gdData1);

	}

	/**
	 * Method to create a text field for IP address of the CMS Server
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type GridData
	 */
	private void createIPText(final Composite firstGroup, GridData gdData1)// method
																			// to
																			// create
																			// Text
																			// box
																			// for
																			// IP
																			// address
																			// of
																			// the
																			// database
																			// server
	{// This method is used to create a text field to enter the IP address of
		// the CMS Server
		IPText = new Text(firstGroup, SWT.BORDER);
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);
		gdData1.horizontalSpan = 2;
		IPText.setLayoutData(gdData1);
	}

	/**
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type GridData
	 */

	private void createPortText(final Composite firstGroup, GridData gdData1) {
		portText = new Text(firstGroup, SWT.BORDER);
		gdData1 = new GridData(GridData.FILL_HORIZONTAL);
		gdData1.horizontalSpan = 2;
		portText.setLayoutData(gdData1);
		portText.setText("21099");
	}
}
