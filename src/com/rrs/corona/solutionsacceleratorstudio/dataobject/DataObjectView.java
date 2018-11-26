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
 * $Id: DataObjectView.java,v 1.4 2006/08/07 04:50:13 redrabbit Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/dataobject/DataObjectView.java,v $
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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
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
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import com.rrs.corona.sasadapter.DataObjectType;
import com.rrs.corona.sasadapter.SolutionsAdapter;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.SASImages;
import com.rrs.corona.solutionsacceleratorstudio.dragdrop.DatabaseBean;
import com.rrs.corona.solutionsacceleratorstudio.dragdrop.DatabaseTransfer;
import com.rrs.corona.solutionsacceleratorstudio.dragdrop.DragSourceListenerAbstract;
import com.rrs.corona.solutionsacceleratorstudio.dragdrop.DropListenerAbstract;
import com.rrs.corona.solutionsacceleratorstudio.plugin.DatabaseViewer;
import com.rrs.corona.solutionsacceleratorstudio.project.AtomicMetricBean;
import com.rrs.corona.solutionsacceleratorstudio.project.ProjectData;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SolutionAdapterView;

/**
 * This class is used to show the IntermediateDataObject view and also the tree
 * view which contains group and classes.
 * <p>
 * This classes will contian fields with mapping information like database with
 * IntermediateDataobject mapping and AtomicMetric with IntermediateDataObject.
 * </p>
 * <p>
 * This class will also generate the Java source code. Which will used for OJB
 * </p>
 * 
 * @author Maharajan
 * @author Debadatta Mishra
 */

public class DataObjectView extends ViewPart
{
	/**
	 * <code>viewer_s</code>TreeViewer object
	 */
	public static TreeViewer	viewer_s;
	/**
	 * <code>groupName_s</code>It is used to hold the new group name while
	 * adding new group throuhg dialog box
	 */
	public static String		groupName_s;
	/**
	 * <code>className_s</code>It is used to hold the new class name while
	 * adding new group throuhg dialog box
	 */
	public static String		className_s;
	/**
	 * <code>fieldName_s</code>It is used to hold the new field name while
	 * adding new group throuhg dialog box
	 */
	public static String		fieldName_s;
	/**
	 * <code>fieldType_s</code>It is used to hold the new type of the field
	 * while adding new group throuhg dialog box
	 */
	public static String		fieldType_s;
	/**
	 * <code>mappingType_s</code>It contains the value as field for mapping
	 * purpose
	 */
	public static String		mapFieldType_s	= "datasource";
	/**
	 * <code>mapAtomicType_s</code>It contains the value as atomic for
	 * mapping purpose
	 */
	public static String		mapAtomicType_s	= "atomic";
	/**
	 * <code>dataObj_s</code>hold the string "dataObj" for this is to refer
	 * that the tree item is the root
	 */
	public static String		dataObj_s		= "dataObj";
	/**
	 * <code>group_s</code>hold the string "group" for this is to refer that
	 * the tree item is the group
	 */
	public static String		group_s			= "group";
	/**
	 * <code>class_s</code>hold the string "class" for this is to refer that
	 * the tree item is the class
	 */
	public static String		class_s			= "class";
	/**
	 * <code>field_s</code>hold the string "field" for this is to refer that
	 * the tree item is the field
	 */
	public static String		field_s			= "field";
	/**
	 * <code>table_s</code>hold the string "table"
	 */
	public static String		table_s			= "table";
	// private Action OpenDataObject;
	/**
	 * <code>addNewClass</code>This is of type Action for the right click
	 * pop-up menu. This is used to add new class will open up a dialog box for
	 * adding a new class
	 */
	private Action				addNewClass;
	/**
	 * <code>addNewField</code>This is of type Action for the right click
	 * pop-up menu. This is used to add new field will open up a dialog box for
	 * adding a new field
	 */
	private Action				addNewField;
	/**
	 * <code>addNewGroup</code>This is of type Action for the right click
	 * pop-up menu. This is used to add new group will open up a dialog box for
	 * adding a new group
	 */
	private Action				addNewGroup;
	/**
	 * <code>delete</code>This is of type Action for the right click pop-up
	 * menu. This is used to delete selected items
	 */
	private Action				delete;
	/**
	 * <code>doubleClickAction</code>This is of type Action for the right
	 * click pop-up menu.
	 */
	private Action				doubleClickAction;
	/**
	 * <code>rename</code>This is of type Action for the right click pop-up
	 * menu. This is used to rename selected item
	 */
	private Action				rename;
	/**
	 * <code>cut</code>This is of type Action for the right click pop-up
	 * menu. This is used to cut selected item
	 */
	private Action				cut;
	/**
	 * <code>copy</code>This is of type Action for the right click pop-up
	 * menu. This is used to copy selected item
	 */
	private Action				copy;
	/**
	 * <code>paste</code>This is of type Action for the right click pop-up
	 * menu. This is used to paste selected item
	 */
	private Action				paste;
	/**
	 * <code>generateCode</code>This is of type Action for the right click
	 * pop-up menu. This is used to paste selected item
	 */
	private Action				generateCode;

	/**
	 * <code>parentComposite</code>This is of type Composite
	 */
	public static Composite		parentComposite;
	private DrillDownAdapter	drillDownAdapter;
	/**
	 * <code>invisibleRoot</code>This is of type TreeParent this node is
	 * invisble in the tree
	 */
	public static TreeParent	invisibleRoot;
	/**
	 * <code>previousTree</code>This is of type TreeObject. This used to hold
	 * the TreeObject/TreeParent at the time of cut operation
	 */
	private TreeObject			previousTree;
	/**
	 * <code>isCut</code>This boolean is used to identify that cut operation.
	 */
	private boolean				isCut			= false;
	
	protected Logger logger = Logger.getLogger("DataObjectView.class"); 

	class NameSorter extends ViewerSorter
	{
	}

	/**
	 * This class is used to set the contend provider for the tree viewer ie
	 * which class is going to be act as the model
	 * 
	 * @author Maharajan
	 * 
	 */
	class ViewContentProvider implements IStructuredContentProvider,
			ITreeContentProvider
	{
		public void inputChanged( Viewer v, Object oldInput, Object newInput )
		{
		}

		public void dispose( )
		{
		}

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

		public Object getParent( Object child )
		{
			if( child instanceof TreeObject )
			{
				return ( ( TreeObject ) child ).getParent( );
			}

			return null;
		}

		public Object[] getChildren( Object parent )
		{
			if( parent instanceof TreeParent )
			{
				return ( ( TreeParent ) parent ).getChildren( );
			}

			return new Object[0];
		}

		public boolean hasChildren( Object parent )
		{
			if( parent instanceof TreeParent )
				return ( ( TreeParent ) parent ).hasChildren( );

			return false;
		}

		/**
		 * This method is used to intialize in the TreeParent
		 * 
		 */
		private void initialize( )
		{
			// create the root tree item while initialize
			TreeParent root = new TreeParent(
												SASConstants.DATAOBJECT_TREE_ROOT_s );
			root.setValue( dataObj_s );
			invisibleRoot = new TreeParent( "" );
			invisibleRoot.addChild( root );
			root.setParent( invisibleRoot );
		}
	}

	/**
	 * This class is used to set the lable provider for the treeviewer It is
	 * used to set the images for every tree item created on the treeviewer
	 * 
	 * @author Maharajan
	 * 
	 */
	class ViewLabelProvider extends LabelProvider
	{
		public String getText( Object obj )
		{
			TreeObject tObject = ( TreeObject ) obj;

			return tObject.getName( );
		}

		public Image getImage( Object obj )
		{
			Image imageKey = SASImages.getFIELD_IMG( );

			if( obj instanceof TreeParent )
			{
				TreeParent parent = ( TreeParent ) obj;
				String value = parent.getValue( );
				if( value.equals( DataObjectView.dataObj_s ) )
				{
					// set the icon for the root in the tree
					imageKey = SASImages.getDOROOT_IMG( );
				}
				else if( value.equals( DataObjectView.group_s ) )
				{
					// set the icon for the group in the tree
					imageKey = SASImages.getGROUP_IMG( );
				}
				else if( value.equals( DataObjectView.class_s ) )
				{
					// set the icon for the class in the tree
					imageKey = SASImages.getCLASS_IMG( );
				}
				else if( value.equals( DataObjectView.field_s ) )
				{
					// set the icon for the field in the tree
					imageKey = SASImages.getFIELD_IMG( );
					if( parent.getMapping( )
							.equals( DataObjectView.mapFieldType_s ) )
					{
						// set the icon for the mapping field in the tree
						imageKey = SASImages.getTBFIELD_IMG( );
					}
					else if( parent.getMapping( )
							.equals( DataObjectView.mapAtomicType_s ) )
					{
						imageKey = SASImages.getATOMICMETRICS_IMG( );
					}
				}
			}
			return imageKey;
		}
	}

