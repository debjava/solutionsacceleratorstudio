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
 * $Id: DataObjectFieldDialog.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/dataobject/DataObjectFieldDialog.java,v $
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

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.rrs.corona.solutionsacceleratorstudio.SASConstants;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to show the input dialog box for field and its type. User
 * can enter the value of the field and its type.
 * <p>
 * This class extends TitleAreaDialog to show the dialog box
 * </p>
 * 
 * 
 * @author Maharajan
 * @author Debadatta Mishra
 * @see com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView
 */

public class DataObjectFieldDialog extends TitleAreaDialog
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
	 * <code>textFieldUI</code> This is a SWT textfield used to enter field
	 * information
	 */
	transient private Text		textFieldUI		= null;
	/**
	 * <code>labelFieldUI</code> This is a SWT label for field
	 */
	transient private Label		labelFieldUI	= null;
	/**
	 * <code>textTypeUI</code> This is a SWT Combo used to show field's type
	 * information
	 */
	transient private Combo		textTypeUI		= null;
	/**
	 * <code>labelTypeUI</code> This is a SWT label for field's type
	 */
	transient private Label		labelTypeUI		= null;
	/**
	 * <code>myRegExp</code> This string contains all the special characters
	 * other than numbers,alphabets etc., used for regular expression
	 */
	transient private String	myRegExp		= "\\W";
	/**
	 * <code>myPattrern</code> This is a Pattern type used for regular
	 * expression
	 */
	transient private Pattern	myPattrern;
	
	/**
	 * An ArrayList containing the list of class field names
	 */
	transient private ArrayList clsFieldNamesList = new ArrayList();
	
	protected Logger logger = Logger.getLogger("DataObjectFieldDialog.class");
	/**
	 * <code>fieldType</code> This string array holds all the possible
	 * premitive types
	 */
	private String				allTypes[]		= new String[] { "String",
			"int", "float", "double", "long", "short", "char", "Boolean",
			"Date"								};

	/**
	 * This constructor is used to intialise myPattern etc.,
	 * 
	 * @param parentShell
	 *            This contains the shell instance
	 */
	public DataObjectFieldDialog( Shell parentShell,ArrayList clsFieldList )
	{
		super( parentShell );
		DataObjectView.groupName_s = null;
		myPattrern = Pattern.compile( myRegExp );
		DataObjectView.fieldName_s = null;
		clsFieldNamesList = clsFieldList;
	}

	/**
	 * This method is used to call the create() method in the super class and
	 * set the title for the dialog box and set the message for the dialog
	 * box.It overrides the supper class method
	 */
	public void create( )
	{
		super.create( );
		setTitle( SASConstants.DO_DIALOG_FIELD_TITLE_s );
		setMessage( SASConstants.DO_DIALOG_FIELD_MSG_s );
	}

	/**
	 * This method is also overrides the super class method used to create UI
	 * with the help of the Composite from the parameter
	 * 
	 * @param parent
	 *            this of type Composite
	 * @return Control which contains the Composite type
	 */
	public Control createDialogArea( Composite parent )
	{
		parent.getShell( ).setText( SASConstants.DO_DIALOG_FIELD_TITLE_s );
		Composite child = new Composite( parent, SWT.NULL );
		Group group = new Group( child, SWT.NONE );
		group.setLayout( new GridLayout( 2, true ) );
		GridData gridData = new GridData( GridData.FILL_HORIZONTAL );
		// this is to create the lable for field name text field
		createLabel( group, SASConstants.DO_DIALOG_FIELD_NAME_s, gridData );
		// this is to create the text field in the UI for field name
		createTextField( group, gridData );
		// this is to create the lable for field type text field
		createTypeLabel( group, SASConstants.DO_DIALOG_FIELD_TYPE_s, gridData );
		// this is to create the text field in the UI for field type
		createTypeTextField( group, gridData );

		group.setBounds( 25, 25, 380, 120 );

		return parent;
	}

	/**
	 * This method is used to create the label for field
	 * 
	 * @param subParent
	 *            This is of type Composite on which lable will be created
	 * @param text
	 *            This string contains the label to be dispayed
	 * @param gridData
	 *            This is of type GridData for layout
	 */
	private void createLabel( Composite subParent, String text,
			GridData gridData )
	{
		labelFieldUI = new Label( subParent, SWT.NULL );
		labelFieldUI.setText( text );
	}

	/**
	 * This method is used to create a textfield for field
	 * 
	 * @param subParent
	 *            This is of type Composite on which textfield will be created
	 * @param gridData
	 *            This is of type GridData for layout
	 */
	private void createTextField( Composite subParent, GridData gridData )
	{
		textFieldUI = new Text( subParent, SWT.BORDER );
		textFieldUI.setLayoutData( gridData );
	}

	/**
	 * This method is used to create a lable for type of the field
	 * 
	 * @param subParent
	 *            This is of type Composite on which label will be created
	 * @param text
	 *            This string contains the label to be dispayed
	 * @param gridData
	 *            This is of type GridData for layout
	 */
	private void createTypeLabel( Composite subParent, String text,
			GridData gridData )
	{
		labelTypeUI = new Label( subParent, SWT.NULL );
		labelTypeUI.setText( text );
	}

	/**
	 * This method is used to create a combo box for the java datatypes
	 * 
	 * @param subParent
	 *            This is of type Composite on which combo box will be created
	 * @param gridData
	 *            This is of type GridData for layout
	 */
	private void createTypeTextField( Composite subParent, GridData gridData )
	{
		textTypeUI = new Combo( subParent, SWT.BORDER | SWT.READ_ONLY );
		textTypeUI.setLayoutData( gridData );
		textTypeUI.setItems( allTypes );
		setListeners( );
	}

	/**
	 * This method is used to create a buttons for the UI. It is override from
	 * the super class
	 */
	protected void createButtonsForButtonBar( final Composite parent )
	{

		try
		{
			// create ok button in the UI
			okButton = createButton( parent, 999, SASConstants.UI_OK_BUTTON_s,
										true );
			okButton.setEnabled( false );
			// create cancel button in the UI
			cancelButton = createButton( parent, 999,
											SASConstants.UI_CANCEL_BUTTON_s,
											true );
			// create listener for ok button
			okButton.addSelectionListener( new SelectionAdapter( ) {
				public void widgetSelected( SelectionEvent e )
				{
					String fieldName = textFieldUI.getText( );
					String typeName = textTypeUI.getText( );
					if( fieldName != null && !"".equals( fieldName )
							&& !" ".equals( fieldName ) )
					{
						DataObjectView.fieldName_s = fieldName;
						if( typeName != null && !"".equals( typeName )
								&& !" ".equals( typeName ) )
						{
							DataObjectView.fieldType_s = typeName;
						}
					}
					parent.getShell( ).close( );
				}

			} );
			// create listener for cancel button
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

		textFieldUI.addModifyListener( new ModifyListener( ) {
			public void modifyText( ModifyEvent event )
			{
				final String fieldName = textFieldUI.getText();
				checkForText( );
				boolean fieldFlag = checkForDuplicate(fieldName.toLowerCase());
				if(fieldName == null || fieldName == "" || fieldName.contains( " " ))
				{
					setMessage("Not a valid field name, it contains blank space , give a different name",IMessageProvider.ERROR);
					okButton.setEnabled(false);
				}
				else if(fieldName.equalsIgnoreCase("int") || fieldName.equalsIgnoreCase("double") ||
						fieldName.equalsIgnoreCase("float") || fieldName.equalsIgnoreCase("short") ||
						fieldName.equalsIgnoreCase("byte") || fieldName.equalsIgnoreCase("char") ||
						fieldName.equalsIgnoreCase("long") || fieldName.equalsIgnoreCase("boolean"))
				{
					setMessage("Not a valid field name, it conflicts with java keyword ,give a different name",IMessageProvider.ERROR);
					okButton.setEnabled(false);
				}
				else if(fieldFlag)
				{
					setMessage("A field with this name already exists,give a different name",IMessageProvider.ERROR);
				}
				else
				{
					setMessage(SASConstants.DO_DIALOG_FIELD_MSG_s);
					okButton.setEnabled(true);
				}
			}
		} );
		textTypeUI.addModifyListener( new ModifyListener( ) {
			public void modifyText( ModifyEvent event )
			{
				checkForText( );
			}
		} );
	}
	
	/**
	 * This method is used to check the occurance of the duplicate field name
	 * in a class
	 * @param fieldName of type String specifying the name of the class field
	 * @return true if a duplicate field name is found
	 */
	private boolean checkForDuplicate(String fieldName)
	{//Method used to check the duplicate field name in a class
		boolean checkFlag = false;
		try
		{
			if(clsFieldNamesList.contains(fieldName))
			{
				checkFlag = true;
			}
			else
			{
				checkFlag = false;
			}
			
		}
		catch(Exception e)
		{
			logger.info("Exception thrown in :::DataObjectFieldDialog.java::: in :::checkForDuplicat() method:::");
			e.printStackTrace();
		}
		return checkFlag;
	}

	/**
	 * This method is used to set the ok button enable or diable. If the length
	 * of the text in the textfield is greater than zero then it checks for
	 * space by calling appropriate method
	 * 
	 */
	private void checkForText( )
	{
		if( ( textFieldUI.getText( ).toString( ).length( ) != 0 ) )
		{
			if( ( textTypeUI.getText( ).toString( ).length( ) != 0 ) )
			{
				okButton.setEnabled( true );
				checkForSpace( textFieldUI.getText( ) );
			}
			else
			{
				okButton.setEnabled( false );
			}
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
				setMessage( SASConstants.DO_DIALOG_FIELD_VALID_s );
			}
			else
			{
				boolean valid = true;
				// This to check with the regular expression
				Matcher myMatcher = myPattrern.matcher( textValue );
				while( myMatcher.find( ) )
				{
					setMessage( SASConstants.DO_DIALOG_FIELD_VALID_s );
					okButton.setEnabled( false );
					valid = false;
					break;
				}
				int vall = textValue.charAt( 0 );
				if( vall < 49 || vall > 59 )
				{
					if( valid )
					{
						setMessage( SASConstants.DO_DIALOG_FIELD_MSG_s );
						okButton.setEnabled( true );
					}
				}
				else
				{
					setMessage( SASConstants.DO_DIALOG_FIELD_VALID_s );
					okButton.setEnabled( false );
				}
			}
		}
		else
		{
			okButton.setEnabled( false );
			setMessage( SASConstants.DO_DIALOG_FIELD_VALID_s );
		}
	}

}
