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
 *$Id: SolutionAdapterView.java,v 1.5 2006/08/07 04:49:42 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/SolutionAdapterView.java,v $
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
package com.rrs.corona.solutionsacceleratorstudio.solutionadapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.rrs.corona.server.adapterreceiver.SolutionAdapterReceiver;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.SASImages;
import com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView;
import com.rrs.corona.solutionsacceleratorstudio.messagehandler.BDMSASMessageHandler;
import com.rrs.corona.solutionsacceleratorstudio.messagehandler.ConfigReader;
//import com.rrs.corona.solutionsacceleratorstudio.messagehandler.SASServerInforReader;
import com.rrs.corona.solutionsacceleratorstudio.plugin.DatabaseViewer;
import com.rrs.corona.solutionsacceleratorstudio.plugin.DsReader;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;
// import com.rrs.corona.solutionsacceleratorstudio.plugin.TreeParent;
import com.rrs.corona.solutionsacceleratorstudio.project.LoadProject;
import com.rrs.corona.solutionsacceleratorstudio.project.ProjectData;
import com.rrs.corona.solutionsacceleratorstudio.publisher.JARPublisher;
import com.rrs.corona.solutionsacceleratorstudio.publisher.SASPublisher;

/**
 * This class is used to provide the Solutions Adapter view in the left hand
 * side of the SASPerspective.
 * <p>
 * This will create a new solutions adapter which contaisn the information about
 * the IntermediateDataobject and Datasource
 * </p>
 * <p>
 * By creating a new adapter under the group then this will ask for the CMS
 * server and Datasource to be populated in their respective view.
 * </p>
 * <p>
 * This view is mainly used to save and open the mapping relationship between
 * the Datasource,IntermediateDataobject and the Project
 * </p>
 * 
 * @author Maha
 * 
 */
public class SolutionAdapterView extends ViewPart {
	/**
	 * This of type TreeViewer on which tree is going to be contructed
	 */
	public static TreeViewer viewer_s;

	/**
	 * This string holds "root"
	 */
	public static final String ROOT_s = "root";

	/**
	 * This of type Action for right click menu for deleting existing server
	 */
	private Action delete_;

	/**
	 * This of type Action for right click menu for adding new server
	 */
	private Action add_Group;

	/**
	 * This of type Action for right click menu for editing the existing server
	 */
	private Action rename_;

	/**
	 * This of type Action for right click menu for adding new solution adapter
	 */
	private Action add_SAdapter_;

	/**
	 * This of type Action for right click menu for open an existing solution
	 * adapter
	 */
	private Action open_SAdapter_;

	/**
	 * This of type Action for right click menu for save
	 */
	private Action save_SAdapter_;

	/**
	 * This of type Action for right click menu for save as
	 */
	private Action saveAS_SAdapter_;

	/**
	 * This of type Action for right click menu for close an existing solution
	 * adapter
	 */
	private Action close_SAdapter_;

	/**
	 * Action to publish the SA adapter
	 */
	private Action publish_SAdapter;

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
	 * This string will hold the server name from the dialog box
	 */
	public static String serverName_s;

	/**
	 * This string will hold the data source name
	 */
	public static String dataSourceName_s;

	/**
	 * This string will hold the adapter name
	 */
	public static String adapterName_s;

	/**
	 * This TreeParent will hold the selected adapter in the tree
	 */
	public static TreeParent solutionAdapterName_s = null;

	/**
	 * This string will hold "DATAOBJECT" for displaying the icons
	 */
	public static String DATAOBJECT_s = "DATAOBJECT";

	/**
	 * This string will hold "GROUP" for displaying the icons
	 */
	public static String GROUP_s = "GROUP";

	/**
	 * This string will hold "PROJECT" for displaying the icons
	 */
	public static String PROJECT_s = "PROJECT";

	/**
	 * This string will hold "DATASOURCE" for displaying the icons
	 */
	public static String DSOBJ_s = "DATASOURCE";

	/**
	 * This string will hold "SA" for displaying the icons
	 */
	public static String SOLUTIONSADAPTER_s = "SA";

	public static BDMSASMessageHandler CONNECTION_s;

	/**
	 * This boolean will say that the adapter is created or not
	 */
	private static boolean isSolutionsAdapter_s = false;

	private static boolean isOpend_s = false;

	/**
	 * This string will hold the group name
	 */
	public static String groupName_s = null;

