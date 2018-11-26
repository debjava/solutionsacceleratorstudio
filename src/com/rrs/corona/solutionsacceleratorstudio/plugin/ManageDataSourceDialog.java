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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.rrs.corona.Sas.DataSourceList;
// import com.rrs.corona.Sas.Sas;
import com.rrs.corona.solutionsacceleratorstudio.dataobject.ObjectMapping;

public class ManageDataSourceDialog extends TitleAreaDialog
{
	Logger						logger								= Logger
																			.getLogger( "ManageDataSourceDialog.class" );
	transient private Button	okButton							= null;
	transient private Button	cancelButton						= null;
	private transient Text		commentText;
	private transient boolean	testFlag							= false;
	private transient Button	addBtn;
	private transient Text		dsFilterText;
	private transient Text		dsSelectedFilterText;
	private transient List		totalDsList;																				// for
	// Data
	// Source
	// List
	// box
	private transient List		selectedDsList;																			// for
	// selected
	// Data
	// Source
	// List
	// box
	private transient Button	rightArrowButton;																			// for
	// right
	// arrow
	// button
	private transient Button	leftArrowButton;																			// for
	// left
	// arrow
	// Button
	private transient Button	filterButtonDsList;																		// filter
	// button
	// for
	// DataSource
	// List
	private transient Button	matchButtonDsList					= null;												// for
	// Match
	// Case
	// button
	// for
	// Total
	// DataSource
	// list
	private transient Button	matchButtonSelectedDsList			= null;												// for
	// Match
	// Case
	// button
	// for
	// Selected
	// DataSource
	// list
	private transient Button	matchWholewordButtonDsList			= null;												// for
	// Match
	// whole
	// word
	// button
	// for
	// Total
	// DataSource
	// list
	private transient Button	matchWholewordButtonSelectedDsList	= null;												// for
	// Match
	// whole
	// word
	// button
	// for
	// Total
	// DataSource
	// list
	private transient String	selectedDsnameList[];
	private transient ArrayList	selectedDsItemList1					= new ArrayList( );
	private transient ArrayList	selectedDsitemList					= new ArrayList( );

	static ArrayList			totalDsitemList						= new ArrayList( );
	private transient boolean	checkDuplicate						= false;

	public ManageDataSourceDialog( Shell parentShell )
	{
		super( parentShell );
	}

	public void create( )
	{
		super.create( );
		setTitle( "Solution Accelerator Studio" );
		setMessage( "Select Data Source from list" );
	}

