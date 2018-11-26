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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.ViewPart;
import com.rrs.corona.Sas.DataSourceList;
import com.rrs.corona.Sas.SolutionAdapter;
import com.rrs.corona.sasadapter.DataSourceType;
import com.rrs.corona.solutionsacceleratorstudio.SASImages;
import com.rrs.corona.solutionsacceleratorstudio.dragdrop.DatabaseBean;
import com.rrs.corona.solutionsacceleratorstudio.dragdrop.DatabaseTransfer;
import com.rrs.corona.solutionsacceleratorstudio.dragdrop.DragSourceListenerAbstract;
import com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectTableView;
import com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView;
import com.rrs.corona.solutionsacceleratorstudio.dataobject.EditableTableItem;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SaveAdapter;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SolutionAdapterView;

public class DatabaseViewer extends ViewPart
{
	/**
	 * Logger to log
	 */
	Logger						logger				= Logger
															.getLogger( "Databaseviewer.class" );
	/**
	 * TreeViewer to create a Tree for displaying the DataSource information
	 */
	public static TreeViewer	viewer;
	/**
	 * Right click action to rename a DataSource
	 */
	private Action				renameAction;														// right
																									// click
																									// action
																									// rename
	/**
	 * Right click action to remove a DataSource from the tree view
	 */
	private Action				removeAction;														// right
																									// click
																									// action
																									// hide
	/**
	 * Right click action to refresh the DataSource
	 */
	private Action				refreshAction;														// right
																									// click
																									// action
																									// refresh
	/**
	 * Right click action to add a DataSource in the tree view
	 */
	private Action				addDataSourceAction;												// right
																									// click
																									// action
																									// to
																									// manage
																									// datasource
	/**
	 * Right click action to manage the tables from the tree view
	 */
	private Action				manageTableAction;													// right
																									// click
																									// action
																									// to
																									// manage
																									// table
	/**
	 * Right click action to manage the table fields
	 */
	private Action				manageFieldAction;													// right
																									// click
																									// action
																									// to
																									// manage
																									// fields
	/**
	 * Double click action
	 */
	private Action				doubleClickAction;
	/**
	 * Right click action to copy
	 */
	private Action				copy;
	/**
	 * String variable for selecting the tree node
	 */
	public String				selectedValue;														// for
																									// selecting
																									// the
																									// first
																									// node
	/**
	 * String variable for tree objects
	 */
	public String				treeObjs			= "tree_Objs";
	/**
	 * String variable to hold dataSource name to be renamed
	 */
	public static String		renameDatasource	= null;
	/**
	 * String variable to hold DataSource name
	 */
	public static String		dataSourceName		= null;
	/**
	 * String variable to hold DataSource table name from the tree view
	 */
	public static String		treeTableName		= null;
	/**
	 * ArrayList containg the list of trees
	 */
	public static ArrayList		treeList			= new ArrayList( );
	/**
	 * int varibale to locate position of the selected tree node
	 */
	public static int			findIndex			= 1;
	/**
	 * TreeParent to store the invisble toot node of the tree
	 */
	public static TreeParent	invisibleRoot;
	/**
	 * TreeParent to store the tree noode selected by the user
	 */
	private static TreeParent	selectedItem;

	public static final String	DATASOURCE_s		= "group";
	public static final String	TABLE_s				= "table";
	public static final String	FIELD_s				= "field";
	public static final String	MAPPING_s			= "mapping";
	public static final String	ROOT_s				= "root";
	
	private transient String oldDSName = null;
	

	class ViewContentProvider implements IStructuredContentProvider,
			ITreeContentProvider
	{
		public void inputChanged( Viewer v, Object oldInput, Object newInput )
		{
		}

		/**
		 * Method to dispose the Content provider
		 */
		public void dispose( )
		{
		}

		/**
		 * Returns the elements to display in the viewer
		 */
		public Object[] getElements( Object parent )
		{
			if( parent.equals( getViewSite( ) ) )
			{
				if( invisibleRoot == null )
					initialize( );
				return getChildren( invisibleRoot );
			}
			return getChildren( parent );
		}

		/**
		 * Method to obtain a parent of the given element
		 * 
		 * @return the parent of the given element
		 */
		public Object getParent( Object child )
		{
			if( child instanceof TreeObject )
			{
				return ( ( TreeObject ) child ).getParent( );
			}
			return null;
		}

		/**
		 * Method to obtain a an array of children
		 * 
		 * @return an array of Children
		 */
		public Object[] getChildren( Object parent )
		{
			if( parent instanceof TreeParent )
			{
				return ( ( TreeParent ) parent ).getChildren( );
			}
			return new Object[0];
		}

		/**
		 * Method to check wheather a Parent node has children
		 * 
		 * @return boolean value of true if parent has children,otherwise false
		 */
		public boolean hasChildren( Object parent )
		{
			if( parent instanceof TreeParent )
				return ( ( TreeParent ) parent ).hasChildren( );
			return false;
		}

		/**
		 * Method to initialize the tree
		 * 
		 */
		private void initialize( )
		{
			invisibleRoot = new TreeParent( "" );
			TreeParent newDataSrc = new TreeParent( "Data Source" );// setting
																	// the node
																	// as Data
																	// Source
			invisibleRoot.addChild( newDataSrc );
			newDataSrc.setType( "root" );
		}
	}
	class ViewLabelProvider extends LabelProvider
	{
		public String getText( Object obj )
		{
			return obj.toString( );
		}

		public Image getImage( Object obj )
		{
			Image imageKey = SASImages.getTBFIELD_IMG( );
			if( obj instanceof TreeParent )
			{
				TreeParent objTree = ( TreeParent ) obj;
				if( objTree.getType( ).equals( DatabaseViewer.ROOT_s ) )
				{
					imageKey = SASImages.getDSROOT_IMG( );
				}
				else if( objTree.getType( )
						.equals( DatabaseViewer.DATASOURCE_s ) )
				{
					imageKey = SASImages.getDATASOURCE_IMG( );
				}
				else if( objTree.getType( ).equals( DatabaseViewer.TABLE_s ) )
				{
					imageKey = SASImages.getTABLE_IMG( );
				}
				else if( objTree.getType( ).equals( DatabaseViewer.MAPPING_s ) )
				{
					imageKey = SASImages.getFIELD_IMG( );
				}
			}
			return imageKey;
		}
	}
	class NameSorter extends ViewerSorter
	{
	}

