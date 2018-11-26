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
 * $Id: DataObjectClassDialog.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/dataobject/DataObjectClassDialog.java,v $
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

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

/**
 * This class is used to show the input dialog box for class. User can change
 * the value of the class name. 
 * <p.This class extends TitleAreaDialog to show the
 * dialog box</p>
 * 
 * 
 * @author Maharajan
 * @author Debadatta Mishra
 * @see com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView
 */

public class DataObjectClassDialog extends TitleAreaDialog
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
	 * An ArrayList containing the list of class names
	 */
	private transient ArrayList classNamesList = new ArrayList();
	
	protected Logger logger = Logger.getLogger("DataObjectClassDialog.class");

	/**
	 * This constructor is used to intialise myPattern etc.,
	 * 
	 * @param parentShell
	 *            This contains the shell instance
	 */
	public DataObjectClassDialog( Shell parentShell,ArrayList classNames )
	{
		super( parentShell );
		myPattrern = Pattern.compile( myRegExp );
		DataObjectView.className_s = null;
		classNamesList = classNames;
	}

	public void create( )
	{
		super.create( );
		setTitle( SASConstants.DO_DIALOG_CLASS_TITLE_s );
		setMessage( SASConstants.DO_DIALOG_CLASS_MSG_s );
	}

	/**
	 * This is a call back method used to create the UI
	 */
	public Control createDialogArea( Composite parent )
	{

		parent.getShell( ).setText( SASConstants.DO_DIALOG_CLASS_TITLE_s );
		Composite child = new Composite( parent, SWT.NULL );
		Group group = new Group( child, SWT.NONE );
		group.setLayout( new GridLayout( 2, true ) );
		GridData gridData = new GridData( GridData.FILL_HORIZONTAL );
		// This is to create the label for the text box
		createLabel( group, SASConstants.DO_DIALOG_CLASS_NAME_s, gridData );
		// This is to create the TextField
		createTextField( group, gridData );
		// set the bound for the group
		group.setBounds( 25, 25, 380, 120 );

		return parent;
	}

	/**
	 * This method is used to create a label in the dialog box
	 * 
	 * @param subParent
	 *            This of type Composite under which lable going to be created
	 * @param text
	 *            This of type String contains the text message for the lable
	 * @param girdData
	 *            This of type GridData
	 */
	private void createLabel( Composite subParent, String text,
			GridData girdData )
	{
		labelUI = new Label( subParent, SWT.NULL );
		labelUI.setText( text );
	}

	/**
	 * This method is used to create a text field in the dialog box
	 * 
	 * @param subParent
	 *            This of type Composite under which text field going to be
	 *            created
	 * @param girdData
	 *            This of type GridData
	 */
	private void createTextField( Composite subParent, GridData gridData )
	{
		textUI = new Text( subParent, SWT.BORDER );
		textUI.setLayoutData( gridData );
		setListeners( );
	}

	protected void createButtonsForButtonBar( final Composite parent )
	{

		try
		{
			// create the ok button
			okButton = createButton( parent, 999, SASConstants.UI_OK_BUTTON_s,
										true );
			okButton.setEnabled( false );
			// create the cancel button
			cancelButton = createButton( parent, 999,
											SASConstants.UI_CANCEL_BUTTON_s,
											true );
			// listener for ok button
			okButton.addSelectionListener( new SelectionAdapter( ) {
				public void widgetSelected( SelectionEvent e )
				{
					String className = textUI.getText( );
					// set the class name
					DataObjectView.className_s = className;
					parent.getShell( ).close( );
				}
			} );
			// listener for cancel button
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
	 * This method is used to set the listeners for the text field
	 * 
	 */
	public void setListeners( )
	{

		textUI.addModifyListener( new ModifyListener( ) {
			public void modifyText( ModifyEvent event )
			{
				final String className = textUI.getText();
				checkForText( );
				checkForSpace( textUI.getText( ) );
				boolean checkClassName = checkForDuplicate(className.toLowerCase());//Method to check the duplicate
				if(className == null || className == "" || className.contains( " " ))
				{
					setMessage("A class should not contain blank space, give a different name",IMessageProvider.ERROR);
					okButton.setEnabled(false);
				}
				else if(checkClassName)
				{
					setMessage("A class with this name already exists, give a different name",IMessageProvider.ERROR);
					okButton.setEnabled(false);
				}
				else
				{
					okButton.setEnabled(true);
				}
			}
		} );
	}

	/**
	 * This method is used to check for empty text
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
	 * This method is used to check for space in the text
	 * 
	 * @param textValue
	 *            This will contains the text value
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
				boolean valid = true;
				Matcher myMatcher = myPattrern.matcher( textValue );
				while( myMatcher.find( ) )
				{
					setMessage( SASConstants.DO_DIALOG_CLASS_VALID_s );
					okButton.setEnabled( false );
					valid = false;
					break;
				}
				int vall = textValue.charAt( 0 );
				if( vall < 49 || vall > 59 )
				{
					if( valid )
					{
						setMessage( SASConstants.DO_DIALOG_CLASS_MSG_s );
						okButton.setEnabled( true );
					}
				}
				else
				{
					setMessage( SASConstants.DO_DIALOG_CLASS_VALID_s );
					okButton.setEnabled( false );
				}
			}
		}
		else
		{
			okButton.setEnabled( false );
			setMessage( SASConstants.DO_DIALOG_CLASS_VALID_s );
		}
	}
	
	/**
	 * This method is used to check the occurance of the duplicate class name
	 * @param className of type String specifying the name of the class
	 * @return true if duplicate class name is found
	 */
	private boolean checkForDuplicate(String className)
	{//Method to check the presence of duplicate class name
		boolean checkFlag = false;
		try
		{
			if(classNamesList.contains(className))
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
			logger.info("Exception thrown in :::DataObjectClassDialog.java::: in :::checkForDuplicate()::: method");
			e.printStackTrace();
		}
		return checkFlag;
	}
}
