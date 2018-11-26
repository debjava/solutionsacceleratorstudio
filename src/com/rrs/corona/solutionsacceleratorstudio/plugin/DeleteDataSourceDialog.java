/*******************************************************************************
 * @rrs_start_copyright
 * 
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved. This
 * software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Red Rabbit Software.
 * 
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

package com.rrs.corona.solutionsacceleratorstudio.plugin;

/**
 * @author Debadatta Mishra
 */
import java.util.List;
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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import com.rrs.corona.Sas.DataSourceList;
import com.rrs.corona.Sas.SolutionAdapter;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SolutionAdapterView;

public class DeleteDataSourceDialog extends TitleAreaDialog
{
	/**
	 * Push button for Ok button in the button bar
	 */
	transient private Button	okButton		= null;	// for OK button
	/**
	 * Push button for Cancel button in the button bar
	 */
	transient private Button	cancelButton	= null;	// for cancel button
	Text						commentText;
	/**
	 * Boolean variable to check certain condition
	 */
	boolean						testFlag		= false;
	/**
	 * Combo box for dataSource name
	 */
	transient private Combo		dsName			= null;	// Combo box for
	// datasource name
	/**
	 * Text field for database URL
	 */
	transient private Text		urlText			= null;	// Text field for
	// database URL
	/**
	 * Text field for database user
	 */
	transient private Text		userText		= null;	// Text field for
	// database USER
	/**
	 * Text field for database password
	 */
	transient private Text		passwordText	= null;	// Text field for
	// database pasword
	/**
	 * String Variable to store database user name
	 */
	private transient String	username		= null;	// String to store
	// database username
	/**
	 * String variable to store database URL
	 */
	private transient String	url				= null;	// String to store
	// database url
	/**
	 * String variable to store database password
	 */
	private transient String	password		= null;	// String to store
	// database password
	private transient Button	addBtn;

	/**
	 * Constructor for title area dialog
	 * 
	 * @param parentShell
	 *            of type Shell
	 */
	public DeleteDataSourceDialog( Shell parentShell )
	{
		super( parentShell );
	}

	/**
	 * Method to create a new window widget in the top level shell
	 */
	public void create( )
	{
		super.create( );
		setTitle( "Solution Accelerator Studio" );// Title message of the
		// dialog box
		setMessage( "Select Data Source to delete from the list" );// Message
		// below the
		// title
		// message
	}

	/**
	 * Method to create and return the upper part of the dialog
	 * 
	 * @return parent of type Composite
	 */
	public Control createDialogArea( Composite parent )
	{
		parent.getShell( ).setText( "Delete Data Source" );
		try
		{ // createWorkArea(parent) Method to create the dialog box and its
			// associated controls in the container
			createWorkArea( parent );
			return parent;
		}
		catch( Exception ex )
		{
			final String errMsg = "Exception thrown in Method **createDialogArea()"
					+ "** in class **DeleteDataSourceDialog**";
			SolutionsacceleratorstudioPlugin.getDefault( )
					.logError( errMsg, ex );
			ex.printStackTrace( );
		}
		return parent;
	}