	/**
	 * The constructor for DatabaseViewer
	 */
	public DatabaseViewer( )
	{
	}

	/**
	 * This is a callback that will allow to create the viewer and initialize
	 * it.
	 */

	public void createPartControl( final Composite parent )
	{
		viewer = new TreeViewer( parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL );
		viewer.setContentProvider( new ViewContentProvider( ) );
		viewer.setLabelProvider( new ViewLabelProvider( ) );
		invisibleRoot = new TreeParent( "" );// newly added
		TreeParent newDataSrc = new TreeParent( "Data Source" );// newly added
		invisibleRoot.addChild( newDataSrc ); // newly added
		newDataSrc.setType( "root" );// newly added
		viewer.setInput( invisibleRoot );// newly added
		makeActions( );
		hookContextMenu( );
		contributeToActionBars( );

		viewer.getTree( ).addKeyListener( new KeyListener( ) {
			public void keyPressed( KeyEvent e )
			{
				try
				{
				}
				catch( Exception ex )
				{
					final String errorMsg = "Exception thrown in Method " +
							"**createPartControl()** in class **DataViewer.java**";
					SolutionsacceleratorstudioPlugin.getDefault( )
							.logError( errorMsg, ex );
					ex.printStackTrace( );
				}
			}

			public void keyReleased( KeyEvent e )
			{
			}

		} );
		/** right click menu to enable and disable* */
		viewer.addSelectionChangedListener( new ISelectionChangedListener( ) {
			public void selectionChanged( SelectionChangedEvent event )
			{
				if( SolutionAdapterView.getSolutionsAdapterFlag( ) == false )
				{
					makeAllDisable( );
				}
				else
				{
					doEnableOrDisable( );
				}
			}

		} );

		viewer.addDragSupport( SWT.Move, new Transfer[] { DatabaseTransfer
				.getInstance( ) }, new DragSourceListenerAbstract( ) {

			public void dragStart( DragSourceEvent event )
			{
				event.doit = true;
			}

			public void dragSetData( DragSourceEvent event )
			{
				startDrag( event );
			}
		} );
	}

	public void makeAllDisable( )
	{
		IStructuredSelection select = ( IStructuredSelection ) viewer
				.getSelection( );
		TreeObject obj = ( TreeObject ) select.getFirstElement( );
		if( null != obj )
		{
			addDataSourceAction.setEnabled( false );
			removeAction.setEnabled( false );
			//renameAction.setEnabled( false );
			manageTableAction.setEnabled( false );
			manageFieldAction.setEnabled( false );
			refreshAction.setEnabled( false );
			//copy.setEnabled( false );
		}
	}

