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

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
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

/**
 * @author Debadatta Mishra
 *
 */
public class RenameDataSourceDialog extends TitleAreaDialog
{
	/**
	 * Push button for OK button
	 */
	transient private Button	okButton		= null;

	/**
	 * Push button for Cancel button
	 */
	transient private Button	cancelButton	= null;

	Text						commentText;

	/**
	 * Boolean variable to check condition
	 */
	boolean						testFlag		= false;

	/**
	 * Combo box to display the DataSource name
	 */
	transient private Combo		previousDsName	= null;			// name of the Combo field

	/**
	 * Text field to enter the DataSource name
	 */
	transient private Text		newDsNameText	= null;

	Button						addBtn;

	/**
	 * An ArrayList containing the dataSource names
	 */
	ArrayList					dsNames			= new ArrayList( );

	/**
	 * Default Constructor for RenameDataSourceDialog
	 * 
	 * @param parentShell
	 *            of type Shell
	 */
	public RenameDataSourceDialog( Shell parentShell )
	{
		super( parentShell );
	}

	/**
	 * Method to create window's widget
	 */
	public void create( )
	{
		super.create( );
		setTitle( "Solution Accelerator Studio" );// title message of the Dialog
		// box
		setMessage( "Select Data Source to rename" );// Message below the title
		// message
	}

	/**
	 * Method to create and return the upper part of the Dialog area
	 * 
	 * @param parent
	 *            of type Composite
	 */
	public Control createDialogArea( Composite parent )
	{
		parent.getShell( ).setText( "Rename Data Source " );

		try
		{
			createWorkArea( parent );
			return parent;
		}
		catch( Exception ex )
		{
			final String errMsg = "Exception thrown in Method " +
					"**createDialogArea()** in class [ RenameDataSourceDialog.java ]";
			SolutionsacceleratorstudioPlugin.getDefault( )
					.logError( errMsg, ex );
			ex.printStackTrace( );
		}
		return parent;
	}

	/**
	 * Method to create buttons for button bar of the Dialog box
	 */
	protected void createButtonsForButtonBar( final Composite parent )
	{
		testFlag = false;

		try
		{
			okButton = createButton( parent, 999, "OK", true );
			okButton.setEnabled( false );
			cancelButton = createButton( parent, 999, "Cancel", true );

			okButton.addSelectionListener( new SelectionAdapter( ) {
				public void widgetSelected( SelectionEvent e )
				{
					// write logic here
					final String oldDsName = previousDsName.getText( );
					final String newDsName = newDsNameText.getText( );
					final DsRename dsrename = new DsRename( );
					if( !validateRenameTextField( ) )
					{
						MessageDialog
								.openInformation( parent.getShell( ),
													"Message",
													"Please enter the dataSource name to rename" );

					}
					else if( !validateDsComboBox( ) )
					{
						MessageDialog
								.openInformation( parent.getShell( ),
													"Message",
													"Please select a dataSource name to rename" );
					}
					else
					{
						DatabaseViewer.renameDatasource = newDsName;
						dsrename.renameDataSourcename( oldDsName, newDsName );
						parent.getShell( ).close( );
					}

				}
			} );

			cancelButton.addSelectionListener( new SelectionAdapter( ) {

				public void widgetSelected( SelectionEvent e )
				{
					parent.getShell( ).close( );

				}

			} );
			testFlag = true;
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in Method " +
					"**createButtonsForButtonBar()** in Class [ RenameDataSourceDialog.java ]";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			testFlag = false;
			e.printStackTrace( );
		}
	}

	/**
	 * Method to create a Listener
	 * 
	 */
	public void setListeners( )
	{
		testFlag = false;
		commentText.addModifyListener( new ModifyListener( ) {
			public void modifyText( ModifyEvent event )
			{
				checkForText( );
			}
		} );
		testFlag = true;
	}

	public void checkForText( )
	{
		if( ( commentText.getText( ).toString( ).length( ) != 0 ) )
		{
			okButton.setEnabled( true );
		}
		else
		{
			okButton.setEnabled( false );
		}
	}

	/**
	 * Method to create the work area of the dialog box and to place the UI
	 * controls on the Container of the Dialog box
	 * 
	 * @param parent
	 */
	private void createWorkArea( Composite parent )
	{// Method used to place all
		// the required UI controls
		Composite area = new Composite( parent, SWT.NULL );
		Composite firstGroup = new Composite( area, SWT.NONE ); // 1st group
		firstGroup.setLayout( new GridLayout( 3, true ) ); // lay out
		GridData gridData = new GridData( ); // to show in a grid
		createLabel( firstGroup, "Select Data Source", gridData );// Method for
		// label
		createPrevCombo( firstGroup, gridData );// Method to create a Combo box
		createLabel( firstGroup, "New Data Source", gridData );// Method to
		// ceate a label
		createDsText( firstGroup, gridData ); // Method to create a text field
		// firstGroup.setBounds(10, 20, 480, 150);
		firstGroup.setBounds( 25, 25, 380, 120 );// Boundary area of the dialog
		// box
	}