	/**
	 * This class will call when the perspective is opened through the
	 * plugin.xml
	 */
	public void createPartControl( Composite parent )
	{
		try
		{
			parentComposite = parent;
			viewer_s = new TreeViewer( parent, SWT.MULTI | SWT.H_SCROLL
					| SWT.V_SCROLL );
			drillDownAdapter = new DrillDownAdapter( viewer_s );
			// set the content provider
			viewer_s.setContentProvider( new ViewContentProvider( ) );
			// set the lable provider for images
			viewer_s.setLabelProvider( new ViewLabelProvider( ) );
			viewer_s.setSorter( new NameSorter( ) );
			viewer_s.setInput( getViewSite( ) );
			// set the droplistener
			viewer_s.addDropSupport( SWT.Move,
										new Transfer[] { DatabaseTransfer
												.getInstance( ) },
										new DropListenerAbstract( ) {
											public void drop(
													DropTargetEvent event )
											{
												dropInTree( event );
											}
										} );
			// set the drag listener
			viewer_s.addDragSupport( SWT.Move,
										new Transfer[] { DatabaseTransfer
												.getInstance( ) },
										new DragSourceListenerAbstract( ) {

											public void dragStart(
													DragSourceEvent event )
											{
												event.doit = true;
											}

											public void dragSetData(
													DragSourceEvent event )
											{
												// drag the specified tree item
												// and its childeren if present
												dragClassAndField( event );
											}
										} );
			viewer_s
					.addSelectionChangedListener( new ISelectionChangedListener( ) {
						public void selectionChanged(
								SelectionChangedEvent event )
						{
							populateObjMapping( );
						}
					} );
			// this method will initialize all the Action objects
			makeActions( );
			hookContextMenu( );
			hookDoubleClickAction( );
			// This will initialize all the tool bar items
			contributeToActionBars( );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}

	/**
	 * This method is used to populate the mapping information in the property
	 * window
	 * 
	 */
	private void populateObjMapping( )
	{
		try
		{
			// get the selected item in the tree
			IStructuredSelection select = ( IStructuredSelection ) viewer_s
					.getSelection( );
			TreeObject obj = ( TreeObject ) select.getFirstElement( );
			if( null != obj )
			{
				if( SolutionAdapterView.getSolutionsAdapterFlag( ) )
				{
					//Actual sequence was the following 
					//populateTableViewer( obj );
					//enableDiable( obj );
					//The objective of this is to prevent
					//the user to create more nested objects from the
					//nested object.It can be noted that
					//There can be only one level of nesting
					//as recomended by RedRabbit Software
					enableDiable( obj );
					populateTableViewer( obj ); //for temporary
				}
				else
				{
					disableAllItem( );
				}
			}
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}

	}

	/**
	 * This method is used to disable all the right click menu items
	 * 
	 */
	private void disableAllItem( )
	{
		addNewGroup.setEnabled( false );
		addNewClass.setEnabled( false );
		addNewField.setEnabled( false );
		delete.setEnabled( false );
		rename.setEnabled( false );
		cut.setEnabled( false );
		copy.setEnabled( false );
		paste.setEnabled( false );
		generateCode.setEnabled( false );
	}

	/**
	 * This method is used to populate the tree trough drop event
	 * 
	 * @param event
	 *            This of type DropTargetEvent
	 */
	private void dropInTree( DropTargetEvent event )
	{
		DatabaseBean dataBean = null;
		ArrayList fieldList = null;
		HashMap fieldType = null;
		dataBean = ( DatabaseBean ) event.data;
		if( dataBean != null )
		{
			DropIntoTree dropIntotree = new DropIntoTree( );
			TreeParent selection = ( TreeParent ) event.item.getData( );

			if( dataBean.getTypeofData( ).equals( DataObjectView.group_s )
					&& dataBean.getGroupName( ) != null
					&& selection.getValue( ).equals( dataObj_s ) )
			{
				// populate all the tables in the tree
				dropIntotree.dropAllTableInTree( selection, dataBean );
			}
			else if( dataBean.getTypeofData( ).equals( DataObjectView.field_s )
					&& selection.getValue( ).equals( class_s ) )
			{
				// populate field in the tree under table
				dropIntotree.dropFieldInTree( selection, dataBean, fieldList,
												fieldType );
			}
			else if( dataBean.getTypeofData( ).equals( DataObjectView.table_s )
					&& ( dataBean.getTableName( ) != null )
					&& selection.getValue( ).equals( group_s ) )
			{
				// populate table and its fields in the tree under class
				dropIntotree.dropTableAndFieldInTree( selection, dataBean,
														fieldList, fieldType );
			}
			//for nested classes, ie One Object contain another object
			//It is not like inner classes
			//for more details refer technical document and Advanced mapping
			//for nested object in OJB
			else if(dataBean.getTypeofData( ).equals( DataObjectView.table_s )
					&& ( dataBean.getTableName( ) != null )
					&& selection.getValue( ).equals( class_s ))//table VS class
			{//for nested class
				String className = selection.getName();
				String draggedTableName = dataBean.getTableName();
				//Preventing the same class name object in the same object
				if(className.equals(draggedTableName))
				{
					MessageDialog.openError(new Shell(),"Warning","Sorry, you can not drag here");
				}
				else
				{
					dropIntotree.dropTableAndFieldInTree( selection, dataBean,
															fieldList, fieldType );
				}
				
//				dropIntotree.dropTableAndFieldInTree( selection, dataBean,
//														fieldList, fieldType );
			}
			else if( dataBean.getTypeofData( ).equals( DataObjectView.field_s )
					&& dataBean.getMappingType( )
							.equals( DataObjectView.mapFieldType_s )
					&& selection.getValue( ).equals( DataObjectView.field_s ) )
			{
				// populate field under field for mapping
				if( dropIntotree.dropFieldInFieldTree( selection, dataBean,
														fieldList, fieldType ) )
				{
					DatabaseViewer viewer = new DatabaseViewer( );
					viewer.createMapping( selection.getName( ) );
				}
			}
			else if( dataBean.getTypeofData( )
					.equals( DataObjectView.mapAtomicType_s )
					&& selection.getValue( ).equals( DataObjectView.field_s ) )
			{
				AtomicMetricBean atomicBean = ( AtomicMetricBean ) dataBean
						.getAtomicBean( );

				if( dropIntotree.dropAtomicIntoTree( selection, atomicBean ) )
				{
					ProjectData project = new ProjectData( );
					project.createMapping( selection.getName( ) );
				}
			}
			DataObjectView.viewer_s.refresh( selection, true );
		}

	}

	/**
	 * This method is used to update the tree items from the table
	 * viewer(property window) This will remove the children and add as new
	 * children with the updated values
	 * 
	 * @param obj
	 *            This is of type TreeObject on which fields and their types
	 *            going to update
	 */
	public void updataClass( )
	{
		IStructuredSelection select = ( IStructuredSelection ) viewer_s
				.getSelection( );
		TreeParent obj = ( TreeParent ) select.getFirstElement( );
		TreeParent parentClass = ( TreeParent ) obj;
		TreeObject[] child = parentClass.getChildren( );
		for( int i = 0; i < child.length; i++ )
		{
			parentClass.removeChild( child[i] );
		}
		Object element[] = DataObjectTableView.CONTENT;
		for( int i = 0; i < element.length; i++ )
		{
			EditableTableItem item = ( EditableTableItem ) element[i];
			TreeParent newChild = ( TreeParent ) item.treeItem;
			newChild.setParent( parentClass );
			parentClass.addChild( newChild );
		}
		viewer_s.refresh( parentClass, true );

	}

	private void hookContextMenu( )
	{
		MenuManager menuMgr = new MenuManager( "#PopupMenu" );
		menuMgr.setRemoveAllWhenShown( true );
		menuMgr.addMenuListener( new IMenuListener( ) {
			public void menuAboutToShow( IMenuManager manager )
			{
				DataObjectView.this.fillContextMenu( manager );
			}
		} );

		Menu menu = menuMgr.createContextMenu( viewer_s.getControl( ) );
		viewer_s.getControl( ).setMenu( menu );
		getSite( ).registerContextMenu( menuMgr, viewer_s );
	}

	/**
	 * This is method is used to get the tool bar and add the items in the tool
	 * bar
	 * 
	 */
	private void contributeToActionBars( )
	{
		IActionBars bars = getViewSite( ).getActionBars( );
		fillLocalPullDown( bars.getMenuManager( ) );
		fillLocalToolBar( bars.getToolBarManager( ) );
	}

	private void fillLocalPullDown( IMenuManager manager )
	{

	}

	/**
	 * This method is used to add the right click menu items
	 * 
	 * @param manager
	 *            This of type IMenuManager which will add the items
	 */
	private void fillContextMenu( IMenuManager manager )
	{
		manager.add( addNewGroup );
		manager.add( addNewClass );
		manager.add( addNewField );
		manager.add( new Separator( ) );
		manager.add( delete );
		manager.add( rename );
		manager.add( cut );
		manager.add( copy );
		manager.add( paste );
		manager.add( new Separator( ) );
		manager.add( generateCode );
		manager.add( new Separator( ) );
		manager.add( new Separator( IWorkbenchActionConstants.MB_ADDITIONS ) );
	}

	/**
	 * This method is used to add the items in the tool bar
	 * 
	 * @param manager
	 *            This of type IToolBarManager
	 */
	private void fillLocalToolBar( IToolBarManager manager )
	{
		manager.add( delete );
		manager.add( cut );
		manager.add( copy );
		manager.add( paste );
	}

	/**
	 * This method will call the other method for doing serval operations while
	 * clicking on right click menu
	 * 
	 */
	private void makeActions( )
	{
		// doOpenDataObject();
		doAddNewGroup( );
		doAddNewClass( );
		doAddNewField( );
		doDelete( );
		doEdit( );
		doCut( );
		doCopy( );
		doPaste( );
		doGenerateCode( );

		doubleClickAction = new Action( ) {
			public void run( )
			{

				ISelection selection = viewer_s.getSelection( );
				TreeObject treeObj = ( TreeObject ) ( ( IStructuredSelection ) selection )
						.getFirstElement( );
				if( treeObj.getValue( ).equals( DataObjectView.field_s ) )
				{
					if( treeObj.getMapping( )
							.equals( DataObjectView.mapFieldType_s ) )
					{
						// this is for highlighting the mapped item in the
						// datasource view
						String dataSource = treeObj.getDataSource( );
						String tableName = treeObj.getTableName( );
						String fieldName = treeObj.getName( );
						// String fieldType = treeObj.getType();
						DatabaseViewer viewer = new DatabaseViewer( );
						viewer.findAndExpandTree( dataSource, tableName,
													fieldName );
					}
					else if( treeObj.getMapping( )
							.equals( DataObjectView.mapAtomicType_s ) )
					{
						// to be implemeted
					}
				}
			}
		};
	}

	private void hookDoubleClickAction( )
	{
		viewer_s.addDoubleClickListener( new IDoubleClickListener( ) {
			public void doubleClick( DoubleClickEvent event )
			{
				doubleClickAction.run( );
			}
		} );
	}

	/**
	 * This method is used to set the focus on the TreeViewer
	 */
	public void setFocus( )
	{
		viewer_s.getControl( ).setFocus( );
	}

	/**
	 * This method will add a new group in the IntermediateDataObject view
	 * 
	 * @param treeObj
	 *            This will contain the object type of TreeParent
	 */
	public void populateGroup( Object treeObj )
	{
		if( treeObj instanceof TreeParent )
		{
			TreeObject obj[] = DataObjectView.invisibleRoot.getChildren( );
			TreeParent boss = ( TreeParent ) obj[0];
			TreeParent parent = ( TreeParent ) treeObj;
			boss.addChild( parent );
			parent.setParent( boss );
			DataObjectView.viewer_s.refresh( boss, true );
			DataObjectView.viewer_s.expandToLevel( boss, 1 );

		}
	}

	/**
	 * This method is used to add a new group in the tree item. This will invoke
	 * a dialog box through which user can set the group name
	 * 
	 */
	private void addNewGroup( )
	{
		ArrayList groupNamesList = new ArrayList();
		IStructuredSelection select = ( IStructuredSelection ) viewer_s
				.getSelection( );
		TreeParent parent = ( TreeParent ) select.getFirstElement( );
		String value = parent.getValue( );
		//for extra enhancement
		if(parent.hasChildren())
		{
			groupNamesList = getPresentGroupNamesList(parent);
		}
		else
		{
			//do nothing
		}
		//for extra enhancement

		if( value.equals( DataObjectView.dataObj_s ) )
		{
			// opens the dialog box for enterning new group
			new DataObjectGroupDialog( viewer_s.getTree( ).getShell( ),groupNamesList ).open( );

			if( DataObjectView.groupName_s != null )
			{
				TreeParent groupClassTree = new TreeParent(
															DataObjectView.groupName_s );
				groupClassTree.setValue( DataObjectView.group_s );
				parent.addChild( groupClassTree );
				groupClassTree.setParent( parent );
				viewer_s.refresh( parent, true );
				viewer_s.expandToLevel( parent, 1 );
				SolutionAdapterView adapter = new SolutionAdapterView( );
				adapter.populateSolutionAdapter( ( Object ) groupClassTree,
													groupClassTree.getName( ) );
			}
		}

	}
	
	private ArrayList getPresentGroupNamesList(final TreeParent parent)
	{
		ArrayList groupList = new ArrayList();
		
		try
		{
			if(parent.hasChildren())
			{
				TreeObject[] groupObjects = (TreeObject[])parent.getChildren();
				for(int i=0;i<groupObjects.length;i++)
				{
					TreeParent groupParent = (TreeParent)groupObjects[i];
					final String groupName = groupParent.getName();
					groupList.add(groupName.toLowerCase());
				}
			}
		}
		catch(Exception e)
		{
			logger.info("Exception thrown in :::DataObjectView.java::: in :::getGroupNamesList()::: method");
			e.printStackTrace();
		}
		return groupList;
	}

	/**
	 * This method is used to add a new class in the tree item. This will invoke
	 * a dialog box through which user can set the class name
	 * 
	 */
	private void addNewClass( )
	{
		ArrayList classNames = new ArrayList();
		IStructuredSelection select = ( IStructuredSelection ) viewer_s
				.getSelection( );
		TreeParent parent = ( TreeParent ) select.getFirstElement( );
//		//for testing
//		System.out.println("parent.getParent ::::"+parent.getParent().getValue());
//		if((parent.getParent().getValue()).equals(DataObjectView.class_s ))
//		{
//			System.out.println("You are selecting more nested class.....");
//			//addNewClass.setEnabled( false );
//		}
//		
//		
//		
//		//for testing
		String value = parent.getValue( );
		if(parent.hasChildren())
		{
			classNames = getPresentClassNamesList(parent);
		}
		if( value.equals( DataObjectView.group_s ) )
		{
			// opens the dialog box for enterning new class
			new DataObjectClassDialog( DataObjectView.viewer_s.getTree( )
					.getShell( ),classNames ).open( );

			if( DataObjectView.className_s != null )
			{
				TreeParent classParent = new TreeParent(
															DataObjectView.className_s );
				classParent.setValue( DataObjectView.class_s );
				parent.addChild( classParent );
				classParent.setParent( parent );
				viewer_s.refresh( parent, true );
				viewer_s.expandToLevel( parent, 1 );
			}
		}
		//for nested object
		else if(value.equals( DataObjectView.class_s ))
		{//This else if is used for nested object
			//If it is of type class another class can 
			//be created
			classNames = new ArrayList();
			classNames = getPresentClassNamesList(parent.getParent());
			new DataObjectClassDialog( DataObjectView.viewer_s.getTree( )
					.getShell( ),classNames ).open( );
			if( DataObjectView.className_s != null )
			{
				TreeParent classParent = new TreeParent(
															DataObjectView.className_s );
				classParent.setValue( DataObjectView.class_s );
				parent.addChild( classParent );
				//for nested object
				classParent.setParent( parent );
				viewer_s.refresh( parent, true );
				viewer_s.expandToLevel( parent, 1 );
			}
			
		}
	}
	private ArrayList classNamesList = new ArrayList();
	/**
	 * This method is used to obtain the list of already present class names
	 * @param parent of type TreeParent
	 * @return the list of class names
	 */
	private ArrayList getPresentClassNamesList(final TreeParent parent)
	{//Method to obtain the list of class names
		//ArrayList classNamesList = new ArrayList();
		
		classNamesList = new ArrayList();
		
		try
		{
			if(parent.hasChildren())
			{
				TreeObject[] classObject = (TreeObject[])parent.getChildren();
				for(int i=0;i<classObject.length;i++)
				{
					//classNamesList = new ArrayList();//for testing
					final TreeParent classParent = (TreeParent)classObject[i];
					final String className = classParent.getName();
					final String clsValue = classParent.getValue();
					if(clsValue.equals(DataObjectView.class_s))
					{
						classNamesList.add(className.toLowerCase());
					}
					if(classParent.hasChildren())
					{
						TreeObject[] nestedClsObject = (TreeObject[])classParent.getChildren();
						for(int j=0;j<nestedClsObject.length;j++)
						{
							TreeParent nestedParent = (TreeParent)nestedClsObject[j];
							final String nestedClsName = nestedParent.getName();
							final String nestedClsValue = nestedParent.getValue();
							if(nestedClsValue.equals(DataObjectView.class_s))
							{
								classNamesList.add(nestedClsName.toLowerCase());
							}
							else
							{
								// do not add to the list
							}
						}
						
					}
				}
			}
		}
		catch(Exception e)
		{
			logger.info("Exception thrown in :::DataObjectView.java::: in :::getPresentClassNamesList()::: method");
			e.printStackTrace();
		}
		return classNamesList;
	}

	/**
	 * This method is used to add a new field and its type in the tree item.
	 * This will invoke a dialog box through which user can set the field and
	 * its type name
	 * 
	 */
	private void addNewField( )
	{
		ArrayList clsFieldList = new ArrayList();
		IStructuredSelection select = ( IStructuredSelection ) viewer_s
				.getSelection( );
		TreeParent parent = ( TreeParent ) select.getFirstElement( );
		String value = parent.getValue( );
		if(parent.hasChildren())
		{
			clsFieldList = getClsFieldList(parent);
		}

		if( value.equals( class_s ) )
		{
			// opens the dialog box for enterning new field and its type
			new DataObjectFieldDialog( viewer_s.getTree( ).getShell( ),clsFieldList ).open( );
			if( DataObjectView.fieldName_s != null )
			{
				TreeParent field = new TreeParent( fieldName_s );
				field.setValue( DataObjectView.field_s );
				field.setType( DataObjectView.fieldType_s );
				field.setMapping( "" );
				parent.addChild( field );
				field.setParent( parent );
			}
		}
		viewer_s.refresh( parent, false );
		viewer_s.expandToLevel( parent, 1 );
	}
	
	/**
	 * This method is used to obtain the 
	 * list of class field names in class
	 * @param parent of type TreeParent 
	 * @return a list of class field names
	 */
	private ArrayList getClsFieldList(TreeParent parent)
	{//Method used to obtain the list of class field names in a class
		ArrayList fieldList = new ArrayList();
		try
		{
			TreeObject[] fieldObject = (TreeObject[])parent.getChildren();
			for(int i=0;i<fieldObject.length;i++)
			{
				TreeParent fieldParent = (TreeParent)fieldObject[i];
				final String fieldName = fieldParent.getName();
				final String fieldValue = fieldParent.getValue();
				if(fieldValue.equals(DataObjectView.field_s))
				{
					fieldList.add(fieldName.toLowerCase());
				}
			}
		}
		catch(Exception e)
		{
			logger.info("Exception thrown in :::DataObjectView.java::: in :::getClsFieldList()::: method");
		}
		return fieldList;
	}

	/**
	 * This method is used to call the java source file generator method. It
	 * calls the generator for generating only one class at one time or
	 * generating all the classes under a group
	 */
	private void generateClass( )
	{
		IStructuredSelection select = ( IStructuredSelection ) viewer_s
				.getSelection( );
		TreeParent parent = ( TreeParent ) select.getFirstElement( );
		String value = parent.getValue( );
		if( value.equals( DataObjectView.class_s ) )//If only a particular class is selected
		{
			TreeObject[] obj = parent.getChildren( );
			generateAllClasses(obj,parent);//Method to generate classes
			//To display that classes are generated
			Shell shell = parentComposite.getShell( );
			MessageBox mBox = new MessageBox( shell );
			mBox.setMessage( SASConstants.DATAOBJECT_FILE_GENERATE_s );
			mBox.setText( "success" );
			mBox.open( );
		}
		else if( value.equals( group_s ) )//If the package is selected
		{
			TreeObject[] groupObj = parent.getChildren( );
			System.out.println("group object ::: "+groupObj);
			if( null != groupObj )
			{
				try
				{
					for( int count = 0; count < groupObj.length; count++ )
					{
						TreeParent classParent = ( TreeParent ) groupObj[count];
						TreeObject obj[] = classParent.getChildren( );
						String[] fieldNames = new String[obj.length];
						final String className = classParent.getName( );
						final String groupName = parent.getName( );
						generateAllClasses(obj,classParent);//to generate classes
					}
					//To display that java classes are generated
					Shell shell = parentComposite.getShell( );
					MessageBox mBox = new MessageBox( shell );
					mBox.setMessage( SASConstants.DATAOBJECT_FILES_GENERATE_s );
					mBox.setText( "success" );
					mBox.open( );
				}
				catch(Exception e)
				{
					e.printStackTrace( );
				}
			}
		}
	}
	
	/**
	 * This method is used to generate the
	 * classes from the user interface design
	 * @param obj an Array of TreeObject
	 * @param parent of type TreeParent
	 */
	private void generateAllClasses(TreeObject[] obj,TreeParent parent )
	{//Method used to iterate the UI nodes and to generate the classes
		if( null != obj )
		{
			//String[] fieldNames = new String[obj.length];
			ArrayList fieldNamesList = new ArrayList();//for temporary use
			String className = parent.getName( );
			String groupName = parent.getParent( ).getName( );
			
children_Loop: for( int i = 0; i < obj.length; i++ )
			 {
				final String name = obj[i].getName( );
				String type = obj[i].getType( );
				
				final String fieldValue = obj[i].getValue();
				if(fieldValue.equals("class"))
				{
					final String nestedClassName = obj[i].getName();
					//method to get the children of nested object and to generate the class
					generateNestedClass(obj[i],nestedClassName,groupName);
					final String clsObjName = nestedClassName.toLowerCase()+"1_";
					final String tempName = nestedClassName + " " + clsObjName;
					fieldNamesList.add(tempName);
					continue children_Loop;
				}
				if(fieldValue.equals("field"))
				{
					if( type.equals( SASConstants.DATAOBJECT_JAVATYPEDATE ) )
					{
						type = SASConstants.SAS_JAVA_DATE_s;
					}
					final String tempFieldName = type + " " + name;
					fieldNamesList.add(tempFieldName);
				}
			}
			String[] fieldNames = new String[fieldNamesList.size()];
			for(int k=0;k<fieldNamesList.size();k++)
			{
				fieldNames[k] = (String)fieldNamesList.get(k);
			}
			generateClass(className, groupName,fieldNames);//Method to generate the java class
		}

	}
	
	/**
	 * This method is used to generate the java classes for
	 * nested objects
	 * @param treeObject of type TreeObject
	 * @param nestedClassName of type String specifying the nested Object name
	 * @param groupName of type String specifying the package name
	 */
	private void generateNestedClass(final TreeObject treeObject,final String nestedClassName,final String groupName)
	{//Method used to genrate the java class for nested object
		TreeParent nestedParent = (TreeParent)treeObject;
		TreeObject[] nestedObj = nestedParent.getChildren();
		String[] nestedFieldNames = new String[nestedObj.length];//length of children
		for(int i=0;i<nestedObj.length;i++)
		{
			String nestedName = nestedObj[i].getName();
			String nestedType = nestedObj[i].getType();
			if( nestedType.equals( SASConstants.DATAOBJECT_JAVATYPEDATE ) )
			{
				nestedType = SASConstants.SAS_JAVA_DATE_s;
			}
			nestedFieldNames[i] = nestedType + " " + nestedName;
		}
		try
		{
			// call the generatecode method in JavaFiileGenerator class
			JavaFiileGenerator generator = new JavaFiileGenerator( );
			generator.generateCode( nestedClassName, groupName, nestedFieldNames );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}
	
	/**
	 * This method is used to generate the files for java classes
	 * @param name of type String specifying the name of the class
	 * @param groupName of type String specifying the name of the package
	 * @param fieldNames an array of fields specifying the names of the class fields and their types
	 */
	private void generateClass(final String name, final String groupName, final String[] fieldNames)
	{
		try
		{
			// call the generatecode method in JavaFiileGenerator class
			JavaFiileGenerator generator = new JavaFiileGenerator( );
			generator.generateCode( name, groupName, fieldNames );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}
	//May be unused
	/**
	 * Method used to check wheather an object contains
	 * another object. It is useful for nested object
	 * @param obj It represents an array of TreeObject
	 * @return true if Object contains another object  
	 * otherwise returns false
	 * @see com.rrs.corona.solutionsacceleratorstudio.dataobject.TreeObject
	 */
	private boolean checkForNestedObject(TreeObject[] obj)
	{//This method is used to know wheather an object contains 
		//another object
		boolean nestedFlag = false;
		first : for( int i = 0; i < obj.length; i++ )
		{
			logger.info( "Get object value :::: "+obj[i].getValue());
			if((obj[i].getValue().equals("class")))
			{
				nestedFlag = true;
				break first;
			}
		}	
		return nestedFlag;
	}
	/**
	 * This method is used to delete one or more tree items from the tree as
	 * well as from the xml file
	 * 
	 */
	private void deleteFromTree( )
	{
		try
		{
			IStructuredSelection select = ( IStructuredSelection ) viewer_s
					.getSelection( );
			Iterator iter = select.iterator( );
			TreeParent superParent = null;
			for( ; iter.hasNext( ); )
			{
				TreeObject parent = ( TreeObject ) iter.next( );
				superParent = parent.getParent( );
				if( parent.getValue( ).equals( DataObjectView.group_s ) )
				{// deleting the tree object from solutions adapter view
					SolutionAdapterView deleteObj = new SolutionAdapterView( );
					deleteObj.deleteFromDataObject( ( Object ) parent );
				}
				superParent.removeChild( parent );
				if(classNamesList.contains(parent.getName()))
				{
					classNamesList.remove(parent.getName());
				}
			}
			viewer_s.refresh( superParent, false );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}

	/**
	 * This method is used to rename the tree items. It will open up a edit
	 * dialog box with the old value, user can replace the new value
	 * 
	 */
	private void editTreeitem( )
	{
		IStructuredSelection select = ( IStructuredSelection ) viewer_s
				.getSelection( );
		TreeObject parent = ( TreeObject ) select.getFirstElement( );
		String value = parent.getValue( );
		// renaming the group name using edit dialog
		if( value.equals( DataObjectView.group_s ) )
		{
			DataObjectView.groupName_s = parent.getName( );
			new EditGroupDialog( viewer_s.getTree( ).getShell( ), parent
					.getName( ) ).open( );
			parent.setName( DataObjectView.groupName_s );
			viewer_s.refresh( parent.getParent( ), true );
		}// renaming the class using edit dialog for class
		else if( value.equals( DataObjectView.class_s ) )
		{
			DataObjectView.className_s = parent.getName( );
			new EditClassDialog( viewer_s.getTree( ).getShell( ), parent
					.getName( ) ).open( );
			parent.setName( DataObjectView.className_s );
			viewer_s.refresh( parent.getParent( ), true );
		}// renaming the field and its type using edit dialog for field
		else if( value.equals( DataObjectView.field_s ) )
		{
			TreeObject treeObj = ( TreeObject ) select.getFirstElement( );
			DataObjectView.fieldName_s = treeObj.getName( );
			DataObjectView.fieldType_s = treeObj.getType( );
			new EditFieldDialog( viewer_s.getTree( ).getShell( ), treeObj
					.getName( ), treeObj.getType( ) ).open( );
			treeObj.setName( DataObjectView.fieldName_s );
			treeObj.setValue( DataObjectView.field_s );
			treeObj.setType( DataObjectView.fieldType_s );
			TreeParent subparent = treeObj.getParent( );
			viewer_s.refresh( subparent, true );
		}

	}

	/**
	 * This method is used to do the enable or disable function over the right
	 * click menu while clicking on each level of tree item
	 * 
	 * @param obj
	 *            This of type TreeObject which is currently selected
	 */

	public void enableDiable( TreeObject obj )
	{
		try
		{
			// disable and enable operations for intermediateDataObject node in
			// the
			// tree
			if( obj.getValue( ).equals( DataObjectView.dataObj_s ) )
			{
				//System.out.println("You are selecting dataObject....");
				addNewGroup.setEnabled( true );
				addNewClass.setEnabled( false );
				addNewField.setEnabled( false );
				delete.setEnabled( false );
				rename.setEnabled( false );
				cut.setEnabled( false );
				copy.setEnabled( false );
				paste.setEnabled( false );
				generateCode.setEnabled( false );
				// OpenDataObject.setEnabled(false);
			}// disable enable operations for group in the tree view
			else if( obj.getValue( ).equals( DataObjectView.group_s ) )
			{
				addNewGroup.setEnabled( false );
				addNewClass.setEnabled( true );
				addNewField.setEnabled( false );
				delete.setEnabled( true );
				rename.setEnabled( true );
				cut.setEnabled( false );
				copy.setEnabled( false );
				paste.setEnabled( false );
				generateCode.setEnabled( true );
				// OpenDataObject.setEnabled(false);
			}// disable and enable operations for classes in the tree view
			else if( obj.getValue( ).equals( DataObjectView.class_s ) )
			{
				
				//The following modification is done to prevent
				//the user to create further nested object from a
				//nested object. It can be noted that there can be 
				//only one level of nesting as recomended by 
				//RedRabbit Software.
				
				if((obj.getParent().getValue()).equals(DataObjectView.class_s))
				{
					System.out.println("You are selecting more nested class....");
					addNewGroup.setEnabled( false );
					addNewClass.setEnabled( false );//for nested object
					addNewField.setEnabled( true );
					delete.setEnabled( true );
					rename.setEnabled( true );
					cut.setEnabled( true );
					copy.setEnabled( true );
					paste.setEnabled( true );
					//generateCode.setEnabled( true );
//java source files should be generated from the package and not from the class					
					generateCode.setEnabled( false );
				}
				else
				{
					addNewGroup.setEnabled( false );
					addNewClass.setEnabled( true );//for nested object
					addNewField.setEnabled( true );
					delete.setEnabled( true );
					rename.setEnabled( true );
					cut.setEnabled( true );
					copy.setEnabled( true );
					paste.setEnabled( true );
					//generateCode.setEnabled( true );
//java source files should be generated from the package and not from the class					
					generateCode.setEnabled( false );
				}
				
				
				
				
				
				
				
				
				/*addNewGroup.setEnabled( false );
				addNewClass.setEnabled( true );//for nested object
				addNewField.setEnabled( true );
				delete.setEnabled( true );
				rename.setEnabled( true );
				cut.setEnabled( true );
				copy.setEnabled( true );
				paste.setEnabled( true );
				generateCode.setEnabled( true );*/
				
				
				
				
				
				
				
				
				
				
				// OpenDataObject.setEnabled(false);
			}// disable and enable operations for fields in the tree view
			else if( obj.getValue( ).equals( DataObjectView.field_s ) )
			{
				addNewGroup.setEnabled( false );
				addNewClass.setEnabled( false );
				addNewField.setEnabled( false );
				delete.setEnabled( true );
				if( obj.getMapping( ).equals( DataObjectView.mapFieldType_s )
						|| obj.getMapping( )
								.equals( DataObjectView.mapAtomicType_s ) )
				{
					rename.setEnabled( false );
				}
				else
				{
					rename.setEnabled( true );
				}
				cut.setEnabled( true );
				copy.setEnabled( true );
				paste.setEnabled( true );
				generateCode.setEnabled( false );
				// OpenDataObject.setEnabled(false);
			}
			
			
			
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}

	/**
	 * This method is sued to drag and drop a class and its fileds or field
	 * only. This will set the information about the tree item in the bean class
	 * and this bean class will transfer
	 * 
	 * @param event
	 *            This is of type DragSourceEvent which is responsible for drag,
	 *            which contains the bean object
	 */
	public void dragClassAndField( DragSourceEvent event )
	{
		IStructuredSelection select = ( IStructuredSelection ) viewer_s
				.getSelection( );
		TreeObject objTree = ( TreeObject ) select.getFirstElement( );
		// drag operation for class and its fields
		if( objTree.getValue( ).equals( DataObjectView.class_s ) )
		{
			DatabaseBean dataBean = new DatabaseBean( );
			ArrayList fieldList = new ArrayList( );
			HashMap fieldType = new HashMap( );
			TreeParent objTreeParent = ( TreeParent ) select.getFirstElement( );
			dataBean.setTableName( objTreeParent.getName( ) );
			TreeObject[] child = objTreeParent.getChildren( );
			for( int i = 0; i < child.length; i++ )
			{
				fieldList.add( child[i].getName( ) + " " );
				fieldType.put( child[i].getName( ) + " ", child[i].getType( ) );
			}
			dataBean.setFieldMap( fieldList );
			dataBean.setFieldType( fieldType );
			dataBean.setTypeofData( DataObjectView.table_s );
			event.data = dataBean;
		}// drag operation for field
		else if( objTree.getValue( ).equals( field_s )
				&& !objTree.getMapping( )
						.equals( DataObjectView.mapAtomicType_s ) )
		{
			DatabaseBean dataBean = new DatabaseBean( );
			ArrayList fieldList = new ArrayList( );
			HashMap fieldType = new HashMap( );
			dataBean.setFieldName( ( String ) objTree.getName( ) + " " );
			String mapping = objTree.getMapping( );
			String dsn = objTree.getDataSource( );
			String tbn = objTree.getTableName( );
			if( null == mapping )
			{
				mapping = "";
			}
			if( null == dsn )
			{
				dsn = "";
			}
			if( null == tbn )
			{
				tbn = "";
			}
			dataBean.setMappingType( mapping );
			dataBean.setDataSourceName( dsn );
			dataBean.setTableName( tbn );

			fieldList.add( objTree.getName( ) + " " );
			fieldType.put( objTree.getName( ) + " ", objTree.getType( ) );
			dataBean.setFieldMap( fieldList );
			dataBean.setFieldType( fieldType );
			dataBean.setTypeofData( DataObjectView.field_s );
			event.data = dataBean;
		}// drag operation for field
		else if( objTree.getMapping( ).equals( DataObjectView.mapAtomicType_s ) )
		{
			DatabaseBean dataBean = new DatabaseBean( );
			dataBean.setTypeofData( DataObjectView.mapAtomicType_s );
			dataBean.setAtomicBean( objTree.getAtomicBean( ) );
			event.data = dataBean;
		}
		else
		{
			event.doit = false;
		}

	}

	/**
	 * This method is responsible for copy class and its fields or field only.
	 * This will set the information about the tree item in a bean.
	 * 
	 * @return dataBean This is of type DatabaseBean
	 */
	private DatabaseBean copyClassAndField( )
	{

		IStructuredSelection select = ( IStructuredSelection ) viewer_s
				.getSelection( );
		TreeObject objTree = ( TreeObject ) select.getFirstElement( );
		DatabaseBean dataBean = new DatabaseBean( );
		// copying classes
		if( objTree.getValue( ).equals( DataObjectView.class_s ) )
		{
			ArrayList fieldList = new ArrayList( );
			HashMap fieldType = new HashMap( );
			TreeParent objTreeParent = ( TreeParent ) select.getFirstElement( );
			dataBean.setTableName( objTreeParent.getName( ) );
			TreeObject[] child = objTreeParent.getChildren( );
			for( int i = 0; i < child.length; i++ )
			{
				fieldList.add( child[i].getName( ) + " " );
				fieldType.put( child[i].getName( ) + " ", child[i].getType( ) );
			}
			dataBean.setFieldMap( fieldList );
			dataBean.setFieldType( fieldType );
			dataBean.setTypeofData( DataObjectView.table_s );
		}// fields and mapping database nodes can only be able to copy
		else if( objTree.getValue( ).equals( DataObjectView.field_s )
				&& !objTree.getMapping( )
						.equals( DataObjectView.mapAtomicType_s ) )
		{
			ArrayList fieldList = new ArrayList( );
			HashMap fieldType = new HashMap( );
			dataBean.setFieldName( ( String ) objTree.getName( ) + " " );
			fieldList.add( objTree.getName( ) + " " );
			fieldType.put( objTree.getName( ) + " ", objTree.getType( ) );
			dataBean.setDataSourceName( objTree.getDataSource( ) );
			dataBean.setTableName( objTree.getTableName( ) );
			dataBean.setMappingType( objTree.getMapping( ) );
			dataBean.setPrimaryKey( objTree.isPrimaryKey( ) );
			dataBean.setFieldMap( fieldList );
			dataBean.setFieldType( fieldType );
			dataBean.setTypeofData( DataObjectView.field_s );

		}// atomicmetric mapped nodes only be able to copy
		else if( objTree.getMapping( ).equals( DataObjectView.mapAtomicType_s ) )
		{
			dataBean.setTypeofData( DataObjectView.mapAtomicType_s );
			dataBean.setAtomicBean( objTree.getAtomicBean( ) );
		}
		return dataBean;
	}

	/**
	 * This method is responsible for cut class and its fields or field only.
	 * This will set the information about the tree item in a bean.
	 * 
	 * @return dataBean this is of type DatabaseBean
	 */
	private DatabaseBean cutClassAndField( )
	{

		IStructuredSelection select = ( IStructuredSelection ) viewer_s
				.getSelection( );
		TreeObject objTree = ( TreeObject ) select.getFirstElement( );
		DatabaseBean dataBean = new DatabaseBean( );
		if( objTree.getValue( ).equals( DataObjectView.class_s ) )
		{
			// this is to have all the fields under the class
			ArrayList fieldList = new ArrayList( );
			// this is to have all the fields type
			HashMap fieldType = new HashMap( );
			TreeParent objTreeParent = ( TreeParent ) select.getFirstElement( );
			// this will have the currently selected tree parent
			previousTree = objTreeParent;
			dataBean.setTableName( objTreeParent.getName( ) );
			TreeObject[] child = objTreeParent.getChildren( );
			for( int i = 0; i < child.length; i++ )
			{
				fieldList.add( child[i].getName( ) + " " );
				fieldType.put( child[i].getName( ) + " ", child[i].getType( ) );
			}// put all the things in the bean object
			dataBean.setFieldMap( fieldList );
			dataBean.setFieldType( fieldType );
			dataBean.setTypeofData( DataObjectView.table_s );
		}
		else if( objTree.getValue( ).equals( field_s )
				&& !objTree.getMapping( )
						.equals( DataObjectView.mapAtomicType_s ) )
		{// this will have the currently selected tree object
			previousTree = ( TreeObject ) objTree;
			// this is to have all the fields under the class
			ArrayList fieldList = new ArrayList( );
			// this is to have all the fields type
			HashMap fieldType = new HashMap( );
			dataBean.setFieldName( ( String ) objTree.getName( ) + " " );
			fieldList.add( objTree.getName( ) + " " );
			fieldType.put( objTree.getName( ) + " ", objTree.getType( ) );
			dataBean.setDataSourceName( objTree.getDataSource( ) );
			dataBean.setTableName( objTree.getTableName( ) );
			dataBean.setMappingType( objTree.getMapping( ) );
			dataBean.setFieldMap( fieldList );
			dataBean.setFieldType( fieldType );
			dataBean.setTypeofData( DataObjectView.field_s );
		}
		else if( objTree.getMapping( ).equals( DataObjectView.mapAtomicType_s ) )
		{// this will have the currently selected tree object
			previousTree = ( TreeObject ) objTree;
			dataBean.setTypeofData( DataObjectView.mapAtomicType_s );
			dataBean.setAtomicBean( objTree.getAtomicBean( ) );
		}
		return dataBean;
	}

	/**
	 * This method is responsible for paste class and its fields or field only.
	 * This will get the information about the tree item from the bean. If it is
	 * a cut operation then this will delete the source item from the tree
	 * 
	 * @return dataBean this is of type DatabaseBean
	 */
	private void pasteClassAndField( DatabaseBean dataBean, TreeParent selection )
	{
		boolean result = false;
		ArrayList fieldList = null;
		HashMap fieldType = null;
		if( dataBean != null )
		{
			DropIntoTree dropIntotree = new DropIntoTree( );
			// past all the groups under the IntermediateDataObject node
			if( dataBean.getTypeofData( ).equals( DataObjectView.group_s )
					&& dataBean.getGroupName( ) != null
					&& selection.getValue( ).equals( DataObjectView.dataObj_s ) )
			{
				result = dropIntotree.dropAllTableInTree( selection, dataBean );
			}// paste all the fields under the class node
			else if( dataBean.getTypeofData( ).equals( DataObjectView.field_s )
					&& selection.getValue( ).equals( DataObjectView.class_s ) )
			{
				result = dropIntotree.dropFieldInTree( selection, dataBean,
														fieldList, fieldType );
			}// paste all the table information under the group
			else if( dataBean.getTypeofData( ).equals( DataObjectView.table_s )
					&& ( dataBean.getTableName( ) != null )
					&& selection.getValue( ).equals( DataObjectView.group_s ) )
			{
				result = dropIntotree.dropTableAndFieldInTree( selection,
																dataBean,
																fieldList,
																fieldType );
			}// paste all the database mapping information under the field
				// node
			else if( dataBean.getTypeofData( ).equals( DataObjectView.field_s )
					&& dataBean.getMappingType( )
							.equals( DataObjectView.mapFieldType_s )
					&& selection.getValue( ).equals( DataObjectView.field_s ) )
			{
				// populate field under field for mapping
				result = dropIntotree.dropFieldInFieldTree( selection,
															dataBean,
															fieldList,
															fieldType );
			}// paste all the atomicmetric mapping information under the
				// field node
			else if( dataBean.getTypeofData( )
					.equals( DataObjectView.mapAtomicType_s ) )
			{
				AtomicMetricBean atomicBean = ( AtomicMetricBean ) dataBean
						.getAtomicBean( );

				result = dropIntotree
						.dropAtomicIntoTree( selection, atomicBean );
			}
			DataObjectView.viewer_s.expandToLevel( selection, 1 );
		}
		// this is used for cut operation after pasting is done then delete
		// the previously selected node to be copied
		if( isCut && result )
		{
			TreeParent parent = previousTree.getParent( );
			if( previousTree instanceof TreeParent )
			{
				parent.removeChild( previousTree );
			}
			else
			{
				parent.removeChild( previousTree );
			}
			viewer_s.refresh( parent );
		}
	}

	/**
	 * This method is used to initialize the clipboard class and get the
	 * Transferclass instance. Then get the bean object which contains the tree
	 * information for copy finally this will set into the clipboard
	 * 
	 */
	private void doCopy( )
	{
		copy = new Action( ) {
			public void run( )
			{
				final Clipboard clipboard = new Clipboard( viewer_s
						.getControl( ).getDisplay( ) );
				final DatabaseTransfer dataTransfer = DatabaseTransfer
						.getInstance( );
				DatabaseBean dataBean = copyClassAndField( );
				clipboard.clearContents( );
				// copy the bean in the clipboard
				clipboard
						.setContents(
										new Object[] { dataBean },
										new DatabaseTransfer[] { dataTransfer },
										DND.CLIPBOARD );
				clipboard.dispose( );
			}
		};
		copy.setText( SASConstants.DATAOBJECT_TREE_MENU_COPY_s );
		copy.setToolTipText( SASConstants.DATAOBJECT_TREE_MENU_COPY_s );
		getViewSite( ).getActionBars( )
				.setGlobalActionHandler( ActionFactory.COPY.getId( ), copy );
		copy.setImageDescriptor( PlatformUI.getWorkbench( ).getSharedImages( )
				.getImageDescriptor( ISharedImages.IMG_TOOL_COPY ) );
	}

	/**
	 * This method is used to initialize the clipboard class and get the
	 * Transferclass instance. Then get the bean object which contains the tree
	 * information for paste finally this will set into the tree
	 * 
	 */
	private void doPaste( )
	{
		paste = new Action( ) {
			public void run( )
			{
				final Clipboard clipboard = new Clipboard( viewer_s
						.getControl( ).getDisplay( ) );
				// get the information from the clip board
				final Object clip = ( Object ) clipboard
						.getContents( DatabaseTransfer.getInstance( ) );
				if( clip instanceof DatabaseBean )
				{
					IStructuredSelection select = ( IStructuredSelection ) viewer_s
							.getSelection( );
					TreeParent selection = ( TreeParent ) select
							.getFirstElement( );
					DatabaseBean dataBean = ( DatabaseBean ) clip;
					pasteClassAndField( dataBean, selection );
					viewer_s.refresh( selection, true );
				}
			}
		};
		paste.setText( SASConstants.DATAOBJECT_TREE_MENU_PASTE_s );
		paste.setToolTipText( SASConstants.DATAOBJECT_TREE_MENU_PASTE_s );
		getViewSite( ).getActionBars( )
				.setGlobalActionHandler( ActionFactory.PASTE.getId( ), paste );
		paste.setImageDescriptor( PlatformUI.getWorkbench( ).getSharedImages( )
				.getImageDescriptor( ISharedImages.IMG_TOOL_PASTE ) );
	}

	/**
	 * This method is used to initialize the clipboard class and get the
	 * Transferclass instance. Then get the bean object which contains the tree
	 * information for cut finally this will set into the clipboard
	 * 
	 */
	private void doCut( )
	{
		cut = new Action( ) {
			public void run( )
			{
				final Clipboard clipboard = new Clipboard( viewer_s
						.getControl( ).getDisplay( ) );
				final DatabaseTransfer dataTransfer = DatabaseTransfer
						.getInstance( );
				DatabaseBean dataBean = cutClassAndField( );
				clipboard
						.setContents(
										new Object[] { dataBean },
										new DatabaseTransfer[] { dataTransfer },
										DND.CLIPBOARD );
				// cut and place the bean information in the clip board
				clipboard.dispose( );
				isCut = true;
			}
		};
		cut.setText( SASConstants.DATAOBJECT_TREE_MENU_CUT_s );
		cut.setToolTipText( SASConstants.DATAOBJECT_TREE_MENU_CUT_s );
		getViewSite( ).getActionBars( )
				.setGlobalActionHandler( ActionFactory.CUT.getId( ), cut );
		cut.setImageDescriptor( PlatformUI.getWorkbench( ).getSharedImages( )
				.getImageDescriptor( ISharedImages.IMG_TOOL_CUT ) );
	}

	/**
	 * This method is used to initialize the delete Action and call the
	 * appropriate method for delete one or more item from the tree
	 * 
	 */
	private void doDelete( )
	{
		delete = new Action( ) {
			public void run( )
			{
				final Clipboard clipboard = new Clipboard( viewer_s
						.getControl( ).getDisplay( ) );
				clipboard.clearContents( );
				// delete the selected node from the tree
				final boolean deleteFlag = MessageDialog.openConfirm(new Shell(),"Confirmation","Do you want to delete ?");
				if(deleteFlag)
				{
					deleteFromTree( );
				}
				else
				{
					// do nothing
				}
				//deleteFromTree( );
			}
		};
		delete.setText( SASConstants.DATAOBJECT_TREE_MENU_DELETE_s );
		delete.setToolTipText( SASConstants.DATAOBJECT_TREE_MENU_DELETE_s );
		getViewSite( ).getActionBars( )
				.setGlobalActionHandler( ActionFactory.DELETE.getId( ), delete );
		delete.setImageDescriptor( PlatformUI.getWorkbench( ).getSharedImages( )
				.getImageDescriptor( ISharedImages.IMG_TOOL_DELETE ) );
	}

	/**
	 * This method is used to initialize the edit Action and call the
	 * appropriate method for edit a item in the tree
	 * 
	 */
	private void doEdit( )
	{
		rename = new Action( ) {
			public void run( )
			{
				editTreeitem( );
			}
		};
		rename.setText( SASConstants.DATAOBJECT_TREE_MENU_RENAME_s );
		rename.setToolTipText( SASConstants.DATAOBJECT_TREE_MENU_RENAME_s );
		getViewSite( ).getActionBars( )
				.setGlobalActionHandler( ActionFactory.RENAME.getId( ), rename );
		rename.setImageDescriptor( PlatformUI.getWorkbench( ).getSharedImages( )
				.getImageDescriptor( ISharedImages.IMG_TOOL_FORWARD ) );
	}

	/**
	 * This method is used to initialize the addNewGroup Action and call the
	 * appropriate method for add a new group item in the tree
	 * 
	 */
	private void doAddNewGroup( )
	{
		addNewGroup = new Action( ) {
			public void run( )
			{
				addNewGroup( );
			}
		};
		addNewGroup.setText( SASConstants.DATAOBJECT_TREE_MENU_GROUP_s );
		addNewGroup.setToolTipText( SASConstants.DATAOBJECT_TREE_MENU_GROUP_s );
		addNewGroup.setImageDescriptor( PlatformUI.getWorkbench( )
				.getSharedImages( )
				.getImageDescriptor( ISharedImages.IMG_TOOL_NEW_WIZARD ) );
	}

	/**
	 * This method is used to initialize the addNewClass Action and call the
	 * appropriate method for add a new class item in the tree
	 * 
	 */
	private void doAddNewClass( )
	{
		addNewClass = new Action( ) {
			public void run( )
			{
				addNewClass( );
			}
		};
		addNewClass.setText( SASConstants.DATAOBJECT_TREE_MENU_CLASS_s );
		addNewClass.setToolTipText( SASConstants.DATAOBJECT_TREE_MENU_CLASS_s );
		addNewClass.setImageDescriptor( PlatformUI.getWorkbench( )
				.getSharedImages( )
				.getImageDescriptor( ISharedImages.IMG_TOOL_NEW_WIZARD ) );
	}

	/**
	 * This method is used to initialize the addNewfield and its type Action and
	 * call the appropriate method for add a new field and its type item in the
	 * tree
	 * 
	 */
	private void doAddNewField( )
	{
		addNewField = new Action( ) {
			public void run( )
			{
				addNewField( );
			}
		};
		addNewField.setText( SASConstants.DATAOBJECT_TREE_MENU_FIELD_s );
		addNewField.setToolTipText( SASConstants.DATAOBJECT_TREE_MENU_FIELD_s );
		addNewField.setImageDescriptor( PlatformUI.getWorkbench( )
				.getSharedImages( )
				.getImageDescriptor( ISharedImages.IMG_TOOL_NEW_WIZARD ) );
	}

	private void doOpenDataObject( )
	{
	}

	/**
	 * This method is used to initialize the generate Action and call the
	 * appropriate method for generate the java source file
	 * 
	 */
	private void doGenerateCode( )
	{
		generateCode = new Action( ) 
		{
			public void run( )
			{
				generateClass( );
			}
		};
		generateCode.setText( SASConstants.DATAOBJECT_TREE_MENU_GENERATE_s );
		generateCode
				.setToolTipText( SASConstants.DATAOBJECT_TREE_MENU_GENERATE_s );
		generateCode.setImageDescriptor( PlatformUI.getWorkbench( )
				.getSharedImages( )
				.getImageDescriptor( ISharedImages.IMG_TOOL_NEW_WIZARD ) );
	}

	/**
	 * This method is used to expand the tree view by one level
	 * 
	 * @param obj
	 *            This is is of type TreeParent
	 */
	public void doExpand( Object obj )
	{
		if( obj instanceof TreeParent )
		{
			DataObjectView.viewer_s.expandToLevel( obj, 1 );
			TreeParent parent = ( TreeParent ) obj;
			populateTableViewer( parent );
		}
	}

	/**
	 * This method is used to populate group properties
	 * 
	 * @param parent
	 *            This of type TreeParent which contains all the group as
	 *            children
	 * @param title
	 *            This will contain the title of the property window
	 */
	private void populateGroup( TreeParent parent, String title )
	{

		try
		{
			if( parent.hasChildren( ) )
			{
				TreeObject[] children = parent.getChildren( );
				Object itemObj[] = new Object[children.length];
				if( DataObjectTableView.CONTENT.length > 0 )
				{
					DataObjectTableView.viewer
							.remove( DataObjectTableView.CONTENT );
				}
				DataObjectTableView.VALUE_SET = new String[] { "" };
				Table table = DataObjectTableView.viewer.getTable( );
				TableLayout layout = ( TableLayout ) table.getLayout( );
				layout.addColumnData( new ColumnWeightData( 50, 75, true ) );
				layout.addColumnData( new ColumnWeightData( 50, 75, true ) );
				layout.addColumnData( new ColumnWeightData( 50, 75, true ) );
				layout.addColumnData( new ColumnWeightData( 50, 75, true ) );
				table.setLayout( layout );
				// decrease the width of the combo box column
				table.getColumn( 0 ).setText( title );
				table.getColumn( 2 ).setText( "" );
				table.getColumn( 0 ).setWidth( 370 );
				table.getColumn( 1 ).setWidth( 0 );
				table.getColumn( 2 ).setWidth( 380 );
				table.getColumn( 3 ).setWidth( 0 );
				for( int i = 0; i < children.length; i++ )
				{
					EditableTableItem itemName = new EditableTableItem(
																		children[i]
																				.getName( ),
																		0,
																		"",
																		( Object ) children[i] );
					itemObj[i] = ( Object ) itemName;
					DataObjectTableView.viewer.add( itemName );
					DataObjectTableView.viewer.refresh( itemName );
				}
				DataObjectTableView.CONTENT = itemObj;
				org.eclipse.jface.viewers.CellEditor[] cells = DataObjectTableView.viewer
						.getCellEditors( );
				cells[0].setStyle( SWT.READ_ONLY );
				cells[0].getControl( ).setEnabled( false );
				cells[2].getControl( ).setEnabled( false );
				DataObjectTableView.viewer.setCellEditors( cells );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}

	}

	/**
	 * This method is used to populate the property window. If user click the
	 * class item in the tree then all the fields and their types will be
	 * populated in the table, If user clicks on the fields then the mapping
	 * will be displayed in the table
	 * 
	 * @param obj
	 *            This of type TreeObject which is the one currenly selected
	 */
	private void populateTableViewer( TreeObject obj )
	{
		Table table = DataObjectTableView.viewer.getTable( );
		TableLayout layout = ( TableLayout ) table.getLayout( );
		layout.addColumnData( new ColumnWeightData( 50, 75, true ) );
		layout.addColumnData( new ColumnWeightData( 50, 75, true ) );
		layout.addColumnData( new ColumnWeightData( 50, 75, true ) );
		layout.addColumnData( new ColumnWeightData( 50, 75, true ) );
		table.setLayout( layout );
		// this to increase the width of the column combo box column and first
		// column
		table.getColumn( 0 ).setWidth( 370 );
		table.getColumn( 1 ).setWidth( 380 );
		table.getColumn( 2 ).setWidth( 0 );
		table.getColumn( 3 ).setWidth( 0 );
		for( int i = 0; i < DataObjectTableView.CONTENT.length; i++ )
		{
			EditableTableItem item = ( EditableTableItem ) DataObjectTableView.CONTENT[i];
			DataObjectTableView.viewer.remove( item );
		}
		if( obj.getValue( ).equals( DataObjectView.class_s ) )
		{
			updateTableForClass( obj, table );
		}
		else if( obj.getValue( ).equals( DataObjectView.field_s ) )
		{
			updateTableForField( obj, table );
		}
		else if( obj.getValue( ).equals( DataObjectView.group_s ) )
		{
			updateTableForGroup( obj, table );
		}
		else if( obj.getValue( ).equals( DataObjectView.dataObj_s ) )
		{
			populateGroup( ( TreeParent ) obj, "Group" );
		}

	}

	/**
	 * This method is used to update the table viewer items for classes
	 * 
	 * @param obj
	 *            This of type TreeObject which contains all the fields of a
	 *            class
	 * @param table
	 *            This of Type Table on which items going to be changed
	 */
	private void updateTableForClass( TreeObject obj, Table table )
	{
		TreeParent parent = ( TreeParent ) obj;
		TreeObject[] child = parent.getChildren( );
		DataObjectTableView.VALUE_SET = SASConstants.DATAOBJECT_VALUE_SET_s;
		table.getColumn( 0 ).setText( "Field" );
		table.getColumn( 1 ).setText( "Value" );
		Object itemObj[] = new Object[child.length];
		for( int i = 0; i < child.length; i++ )
		{
			int index = 0;
			for( int j = 0; j < DataObjectTableView.VALUE_SET.length; j++ )
			{
				System.out.println("Here ==>> "+DataObjectTableView.VALUE_SET[j]);
				if( child[i].getType( )
						.equals( DataObjectTableView.VALUE_SET[j] ) )
				{
					index = j;
					System.out.println("index ::: "+index);
				}
			}
			EditableTableItem item = new EditableTableItem(
															child[i].getName( ),
															new Integer( index ),
															"",
															( TreeParent ) child[i] );
			itemObj[i] = ( Object ) item;
			DataObjectTableView.viewer.add( item );
			DataObjectTableView.viewer.refresh( item );
		}
		DataObjectTableView.CONTENT = itemObj;
		org.eclipse.jface.viewers.CellEditor[] cells = DataObjectTableView.viewer
				.getCellEditors( );
		cells[0].setStyle( SWT.READ_ONLY );
		cells[0].getControl( ).setEnabled( true );
		DataObjectTableView.viewer.setCellEditors( cells );
	}

	/**
	 * This method is used to update the table viewer items for groups
	 * 
	 * @param obj
	 *            This of type TreeObject which contains all the fields of a
	 *            group
	 * @param table
	 *            This of Type Table on which items going to be changed
	 */
	private void updateTableForGroup( TreeObject obj, Table table )
	{
		// this is to decrease the combo box column
		table.getColumn( 0 ).setText( "Class Object" );
		table.getColumn( 2 ).setText( "" );
		table.getColumn( 0 ).setWidth( 370 );
		table.getColumn( 2 ).setWidth( 380 );
		table.getColumn( 1 ).setWidth( 0 );
		table.getColumn( 3 ).setWidth( 0 );
		TreeParent parent = ( TreeParent ) obj;
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
	 * This method is used to update the table viewer items for groups
	 * 
	 * @param obj
	 *            This of type TreeObject which contains all the fields of a
	 *            group
	 * @param table
	 *            This of Type Table on which items going to be changed
	 */
	private void updateTableForField( TreeObject obj, Table table )
	{
		// this is to decrease the combo box column
		table.getColumn( 0 ).setText( "Object Mapping" );
		table.getColumn( 2 ).setText( "Value" );
		table.getColumn( 0 ).setWidth( 380 );
		table.getColumn( 2 ).setWidth( 370 );
		table.getColumn( 1 ).setWidth( 0 );
		table.getColumn( 3 ).setWidth( 0 );
		TreeParent parent = obj.getParent( );
		boolean flag = parent.getValue( ).equals( DataObjectView.field_s );
		if( !flag )
		{
			mappingAll( obj );
		}
		org.eclipse.jface.viewers.CellEditor[] cells = DataObjectTableView.viewer
				.getCellEditors( );
		cells[0].setStyle( SWT.READ_ONLY );
		cells[0].getControl( ).setEnabled( false );
		DataObjectTableView.viewer.setCellEditors( cells );
	}

	/**
	 * This method is used to populate the property window for Atomicmetric
	 * mapping and database mapping
	 * 
	 * @param obj
	 *            This is of type TreeObject which contains the field node under
	 *            which mapping has done
	 */
	private void mappingAll( TreeObject obj )
	{
		TreeParent dataObject = ( TreeParent ) obj;
		boolean isMapping = dataObject.getMapping( )
				.equals( DataObjectView.mapAtomicType_s )
				|| dataObject.getMapping( )
						.equals( DataObjectView.mapFieldType_s );

		if( !isMapping )
		{
			Object items[] = null;
			String dataObjectitem = dataObject.getName( );
			if( dataObject.hasChildren( ) )
			{
				TreeObject children[] = ( TreeObject[] ) dataObject
						.getChildren( );
				items = new Object[children.length];
				for( int i = 0; i < children.length; i++ )
				{// This is to dispaly the atomicmetric mapping in the
					// property window
					if( children[i].getMapping( )
							.equals( DataObjectView.mapAtomicType_s ) )
					{
						AtomicMetricBean beanObj = ( AtomicMetricBean ) children[i]
								.getAtomicBean( );
						String mapping = children[i].getName( ) + ":"
								+ beanObj.getProjectName( );
						EditableTableItem item = new EditableTableItem(
																		dataObjectitem
																				+ "  (AM Mapping)",
																		0,
																		mapping,
																		null );
						DataObjectTableView.viewer.add( item );
						DataObjectTableView.viewer.refresh( item );
						items[i] = item;
					}// This is to display the database mappings in the
						// property window
					else if( children[i].getMapping( )
							.equals( DataObjectView.mapFieldType_s ) )
					{
						String mapping = children[i].getDataSource( ) + ":"
								+ children[i].getTableName( ) + ":"
								+ children[i].getName( );
						EditableTableItem item = new EditableTableItem(
																		dataObjectitem
																				+ "  (DB Mapping)",
																		0,
																		mapping,
																		null );
						DataObjectTableView.viewer.add( item );
						DataObjectTableView.viewer.refresh( item );
						items[i] = item;
					}
				}
			}
			else
			{
				items = new Object[1];
				EditableTableItem item = new EditableTableItem( dataObjectitem,
																0, "", null );
				DataObjectTableView.viewer.add( item );
				DataObjectTableView.viewer.refresh( item );
				items[0] = item;
			}
			DataObjectTableView.CONTENT = items;
		}
	}

	/**
	 * This method is used to clear the tree view in the data source view
	 * 
	 */
	public void cleanDataObjectTree( )
	{
		TreeObject[] child = DataObjectView.invisibleRoot.getChildren( );
		TreeParent root = ( TreeParent ) child[0];
		TreeObject[] subChild = root.getChildren( );
		// remove all the childrens it have
		for( int i = 0; i < subChild.length; i++ )
		{
			root.removeChild( subChild[i] );
		}
		DataObjectView.viewer_s.refresh( );
	}

	/**
	 * This method is used to get the information about the data object tree and
	 * place those information in the schema for saving purpose
	 * 
	 * @param dataObjectType
	 *            This of type DataObjectType which will have the information
	 *            about the data object
	 */
	public void getDataObjectInfo( DataObjectType dataObjectType )
	{
		TreeObject[] dataObject = DataObjectView.invisibleRoot.getChildren( );
		TreeObject[] groupObj = ( ( TreeParent ) dataObject[0] ).getChildren( );
		DataObjectOpen saveObj = new DataObjectOpen( );
		saveObj.saveDataObjectToFile( groupObj, dataObjectType );
	}
	
//	create a method to store nested object data

	public void setDataObjectInfo( SolutionsAdapter adapterRoot )
	{
		TreeObject[] dataObject = DataObjectView.invisibleRoot.getChildren( );
		DataObjectOpen openObj = new DataObjectOpen( );
		openObj.openDataObjectFromFile( ( TreeParent ) dataObject[0],
										adapterRoot );
		DataObjectView.viewer_s.refresh( dataObject[0], true );
	}

	/**
	 * This method is used to delete the tree items from the data object view
	 * when it has been deleted from the solutions adapter view
	 * 
	 * @param dataObject
	 *            This is of type Object contains the TreeParent
	 */
	public void removeDataObjectFromSA( Object dataObject )
	{
		if( dataObject instanceof TreeParent )
		{
			TreeParent treeObj = ( TreeParent ) dataObject;
			TreeParent parent = treeObj.getParent( );
			parent.removeChild( treeObj );
			DataObjectView.viewer_s.refresh( parent, true );
		}
	}

}

/*
 * The content provider class is responsible for providing objects to the view.
 * It can wrap existing objects in adapters or simply return objects as-is.
 * These objects may be sensitive to the current input of the view, or ignore it
 * and always show the same content (like Task List, for example).
 */
class TreeObject implements IAdaptable
{
	private String				name;
	private String				type;
	private String				value;
	private String				mapping;
	private String				dataSource;
	private String				tableName;
	private boolean				primaryKey;
	private TreeParent			parent;
	private AtomicMetricBean	atomicBean;

	/**
	 * This method will return the information about the atomic metric as a bean
	 * object
	 * 
	 * @return AtomicMetricBean object
	 */
	public AtomicMetricBean getAtomicBean( )
	{
		return atomicBean;
	}

	/**
	 * This method is used to set the AtomicMetricBean object which contains the
	 * information about the atomic metric
	 * 
	 * @param atomicBean
	 *            this is of Type AtomicMetricBean
	 */
	public void setAtomicBean( AtomicMetricBean atomicBean )
	{
		this.atomicBean = atomicBean;
	}

	/**
	 * This constructor is used to create the new tree item as the name given in
	 * the argument
	 * 
	 * @param name
	 *            This string contains the name which is going to be displayed
	 *            in the tree
	 */
	public TreeObject( String name )
	{
		this.name = name;
	}

	/**
	 * This method is used to get the name of the tree item
	 * 
	 * @return
	 */
	public String getName( )
	{
		return name;
	}

	/**
	 * This method is used to set the display name of the tree item
	 * 
	 * @param name
	 *            String which contais the name of the tree item
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * This method is used to set the tree parent for newly added tree item
	 * 
	 * @param parent
	 *            This of type TreeParent
	 */
	public void setParent( TreeParent parent )
	{
		this.parent = parent;
	}

	/**
	 * This method is used to get the parent of the particulare tree item
	 * 
	 * @return this of type TreeParent
	 */
	public TreeParent getParent( )
	{
		return parent;
	}

	/**
	 * This method will return the name of the tree item
	 */
	public String toString( )
	{
		return getName( );
	}

	/**
	 * This method will return null
	 */
	public Object getAdapter( Class key )
	{
		return null;
	}

	/**
	 * This method is used to get the value of the tree item. This is used to
	 * identify the tree item is belongs to group or class or field
	 * 
	 * @return this string will hold the value of group or class or field
	 */
	public String getValue( )
	{
		return value;
	}

	/**
	 * This method is used to set the value of the tree item. This is used to
	 * identify the tree item is belongs to group or class or field
	 * 
	 * @param value
	 *            This string will hold the value of group or class or field
	 */
	public void setValue( String value )
	{
		this.value = value;
	}

	/**
	 * This method is used to get the type of the field in the field tree
	 * 
	 * @return This string contains any of the java datatypes
	 */
	public String getType( )
	{
		return type;
	}

	/**
	 * This method is used to set the type of the field in the field tree
	 * 
	 * @param type
	 *            This string contains any of the java datatypes
	 */
	public void setType( String type )
	{
		this.type = type;
	}

	/**
	 * This method is used to get the mapping. It is used to identify wheather
	 * it is atomicmetric mapping or datasource mapping
	 * 
	 * @return This string will contains the mapping type
	 */
	public String getMapping( )
	{
		return mapping;
	}

	/**
	 * This method is used to set the mapping. It is used to identify wheather
	 * it is atomicmetric mapping or datasource mapping
	 * 
	 * @param mapping
	 *            This string will contains the mapping type
	 */
	public void setMapping( String mapping )
	{
		this.mapping = mapping;
	}

	/**
	 * This method is used to get the data source name of the particular mapped
	 * table column
	 * 
	 * @return This will contains the name of the datasource
	 */
	public String getDataSource( )
	{
		return dataSource;
	}

	/**
	 * This method is used to set the data source name of the particular mapped
	 * table column
	 * 
	 * @param dataSource
	 *            This will contains the name of the datasource
	 */
	public void setDataSource( String dataSource )
	{
		this.dataSource = dataSource;
	}

	/**
	 * This method is used to get the name of the table for the particular
	 * mapped table column
	 * 
	 * @return This will return the name of the table
	 */
	public String getTableName( )
	{
		return tableName;
	}

	/**
	 * This method is used to set the name of the table for the particular
	 * mapped table column
	 * 
	 * @param tableName
	 *            This will contains the name of the table
	 */
	public void setTableName( String tableName )
	{
		this.tableName = tableName;
	}

	/**
	 * @return Returns the primaryKey.
	 */
	public boolean isPrimaryKey( )
	{
		return primaryKey;
	}

	/**
	 * @param primaryKey
	 *            The primaryKey to set.
	 */
	public void setPrimaryKey( boolean primaryKey )
	{
		this.primaryKey = primaryKey;
	}
}

class TreeParent extends TreeObject
{
	private ArrayList<Object>	children;

	/**
	 * This constructor is used to create a new tree item
	 * 
	 * @param name
	 *            This will contains the name of the tree item
	 */
	public TreeParent( String name )
	{
		super( name );
		children = new ArrayList<Object>( );
	}

	/**
	 * This method is used to add a new child under a tree item
	 * 
	 * @param child
	 *            This contains the TreeObject
	 */
	public void addChild( TreeObject child )
	{
		children.add( child );
		child.setParent( this );
	}

	/**
	 * This method is used to remove the child from the tree item
	 * 
	 * @param child
	 */
	public void removeChild( TreeObject child )
	{
		children.remove( child );

		// child.setParent(null);
	}

	/**
	 * This method will get all the children under a particular tree item
	 * 
	 * @return This will contains array of objects of tree objects
	 */
	public TreeObject[] getChildren( )
	{
		return ( TreeObject[] ) children.toArray( new TreeObject[children
				.size( )] );
	}

	/**
	 * This method is used to know wheather it contains children or not for a
	 * particular tree item
	 * 
	 * @return true if it contains one or more children or else false
	 */
	public boolean hasChildren( )
	{
		return children.size( ) > 0;
	}
}