	/**
	 * Method to enable or disable the the right click action menu
	 * 
	 */
	public void doEnableOrDisable( )
	{// Method to enable or disable the rifht click action menu
		try
		{
			IStructuredSelection select = ( IStructuredSelection ) viewer
					.getSelection( );
			TreeObject obj = ( TreeObject ) select.getFirstElement( );
			if( null != obj )
			{
				populateTable( ( TreeParent ) obj );
				if( obj.getType( ).equals( "root" ) )
				{// If selection is a root
					TreeParent treeParent = ( TreeParent ) obj;
					if( treeParent.getChildren( ).length > 0 )
					{
						addDataSourceAction.setEnabled( false );
						removeAction.setEnabled( false );
						//renameAction.setEnabled( false );
						manageTableAction.setEnabled( false );
						manageFieldAction.setEnabled( false );
						refreshAction.setEnabled( false );
						//copy.setEnabled( false );
					}
					else
					{
						addDataSourceAction.setEnabled( true );
						removeAction.setEnabled( false );
						//renameAction.setEnabled( false );
						manageTableAction.setEnabled( false );
						manageFieldAction.setEnabled( false );
						refreshAction.setEnabled( false );
						//copy.setEnabled( false );
					}
				}
				else if( obj.getType( ).equals( "group" ) )
				{// if selection is DataSource
					removeAction.setEnabled( true );
					//renameAction.setEnabled( true );
					refreshAction.setEnabled( true );
					manageTableAction.setEnabled( true );
					manageFieldAction.setEnabled( false );
				}
				else if( obj.getType( ).equals( "table" ) )
				{// If selection is table
					removeAction.setEnabled( true );
					//renameAction.setEnabled( false );
					refreshAction.setEnabled( false );
					manageTableAction.setEnabled( false );
					manageFieldAction.setEnabled( true );
				}
				else if( obj.getType( ).equals( "field" ) )
				{// If selection is field
					removeAction.setEnabled( true );
					//renameAction.setEnabled( false );
					refreshAction.setEnabled( false );
					manageTableAction.setEnabled( false );
					manageFieldAction.setEnabled( false );
				}
			}
		}
		catch( Exception e )
		{
			final String errorMsg = "Exception thrown in Method " +
					"**doEnableOrDisable()** in class **DataViewer.java**";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errorMsg,
																		e );
			e.printStackTrace( );
		}
	}

	/**
	 * Method to exapand the collpased tree
	 * 
	 * @param obj
	 *            of type Object
	 */
	public void doExpand( Object obj )
	{
		if( obj instanceof TreeObject )
		{
			DatabaseViewer.viewer.expandToLevel( obj, 1 );
			populateTable( ( TreeParent ) obj );
		}
	}

	/**
	 * Method used to populate the DataSource information in the tree viewer, if
	 * a dataSource is selected from the Solution Adapter view
	 * 
	 * @param dataSourceName
	 *            of type String
	 * @return a TreeParent
	 */
	public Object populateDataSourceFromSA( String dataSourceName )
	{// This method is used to show the dataSource information,
		// when this dataSource is selected from the
		// Solution adapter view
		TreeObject root[] = DatabaseViewer.invisibleRoot.getChildren( );
		TreeParent parent = ( TreeParent ) root[0];
		removeChild( parent );
		TreeParent dsNode = new TreeParent( dataSourceName );
		dsNode.setType( "group" );
		parent.addChild( dsNode );
		showAllDsInformationInTree( dataSourceName, dsNode );
		DatabaseViewer.viewer.refresh( parent, true );
		return dsNode;
	}

	/**
	 * Method used to display the
	 * 
	 * @param DsName
	 *            of type String
	 * @param treeParent11
	 *            of type TreeParent
	 */
	public void showAllDsInformationInTree( final String DsName,
			TreeParent treeParent11 )
	{// This method is used to show all the dataSource information in the
		// tree viewer
		int level = 1;
		Shell shell = new Shell( new Shell( ).getDisplay( ), SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL );
		ProgressBar pb = createProgBar( shell );
		Label progBtn = new Label( shell, SWT.NONE | SWT.CENTER );
		GridData gd = new GridData( GridData.FILL_HORIZONTAL );
		gd.verticalSpan = 4;
		progBtn.setLayoutData( gd );
		shell.open( );
		final String showDsname = DsName;
		final ArrayList totalTableNames = new DsReader( )
				.getTotalTableNamesOfOneDS( showDsname );
		pb.setMaximum( totalTableNames.size( ) );
		shell.setText( "Loading " + showDsname );

		for( int j = 0; j < totalTableNames.size( ); j++ )
		{
			Object object1 = ( Object ) totalTableNames.get( j );
			final DsTableInfo tabInfo = ( DsTableInfo ) object1;
			final String tab_Name = tabInfo.getDsTableName( );
			TreeParent treeParent2 = new TreeParent( tab_Name );
			treeParent2.setDataSourceName( showDsname );
			treeParent11.addChild( treeParent2 );
			treeParent2.setType( "table" );
			final ArrayList totalFieldNames_OneTab = new DsReader( )
					.getTotalFieldNamesFromOneTab( showDsname, tab_Name );
			progBtn.setAlignment( SWT.LEFT );
			progBtn.setText( "Loading tables " + tab_Name );
			pb.setSelection( level );
			level = level + 1;

			for( int k = 0; k < totalFieldNames_OneTab.size( ); k++ )
			{
				Object fieldObject = ( Object ) totalFieldNames_OneTab.get( k );
				DsFieldInfo dsfieldInfo = ( DsFieldInfo ) fieldObject;
				String showFieldNType = dsfieldInfo.getDsFieldName( )
						+ "   ::   " + "< " + dsfieldInfo.getDsFieldType( )
						+ " >";
				
				//for testing
				final boolean pkVal = dsfieldInfo.isPrimaryKey();
				String primarykey = "";
				if(dsfieldInfo.isPrimaryKey())
				{
					primarykey = "PK";
					showFieldNType = showFieldNType+" "+primarykey;
				}
				else
				{
					primarykey = "";
				}
				
				
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

				/** for testing * */
				// following lines are used to set the required dataSource name
				// URL,userName and password for the tree object
				treeParent11
						.setDataSourceName( dsfieldInfo.getDataSourceName( ) );
				treeParent11.setDbURL( dsfieldInfo.getDataSourceURL( ) );
				treeParent11
						.setDbUserName( dsfieldInfo.getDataSourceUserName( ) );
				treeParent11
						.setDbPassword( dsfieldInfo.getDataSourcePassword( ) );

				/** for testing * */
			}
		}
		shell.close( );
	}

	/**
	 * Method used to create a progress bar
	 * 
	 * @param shell
	 *            of type Shell
	 * @return a progress bar object
	 */
	private ProgressBar createProgBar( final Shell shell )// ,int maxLimit)
	{// This method is used to create a progress bar and
		// returns a progress bar object
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
			final String errMsg = "Exception thrown in Method " +
					"**createProgBar()** in class **DatabaseViewer.java**";
			ex.printStackTrace( );
		}
		return pb;
	}

	/**
	 * Method used to remove a child from the tree view
	 * 
	 * @param parent
	 *            of type TreeParent
	 */
	private void removeChild( TreeParent parent )
	{// This method is used to delete the tree nodes from the tree view
		if( parent.hasChildren( ) )
		{
			TreeObject child[] = parent.getChildren( );
			for( int i = 0; i < child.length; i++ )
			{
				parent.removeChild( child[i] );
			}
		}
	}

	public void populateDsInfo( Object treeObj )
	{
		TreeObject root[] = DatabaseViewer.invisibleRoot.getChildren( );
		TreeParent parent = ( TreeParent ) root[0];
		removeChild( parent );
		if( treeObj instanceof TreeParent )
		{
			TreeParent child = ( TreeParent ) treeObj;
			parent.addChild( child );
		}
		DatabaseViewer.viewer.refresh( parent, true );
		DatabaseViewer.viewer.expandToLevel( parent, 1 );
	}

	public void cleanDatabaseView( )
	{
		TreeObject root[] = DatabaseViewer.invisibleRoot.getChildren( );
		TreeParent parent = ( TreeParent ) root[0];
		removeChild( parent );
		DatabaseViewer.viewer.refresh( parent, true );
	}

	/**
	 * This will call the method which populate the datas in the table
	 * 
	 * @param parent
	 *            This of type TreeParent
	 */
	private void populateTable( TreeParent parent )
	{
		if( parent.getType( ).equals( "group" ) )
		{
			// updataTableForgroup(parent,"Data Source");
			updateDataSourcePropertyView( parent, "Data Source" );
		}
		else if( parent.getType( ).equals( "table" ) )
		{
			updataTableForgroup( parent, "Table" );
		}
		else if( parent.getType( ).equals( "field" ) )
		{
			updataTableForgroup( parent, "Columns" );
		}
		else if( parent.getType( ).equals( "root" ) )
		{
			updataTableForgroup( parent, "Data Source" );
		}
	}

	/**
	 * Method to show the the DataSource details in the property view
	 * 
	 * @param parent
	 *            of type TreeParent
	 * @param title
	 *            of type String
	 */
	private void updateDataSourcePropertyView( TreeParent parent,
			final String title )
	{// This method is used to show the dataSource details in the DataObject
		// property view
		IStructuredSelection select = ( IStructuredSelection ) viewer
				.getSelection( );
		TreeObject obj = ( TreeObject ) select.getFirstElement( );
		String selectedItemName = obj.getName( );
		if( DataObjectTableView.CONTENT.length > 0 )
		{
			DataObjectTableView.viewer.remove( DataObjectTableView.CONTENT );
		}
		Table table = DataObjectTableView.viewer.getTable( );
		TableLayout layout = ( TableLayout ) table.getLayout( );
		table.getColumn( 0 ).setText( title + " - " + obj.getDataSourceName( ) );
		table.getColumn( 2 ).setText( "" );
		table.getColumn( 0 ).setWidth( 370 );
		table.getColumn( 2 ).setWidth( 380 );
		table.getColumn( 1 ).setWidth( 0 );
		table.getColumn( 3 ).setWidth( 0 );
		TreeObject children[] = parent.getChildren( );
		Object tableItem[] = new Object[4];
		// EditableTableItem item = new EditableTableItem( "Data Source Name",0,
		// dsName, null );
		EditableTableItem item = new EditableTableItem(
														"Data Source Name",
														0,
														obj.getDataSourceName( ),
														null );
		tableItem[0] = item;
		DataObjectTableView.viewer.add( item );
		DataObjectTableView.viewer.refresh( item );
		EditableTableItem item1 = new EditableTableItem( "Url", 0, obj
				.getDbURL( ), null );
		tableItem[1] = item1;
		DataObjectTableView.viewer.add( item1 );
		DataObjectTableView.viewer.refresh( item1 );
		EditableTableItem item2 = new EditableTableItem( "User Name", 0, obj
				.getDbUserName( ), null );
		tableItem[2] = item2;
		DataObjectTableView.viewer.add( item2 );
		DataObjectTableView.viewer.refresh( item2 );
		EditableTableItem item3 = new EditableTableItem( "Password", 0, obj
				.getDbPassword( ), null );
		tableItem[3] = item3;
		DataObjectTableView.viewer.add( item3 );
		DataObjectTableView.viewer.refresh( item3 );
		DataObjectTableView.CONTENT = tableItem;
		org.eclipse.jface.viewers.CellEditor[] cells = DataObjectTableView.viewer
				.getCellEditors( );
		cells[0].setStyle( SWT.READ_ONLY );
		cells[0].getControl( ).setEnabled( false );
		DataObjectTableView.viewer.setCellEditors( cells );
	}

	/**
	 * This method is used to populate the data in the table
	 * 
	 * @param parent
	 *            This of type TreeParent
	 * @param title
	 *            This string will hold the title of the table
	 */
	private void updataTableForgroup( TreeParent parent, String title )
	{
		// this is to decrease the combo box column
		if( DataObjectTableView.CONTENT.length > 0 )
		{
			DataObjectTableView.viewer.remove( DataObjectTableView.CONTENT );
		}
		Table table = DataObjectTableView.viewer.getTable( );
		TableLayout layout = ( TableLayout ) table.getLayout( );
		table.getColumn( 0 ).setText( title );
		table.getColumn( 2 ).setText( "" );
		table.getColumn( 0 ).setWidth( 370 );
		table.getColumn( 2 ).setWidth( 380 );
		table.getColumn( 1 ).setWidth( 0 );
		table.getColumn( 3 ).setWidth( 0 );
		TreeObject children[] = parent.getChildren( );
		Object tableItem[] = new Object[children.length];
		for( int i = 0; i < children.length; i++ )
		{
			EditableTableItem item = new EditableTableItem( children[i]
					.getName( ), 0, "", children[i] );
			tableItem[i] = item;
			DataObjectTableView.viewer.add( item );
			DataObjectTableView.viewer.refresh( item );
		}
		DataObjectTableView.CONTENT = tableItem;
		org.eclipse.jface.viewers.CellEditor[] cells = DataObjectTableView.viewer
				.getCellEditors( );
		cells[0].setStyle( SWT.READ_ONLY );
		cells[0].getControl( ).setEnabled( false );
		DataObjectTableView.viewer.setCellEditors( cells );

	}

	/**
	 * Method used to create popup menu
	 * 
	 */
	private void hookContextMenu( )
	{
		MenuManager menuMgr = new MenuManager( "#PopupMenu" );
		menuMgr.setRemoveAllWhenShown( true );
		menuMgr.addMenuListener( new IMenuListener( ) {
			public void menuAboutToShow( IMenuManager manager )
			{
				DatabaseViewer.this.fillContextMenu( manager );
			}
		} );
		Menu menu = menuMgr.createContextMenu( viewer.getControl( ) );
		viewer.getControl( ).setMenu( menu );
		getSite( ).registerContextMenu( menuMgr, viewer );
	}

	private void contributeToActionBars( )
	{
		IActionBars bars = getViewSite( ).getActionBars( );
		fillLocalToolBar( bars.getToolBarManager( ) );
	}

	/**
	 * Method used to add all the right click action menu to the Menu Manager
	 * 
	 * @param manager
	 */
	private void fillContextMenu( IMenuManager manager )
	{// Here all the right click action menus are added to the menu manager
		manager.add( addDataSourceAction );
		//manager.add( renameAction );
		manager.add( removeAction );
		manager.add( new Separator( ) );
		manager.add( manageTableAction );
		manager.add( manageFieldAction );
		manager.add( new Separator( ) );
		manager.add( new Separator( ) );
		manager.add( refreshAction );
		//manager.add( copy );
		manager.add( new Separator( IWorkbenchActionConstants.MB_ADDITIONS ) );
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	private void fillLocalToolBar( IToolBarManager manager )
	{
		//manager.add( copy );
	}

	/**
	 * Method to perform the defined actions
	 * 
	 */
	private void makeActions( )
	{
		doRename( );// method to rename the dataSource
		doRemove( );// method to remove the dataSource
		doRefresh( );// Methos to refresh the DataSource
		addDataSource( );// Methods to add DataSource to the tree view
		manageTables( );// for manage tables action
		manageFields( );// for manage fields
		doCopyAction( );// method to perform copy action
	}

	/**
	 * Method used to rename the DataSource
	 * 
	 */
	public void doRename( )
	{// This method is used to rename a DataSource
		renameAction = new Action( ) // for rename action
		{
			public void run( )
			{
				IStructuredSelection select = ( IStructuredSelection ) viewer
						.getSelection( );
				
				TreeObject objTree = ( TreeObject ) select.getFirstElement( );
				oldDSName = objTree.getName();
				System.out.println("Here oldDSName ::: "+oldDSName);
				if( null != objTree )
				{
					renameDatasource = objTree.getName( );
					RenameDataSourceDialog renameDsDialog = new RenameDataSourceDialog(
																						viewer
																								.getControl( )
																								.getShell( ) );
					renameDsDialog.open( );
					objTree.setName( renameDatasource );
					objTree.setDataSourceName( renameDatasource );
					viewer.refresh( objTree, true );
					//final String oldDSName = objTree.getName();
					//System.out.println("OLD NAME ::: "+objTree.getName());
					new SolutionAdapterView().renameDataSource(oldDSName , renameDatasource);
					
					
					
				}
			}
		};
		renameAction.setText( "Rename" );
		renameAction.setToolTipText( "Click here to rename" );
		renameAction.setImageDescriptor( PlatformUI.getWorkbench( )
				.getSharedImages( )
				.getImageDescriptor( ISharedImages.IMG_TOOL_FORWARD ) );
		getViewSite( ).getActionBars( )
				.setGlobalActionHandler( ActionFactory.RENAME.getId( ),
											renameAction );
	}

	/**
	 * Method used to remove the DataSource from the tree view
	 * 
	 */
	public void doRemove( )
	{// This method is used to remove the dataSource from the tree view
		final String cnfrmMsg = "Do you want to remove ?";
		removeAction = new Action( ) // For hide action
		{
			public void run( )
			{
				IStructuredSelection select = ( IStructuredSelection ) viewer
						.getSelection( );
				TreeObject table = ( TreeObject ) select.getFirstElement( );
				TreeParent tableParent = table.getParent( );
				Iterator iterator = select.iterator( );
				final boolean removeFlag = MessageDialog.openConfirm(new Shell(),"Confirmation",cnfrmMsg);
				if(removeFlag)
				{
					
					for( ; iterator.hasNext( ); )
					{
						TreeObject parent = ( TreeObject ) iterator.next( );
						tableParent.removeChild( parent );
						// new SolutionAdapterView().RemoveDsFromSa();//method to
						// remove from solutionAdapter
					}
					if( table.getType( ).equals( "table" )
							|| table.getType( ).equals( "field" )
							|| table.getType( ).equals( "mapping" ) )
					{
						// do nothing, do not delete
					}
					else
					{
						new SolutionAdapterView( ).removeDsFromSa( );// method to
																		// remove
																		// from
																		// solutionAdapter
					}
					
					

				}
				else
				{
					//do nothing
				}

//				
//				for( ; iterator.hasNext( ); )
//				{
//					TreeObject parent = ( TreeObject ) iterator.next( );
//					tableParent.removeChild( parent );
//					// new SolutionAdapterView().RemoveDsFromSa();//method to
//					// remove from solutionAdapter
//				}
//				if( table.getType( ).equals( "table" )
//						|| table.getType( ).equals( "field" )
//						|| table.getType( ).equals( "mapping" ) )
//				{
//					// do nothing, do not delete
//				}
//				else
//				{
//					new SolutionAdapterView( ).removeDsFromSa( );// method to
//																	// remove
//																	// from
//																	// solutionAdapter
//				}
//				
//				
				
				
				viewer.refresh( );
			}
		};
		removeAction.setText( "Remove" );
		removeAction.setToolTipText( "click here to hide the Data Source" );
		getViewSite( ).getActionBars( )
				.setGlobalActionHandler( ActionFactory.DELETE.getId( ),
											removeAction );
		removeAction.setImageDescriptor( PlatformUI.getWorkbench( )
				.getSharedImages( )
				.getImageDescriptor( ISharedImages.IMG_TOOL_DELETE ) );
	}

	/**
	 * Method used to remove the DataSource when that dataSource is removed from
	 * the Solution Adapter
	 * 
	 * @param obj
	 *            of type Object
	 */
	public void removeDsFromSA( Object obj )
	{// This method is used to delete a datasource from
		// Solution Adapter view
		if( obj instanceof TreeParent )
		{
			TreeParent tParent = ( TreeParent ) obj;
			TreeParent root = tParent.getParent( );
			root.removeChild( tParent );
			DatabaseViewer.viewer.refresh( root, true );
		}
	}

	/**
	 * Method used to refresh the dataSource
	 * 
	 */
	public void doRefresh( )
	{// This method is used to refresh the DataSource
		// Refresh means if new tables are added to the database, after
		// refreshing
		// the dataSource will show all the tables and fields in the tree viewer
		refreshAction = new Action( )// for refresh action
		{
			public void run( )
			{
				IStructuredSelection select = ( IStructuredSelection ) viewer
						.getSelection( );
				TreeObject table = ( TreeObject ) select.getFirstElement( );
				logger
						.info( "In refresh action selected dataSource name ::::::::::  "
								+ table.getName( ) );
				if( table.getType( ).equals( "root" ) )
				{
					viewer.collapseAll( );
					viewer.refresh( );
					// showMessage("Data Source refreshed");
				}
				else
				{
					new RefreshDs( ).refreshDs( table.getName( ), table );
					showMessage( "Data Source refreshed" );
				}
			}
		};
		refreshAction.setText( "Refresh" );
		refreshAction.setToolTipText( "Click here to refresh" );
		getViewSite( ).getActionBars( )
				.setGlobalActionHandler( ActionFactory.REFRESH.getId( ),
											refreshAction );
	}

	/**
	 * Method used to add a DataSource in the tree view
	 * 
	 */
	public void addDataSource( )
	{// This method is used to add the dataSource in the tree viewer
		addDataSourceAction = new Action( ) // for manage datasource action
		{
			public void run( )
			{
				AddDataSourceDialog addDsDialog = new AddDataSourceDialog(
																			viewer
																					.getControl( )
																					.getShell( ) );
				addDsDialog.open( );
				addDataSourceAction.setEnabled( false );
			}
		};
		addDataSourceAction.setText( "Add Data Source" );
		addDataSourceAction.setToolTipText( "Click here to add dataSources" );
		addDataSourceAction.setImageDescriptor( PlatformUI.getWorkbench( )
				.getSharedImages( )
				.getImageDescriptor( ISharedImages.IMG_TOOL_NEW_WIZARD ) );
	}

	/**
	 * Method used to manage tables in a particular dataSource
	 * 
	 */
	public void manageTables( )
	{// This method is used to manage the tables in a particular database
		manageTableAction = new Action( ) // for manage table action
		{
			public void run( )
			{
				IStructuredSelection select = ( IStructuredSelection ) viewer
						.getSelection( );
				TreeObject objTree = ( TreeObject ) select.getFirstElement( );
				if( null != objTree )
				{
					dataSourceName = objTree.getName( );
					ManageTableDialog manageTable = new ManageTableDialog(
																			viewer
																					.getControl( )
																					.getShell( ) );
					manageTable.open( );
					viewer.refresh( objTree, true );
				}
			}
		};
		manageTableAction.setText( "Manage Tables" );
		manageTableAction.setToolTipText( "Click here to manage tables" );
	}

	/**
	 * Method used to manage the fields in a database table
	 * 
	 */
	public void manageFields( )
	{// This method is used to manage the fields inside a table
		manageFieldAction = new Action( )// for manage field action
		{
			public void run( )
			{
				IStructuredSelection select = ( IStructuredSelection ) viewer
						.getSelection( );
				TreeObject objTree = ( TreeObject ) select.getFirstElement( );

				if( null != objTree )
				{
					treeTableName = objTree.getName( );
					ManageFieldDialog manageField = new ManageFieldDialog(
																			viewer
																					.getControl( )
																					.getShell( ) );
					manageField.open( );
					// objTree.setName(dataSourceName);
					viewer.refresh( objTree, true );
				}
			}
		};
		manageFieldAction.setText( "Manage Fields" );
		manageFieldAction.setToolTipText( "Click here to manage field" );
	}

	/**
	 * Method used to copy the dataSource
	 * 
	 */
	public void doCopyAction( )
	{// This method is used to copy the dataSource
		copy = new Action( ) {
			public void run( )
			{
				final Clipboard clipboard = new Clipboard( viewer.getControl( )
						.getDisplay( ) );
				final DatabaseTransfer dataTransfer = DatabaseTransfer
						.getInstance( );
				DatabaseBean dataBean = doCopy( );
				clipboard.clearContents( );
				clipboard
						.setContents(
										new Object[] { dataBean },
										new DatabaseTransfer[] { dataTransfer },
										DND.CLIPBOARD );
				clipboard.dispose( );
			}
		};
		// findAction.setAccelerator(SWT.CTRL+'F');
		copy.setText( "Copy" );
		copy.setToolTipText( "Copy" );
		copy.setImageDescriptor( PlatformUI.getWorkbench( ).getSharedImages( )
				.getImageDescriptor( ISharedImages.IMG_TOOL_COPY ) );
	}

	private void hookDoubleClickAction( )
	{
		viewer.addDoubleClickListener( new IDoubleClickListener( ) {
			public void doubleClick( DoubleClickEvent event )
			{
				doubleClickAction.run( );
			}
		} );
	}

	/**
	 * Method used to display the title message in the tree view
	 * 
	 * @param message
	 *            of type String
	 */
	private void showMessage( String message )
	{
		MessageDialog
				.openInformation( viewer.getControl( ).getShell( ),
									"Solutions Accelerator Studio", message );
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus( )
	{
		viewer.getControl( ).setFocus( );
	}

	/**
	 * This method is used to drag datasource,table and field etc.,
	 * 
	 * @param event
	 *            This of type DragSourceEvent
	 */
	public void startDrag( DragSourceEvent event )
	{
		IStructuredSelection select = ( IStructuredSelection ) viewer
				.getSelection( );
		TreeObject objTree = ( TreeObject ) select.getFirstElement( );
		ArrayList listOfTable = new ArrayList( );
		if( objTree.getType( ).equals( "group" ) )
		{
			DatabaseBean groupBean = new DatabaseBean( );
			TreeParent objTreeParent = ( TreeParent ) select.getFirstElement( );
			groupBean.setGroupName( objTreeParent.getName( ) );
			TreeObject[] tableTree = objTreeParent.getChildren( );
			for( int count = 0; count < tableTree.length; count++ )
			{
				DatabaseBean dataBean = new DatabaseBean( );
				ArrayList fieldList = new ArrayList( );
				HashMap fieldType = new HashMap( );
				TreeParent parent = ( TreeParent ) tableTree[count];
				dataBean.setTableName( parent.getName( ) );
				TreeObject[] child = parent.getChildren( );
				for( int i = 0; i < child.length; i++ )
				{
					fieldList.add( child[i].getName( ) );
					fieldType
							.put( child[i].getName( ), child[i].getDataType( ) );
				}
				dataBean.setFieldMap( fieldList );
				dataBean.setFieldType( fieldType );
				// dataBean.setTypeofData("group");
				listOfTable.add( dataBean );
			}
			groupBean.setTypeofData( "group" );
			groupBean.setAllTables( listOfTable );
			event.data = groupBean;
		}
		else if( objTree.getType( ).equals( "table" ) )
		{
			DatabaseBean dataBean = new DatabaseBean( );
			ArrayList fieldList = new ArrayList( );
			HashMap fieldType = new HashMap( );
			TreeParent objTreeParent = ( TreeParent ) select.getFirstElement( );
			dataBean.setTableName( objTreeParent.getName( ) );
			TreeObject[] child = objTreeParent.getChildren( );
			for( int i = 0; i < child.length; i++ )
			{
				fieldList.add( child[i].getName( ) );
				fieldType.put( child[i].getName( ), child[i].getDataType( ) );
				logger.info( child[i].getDataType( ) );
			}
			dataBean.setFieldMap( fieldList );
			dataBean.setFieldType( fieldType );
			dataBean.setTypeofData( "table" );
			event.data = dataBean;
		}
		else if( objTree.getType( ).equals( "field" ) )
		{
			DatabaseBean dataBean = new DatabaseBean( );
			ArrayList fieldList = new ArrayList( );
			HashMap fieldType = new HashMap( );
			dataBean.setFieldName( ( String ) objTree.getName( ) );
			fieldList.add( objTree.getName( ) );
			fieldType.put( objTree.getName( ), objTree.getDataType( ) );
			dataBean.setFieldMap( fieldList );
			dataBean.setFieldType( fieldType );
			dataBean.setPrimaryKey( objTree.isPrimaryKey( ) );
			dataBean.setTypeofData( "field" );
			dataBean.setDataSourceName( objTree.getDataSourceName( ) );
			dataBean.setTableName( objTree.getTableName( ) );
			dataBean.setMappingType( DataObjectView.mapFieldType_s );
			DatabaseViewer.selectedItem = ( TreeParent ) objTree;
			event.data = dataBean;
		}
	}

	public void createMapping( String dataObjName )
	{
		TreeParent parent = DatabaseViewer.selectedItem;
		TreeParent child = new TreeParent( dataObjName );
		child.setType( "mapping" );
		parent.addChild( child );
		DatabaseViewer.viewer.refresh( parent, true );
		DatabaseViewer.viewer.expandToLevel( parent, 1 );
	}

	/**
	 * This method is used for the purpose of copy
	 * 
	 * @return DatabaseBean object for transfer it to data object view
	 */
	public DatabaseBean doCopy( )
	{
		IStructuredSelection select = ( IStructuredSelection ) viewer
				.getSelection( );
		TreeObject objTree = ( TreeObject ) select.getFirstElement( );
		ArrayList listOfTable = new ArrayList( );
		DatabaseBean beanObj = null;
		if( objTree.getType( ).equals( "group" ) )
		{
			DatabaseBean groupBean = new DatabaseBean( );
			TreeParent objTreeParent = ( TreeParent ) select.getFirstElement( );
			groupBean.setGroupName( objTreeParent.getName( ) );
			TreeObject[] tableTree = objTreeParent.getChildren( );
			for( int count = 0; count < tableTree.length; count++ )
			{
				DatabaseBean dataBean = new DatabaseBean( );
				ArrayList fieldList = new ArrayList( );
				HashMap fieldType = new HashMap( );
				TreeParent parent = ( TreeParent ) tableTree[count];
				dataBean.setTableName( parent.getName( ) );
				TreeObject[] child = parent.getChildren( );
				for( int i = 0; i < child.length; i++ )
				{
					fieldList.add( child[i].getName( ) );
					fieldType
							.put( child[i].getName( ), child[i].getDataType( ) );
				}
				dataBean.setFieldMap( fieldList );
				dataBean.setFieldType( fieldType );
				// dataBean.setTypeofData("group");
				listOfTable.add( dataBean );
			}
			groupBean.setTypeofData( "group" );
			groupBean.setAllTables( listOfTable );
			beanObj = groupBean;
		}
		else if( objTree.getType( ).equals( "table" ) )
		{
			DatabaseBean dataBean = new DatabaseBean( );
			ArrayList fieldList = new ArrayList( );
			HashMap fieldType = new HashMap( );
			TreeParent objTreeParent = ( TreeParent ) select.getFirstElement( );
			dataBean.setTableName( objTreeParent.getName( ) );
			TreeObject[] child = objTreeParent.getChildren( );
			for( int i = 0; i < child.length; i++ )
			{
				fieldList.add( child[i].getName( ) );
				fieldType.put( child[i].getName( ), child[i].getDataType( ) );
			}
			dataBean.setFieldMap( fieldList );
			dataBean.setFieldType( fieldType );
			dataBean.setTypeofData( "table" );
			beanObj = dataBean;
		}
		else if( objTree.getType( ).equals( "field" ) )
		{
			DatabaseBean dataBean = new DatabaseBean( );
			ArrayList fieldList = new ArrayList( );
			HashMap fieldType = new HashMap( );
			dataBean.setFieldName( ( String ) objTree.getName( ) );
			fieldList.add( objTree.getName( ) );
			fieldType.put( objTree.getName( ), objTree.getDataType( ) );
			dataBean.setFieldMap( fieldList );
			dataBean.setFieldType( fieldType );
			dataBean.setTypeofData( "field" );
			dataBean.setDataSourceName( objTree.getDataSourceName( ) );
			dataBean.setTableName( objTree.getTableName( ) );
			dataBean.setMappingType( DataObjectView.mapFieldType_s );
			DatabaseViewer.selectedItem = ( TreeParent ) objTree;
			beanObj = dataBean;
		}
		return beanObj;
	}

	/**
	 * This method is used to expand the tree for the particulare filed selected
	 * from the dataobject view. This is to highlight the mapped field with the
	 * dataobjec
	 * 
	 * @param dataSource
	 *            This contains the datasource name
	 * @param tableName
	 *            This contains the table name
	 * @param fieldName
	 *            This contains the field name
	 */
	public void findAndExpandTree( String dataSource, String tableName,
			String fieldName )
	{
		TreeItem invisible = viewer.getTree( ).getItem( 0 );
		invisible.setExpanded( true );
		viewer.refresh( );
		TreeItem dsObject[] = invisible.getItems( );
		TreeItem tbObject[];
		TreeItem flObject[];
		for( int i = 0; i < dsObject.length; i++ )
		{
			if( dataSource.equals( dsObject[i].getText( ) ) )
			{
				dsObject[i].setExpanded( true );
				viewer.refresh( );
				tbObject = dsObject[i].getItems( );
				for( int j = 0; i < tbObject.length; j++ )
				{
					if( tableName.equals( tbObject[j].getText( ) ) )
					{
						tbObject[j].setExpanded( true );
						viewer.refresh( );
						flObject = tbObject[j].getItems( );
						for( int k = 0; k < flObject.length; k++ )
						{
							String field = flObject[k].getText( );
							field = field.substring( 0, field.indexOf( " " ) );
							if( fieldName.equals( field ) )
							{
								flObject[k].setExpanded( true );
								viewer
										.getTree( )
										.setSelection(
														new TreeItem[] { flObject[k] } );
								viewer.refresh( );
								break;
							}
						}
						break;
					}
				}
				break;
			}
		}
	}

	/**
	 * This method is used to get the information about the datasource tree and
	 * save those inforamtion through schema in an xml file
	 * 
	 * @param dataSourceType
	 *            This is of type DataSourceType will store all the data source
	 *            information and save those information in an xml file
	 */
	public void getDatabaseInfo( DataSourceType dataSourceType )
	{
		TreeObject[] rootObj = DatabaseViewer.invisibleRoot.getChildren( );
		TreeObject[] dataSourceObj = ( ( TreeParent ) rootObj[0] )
				.getChildren( );
		SaveAndOpenDataSource saveObj = new SaveAndOpenDataSource( );
		saveObj.saveDatasourceToFile( dataSourceObj, dataSourceType );
	}

	/**
	 * This is method is used to set the data source information in the tree by
	 * getting those information from the schema Object
	 * 
	 * @param dataSourceType
	 *            This is of Type DataSourceType schema object
	 */
	public void setDatabaseInfo( DataSourceType dataSourceType )
	{
		TreeObject[] rootObj = DatabaseViewer.invisibleRoot.getChildren( );
		if( ( ( TreeParent ) rootObj[0] ).hasChildren( ) )
		{
			TreeObject[] dataSourceObj = ( ( TreeParent ) rootObj[0] )
					.getChildren( );
			( ( TreeParent ) rootObj[0] ).removeChild( dataSourceObj[0] );
		}
		SaveAndOpenDataSource openObj = new SaveAndOpenDataSource( );
		openObj.openDataSourceFromFile( ( TreeParent ) rootObj[0],
										dataSourceType );
		DatabaseViewer.viewer.refresh( rootObj[0], true );
	}

	public String getOldDSName() {
		return oldDSName;
	}

	public void setOldDSName(String oldDSName) {
		this.oldDSName = oldDSName;
	}
}

class TreeObject implements IAdaptable
{
	private String		name;
	private TreeParent	parent;
	private String		type;
	private String		dataType;
	private String		selectTreeValue;
	private String		dataSourceName;
	private String		tableName;
	private String		dbURL;
	private String		dbUserName;
	private String		dbPassword;
	private boolean		isPrimaryKey;

	public String getDataType( )
	{
		return dataType;
	}

	public void setDataType( String dataType )
	{
		this.dataType = dataType;
	}

	public TreeObject( String name )
	{
		this.name = name;
	}

	public String getName( )
	{
		return name;
	}

	public void setParent( TreeParent parent )
	{
		this.parent = parent;
	}

	public TreeParent getParent( )
	{
		return parent;
	}

	public String toString( )
	{
		return getName( );
	}

	public Object getAdapter( Class key )
	{
		return null;
	}

	public String getType( )
	{
		return type;
	}

	public void setType( String type )
	{
		this.type = type;
	}

	public String getSelectTreeValue( )
	{
		return selectTreeValue;
	}

	public void setSelectTreeValue( String selectTreeValue )
	{
		this.selectTreeValue = selectTreeValue;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getDataSourceName( )
	{
		return dataSourceName;
	}

	public void setDataSourceName( String dataSourceName )
	{
		this.dataSourceName = dataSourceName;
	}

	public String getTableName( )
	{
		return tableName;
	}

	public void setTableName( String tableName )
	{
		this.tableName = tableName;
	}

	/**
	 * @return Returns the dbPassword.
	 */
	public String getDbPassword( )
	{
		return dbPassword;
	}

	/**
	 * @param dbPassword The dbPassword to set.
	 */
	public void setDbPassword( String dbPassword )
	{
		this.dbPassword = dbPassword;
	}

	/**
	 * @return Returns the dbURL.
	 */
	public String getDbURL( )
	{
		return dbURL;
	}

	/**
	 * @param dbURL The dbURL to set.
	 */
	public void setDbURL( String dbURL )
	{
		this.dbURL = dbURL;
	}

	/**
	 * @return Returns the dbUserName.
	 */
	public String getDbUserName( )
	{
		return dbUserName;
	}

	/**
	 * @param dbUserName The dbUserName to set.
	 */
	public void setDbUserName( String dbUserName )
	{
		this.dbUserName = dbUserName;
	}

	/**
	 * @return Returns the isPrimaryKey.
	 */
	public boolean isPrimaryKey( )
	{
		return isPrimaryKey;
	}

	/**
	 * @param isPrimaryKey The isPrimaryKey to set.
	 */
	public void setPrimaryKey( boolean isPrimaryKey )
	{
		this.isPrimaryKey = isPrimaryKey;
	}

}

class TreeParent extends TreeObject
{
	/**
	 * An ArrayList containing the list of children nodes
	 */
	private ArrayList	children;

	/**
	 * Constructor for TreeParent
	 * @param name of type String
	 */
	public TreeParent( String name )
	{
		super( name );
		children = new ArrayList( );
	}

	/**
	 * Method to add Child node to the tree view
	 * @param child of type TreeObject
	 */
	public void addChild( TreeObject child )
	{
		children.add( child );
		child.setParent( this );
	}

	/**
	 * Method to remove the child node from the tree viewer
	 * @param child of type TreeObject
	 */
	public void removeChild( TreeObject child )
	{
		children.remove( child );
		child.setParent( null );
	}

	/**
	 * Method to obtain an array of Children nodes
	 * @return an array of TreeObject
	 */
	public TreeObject[] getChildren( )
	{
		return ( TreeObject[] ) children.toArray( new TreeObject[children
				.size( )] );
	}

	/**
	 * Method to check wheather a parent node has children nodes
	 * @return true if parent node has children otherwise false
	 */
	public boolean hasChildren( )
	{
		return children.size( ) > 0;
	}

	public void setChildren( ArrayList arr )
	{
		this.children = arr;
	}

}
