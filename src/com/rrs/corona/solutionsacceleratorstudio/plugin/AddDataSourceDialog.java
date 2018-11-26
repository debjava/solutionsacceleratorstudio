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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SaveAdapter;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SolutionAdapterView;

public class AddDataSourceDialog extends TitleAreaDialog
{
	/**
	 * Logger to log
	 */
	protected Logger			logger				= Logger
															.getLogger( "AddDataSourceDialog.class" );	// Logger
	// to
	// log
	/**
	 * Push button for ok button
	 */
	transient private Button	okButton			= null;											// For
	// Ok
	// button
	/**
	 * Push button for Cancel button
	 */
	transient private Button	cancelButton		= null;											// for
	// Cancel
	// button
	/**
	 * List box to show all the avilable dataSources
	 */
	private transient List		totalDsList;															// for
	// Data
	// Source
	// List
	// box
	/**
	 * List box for the dataSources selected by the user
	 */
	private transient List		selectedDsList;														// for
	// selected
	// Data
	// Source
	// List
	// box
	/**
	 * Push button for right arrow button
	 */
	private transient Button	rightArrowButton;														// for
	// right
	// arrow
	// button
	/**
	 * Push button for left arrow button
	 */
	private transient Button	leftArrowButton;														// for
	// left
	// arrow
	// Button
	/**
	 * ArrayList containing the list of selected dataSources
	 */
	private transient ArrayList	selectedDsItemList1	= new ArrayList( );								// This
	// arrayList
	// contains
	// the
	// list
	// of
	// selected
	// dataSources
	/**
	 * ArrayList containing list of selected DataSource list items
	 */
	private transient ArrayList	selectedDsitemList	= new ArrayList( );
	/**
	 * Array of String containing the dataSources selected by the user
	 */
	private transient String	selectedDsnameList[];													// It
	// contains
	// an
	// array
	// of
	// datasources
	// selected
	// by
	// the
	// user
	/**
	 * ArryList containing the list of DataSources displayed in the tree view
	 */
	static ArrayList			totalDsitemList		= new ArrayList( );								// This
	// arrayList
	// contains
	// the
	// DataSoures
	// displayed
	// in
	// the
	// tree
	// view
	/**
	 * Boolean variable to check the duplicate item
	 */
	private transient boolean	checkDuplicate		= false;											// a
	// boolean
	// variable
	// to
	// check
	// the
	// occurance
	// of a
	// uplicate
	// value
	private transient boolean	testFlag			= false;
	/**
	 * A String variable to hold the dataSource name that is visible in the
	 * Database viewer
	 */
	private transient String	treeDataSourceName	= null;
	/**
	 * TreeParent to hold an object of type treeParent;
	 */
	TreeParent					tParent				= null;

	/**
	 * Constructor for AddDataSourceDialog
	 * 
	 * @param parentShell
	 *            of type Shell
	 */
	public AddDataSourceDialog( final Shell parentShell )
	{// Constructor
		super( parentShell );
	}

	/**
	 * Method to create window's widget
	 */
	public void create( )
	{
		super.create( );
		setTitle( "Solution Accelerator Studio" );// For title message
		setMessage( "Select Data Source from list" );// Message below the
		// title message
	}

	/**
	 * Method to create a Dialog area
	 * 
	 * @return parent of type Composite
	 */
	public Control createDialogArea( final Composite parent )
	{// Method to create and return upper part of the dialog box
		parent.getShell( ).setText( "Add Data Source" );
		try
		{
			createWorkArea( parent );
			return parent;
		}
		catch( Exception ex )
		{
			final String Error = "Exception thrown in createDialogArea() "
					+ "in class AddDataSourceDialog";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( Error, ex );
			ex.printStackTrace( );
		}
		return parent;
	}