	/**
	 * Method to create button in the button bar in the dialog box
	 */
	protected void createButtonsForButtonBar( final Composite parent )
	{// Method to create OK/CANCEL buttons for dialog box
		testFlag = false;
		try
		{
			okButton = createButton( parent, 999, "OK", true );// OK button
			// creation
			okButton.setEnabled( false );
			cancelButton = createButton( parent, 999, "Cancel", true );// Cancel
			// button
			// creation
			/**
			 * selection listener for okButton
			 */
			okButton.addSelectionListener( new SelectionAdapter( )// Selection
					// listener
					// for
					// okButton
					{
						/**
						 * Method when selection occurs in the control
						 */
						public void widgetSelected( SelectionEvent e )
						{
//							TreeObject[] treeObj = (TreeObject[]) DatabaseViewer.invisibleRoot.getChildren();
//							for(int i=0 ; i<treeObj.length;i++)
//							{
//								TreeParent treeParent = (TreeParent)treeObj[i];
//								System.out.println("I am in DeleteDataSource class *********");
//								System.out.println("treeParent Name ::: "+treeParent.getName());
//								final String dataSourceName = dsName.getText();
//								TreeObject[] dsObject = (TreeObject[])treeParent.getChildren();
//								for(int j=0 ; j<dsObject.length ; j++)
//								{
//									TreeParent dsParent = (TreeParent)dsObject[j];
//									if((dsParent.getName()).equals(dataSourceName))
//									{
//										//System.out.println("DataSource is already in use ......");
//										MessageDialog.openError(new Shell(),"Error"," This datasource is already in use, you can not delete it. ");
//									}
//									else
//									{
//										deleteDsInfo( parent );
//									}
//								}
//							}
							if(checkDsInDatabaseviewer())
							{
								MessageDialog.openError(new Shell(),"Error"," This datasource " +
										"is already in use, you can not delete it. ");
							}
							else if(checkDsInSaViewer(dsName.getText()))
							{
								System.out.println("It is checking............");
								MessageDialog.openError(new Shell(),"Error"," This datasource " +
								"is already in use, you can not delete it. ");
							}
							else
							{
								deleteDsInfo( parent );
							}
							//deleteDsInfo( parent );
						}
					} );
			/**
			 * Selection listener for cancel button
			 */
			cancelButton.addSelectionListener( new SelectionAdapter( )// Listener
					// for
					// Cancel
					// button
					// to
					// close
					// the
					// dialog
					// box
					// when
					// Cancel
					// button
					// is
					// pressed
					{
						/**
						 * Method when a selection occurs in the control
						 */
						public void widgetSelected( SelectionEvent e )
						{
							parent.getShell( ).close( );
						}
					} );
			testFlag = true;
		}
		catch( Exception e )
		{
			testFlag = false;
			e.printStackTrace( );
		}
	}

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
	 * Method to create several User Interface controls in the container
	 * 
	 * @param parent
	 *            of type Composite
	 */
	private void createWorkArea( final Composite parent )
	{// Method to create User Interface dialog box and its associated User
		// Interface controls on the container
		Composite area = new Composite( parent, SWT.NULL ); // First main
		// Composite
		Composite firstGroup = new Composite( area, SWT.NONE ); // 1st group
		firstGroup.setLayout( new GridLayout( 3, true ) ); // lay out of the
		// dialog box
		GridData gridData = new GridData( ); // to show in a grid
		GridData gdData1 = new GridData( GridData.FILL_HORIZONTAL );// It should
		// be
		// initialized
		// first
		createLabel( firstGroup, "Select Data Source ", gridData );// label for
		// dataSource
		createDataSourceCombo( firstGroup, gdData1 );// method to create
		// Combo box for
		// dataSource
		createLabel( firstGroup, "Database URL", gridData );// Method to create
		// a label database
		// URL
		createURLText( firstGroup, gdData1 );// method to create text field
		// for database URL
		createLabel( firstGroup, "User Name", gridData );// Method to create
		// a label for
		// database User
		// Name
		createUserText( firstGroup, gdData1 );// method to create a text field
		// for database user
		createLabel( firstGroup, "Password", gridData );// Method to create a
		// label for database
		// password field
		createPasswordText( firstGroup, gdData1 );// Method to create a text
		// field for database
		// password field
		firstGroup.setBounds( 25, 25, 380, 120 );// It is the outer layout of
		// the dialog box,for any
		// changes in the
		// layout,numeric values in
		// the setBounds method can
		// be changed
	}

