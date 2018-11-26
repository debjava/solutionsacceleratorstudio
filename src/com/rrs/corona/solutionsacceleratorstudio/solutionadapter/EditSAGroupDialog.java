/*******************************************************************************
 * @rrs_start_copyright
 * 
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved. This
 * software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Red Rabbit Software. ©
 * 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights
 * reserved. Red Rabbit Software - Development Program 5 of 15
 * 
 * $Id: EditSAGroupDialog.java,v 1.2 2006/07/28 13:26:16 redrabbit Exp $ 
 * $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/EditSAGroupDialog.java,v $
 * 
 * @rrs_end_copyright
 ******************************************************************************/
/*******************************************************************************
 * @rrs_start_disclaimer
 * 
 * The contents of this file are subject to the Red Rabbit Software Inc. Corona
 * License ("License"); You may not use this file except in compliance with the
 * License. THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL THE RED RABBIT SOFTWARE INC. OR ITS CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * @rrs_end_disclaimer
 ******************************************************************************/

package com.rrs.corona.solutionsacceleratorstudio.solutionadapter;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

/**
 * This class is used to show the edit dialog box for Gorup. User can change the
 * value of the group. This class extends TitleAreaDialog to show the dialog box
 * 
 * 
 * @author Maharajan
 * @see com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView
 */

public class EditSAGroupDialog extends TitleAreaDialog {
	/**
	 * <code>okButton</code> This is a SWT button labeled as OK
	 */
	transient private Button okButton = null;

	/**
	 * <code>cancelButton</code> This is a SWT button labeled as CANCEL
	 */
	transient private Button cancelButton = null;

	/**
	 * <code>groupTextUI</code> This is a SWT textfield used to enter group
	 * information
	 */
	transient private Text groupTextUI = null;

	/**
	 * <code>newGroupTextUI</code> This is a SWT textfield used to enter new group
	 * information
	 */
	transient private Text newGroupTextUI = null;

	/**
	 * <code>labelUI</code> This is a SWT label for group
	 */
	transient private Label labelUI = null;

	/**
	 * <code>groupName</code> This is used to hold gourp name
	 */
	private String groupName = null;

	private ArrayList saNamesList = new ArrayList();

	/**
	 * This constructor is used to intialize the super constructor and groupname
	 * of the data object
	 * 
	 * @param parentShell
	 *            This of type Shell on which UI going to be created
	 * @param groupName
	 *            This string will contains the group name from the data object
	 *            view
	 */
	public EditSAGroupDialog(Shell parentShell, String groupName,
			ArrayList saList) {
		super(parentShell);
		this.groupName = groupName;
		saNamesList = saList;
	}

	public void create() {
		super.create();
		setTitle(SASConstants.DO_DIALOG_GROUP_TITLE_s);// "Group Dialog");
		setMessage(SASConstants.DO_DIALOG_GROUP_EDIT_MSG_s);// "Edit dialog
		// box for group
		// name");
	}

	/**
	 * This is a call back method for dialog used to create UI for group dialog
	 */
	public Control createDialogArea(Composite parent) {
		parent.getShell().setText(SASConstants.DO_DIALOG_GROUP_TITLE_s);
		Composite child = new Composite(parent, SWT.NULL);
		Group group = new Group(child, SWT.NONE);
		group.setLayout(new GridLayout(2, true));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		//createLabel( group, SASConstants.DO_DIALOG_GROUP_NEW_NAME_s, gridData );
		createLabel(group, "Group name", gridData);
		createTextField(group, gridData);
		createLabel(group, "New Group Name", gridData);
		createNewTextField(group, gridData);
		group.setBounds(25, 25, 380, 120);

		return parent;
	}

	/**
	 * This method is used to create the lable in the UI
	 * 
	 * @param subParent
	 *            This is of type Composite under which UI going to be created
	 * @param text
	 *            This string contains text for the label
	 * @param gridData
	 */
	private void createLabel(Composite subParent, String text, GridData gridData) {
		labelUI = new Label(subParent, SWT.NULL);
		labelUI.setText(text);
	}

	/**
	 * This method is used to creat the text box in the UI
	 * 
	 * @param subParent
	 *            This is of type Composite under which UI going to be created
	 * @param gridData
	 */
	private void createTextField(Composite subParent, GridData gridData) {
		groupTextUI = new Text(subParent, SWT.BORDER | SWT.READ_ONLY);
		groupTextUI.setLayoutData(gridData);
		groupTextUI.setText(this.groupName);
	}

	/**
	 * This method is used to creat the text box in the UI
	 * 
	 * @param subParent
	 *            This is of type Composite under which UI going to be created
	 * @param gridData
	 */
	private void createNewTextField(Composite subParent, GridData gridData) {
		newGroupTextUI = new Text(subParent, SWT.BORDER);
		newGroupTextUI.setLayoutData(gridData);
		newGroupTextUI.setFocus();
		setListeners();
	}

	protected void createButtonsForButtonBar(final Composite parent) {
		try {
			okButton = createButton(parent, 999, SASConstants.UI_OK_BUTTON_s,
					true);
			okButton.setEnabled(false);
			cancelButton = createButton(parent, 999,
					SASConstants.UI_CANCEL_BUTTON_s, true);
			okButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					String groupName = newGroupTextUI.getText();
					SolutionAdapterView.groupName_s = groupName;
					parent.getShell().close();
				}
			});
			cancelButton.addSelectionListener(new SelectionAdapter() {

				public void widgetSelected(SelectionEvent e) {
					parent.getShell().close();
				}
			});

		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method " +
					"**createButtonsForButtonBar** in class [ EditSAGroupDialog ] ";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to set the listener for the text box
	 * 
	 */
	public void setListeners() {

		newGroupTextUI.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				checkForText();
				checkForSpace(newGroupTextUI.getText());
				final String groupName = newGroupTextUI.getText();
				boolean duplicateGrp = checkForDuplicateGroup(groupName
						.toLowerCase());
				if (duplicateGrp) {
					setMessage(
							"A group of this name already exists,give a different name",
							IMessageProvider.ERROR);
					okButton.setEnabled(false);
				} else {
					okButton.setEnabled(true);
				}
			}
		});
	}

	/**
	 * This method is used to check for the empty space in the text box. If
	 * empty found then OK button will be disabled
	 * 
	 */
	private void checkForText() {
		if ((newGroupTextUI.getText().toString().length() != 0)) {
			okButton.setEnabled(true);
		} else {
			okButton.setEnabled(false);
		}
	}

	/**
	 * This method is used to check wheather it contains any space. If space
	 * found then OK button will be disabled
	 * 
	 * @param textValue
	 *            This will contains the text value
	 */
	private void checkForSpace(String textValue) {
		if (null != textValue && ' ' != textValue.charAt(0)) {
			boolean spaceIdentifier = textValue.contains(" ");
			if (spaceIdentifier) {
				okButton.setEnabled(false);
				setMessage(SASConstants.DO_DIALOG_GROUP_VALID_s);
			} else {
				setMessage(SASConstants.DO_DIALOG_GROUP_EDIT_MSG_s);
				okButton.setEnabled(true);
			}
		} else {
			okButton.setEnabled(false);
			setMessage(SASConstants.DO_DIALOG_GROUP_VALID_s);
		}
	}

	private boolean checkForDuplicateGroup(final String groupName) {
		boolean checkFlag = false;
		if (saNamesList.contains(groupName)) {
			checkFlag = true;
		} else {
			checkFlag = false;
		}
		return checkFlag;
	}
}
