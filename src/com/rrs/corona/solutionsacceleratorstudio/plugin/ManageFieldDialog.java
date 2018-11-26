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
import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
import org.eclipse.swt.widgets.Text;

public class ManageFieldDialog extends TitleAreaDialog
{
	/** main declaration of variables for implementation of logic* */
	/**
	 * Logger to log
	 */
	Logger						logger							= Logger
																		.getLogger( "ManageTableDialog.class" );
	/**
	 * Push button for Ok button
	 */
	transient private Button	okButton						= null;
	// for OK button
	/**
	 * Push button for cancel button
	 */
	transient private Button	cancelButton					= null;											// for
	// cancel
	// button
	/**
	 * List box for total FieldList
	 */
	private transient List		totalFieldList;																	// for
	// Field
	// List
	// box
	/**
	 * List box for selected field list
	 */
	private transient List		selectedFieldList;																	// for
	// selected
	// Data
	// Source
	// List
	// box
	/**
	 * Push button for right arrow button
	 */
	private transient Button	rightArrowButton;																	// for
	// right
	// arrow
	// button
	/**
	 * Push button for left arrow button
	 */
	private transient Button	leftArrowButton;																	// for
	// left
	// arrow
	// button
	/**
	 * Check box for match case
	 */
	private transient Button	matchButtonFieldList			= null;											// for
	// Match
	// Case
	// button
	// for
	// Total
	// DataSource
	// list
	/**
	 * Check box for match whole word button
	 */
	private transient Button	matchWholewordButtonFieldList	= null;											// for
	// Match
	// whole
	// word
	// button
	// for
	// Total
	// tables
	// list
	/**
	 * Text field for filtering the field items
	 */
	private transient Text		fieldFilterText;																	// text
	// field
	// for
	// Filter
	/**
	 * Boolean variable to check the duplicate items
	 */
	boolean						checkDuplicate					= false;											// boolean
	// variable
	// to
	// check
	// the
	// duplicate
	/**
	 * ArrayList containing the list of field items
	 */
	private transient ArrayList	fieldItemList					= new ArrayList( );								// Arraylist
	// containing
	// the
	// list
	// of
	// field
	// items
	/**
	 * ArrayList containing the list of selected field items
	 */
	private transient ArrayList	selectedFieldItemList1			= new ArrayList( );								// ArrayList
	// containing
	// the
	// list
	// of
	// fields
	// selected
	// by
	// the
	// user
	/**
	 * An array of selected field names list
	 */
	private transient String	selectedFieldnameList[];															// Array
	// of
	// field
	// items
	// selected
	// by
	// the
	// user
	/**
	 * Tree viewer table name
	 */
	private transient String	viewerTableName					= DatabaseViewer.treeTableName;					// table
	// name
	// from
	// the
	// tree
	// view
	/**
	 * An ArrayList containing the list of selected field items list
	 */
	private transient ArrayList	onlySelectedListItems			= new ArrayList( );								// this
	// arraylist
	// contains
	// only
	// selected
	// field
	// items
	/**
	 * An Arraylist containg the list of only field names
	 */
	private transient ArrayList	onlyNames						= new ArrayList( );								// newly
	// added
	// feature,
	// it
	// contains
	// only
	// field
	// names
	/**
	 * An ArrayList contains the list of selected or already displayed field
	 * items
	 */
	static ArrayList			selectedFieldItemList			= new ArrayList( );								// This
	// arrayList
	// is
	// used
	// to
	// track
	// the
	// items
	// from
	// the
	// selected
	// List
	/**
	 * DsTableInfo is a plain java bean to store dataSource table information
	 */
	DsTableInfo					tabInfo							= null;