	/**
	 * Method to create label in the container
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
	{// Method to create a label
		final Label labelName = new Label( firstGroup, SWT.LEFT );
		labelName.setText( text ); // Label Name of the test field to be
		// displayed
		labelName.setLayoutData( gridData );
	}

	/**
	 * Method create a Combo box for dataSource
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type GridData
	 */
	private void createDataSourceCombo( final Composite firstGroup,
			final GridData gdData1 )
	{// Method to create a Combo box
		dsName = new Combo( firstGroup, SWT.BORDER | SWT.READ_ONLY ); // Creates
		// a
		// Combo
		// box
		gdData1.horizontalSpan = 2;
		dsName.setLayoutData( gdData1 );
		final DsReader dsreader = new DsReader( );// DsReader is an object to
		// parse the XML file
		SolutionAdapter solutionAdapter = dsreader.getSA( );// Method to get
		// SolutionAdapter
		// object
		List dataSourceList = solutionAdapter.getDataSource( );// dataSourceList
		// is an
		// arrayList
		// which
		// contains the
		// list of
		// dataSources
		// All the available dataSources are added to the DataSource Combo box,
		// so that the combox displays the list of dataSources to the user
		for( int i = 0; i < dataSourceList.size( ); i++ )
		{
			DataSourceList dataSource = ( DataSourceList ) dataSourceList
					.get( i );
			dsName.add( ( String ) dataSource.getDataSourceName( ) );// DataSources
			// name
			// is
			// added
			// to
			// the
			// Combo
			// box
			username = dataSource.getUserName( );
			url = dataSource.getURL( );
			password = dataSource.getPassword( );
		}
		/**
		 * Method for modify listener for DataSource Combo box
		 */
		dsName.addModifyListener( new ModifyListener( ) // implementation of
				// Listener,when user
				// chooses a dataSource
				// name user name,
				// password etc will be
				// automatically
				// displayed to the user
				{
					public void modifyText( ModifyEvent e )
					{
						userText.setText( username );
						urlText.setText( url );
						passwordText.setText( password );
						okButton.setEnabled( true ); // if some characters
						// are entered in the
						// text field, OK btn
						// will be enabled.
					}

				} );
	}

	/**
	 * Method to create a text field for database URL
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type Griddata
	 */
	private void createURLText( final Composite firstGroup, GridData gdData1 )
	{// method to create a text field for database URL
		urlText = new Text( firstGroup, SWT.BORDER | SWT.READ_ONLY );
		gdData1 = new GridData( GridData.FILL_HORIZONTAL );
		gdData1.horizontalSpan = 2;
		urlText.setLayoutData( gdData1 );
	}

	/**
	 * Method to create a text field for database User
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type GridData
	 */
	private void createUserText( final Composite firstGroup, GridData gdData1 )
	{// Method to create a text field for database user
		userText = new Text( firstGroup, SWT.BORDER | SWT.READ_ONLY );
		gdData1 = new GridData( GridData.FILL_HORIZONTAL );
		gdData1.horizontalSpan = 2;
		gdData1.horizontalSpan = 2;
		userText.setLayoutData( gdData1 );
	}

	/**
	 * Method to create a text field for database Password
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param gdData1
	 *            of type GridData
	 */
	private void createPasswordText( final Composite firstGroup,
			GridData gdData1 )
	{// Method to create text field for database password
		passwordText = new Text( firstGroup, SWT.BORDER | SWT.READ_ONLY );
		passwordText.setEchoChar( '*' );
		gdData1 = new GridData( GridData.FILL_HORIZONTAL );
		gdData1.horizontalSpan = 2;
		passwordText.setLayoutData( gdData1 );
	}

