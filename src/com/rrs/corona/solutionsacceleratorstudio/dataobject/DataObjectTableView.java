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
 * $Id: DataObjectTableView.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/dataobject/DataObjectTableView.java,v $
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

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.plugin.DatabaseViewer;
import com.rrs.corona.solutionsacceleratorstudio.project.ProjectData;

/**
 * This class is used to show the table view
 * 
 * 
 * @author Maharajan
 */
public class DataObjectTableView extends ViewPart
{
	/**
	 * This is of type Composite under which table viewer is going to be built
	 */
	public Composite			parent;
	/**
	 * This holds all the objects for each row in the tableviewer
	 */
	public static Object[]		CONTENT					= new Object[] { new EditableTableItem(
																								"",
																								new Integer(
																												0 ),
																								"",
																								null ) };
	/**
	 * This string array holds required java data types
	 */
	public static String[]		VALUE_SET				= SASConstants.DATAOBJECT_VALUE_SET_s;
	/**
	 * This string holds the column property name as "filedname" for data object
	 * property
	 */
	public String				NAME_PROPERTY			= "filedname";
	/**
	 * This string holds the column property name as "tablename" for data object
	 * property
	 */
	public String				VALUE_PROPERTY			= "tablename";
	/**
	 * This string holds the column property name as "atomicmetric" for
	 * atomicmetric property
	 */
	public String				ATOMIC_NAME_PROPERTY	= "atomicmetric";
	/**
	 * This string holds the column property name as "property" for atomicmetric
	 * property
	 */
	public String				ATOMIC_VALUE_PROPERTY	= "property";
	/**
	 * This of type TableViewer under which table is going to be built
	 */
	public static TableViewer	viewer;

	/**
	 * This method is used to build the controls for the table viewer
	 * 
	 * @param parent
	 *            This of type compoiste
	 * @param table
	 *            This of type Table
	 */
	protected void buildControls( Composite parent )
	{
		// create a table to be fiexed in the tableviewer
		final Table table = new Table( parent, SWT.FULL_SELECTION );
		viewer = buildAndLayoutTable( table );
		// This will call the contend provider to be set
		attachContentProvider( viewer );
		// This will call the lable provider to be set
		attachLabelProvider( viewer );
		// This is to add the cell editors
		attachCellEditors( viewer, table );
		// This is to add the double click listener
		doubleClickListener( );
		MenuManager popupMenu = new MenuManager( );
		Menu menu = popupMenu.createContextMenu( table );
		table.setMenu( menu );
		viewer.setInput( DataObjectTableView.CONTENT );
	}

	/**
	 * This method is sued to set the lableProvider for the table viewer
	 * 
	 * @param viewer
	 *            This of type TableViewer
	 */
	public void attachLabelProvider( TableViewer viewer )
	{
		viewer.setLabelProvider( new ITableLabelProvider( ) {
			public Image getColumnImage( Object element, int columnIndex )
			{
				return null;
			}

			public String getColumnText( Object element, int columnIndex )
			{
				switch( columnIndex )
				{
					case 0:
						return ( ( EditableTableItem ) element ).fieldName;
					case 1:
						Number index = ( ( EditableTableItem ) element ).fieldType;
						return DataObjectTableView.VALUE_SET[index.intValue( )];
					case 2:
						return ( ( EditableTableItem ) element ).atomicMetricProperty;
					default:
						return "Invalid column: " + columnIndex;
				}
			}

			public void addListener( ILabelProviderListener listener )
			{
			}

			public void dispose( )
			{
			}

			public boolean isLabelProperty( Object element, String property )
			{
				return false;
			}

			public void removeListener( ILabelProviderListener lpl )
			{
			}
		} );
	}

	/**
	 * This method is used to set the listener for the double click mouse event
	 * 
	 */
	private void doubleClickListener( )
	{
		viewer.addDoubleClickListener( new IDoubleClickListener( ) {

			public void doubleClick( DoubleClickEvent event )
			{
				IStructuredSelection selection = ( IStructuredSelection ) event
						.getSelection( );
				Object obj = ( Object ) selection.getFirstElement( );
				EditableTableItem item = ( EditableTableItem ) obj;
				Object itemObj = item.treeItem;
				// This is for expand the IntermediateDataObject tree view
				DataObjectView expand = new DataObjectView( );
				expand.doExpand( itemObj );
				// This is for expand the Project view
				ProjectData expProject = new ProjectData( );
				expProject.doExpand( itemObj );
				// This is for expand the database view
				DatabaseViewer viewerExp = new DatabaseViewer( );
				viewerExp.doExpand( itemObj );
			}

		} );
	}

	/**
	 * This method is used to set the content provider for the table viewer
	 * 
	 * @param viewer
	 *            This of type TableViewer
	 */
	public void attachContentProvider( TableViewer viewer )
	{
		viewer.setContentProvider( new IStructuredContentProvider( ) {
			public Object[] getElements( Object inputElement )
			{
				return ( Object[] ) inputElement;
			}

			public void dispose( )
			{
			}

			public void inputChanged( Viewer viewer, Object oldInput,
					Object newInput )
			{
			}
		} );
	}

