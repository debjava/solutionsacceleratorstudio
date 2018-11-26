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
 *
 * $Id: ProjectData.java,v 1.6 2006/08/07 04:49:52 redrabbit Exp $
 * $Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/project/ProjectData.java,v $
 *
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

import com.rrs.corona.sasadapter.AdapterType;
import com.rrs.corona.sasadapter.SolutionsAdapter;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.SASImages;
import com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectTableView;
import com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView;
import com.rrs.corona.solutionsacceleratorstudio.dataobject.EditableTableItem;
import com.rrs.corona.solutionsacceleratorstudio.dragdrop.DatabaseBean;
import com.rrs.corona.solutionsacceleratorstudio.dragdrop.DatabaseTransfer;
import com.rrs.corona.solutionsacceleratorstudio.dragdrop.DragSourceListenerAbstract;
import com.rrs.corona.solutionsacceleratorstudio.messagehandler.BDMSASMessageHandler;
import com.rrs.corona.solutionsacceleratorstudio.messagehandler.ConfigReader;

//import com.rrs.corona.solutionsacceleratorstudio.messagehandler.SASServerInforReader;


import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;
import com.rrs.corona.solutionsacceleratorstudio.publisher.SASPublisher;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.ReadAndWriteXML;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SaveAdapter;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SolutionAdapterView;

/**
 * This class is used to show the project view
 * 
 * @author Maharajan
 * @see com.rrs.corona.solutionsacceleratorstudio.project.LoadProject
 */
public class ProjectData extends ViewPart {
	/**
	 * This of type TreeViewer on which tree is going to be contructed
	 */
	public static TreeViewer viewer_s;

	/**
	 * This string holds "project"
	 */
	public static final String PROJECT_S = "project";

	/**
	 * This string holds "project"
	 */
	public static final String PROJECTNAME_S = "project";

	/**
	 * This string holds "composite"
	 */
	public static final String COMPOSITE_S = "Composite";

	/**
	 * This string holds "simple"
	 */
	public static final String SIMPLE_S = "Simple";

	/**
	 * This string holds "group"
	 */
	public static final String GROUPMETRIC_S = "Group";

	/**
	 * This string holds "atomic"
	 */
	public static final String ATOMICMETRIC_S = "Atomic";

	/**
	 * This string holds "root"
	 */
	public static final String ROOT_S = "root";

	/**
	 * This of type Action for right click menu for copy
	 */
	private Action copy;

	/**
	 * This of type Action for right click menu for getting projects
	 */
	private Action getProject;

	/**
	 * This of type Action for right click menu for getting selected projects
	 */
	private Action getSelectedProject;

	/**
	 * This of type Action for right click menu for getting selected projects
	 */
	private Action removeProject;
	
	private Action removeNode;

	/**
	 * This is of type parentComposite
	 */
	private Composite parentComposite;

	private DrillDownAdapter drillDownAdapter;

	/**
	 * This of type TreeParent this is the root node for all the tree in the
	 * treeviewer
	 */
	public static TreeParent invisibleRoot;

	/**
	 * This is of type Display
	 */
	private static Display display_s;

	/**
	 * This string used to hold the server name
	 */
	public static String serverName_s = null;

	/**
	 * This boolean is used to identify wheather connection is made or
	 * disconnected
	 */
	public static boolean connectionStatus_s = false;

	/**
	 * This of type TreeObject used to hold the selected item while drag and
	 * drop for mapping
	 */
	private static TreeObject selectedItem;

	protected Logger logger = Logger.getLogger("ProjectData.class");

	class NameSorter extends ViewerSorter {
	}

	/**
	 * This class is used to set the contend provider for the tree viewer ie
	 * which class is going to be act as the model
	 * 
	 * @author Maha
	 * 
	 */
	class ViewContentProvider implements IStructuredContentProvider,
			ITreeContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				if (invisibleRoot == null)
					initialize();

