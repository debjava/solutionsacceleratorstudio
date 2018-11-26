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
 * $Id: LoadProject.java,v 1.2 2006/07/28 13:31:59 redrabbit Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/project/LoadProject.java,v $
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

package com.rrs.corona.solutionsacceleratorstudio.project;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import com.rrs.corona.metricevent.DerivedMetric;
import com.rrs.corona.metricevent.SimpleEvent;
import com.rrs.corona.metricevent.CompositeEvent;
//import com.rrs.corona.businessdomainmodeler.BDMConstants;
//import com.rrs.corona.server.sas.BDMSASBridge;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
//import com.rrs.corona.solutionsacceleratorstudio.messagehandler.BDMSASMessageHandler;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SaveAdapter;

/**
 * This class is used to populate all the projects present in the file system
 * <P>
 * It also populate the compositeevent present under every projects and nested
 * composisteevents
 * </p>
 * <p>
 * It also populate the simpleevent present under every composisteevent, and
 * groupmetrics present under the simpleevent and the atomicmetrics present
 * under both the simpleevent and the groupmetrics.
 * </p>
 * 
 * 
 * @author Maharajan
 * @see com.rrs.corona.solutionsacceleratorstudio.project.ProjectData
 */
public class LoadProject extends ProjectData {
	/**
	 * This method is used to load all the projects from the file system when
	 * user say LoadProject from the right click menu. It will load the new
	 * projects if the project is already load then this will not load a
	 * duplicate project in the tree
	 * 
	 * @param filePath
	 *            This of type string which contains the path of the project in
	 *            the file system
	 * @param projectName
	 *            This of type string which contains the name of the project to
	 *            be loaded
	 */
	public void loadNewProject(final String filePath, final String projectName) {
		boolean flag = true;
		// get the invisible root to get the project root
		TreeObject[] root = ProjectData.invisibleRoot.getChildren();
		TreeParent root1 = (TreeParent) root[0];
		TreeObject rootChild[] = root1.getChildren();
		TreeParent rootParent = (TreeParent) rootChild[0];
		// if it has children then it will check for duplicate projects
		if (rootParent.hasChildren()) {
			TreeObject objTree[] = rootParent.getChildren();
			for (int i = 0; i < objTree.length; i++) {
				TreeParent child = (TreeParent) objTree[i];
				if (child.getName().equals(projectName)) {
					flag = false;
				}
			}
		}// if no duplicate is found then add the project in the tree view
		if (flag) {
			TreeParent parent = new TreeParent(projectName);
			parent.setType(ProjectData.PROJECT_S);
			File allComposite = new File(filePath + projectName
					+ SASConstants.PROJECT_COMPOSITE);
			String[] allCompositeFiles = allComposite.list();
			try {
				if (allCompositeFiles != null) {
					for (int i = 0; i < allCompositeFiles.length; i++) {
						//						final JAXBContext jtx = JAXBContext.newInstance(
						//								SASConstants.COMPOSITE_EVENT_CONTEXT, this.getClass()
						//										.getClassLoader());
						final JAXBContext jtx = JAXBContext.newInstance(
								SASConstants.COMPOSITEJAXB_CONTEXT, this
										.getClass().getClassLoader());

						// create an Unmarshaller
						final Unmarshaller unMarshall = jtx
								.createUnmarshaller();

						// unmarshal a instance document into a tree of Java
						// content
						// objects composed of classes from the generated
						// package.
						String compositeFile = filePath + projectName
								+ SASConstants.PROJECT_COMPOSITE + "/"
								+ allCompositeFiles[i];
						File xmlfile = new File(compositeFile);
						if (xmlfile.isFile()
								&& compositeFile
										.endsWith(SASConstants.PROJECT_COMPOSITE_EXT)) {
							CompositeEvent compEvt = (CompositeEvent) unMarshall
									.unmarshal(xmlfile);
							loadCompositeEvent(compEvt, parent);
						}
					}
				}
			} catch (Exception e) {
				final String errMsg = "Exception thrown in Method "
						+ "**loadNewProject()** in class [ LoadProject.java ]";
				SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg,
						e);
				e.printStackTrace();
			}
			parent.setParent(rootParent);
			rootParent.addChild(parent);
			ProjectData.viewer_s.expandToLevel(rootParent, 1);
			ProjectData.viewer_s.refresh(rootParent, true);
			SaveAdapter adaObj = new SaveAdapter();
			SaveAndOpenProject saveObj = new SaveAndOpenProject();
			String XMLFile = SASConstants.SAS_ROOT
					+ rootParent.getAdapterName() + SASConstants.BACK_SLASH_s
					+ SASConstants.SOLUTIONS_ADAPTER_XML;
			// save the project while loading in the xml file
			saveObj.saveWhileLoadingProject(parent, adaObj
					.getSolutionsAdapterType(), XMLFile);
		}
	}

	/**
	 * This method is used to iterate through groupmetric for atomicmetric and
	 * atomicmetric under derivedmetric and call the approriate method for
	 * populate groupmetric and atomicmetric in the tree.
	 * 
	 * @param groupMetric
	 *            This is the type of GroupMetric which contains the JAXb schema
	 *            object
	 * @param simpleParent
	 *            This contains the parent tree object of group metric
	 */
	public void loadGroupMetric(
			com.rrs.corona.metricevent.GroupMetric groupMetric,
			TreeParent simpleParent) {
		// add the groupmetrics in the tree
		TreeParent groupParent = new TreeParent(groupMetric.getName());
		// setting the property for the groupmetric in the tree object
		groupParent.setCompositeevent(simpleParent.getCompositeevent());
		groupParent.setSimpleevent(simpleParent.getName());
		groupParent.setGUID(groupMetric.getGUID());
		groupParent.setProjectName(simpleParent.getProjectName());
		List allAtomic = groupMetric.getAtomicMetric();
		// add all the atomicmetrics which is present under the groupmetrics
		for (Iterator atomicIter = allAtomic.iterator(); atomicIter.hasNext();) {
			com.rrs.corona.metricevent.AtomicMetric atomicMetric = (com.rrs.corona.metricevent.AtomicMetric) atomicIter
					.next();
			// add a new atomicmetric tree node under the groupmetric node
			TreeParent atomicTree = new TreeParent(atomicMetric.getName());
			// set the atomicmetric bean of all the atomicmetric property
			AtomicMetricBean atomicBean = new AtomicMetricBean();
			atomicBean.setName(atomicMetric.getName());
			atomicBean.setDescription(atomicMetric.getDescription());
			atomicBean.setGuid(atomicMetric.getGUID());
			atomicBean.setCategory(atomicMetric.getCategory());
			atomicBean.setCorelationID(atomicMetric.getCorrelationID());
			atomicBean.setTimeStamp(atomicMetric.getTimeStamp());
			atomicBean.setSessionID(atomicMetric.getSessionID());
			atomicBean.setMetricID(atomicMetric.getID());
			atomicBean.setTransactionID(atomicMetric.getTransactionID());
			atomicBean.setType(atomicMetric.getType());
			atomicBean.setData(atomicMetric.getData());
			atomicTree.setAtomicBean(atomicBean);
			atomicTree.setParent(groupParent);
			atomicTree.setType(ProjectData.ATOMICMETRIC_S);
			atomicTree.setGroupmetric(groupParent.getName());
			atomicTree.setProjectName(groupParent.getProjectName());
			atomicTree.setSimpleevent(groupParent.getSimpleevent());
			atomicTree.setCompositeevent(groupParent.getCompositeevent());
			groupParent.addChild(atomicTree);
			// add to tree
		}
		/*
		 * List allDerived = groupMetric.getDerivedMetrics( ); for( Iterator
		 * derivedIter = allDerived.iterator( ); derivedIter .hasNext( ); ) {
		 * com.rrs.corona.CompositeEvents.DerivedMetrics derivedMetric = (
		 * DerivedMetrics ) derivedIter .next( ); TreeObject derivedTree = new
		 * TreeObject(derivedMetric.getName());
		 * derivedTree.setParent(groupParent);
		 * groupParent.addChild(derivedTree); }
		 */

		List allGroup = groupMetric.getGroupMetric();
		// to load groupmetrics with in groupmetric, nested groupmetric
		if (allGroup.size() > 0) {
			for (Iterator groupIter = allGroup.iterator(); groupIter.hasNext();) {
				com.rrs.corona.metricevent.GroupMetric groupMet = (com.rrs.corona.metricevent.GroupMetric) groupIter
						.next();
				loadGroupMetric(groupMet, simpleParent);
			}
		}
		simpleParent.addChild(groupParent);
		groupParent.setParent(simpleParent);
		groupParent.setType(ProjectData.GROUPMETRIC_S);
	}

	/**
	 * This method is used to populate data for atomic metric present under
	 * simplevent iterate through the simplevent upto atomicmetric and call the
	 * appropriate method for populate atomicmetric and simpleevent in the tree
	 * 
	 * @param simpleEvent
	 *            This is the type of SimpleEvent which contains the JAXb schema
	 *            object
	 * @param compositeParent
	 *            This contains the parent tree object for simpleevent tree
	 */
	public void loadSimpleEvent(SimpleEvent simpleEvent,
			TreeParent compositeParent) {// add simpleevent in the tree under
		// the compositeevent tree node
		TreeParent simpleParent = new TreeParent(simpleEvent.getName());
		simpleParent.setCompositeevent(compositeParent.getGUID());
		simpleParent.setGUID(simpleEvent.getGUID());
		simpleParent.setProjectName(compositeParent.getProjectName());
		List allAtomic = simpleEvent.getAtomicMetric();
		// add all the atomicmetric which is present under the simpleevent
		for (Iterator atomicIter = allAtomic.iterator(); atomicIter.hasNext();) {
			com.rrs.corona.metricevent.AtomicMetric atomicMetric = (com.rrs.corona.metricevent.AtomicMetric) atomicIter
					.next();
			// add new atomicmetric tree under the simpleevent tree node
			TreeParent atomicTree = new TreeParent(atomicMetric.getName());
			// set the all the atomicmetric property in the atomicmetric bean
			AtomicMetricBean atomicBean = new AtomicMetricBean();
			atomicBean.setName(atomicMetric.getName());
			atomicBean.setDescription(atomicMetric.getDescription());
			atomicBean.setGuid(atomicMetric.getGUID());
			atomicBean.setCategory(atomicMetric.getCategory());
			atomicBean.setCorelationID(atomicMetric.getCorrelationID());
			atomicBean.setTimeStamp(atomicMetric.getTimeStamp());
			atomicBean.setSessionID(atomicMetric.getSessionID());
			atomicBean.setMetricID(atomicMetric.getID());
			atomicBean.setTransactionID(atomicMetric.getTransactionID());
			atomicBean.setType(atomicMetric.getType());
			atomicBean.setData(atomicMetric.getData());
			atomicTree.setAtomicBean(atomicBean);
			atomicTree.setParent(simpleParent);
			atomicTree.setType(ProjectData.ATOMICMETRIC_S);
			atomicTree.setCompositeevent(simpleParent.getCompositeevent());
			atomicTree.setSimpleevent(simpleParent.getName());
			atomicTree.setProjectName(simpleParent.getProjectName());
			atomicTree.setGroupmetric(null);
			simpleParent.addChild(atomicTree);
		}
		List allGroup = simpleEvent.getGroupMetric();
		// to load all the groupmetric present under this simpleevent
		if (allGroup.size() > 0) {
			for (Iterator groupIter = allGroup.iterator(); groupIter.hasNext();) {
				com.rrs.corona.metricevent.GroupMetric groupMet = (com.rrs.corona.metricevent.GroupMetric) groupIter
						.next();
				loadGroupMetric(groupMet, simpleParent);
			}
		}
		compositeParent.addChild(simpleParent);
		simpleParent.setParent(compositeParent);
		simpleParent.setType(ProjectData.SIMPLE_S);
	}

	/**
	 * This method is used to populate data for atomicmetric present under
	 * compositeevent iterate through compositeevent for atomicmetric and call
	 * the appropriate method for populate composite event in the tree
	 * 
	 * @param compositeEvent
	 *            This is the type of CompoisteEvent which contains the Jaxb
	 *            schema object
	 * @param rootParent
	 *            This contains the tree parent object for compositeevent
	 */
	public void loadCompositeEvent(CompositeEvent compositeEvent,
			TreeParent rootParent) {// add a new compositeevent tree node under
		// the project tree node
		TreeParent compositeParent = new TreeParent(compositeEvent.getName());
		compositeParent.setProjectName(rootParent.getName());
		compositeParent.setGUID(compositeEvent.getGUID());
		List allSimple = compositeEvent.getSimpleEvent();
		// add all the simpleevent as a tree under the compositeevent tree node
		for (Iterator atomicIter = allSimple.iterator(); atomicIter.hasNext();) {
			SimpleEvent SimpleEvent = (SimpleEvent) atomicIter.next();
			loadSimpleEvent(SimpleEvent, compositeParent);
		}
		List allComp = compositeEvent.getCompositeEvent();
		// add all the composisteevent in the tree under the composisteevent
		// tree node
		// nested compositeevent
		if (allComp.size() > 0) {
			for (Iterator groupIter = allComp.iterator(); groupIter.hasNext();) {
				CompositeEvent compEve = (CompositeEvent) groupIter.next();
				loadCompositeEvent(compEve, rootParent);
			}
		}
		compositeParent.setParent(rootParent);
		compositeParent.setType(ProjectData.COMPOSITE_S);
		rootParent.addChild(compositeParent);
	}

	/**
	 * This method is used to delete the project information from the tree It
	 * will delete all the child nodes under this particulate project and also
	 * delete the project from the file system
	 * 
	 * @param adapterPath
	 *            This string which contains the adapterpath under which project
	 *            is present
	 * @param projectName
	 *            This string contains the name of the project which is going to
	 *            be deleted
	 */
	public void deleteProjects(String adapterPath, String projectName) {
		String filePath = SASConstants.SAS_ROOT + adapterPath
				+ SASConstants.BACK_SLASH_s + projectName;
		File projectRoot = new File(filePath);
		deleteFolder(projectRoot);
	}

	/**
	 * This methdod is used to delete the folder. This will recursively delete
	 * the subfolders and its files in the file system
	 * 
	 * @param file
	 *            This is of type File object which contains the folder to be
	 *            deleted
	 * @return This returns true if all the sub-folders and files and the root
	 *         folder deleted successfully.
	 */
	public boolean deleteFolder(File file) {
		try {
			boolean success = true;
			if (file.isDirectory()) {
				String[] child = file.list();
				for (int i = 0; i < child.length; i++) {
					success = deleteFolder(new File(file, child[i]));
					if (!success) {
						return false;
					}
				}
			}
			return file.delete();
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**deleteFolder()** in class [ LoadProject.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			return false;
		}
	}
}