	/**
	 * Constructor for the dialog box
	 * 
	 * @param parentShell
	 *            of type Shell
	 */
	public ManageFieldDialog( Shell parentShell )
	{
		super( parentShell );
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method to create window's widget in the top level shell
	 */
	public void create( )
	{
		super.create( );
		setTitle( "Solution Accelerator Studio" );
		setMessage( "Select fields from list" );
	}

	/**
	 * Method to create and return the upper part of the dialog box
	 * 
	 * @return parent of type Composite
	 */
	public Control createDialogArea( final Composite parent )
	{
		parent.getShell( ).setText( "Manage fields" );
		try
		{
			createWorkArea( parent );// Method to create the dialog box and
			// its associated controls in the
			// container
			return parent;
		}
		catch( Exception ex )
		{
			final String errMsg = "Exception thrown in Method "
					+ "**createDialogArea** in Class [ManageFieldDialog.java]";
			SolutionsacceleratorstudioPlugin.getDefault( )
					.logError( errMsg, ex );
			ex.printStackTrace( );
		}
		return parent;
	}

	/**
	 * Method to create the buttons for the button bar in the dialog box
	 */
	protected void createButtonsForButtonBar( final Composite parent )
	{// Method to create Buttons for the dialog box
		try
		{
			okButton = createButton( parent, 999, "OK", true );
			okButton.setEnabled( true );
			cancelButton = createButton( parent, 999, "Cancel", true );
			okButton.addSelectionListener( new SelectionAdapter( ) {
				public void widgetSelected( SelectionEvent e )
				{
					showAllFieldInformationInTree( parent );// method to display
					// the selected
					// fields in the
					// tree view
				}
			} );
			cancelButton.addSelectionListener( new SelectionAdapter( ) {
				public void widgetSelected( SelectionEvent e )
				{
					parent.getShell( ).close( );
				}
			} );
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in Method "
					+ "**createButtonsForButtonBar()** in Class [ManageFieldDialog.java]";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			e.printStackTrace( );
		}
	}

	/**
	 * Method to for Working area and logic for UserInterface designa and
	 * business logic
	 * 
	 * @param parent
	 *            of type Composite
	 */
	private void createWorkArea( Composite parent )
	{// Method to create the dialog box for manage field and its associated
		// controls on the container
		Composite area = new Composite( parent, SWT.NULL );
		Composite firstGroup1 = new Composite( area, SWT.NONE ); // 1st group
		firstGroup1.setBounds( 10, 10, 430, 200 );// boundary area of the
		// Composite
		createTotalFieldLabel( firstGroup1, "Field list" );// Method for create
		// a label
		createSelectedFieldLabel( firstGroup1, "Selected field list" );// method
		// to
		// create
		// a
		// label
		createTotalFieldList( firstGroup1 );// Method to create total field List
		// box
		createSelectedFieldList( firstGroup1 );// Method to create a selectd
		// field list box
		createRightArrowButton( firstGroup1 );// Method to create a right
		// arrow button
		createLeftArrowButton( firstGroup1 );// Method to create left arrow
		// button
		createTotalFieldFilterLabel( firstGroup1, "Table filter" );// Method to
		// create
		// field
		// filter
		// label
		createFieldFilterText( firstGroup1 );// Method to create a text field
		// for Field filter
		createMatchCaseButtonFieldList( firstGroup1 );// For Match case
		createMatchWholeWordButtonFieldList( firstGroup1 );// For Match whole
		// word
	}

	/**
	 * Method to create a Label
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param text
	 *            of type String
	 */
	private void createTotalFieldLabel( final Composite firstGroup,
			final String text )
	{// This method is used to create a label for Total field
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
	 * Method to create a label
	 * 
	 * @param firstGroup
	 *            fo type Composite
	 * @param text
	 *            of type String
	 */
	private void createSelectedFieldLabel( final Composite firstGroup,
			final String text )
	{// This method is used to create a label Selected field
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
	 * Method to create a label
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * @param text
	 *            of type String
	 */
	private void createTotalFieldFilterLabel( final Composite firstGroup,
			final String text )
	{// This method is used to create a label for Field filter
		final Label firstLabel = new Label( firstGroup, SWT.LEFT ); // It should
		// be
		// initialized
		// first,
		// First
		// Label
		firstLabel.setText( text ); // Label Name of the test field to be
		// displayed
		firstLabel.setBounds( 20, 120, 100, 20 );
	}

	/**
	 * Method to create List box for total field list
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * 
	 */
	private void createTotalFieldList( final Composite firstGroup )
	{// method to create a List box for total field list from a particular
		// table
		totalFieldList = new List( firstGroup, SWT.MULTI | SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL ); // for total no of table
		// list
		totalFieldList.setBounds( 20, 30, 100, 80 );
		populateFieldList( );// Method to populate the field list to the list
		// box
	}

	/**
	 * Method to create the right arrow push button
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * 
	 */
	private void createRightArrowButton( final Composite firstGroup )
	{// Method to create a right arrow button
		rightArrowButton = new Button( firstGroup, SWT.PUSH );
		rightArrowButton.setText( ">>" );
		rightArrowButton.setBounds( 180, 50, 50, 20 );

		rightArrowButton.addSelectionListener( new SelectionAdapter( ) {
			public void widgetSelected( SelectionEvent e )
			{
				// action logic for button
				final String selectedFieldnameList[] = totalFieldList
						.getSelection( );// This array maintains the list of
				// selected items from the list box
				for( int i = 0; i < selectedFieldnameList.length; i++ )
				{
					final String selectedItem = selectedFieldnameList[i];
					selectedFieldItemList1.add( selectedItem );// This
					// arraylist
					// contains all
					// the items
					// selected by
					// the user
					checkforText( );// Method to validate wheather the selected
					// field list contains some items or not,If
					// it contains some data, then Ok button
					// will be enabled otherwise it will be
					// diabaled
					checkDuplicate = checkForDuplicates(
															selectedFieldItemList1,
															selectedItem );// Method
					// to
					// check
					// duplicate
					// items
					if( checkDuplicate == false )
					{
						MessageDialog
								.openWarning(
												firstGroup.getShell( ),
												"Warning",
												"Selected list already contains this field item, please select a different field name" );
						selectedFieldItemList1.remove( selectedItem );// If a
						// duplicate
						// item
						// is
						// found
						// , it
						// will
						// be
						// deleted
						// from
						// the
						// list
					}
					else
					{
						selectedFieldList.add( selectedItem );// This
						// arrayList
						// only contains
						// selected
						// items
						onlySelectedListItems.add( selectedItem );// new
						// enhancement,
						// this list
						// contains
						// only
						// selected
						// unique
						// items
						okButton.setEnabled( true );
					}
					checkforText( );// Method to validate the list box wheather
					// the selected field list contains some
					// items or not,If it contains some data,
					// then Ok button will be enabled otherwise
					// it will be diabaled
				}

			}
		} );
	}

	/**
	 * Method to create the right arrow button
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * 
	 */
	private void createLeftArrowButton( final Composite firstGroup )
	{// Method to create left arrow button
		leftArrowButton = new Button( firstGroup, SWT.PUSH );
		leftArrowButton.setText( "<<" );
		leftArrowButton.setBounds( 180, 80, 50, 20 );
		leftArrowButton.addSelectionListener( new SelectionAdapter( ) {
			public void widgetSelected( SelectionEvent e )
			{
				// action logic for button
				selectedFieldnameList = selectedFieldList.getSelection( );
				for( int i = 0; i < selectedFieldnameList.length; i++ )
				{
					final String selectedItem = selectedFieldnameList[i];
					selectedFieldList.remove( selectedItem );// When user
					// decides to
					// delete an
					// item from the
					// selected list
					// box, it will
					// be deleted
					// from this
					// list
					selectedFieldItemList.remove( selectedItem ); // When user
					// decides
					// to delete
					// an item
					// from the
					// selected
					// list box,
					// it will
					// be
					// deleted
					// from this
					// list
					selectedFieldItemList1.remove( selectedItem );
					checkforText( ); // to enable or disable the okButton
				}
			}
		} );
	}

	/**
	 * Method to select the selected Filed List
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * 
	 */
	private void createSelectedFieldList( final Composite firstGroup )
	{// Method to create list box containing the list of fields selected by
		// the user
		selectedFieldList = new List( firstGroup, SWT.MULTI | SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL ); // for selected Datasource
		// List
		selectedFieldList.setBounds( 293, 30, 100, 80 );
		populateSelectedFieldList( );// Method used to display the list of
		// field items that are already present
		// in the tree view
		// checkforText();
	}

	/**
	 * Method to create a text field for filtering the fields
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * 
	 */
	private void createFieldFilterText( final Composite firstGroup )
	{// Method to create a text field to filter the field
		fieldFilterText = new Text( firstGroup, SWT.BORDER );
		fieldFilterText.setBounds( 20, 140, 100, 18 );

		fieldFilterText.addModifyListener( new ModifyListener( )// listener for
				// fieldFilterText
				{
					public void modifyText( ModifyEvent e )
					{
						final String filterString = fieldFilterText.getText( )
								.toString( );

						if( matchButtonFieldList.getSelection( ) )// When
						// match
						// case
						// check box
						// is
						// selected
						{
							if( matchWholewordButtonFieldList.getSelection( ) )// when
							// both
							// are
							// selected
							{
								logger.info( "Both are selected" );
								totalFieldList.removeAll( );
								populateFieldListWithMatchCase( filterString );
							}
							else
							{
								logger.info( "Match case selected only" );// only
								// match
								// case
								// is
								// selected
								totalFieldList.removeAll( );
								populateFieldListWithMatchCase( filterString );
							}
						}
						else
						{
							if( matchWholewordButtonFieldList.getSelection( ) )// when
							// match
							// whole
							// word
							// is
							// selected
							{
								logger.info( "Whole word selected only" );
								totalFieldList.removeAll( );
								populateFieldListWithWholeword( filterString );
							}
							else
							// when nothing is selected
							{
								logger
										.info( "In filter text listener ....Nothing is  selected" );
								totalFieldList.removeAll( );
								populateFieldListWithoutCase( filterString
										.toLowerCase( ) );// Method to
								// populate the
								// field list to the
								// tree view
							}
						}

					}
				} );
	}

	/**
	 * Method to populated the selected field items to the List box
	 * 
	 * @param filterString
	 *            of type String
	 */
	private void populateFieldListWithoutCase( final String filterString )
	{// Method to add the fields to the list box with no condition
		ArrayList totalFieldNames = onlyNames;

		for( int i = 0; i < totalFieldNames.size( ); i++ )
		{
			final String dsname = ( String ) totalFieldNames.get( i );
			final String temp_dsName = dsname.toLowerCase( );
			if( temp_dsName.contains( filterString ) )
			{
				totalFieldList.add( ( String ) dsname );
			}
		}

	}

	/**
	 * Method to create a match case check box for field List
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * 
	 */
	private void createMatchCaseButtonFieldList( final Composite firstGroup )
	{// method to create a check box for match case button
		matchButtonFieldList = new Button( firstGroup, SWT.CHECK );
		matchButtonFieldList.setText( "Match Case" );
		matchButtonFieldList.setBounds( 20, 160, 100, 20 );

		/** Listener for match case button */
		matchButtonFieldList.addSelectionListener( new SelectionAdapter( ) {
			public void widgetSelected( SelectionEvent e )
			{
				// action logic for match button
				final String filterString = fieldFilterText.getText( );

				if( matchButtonFieldList.getSelection( ) )// when match case
				// check box is
				// selected
				{
					if( matchWholewordButtonFieldList.getSelection( ) )// when
					// both
					// are
					// selected
					{
						logger.info( "Both are selected" );
						totalFieldList.removeAll( );
						populateFieldListWithMatchCase( filterString );
					}
					else
					{
						logger.info( "Match case selected only" );// only
						// match
						// case is
						// selected
						totalFieldList.removeAll( );
						populateFieldListWithMatchCase( filterString );
					}
				}
				else
				{
					if( matchWholewordButtonFieldList.getSelection( ) )// when
					// match
					// whole
					// word
					// is
					// selected
					{
						logger.info( "Whole word selected only" );
						totalFieldList.removeAll( );
						populateFieldListWithWholeword( filterString );
					}
					else
					// when nothing is selected
					{
						logger
								.info( "In match button listener ..... Nothing is  selected" );
						populateFieldListWithoutCase( filterString
								.toLowerCase( ) );// populate the field items
						// to the List box
					}
				}
			}
		} );

	}

	/**
	 * Method to Populate the filterd field items to the List box
	 * 
	 * @param filterString
	 *            of type String
	 */
	private void populateFieldListWithMatchCase( final String filterString )
	{// It will work when apply filter button is pressed
		final ArrayList totalFieldNames = onlyNames;

		for( int i = 0; i < totalFieldNames.size( ); i++ )
		{
			final String fldname = ( String ) totalFieldNames.get( i );
			if( fldname.contains( filterString ) )
			{
				totalFieldList.add( ( String ) fldname );
			}
		}
	}

	/**
	 * Method to create Match whole word check box
	 * 
	 * @param firstGroup
	 *            of type Composite
	 * 
	 */
	public void createMatchWholeWordButtonFieldList( final Composite firstGroup )
	{// Method for creating match whole word button for field list
		matchWholewordButtonFieldList = new Button( firstGroup, SWT.CHECK );
		matchWholewordButtonFieldList.setText( "Match whole word" );
		matchWholewordButtonFieldList.setBounds( 20, 180, 130, 20 );// 20,180,100,20

		matchWholewordButtonFieldList
				.addSelectionListener( new SelectionAdapter( ) {
					public void widgetSelected( SelectionEvent e )
					{
						// action logic for button
						final String filterString = fieldFilterText.getText( );

						if( matchWholewordButtonFieldList.getSelection( ) )// when
						// match
						// whole
						// word
						// check
						// box
						// is
						// selected
						{
							if( matchButtonFieldList.getSelection( ) )// whenboth
							// buttons
							// are
							// selected
							{
								logger.info( "Both selected" );
								totalFieldList.removeAll( );
								populateFieldListWithMatchCase( filterString );// Method
								// to
								// populated
								// the
								// filtered
								// field
								// items
								// to
								// the
								// list
								// box
							}
							else
							{
								logger.info( "Whole word selected only" );
								totalFieldList.removeAll( );
								populateFieldListWithWholeword( filterString );
							}
						}
						else
						{
							if( matchButtonFieldList.getSelection( ) )// when
							// match
							// case
							// is
							// selected
							{
								logger
										.info( "In matchwholewordbutton "
												+ "listener .... Match case selected only" );
								totalFieldList.removeAll( );
								populateFieldListWithMatchCase( filterString );
							}
							else
							{
								logger
										.info( "In matchwholeword listener ..... Nothing is  selected" );
								totalFieldList.removeAll( );
								populateFieldListWithoutCase( filterString
										.toLowerCase( ) );
							}
						}
					}
				} );

	}

	/**
	 * Method to populate the field items to the List box with whole word
	 * condition
	 * 
	 * @param filterString
	 *            of type String
	 */
	private void populateFieldListWithWholeword( final String filterString )
	{// Method to add the field to the list box with wholeword condition
		final ArrayList totalFieldNames = onlyNames;

		for( int i = 0; i < totalFieldNames.size( ); i++ )
		{
			final String fldname = ( String ) totalFieldNames.get( i );

			if( fldname.equalsIgnoreCase( filterString ) )
			{
				totalFieldList.add( ( String ) fldname );
			}
		}
	}

	/**
	 * Method to populate the field items to the tree view
	 * 
	 */
	public void populateFieldList( )
	{// Method to populate the fields to the field List box
		IStructuredSelection select = ( IStructuredSelection ) DatabaseViewer.viewer
				.getSelection( );
		TreeObject objTree = ( TreeObject ) select.getFirstElement( );// it
		// gives
		// the
		// selected
		// tree
		// parent
		// name
		final String selectedObjectName = objTree.getName( );
		logger.info( " Selected table name for field :::>>>  "
				+ selectedObjectName );
		TreeParent mainParent = objTree.getParent( );// it returns the main
		// parent
		final String parentName = mainParent.getName( );
		DsReader dsreader = new DsReader( );
		final ArrayList dataSourcenamesList = dsreader.getDataSourceNames( );

		for( int i = 0; i < dataSourcenamesList.size( ); i++ )
		{
			final String dsname = ( String ) dataSourcenamesList.get( i );

			if( dsname.equals( parentName ) )
			{
				final ArrayList totalTableNames = dsreader
						.getTotalTableNamesOfOneDS( dsname );

				for( int j = 0; j < totalTableNames.size( ); j++ )
				{
					Object object1 = ( Object ) totalTableNames.get( j );
					tabInfo = ( DsTableInfo ) object1;
					final String tab_Name = tabInfo.getDsTableName( );

					if( tab_Name.equals( selectedObjectName ) )
					{
						final ArrayList totalFieldNames_OneTab = dsreader
								.getTotalFieldNamesFromOneTab( dsname, tab_Name );

						for( int k = 0; k < totalFieldNames_OneTab.size( ); k++ )
						{
							TreeFieldBean fieldBean = new TreeFieldBean( );
							Object fieldObject = ( Object ) totalFieldNames_OneTab
									.get( k );
							DsFieldInfo dsfieldInfo = ( DsFieldInfo ) fieldObject;
							final String temp_String = dsfieldInfo
									.getDsFieldName( );
							String[] temp_objNames = temp_String.split( "::" );
							final String firstName = temp_objNames[0].trim( );
							fieldBean.setFieldName( dsfieldInfo
									.getDsFieldName( ) );
							fieldBean.setFieldDataType( dsfieldInfo
									.getDsFieldType( ) );
							fieldBean.setFieldType( "field" );
							fieldBean
									.setPrimaryKey( dsfieldInfo.isPrimaryKey( ) );// setting
							// primary
							// key
							fieldItemList.add( fieldBean );
							onlyNames.add( firstName );
							totalFieldList.add( firstName );
						}
					}
				}
			}
		}

	}

	/**
	 * Method to get all the field items
	 * 
	 * @return an arraylist This feature will be implemented later on, it is on
	 *         hold
	 */
	private ArrayList getFieldItems( )
	{// Method to obtain a list of field items
		final ArrayList newTreeNames = new ArrayList( );

		for( int i = 0; i < fieldItemList.size( ); i++ )
		{
			Object object = ( Object ) fieldItemList.get( i );
			TreeFieldBean fieldbean = ( TreeFieldBean ) object;
			final String localString1 = fieldbean.getFieldName( );
			final String localFirstString = localString1;
			onlySelectedListItems = selectedFieldItemList1;// newly added for
			// testing

			for( int j = 0; j < onlySelectedListItems.size( ); j++ )
			{
				final String newTemp = ( String ) onlySelectedListItems.get( j );
				logger.info( "===========  newTemp  " + newTemp );
				if( newTemp.equals( localFirstString ) )
				{
					newTreeNames.add( fieldbean );
				}
			}
		}
		return newTreeNames;
	}

	/**
	 * Method to check wheather the selected list box contains some data or not.
	 * If it contains some data, then Ok button will be enabled otherwise it
	 * will be disabled
	 * 
	 */
	public void checkforText( )
	{
		if( selectedFieldItemList1.size( ) > 0 )
		{
			okButton.setEnabled( true );
		}
		else
		{
			okButton.setEnabled( false );
		}
	}

	/**
	 * Method to check the occurance of duplicate field items
	 * 
	 * @param selectedFieldnameList
	 * @param item
	 *            of type String
	 * @return true if duplicate is found otherwise false
	 */
	public boolean checkForDuplicates( final ArrayList selectedFieldnameList,
			final String item )
	{// Method to check the duplicate fields when adding to the list box

		boolean returnFlag = false;
		final HashSet newHashSet = new HashSet( );
		for( int i = 0; i < selectedFieldnameList.size( ); i++ )
		{
			newHashSet.add( selectedFieldnameList.get( i ) );
		}

		if( newHashSet.size( ) < selectedFieldnameList.size( ) )
		{
			returnFlag = false;
		}
		else if( newHashSet.size( ) == selectedFieldnameList.size( ) )
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
	 * Method to create a progress bar to load the field items
	 * 
	 * @param shell
	 *            of type Shell
	 * @return a progress bar
	 */
	private ProgressBar createProgBar( final Shell shell )
	{// Method to create a progress bar while loading the field to the
		// tables,int maxLimit
		ProgressBar pb = new ProgressBar( shell, SWT.CENTER );
		try
		{
			shell.setLayout( new GridLayout( 1, false ) );
			pb.setMinimum( 0 );
			GridData gd1 = new GridData( GridData.FILL_HORIZONTAL );
			gd1.verticalSpan = 12;
			pb.setLayoutData( gd1 );
			shell.setSize( 650, 150 );
		}
		catch( Exception ex )
		{
			final String errMsg = "Exception thrown in Method "
					+ "**createprogBar()** in class [ ManageFieldDialog.java ] ";
			SolutionsacceleratorstudioPlugin.getDefault( )
					.logError( errMsg, ex );
			ex.printStackTrace( );
		}
		return pb;
	}

	/**
	 * Method to show all the field items in the tree view
	 * 
	 * @param parent
	 *            of type Composite
	 */
	private void showAllFieldInformationInTree( final Composite parent )
	{// Method to show all the fields to the tree view
		IStructuredSelection select = ( IStructuredSelection ) DatabaseViewer.viewer
				.getSelection( );
		TreeObject table = ( TreeObject ) select.getFirstElement( );
		TreeParent treeParent1 = table.getParent( );
		treeParent1.setType( "group" );// to set for database viewer
		treeParent1.setDataSourceName( tabInfo.getDataSourceName( ) );// to
		// set
		// for
		// database
		// viewer
		treeParent1.setDbURL( tabInfo.getDataSourceURL( ) );// // to set for
		// database viewer
		treeParent1.setDbUserName( tabInfo.getDataSourceUserName( ) );// to
		// set
		// for
		// database
		// viewer
		treeParent1.setDbPassword( tabInfo.getDataSourcePassword( ) );// to
		// set
		// for
		// database
		// viewer
		// logger.info( " dataSource URL ::: "+tabInfo.getDataSourceURL());
		logger.info( "Main parent in Manage field =====  "
				+ table.getParent( ).getName( ) );
		logger.info( "table name :::::: " + table.getName( ) );
		TreeParent parentitem = ( TreeParent ) table;
		TreeObject obj[] = parentitem.getChildren( );
		for( int size = 0; size < obj.length; size++ )
		{
			parentitem.removeChild( obj[size] );
		}
		DatabaseViewer.viewer.refresh( );

		int level = 1;
		parent.getShell( ).close( );
		Shell shell = new Shell( new Shell( ).getDisplay( ), SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL );
		ProgressBar pb = createProgBar( shell );
		Label progBtn = new Label( shell, SWT.NONE | SWT.CENTER );
		GridData gd = new GridData( GridData.FILL_HORIZONTAL );
		gd.verticalSpan = 4;
		progBtn.setLayoutData( gd );
		shell.open( );
		level = 1;
		final String showDsname = viewerTableName;
		TreeParent treeObject = ( TreeParent ) table;
		treeObject.setType( "table" );
		final ArrayList totalFieldNames = getFieldItems( );
		pb.setMaximum( totalFieldNames.size( ) );
		shell.setText( "Loading " + showDsname );

		for( int j = 0; j < totalFieldNames.size( ); j++ )
		{
			Object object = ( Object ) totalFieldNames.get( j );
			TreeFieldBean treeBean = ( TreeFieldBean ) object;
			final String field_Name = treeBean.getFieldName( );
			// TreeObject treeParent2 = new TreeObject(field_Name+" :: "+"<
			// "+treeBean.getFieldDataType()+" >");
			TreeParent treeParent2 = new TreeParent( field_Name + "   ::   "
					+ "< " + treeBean.getFieldDataType( ) + " >" );
			treeObject.addChild( treeParent2 );
			treeParent2.setType( "field" );// // to set database viewer
			treeParent2.setPrimaryKey( treeBean.isPrimaryKey( ) );// for
			// setting
			// primary
			// key
			treeParent2.setDataType( treeBean.getFieldDataType( ) );
			treeParent2.setType( treeBean.getFieldType( ) );
			treeParent2.setDataSourceName( tabInfo.getDataSourceName( ) );// setting
			// dataSource
			// name
			treeParent2.setTableName( showDsname );// setting table name
			treeParent2.setParent( treeObject );
			progBtn.setAlignment( SWT.LEFT );
			progBtn.setText( "Loading tables " + field_Name );
			pb.setSelection( level );
			level = level + 1;
		}
		DatabaseViewer.viewer.refresh( treeObject, true );
		shell.close( );
	}

	/**
	 * Method to populate the fields present in the tree view 
	 * to the selected field list 
	 *
	 */
	public void populateSelectedFieldList( )
	{//Method for populating field list
		selectedFieldList.removeAll( );
		selectedFieldItemList1.clear( );
		// Fields will be obtained from the tree viewer not from the XML file
		IStructuredSelection select = ( IStructuredSelection ) DatabaseViewer.viewer
				.getSelection( );
		TreeObject table = ( TreeObject ) select.getFirstElement( );
		TreeParent treeParent = ( TreeParent ) table;
		TreeObject[] treeObj = ( TreeObject[] ) treeParent.getChildren( );
		for( int i = 0; i < treeObj.length; i++ )
		{// Only field names are collected and added to the arraylist
			final String temp_name = ( treeObj[i].getName( ) );
			String[] temp2_name = temp_name.split( "::" );
			final String final_temp_name = temp2_name[0].trim( );
			selectedFieldItemList1.add( final_temp_name );//This arrayList contains only field names not the field types
		}
		for( int j = 0; j < selectedFieldItemList1.size( ); j++ )
		{// It is used to show only the field name in the selected 
			String[] temp_FieldNames = ( ( String ) selectedFieldItemList1
					.get( j ) ).split( "::" );
			final String firstName = temp_FieldNames[0].trim( );
			selectedFieldList.add( firstName );
		}
	}

}
