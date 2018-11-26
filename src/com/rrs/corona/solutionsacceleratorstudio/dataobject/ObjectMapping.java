/*******************************************************************************
 * @rrs_start_copyright
 * 
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved. This
 * software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Red Rabbit Software. ©
 * 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights
 * reserved. Red Rabbit Software - Development Program 5 of 15 $Id:
 * ObjectMapping.java,v 1.1.2.9 2006/04/03 15:58:27 maha Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/dataobject/Attic/ObjectMapping.java,v $
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
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
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

/**
 * This class is used to create the property view for Object mapping
 * 
 * @author Maharajan
 * @see com.rrs.corona.solutionsacceleratorstudio.SASConstants
 * @see com.rrs.corona.solutionsacceleratorstudio.dataobject.ObjectMappingEditor
 */
public class ObjectMapping extends ViewPart
{

	/**
	 * This is of type Composite under which table viewer is going to be built
	 */
	private Composite			parent;
	/**
	 * This holds all the objects for each row in the tableviewer
	 */
	public static Object[]		CONTENT				= new Object[] { new ObjectMappingEditor(
																								"",
																								"",
																								"" ) };
	/**
	 * This string holds the column property name as "amMapping" for atomic
	 * metric mapping
	 */
	private String				amMapping_PROPERTY	= "amMapping";
	/**
	 * This string holds the column property name as "dbMapping" for database
	 * mapping
	 */
	private String				dbMapping_PROPERTY	= "dbMapping";
	/**
	 * This string holds the column property name as "cfiledname" for fields
	 */
	private String				classField_PROPERTY	= "cfiledname";
	/**
	 * This of type TableViewer on which table is going to be constructed
	 */
	public static TableViewer	viewer;
	/**
	 * This of type TableLayout for layout settings
	 */
	private TableLayout			layout				= null;
	/**
	 * This of type Table for TableViewer
	 */
	private Table				table;

	/**
	 * This method is used to build the controls for the table viewer
	 * 
	 * @param parent
	 *            This of type compoiste
	 * @param table
	 *            This of type Table
	 */
	protected void buildControls( Composite parent, Table table )
	{
		viewer = buildAndLayoutTable( table );
		attachContentProvider( viewer );
		attachLabelProvider( viewer );
		MenuManager popupMenu = new MenuManager( );
		Menu menu = popupMenu.createContextMenu( table );
		table.setMenu( menu );
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
						return ( ( ObjectMappingEditor ) element ).fieldname;
					case 1:
						return ( ( ObjectMappingEditor ) element ).amMapping;
					case 2:
						return ( ( ObjectMappingEditor ) element ).dbMapping;
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
	 * This method is used to set the content provider for the table viewer
	 * 
	 * @param viewer
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

		layout = new TableLayout( );
		layout.addColumnData( new ColumnWeightData( 23, 75, true ) );
		layout.addColumnData( new ColumnWeightData( 23, 75, true ) );
		layout.addColumnData( new ColumnWeightData( 23, 75, true ) );
		table.setLayout( layout );

		TableColumn fieldnameColumn = new TableColumn( table, SWT.LEFT );
		fieldnameColumn.setText( SASConstants.OBJECT_MAP_TB_COL_ITEM_s );// "Data
																			// Object-Item");
		fieldnameColumn.setWidth( 23 );

		TableColumn noofMapColumn = new TableColumn( table, SWT.LEFT );
		noofMapColumn.setText( SASConstants.OBJECT_MAP_TB_COL_AMMAP_s );// "No
																		// of
																		// Mapping");
		noofMapColumn.setWidth( 23 );

		TableColumn datasourceColumn = new TableColumn( table, SWT.LEFT );
		datasourceColumn.setText( SASConstants.OBJECT_MAP_TB_COL_DBMAP_s );// "Datasource");
		datasourceColumn.setWidth( 23 );

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

		viewer.setCellModifier( new ICellModifier( ) {
			public boolean canModify( Object element, String property )
			{
				return true;
			}

			public Object getValue( Object element, String property )
			{
				if( classField_PROPERTY.equals( property ) )
				{
					return ( ( ObjectMappingEditor ) element ).fieldname;
				}
				else if( amMapping_PROPERTY.equals( property ) )
				{
					return ( ( ObjectMappingEditor ) element ).amMapping;
				}
				else if( dbMapping_PROPERTY.equals( property ) )
				{
					return ( ( ObjectMappingEditor ) element ).dbMapping;
				}
				else
				{
					return null;
				}
			}

			public void modify( Object element, String property, Object value )
			{
				TableItem tableItem = ( TableItem ) element;
				ObjectMappingEditor data = ( ObjectMappingEditor ) tableItem
						.getData( );
				if( classField_PROPERTY.equals( property ) )
				{
					data.fieldname = value.toString( );
				}
				if( amMapping_PROPERTY.equals( property ) )
				{
					data.amMapping = value.toString( );
				}
				if( dbMapping_PROPERTY.equals( property ) )
				{
					data.dbMapping = value.toString( );
				}

				viewer.refresh( data );
			}
		} );
		viewer.setCellEditors( new CellEditor[] {
				new TextCellEditor( parent, SWT.READ_ONLY ),
				new TextCellEditor( parent, SWT.READ_ONLY ),
				new TextCellEditor( parent, SWT.READ_ONLY ) } );
		viewer.setColumnProperties( new String[] { classField_PROPERTY,
				amMapping_PROPERTY, dbMapping_PROPERTY } );
	}

	@Override
	public void createPartControl( Composite parent )
	{
		this.parent = parent;
		table = new Table( parent, SWT.FULL_SELECTION );
		buildControls( parent, table );
		attachCellEditors( viewer, table );
		viewer.setInput( CONTENT );
	}

	@Override
	public void setFocus( )
	{
	}

}
/**/