	/**
	 * This method is used to build the laout and the table columns for the
	 * table viewer
	 * 
	 * @param table
	 *            This of type Table
	 * @return This returns the TableViewer object
	 */
	public TableViewer buildAndLayoutTable( final Table table )
	{
		TableViewer tableViewer = new TableViewer( table );
		tableViewer.getTable( ).setLinesVisible( true );
		tableViewer.getTable( ).setHeaderVisible( true );

		TableLayout layout = new TableLayout( );
		layout.addColumnData( new ColumnWeightData( 50, 75, true ) );
		layout.addColumnData( new ColumnWeightData( 0, 0, true ) );
		layout.addColumnData( new ColumnWeightData( 50, 75, true ) );
		layout.addColumnData( new ColumnWeightData( 0, 0, true ) );
		table.setLayout( layout );

		TableColumn fieldnameColumn = new TableColumn( table, SWT.LEFT );
		fieldnameColumn.setResizable( true );
		fieldnameColumn.setText( "" );
		fieldnameColumn.setWidth( 0 );

		TableColumn filedtypeColumn = new TableColumn( table, SWT.LEFT );
		filedtypeColumn.setResizable( true );
		filedtypeColumn.setText( "" );
		filedtypeColumn.setWidth( 0 );

		TableColumn atomicPropColumn = new TableColumn( table, SWT.LEFT );
		atomicPropColumn.setResizable( true );
		atomicPropColumn.setText( "" );
		atomicPropColumn.setWidth( 0 );

		TableColumn atomicValColumn = new TableColumn( table, SWT.LEFT );
		atomicValColumn.setResizable( true );
		atomicValColumn.setText( "" );
		atomicValColumn.setWidth( 0 );

		table.setHeaderVisible( true );

		return tableViewer;
	}

	/**
	 * This method is used to set the cell editors for each column of the table
	 * viewer
	 * 
	 * @param viewer
	 *            This of type TableViewer
	 * @param parent
	 *            This of type Composite
	 */
	public void attachCellEditors( final TableViewer viewer, Composite parent )
	{
		try
		{
			viewer.setCellModifier( new ICellModifier( ) {
				public boolean canModify( Object element, String property )
				{
					return true;
				}

				public Object getValue( Object element, String property )
				{
					if( NAME_PROPERTY.equals( property ) )
					{
						return ( ( EditableTableItem ) element ).fieldName;
					}
					else if( VALUE_PROPERTY.equals( property ) )
					{
						return ( ( EditableTableItem ) element ).fieldType;
					}
					else if( ATOMIC_NAME_PROPERTY.equals( property ) )
					{
						return ( ( EditableTableItem ) element ).atomicMetricProperty;
					}
					else if( ATOMIC_VALUE_PROPERTY.equals( property ) )
					{
						return ( ( EditableTableItem ) element ).treeItem;
					}
					else
						return null;
				}

				public void modify( Object element, String property,
						Object value )
				{
					try
					{
						TableItem tableItem = ( TableItem ) element;
						EditableTableItem data = ( EditableTableItem ) tableItem
								.getData( );
						if( NAME_PROPERTY.equals( property ) )
						{
							data.fieldName = value.toString( );
							org.eclipse.jface.viewers.CellEditor[] cells = DataObjectTableView.viewer
									.getCellEditors( );
							if( cells[0].getControl( ).getEnabled( ) )
							{
								( ( TreeParent ) data.treeItem )
										.setName( data.fieldName );
								// This will update the field name information
								// in the IntermediateDataObject tree view
								new DataObjectView( ).updataClass( );
							}
						}
						if( VALUE_PROPERTY.equals( property ) )
						{
							data.fieldType = ( Integer ) value;
							( ( TreeParent ) data.treeItem )
									.setType( VALUE_SET[data.fieldType] );
							// This will update the field type information in
							// the IntermediateDataObject treeview
							new DataObjectView( ).updataClass( );
						}
						viewer.refresh( data );
					}
					catch( Exception e )
					{
						e.printStackTrace( );
					}
				}
			} );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
		// set the cell editors in the table viewer
		viewer.setCellEditors( new CellEditor[] { new TextCellEditor( parent ),
				new ComboBoxCellEditor( parent, VALUE_SET, SWT.READ_ONLY ),
				new TextCellEditor( parent, SWT.READ_ONLY ),
				new TextCellEditor( parent, SWT.READ_ONLY ) } );
		// set the column properties
		viewer.setColumnProperties( new String[] { NAME_PROPERTY,
				VALUE_PROPERTY, ATOMIC_NAME_PROPERTY, ATOMIC_VALUE_PROPERTY } );

	}

	/**
	 * This is a call back method in the super class ViewPart. This will
	 * automatically called when the UI is loaded
	 */
	public void createPartControl( Composite parent )
	{
		this.parent = parent;
		buildControls( parent );
	}

	public void setFocus( )
	{
	}
}