	/**
	 * Method to create a Combo Box to show the list of available DataSources
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gridData1
	 *            of type GridData
	 */
	private void createPrevCombo( final Composite firstGroup, GridData gridData1 )
	{// This
		// method
		// is
		// used
		// to
		// create
		// a
		// Combo
		// box
		// which contains the list of available dataSources
		previousDsName = new Combo( firstGroup, SWT.BORDER | SWT.READ_ONLY ); // Creates
		// a
		// Combo
		// box
		gridData1 = new GridData( GridData.FILL_HORIZONTAL );// It should be
		// initialized first
		gridData1.horizontalSpan = 2;
		previousDsName.setLayoutData( gridData1 );

		try
		{
			final DsReader dsreader = new DsReader( );
			dsNames = dsreader.getDataSourceNames( );
			if( null != dsNames )
			{
				String[] allDs = new String[dsNames.size( )];
				for( int i = 0; i < dsNames.size( ); i++ )
				{
					allDs[i] = ( String ) dsNames.get( i );
				}
				previousDsName.setItems( allDs );
				if( DatabaseViewer.renameDatasource != null )
				{
					previousDsName.setText( DatabaseViewer.renameDatasource );
				}
				else
				{
					// do nothing
				}
			}

		}
		catch( Exception e )
		{
			final String errMsg = "Exception throwns in Method " +
					"**createPrevCombo()** in Class [ RenameDataSourceDialog.java ]";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			e.printStackTrace( );
		}
		previousDsName.addModifyListener( new ModifyListener( ) // implementation
				// of Listener
				{
					public void modifyText( ModifyEvent e )
					{
						okButton.setEnabled( true ); // if some characters are
						// entered in the text
						// field, OK btn will be
						// enabled.
					}
				} );
	}

	/**
	 * Method to create a Text field to enter the new name for the DataSource
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gridData1
	 *            of type GridData
	 */
	private void createDsText( final Composite firstGroup, GridData gridData1 )
	{// This
		// method
		// is
		// used
		// to
		// create
		// a
		// text
		// field
		// where the user will enter the new name for the DataSource
		newDsNameText = new Text( firstGroup, SWT.BORDER );
		gridData1 = new GridData( GridData.FILL_HORIZONTAL );
		gridData1.horizontalSpan = 2;
		newDsNameText.setLayoutData( gridData1 );
		newDsNameText.setFocus( );
		validateNewDsNameText( newDsNameText );
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
	private void createLabel( final Composite firstGroup, final String text,
			final GridData gridData )
	{// Method for label
		final Label previousDsLabelName = new Label( firstGroup, SWT.LEFT ); // It
		// should
		// be
		// initialized
		// first,
		// First
		// Label
		previousDsLabelName.setText( text ); // Label Name of the test field to
		// be displayed
		// previousDsLabelName.setSize(180,50);
		previousDsLabelName.setLayoutData( gridData );
	}

	/**
	 * Method used to validate the text field for new DataSource name
	 * 
	 * @param newDsNameText
	 *            of type Text
	 */
	private void validateNewDsNameText( final Text newDsNameText )
	{
		newDsNameText.addModifyListener( new ModifyListener( ) // implementation
				// of Listener
				{
					public void modifyText( ModifyEvent e )
					{
						if( newDsNameText.getText( ) == ""
								|| newDsNameText.getText( ) == null
								|| newDsNameText.getText( ).contains( " " ) )
						{
							okButton.setEnabled( false );
						}
						else
						{
							okButton.setEnabled( true ); // if some characters
							// are entered in the
							// text field, OK btn
							// will be enabled.
						}
					}

				} );
	}

	/**
	 * Method to check the validation of the DatSource text field
	 * 
	 * @return false if validation fails
	 */
	private boolean validateRenameTextField( )
	{// Method to validate the text
		// field
		boolean textFieldFlag = false;
		final String tmp_textField = newDsNameText.getText( );
		if( tmp_textField == "" || tmp_textField == null
				|| tmp_textField.contains( " " ) )
		{
			textFieldFlag = false;
		}
		else
		{
			textFieldFlag = true;
		}

		return textFieldFlag;
	}

	/**
	 * Method to validate the DataSource Combo box
	 * 
	 * @return false if validation fails
	 */
	private boolean validateDsComboBox( )
	{// Method for validation of the
		// DataSource Combo box
		boolean textFlag = false;
		final String tmp_dsField = previousDsName.getText( );
		if( tmp_dsField == "" || tmp_dsField == null
				|| tmp_dsField.contains( " " ) )
		{
			textFlag = false;
		}
		else
		{
			textFlag = true;
		}
		return textFlag;
	}

	/**
	 * **************************** Accessor and Mutator methods for JUnit test
	 * cases ********************************
	 */

	public Button getAddBtn( )
	{
		return addBtn;
	}

	public void setAddBtn( Button addBtn )
	{
		this.addBtn = addBtn;
	}

	public Button getCancelButton( )
	{
		return cancelButton;
	}

	public void setCancelButton( Button cancelButton )
	{
		this.cancelButton = cancelButton;
	}

	public Text getCommentText( )
	{
		return commentText;
	}

	public void setCommentText( Text commentText )
	{
		this.commentText = commentText;
	}

	public Text getNewDsNameText( )
	{
		return newDsNameText;
	}

	public void setNewDsNameText( Text newDsNameText )
	{
		this.newDsNameText = newDsNameText;
	}

	public Combo getPreviousDsName( )
	{
		return previousDsName;
	}

	public void setPreviousDsName( Combo previousDsName )
	{
		this.previousDsName = previousDsName;
	}

}