				return getChildren(invisibleRoot);
			}

			return getChildren(parent);
		}

		public Object getParent(Object child) {
			if (child instanceof TreeObject) {
				return ((TreeObject) child).getParent();
			}

			return null;
		}

		public Object[] getChildren(Object parent) {
			if (parent instanceof TreeParent) {
				return ((TreeParent) parent).getChildren();
			}

			return new Object[0];
		}

		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent)
				return ((TreeParent) parent).hasChildren();

			return false;
		}

		/**
		 * This method is used to initialize the tree and its root
		 * 
		 */
		private void initialize() {
			TreeParent root = new TreeParent(PROJECT_S);
			root.setType(ROOT_S);
			invisibleRoot = new TreeParent("");
			invisibleRoot.addChild(root);
			root.setParent(invisibleRoot);
			display_s = viewer_s.getTree().getDisplay();
			viewer_s.refresh(root, true);
		}
	}

	/**
	 * This class is used to set the lable provider for the treeviewer It is
	 * used to set the images for every tree item created on the treeviewer
	 * 
	 * @author Maha
	 * 
	 */
	class ViewLabelProvider extends LabelProvider {
		public String getText(Object obj) {
			TreeObject tObject = (TreeObject) obj;

			return tObject.getName();
		}

		public Image getImage(Object obj) {
			Image imageKey = SASImages.getATOMICMETRICS_IMG();

			if (obj instanceof TreeParent) {
				TreeParent parent = (TreeParent) obj;
				String type = parent.getType();
				if (type.equals(COMPOSITE_S)) {// set the icon for the
												// compisteevent in the tree
					imageKey = SASImages.getCOMPOSITEEVENTS_IMG();
				} else if (type.equals(SIMPLE_S)) {// set the icon for the
													// simpleevent in the tree
					imageKey = SASImages.getSIMPLEEVENTS_IMG();
				} else if (type.equals(GROUPMETRIC_S)) {// set the icon for
														// groupmetric in the
														// tree
					imageKey = SASImages.getGROUPMETRICS_IMG();
				} else if (type.equals(PROJECT_S)) {// set the icon for project
													// in the tree
					imageKey = SASImages.getPROJECT_IMG();
				} else if (type.equals(ROOT_S)) {// set the icon for the root
													// in the tree
					imageKey = SASImages.getPROJROOT_IMG();
				} else if (type.equals(DataObjectView.field_s)) {// set the
																	// icon for
																	// the
																	// mapped
																	// field in
																	// the tree
					imageKey = SASImages.getFIELD_IMG();
				}
			}
			return imageKey;
		}
	}

	/**
	 * This class will call when the perspective is opened through the
	 * plugin.xml
	 */
	public void createPartControl(Composite parent) {
		try {
			parentComposite = parent;
			viewer_s = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
					| SWT.V_SCROLL);
			drillDownAdapter = new DrillDownAdapter(viewer_s);
			// set the content provider
			viewer_s.setContentProvider(new ViewContentProvider());
			// set the label provider
			viewer_s.setLabelProvider(new ViewLabelProvider());
			viewer_s.setSorter(new NameSorter());
			viewer_s.setInput(getViewSite());
			// set the drag listener
			viewer_s.addDragSupport(SWT.Move, new Transfer[] { DatabaseTransfer
					.getInstance() }, new DragSourceListenerAbstract() {
				public void dragStart(DragSourceEvent event) {
					event.doit = true;
				}

				public void dragSetData(DragSourceEvent event) {
					dragSelectedNode(event);
				}
			});
			// set the action listener for every selection made in the tree
			viewer_s
					.addSelectionChangedListener(new ISelectionChangedListener() {
						public void selectionChanged(SelectionChangedEvent event) {
							IStructuredSelection select = (IStructuredSelection) event
									.getSelection();
							TreeObject parent = (TreeObject) select
									.getFirstElement();
							if (null != parent) {
								actionListener(parent);
								setEnableOrDisable((TreeParent) parent);
							}
						}
					});
			makeActions();
			hookContextMenu();
			hookDoubleClickAction();
			contributeToActionBars();
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**createPartControl()** in class [ ProjectData.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to call corresponding methods to populate the
	 * properties in the table viewer
	 * 
	 * @param parent
	 *            This of type TreeObject which will populate its properties in
	 *            the table
	 */
	private void actionListener(TreeObject parent) {

		if (parent.getType().equals(ProjectData.GROUPMETRIC_S)) {// This is
																	// to
																	// populate
																	// the
																	// information
																	// about the
																	// groupmetric
																	// in the
			// properties window
			populateProperties((TreeParent) parent, "Event/Metric");
		} else if (parent.getType().equals(ProjectData.ATOMICMETRIC_S)) {// This
																			// is
																			// to
																			// populate
																			// the
																			// information
																			// about
																			// the
																			// atomicmetric
																			// in
																			// the
			// properties window
			populateAtomicProperties(parent, "Event/Metric");
			if (parent.getType().equals(ProjectData.ATOMICMETRIC_S)) {
				//copy.setEnabled(true);
			} else {
				//copy.setEnabled(false);
			}
		} else if (parent.getType().equals(ProjectData.SIMPLE_S)) {// This is
																	// to
																	// populate
																	// the
																	// information
																	// about the
																	// simpleevent
																	// in the
			// properties window
			populateProperties((TreeParent) parent, "Event/Metric");
		} else if (parent.getType().equals(ProjectData.COMPOSITE_S)) {// This
																		// is to
																		// populate
																		// the
																		// information
																		// about
																		// the
																		// compositeevent
																		// in
																		// the
			// properties window
			populateProperties((TreeParent) parent, "Event/Metric");
		} else if (parent.getType().equals(ProjectData.PROJECTNAME_S)) {// This
																		// is to
																		// populate
																		// the
																		// information
																		// about
																		// the
																		// project
																		// in
																		// the
			// properties window
			populateProperties((TreeParent) parent, "Project");
		} else if (parent.getType().equals(ProjectData.ROOT_S)) {// This to
																	// populate
																	// the
																	// information
																	// about the
																	// root in
																	// the
			//
			populateProperties((TreeParent) parent, "Project");
		}

	}

	/**
	 * This method is used to expand the specified tree item in the tree wiht
	 * level one
	 * 
	 * @param obj
	 *            This os type Object which inturn contains TreeParent object of
	 *            various levels of tree items
	 */
	public void doExpand(Object obj) {
		if (obj instanceof TreeObject) {

			TreeParent parent = (TreeParent) obj;
			if (parent.getType().equals(ProjectData.GROUPMETRIC_S)) {// this
																		// is to
																		// expand
																		// the
																		// tree
																		// level
																		// by
																		// one
																		// when
																		// double
																		// click
				// on the properties window in a particular groupmetric
				populateProperties((TreeParent) parent, "Event/Metric");
				ProjectData.viewer_s.expandToLevel(obj, 1);
			} else if (parent.getType().equals(ProjectData.ATOMICMETRIC_S)) {// this
																				// is
																				// to
																				// expand
																				// the
																				// tree
																				// level
																				// by
																				// one
																				// when
																				// double
																				// click
				// on the properties window in a particular atomicmetric
				populateAtomicProperties(parent, "Event/Metric");
			} else if (parent.getType().equals(ProjectData.SIMPLE_S)) {// this
																		// is to
																		// expand
																		// the
																		// tree
																		// level
																		// by
																		// one
																		// when
																		// double
																		// click
				// on the properties window in a particular simpleevent
				populateProperties((TreeParent) parent, "Event/Metric");
				ProjectData.viewer_s.expandToLevel(obj, 1);
			} else if (parent.getType().equals(ProjectData.COMPOSITE_S)) {// this
																			// is
																			// to
																			// expand
																			// the
																			// tree
																			// level
																			// by
																			// one
																			// when
																			// double
																			// click
				// on the properties window in a particular compositeevent
				populateProperties((TreeParent) parent, "Event/Metric");
				ProjectData.viewer_s.expandToLevel(obj, 1);
			} else if (parent.getType().equals(ProjectData.PROJECTNAME_S)) {// this
																			// is
																			// to
																			// expand
																			// the
																			// tree
																			// level
																			// by
																			// one
																			// when
																			// double
																			// click
				// on the properties window in a particular project naem
				populateProperties((TreeParent) parent, "Project");
				ProjectData.viewer_s.expandToLevel(obj, 1);
			} else if (parent.getType().equals(ProjectData.ROOT_S)) {// this
																		// is to
																		// expand
																		// the
																		// tree
																		// level
																		// by
																		// one
																		// when
																		// double
																		// click
				// on the properties window in a particular root
				populateProperties((TreeParent) parent, "Project");
				ProjectData.viewer_s.expandToLevel(obj, 1);
			}
		}
	}

	/**
	 * This method is used to populate the properties ie., immediate children in
	 * the tree will get populated in the properties window
	 * 
	 * @param parent
	 *            This of type TreeParent which is currently been selected
	 */
	private void populateProperties(TreeParent parent, String title) {
		try {
			if (parent.hasChildren()) {
				TreeObject[] children = parent.getChildren();
				Object itemObj[] = new Object[children.length];
				// remove the contents from the tableviewer
				if (DataObjectTableView.CONTENT.length > 0) {
					DataObjectTableView.viewer
							.remove(DataObjectTableView.CONTENT);
				}
				DataObjectTableView.VALUE_SET = new String[] { "" };
				Table table = DataObjectTableView.viewer.getTable();
				TableLayout layout = (TableLayout) table.getLayout();
				layout.addColumnData(new ColumnWeightData(50, 75, true));
				layout.addColumnData(new ColumnWeightData(50, 75, true));
				layout.addColumnData(new ColumnWeightData(50, 75, true));
				layout.addColumnData(new ColumnWeightData(50, 75, true));
				table.setLayout(layout);
				// decrease the width of the combo box column
				table.getColumn(0).setText(title);
				table.getColumn(2).setText("");
				table.getColumn(0).setWidth(370);
				table.getColumn(1).setWidth(0);
				table.getColumn(2).setWidth(380);
				table.getColumn(3).setWidth(0);
				for (int i = 0; i < children.length; i++) {
					EditableTableItem itemName = new EditableTableItem(
							children[i].getName(), 0, "", (Object) children[i]);
					itemObj[i] = (Object) itemName;
					DataObjectTableView.viewer.add(itemName);
					DataObjectTableView.viewer.refresh(itemName);
				}
				DataObjectTableView.CONTENT = itemObj;
				org.eclipse.jface.viewers.CellEditor[] cells = DataObjectTableView.viewer
						.getCellEditors();
				cells[0].setStyle(SWT.READ_ONLY);
				cells[0].getControl().setEnabled(false);
				cells[2].getControl().setEnabled(false);
				DataObjectTableView.viewer.setCellEditors(cells);
			}
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**populateProperties()** in class [ ProjectData.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to populate the atomicmetric properties in the
	 * property window
	 * 
	 * @param event
	 *            This of type SelectionChangedEvent
	 */
	private void populateAtomicProperties(TreeObject obj, String title) {
		if (null != obj) {
			try {
				AtomicMetricBean atomicBean = obj.getAtomicBean();
				Object itemObj[] = new Object[11];
				DataObjectTableView.viewer.remove(DataObjectTableView.CONTENT);
				DataObjectTableView.VALUE_SET = new String[] { "" };
				Table table = DataObjectTableView.viewer.getTable();
				TableLayout layout = (TableLayout) table.getLayout();
				layout.addColumnData(new ColumnWeightData(50, 75, true));
				layout.addColumnData(new ColumnWeightData(50, 75, true));
				layout.addColumnData(new ColumnWeightData(50, 75, true));
				layout.addColumnData(new ColumnWeightData(50, 75, true));
				table.setLayout(layout);
				// decrease the width of the combo box column
				table.getColumn(0).setText(title);
				table.getColumn(2).setText("Value");
				table.getColumn(0).setWidth(370);
				table.getColumn(1).setWidth(0);
				table.getColumn(2).setWidth(380);
				table.getColumn(3).setWidth(0);
				// This is to populate the atomicemtric properties in the
				// tableviewer
				if (null != atomicBean) {
					EditableTableItem itemName = new EditableTableItem("Name",
							0, atomicBean.getName(), obj);
					itemObj[0] = (Object) itemName;
					DataObjectTableView.viewer.add(itemName);
					DataObjectTableView.viewer.refresh(itemName);

					EditableTableItem itemDesc = new EditableTableItem(
							"Description", 0, atomicBean.getDescription(), obj);
					itemObj[1] = (Object) itemDesc;
					DataObjectTableView.viewer.add(itemDesc);
					DataObjectTableView.viewer.refresh(itemDesc);

					EditableTableItem itemCat = new EditableTableItem(
							"Category", 0, atomicBean.getCategory(), obj);
					itemObj[2] = (Object) itemCat;
					DataObjectTableView.viewer.add(itemCat);
					DataObjectTableView.viewer.refresh(itemCat);

					EditableTableItem itemID = new EditableTableItem("Id", 0,
							atomicBean.getMetricID(), obj);
					itemObj[3] = (Object) itemID;
					DataObjectTableView.viewer.add(itemID);
					DataObjectTableView.viewer.refresh(itemID);

					EditableTableItem itemGUID = new EditableTableItem("GUID",
							0, atomicBean.getGuid(), obj);
					itemObj[4] = (Object) itemGUID;
					DataObjectTableView.viewer.add(itemGUID);
					DataObjectTableView.viewer.refresh(itemGUID);

					EditableTableItem itemType = new EditableTableItem("Type",
							0, atomicBean.getType(), obj);
					itemObj[5] = (Object) itemType;
					DataObjectTableView.viewer.add(itemType);
					DataObjectTableView.viewer.refresh(itemType);

					EditableTableItem itemData = new EditableTableItem("Data",
							0, atomicBean.getData(), obj);
					itemObj[6] = (Object) itemData;
					DataObjectTableView.viewer.add(itemData);
					DataObjectTableView.viewer.refresh(itemData);

					EditableTableItem itemTime = new EditableTableItem(
							"TimeStamp", 0, atomicBean.getTimeStamp(), obj);
					itemObj[7] = (Object) itemTime;
					DataObjectTableView.viewer.add(itemTime);
					DataObjectTableView.viewer.refresh(itemTime);

					EditableTableItem itemCorr = new EditableTableItem(
							"CorrelationId", 0, atomicBean.getCorelationID(),
							obj);
					itemObj[8] = (Object) itemCorr;
					DataObjectTableView.viewer.add(itemCorr);
					DataObjectTableView.viewer.refresh(itemCorr);

					EditableTableItem itemTans = new EditableTableItem(
							"TransactionId", 0, atomicBean.getTransactionID(),
							obj);
					itemObj[9] = (Object) itemTans;
					DataObjectTableView.viewer.add(itemTans);
					DataObjectTableView.viewer.refresh(itemTans);

					EditableTableItem itemSess = new EditableTableItem(
							"SessionId", 0, atomicBean.getSessionID(), obj);
					itemObj[10] = (Object) itemSess;
					DataObjectTableView.viewer.add(itemSess);
					DataObjectTableView.viewer.refresh(itemSess);

					DataObjectTableView.CONTENT = itemObj;
					// ProjectDataTableView.viewer.cancelEditing();
				}
				org.eclipse.jface.viewers.CellEditor[] cells = DataObjectTableView.viewer
						.getCellEditors();
				cells[0].setStyle(SWT.READ_ONLY);
				cells[0].getControl().setEnabled(false);
				cells[2].getControl().setEnabled(false);
				DataObjectTableView.viewer.setCellEditors(cells);
			} catch (Exception e) {
				final String errMsg = "Exception thrown in Method "
						+ "**populateAtomicProperties()** in class [ ProjectData.java ]";
				SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg,
						e);
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method is used to initialize the MenuManger for menu operation
	 * 
	 */
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ProjectData.this.fillContextMenu(manager);
			}
		});

		Menu menu = menuMgr.createContextMenu(viewer_s.getControl());
		viewer_s.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer_s);
	}

	/**
	 * This method is used to add the menu in the tool bar
	 */
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
	}

	/**
	 * This method is used to add the rigth click menu items
	 * 
	 * @param manager
	 *            This is of type IMenuManager to add all the menu items
	 */
	private void fillContextMenu(IMenuManager manager) {
		manager.add(getProject);
		manager.add(removeNode);
/* ******************** Bug fix release , bug reported on 04.08.06 ***************************/		
		//for removing unnecessary items
//		manager.add(getSelectedProject);
//		manager.add(removeProject);
//		manager.add(new Separator());
//		manager.add(copy);
//		manager.add(new Separator());
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	/**
	 * This method is used to add the tool menu
	 * 
	 * @param manager
	 *            This is of type IToolBarManager to add the tool bar menu item
	 */
	private void fillLocalToolBar(IToolBarManager manager) 
	{
		//manager.add(copy);
	}

	/**
	 * This method will call respective methods to instantiate the Action in
	 * right click menu
	 * 
	 */
	private void makeActions() 
	{
		dogetProject();
		doRemoveMapping();
/***************** Bugs reported on 03/08/06 *************************/
//Removing unnecessary items from the published project view		

//		
//		doCopy();
//		dogetSelectedProject();
//		doRemoveProjects();
//		
		
	}
	
	private void doRemoveMapping()
	{
		removeNode = new Action()
		{
			public void run()
			{
				removeFromTree();
			}
		};
		removeNode.setText("Remove");
		removeNode.setToolTipText("Remove mapping from the view" );
		getViewSite().getActionBars().setGlobalActionHandler(
				ActionFactory.DELETE.getId(), removeNode);
		removeNode.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
	}
	
	private void removeFromTree()
	{
		//System.out.println("It will be removed from the tree....");
		IStructuredSelection select = (IStructuredSelection) viewer_s
		.getSelection();
		//TreeParent mappingParent = (TreeParent) select.getFirstElement();
		TreeObject treeObj = ( TreeObject ) select.getFirstElement( );
		TreeParent treeParent = treeObj.getParent( );
		boolean removeFlag = MessageDialog.openConfirm(new Shell(),"Warning","Do you want to remove ?");
		if(removeFlag)
		{
			treeParent.removeChild(treeObj);
		}
		else
		{
			// do nothing
		}
		
		
		
		//treeParent.removeChild(treeObj);
//		if((treeObj.getType()).equals("field"))
//		{
//			treeParent.removeChild(treeObj);
//			viewer_s.refresh( );
//		}
//		else
//		{
//			//do nothing
//		}
		//treeParent.removeChild(treeObj);
		viewer_s.refresh( );
		
	}

	/**
	 * This method is used to instantiate the copy Action and also call the
	 * appropriate method to perform copy operation
	 * 
	 */
	private void doCopy() {
		copy = new Action() {
			public void run() {
				copyAtomic();
			}
		};
		copy.setText(SASConstants.DATAOBJECT_TREE_MENU_COPY_s);
		copy.setToolTipText(SASConstants.DATAOBJECT_TREE_MENU_COPY_s);
		getViewSite().getActionBars().setGlobalActionHandler(
				ActionFactory.COPY.getId(), copy);
		copy.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
	}

	/**
	 * This method is used to instantiate the get all project from the specified
	 * remote cms and also copy all the projects into the file system
	 * 
	 */
	private void dogetProject() {
		getProject = new Action() {
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) ProjectData.viewer_s
						.getSelection();
				TreeParent root = (TreeParent) selection.getFirstElement();
				if (null != root
						&& root.getType().equals(ProjectData.PROJECT_S)) {
					String serverName = root.getName();
					getAllProjectFromServer(serverName, root);
				}
			}
		};
		getProject.setText("Get Projects");
		getProject.setToolTipText("Get Projects from server");
		getProject.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_OBJ_FILE));
	}

	/**
	 * This method is used to instantiate the get selected project from the
	 * server
	 * 
	 */
	private void dogetSelectedProject() {
		getSelectedProject = new Action() {
			public void run() {
				// TODO implement code to get Selected projects from server
			}
		};
		getSelectedProject.setText("Get Selected Projects");
		getSelectedProject.setToolTipText("Get Selected Projects from server");
		getSelectedProject.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_OBJ_FILE));
	}

	/**
	 * This method is used to instantiate the remove project Action and also
	 * call the appropriate method to remove the project from the tree and the
	 * file system
	 * 
	 */
	private void doRemoveProjects() {
		removeProject = new Action() {
			public void run() {
				removeProjectsFromTree();
			}
		};
		removeProject.setText("Remove Project");
		removeProject.setToolTipText("Remove Project");
		removeProject.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_OBJ_FILE));
	}

	/**
	 * This method is used to remove one or more projects from tree and also
	 * from the file system
	 * 
	 */
	private void removeProjectsFromTree() {
		IStructuredSelection select = (IStructuredSelection) viewer_s
				.getSelection();
		Iterator iter = select.iterator();
		TreeParent superParent = null;
		for (; iter.hasNext();) {
			TreeObject parent = (TreeObject) iter.next();
			superParent = parent.getParent();
			if (parent.getType().equals(ProjectData.PROJECT_S)) {
				String adapterName = superParent.getAdapterName()
						+ SASConstants.SOLUTIONS_ADAPTER_PROJECT;
				LoadProject loadObj = new LoadProject();
				loadObj.deleteProjects(adapterName, parent.getName());
				SaveAndOpenProject saveObj = new SaveAndOpenProject();
				String XMLFile = SASConstants.SAS_ROOT
						+ superParent.getAdapterName()
						+ SASConstants.BACK_SLASH_s
						+ SASConstants.SOLUTIONS_ADAPTER_XML;
				saveObj.resetProjectInfo(XMLFile, parent.getName());
			}
			superParent.removeChild(parent);
		}
		viewer_s.refresh(superParent, false);
	}

	BDMSASMessageHandler handler;

	/**
	 * This method is used to get all the projects from the remote cms specified
	 * int he ipaddress and the port number and also place the fetched project
	 * into the file system
	 * 
	 * @param serverName
	 *            This string will hold the name of the cms
	 * @param parent
	 *            This is of type TreeParent this will give the information
	 *            about the server
	 */
	private void getAllProjectFromServer(String serverName, TreeParent parent) 
	{
		ReadAndWriteXML readObj = new ReadAndWriteXML();
		final HashMap serverInfo = readObj.getServerInfo(serverName);
		String ipAddress = (String) serverInfo.get(ReadAndWriteXML.IPADDRESS_s);
		logger.info("ip address::" + ipAddress);
		String portNo = (String) serverInfo.get(ReadAndWriteXML.PORTNO_s);
		
		ConfigReader readerConfig = new ConfigReader();
		readerConfig.init();
		readerConfig.startParsing();
		String adapterName = parent.getAdapterName();
		if (handler == null) {
			logger.info("it is null");
			handler = new BDMSASMessageHandler(ipAddress, portNo, serverName,
					adapterName);// "10.0.0.9","1099");
			
		} else {
			logger.info("it is not null");
			handler.closeConnection();
			handler = new BDMSASMessageHandler(ipAddress, portNo, serverName,
					adapterName);// "10.0.0.9","1099");s
		}
		SASPublisher publisher = new SASPublisher();
		publisher.initializePublisherAndPublish(
				ConfigReader.bdmbridgeRequestTopic_s, ipAddress, portNo);// "Corona_CEReq_SAS_Topic","10.0.0.9","1099");
		viewer_s.expandToLevel(parent, 2);
		viewer_s.refresh(parent, true);
	}

	private void hookDoubleClickAction() {
		viewer_s.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				// doubleClickAction.run ();
			}
		});
	}

	/**
	 * This method is used to copy the atomicmetrci node from the tree and its
	 * information to the clip board for copy operation
	 * 
	 */
	private void copyAtomic() {
		IStructuredSelection select = (IStructuredSelection) viewer_s
				.getSelection();
		TreeObject obj = (TreeObject) select.getFirstElement();
		if (null != obj) {
			if (obj.getType().equals(ProjectData.ATOMICMETRIC_S)) {
				try {// add the content in the clip board for copy
					final Clipboard clipboard = new Clipboard(viewer_s
							.getControl().getDisplay());
					final DatabaseTransfer dataTransfer = DatabaseTransfer
							.getInstance();
					DatabaseBean beanObj = new DatabaseBean();
					AtomicMetricBean atomicBean = obj.getAtomicBean();
					beanObj.setTypeofData(ProjectData.ATOMICMETRIC_S);
					beanObj.setAtomicBean(atomicBean);
					clipboard.clearContents();
					clipboard.setContents(new Object[] { beanObj },
							new DatabaseTransfer[] { dataTransfer },
							DND.CLIPBOARD);
					clipboard.dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This method is used to clean the project data view
	 * 
	 */
	public void cleanProjectData() {
		TreeObject root[] = ProjectData.invisibleRoot.getChildren();
		TreeParent parent = (TreeParent) root[0];
		removeChildren(parent);
	}

	/**
	 * This method is used to call the approriate methods to enable or disable
	 * the rightclik menu operations
	 * 
	 * @param parent
	 *            This is of type Parent
	 */
	private void setEnableOrDisable(TreeParent parent) 
	{
		//System.out.println("Parent Type ::: "+parent.getType());
		if (parent.getType().equals(ProjectData.PROJECTNAME_S) && 
				parent.getParent().getType().equals(ProjectData.ROOT_S)) 
		{
			setEnableToGetProject();
			removeNode.setEnabled(false);
		} 
		else if (parent.getType().equals(ProjectData.ATOMICMETRIC_S)) 
		{
			setEnableToAtomic();
			//removeMapping.setEnabled(false);
		}
		else 
		{
			setDisableAll(parent);
		}
	}

	/**
	 * This method is used to enable the getproject action to get the project
	 * from the remote server
	 * 
	 */
	private void setEnableToGetProject() 
	{
		getProject.setEnabled(true);
		
//		removeProject.setEnabled(false);
//		copy.setEnabled(false);
	}

	/**
	 * This method is used to enable the copy operation for the mapping between
	 * atomic metric and the data object
	 * 
	 */
	private void setEnableToAtomic() 
	{
		getProject.setEnabled(false);

//		
//		removeProject.setEnabled(false);
//		copy.setEnabled(true);
		
		
	}

	/**
	 * This method is used to disable all the actions
	 * 
	 */
	private void setDisableAll(TreeParent parent) 
	{
		getProject.setEnabled(false);
		//removeMapping.setEnabled(true);
		if(parent.getType().equals(ProjectData.ROOT_S))
		{
			removeNode.setEnabled(false);
			//getProject.setEnabled(true);
		}
		else
		{
			removeNode.setEnabled(true);
		}
		
		
		
		
		
		
//		removeProject.setEnabled(false);
//		copy.setEnabled(false);
//		getSelectedProject.setEnabled(false);
	}

	/**
	 * This method is used to load the project from the file system specific to
	 * the mentioned the adapter and group
	 * 
	 * @param serverName
	 *            This string will hold the server name of the projects been
	 *            fetched
	 * @param adapterName
	 *            This string will hold the adapter name
	 * @param groupName
	 *            This string will hold the group name
	 */
	public void addNewCMSserverProjects(String serverName, String adapterName,
			String groupName) {
		TreeObject root[] = ProjectData.invisibleRoot.getChildren();
		TreeParent projectNode = new TreeParent(serverName);
		projectNode.setType(ProjectData.PROJECT_S);
		projectNode.setAdapterName(groupName + SASConstants.BACK_SLASH_s
				+ adapterName);
		((TreeParent) root[0]).addChild(projectNode);
		groupName = groupName + SASConstants.BACK_SLASH_s + adapterName;
		SaveAdapter openObj = new SaveAdapter();
		SolutionsAdapter adapterRoot = openObj.getSolutionsAdapterType();
		setProjectInfo(adapterRoot, serverName, adapterName);
	}

	/**
	 * This method is used to populate the project from the solutions adapter
	 * for each adapter seperate projects will be there
	 * 
	 * @param projectObj
	 *            This is of type TreeParent which contains the projects
	 */
	public void populateProjectFromSA(Object projectObj) {
		if (projectObj instanceof TreeParent) {
			TreeObject root[] = ProjectData.invisibleRoot.getChildren();
			TreeParent rootParent = (TreeParent) root[0];
			rootParent.addChild((TreeParent) projectObj);
			ProjectData.viewer_s.refresh(rootParent, true);
		}
	}

	/**
	 * This method is used to create the new server under the project tree which
	 * will be populate automatically once the new adapter added
	 * 
	 * @param serverName
	 *            This contains the server name on which tree is going to be
	 *            created
	 * @param adapterName
	 *            This contains the adapter name repersents under which adapter
	 *            the projects is going to be placed
	 * @param groupName
	 *            This contains the group name of the adapter
	 * @return This returns Object which contains the TreeParent of the newly
	 *         created server
	 */
	public Object createNewServerNode(String serverName, String adapterName,
			String groupName) {
		TreeObject root[] = ProjectData.invisibleRoot.getChildren();
		TreeParent newNode = new TreeParent(serverName);
		newNode.setType(ProjectData.PROJECTNAME_S);
		newNode.setAdapterName(groupName + SASConstants.BACK_SLASH_s
				+ adapterName);
		((TreeParent) root[0]).addChild(newNode);
		ProjectData.viewer_s.refresh(root[0], true);
		return (TreeParent) newNode;
	}

	/**
	 * This method is used to reset the adapter name once the adapter name in
	 * the solutions adapter view is been changed
	 * 
	 * @param adapterName
	 *            This string will hold the adapter name
	 * @param groupName
	 *            This string will hold the group name under which adapter is
	 *            present
	 */
	public void resetAdapterName(String adapterName, String groupName) {
		TreeObject root[] = ProjectData.invisibleRoot.getChildren();
		TreeObject parent[] = ((TreeParent) root[0]).getChildren();
		String oldFilePath = SASConstants.SAS_ROOT + parent[0].getAdapterName();
		root[0].setAdapterName(groupName + SASConstants.BACK_SLASH_s
				+ adapterName);
		String newFilePath = SASConstants.SAS_ROOT + groupName
				+ SASConstants.BACK_SLASH_s + adapterName;
		File oldFile = new File(oldFilePath);
		oldFile.renameTo(new File(newFilePath));
	}

	/**
	 * This method is used to remove the children of the particulare tree parent
	 * 
	 * @param parent
	 *            This of type Treeparent
	 */
	private void removeChildren(TreeParent parent) {
		if (parent.hasChildren()) {
			TreeObject[] children = parent.getChildren();
			for (int i = 0; i < children.length; i++) {
				parent.removeChild(children[i]);
			}
			ProjectData.viewer_s.refresh(parent, true);
		}
	}

	/**
	 * This method is used to set the focus
	 */
	public void setFocus() {
		viewer_s.getControl().setFocus();
	}

	/**
	 * This method is used to send the display object to the caller
	 * 
	 * @return This will return the display object
	 */
	public static Display getDisplay() {
		return display_s;
	}

	/**
	 * This method is used to get the project from the remote server and load
	 * all the projects in the tree
	 * 
	 */
/*	public void getProject() {
		IStructuredSelection select = (IStructuredSelection) viewer_s
				.getSelection();
		TreeParent parent = (TreeParent) select.getFirstElement();
		if (null != serverName_s) {
			String serverIP = new SASServerInforReader()
					.sasConfigReader(serverName_s);
			if (null != serverIP) {
				StringTokenizer tokenize = new StringTokenizer(serverIP, ":");
				serverIP = tokenize.nextToken();
				String serverPort = tokenize.nextToken();
				ConfigReader readerConfig = new ConfigReader();
				readerConfig.init();
				readerConfig.startParsing();
				// it will invoke the project request
				new SASPublisher().initializePublisherAndPublish(
						ConfigReader.bdmbridgeRequestTopic_s, serverIP,
						serverPort);// "Corona_CEReq_SAS_Topic","10.0.0.9","1099");
				viewer_s.expandToLevel(parent, 2);
				viewer_s.refresh(parent, true);
			}
		}
	}
*/
	/**
	 * This method is used to drag the atomic metric for mapping purpose. This
	 * will be dropped in the data object tree
	 * 
	 * @param event
	 *            This of type DragSourceEvent
	 */
	private void dragSelectedNode(DragSourceEvent event) {
		try {
			DatabaseBean beanObj = new DatabaseBean();
			IStructuredSelection select = (IStructuredSelection) viewer_s
					.getSelection();
			TreeObject selectedItem = (TreeObject) select.getFirstElement();
			ProjectData.selectedItem = selectedItem;
			if (selectedItem.getType().equals(ProjectData.ATOMICMETRIC_S)) {
				beanObj.setTypeofData(DataObjectView.mapAtomicType_s);
				AtomicMetricBean atomicBean = selectedItem.getAtomicBean();
				String project = selectedItem.getProjectName();
				String comp = selectedItem.getCompositeevent();
				String simple = selectedItem.getSimpleevent();
				String group = selectedItem.getGroupmetric();
				project = project + "|" + SASConstants.SAS_COMPOSITE + "="
						+ comp + "|" + SASConstants.SAS_SIMPLE + "=" + simple;
				if (null != group) {
					project = project + "|" + SASConstants.SAS_GROUP + "="
							+ group;
				}
				atomicBean.setProjectName(project);
				beanObj.setAtomicBean(atomicBean);
			}
			event.data = beanObj;
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method **dragSelectedNode()** in Method [ ProjectData.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

	/**
	 * This method will create a tree item under the atomic metric just to
	 * mention the mapping data object field
	 * 
	 * @param dataObjName
	 *            This will contains the name of the data object fieldd
	 */
	public void createMapping(String dataObjName) {
		TreeParent parent = (TreeParent) ProjectData.selectedItem;
		TreeParent child = new TreeParent(dataObjName);
		child.setType(DataObjectView.field_s);
		parent.addChild(child);
		ProjectData.viewer_s.refresh(parent, true);
		ProjectData.viewer_s.expandToLevel(parent, 1);
	}

	/**
	 * This method is used to save the Project information in the tree
	 * 
	 * @param projectType
	 *            This is of type SolutionsAdapter schema object
	 */
	public void getProjectInfo(SolutionsAdapter projectType) {
		TreeObject[] rootNode = ProjectData.invisibleRoot.getChildren();
		TreeObject[] projectNode = ((TreeParent) rootNode[0]).getChildren();
		SaveAndOpenProject saveObj = new SaveAndOpenProject();
		saveObj.saveProjectToFile(((TreeParent) projectNode[0]).getChildren(),
				projectType);
	}

	// create a method to store nested object data

	/**
	 * This method is used to set the project information in the tree from the
	 * xml file
	 * 
	 * @param adapterRoot
	 *            This is of type SolutionsAdapter schema object
	 * @param serverName
	 *            This will hold the server name
	 */
	public void setProjectInfo(SolutionsAdapter adapterRoot, String serverName,
			String adapterName) {
		TreeObject[] project = ProjectData.invisibleRoot.getChildren();
		if (((TreeParent) project[0]).hasChildren()) {
			TreeObject[] children = ((TreeParent) project[0]).getChildren();
			for (int i = 0; i < children.length; i++) {
				((TreeParent) project[0]).removeChild(children[i]);
			}
		}

		TreeParent newServer = new TreeParent(serverName);
		newServer.setType(ProjectData.PROJECT_S);
		newServer.setAdapterName(adapterName);
		((TreeParent) project[0]).addChild(newServer);
		SaveAndOpenProject openObj = new SaveAndOpenProject();
		openObj.openProjectFromFile((TreeParent) newServer, adapterRoot);
		ProjectData.viewer_s.refresh(project[0], true);
		SolutionAdapterView adapterObj = new SolutionAdapterView();
		adapterObj.addProjectInfoToSA(newServer);
	}
}

/**
 * The content provider class is responsible for providing objects to the view.
 * It can wrap existing objects in adapters or simply return objects as-is.
 * These objects may be sensitive to the current input of the view, or ignore it
 * and always show the same content (like Task List, for example).
 */
class TreeObject implements IAdaptable {
	/**
	 * Name of the Tree node
	 */
	private String name;

	/**
	 * Type of the event/metrics
	 */
	private String type;

	/**
	 * Value would differentialte different levels
	 */
	private String value;

	/**
	 * This is of type TreeParent
	 */
	private TreeParent parent;

	/**
	 * This is of type AtomicMetricBean which hold the atomicmetric information
	 */
	private AtomicMetricBean atomicBean = null;

	/**
	 * This will hold the project name
	 */
	private String projectName;

	/**
	 * This will hold the GUID of the Events/Metrics
	 */
	private String GUID;

	/**
	 * This will hold the compositeevent name
	 */
	private String compositeevent;

	/**
	 * This will hold the simpleevent name
	 */
	private String simpleevent;

	/**
	 * This will hold the groupmetric name
	 */
	private String groupmetric;

	/**
	 * This will hold the adapterName
	 */
	private String adapterName;

	/**
	 * This method is used to get the compositeevent name
	 * 
	 * @return string which contaisn the compositeevent name
	 */
	public String getCompositeevent() {
		return compositeevent;
	}

	/**
	 * This method is used to set the compositeevent name
	 * 
	 * @param compositeevent
	 *            string which contains compositeevnet name
	 */
	public void setCompositeevent(String compositeevent) {
		this.compositeevent = compositeevent;
	}

	/**
	 * This method is used to get the groupmetric name
	 * 
	 * @return string which contains the groupmetric name
	 */
	public String getGroupmetric() {
		return groupmetric;
	}

	/**
	 * This method is used to set the groupmetric name
	 * 
	 * @param groupmetric
	 *            This string contias the groupmetric name
	 */
	public void setGroupmetric(String groupmetric) {
		this.groupmetric = groupmetric;
	}

	/**
	 * This method is used to get the simpleevent name
	 * 
	 * @return string which contains the simpleevent name
	 */
	public String getSimpleevent() {
		return simpleevent;
	}

	/**
	 * This methos is used to set the simpleevent name
	 * 
	 * @param simpleevent
	 *            This string which contains the simpleevent name
	 */
	public void setSimpleevent(String simpleevent) {
		this.simpleevent = simpleevent;
	}

	/**
	 * This method is used to get the value. This value is used to identify the
	 * level of the tree
	 * 
	 * @return string which contains the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * This method is used to se the value. This value is used to identify the
	 * level of the tree
	 * 
	 * @param value
	 *            This string which contains the value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * This is the constructor of the TreeObject
	 * 
	 * @param name
	 *            string which contaisn the name of the TreeObject
	 */
	public TreeObject(String name) {
		this.name = name;
	}

	/**
	 * This method is used to get the name of the tree node
	 * 
	 * @return string which contains the name of the tree node
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method is used to set the name of the tree node
	 * 
	 * @param name
	 *            This string which contaisn the name of the tree node
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method is used to set the parent of the tree node
	 * 
	 * @param parent
	 *            This of type TreeParent which will hold parent object
	 */
	public void setParent(TreeParent parent) {
		this.parent = parent;
	}

	/**
	 * This method is used to get the parent object of the particular object
	 * 
	 * @return This is of type TreeParent
	 */
	public TreeParent getParent() {
		return parent;
	}

	/**
	 * This method is used to return the name of the TreeParen or TreeObject
	 * 
	 */
	public String toString() {
		return getName();
	}

	public Object getAdapter(Class key) {
		return null;
	}

	/**
	 * This method is used to get the type of the tree parent
	 * 
	 * @return stirng which contians the type of the tree parent
	 */
	public String getType() {
		return type;
	}

	/**
	 * This method is used to set the type of the tree parent
	 * 
	 * @param type
	 *            This string which contains the type of the tree parent
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * This method is used to get the AtomicMetricBean object
	 * 
	 * @return This is of type AtomicMetricBean
	 */
	public AtomicMetricBean getAtomicBean() {
		return atomicBean;
	}

	/**
	 * This method is used to set the AtomicMetricBean object
	 * 
	 * @param atomicBean
	 *            This is of type AtomicMetricBean
	 */
	public void setAtomicBean(AtomicMetricBean atomicBean) {
		this.atomicBean = atomicBean;
	}

	/**
	 * This method is used to get the Adapter Name
	 * 
	 * @return string which contains the Adapter Name
	 */
	public String getAdapterName() {
		return adapterName;
	}

	/**
	 * This method is used to set the Adapter Name
	 * 
	 * @param adapterName
	 *            This string which contains the Adapter Name
	 */
	public void setAdapterName(String adapterName) {
		this.adapterName = adapterName;
	}

	/**
	 * This method is used to get the Project Name
	 * 
	 * @return string which contains the project Name
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * This method is used to set the project name
	 * 
	 * @param projectName
	 *            This string which contains the project name
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * This method is used to get the GUID
	 * 
	 * @return string which contains the GUID
	 */
	public String getGUID() {
		return this.GUID;
	}

	/**
	 * This method is used to set the GUID
	 * 
	 * @param guid
	 *            This string contains the GUID
	 */
	public void setGUID(String guid) {
		this.GUID = guid;
	}
}

/**
 * This class used to maitain a arraylist which will contaisn the Tree item
 * informations . It extends TreeObject
 * 
 * @author Maha
 * 
 */
class TreeParent extends TreeObject {
	/**
	 * This Arraylist contains the objects of the Tree item
	 */
	private ArrayList<Object> children;

	/**
	 * This constructor is used to create a new tree item
	 * 
	 * @param name
	 */
	public TreeParent(String name) {
		super(name);
		children = new ArrayList<Object>();
	}

	/**
	 * This is methos is used to add a new child in the tree
	 * 
	 * @param child
	 *            This is of type TreeObject
	 */
	public void addChild(TreeObject child) {
		children.add(child);
		child.setParent(this);
	}

	/**
	 * This method is used to remove child from the tree
	 * 
	 * @param child
	 *            This is of tyep TreeObject which is going to be removed
	 */
	public void removeChild(TreeObject child) {
		children.remove(child);

		// child.setParent(null);
	}

	/**
	 * This method is used to get all the children under the tree
	 * 
	 * @return This returns the TreeObject array
	 */
	public TreeObject[] getChildren() {
		return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
	}

	/**
	 * This method is used to check fo the children under the selected tree item
	 * 
	 * @return This return true if children are present or else false
	 */
	public boolean hasChildren() {
		return children.size() > 0;
	}
}
