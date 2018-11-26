
/******************************************************************************
 * @rrs_start_copyright
 *
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved.
 * This software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of
 * the license agreement you entered into with Red Rabbit Software.
 © 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights reserved.
 Red Rabbit Software - Development Program 5 of 15
 *$Id: ProjectDataTableView.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/project/ProjectDataTableView.java,v $
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
package com.rrs.corona.solutionsacceleratorstudio.project;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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


import com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView;
/**
* This class is used to show the project property table view
* 
* @author Maharajan
* @see com.rrs.corona.solutionsacceleratorstudio.project.NonEditableTableItem
*/

public class ProjectDataTableView extends ViewPart
{/*

	public static Composite parent;
	public static Object[] CONTENT = new Object[] {
		new NonEditableTableItem("", "")};
	
	public static  String NAME_PROPERTY = "property";
	
	public static  String VALUE_PROPERTY = "value";
	
	public static TableViewer viewer;
	
	public class NewRowAction extends Action {
		public NewRowAction() {
			super("Insert New Row");
		}
		
		public void run() {
			
			EditableTableItem newItem = new EditableTableItem("new row",
					new Integer(2));
			viewer.add(newItem);
			
		}
	}
	
	protected void buildControls(Composite parent) 
	{
		
		final Table table = new Table(parent, SWT.FULL_SELECTION);
		viewer = buildAndLayoutTable(table);
		
		attachContentProvider(viewer);
		attachLabelProvider(viewer);
		attachCellEditors(viewer, table);
		
		MenuManager popupMenu = new MenuManager();
		//IAction newRowAction = new NewRowAction();
		//popupMenu.add(newRowAction);
		Menu menu = popupMenu.createContextMenu(table);
		table.setMenu(menu);
		
		viewer.setInput(CONTENT);
		viewer.addSelectionChangedListener(new ISelectionChangedListener()
				{

					public void selectionChanged(SelectionChangedEvent event) 
					{
						
					}
				
				});
	}
	
	public void attachLabelProvider(TableViewer viewer) 
	{
		viewer.setLabelProvider(new ITableLabelProvider() 
				{
			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}
			
			public String getColumnText(Object element, int columnIndex) {
				switch (columnIndex) {
				case 0:
					return ((NonEditableTableItem) element).name;
				case 1:
					return ((NonEditableTableItem) element).value;
				default:
					return "Invalid column: " + columnIndex;
				}
			}
			
			public void addListener(ILabelProviderListener listener) {
			}
			
			public void dispose() {
			}
			
			public boolean isLabelProperty(Object element, String property) {
				return false;
			}
			
			public void removeListener(ILabelProviderListener lpl) {
			}
		});
	}
	
	public void attachContentProvider(TableViewer viewer)
	{
		viewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				return (Object[]) inputElement;
			}
			
			public void dispose() {
			}
			
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		});
	}
	
	public TableViewer buildAndLayoutTable(final Table table) 
	{
		TableViewer tableViewer = new TableViewer(table);
		tableViewer.getTable().setLinesVisible(true);
		tableViewer.getTable().setHeaderVisible(true);
		
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(50, 75, true));
		layout.addColumnData(new ColumnWeightData(50, 75, true));
		table.setLayout(layout);
		
		TableColumn nameColumn = new TableColumn(table, SWT.LEFT);
		nameColumn.setText("AtomicMetric Property");
		nameColumn.setWidth(50);
		TableColumn valColumn = new TableColumn(table, SWT.LEFT);
		valColumn.setText("Value");
		valColumn.setWidth(50);
		table.setHeaderVisible(true);
		return tableViewer;
	}
	
	public static void attachCellEditors(final TableViewer viewer, Composite parent) 
	{
		viewer.setCellModifier(new ICellModifier() {
			public boolean canModify(Object element, String property) 
			{
				return true;
			}
			
			public Object getValue(Object element, String property) 
			{
				if (NAME_PROPERTY.equals(property))
				 return ((NonEditableTableItem) element).name;
				 else
				if (VALUE_PROPERTY.equals(property))
					return ((NonEditableTableItem) element).value;
				else
					return null;
			}
			
			public void modify(Object element, String property, Object value) 
			{
				TableItem tableItem = (TableItem) element;
				NonEditableTableItem data = (NonEditableTableItem) tableItem
				.getData();
				 if (NAME_PROPERTY.equals(property))
				 {
					 data.name = value.toString();
					
				 }
				if (VALUE_PROPERTY.equals(property))
				{
					data.value = value.toString();
				}
				
				viewer.refresh(data);
			}
		});
		
		viewer.setCellEditors(new CellEditor[] { new TextCellEditor(parent,SWT.READ_ONLY),
				new TextCellEditor(parent,SWT.READ_ONLY) });
		
		viewer
		.setColumnProperties(new String[] { NAME_PROPERTY,
				VALUE_PROPERTY });
		
	}
	public static void attachAtomic(final TableViewer viewer, Composite parent)
	{
		viewer.setCellModifier(new ICellModifier() {
			public boolean canModify(Object element, String property) 
			{
				return false;
			}
			
			public Object getValue(Object element, String property) 
			{
				if (ProjectDataTableView.NAME_PROPERTY.equals(property))
				 return ((NonEditableTableItem) element).name;
				 else
				if (ProjectDataTableView.VALUE_PROPERTY.equals(property))
					return ((NonEditableTableItem) element).value;
				else
					return null;
			}
			
			public void modify(Object element, String property, Object value) 
			{
				TableItem tableItem = (TableItem) element;
				NonEditableTableItem data = (NonEditableTableItem) tableItem
				.getData();
				 if (ProjectDataTableView.NAME_PROPERTY.equals(property))
				 {
					 data.name = value.toString();
					// new DataObjectView().updateTreeFieldName(data.name);
				 }
				if (ProjectDataTableView.VALUE_PROPERTY.equals(property))
				{
					data.value =  value.toString();
					// new DataObjectView().updataTreeFieldType(data.value.intValue());
				}
			}
		});
	}
	
*/@Override
public void createPartControl( Composite parent )
{
	/*this.parent = parent;
	buildControls( parent);*/
}

@Override
public void setFocus( )
{
}}