	/**
	 * Method to create buttons for the button bar
	 */
	protected void createButtonsForButtonBar( final Composite parent )
	{// Method to create buttons for the button in the button bar
		testFlag = false;
		try
		{
			okButton = createButton( parent, 999, "OK", true );
			okButton.setEnabled( true );
			cancelButton = createButton( parent, 999, "Cancel", true );
			okButton.addSelectionListener( new SelectionAdapter( ) {
				public void widgetSelected( SelectionEvent e )
				{
					showAllDsInformationInTree( parent );
					// Method to add the dataSource in the SolutionAdapter view
					new SolutionAdapterView( )
							.addDataSourceToSA( treeDataSourceName, tParent );
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
			testFlag = false;
			e.printStackTrace( );
			final String Error = "Exception thrown in createButtonsForButtonBar() "
					+ "in class AddDataSourceDialog";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( Error, e );
		}
	}

	/**
	 * Method to create the work area of the dialog box
	 * 
	 * @param parent
	 */
	private void createWorkArea( final Composite parent )
	{// This method is used to create the required contols
		// on the container of the dialog box and its associated logic
		Composite area = new Composite( parent, SWT.NULL ); // main composite
		Composite firstGroup1 = new Composite( area, SWT.NONE ); // 2nd
		// composite
		firstGroup1.setBounds( 10, 10, 430, 200 );// oundary of the dialog box
		createTotalDsLabel( firstGroup1, "Data Source list" );// Method to
		// create a
		// label for
		// Total
		// DataSources
		createSelectedDsLabel( firstGroup1, "Selected Data Source list   " );// Method
		// to
		// create
		// Selected
		// Datasource
		// label
		createTotalDsList( firstGroup1 );// Method to create a List box for
		// Total DataSource
		createSelectedDsList( firstGroup1 );// Method to create a List box for
		// selected DataSources
		createRightArrowButton( firstGroup1 );// Method to create right arrow
		// button
		createLeftArrowButton( firstGroup1 );// Method to create left arrow
		// button
	}

	/**
	 * ************************************Dialog box functionality
	 * below****************************************
	 */

	/**
	 * Method to create a label for Total DataSource
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param text
	 *            of type String
	 */
	private void createTotalDsLabel( final Composite firstGroup,
			final String text )
	{// This method create a label for total DataSources
		final Label firstLabel = new Label( firstGroup, SWT.LEFT ); // It should
		// be
		// initialized
		// first,
		// First
		// Label
		firstLabel.setText( text ); // Label Name of the test field to be
		// displayed
		firstLabel.setBounds( 20, 10, 100, 20 );
	}

	/**
	 * Method to create a Label for dataSources selected by the user
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param text
	 *            of type String
	 */
	private void createSelectedDsLabel( final Composite firstGroup, String text )
	{// Method to create a label for selected DataSources
		final Label firstLabel = new Label( firstGroup, SWT.LEFT ); // It should
		// be
		// initialized
		// first,
		// First
		// Label
		firstLabel.setText( text ); // Label Name of the test field to be
		// displayed
		firstLabel.setBounds( 275, 10, 130, 20 );
	}

	/**
	 * Method to create a List box to show the list of available dataSources
	 * 
	 * @param firstGroup
	 *            of Type Composite
	 */
	private void createTotalDsList( Composite firstGroup )
	{// This method is used to show the list of available dataSources
		totalDsList = new List( firstGroup, SWT.MULTI | SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL ); // for total no of
		// datasource list
		totalDsList.setBounds( 20, 30, 100, 80 );
		populateDsList( );// Method to show the total dataSource in the List
		// box for total dataSources
	}

	/**
	 * Method to create a List box for the dataSources selected by the user
	 * 
	 * @param firstGroup
	 *            of type Composite
	 */
	private void createSelectedDsList( final Composite firstGroup )
	{// This method is used to show the dataSources selected by the user
		selectedDsList = new List( firstGroup, SWT.MULTI | SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL ); // for selected Datasource
		// List
		selectedDsList.setBounds( 275, 30, 100, 80 );
		populateSelectedDsList( );
	}

	/**
	 * Method to create a push button for right arrow
	 * 
	 * @param firstGroup
	 *            of type Composite
	 */
	private void createRightArrowButton( final Composite firstGroup )
	{// This method is used to create a push button to
		// move the dataSource to the selected dataSource list box
		rightArrowButton = new Button( firstGroup, SWT.PUSH );
		rightArrowButton.setText( ">>" );
		rightArrowButton.setBounds( 170, 50, 50, 20 );
		rightArrowButton.addSelectionListener( new SelectionAdapter( ) {
			public void widgetSelected( SelectionEvent e )
			{
				// action logic for button
				final String selectedDsnameList[] = totalDsList.getSelection( );
				for( int i = 0; i < selectedDsnameList.length; i++ )
				{
					final String selectedItem = selectedDsnameList[i];
					selectedDsItemList1.add( selectedItem );
					selectedDsitemList.add( selectedItem );
					// checkforText();//Method to set the visibility of the ok
					// button

					if( totalDsitemList.contains( selectedItem ) )// duplicate
					// in the
					// selected
					// DataSource
					// list tree
					{
						MessageDialog
								.openWarning(
												firstGroup.getShell( ),
												"Warning",
												"The tree already contains this "
														+ "dataSource, please select a different data source name" );
						selectedDsItemList1.remove( selectedItem );
						selectedDsList.remove( selectedItem );
						totalDsitemList.remove( selectedItem );// new
					}
					// else
					// {
					// // do nothing
					// }

					// checkDuplicate =
					// checkForDuplicates(selectedDsItemList1,selectedItem);//
					// duplicate in the selected DataSource list box
					checkDuplicate = checkForDuplicates( selectedDsitemList,
															selectedItem );
					if( checkDuplicate == false )
					{
						MessageDialog
								.openWarning(
												firstGroup.getShell( ),
												"Warning",
												"Selected list already contains "
														+ "this data source, please select a different data source name" );
						selectedDsItemList1.remove( selectedItem );
						selectedDsitemList.remove( selectedItem );
					}
					else
					{
						selectedDsList.add( selectedItem );
						okButton.setEnabled( true );
					}
					// checkforText();//Method to set the visibility of the ok
					// button
				}
			}
		} );
	}

	/**
	 * Method used to create a push button for left arrow
	 * 
	 * @param firstGroup
	 *            of type Composite
	 */
	private void createLeftArrowButton( final Composite firstGroup )
	{
		leftArrowButton = new Button( firstGroup, SWT.PUSH );
		leftArrowButton.setText( "<<" );
		leftArrowButton.setBounds( 170, 80, 50, 20 );

		leftArrowButton.addSelectionListener( new SelectionAdapter( ) {
			public void widgetSelected( SelectionEvent e )
			{
				// action logic for button
				selectedDsnameList = selectedDsList.getSelection( );
				for( int i = 0; i < selectedDsnameList.length; i++ )
				{
					String selectedItem = selectedDsnameList[i];
					if( checkDsInTreeView( selectedItem ) )
					{
						MessageDialog
								.openWarning(
												firstGroup.getShell( ),
												"Warning",
												"The tree already contains "
														+ "this dataSource, you can not remove from the list." );
					}
					else
					{
						selectedDsList.remove( selectedItem );
						selectedDsItemList1.remove( selectedItem ); // /
						selectedDsitemList.remove( selectedItem );
						// checkforText(); // to enable or disable the okButton
					}

				}
			}
		} );
	}

	/** *************************************************************************************************************** */
	/**
	 * ***** The following method performs the logical operations depending upon
	 * the UI contols handled by the user***
	 */
	/** *************************************************************************************************************** */
	/**
	 * Method used to populate the dataSources to the Total DataSource list box
	 */
	private void populateDsList( )
	{// This method is used to show all the dataSources available to the user
		final DsReader dsreader = new DsReader( );// Object to parse the XML
		// file containing database
		// information
		ArrayList dataSourcenamesList = dsreader.getDataSourceNames( );// This
		// arrayList
		// contains
		// the
		// list
		// of
		// dataSource
		// name
		// from
		// the
		// XML
		// file
		for( int i = 0; i < dataSourcenamesList.size( ); i++ )
		{
			final String dsname = ( String ) dataSourcenamesList.get( i );
			totalDsList.add( ( String ) dsname );// Adding the dataSource
			// names to the list box
		}
	}

	/**
	 * Method to show how many dataSources are present in the tree viewer
	 * 
	 */
	private void populateSelectedDsList( )
	{// First of all it will show how many DataSources are present in the
		// tree viewer
		selectedDsList.removeAll( );
		totalDsitemList.clear( );
		selectedDsitemList.clear( );
		IStructuredSelection select = ( IStructuredSelection ) DatabaseViewer.viewer
				.getSelection( );
		TreeObject table = ( TreeObject ) select.getFirstElement( );
		TreeParent treeParent = ( TreeParent ) table;
		TreeObject[] treeObj = ( TreeObject[] ) treeParent.getChildren( );
		for( int i = 0; i < treeObj.length; i++ )
		{
			selectedDsitemList.add( treeObj[i].getName( ) );
		}

		for( int l = 0; l < selectedDsitemList.size( ); l++ )
		{
			selectedDsList.add( ( String ) selectedDsitemList.get( l ) );
		}
	}

	/**
	 * Method to check the occurance of duplicate values
	 * 
	 * @param selectedDsnameList
	 *            of type ArrayList
	 * @param item
	 *            of type String
	 * @return true if duplicate is found or otherwise false
	 */
	private boolean checkForDuplicates( final ArrayList selectedDsnameList,
			final String item )
	{// This method is used to check the occurance of duplicate items
		// It prevents the user from selecting a duplicate dataSource name
		boolean returnFlag = false;
		final HashSet newHashSet = new HashSet( );
		for( int i = 0; i < selectedDsnameList.size( ); i++ )
		{
			newHashSet.add( selectedDsnameList.get( i ) );
		}

		if( newHashSet.size( ) < selectedDsnameList.size( ) )
		{
			returnFlag = false;
		}
		else if( newHashSet.size( ) == selectedDsnameList.size( ) )
		{
			returnFlag = true;
			okButton.setEnabled( true );
		}
		else
		{
			returnFlag = true;
			okButton.setEnabled( true );
		}
		return returnFlag;
	}

	/**
	 * Method to check wheather a dataSource is already present in the tree view
	 * 
	 * @param treeView_DsName
	 *            of type String
	 * @return true if dataSource is already present in the tree view otherwise
	 *         false
	 */
	private boolean checkDsInTreeView( final String treeView_DsName )
	{// This method is used to check whether a DataSource is already present
		// in the tree view or not
		boolean flag = false;
		IStructuredSelection select = ( IStructuredSelection ) DatabaseViewer.viewer
				.getSelection( );
		TreeObject table = ( TreeObject ) select.getFirstElement( );
		TreeParent treeParent = ( TreeParent ) table;
		TreeObject[] treeObj = ( TreeObject[] ) treeParent.getChildren( );
		for( int i = 0; i < treeObj.length; i++ )
		{
			final String temp_String = treeObj[i].getName( );
			if( temp_String.equals( treeView_DsName ) )
			{
				flag = true;
				break;
			}
			else
			{
				flag = false;
				continue;
			}
		}
		return flag;
	}

	/**
	 * Method to create a progress bar
	 * 
	 * @param shell
	 *            of type Shell
	 * @return a progress bar object
	 */
	private ProgressBar createProgBar( final Shell shell )// ,int maxLimit)
	{// This method is used to create a progress bar
		final ProgressBar progress_bar = new ProgressBar( shell, SWT.CENTER );
		try
		{
			shell.setLayout( new GridLayout( 1, false ) );
			progress_bar.setMinimum( 0 );
			GridData gd1 = new GridData( GridData.FILL_HORIZONTAL );
			gd1.verticalSpan = 12;
			progress_bar.setLayoutData( gd1 );
			shell.setSize( 650, 150 );
		}
		catch( Exception ex )
		{
			ex.printStackTrace( );
			final String Error = "Exception thrown in createProgBar() in class AddDataSourceDialog";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( Error, ex );
		}
		return progress_bar;
	}

	/**
	 * Method used to populate the DataSource information in the tree view
	 * 
	 * @param parent
	 *            of type Composite
	 */
	public void showAllDsInformationInTree( final Composite parent )
	{// This method is used to show the dataSource information in the tree
		// view
		int level = 1;
		parent.getShell( ).close( );
		Shell shell = new Shell( new Shell( ).getDisplay( ), SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL );
		ProgressBar progressBar = createProgBar( shell );// method to create
		// and obtain a
		// progress bar
		// object
		Label progBtn = new Label( shell, SWT.NONE | SWT.CENTER );
		GridData gd = new GridData( GridData.FILL_HORIZONTAL );
		gd.verticalSpan = 4;
		progBtn.setLayoutData( gd );
		shell.open( );

		for( int i = 0; i < selectedDsItemList1.size( ); i++ )
		{
			level = 1;
			final String showDsname = ( String ) selectedDsItemList1.get( i );
			treeDataSourceName = showDsname;
			// logger.info( "Show DSNAme :::::::: >>>> "+showDsname);
			new DsRename( ).setDataSourceVisible( showDsname, "true" );// Method
			// used
			// to
			// set
			// the
			// visibility
			// of
			// the
			// dataSource
			// Name
			TreeParent treeObject = new TreeParent( showDsname );
			tParent = treeObject;
			treeObject.setType( "group" );
			IStructuredSelection iStructuredSelection = ( IStructuredSelection ) DatabaseViewer.viewer
					.getSelection( );
			TreeParent treeParent1 = ( TreeParent ) iStructuredSelection
					.getFirstElement( );
			treeParent1.addChild( treeObject );
			final ArrayList totalTableNames = new DsReader( )
					.getTotalTableNamesOfOneDS( showDsname );
			progressBar.setMaximum( totalTableNames.size( ) );
			shell.setText( "Loading " + showDsname );

			for( int j = 0; j < totalTableNames.size( ); j++ )
			{
				Object object1 = ( Object ) totalTableNames.get( j );
				final DsTableInfo tabInfo = ( DsTableInfo ) object1;
				final String tab_Name = tabInfo.getDsTableName( );
				TreeParent treeParent2 = new TreeParent( tab_Name );
				treeParent2.setDataSourceName( showDsname );
				treeObject.addChild( treeParent2 );
				treeParent2.setType( "table" );
				final ArrayList totalFieldNames_OneTab = new DsReader( )
						.getTotalFieldNamesFromOneTab( showDsname, tab_Name );
				progBtn.setAlignment( SWT.LEFT );
				progBtn.setText( "Loading tables " + tab_Name );
				progressBar.setSelection( level );
				level = level + 1;

				for( int k = 0; k < totalFieldNames_OneTab.size( ); k++ )
				{
					Object fieldObject = ( Object ) totalFieldNames_OneTab
							.get( k );
					final DsFieldInfo dsfieldInfo = ( DsFieldInfo ) fieldObject;
					final String showFieldNType = dsfieldInfo.getDsFieldName( )
							+ "   ::   " + "< " + dsfieldInfo.getDsFieldType( )
							+ " >";
					TreeParent fieldObject11 = new TreeParent( showFieldNType );
					fieldObject11.setDataType( dsfieldInfo.getDsFieldType( ) );
					fieldObject11.setDataSourceName( showDsname );
					fieldObject11.setTableName( treeParent2.getName( ) );
					treeParent2.addChild( fieldObject11 );
					fieldObject11.setType( "field" );
					fieldObject11.setPrimaryKey( dsfieldInfo.isPrimaryKey( ) );// for
					// setting
					// primary
					// key
					// Following lines are used to set the dataSourceName,
					// URL,Username and password to the tree object
					treeObject.setDataSourceName( dsfieldInfo
							.getDataSourceName( ) );
					treeObject.setDbURL( dsfieldInfo.getDataSourceURL( ) );
					treeObject.setDbUserName( dsfieldInfo
							.getDataSourceUserName( ) );
					treeObject.setDbPassword( dsfieldInfo
							.getDataSourcePassword( ) );
				}
			}
			DatabaseViewer.viewer.refresh( treeParent1, false );
			SaveAdapter adObj = new SaveAdapter( );
			SaveAndOpenDataSource saveObj = new SaveAndOpenDataSource( );
			saveObj.saveDatasourceToFile( treeParent1.getChildren( ), adObj
					.getDataSourceType( ) );
		}
		shell.close( );
	}

	/**
	 * *****************************************Method for JUnit Test
	 * cases*******************************************
	 */
	public static ArrayList getTotalDsitemList( )
	{
		return totalDsitemList;
	}

	public static void setTotalDsitemList( final ArrayList totalDsitemList )
	{
		AddDataSourceDialog.totalDsitemList = totalDsitemList;
	}

	public Button getCancelButton( )
	{
		return cancelButton;
	}

	public void setCancelButton( final Button cancelButton )
	{
		this.cancelButton = cancelButton;
	}

	public boolean isCheckDuplicate( )
	{
		return checkDuplicate;
	}

	public void setCheckDuplicate( final boolean checkDuplicate )
	{
		this.checkDuplicate = checkDuplicate;
	}

	public Button getLeftArrowButton( )
	{
		return leftArrowButton;
	}

	public void setLeftArrowButton( final Button leftArrowButton )
	{
		this.leftArrowButton = leftArrowButton;
	}

	public Logger getLogger( )
	{
		return logger;
	}

	public void setLogger( final Logger logger )
	{
		this.logger = logger;
	}

	public Button getOkButton( )
	{
		return okButton;
	}

	public void setOkButton( final Button okButton )
	{
		this.okButton = okButton;
	}

	public Button getRightArrowButton( )
	{
		return rightArrowButton;
	}

	public void setRightArrowButton( Button rightArrowButton )
	{
		this.rightArrowButton = rightArrowButton;
	}

	public ArrayList getSelectedDsitemList( )
	{
		return selectedDsitemList;
	}

	public void setSelectedDsitemList( ArrayList selectedDsitemList )
	{
		this.selectedDsitemList = selectedDsitemList;
	}

	public ArrayList getSelectedDsItemList1( )
	{
		return selectedDsItemList1;
	}

	public void setSelectedDsItemList1( ArrayList selectedDsItemList1 )
	{
		this.selectedDsItemList1 = selectedDsItemList1;
	}

	public List getSelectedDsList( )
	{
		return selectedDsList;
	}

	public void setSelectedDsList( List selectedDsList )
	{
		this.selectedDsList = selectedDsList;
	}

	public String[] getSelectedDsnameList( )
	{
		return selectedDsnameList;
	}

	public void setSelectedDsnameList( String[] selectedDsnameList )
	{
		this.selectedDsnameList = selectedDsnameList;
	}

	public boolean isTestFlag( )
	{
		return testFlag;
	}

	public void setTestFlag( final boolean testFlag )
	{
		this.testFlag = testFlag;
	}

	public List getTotalDsList( )
	{
		return totalDsList;
	}

	public void setTotalDsList( final List totalDsList )
	{
		this.totalDsList = totalDsList;
	}

	/**
	 * @return Returns the treeDataSourceName.
	 */
	public String getTreeDataSourceName( )
	{
		return treeDataSourceName;
	}

	/**
	 * @param treeDataSourceName The treeDataSourceName to set.
	 */
	public void setTreeDataSourceName( final String treeDataSourceName )
	{
		this.treeDataSourceName = treeDataSourceName;
	}

	/**
	 * @return Returns the tParent.
	 */
	public TreeParent getTParent( )
	{
		return tParent;
	}

	/**
	 * @param parent The tParent to set.
	 */
	public void setTParent( TreeParent parent )
	{
		tParent = parent;
	}

}