	public static IWorkbenchSite fSite;

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
		 * This method will initialize the tree root
		 * 
		 */
		private void initialize() {
			TreeParent root = new TreeParent("Solutions Adapter");
			root.setType(SolutionAdapterView.ROOT_s);
			invisibleRoot = new TreeParent("");
			invisibleRoot.addChild(root);
			// root.setParent (invisibleRoot);
			display_s = viewer_s.getTree().getDisplay();
			initializeGroupTree(root);
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
			Image imageKey = new Image(SolutionAdapterView.viewer_s.getTree()
					.getShell().getDisplay(), SASImages.SAROOT_s);
			if (obj instanceof TreeParent) {
				if (((TreeParent) obj).getType().equals(
						SolutionAdapterView.ROOT_s)) {// set the icon for the
					// root tree item
					imageKey = SASImages.getSAROOT_IMG();
				} else if (((TreeParent) obj).getType().equals(
						SolutionAdapterView.GROUP_s)) {// set the icon for the
					// group tree item
					imageKey = SASImages.getCMS_IMG();
				} else if (((TreeParent) obj).getType().equals(
						SolutionAdapterView.SOLUTIONSADAPTER_s)) {// set the
					// icon for
					// the
					// solutions
					// adapter
					// tree item
					imageKey = SASImages.getSA_IMG();
				} else if (((TreeParent) obj).getType().equals(
						SolutionAdapterView.DATAOBJECT_s)) {// set the icon for
					// the data object
					// tree item
					imageKey = SASImages.getGROUP_IMG();
				} else if (((TreeParent) obj).getType().equals(
						SolutionAdapterView.DSOBJ_s)) {// set the icon for the
					// data source tree item
					imageKey = SASImages.getDATASOURCE_IMG();
				}
			}
			// SolutionsacceleratorstudioPlugin.getImageDescriptor("icons/xx.gif");
			return imageKey;
		}
	}

	/**
	 * This class will call when the perspective is opened through the
	 * plugin.xml
	 */
	public void createPartControl(Composite parent) {
		try {
			viewer_s = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
					| SWT.V_SCROLL);
			viewer_s.setContentProvider(new ViewContentProvider());
			viewer_s.setLabelProvider(new ViewLabelProvider());
			viewer_s.setSorter(new NameSorter());
			viewer_s.setInput(getViewSite());
			viewer_s
					.addSelectionChangedListener(new ISelectionChangedListener() {
						public void selectionChanged(SelectionChangedEvent event) {
							IStructuredSelection selection = (IStructuredSelection) viewer_s
									.getSelection();
							TreeParent parent = (TreeParent) selection
									.getFirstElement();
							if (null != parent) {
								setEnableDisable(parent);
							}
						}
					});
			makeActions();
			hookContextMenu();
			hookDoubleClickAction();
			contributeToActionBars();
			fSite = getViewSite();
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**createPartControl()** in class [ AolutionAdapterView.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to load the group in the Data Object view while
	 * deleting the or reloading the solution adapter etc.,
	 * 
	 * @param parent
	 *            This of type TreeParent
	 */
	private void loadDataObjectTree(TreeParent parent) {
		DataObjectView loadGroup = new DataObjectView();
		if (parent.hasChildren()) {
			TreeObject childObj[] = parent.getChildren();
			for (int i = 0; i < childObj.length; i++) {
				TreeParent childTree = (TreeParent) childObj[i];
				if (childTree.getType()
						.equals(SolutionAdapterView.DATAOBJECT_s)) {
					loadGroup.populateGroup(childTree.getDataObjectInfo());
				}
			}
		}
	}

	/**
	 * This method is used to delete a tree item from the tree when the same
	 * item in the DataObject view is being deleted. This for group
	 * 
	 * @param dataObjectInfo
	 *            This of type Object which contains the object of the
	 *            solutionadapter tree object
	 */
	public void deleteFromDataObject(Object dataObjectInfo) {
		TreeObject[] treeObj = SolutionAdapterView.solutionAdapterName_s
				.getChildren();
		for (int i = 0; i < treeObj.length; i++) {
			if (dataObjectInfo.equals(treeObj[i].getDataObjectInfo())) {
				TreeParent parent = treeObj[i].getParent();
				parent.removeChild(treeObj[i]);
			}
		}
		SolutionAdapterView.viewer_s.refresh();
	}

	/**
	 * This method is used to populate the group under the solutionadapter tree
	 * as a child when the same group is being added in the DataObject View
	 * 
	 * @param dataObjectInfo
	 *            This is of type Object which inturn contains the DataObject
	 *            Group Tree object
	 * @param groupName
	 *            This will contains the name of the group
	 */
	public void populateSolutionAdapter(Object dataObjectInfo, String groupName) {
		TreeParent parent = (TreeParent) SolutionAdapterView.solutionAdapterName_s;
		if (null != parent) {
			TreeParent child = new TreeParent(groupName);
			child.setType(SolutionAdapterView.DATAOBJECT_s);
			child.setDataObjectInfo(dataObjectInfo);
			parent.addChild(child);
			SolutionAdapterView.viewer_s.expandToLevel(parent, 1);
			SolutionAdapterView.viewer_s.refresh(parent, true);
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
				SolutionAdapterView.this.fillContextMenu(manager);
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
		manager.add(add_Group);
		manager.add(new Separator());
		manager.add(add_SAdapter_);
		manager.add(open_SAdapter_);
		manager.add(save_SAdapter_);
		manager.add(close_SAdapter_);
		// manager.add (saveAS_SAdapter_);
		manager.add(new Separator());
		// manager.add (generate_code_);
		// manager.add (new Separator());
		manager.add(rename_);
		manager.add(delete_);
		manager.add (new Separator());
		//manager.add(close_SAdapter_);
		manager.add(publish_SAdapter);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	/**
	 * This method is used to add the tool menu
	 * 
	 * @param manager
	 *            This is of type IToolBarManager to add the tool bar menu item
	 */
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(rename_);
	}

	/**
	 * This method is used to initialize all the right click menu items and
	 * their operations
	 * 
	 */
	private void makeActions() {
		// adding cms server to the tree
		doAddGroup();
		// adding new solution adapter in the tree
		doAddAdapter();
		// open solution adapter in the tree
		doOpenadapter();
		// save the solution adapter
		doSave();
		// save As the solution adapter
		doSaveAs();
		// renaming cms server in the tree
		doRename();
		// deleting cms server from the tree
		doDelete();
		// close solution adapter ftom the tree
		doCloseadapter();
		// To publish the SA adapter
		doPublish();
	}

	/**
	 * This method is used to initialize the add cms server operation and also
	 * used to add a new cms server in the tree view
	 * 
	 */
	private void doAddGroup() {
		add_Group = new Action() {
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer_s
						.getSelection();
				TreeParent root = (TreeParent) selection.getFirstElement();
				if (null != root) {
					addNewGroup(root);
				}
			}
		};
		add_Group.setText("Create Group");
		add_Group.setToolTipText("Create New Group");
		add_Group.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_NEW_WIZARD));
	}

	/**
	 * This method is used to initialize the add adapter action and add an
	 * adapter in the tree view
	 * 
	 */
	private void doAddAdapter() 
	{
		add_SAdapter_ = new Action() {
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer_s
						.getSelection();
				TreeParent root = (TreeParent) selection.getFirstElement();
				
				if(checkForServer())
				{
					if(checkForDataSource())
					{
						if (null != root && checkForServer()) 
						{
							addNewAdapter(root);
						}
					}
					else
					{
						final String errMsg = "No datasource found, first create a DataSource.";
						MessageDialog.openError(new Shell(),"Error",errMsg);
					}
				}
				else
				{
					final String errMsg = "No server found, first add a server.";
					MessageDialog.openError(new Shell(),"Error",errMsg);
				}
				
//				if (null != root && checkForServer()) 
//				{
//					addNewAdapter(root);
//				}
//				else
//				{
//					final String errMsg = "Server not found, first add a server.";
//					MessageDialog.openError(new Shell(),"Error",errMsg);
//				}
			}
		};
		add_SAdapter_.setText("Add Adapter");
		add_SAdapter_.setToolTipText("Add new Solution Adapter");
		add_SAdapter_.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_OPEN_MARKER));
	}
	
	private boolean checkForDataSource()
	{
		boolean checkDsFlag = false;
		try
		{
			DsReader dsObj = new DsReader();
			ArrayList listOfDs = dsObj.getDataSourceNames();
			if(listOfDs == null || listOfDs.size()==0)
			{
				checkDsFlag = false;
			}
			else
			{
				checkDsFlag = true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkDsFlag;
	}
	
	private boolean checkForServer()
	{
		boolean checkServerFlag = false;
		try
		{
			ReadAndWriteXML readObj = new ReadAndWriteXML();
			String servers[] = readObj.getCmsServer();
			if(servers == null)
			{
				checkServerFlag = false;
				//System.out.println("Server is null");
			}
			else
			{
				checkServerFlag = true;
				//System.out.println("Server is not null ");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkServerFlag;
	}

	/**
	 * This method is used to initialize the open adapter and also open the
	 * specified in the user interface
	 * 
	 */
	private void doOpenadapter() {
		open_SAdapter_ = new Action() {
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer_s
						.getSelection();
				TreeParent root = (TreeParent) selection.getFirstElement();
				if (null != root) {
					openSolutionsAdapter(root);
				}
			}
		};
		open_SAdapter_.setText("Open Adapter");
		open_SAdapter_.setToolTipText("Open Adapter");
		open_SAdapter_.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_OPEN_MARKER));
	}

	/**
	 * This method is used to instatiate the zave adapter Action and aslo call
	 * the appropriate method to save the entire adapter with other view
	 * informations
	 * 
	 */
	private void doSave() {
		save_SAdapter_ = new Action() {
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer_s
						.getSelection();
				final TreeParent root = (TreeParent) selection
						.getFirstElement();
				if (null != root) {
					SaveAdapter saveObj = new SaveAdapter();
					saveObj.saveAdapterAsXML(root, true);
				}
			}
		};
		save_SAdapter_.setText("Save Adapter");
		save_SAdapter_.setToolTipText("Save Adapter");
		save_SAdapter_.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_OPEN_MARKER));
	}

	private void doSaveAs() {
		saveAS_SAdapter_ = new Action() {
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer_s
						.getSelection();
				TreeParent root = (TreeParent) selection.getFirstElement();
				if (null != root) {

				}
			}
		};
		saveAS_SAdapter_.setText("Save As Adapter");
		saveAS_SAdapter_.setToolTipText("Save As Adapter");
		saveAS_SAdapter_.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_OPEN_MARKER));
	}

	/**
	 * This method is used to instantiate the close adapter Action and also call
	 * the appropriate method to close the specified adapter from the tree view
	 * 
	 */
	private void doCloseadapter() {
		close_SAdapter_ = new Action() {
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer_s
						.getSelection();
				TreeParent root = (TreeParent) selection.getFirstElement();
				if (null != root) {
					closeAdapter(root);
				}
			}
		};
		close_SAdapter_.setText("Close Adapter");
		close_SAdapter_.setToolTipText("Close Adapter");
		close_SAdapter_.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_OPEN_MARKER));
	}

	/**
	 * This method is used to initialize the delete action and also delete the
	 * item from the tree
	 * 
	 */
	private void doDelete() 
	{
		delete_ = new Action() {
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer_s
						.getSelection();
				TreeParent root = (TreeParent) selection.getFirstElement();
				if (null != root) 
				{
					final boolean deleteFlag = MessageDialog.openConfirm(new Shell(),"Warning","Do you want to delete ?");
					if(deleteFlag)
					{
						deleteFromTree(root);
					}
					else
					{
						//do nothing
					}
				}
			}
		};
		delete_.setText("Delete");
		delete_.setToolTipText("Delete");
		delete_.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
	}

	/**
	 * This method is used to open the solutions adapter from an xml file and
	 * also populate the information in the tree of all the views
	 * 
	 * @param adapterObj
	 *            This of type TreeParent which contains the adapter node
	 */
	private void openSolutionsAdapter(TreeParent adapterObj) {
		SaveAdapter openObj = new SaveAdapter();
		openObj.openAdapterFromFile(adapterObj);
		if (adapterObj.hasChildren()) {
			adapterObj.setStatus(true);
			SolutionAdapterView.isSolutionsAdapter_s = true;
			setEnableSave();
		}
	}

	/**
	 * This method is used to set enable save action in the right click menu
	 * 
	 */
	private void setEnableSave() {
		delete_.setEnabled(true);
		add_Group.setEnabled(false);
		rename_.setEnabled(true);
		add_SAdapter_.setEnabled(false);
		open_SAdapter_.setEnabled(false);
		close_SAdapter_.setEnabled(true);
		save_SAdapter_.setEnabled(true);
		saveAS_SAdapter_.setEnabled(true);
		publish_SAdapter.setEnabled(true);
	}

	/**
	 * This method is used to delete the selected tree item from the tree and
	 * also delete the group folder from the file system if group is selected
	 * and delete the adapter folder if adapter is selected in the tree
	 * 
	 * @param root
	 *            This is of type TreeParent which will be deleted from the tree
	 */
	private void deleteFromTree(TreeParent root) 
	{
		if (root.getType().equals(SolutionAdapterView.SOLUTIONSADAPTER_s)) 
		{
			SolutionAdapterView.isSolutionsAdapter_s = false;
			SolutionAdapterView.solutionAdapterName_s = null;
			DataObjectView reset = new DataObjectView();
			reset.cleanDataObjectTree();
			cleanOtherView();
			LoadProject loadObj = new LoadProject();
			String filePath = SASConstants.SAS_ROOT
					+ root.getParent().getName() + SASConstants.BACK_SLASH_s
					+ root.getName();
			loadObj.deleteFolder(new File(filePath));
			BDMSASMessageHandler.deleteFromList();
		} else if (root.getType().equals(SolutionAdapterView.GROUP_s)) {
			SolutionAdapterView.isSolutionsAdapter_s = false;
//			SASServerInforReader serverObj = new SASServerInforReader();
//			serverObj.removeServerInfo(root.getName());
			cleanOtherView();
			LoadProject loadObj = new LoadProject();
			loadObj.deleteFolder(new File(SASConstants.SAS_ROOT
					+ root.getName()));
			BDMSASMessageHandler.deleteFromList();
		} else if (root.getType().equals(SolutionAdapterView.DSOBJ_s)) {
			new DatabaseViewer().removeDsFromSA(root.getDataSourceInfo());
			SaveAdapter saveObj = new SaveAdapter();
			saveObj.saveAdapterAsXML(root.getParent(), false);
		} else if (root.getType().equals(SolutionAdapterView.DATAOBJECT_s)) {
			new DataObjectView().removeDataObjectFromSA(root
					.getDataObjectInfo());
		}
		TreeParent parent = root.getParent();
		parent.removeChild(root);
		SolutionAdapterView.viewer_s.refresh();

	}

	/**
	 * This method is used to close the selected adapter and also clean the
	 * other views
	 * 
	 * @param parent
	 *            This is of type TreeParent which is going to be closed
	 */
	private void closeAdapter(TreeParent parent) {
		parent.setStatus(false);
		if (parent.hasChildren()) {
			TreeObject[] children = parent.getChildren();
			for (int i = 0; i < children.length; i++) {
				parent.removeChild(children[i]);
			}
			SolutionAdapterView.viewer_s.refresh(parent, true);
		}
		cleanOtherView();
		setEnableForOpen();
	}

	/**
	 * This method is used to enable the open action in the right click menu
	 * 
	 */
	private void setEnableForOpen() {
		delete_.setEnabled(true);
		add_Group.setEnabled(false);
		rename_.setEnabled(false);
		add_SAdapter_.setEnabled(false);
		open_SAdapter_.setEnabled(true);
		close_SAdapter_.setEnabled(false);
		save_SAdapter_.setEnabled(false);
		saveAS_SAdapter_.setEnabled(false);
		publish_SAdapter.setEnabled(false);
	}

	/**
	 * This method is used to clean other tree view
	 * 
	 */
	private void cleanOtherView() {
		ProjectData projObj = new ProjectData();
		projObj.cleanProjectData();
		DataObjectView dataObj = new DataObjectView();
		dataObj.cleanDataObjectTree();
		DatabaseViewer baseObj = new DatabaseViewer();
		baseObj.cleanDatabaseView();
	}

	/**
	 * Method used to delete a DataSource from the Solution Adapter view
	 * 
	 */
	public void removeDsFromSa() {
		// This method is used to remove DataSource from SolutionAdapter
		IStructuredSelection selection = (IStructuredSelection) viewer_s
				.getSelection();
		TreeParent root = (TreeParent) selection.getFirstElement();

		if (root.getType().equals(SolutionAdapterView.SOLUTIONSADAPTER_s)) {
			TreeObject[] saTreeObject = (TreeObject[]) root.getChildren();
			for (int k = 0; k < saTreeObject.length; k++) {
				TreeParent saTreeParent = (TreeParent) saTreeObject[k];
				root.removeChild(saTreeObject[k]);
			}
			SolutionAdapterView.viewer_s.refresh();
		} else if (root.getType().equals(SolutionAdapterView.GROUP_s)) {
			TreeObject[] tObject = (TreeObject[]) root.getChildren();
			for (int i = 0; i < tObject.length; i++) {
				TreeParent childParent = (TreeParent) tObject[i];
				TreeObject[] childObject = (TreeObject[]) childParent
						.getChildren();
				for (int j = 0; j < childObject.length; j++) {
					childParent.removeChild(childObject[j]);
				}
			}
			SolutionAdapterView.viewer_s.refresh();
		} else if (root.getType().equals(SolutionAdapterView.DSOBJ_s)) {
			TreeParent parent = root.getParent();
			parent.removeChild(root);
			SolutionAdapterView.viewer_s.refresh();
		}
		SolutionAdapterView.viewer_s.refresh(root, true);

	}

	/**
	 * 
	 * This method is used to initialize the rename action and also doing the
	 * operation of renaming the selected item from the tree
	 * 
	 */
	private void doRename() {
		rename_ = new Action() {
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer_s
						.getSelection();
				TreeParent root = (TreeParent) selection.getFirstElement();
				if (null != root) {
					renameDialog(root);
				}
			}
		};
		rename_.setText("Rename");
		rename_.setToolTipText("Rename");
		rename_.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD));
	}

	/**
	 * Method to publish the solution adapter
	 */
	private void doPublish() {

		publish_SAdapter = new Action() {
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer_s
						.getSelection();
				TreeParent root = (TreeParent) selection.getFirstElement();
				if (null != root) {

					String serverName = root.getServerName();
					HashMap hm = new HashMap();
					hm = new ReadAndWriteXML().getServerInfo(root
							.getServerName());
					String ipAdrs = (String) hm
							.get(ReadAndWriteXML.IPADDRESS_s);
					String portNo = (String) hm.get(ReadAndWriteXML.PORTNO_s);
					JARPublisher publisher = new JARPublisher(ipAdrs, portNo);
				}
			}
		};
		publish_SAdapter.setText("Publish");
	}

	/**
	 * This method is used to rename the group or adapter
	 * 
	 * @param root
	 *            This of type TreeParent to be renamed
	 */
	private void renameDialog(TreeParent root) 
	{// rename the group tree item
		
		if (root.getType().equals(SolutionAdapterView.GROUP_s)) {
			// ArrayList saNamesList = getPresentSANames();//new code 29.06.06
			ArrayList grpNamesList = getPresentGroups();// new code 29.06.06
			SolutionAdapterView.groupName_s = root.getName();
			// EditSAGroupDialog renameDialog = new EditSAGroupDialog(viewer_s
			// .getControl().getShell(), root.getName());

			EditSAGroupDialog renameDialog = new EditSAGroupDialog(viewer_s
					.getControl().getShell(), root.getName(), grpNamesList);

			renameDialog.open();
			String oldGroup = root.getName();
			root.setName(SolutionAdapterView.groupName_s);
			renameGroupFolder(oldGroup, root.getName());
			SolutionAdapterView.viewer_s.refresh(root, true);
		}// rename the solutions adapter tree item
		else if (root.getType().equals(SolutionAdapterView.SOLUTIONSADAPTER_s)) 
		{
			SolutionAdapterView.serverName_s = root.getServerName();
			SolutionAdapterView.adapterName_s = root.getName();
			SolutionAdapterView.dataSourceName_s = root.getDataSource();
			EditAdapterDialog editDialog = new EditAdapterDialog(
					SolutionAdapterView.viewer_s.getControl().getShell());
			editDialog.open();
			root.setName(SolutionAdapterView.adapterName_s);
			ProjectData resetAdapterName = new ProjectData();
			resetAdapterName.resetAdapterName(root.getName(), root.getParent()
					.getName());
			SolutionAdapterView.viewer_s.refresh(root, true);
		}
		else if(root.getType().equals(SolutionAdapterView.DSOBJ_s))
		{
			//System.out.println("DataSource is selected ........");
		}
	}
	
	public void renameDataSource(final String oldDSName,final String newDSName) 
	{
		
		IStructuredSelection selection = (IStructuredSelection) viewer_s
				.getSelection();
		TreeParent root = (TreeParent) selection.getFirstElement();

		if (root.getType().equals(SolutionAdapterView.SOLUTIONSADAPTER_s)) 
		{
//			System.out.println("Solutions adapter called");
//			System.out.println("root parent name ::: "+root.getParent().getName());
			
			root = root.getParent();
//			System.out.println("Now root.getname() ::: "+root.getName());
			TreeObject[] tObject = (TreeObject[]) root.getChildren();
			for (int i = 0; i < tObject.length; i++) {
				TreeParent childParent = (TreeParent) tObject[i];
				//childParent.setName(newDSName);
				TreeObject[] childObject = (TreeObject[]) childParent
						.getChildren();
				for (int j = 0; j < childObject.length; j++) 
				{
					//childParent.removeChild(childObject[j]);
					TreeParent dsParent1 = (TreeParent)childObject[j];
					dsParent1.setName(newDSName);
//					System.out.println("child object name ::: "+dsParent1.getName());
					if((dsParent1.getName()).equals(oldDSName))
							{
								dsParent1.setName(newDSName);
							}
					else
					{
						// do nothing
					}
					
					
					
					
					//childParent.setName(renamedDs);
				}
			}
			SolutionAdapterView.viewer_s.refresh();
			
			
			
			
			
			
//			TreeObject[] saTreeObject = (TreeObject[]) root.getChildren();
//			for (int k = 0; k < saTreeObject.length; k++) 
//			{
//				TreeParent saTreeParent = (TreeParent) saTreeObject[k];
//				//root.removeChild(saTreeObject[k]);
//					//root.setName(renamedDs);
//					saTreeParent.setName(renamedDs);
//			}
//			SolutionAdapterView.viewer_s.refresh();
//			TreeObject[] tObject = (TreeObject[]) root.getChildren();
//			for (int i = 0; i < tObject.length; i++) {
//				TreeParent childParent = (TreeParent) tObject[i];
//				TreeObject[] childObject = (TreeObject[]) childParent
//						.getChildren();
//				for (int j = 0; j < childObject.length; j++) 
//				{
//					//childParent.removeChild(childObject[j]);
//					TreeParent dsParent1 = (TreeParent)childObject[j];
//					TreeObject[] adapterObj = (TreeObject[])dsParent1.getChildren();
//					for(int l=0 ; l<adapterObj.length;l++)
//					{
//						TreeParent dsParent = (TreeParent)adapterObj[l];
//						dsParent.setName(renamedDs);
//					}
//					//dsParent1.setName(renamedDs);
//					
//					
//					
//					//childParent.setName(renamedDs);
//				}
//			}
//			SolutionAdapterView.viewer_s.refresh();
//			
//			TreeObject[] tObject = (TreeObject[]) root.getChildren();
//			for (int i = 0; i < tObject.length; i++) {
//				TreeParent childParent = (TreeParent) tObject[i];
//				System.out.println(childParent.getType());
//				childParent.setName(newDSName);
//				
////				TreeObject[] childObject = (TreeObject[]) childParent
////						.getChildren();
////				for (int j = 0; j < childObject.length; j++) 
////				{
////					//childParent.removeChild(childObject[j]);
////					TreeParent dsParent1 = (TreeParent)childObject[j];
////					dsParent1.setName(renamedDs);
////					
////					
////					
////					//childParent.setName(renamedDs);
////				}
//			}
//			SolutionAdapterView.viewer_s.refresh();
			
			
			
			
			
			
		} else if (root.getType().equals(SolutionAdapterView.GROUP_s)) 
		{
//			System.out.println("group called");
			TreeObject[] tObject = (TreeObject[]) root.getChildren();
			for (int i = 0; i < tObject.length; i++) {
				TreeParent childParent = (TreeParent) tObject[i];
				TreeObject[] childObject = (TreeObject[]) childParent
						.getChildren();
				for (int j = 0; j < childObject.length; j++) 
				{
					//childParent.removeChild(childObject[j]);
					TreeParent dsParent1 = (TreeParent)childObject[j];
					dsParent1.setName(newDSName);
					
					
					
					//childParent.setName(renamedDs);
				}
			}
			SolutionAdapterView.viewer_s.refresh();
		} else if (root.getType().equals(SolutionAdapterView.DSOBJ_s)) 
		{
//			System.out.println("DataSource called");
			TreeParent parent = root.getParent();
			TreeObject[] adapterObject = (TreeObject[])parent.getChildren();
			for(int k=0;k<adapterObject.length;k++)
			{
				TreeParent dsParent = (TreeParent)adapterObject[k];
				dsParent.setName(newDSName);
			}
			
			//parent.removeChild(root);
			//parent.setName(renamedDs);
			SolutionAdapterView.viewer_s.refresh();
		}
		SolutionAdapterView.viewer_s.refresh(root, true);

	}

	/**
	 * This method is used to rename the group directory present in the
	 * filesystem
	 * 
	 * @param oldGroup
	 *            This string contains the old group directory name
	 * @param newGroup
	 *            This string contains the new group directory name
	 */
	private void renameGroupFolder(String oldGroup, String newGroup) {
		File oldFile = new File(SASConstants.SAS_ROOT + oldGroup);
		oldFile.renameTo(new File(SASConstants.SAS_ROOT + newGroup));
	}

	/**
	 * This method is used to add a new adapter under the specified group and
	 * also allow the other views to do their functionality
	 * 
	 * @param root
	 *            This is of type TreeParent which is the root for the new
	 *            adapter to be added
	 */
	private void addNewAdapter(TreeParent root) 
	{
		ArrayList adapterList = getPresentSANames();
//		System.out.println("List of Adapters ::: "+adapterList);
		
		AddNewAdapterDialog dialog = new AddNewAdapterDialog(
				SolutionAdapterView.viewer_s.getControl().getShell(),adapterList);
		dialog.open();
		
//		AddNewAdapterDialog dialog = new AddNewAdapterDialog(
//				SolutionAdapterView.viewer_s.getControl().getShell());
//		dialog.open();
		
		
		if (null != SolutionAdapterView.adapterName_s
				&& null != SolutionAdapterView.dataSourceName_s
				&& null != SolutionAdapterView.serverName_s) {
			try {
				TreeParent parent = new TreeParent(
						SolutionAdapterView.adapterName_s);
				parent.setType(SolutionAdapterView.SOLUTIONSADAPTER_s);
				parent.setDataSource(SolutionAdapterView.dataSourceName_s);
				parent.setServerName(SolutionAdapterView.serverName_s);
				parent.setStatus(true);
				// This is to populate the project view with
				// the server name as the tree root name
				root.addChild(parent);
				// in order to populate the database view with
				// the datasource information
				// and also populate the datasource name
				// in the solution adapter view
				DatabaseViewer databaseObj = new DatabaseViewer();
				Object dataSourceObj = databaseObj
						.populateDataSourceFromSA(parent.getDataSource());
				TreeParent child = new TreeParent(parent.getDataSource());
				child.setType(SolutionAdapterView.DSOBJ_s);
				child.setDataSourceInfo(dataSourceObj);
				parent.addChild(child);
				parent.setDataSourceInfo(dataSourceObj);
				SolutionAdapterView.solutionAdapterName_s = parent;
				SolutionAdapterView.viewer_s.expandToLevel(root, 1);
				SolutionAdapterView.viewer_s.refresh();
				SolutionAdapterView.isSolutionsAdapter_s = true;
				new File(SASConstants.SAS_ROOT + parent.getParent().getName()
						+ SASConstants.BACK_SLASH_s + parent.getName())
						.mkdirs();
				ProjectData reload = new ProjectData();
				reload.cleanProjectData();
				parent.setProjectInfo(reload.createNewServerNode(parent
						.getServerName(), parent.getName(), parent.getParent()
						.getName()));
				SaveAdapter saveObj = new SaveAdapter();
				saveObj.saveAdapterAsXML(parent, false);
			} catch (Exception e) {
				final String errMsg = "Exception thrown in Method "
						+ "**addNewAdapter()** in class [ AolutionAdapterView.java ]";
				SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg,
						e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method is used to load the data source specific to the adapter
	 * 
	 * @param parent
	 *            This of type Treeparent which contains the datasource tree
	 *            object
	 */
	private void loadDataSourceTree(TreeParent parent) {
		if (parent.hasChildren()) 
		{
			//System.out.println("I am in load dataSource........");
			TreeObject[] child = parent.getChildren();
			for (int i = 0; i < child.length; i++) {
				if (child[i].getType().equals(SolutionAdapterView.DSOBJ_s)) {
					DatabaseViewer dsObj = new DatabaseViewer();
					dsObj.cleanDatabaseView();
					dsObj.populateDsInfo(child[i].getDataSourceInfo());
				}
			}
		}
	}

	/**
	 * This method is used to add a new group in the tree
	 * 
	 * @param root
	 *            This is of type TreeParent
	 */
	private void addNewGroup(TreeParent root) 
	{
		//Returns the existing names of the SaolutionAdapter
		ArrayList saNamesList = getPresentSANames();
		
		// SolutionsAdapterGroupDialog dialog = new SolutionsAdapterGroupDialog(
		// SolutionAdapterView.viewer_s.getControl().getShell());

		SolutionsAdapterGroupDialog dialog = new SolutionsAdapterGroupDialog(
				SolutionAdapterView.viewer_s.getControl().getShell(),
				saNamesList);
		dialog.open();
		if (null != SolutionAdapterView.groupName_s) {
			TreeParent groupNode = new TreeParent(
					SolutionAdapterView.groupName_s);
			groupNode.setType(SolutionAdapterView.GROUP_s);
			root.addChild(groupNode);
			SolutionAdapterView.viewer_s.refresh(root, true);
			SolutionAdapterView.viewer_s.expandToLevel(root, 1);
			new File(SASConstants.SAS_ROOT + groupNode.getName()).mkdirs();
		}
	}

	/**
	 * This method is used to obtain the list of names of the Solution Adapter
	 * 
	 * @return an ArrayList of already present names of the Solution Adapters
	 */
	private ArrayList getPresentSANames() {
		ArrayList saNames = new ArrayList();
		IStructuredSelection selection = (IStructuredSelection) viewer_s
				.getSelection();
		TreeParent firstRoot = (TreeParent) selection.getFirstElement();
		if (firstRoot.hasChildren()) {
			TreeObject[] saObjects = (TreeObject[]) firstRoot.getChildren();
			for (int i = 0; i < saObjects.length; i++) {
				TreeParent saParent = (TreeParent) saObjects[i];
				final String saName = saParent.getName();
				saNames.add(saName.toLowerCase());
			}
		}
		return saNames;
	}

	private ArrayList getPresentGroups() {
		ArrayList grpNames = new ArrayList();
		IStructuredSelection selection = (IStructuredSelection) viewer_s
				.getSelection();
		TreeParent currentRoot = (TreeParent) selection.getFirstElement();
		TreeParent firstRoot = currentRoot.getParent();

		if (firstRoot.hasChildren()) {
			TreeObject[] saObjects = (TreeObject[]) firstRoot.getChildren();
			for (int i = 0; i < saObjects.length; i++) {
				TreeParent saParent = (TreeParent) saObjects[i];
				final String saName = saParent.getName();
				grpNames.add(saName.toLowerCase());
			}
		}
		return grpNames;
	}

	private void hookDoubleClickAction() {
		viewer_s.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
			}
		});
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
	 * This method is used to do the enable or disable the right click menu
	 * according to the levels of tree item
	 * 
	 * @param parent
	 *            This is of type TreeParent
	 */
	private void setEnableDisable(TreeParent parent) 
	{
//		System.out.println("In Solution adapter view ::: parent type"+parent.getType());
		if (parent.getType().equals(SolutionAdapterView.GROUP_s)) 
		{
			setEnableOrDisableForGroup(parent);
		}
		else if (parent.getType().equals(SolutionAdapterView.ROOT_s)) 
		{
			setEnableOrDisableForRoot(parent);
		}
		else if (parent.getType().equals(
				SolutionAdapterView.SOLUTIONSADAPTER_s)) 
		{
			setEnableOrDisableForSA(parent);
		}
		else if(parent.getType().equals(SolutionAdapterView.DSOBJ_s))
		{
			setEnableOrDisableForDataSource(parent);
		}
	}
	
	public void setEnableOrDisableForDataSource(final TreeParent parent)
	{
		delete_.setEnabled(true);
		add_Group.setEnabled(false);
		rename_.setEnabled(false);
		add_SAdapter_.setEnabled(false);
		open_SAdapter_.setEnabled(false);
		close_SAdapter_.setEnabled(false);
		save_SAdapter_.setEnabled(false);
		saveAS_SAdapter_.setEnabled(false);
		publish_SAdapter.setEnabled(false);
	}

	/**
	 * This method is used to disable/enable the right click menu for the tree
	 * item group
	 * 
	 * @param parent
	 *            This is of type TreeParent which is the selected group tree
	 *            item
	 */
	private void setEnableOrDisableForGroup(TreeParent parent) {
		delete_.setEnabled(true);
		add_Group.setEnabled(false);
		rename_.setEnabled(true);
		add_SAdapter_.setEnabled(true);
		open_SAdapter_.setEnabled(false);
		close_SAdapter_.setEnabled(false);
		save_SAdapter_.setEnabled(false);
		saveAS_SAdapter_.setEnabled(false);
		publish_SAdapter.setEnabled(false);
		if (parent.hasChildren()) {
			SolutionAdapterView.isSolutionsAdapter_s = true;
		} else {
			SolutionAdapterView.isSolutionsAdapter_s = false;
		}
	}

	/**
	 * This method is used to disable/enable the right click menu for the tree
	 * item Root
	 * 
	 * @param parent
	 *            This is of type TreeParent which is the selected Root tree
	 *            item
	 */
	private void setEnableOrDisableForRoot(TreeParent parent) {
		delete_.setEnabled(false);
		add_Group.setEnabled(true);
		rename_.setEnabled(false);
		add_SAdapter_.setEnabled(false);
		open_SAdapter_.setEnabled(false);
		close_SAdapter_.setEnabled(false);
		save_SAdapter_.setEnabled(false);
		saveAS_SAdapter_.setEnabled(false);
		publish_SAdapter.setEnabled(false);
	}

	/**
	 * This method is used to disable/enable the right click menu for the tree
	 * item Adapter
	 * 
	 * @param parent
	 *            This is of type TreeParent which is the selected Adapter tree
	 *            item
	 */
	private void setEnableOrDisableForSA(TreeParent parent) {
		if (parent.isStatus()) {
			delete_.setEnabled(true);
			add_Group.setEnabled(false);
			rename_.setEnabled(true);
			add_SAdapter_.setEnabled(false);
			open_SAdapter_.setEnabled(false);
			close_SAdapter_.setEnabled(true);
			save_SAdapter_.setEnabled(true);
			saveAS_SAdapter_.setEnabled(true);
			publish_SAdapter.setEnabled(true);
			populateOtherView(parent);
		} else {
			delete_.setEnabled(true);
			add_Group.setEnabled(false);
			rename_.setEnabled(false);
			add_SAdapter_.setEnabled(false);
			open_SAdapter_.setEnabled(true);
			close_SAdapter_.setEnabled(false);
			save_SAdapter_.setEnabled(false);
			saveAS_SAdapter_.setEnabled(false);
			publish_SAdapter.setEnabled(false);
		}
		SolutionAdapterView.solutionAdapterName_s = parent;
	}
	
	public boolean checkDsInSaViewer(final String name_param)
	{
		boolean checkFlag = false;
		try
		{
			TreeObject[] treeObj = (TreeObject[])SolutionAdapterView.invisibleRoot.getChildren();
	first_Loop:		for(int i=0;i<treeObj.length;i++)
			{
				TreeParent treeParent = (TreeParent)treeObj[i];
				TreeObject[] saObj = (TreeObject[])treeParent.getChildren();
	sa_Loop:	for(int j=0;j<saObj.length;j++)
				{
					TreeParent saParent = (TreeParent)saObj[j];
					TreeObject[] adapterObj = (TreeObject[])saParent.getChildren();
	adapter_Loop:	for(int k=0;k<adapterObj.length;k++)
					{
						TreeParent adapterParent = (TreeParent)adapterObj[k];
						//System.out.println("adapterParent name ::: "+adapterParent.getName());
						TreeObject[] dsObj = (TreeObject[])adapterParent.getChildren();
						for(int l=0;l<dsObj.length;l++)
						{
							TreeParent dsParent = (TreeParent)dsObj[l];
							//System.out.println("DS Parent name ::: "+dsParent.getName());
							//System.out.println("parameter name ::: "+name_param);
							if((dsParent.getName()).equals(name_param))
							{
								checkFlag = true;
								break first_Loop;
							}
							else
							{
								checkFlag = false;
							}
						}
						
						
//						if((adapterParent.getName()).equals(name_param))
//						{
//							checkFlag = true;
//							break first_Loop;
//						}
//						else
//						{
//							checkFlag = false;
//						}
					}
				}
					
				//System.out.println("TreeParent name in SolutionsAdapterView for checking:::"+treeParent.getName());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return checkFlag;
	}
	
	
	
	
	
	

	/**
	 * This method is used to call the respective methods for populating other
	 * view informtaion from the selected solutions adapter
	 * 
	 * @param parent
	 *            This is of type TreeParent which contains the object of
	 *            Solutions Adapter
	 */
	private void populateOtherView(TreeParent parent) {
		try {
			DataObjectView reset = new DataObjectView();
			reset.cleanDataObjectTree();
			loadDataObjectTree(parent);
			
			if(parent.hasChildren())
			{
				loadDataSourceTree(parent);
			}
			else
			{
				new DatabaseViewer().cleanDatabaseView();
			}
			
			//loadDataSourceTree(parent);
			
			SolutionAdapterView.solutionAdapterName_s = parent;
			ProjectData reload = new ProjectData();
			reload.cleanProjectData();
			reload.populateProjectFromSA(parent.getProjectInfo());
			SolutionAdapterView.isSolutionsAdapter_s = true;

		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**populateOtherView()** in class [ SolutionAdapterView.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

	/**
	 * This method will return the folder structure after the sasroot upto the
	 * adapter
	 * 
	 * @return This string will hold the path
	 */
	public String getFolderStructure() {
		String adapterName = SolutionAdapterView.solutionAdapterName_s
				.getName();
		String groupName = SolutionAdapterView.solutionAdapterName_s
				.getParent().getName();
		return groupName + SASConstants.BACK_SLASH_s + adapterName;
	}

	/**
	 * This method is used to tell the other views that solutions adapter is
	 * created in the solutions adapter tree view. So that other view can be
	 * able to do their operations
	 * 
	 * @return This will return true if flag is set or otherwise false
	 */
	public static boolean getSolutionsAdapterFlag() {
		return SolutionAdapterView.isSolutionsAdapter_s;
	}

	/**
	 * This method is used to populate the data source information and its name
	 * in the tree below the respective solutions adapter from the data source
	 * tree view
	 * 
	 * @param treeDsName
	 *            This contians the name of the data source will the child of
	 *            solutions adapter tree item
	 * @param parent
	 *            This contains the object of type TreeParent of the data source
	 *            tree view
	 */
	public void addDataSourceToSA(String treeDsName, Object parent) {
		TreeParent root = SolutionAdapterView.solutionAdapterName_s;

		if (root.getType().equals(SolutionAdapterView.SOLUTIONSADAPTER_s)) {
			TreeParent treeObj = new TreeParent(treeDsName);
			treeObj.setType(SolutionAdapterView.DSOBJ_s);
			treeObj.setDataSourceInfo(parent);
			root.addChild(treeObj);
			SolutionAdapterView.viewer_s.refresh();
		}
	}

	/**
	 * This method is used to add the project infomation in the adapter after
	 * opening the project.
	 * 
	 * @param projectServer
	 *            This Object will hold the TreeParent of the server which has
	 *            been added to the project view
	 * 
	 */
	public void addProjectInfoToSA(Object projectServer) {
		TreeParent root = SolutionAdapterView.solutionAdapterName_s;
		root.setProjectInfo(projectServer);
	}

	/**
	 * This method is used to initialize the groups in the tree while loading
	 * the application
	 * 
	 * @param root
	 *            This is of type TreeParent under which group will be created
	 */
	private void initializeGroupTree(TreeParent root) {
		File newFileObj = new File(SASConstants.SAS_ROOT);
		String[] allGroup = newFileObj.list();
		if (null != allGroup) {
			for (int i = 0; i < allGroup.length; i++) {
				if (isDirectory(SASConstants.SAS_ROOT + "/" + allGroup[i])) {
					TreeParent gourpNode = createTreeItem(root, allGroup[i],
							SolutionAdapterView.GROUP_s);
					initializeAdapterTree(gourpNode, SASConstants.SAS_ROOT
							+ "/" + allGroup[i]);
				}
			}
		}
	}

	/**
	 * This method is used to initialize the adapters in the tree while loading
	 * the application
	 * 
	 * @param groupNode
	 *            This is of type TreeParent under which adapters will be
	 *            created
	 * @param filePath
	 *            This string will hold the path of the adapters in the file
	 *            system
	 */
	private void initializeAdapterTree(TreeParent groupNode, String filePath) {
		File newFileObj = new File(filePath);
		String[] allAdapter = newFileObj.list();
		if (null != allAdapter) {
			for (int i = 0; i < allAdapter.length; i++) {
				if (isDirectory(filePath + "/" + allAdapter[i])) {
					TreeParent adapterNode = createTreeItem(groupNode,
							allAdapter[i],
							SolutionAdapterView.SOLUTIONSADAPTER_s);
					adapterNode.setStatus(false);
				}
			}
		}
	}

	/**
	 * This method is used to find wheather the path
	 * 
	 * @param path
	 *            This string will hold the path
	 * @return This will return true if the specified path is a directory or
	 *         else false
	 */
	private boolean isDirectory(String path) {
		File fileObj = new File(path);
		return fileObj.isDirectory();
	}

	/**
	 * This method is used to create the tree item under the root specified. It
	 * will set the type of the tree item
	 * 
	 * @param root
	 *            This is of type TreeParent under which child tree item will be
	 *            created
	 * @param itemName
	 *            This string will hold the name of the child tree item
	 * @param itemType
	 *            This string will hold the type of the child tree item
	 * @return This will return the child TreeParent object
	 */
	private TreeParent createTreeItem(TreeParent root, String itemName,
			String itemType) {
		TreeParent child = new TreeParent(itemName);
		child.setType(itemType);
		root.addChild(child);
		return child;
	}
}

/**
 * The content provider class is responsible for providing objects to the view.
 * It can wrap existing objects in adapters or simply return objects as-is.
 * These objects may be sensitive to the current input of the view, or ignore it
 * and always show the same content (like Task List, for example).
 */
class TreeObject implements IAdaptable {
	private String name;

	private String type;

	private String value;

	private String serverName;

	private String dataSource;

	private Object dataObjectInfo;

	private Object projectInfo;

	private Object dataSourceInfo;

	private boolean status;

	private TreeParent parent;

	/**
	 * This constructor is used to create the new tree item as the name given in
	 * the argument
	 * 
	 * @param name
	 *            This string contains the name which is going to be displayed
	 *            in the tree
	 */
	public TreeObject(String name) {
		this.name = name;
	}

	/**
	 * This method is used to get the name of the tree item
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method is used to set the display name of the tree item
	 * 
	 * @param name
	 *            String which contais the name of the tree item
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method is used to set the tree parent for newly added tree item
	 * 
	 * @param parent
	 *            This of type TreeParent
	 */
	public void setParent(TreeParent parent) {
		this.parent = parent;
	}

	/**
	 * This method is used to get the parent of the particulare tree item
	 * 
	 * @return this of type TreeParent
	 */
	public TreeParent getParent() {
		return parent;
	}

	/**
	 * This method will return the name of the tree item
	 */
	public String toString() {
		return getName();
	}

	/**
	 * This method will return null
	 */
	public Object getAdapter(Class key) {
		return null;
	}

	/**
	 * This method is used to get the value of the tree item. This is used to
	 * identify the tree item is belongs to group or class or field
	 * 
	 * @return this string will hold the value of group or class or field
	 */
	public String getValue() {
		return value;
	}

	/**
	 * This method is used to set the value of the tree item. This is used to
	 * identify the tree item is belongs to group or class or field
	 * 
	 * @param value
	 *            This string will hold the value of group or class or field
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * This method is used to get the type of the field in the field tree
	 * 
	 * @return This string contains any of the java datatypes
	 */
	public String getType() {
		return type;
	}

	/**
	 * This method is used to set the type of the field in the field tree
	 * 
	 * @param type
	 *            This string contains any of the java datatypes
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * This methd is used to get the Object(TreeParent) of the
	 * ItermediateDataObject Tree
	 * 
	 * @return This is of type Object contains the Treeparent of
	 *         ItermediateDataObject
	 */
	public Object getDataObjectInfo() {
		return dataObjectInfo;
	}

	/**
	 * This method is used to set the Object(TreeParent) of the
	 * ItermediateDataObject
	 * 
	 * @param dataObjectInfo
	 *            This is of type Object contains the TreeParent of
	 *            ItermediateDataObject
	 */
	public void setDataObjectInfo(Object dataObjectInfo) {
		this.dataObjectInfo = dataObjectInfo;
	}

	/**
	 * This method is used to get the datasource name of the Datasource
	 * 
	 * @return This will return datasource name
	 */
	public String getDataSource() {
		return dataSource;
	}

	/**
	 * This method is used to set the datasource name of the DataSource
	 * 
	 * @param dataSource
	 *            This contains datasource name
	 */
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * This method is used to get the server name
	 * 
	 * @return string which contains the server name
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * This method is used to set the server name
	 * 
	 * @param serverName
	 *            This string which contains the server name
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * This method is used to get the Object(TreeParent) of the Datasource
	 * 
	 * @return This is of type Object contains the TreeParent of Datasource
	 */
	public Object getDataSourceInfo() {
		return dataSourceInfo;
	}

	/**
	 * This method is used to set the Object(TreeParent) of the DataSource
	 * 
	 * @param dataSource
	 *            This is of type Object contaisn the TreeParent of Datasource
	 */
	public void setDataSourceInfo(Object dataSourceInfo) {
		this.dataSourceInfo = dataSourceInfo;
	}

	/**
	 * This method is used to get the status
	 * 
	 * @return This will return true or false
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * This method is used to set the status
	 * 
	 * @param status
	 *            This will hold true or false
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * This method is used to get the Object(TreeParent) of the Project
	 * 
	 * @return This is of type Object contains the TreeParent object of the
	 *         Project
	 */
	public Object getProjectInfo() {
		return this.projectInfo;
	}

	/**
	 * This method is used to set the Object(TreeParent) of the Project
	 * 
	 * @param projectInfo
	 *            This is of type Object contains the TreeParent object fo the
	 *            Project
	 */
	public void setProjectInfo(Object projectInfo) {
		this.projectInfo = projectInfo;
	}

}

class TreeParent extends TreeObject {
	private ArrayList<Object> children;

	/**
	 * This constructor is used to create a new tree item
	 * 
	 * @param name
	 *            This will contains the name of the tree item
	 */
	public TreeParent(String name) {
		super(name);
		children = new ArrayList<Object>();
	}

	/**
	 * This method is used to add a new child under a tree item
	 * 
	 * @param child
	 *            This contains the TreeObject
	 */
	public void addChild(TreeObject child) {
		children.add(child);
		child.setParent(this);
	}

	/**
	 * This method is used to remove the child from the tree item
	 * 
	 * @param child
	 */
	public void removeChild(TreeObject child) {
		children.remove(child);

		// child.setParent(null);
	}

	/**
	 * This method will get all the children under a particular tree item
	 * 
	 * @return This will contains array of objects of tree objects
	 */
	public TreeObject[] getChildren() {
		return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
	}

	/**
	 * This method is used to know wheather it contains children or not for a
	 * particular tree item
	 * 
	 * @return true if it contains one or more children or else false
	 */
	public boolean hasChildren() {
		return children.size() > 0;
	}
}