	/**
	 * Method to delete the DataSource information
	 * 
	 * @param parent
	 *            of type Composite
	 */
	private void deleteDsInfo( final Composite parent )
	{// Method to delete the DataSource name and its associated information
		/**
		 * Boolean variable to obtain a confirmation to delete a dataSource
		 */
		final boolean flag = MessageDialog
				.openConfirm( parent.getShell( ), "Warning",
								"Do you want to delete the data source ?" );
		if( flag == true )
		{
			DsDelete dsdelete = new DsDelete( );// DsDelete object contains
			// logic to delete a dataSource
			final String tempDsName = dsName.getText( );
			dsdelete.deleteDataSource( tempDsName );// deletaDataSource method
			// is used to delete the
			// dataSource name and
			// related information from
			// the XML file
			parent.getShell( ).close( );// Dialog box is closed automatically
			// when dataSource is deleted from the
			// XML file
		}
		else
		{
			// do nothing
		}
	}

	/**
	 * **********************Accesssor and Mutator Methods for JUnit test
	 * cases**************
	 */
	/**
	 * @return dsName
	 */
	public Combo getDsName( )
	{
		return dsName;
	}

	/**
	 * 
	 * @param dsName
	 */
	public void setDsName( Combo dsName )
	{
		this.dsName = dsName;
	}

	/**
	 * 
	 * @return passwordText
	 */
	public Text getPasswordText( )
	{
		return passwordText;
	}

	/**
	 * 
	 * @param passwordText
	 */
	public void setPasswordText( Text passwordText )
	{
		this.passwordText = passwordText;
	}

	/**
	 * 
	 * @return Database URL Text
	 */
	public Text getUrlText( )
	{
		return urlText;
	}

	/**
	 * 
	 * @param urlText
	 */
	public void setUrlText( Text urlText )
	{
		this.urlText = urlText;
	}

	/**
	 * 
	 * @return userText
	 */
	public Text getUserText( )
	{
		return userText;
	}

	/**
	 * 
	 * @param userText
	 */
	public void setUserText( Text userText )
	{
		this.userText = userText;
	}

	/**
	 * 
	 * @return addBtn
	 */
	public Button getAddBtn( )
	{
		return addBtn;
	}

	/**
	 * 
	 * @param addBtn
	 */
	public void setAddBtn( Button addBtn )
	{
		this.addBtn = addBtn;
	}

	/**
	 * @return cancelButton
	 */
	public Button getCancelButton( )
	{
		return cancelButton;
	}

	/**
	 * 
	 * @param cancelButton
	 */
	public void setCancelButton( Button cancelButton )
	{
		this.cancelButton = cancelButton;
	}

	/**
	 * 
	 * @return okButton
	 */
	public Button getOkButton( )
	{
		return okButton;
	}

	/**
	 * 
	 * @param okButton
	 */
	public void setOkButton( Button okButton )
	{
		this.okButton = okButton;
	}
	
	private boolean checkDsInDatabaseviewer()
	{
		boolean checkFlag = false;
		try
		{
			TreeObject[] treeObj = (TreeObject[]) DatabaseViewer.invisibleRoot.getChildren();
			for(int i=0 ; i<treeObj.length;i++)
			{
				TreeParent treeParent = (TreeParent)treeObj[i];
				final String dataSourceName = dsName.getText();
				TreeObject[] dsObject = (TreeObject[])treeParent.getChildren();
				for(int j=0 ; j<dsObject.length ; j++)
				{
					TreeParent dsParent = (TreeParent)dsObject[j];
					if((dsParent.getName()).equals(dataSourceName))
					{
						checkFlag = true;
						//System.out.println("DataSource is already in use ......");
						//MessageDialog.openError(new Shell(),"Error"," This datasource is already in use, you can not delete it. ");
					}
					else
					{
						checkFlag = false;
						//deleteDsInfo( parent );
					}
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkFlag;
	}
	
	private boolean checkDsInSaViewer(final String name_param)
	{
		boolean checkFlag = false;
		try
		{
			//TreeParent treeParent = (TreeParent)SolutionAdapterView.invisibleRoot.getChildren();
			checkFlag = new SolutionAdapterView().checkDsInSaViewer(name_param);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkFlag;
	}

}
