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
 *$Id: RenameCMSServerDialog.java,v 1.2 2006/07/28 13:26:16 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/RenameCMSServerDialog.java,v $
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

public class RenameCMSServerDialog extends TitleAreaDialog {
	transient private Button okButton = null;// for ok button

	transient private Button cancelButton = null;// for cancel button

	transient private Text previousCMSName = null; // name of the Combo field
													// to show the present CMS
													// Server name

	transient private Text newCMSNameText = null;// Text field for entering
													// new CMS Server name

	public RenameCMSServerDialog(Shell parentShell) {
		super(parentShell);
	}

	public void create() {
		super.create();
		setTitle("Solution Accelerator Studio");
		setMessage("Select a CMS server to rename");
	}

	public Control createDialogArea(Composite parent) {
		parent.getShell().setText("Rename CMS Server ");
		try {
			createWorkArea(parent);
			return parent;
		} catch (Exception ex) {
			final String errMsg = "Exception thrown in "
					+ "Method **createDialogArea()** in class [ RenameCMSServerDialog.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, ex);
			ex.printStackTrace();
		}
		return parent;
	}

	protected void createButtonsForButtonBar(final Composite parent) {

		try {
			okButton = createButton(parent, 999, "OK", true);
			okButton.setEnabled(false);
			cancelButton = createButton(parent, 999, "Cancel", true);

			cancelButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					parent.getShell().close();
				}
			});
		} catch (Exception e) {
			final String errMsg = "Exception thrown in "
					+ "Method **createButtonsForButtonsBar()** in class [ RenameCMSServerDialog.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

	private void createWorkArea(Composite parent) {
		Composite area = new Composite(parent, SWT.NULL);
		Composite firstGroup = new Composite(area, SWT.NONE); // 1st group
		firstGroup.setLayout(new GridLayout(3, true)); // lay out
		GridData gridData = new GridData(); // to show in a grid
		createLabel(firstGroup, "Select CMS Server", gridData);
		createCMSCombo(firstGroup, gridData);
		createLabel(firstGroup, "New CMS Server", gridData);
		createCMSText(firstGroup, gridData);
		firstGroup.setBounds(25, 25, 380, 120);
	}

	public void createLabel(Composite firstGroup, String text, GridData gridData) {
		Label previousCMSLabelName = new Label(firstGroup, SWT.LEFT); // It
																		// should
																		// be
																		// initialized
																		// first,
																		// First
																		// Label
		previousCMSLabelName.setText(text); // Label Name of the test field to
											// be displayed
		previousCMSLabelName.setLayoutData(gridData);
	}

	public void createCMSCombo(Composite firstGroup, GridData gridData1) {
		previousCMSName = new Text(firstGroup, SWT.BORDER | SWT.READ_ONLY); // Creates
																			// a
																			// Combo
																			// box
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);// It should be
															// initialized first
		gridData1.horizontalSpan = 2;
		previousCMSName.setLayoutData(gridData1);
		previousCMSName.setText(SolutionAdapterView.serverName_s);
	}

	public void createCMSText(Composite firstGroup, GridData gridData1) {
		newCMSNameText = new Text(firstGroup, SWT.BORDER);
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan = 2;
		newCMSNameText.setLayoutData(gridData1);
	}

}