	public Control createDialogArea( final Composite parent )
	{
		parent.getShell( ).setText( "Manage Data Source" );
		try
		{
			createWorkArea( parent );
			return parent;
		}
		catch( Exception ex )
		{
			final String errMsg = "Exception thrown in Method "
					+ "**createDialogArea** in class [ManageDataSourceDialog]";
			SolutionsacceleratorstudioPlugin.getDefault( )
					.logError( errMsg, ex );
			ex.printStackTrace( );
		}
		return parent;
	}

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
					showAllDsInformationInTree( parent );
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
			final String errMsg = "Exception thrown in Method "
					+ "**createButtonsForButtonBar** in class [ManageDataSourceDialog]";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			e.printStackTrace( );
		}
	}

	private void createWorkArea( Composite parent )
	{
		Composite area = new Composite( parent, SWT.NULL );
		Composite firstGroup1 = new Composite( area, SWT.NONE ); // 1st group
		firstGroup1.setBounds( 10, 10, 430, 200 );
		createTotalDsLabel( firstGroup1, "Data Source list" );
		createSelectedDsLabel( firstGroup1, "Selected Data Source list   " );
		createTotalDsList( firstGroup1 );
		createSelectedDsList( firstGroup1 );
		createFirstButton( firstGroup1 );
		createSecondButton( firstGroup1 );
		createTotalDsFilterLabel( firstGroup1, "Data Source filter" );
		createSelectedDsFilterLabel( firstGroup1, "Selected Data Source filter" );
		createDsFilterText( firstGroup1 );
		createSelectedDsFilterText( firstGroup1 );
		createMatchCaseButtonDsList( firstGroup1 );
		createMatchWholeWordButtonDsList( firstGroup1 );// For Match whole word
		createMatchCaseButtonSelectedDsList( firstGroup1 ); // For Match case
		createMatchWholeWordButtonSelectedDsList( firstGroup1 );// For Match
		// whole word
	}

	private void createTotalDsLabel( final Composite firstGroup,
			final String text )
	{
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

	private void createTotalDsList( final Composite firstGroup )
	{
		totalDsList = new List( firstGroup, SWT.MULTI | SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL ); // for total no of
		// datasource list
		totalDsList.setBounds( 20, 30, 100, 80 );
		populateDsList( );
	}

	private void createSelectedDsLabel( final Composite firstGroup,
			final String text )
	{
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

	private void createSelectedDsList( final Composite firstGroup )
	{
		selectedDsList = new List( firstGroup, SWT.MULTI | SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL ); // for selected Datasource
		// List
		selectedDsList.setBounds( 275, 30, 100, 80 );
		populateSelectedDsList( );
	}

	private void createFirstButton( final Composite firstGroup )
	{
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
					String selectedItem = selectedDsnameList[i];
					selectedDsItemList1.add( selectedItem );
					selectedDsitemList.add( selectedItem );
					checkforText( );

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
					else
					{
						// selectedDsItemList1.add(selectedItem);
					}

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
												"Selected list already contains this data source, please select a different data source name" );
						selectedDsItemList1.remove( selectedItem );
						selectedDsitemList.remove( selectedItem );
					}
					else
					{
						selectedDsList.add( selectedItem );
						okButton.setEnabled( true );
					}
					checkforText( );
				}
			}
		} );
	}

	private void createSecondButton( final Composite firstGroup )
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
					final String selectedItem = selectedDsnameList[i];
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
						checkforText( ); // to enable or disable the okButton
					}
					/*
					 * selectedDsList.remove(selectedItem);
					 * selectedDsItemList1.remove(selectedItem); ///
					 * selectedDsitemList.remove(selectedItem); checkforText(); //
					 * to enable or disable the okButton
					 */
				}
			}
		} );
	}

	private void createTotalDsFilterLabel( final Composite firstGroup,
			final String text )
	{
		Label firstLabel = new Label( firstGroup, SWT.LEFT ); // It should be
		// initialized
		// first, First
		// Label
		firstLabel.setText( text ); // Label Name of the test field to be
		// displayed
		firstLabel.setBounds( 20, 120, 100, 20 );
	}

	private void createDsFilterText( final Composite firstGroup )
	{
		dsFilterText = new Text( firstGroup, SWT.BORDER );
		dsFilterText.setBounds( 20, 140, 100, 18 );
		dsFilterText.addModifyListener( new ModifyListener( ) {
			public void modifyText( ModifyEvent e )
			{
				final String filterString = dsFilterText.getText( ).toString( );

				if( matchButtonDsList.getSelection( ) )
				{
					if( matchWholewordButtonDsList.getSelection( ) )// when both
					// are
					// selected
					{
						logger.info( "Both are selected" );
						totalDsList.removeAll( );
						populateDsListWithMatchCase( filterString );
					}
					else
					{
						logger.info( "Match case selected only" );// only
						// match
						// case is
						// selected
						totalDsList.removeAll( );
						populateDsListWithMatchCase( filterString );
					}
				}
				else
				{
					if( matchWholewordButtonDsList.getSelection( ) )// when
					// match
					// whole
					// word is
					// selected
					{
						logger.info( "Whole word selected only" );
						totalDsList.removeAll( );
						populateDsListWithWholeword( filterString );
					}
					else
					// when nothing is selected
					{
						logger.info( "Nothing is  selected" );
						// totalTableList.removeAll();
						populateDsListWithoutCase( filterString.toLowerCase( ) );
					}
				}
			}
		} );
	}

	private void createMatchCaseButtonDsList( final Composite firstGroup )
	{
		matchButtonDsList = new Button( firstGroup, SWT.CHECK );
		matchButtonDsList.setText( "Match Case" );
		matchButtonDsList.setBounds( 20, 160, 100, 20 );
		matchButtonDsList.addSelectionListener( new SelectionAdapter( ) {
			public void widgetSelected( SelectionEvent e )
			{
				// action logic for button
				final String filterString = dsFilterText.getText( );

				if( matchButtonDsList.getSelection( ) )
				{
					if( matchWholewordButtonDsList.getSelection( ) )// when both
					// are
					// selected
					{
						logger.info( "Both are selected" );
						totalDsList.removeAll( );
						populateDsListWithMatchCase( filterString );
					}
					else
					{
						logger.info( "Match case selected only" );// only
						// match
						// case is
						// selected
						totalDsList.removeAll( );
						populateDsListWithMatchCase( filterString );
					}
				}
				else
				{
					if( matchWholewordButtonDsList.getSelection( ) )// when
					// match
					// whole
					// word is
					// selected
					{
						logger.info( "Whole word selected only" );
						totalDsList.removeAll( );
						populateDsListWithWholeword( filterString );
					}
					else
					// when nothing is selected
					{
						logger.info( "Nothing is  selected" );
						populateDsListWithoutCase( filterString.toLowerCase( ) );
					}
				}
			}
		} );
	}

	private void createMatchCaseButtonSelectedDsList( final Composite firstGroup )
	{
		matchButtonSelectedDsList = new Button( firstGroup, SWT.CHECK );
		matchButtonSelectedDsList.setText( "Match Case" );
		matchButtonSelectedDsList.setBounds( 275, 160, 100, 20 ); // 275,160,100,18
		matchButtonSelectedDsList
				.addSelectionListener( new SelectionAdapter( ) {
					public void widgetSelected( SelectionEvent e )
					{
						// action logic for button

						final String filterString = dsSelectedFilterText
								.getText( );

						if( matchButtonSelectedDsList.getSelection( ) )
						{
							if( matchWholewordButtonSelectedDsList
									.getSelection( ) )// when both are
							// selected
							{
								logger.info( "Both are selected" );
								selectedDsList.removeAll( );
								populateSelectedDsListWithMatchCase( filterString );
							}
							else
							{
								logger.info( "Match case selected only" );// only
								// match
								// case
								// is
								// selected
								selectedDsList.removeAll( );
								populateSelectedDsListWithMatchCase( filterString );
							}
						}
						else
						{
							if( matchWholewordButtonSelectedDsList
									.getSelection( ) )// when match whole word
							// is selected
							{
								logger.info( "Whole word selected only" );
								selectedDsList.removeAll( );
								populateSelectedDsListWithWholeword( filterString );
							}
							else
							// when nothing is selected
							{
								logger.info( "Nothing is  selected" );
								// selectedDsList.removeAll();
								populateSelectedDsListWithoutCase( filterString
										.toLowerCase( ) );
							}
						}
					}
				} );
	}

	private void populateSelectedDsListWithMatchCase( final String filterString ) // It
	// will
	// work
	// when
	// apply
	// filter
	// button
	// is
	// pressed
	{

		final ArrayList dataSourcenamesList = selectedDsItemList1;
		final ArrayList temp_list = new ArrayList( );

		for( int i = 0; i < dataSourcenamesList.size( ); i++ )
		{
			final String dsname = ( String ) dataSourcenamesList.get( i );
			if( dsname.contains( filterString ) )
			{
				selectedDsList.add( ( String ) dsname );
				temp_list.add( ( String ) dsname );
			}
		}
		selectedDsItemList1 = temp_list;
	}

	private void createMatchWholeWordButtonDsList( final Composite firstGroup )
	{
		matchWholewordButtonDsList = new Button( firstGroup, SWT.CHECK );
		matchWholewordButtonDsList.setText( "Match whole word" );
		matchWholewordButtonDsList.setBounds( 20, 180, 130, 20 );// 20,180,100,20

		matchWholewordButtonDsList
				.addSelectionListener( new SelectionAdapter( ) {
					public void widgetSelected( SelectionEvent e )
					{
						// action logic for button
						final String filterString = dsFilterText.getText( );

						if( matchWholewordButtonDsList.getSelection( ) )
						{
							if( matchButtonDsList.getSelection( ) )
							{
								logger.info( "Both selected" );
								totalDsList.removeAll( );
								populateDsListWithMatchCase( filterString );
							}
							else
							{
								logger.info( "Whole word selected only" );
								// populateTableListWithWholeword(filterString);
								totalDsList.removeAll( );
								populateDsListWithWholeword( filterString );
							}
						}
						else
						{
							if( matchButtonDsList.getSelection( ) )
							{
								logger.info( "Match case selected only" );
								totalDsList.removeAll( );
								populateDsListWithMatchCase( filterString );
							}
							else
							{
								logger.info( "Nothing is  selected" );
								populateDsListWithoutCase( filterString
										.toLowerCase( ) );
							}
						}

					}
				} );

	}

	private void createMatchWholeWordButtonSelectedDsList(
			final Composite firstGroup )
	{
		matchWholewordButtonSelectedDsList = new Button( firstGroup, SWT.CHECK );
		matchWholewordButtonSelectedDsList.setText( "Match whole word" );
		matchWholewordButtonSelectedDsList.setBounds( 275, 180, 120, 20 );

		matchWholewordButtonSelectedDsList
				.addSelectionListener( new SelectionAdapter( ) {
					public void widgetSelected( SelectionEvent e )
					{
						// action logic for button
						final String filterString = dsSelectedFilterText
								.getText( );

						if( matchWholewordButtonSelectedDsList.getSelection( ) )
						{
							if( matchButtonSelectedDsList.getSelection( ) )
							{
								logger.info( "Both selected" );
								selectedDsList.removeAll( );
								populateSelectedDsListWithMatchCase( filterString );
							}
							else
							{
								logger.info( "Whole word selected only" );
								// populateTableListWithWholeword(filterString);
								selectedDsList.removeAll( );
								populateSelectedDsListWithWholeword( filterString );
							}
						}
						else
						{
							if( matchButtonSelectedDsList.getSelection( ) )
							{
								logger.info( "Match case selected only" );
								selectedDsList.removeAll( );
								populateSelectedDsListWithMatchCase( filterString );
							}
							else
							{
								logger.info( "Nothing is  selected" );
								populateSelectedDsListWithoutCase( filterString
										.toLowerCase( ) );
							}
						}

					}
				} );
	}

	private void populateSelectedDsListWithWholeword( final String filterString )
	{
		final ArrayList dataSourcenamesList = selectedDsItemList1;
		final ArrayList temp_list = new ArrayList( );

		for( int i = 0; i < dataSourcenamesList.size( ); i++ )
		{
			final String dsname = ( String ) dataSourcenamesList.get( i );
			if( dsname.equals( filterString ) )
			{
				selectedDsList.add( ( String ) dsname );
				temp_list.add( ( String ) dsname );
			}
		}
		selectedDsItemList1 = temp_list;
	}

	private void createSelectedDsFilterLabel( final Composite firstGroup,
			final String text )
	{
		final Label firstLabel = new Label( firstGroup, SWT.LEFT ); // It should
		// be
		// initialized
		// first,
		// First
		// Label
		firstLabel.setText( text ); // Label Name of the test field to be
		// displayed
		firstLabel.setBounds( 275, 120, 130, 20 );
	}

	private void createSelectedDsFilterText( final Composite firstGroup )
	{
		dsSelectedFilterText = new Text( firstGroup, SWT.BORDER );
		dsSelectedFilterText.setBounds( 275, 140, 100, 18 );

		dsSelectedFilterText.addModifyListener( new ModifyListener( ) {

			public void modifyText( ModifyEvent e )
			{
				final String filterString = dsSelectedFilterText.getText( )
						.toString( );

				if( matchButtonSelectedDsList.getSelection( ) )
				{
					if( matchWholewordButtonSelectedDsList.getSelection( ) )// when
					// both
					// are
					// selected
					{
						logger.info( "Both are selected" );
						selectedDsList.removeAll( );
						populateSelectedDsListWithMatchCase( filterString );
					}
					else
					{
						logger.info( "Match case selected only" );// only
						// match
						// case is
						// selected
						selectedDsList.removeAll( );
						populateSelectedDsListWithMatchCase( filterString );
					}
				}
				else
				{
					if( matchWholewordButtonSelectedDsList.getSelection( ) )// when
					// match
					// whole
					// word
					// is
					// selected
					{
						logger.info( "Whole word selected only" );
						selectedDsList.removeAll( );
						populateSelectedDsListWithWholeword( filterString );
					}
					else
					// when nothing is selected
					{
						logger.info( "Nothing is  selected" );
						selectedDsList.removeAll( );
						populateSelectedDsListWithoutCase( filterString
								.toLowerCase( ) );
					}
				}
			}
		} );

	}

	private void populateSelectedDsListWithoutCase( final String filterString )
	{
		final ArrayList dataSourcenamesList = selectedDsItemList1;
		final ArrayList temp_list = new ArrayList( );// it will contain only
		// filtered values

		for( int i = 0; i < dataSourcenamesList.size( ); i++ )
		{
			final String dsname = ( String ) dataSourcenamesList.get( i );
			final String temp_dsName = dsname.toLowerCase( );
			if( temp_dsName.contains( filterString ) )
			{
				selectedDsList.add( ( String ) dsname );
				temp_list.add( ( String ) dsname );
				// selectedDsItemList1.add(dsname);// new line inserted
			}
		}
	}

	private void populateDsList( )
	{
		final DsReader dsreader = new DsReader( );
		final ArrayList dataSourcenamesList = dsreader.getDataSourceNames( );
		for( int i = 0; i < dataSourcenamesList.size( ); i++ )
		{
			final String dsname = ( String ) dataSourcenamesList.get( i );
			totalDsList.add( ( String ) dsname );
		}
	}

	private void populateDsListWithMatchCase( final String filterString ) // It
	// will
	// work
	// when
	// apply
	// filter
	// button
	// is
	// pressed
	{
		final DsReader dsreader = new DsReader( );
		final ArrayList dataSourcenamesList = dsreader.getDataSourceNames( );

		for( int i = 0; i < dataSourcenamesList.size( ); i++ )
		{
			final String dsname = ( String ) dataSourcenamesList.get( i );
			if( dsname.contains( filterString ) )
			{
				totalDsList.add( ( String ) dsname );
			}
		}
	}

	private void populateDsListWithoutCase( final String filterString )
	{
		final DsReader dsreader = new DsReader( );
		final ArrayList dataSourcenamesList = dsreader.getDataSourceNames( );
		totalDsList.removeAll( );

		for( int i = 0; i < dataSourcenamesList.size( ); i++ )
		{
			final String dsname = ( String ) dataSourcenamesList.get( i );
			final String temp_dsName = dsname.toLowerCase( );
			if( temp_dsName.contains( filterString ) )
			{
				totalDsList.add( ( String ) dsname );
			}
		}
	}

	private void populateDsListWithWholeword( final String filterString )
	{
		final DsReader dsreader = new DsReader( );
		final ArrayList dataSourcenamesList = dsreader.getDataSourceNames( );

		for( int i = 0; i < dataSourcenamesList.size( ); i++ )
		{
			final String dsname = ( String ) dataSourcenamesList.get( i );
			if( dsname.equalsIgnoreCase( filterString ) )// for match whole
			// word
			{
				totalDsList.add( ( String ) dsname );
			}
		}
	}

	public void checkforText( )
	{
		if( selectedDsList.getItemCount( ) > 0 )
		{
			okButton.setEnabled( true );
		}
		else
		{
			okButton.setEnabled( false );
		}
	}

	private boolean checkForDuplicates( final ArrayList selectedDsnameList,
			final String item )
	{
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
	 * This method is used to show the progress bar
	 * 
	 * @param parent
	 *            of type Composite
	 */
	private void showProgressBar( final Composite parent )
	{
		ProgressBar progressBar = new ProgressBar(
													new Shell( parent
															.getShell( ) ),
													SWT.SMOOTH );
		progressBar.setMinimum( 0 );
		progressBar.setMaximum( 10 );
	}

	/**
	 * This method is used to obtain a progress bar
	 * 
	 * @param shell
	 *            of type Shell
	 * @return an object of ProgressBar
	 */
	private ProgressBar createProgBar( final Shell shell )// ,int maxLimit)
	{
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
		}
		return pb;
	}

	/**
	 * This method is used to show all the dataSource information in a tree view
	 * 
	 * @param parent
	 *            of type Composite
	 */
	public void showAllDsInformationInTree( final Composite parent )
	{
		int level = 1;
		parent.getShell( ).close( );
		final Shell shell = new Shell( new Shell( ).getDisplay( ),
										SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
		final ProgressBar pb = createProgBar( shell );
		final Label progBtn = new Label( shell, SWT.NONE | SWT.CENTER );
		GridData gd = new GridData( GridData.FILL_HORIZONTAL );
		gd.verticalSpan = 4;
		progBtn.setLayoutData( gd );
		shell.open( );

		for( int i = 0; i < selectedDsItemList1.size( ); i++ )
		{
			level = 1;
			final String showDsname = ( String ) selectedDsItemList1.get( i );

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
			treeObject.setType( "group" );
			IStructuredSelection iStructuredSelection = ( IStructuredSelection ) DatabaseViewer.viewer
					.getSelection( );
			TreeParent treeParent1 = ( TreeParent ) iStructuredSelection
					.getFirstElement( );
			treeParent1.addChild( treeObject );
			final ArrayList totalTableNames = new DsReader( )
					.getTotalTableNamesOfOneDS( showDsname );
			pb.setMaximum( totalTableNames.size( ) );
			shell.setText( "Loading " + showDsname );

			for( int j = 0; j < totalTableNames.size( ); j++ )
			{
				Object object1 = ( Object ) totalTableNames.get( j );
				DsTableInfo tabInfo = ( DsTableInfo ) object1;
				final String tab_Name = tabInfo.getDsTableName( );
				TreeParent treeParent2 = new TreeParent( tab_Name );
				treeParent2.setDataSourceName( showDsname );
				treeObject.addChild( treeParent2 );
				treeParent2.setType( "table" );
				final ArrayList totalFieldNames_OneTab = new DsReader( )
						.getTotalFieldNamesFromOneTab( showDsname, tab_Name );
				progBtn.setAlignment( SWT.LEFT );
				progBtn.setText( "Loading tables " + tab_Name );
				pb.setSelection( level );
				level = level + 1;

				for( int k = 0; k < totalFieldNames_OneTab.size( ); k++ )
				{
					Object fieldObject = ( Object ) totalFieldNames_OneTab
							.get( k );
					DsFieldInfo dsfieldInfo = ( DsFieldInfo ) fieldObject;
					final String showFieldNType = dsfieldInfo.getDsFieldName( )
							+ "   ::   " + "< " + dsfieldInfo.getDsFieldType( )
							+ " >";
					TreeParent fieldObject11 = new TreeParent( showFieldNType );
					fieldObject11.setDataType( dsfieldInfo.getDsFieldType( ) );
					fieldObject11.setDataSourceName( showDsname );
					fieldObject11.setTableName( treeParent2.getName( ) );
					treeParent2.addChild( fieldObject11 );
					fieldObject11.setType( "field" );
				}
			}
			DatabaseViewer.viewer.refresh( treeParent1, false );
		}
		shell.close( );
	}

	public ArrayList getSelectedTreeItems( )
	{
		IStructuredSelection select = ( IStructuredSelection ) DatabaseViewer.viewer
				.getSelection( );
		TreeObject table = ( TreeObject ) select.getFirstElement( );
		logger.info( "table :" + table );
		final ArrayList treeArrayList = new ArrayList( );

		TreeParent parentitem = ( TreeParent ) table;
		TreeObject obj[] = parentitem.getChildren( );
		if( obj.length > 0 )
		{
			for( int size = 0; size < obj.length; size++ )
			{
				parentitem.removeChild( obj[size] );
				final String temp_name = obj[size].getName( );
				logger.info( " In selected Tree view dataSource Name :::  "
						+ obj[size].getName( ) );
				treeArrayList.add( temp_name );
			}
		}

		return treeArrayList;
	}

	public void populateSelectedDsList( )
	{
		//		First of all it will show how many DataSources are present in the tree viewer
		selectedDsList.removeAll( );
		totalDsitemList.clear( );
		//selectedDsItemList1.clear();
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

	public boolean checkDsInTreeView( final String treeView_DsName )
	{//This method is used to check whether a DataSource is already present in the tree view or not

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
}
