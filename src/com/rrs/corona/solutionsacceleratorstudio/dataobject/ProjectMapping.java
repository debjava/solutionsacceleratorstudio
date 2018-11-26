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
 * ProjectMapping.java,v 1.1.2.2 2006/04/01 11:18:15 maha Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/dataobject/Attic/ProjectMapping.java,v $
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

public class ProjectMapping extends ViewPart
{/*
	 * 
	 * public static Composite parent; public static Object[] CONTENT = new
	 * Object[] { new ObjectMappingEditor("","", "")};
	 * 
	 * 
	 * public static String[] VALUE_SET = new String[]
	 * {"short","char","int","float","long","double","String","Date" };//"xxx",
	 * "yyy", //"zzz" };
	 * 
	 * public static String tName_PROPERTY = "tablename"; public static String
	 * dsName_PROPERTY = "datasource"; public static String classField_PROPERTY =
	 * "cfiledname"; public static String fiedls_PROPERTY = "tablefieldname";
	 * 
	 * public static TableViewer viewer;
	 * 
	 * public class NewRowAction extends Action { public NewRowAction() {
	 * super("Insert New Row"); }
	 * 
	 * public void run() {
	 * 
	 * ObjectMappingEditor newItem = new ObjectMappingEditor("new row", new
	 * Integer(2)); viewer.add(newItem);
	 *  } }
	 * 
	 * protected void buildControls(Composite parent) {
	 * 
	 * final Table table = new Table(parent, SWT.FULL_SELECTION); viewer =
	 * buildAndLayoutTable(table);
	 * 
	 * attachContentProvider(viewer); attachLabelProvider(viewer);
	 * attachCellEditors(viewer, table);
	 * 
	 * MenuManager popupMenu = new MenuManager(); //IAction newRowAction = new
	 * NewRowAction(); //popupMenu.add(newRowAction); Menu menu =
	 * popupMenu.createContextMenu(table); table.setMenu(menu);
	 * 
	 * viewer.setInput(CONTENT); viewer.addSelectionChangedListener(new
	 * ISelectionChangedListener() {
	 * 
	 * public void selectionChanged(SelectionChangedEvent event) {
	 *  }
	 * 
	 * }); }
	 * 
	 * public void attachLabelProvider(TableViewer viewer) {
	 * viewer.setLabelProvider(new ITableLabelProvider() { public Image
	 * getColumnImage(Object element, int columnIndex) { return null; }
	 * 
	 * public String getColumnText(Object element, int columnIndex) { switch
	 * (columnIndex) { case 0: return ((ObjectMappingEditor) element).fieldname;
	 * case 1: //Number index1 = ((ObjectMappingEditor) element).dbMapping;
	 * return ((ObjectMappingEditor) element).dbMapping; case 2: Number index2 =
	 * ((ObjectMappingEditor) element).tableName; return
	 * String.valueOf(index2.intValue()); case 3: Number index3 =
	 * ((ObjectMappingEditor) element).tabfieldName; return
	 * String.valueOf(index3.intValue()); default: return "Invalid column: " +
	 * columnIndex; } }
	 * 
	 * public void addListener(ILabelProviderListener listener) { }
	 * 
	 * public void dispose() { }
	 * 
	 * public boolean isLabelProperty(Object element, String property) { return
	 * false; }
	 * 
	 * public void removeListener(ILabelProviderListener lpl) { } }); }
	 * 
	 * public void attachContentProvider(TableViewer viewer) {
	 * viewer.setContentProvider(new IStructuredContentProvider() { public
	 * Object[] getElements(Object inputElement) { return (Object[])
	 * inputElement; }
	 * 
	 * public void dispose() { }
	 * 
	 * public void inputChanged(Viewer viewer, Object oldInput, Object newInput) { }
	 * }); }
	 * 
	 * public TableViewer buildAndLayoutTable(final Table table) { TableViewer
	 * tableViewer = new TableViewer(table);
	 * tableViewer.getTable().setLinesVisible(true);
	 * tableViewer.getTable().setHeaderVisible(true);
	 * 
	 * TableLayout layout = new TableLayout(); layout.addColumnData(new
	 * ColumnWeightData(20, 75, true)); layout.addColumnData(new
	 * ColumnWeightData(20, 75, true)); layout.addColumnData(new
	 * ColumnWeightData(20, 75, true)); layout.addColumnData(new
	 * ColumnWeightData(20, 75, true)); table.setLayout(layout);
	 * 
	 * TableColumn fieldnameColumn = new TableColumn(table, SWT.LEFT);
	 * fieldnameColumn.setText("Field Name"); fieldnameColumn.setWidth(20);
	 * 
	 * TableColumn datasourceColumn = new TableColumn(table, SWT.LEFT);
	 * datasourceColumn.setText("Datasource"); datasourceColumn.setWidth(20);
	 * 
	 * TableColumn tablenameColumn = new TableColumn(table, SWT.LEFT);
	 * tablenameColumn.setText("Table Name"); tablenameColumn.setWidth(20);
	 * 
	 * TableColumn fieldsColumn = new TableColumn(table, SWT.LEFT);
	 * fieldsColumn.setText("Fields"); fieldsColumn.setWidth(20);
	 * table.setHeaderVisible(true); return tableViewer; }
	 * 
	 * public static void attachCellEditors(final TableViewer viewer, Composite
	 * parent) { viewer.setCellModifier(new ICellModifier() { public boolean
	 * canModify(Object element, String property) { return true; }
	 * 
	 * public Object getValue(Object element, String property) { if
	 * (classField_PROPERTY.equals(property)) { return ((ObjectMappingEditor)
	 * element).fieldname; } else if (dsName_PROPERTY.equals(property)) { return
	 * ((ObjectMappingEditor) element).dbMapping; } else if
	 * (tName_PROPERTY.equals(property)) { return ((ObjectMappingEditor)
	 * element).tableName; } else if (fiedls_PROPERTY.equals(property)) { return
	 * ((ObjectMappingEditor) element).tabfieldName; } else { return null; } }
	 * 
	 * public void modify(Object element, String property, Object value) {
	 * TableItem tableItem = (TableItem) element; ObjectMappingEditor data =
	 * (ObjectMappingEditor) tableItem .getData(); if
	 * (classField_PROPERTY.equals(property)) { data.fieldname =
	 * value.toString(); } if (dsName_PROPERTY.equals(property)) {
	 * data.dbMapping = value.toString(); } if (tName_PROPERTY.equals(property)) {
	 * data.tableName = (Integer) value; } if (fiedls_PROPERTY.equals(property)) {
	 * data.tabfieldName = (Integer) value; } viewer.refresh(data); } });
	 * 
	 * viewer.setCellEditors(new CellEditor[] { new TextCellEditor(parent), new
	 * ComboBoxCellEditor(parent, new String[0]),new
	 * ComboBoxCellEditor(parent,new String[0]),new
	 * ComboBoxCellEditor(parent,new String[0]) });
	 * 
	 * viewer.setColumnProperties(new String[] {
	 * classField_PROPERTY,dsName_PROPERTY,tName_PROPERTY,fiedls_PROPERTY });
	 *  }
	 * 
	 * 
	 */
	@Override
	public void createPartControl( Composite parent )
	{
		// this.parent = parent;
		// buildControls( parent);
	}

	@Override
	public void setFocus( )
	{
	}
}
