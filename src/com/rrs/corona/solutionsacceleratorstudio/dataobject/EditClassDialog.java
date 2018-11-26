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
 * $Id: EditClassDialog.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/dataobject/EditClassDialog.java,v $
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

package com.rrs.corona.solutionsacceleratorstudio.dataobject;

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

/**
 * This class is used to show the edit dialog box for class name. User can
 * change the value of the class name.
 * <p>
 * This class extends TitleAreaDialog to show the dialog box
 * </P>
 * 
 * 
 * @author Maharajan
 * @see com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView
 */

public class EditClassDialog extends TitleAreaDialog
{

	/**
	 * <code>okButton</code> This is a SWT button labeled as OK
	 */
	transient private Button	okButton		= null;
	/**
	 * <code>cancelButton</code> This is a SWT button labeled as CANCEL
	 */
	transient private Button	cancelButton	= null;
	/**
	 * <code>textUI</code> This is a SWT textfield used to entre class
	 * information
	 */
	transient private Text		textUI			= null;
	/**
	 * <code>labelUI</code> This is a SWT label for class
	 */
	transient private Label		labelUI			= null;
	/**
	 * <code>className</code> This is used to hold the class name
	 */
	transient private String	className		= null;

	/**
	 * This constructor used to instialize the group name and class name
	 */
	public EditClassDialog( Shell parentShell, String className )
	{
		super( parentShell );
		DataObjectView.groupName_s = null;
		this.className = className;
	}

	public void create( )
	{
		super.create( );
		setTitle( SASConstants.DO_DIALOG_CLASS_TITLE_s );
		setMessage( SASConstants.DO_DIALOG_CLASS_EDIT_MSG_s );
	}

	/**
	 * This is a call back mathod is used to create the UI
	 */
	public Control createDialogArea( Composite parent )
	{
		parent.getShell( ).setText( SASConstants.DO_DIALOG_CLASS_TITLE_s );
		Composite child = new Composite( parent, SWT.NULL );
		Group group = new Group( child, SWT.NONE );
		group.setLayout( new GridLayout( 2, true ) );
		GridData gridData = new GridData( GridData.FILL_HORIZONTAL );
		// create the lable in the UI
		createLabel( group, SASConstants.DO_DIALOG_CLASS_NEW_NAME_s, gridData );
		// create the TextField in the UI
		createTextField( group, gridData );
		group.setBounds( 25, 25, 380, 120 );

		return parent;
	}

	/**
	 * This method is used to create the lable for the UI
	 * 
	 * @param subParent
	 *            This of type of Composite
	 * @param text
	 *            This contains the string for the label
	 * @param gridData
	 */
	private void createLabel( Composite subParent, String text,
			GridData gridData )
	{
		labelUI = new Label( subParent, SWT.NULL );
		labelUI.setText( text );
	}

	/**
	 * This method is used to create the text field for the UI
	 * 
	 * @param subParent
	 *            This of type Composite
	 * @param gridData
	 */
	private void createTextField( Composite subParent, GridData gridData )
	{
		textUI = new Text( subParent, SWT.BORDER );
		textUI.setLayoutData( gridData );
		textUI.setText( this.className );
		setListeners( );
	}

	/**
	 * This method is used to create a buttons for the UI. It is override from
	 * the super class
	 */
	protected void createButtonsForButtonBar( final Composite parent )
	{

		try
		{// create ok button in the UI
			okButton = createButton( parent, 999, SASConstants.UI_OK_BUTTON_s,
										true );
			okButton.setEnabled( false );
			// create cancel button in the UI
			cancelButton = createButton( parent, 999,
											SASConstants.UI_CANCEL_BUTTON_s,
											true );
			// add a listener for ok button
			okButton.addSelectionListener( new SelectionAdapter( ) {
				public void widgetSelected( SelectionEvent e )
				{
					String className = textUI.getText( );
					DataObjectView.className_s = className;
					parent.getShell( ).close( );
				}
			} );
			// add a listener for cancel button
			cancelButton.addSelectionListener( new SelectionAdapter( ) {

				public void widgetSelected( SelectionEvent e )
				{
					parent.getShell( ).close( );
				}

			} );

		}
		catch( Exception e )
		{

			e.printStackTrace( );
		}
	}

	/**
	 * This method is used to create a modify listener for the textfields and
	 * the combo box to make the ok button disable or enable
	 * 
	 */
	public void setListeners( )
	{

		textUI.addModifyListener( new ModifyListener( ) {
			public void modifyText( ModifyEvent event )
			{
				// check for length of the text it should not be zero
				checkForText( );
				// check for any empty spaces anywhere in the text
				checkForSpace( textUI.getText( ) );

			}
		} );
	}

	/**
	 * This method is used to set the ok button enable or diable. If the length
	 * of the text in the textfield is greater than zero then it checks for
	 * space by calling appropriate method
	 * 
	 */
	private void checkForText( )
	{
		if( ( textUI.getText( ).toString( ).length( ) != 0 ) )
		{
			okButton.setEnabled( true );
		}
		else
		{
			okButton.setEnabled( false );
		}
	}

	/**
	 * This method is used to check for any space in the text if found it makes
	 * the ok button disabled or otherwise enabled
	 * 
	 * @param textValue
	 *            This string contains the text which is entered through the
	 *            textfield
	 */
	private void checkForSpace( String textValue )
	{
		if( null != textValue && ' ' != textValue.charAt( 0 ) )
		{
			boolean spaceIdentifier = textValue.contains( " " );
			if( spaceIdentifier )
			{
				okButton.setEnabled( false );
				setMessage( SASConstants.DO_DIALOG_CLASS_VALID_s );
			}
			else
			{
				setMessage( SASConstants.DO_DIALOG_CLASS_EDIT_MSG_s );
				okButton.setEnabled( true );
			}
		}
		else
		{
			okButton.setEnabled( false );
			setMessage( SASConstants.DO_DIALOG_CLASS_VALID_s );
		}
	}
}
