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
 *$Id: SaveAndOpenProject.java,v 1.2 2006/07/28 13:31:59 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/project/SaveAndOpenProject.java,v $
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
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import com.rrs.corona.sasadapter.AtomicMetricList;
import com.rrs.corona.metricevent.AtomicMetric;
import com.rrs.corona.sasadapter.CompositeEventType;
import com.rrs.corona.sasadapter.GroupMetricsType;
import com.rrs.corona.sasadapter.ObjectFactory;
import com.rrs.corona.sasadapter.ProjectType;
import com.rrs.corona.sasadapter.SimpleEventType;
import com.rrs.corona.sasadapter.SolutionsAdapter;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

/**
 * This class is used to save the project information from the tree to the XML
 * file and also used to read the XML file and populate the tree
 * 
 * @author Maharajan
 * 
 */
public class SaveAndOpenProject {
	/**
	 * This method is used to save the projects and its compositeevent
	 * 
	 * @param projectNode
	 *            This is of type array of TreeObject which contains the
	 *            projects
	 * @param adapterRoot
	 *            This of type SolutionsAdapter schema object
	 */
	public void saveProjectToFile(TreeObject[] projectNode,
			SolutionsAdapter adapterRoot) {
		try {
			ObjectFactory objFact = new ObjectFactory();
			for (int pcount = 0; pcount < projectNode.length; pcount++) {
				TreeParent projectItem = (TreeParent) projectNode[pcount];
				ProjectType projectType = objFact.createProjectType();
				// projectType.getProjectName().add(projectItem.getName());
				projectType.setProjectName(projectItem.getName());
				TreeObject[] compositeObj = projectItem.getChildren();
				for (int ccount = 0; ccount < compositeObj.length; ccount++) {
					saveCompoisteItem(compositeObj[ccount], projectType);
				}
				adapterRoot.getProject().add(projectType);
			}
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**saveProjectTofile()** in class [ SaveAndOpenProject.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to load the project in the tree viewer while getting
	 * the project from the server
	 * 
	 * @param projectNode
	 *            This is of type TreeParent which will be loaded in the tree
	 * @param adapterRoot
	 *            This is of type SolutionsAdapter schema object which will save
	 *            the project information in the xml file
	 * @param XMLFile
	 *            This string will hold the path of the xml file
	 */
	public void saveWhileLoadingProject(TreeParent projectNode,
			SolutionsAdapter adapterRoot, String XMLFile) {
		try {
			ObjectFactory objFact = new ObjectFactory();
			ProjectType projectType = objFact.createProjectType();
			projectType.setProjectName(projectNode.getName());
			TreeObject[] compositeObj = projectNode.getChildren();
			for (int ccount = 0; ccount < compositeObj.length; ccount++) {
				saveCompoisteItem(compositeObj[ccount], projectType);
			}
			adapterRoot.getProject().add(projectType);
			final JAXBContext jtx = JAXBContext.newInstance(
					SASConstants.SOLUTIONS_ADAPTER_CONTEXT, this.getClass()
							.getClassLoader());
			File dataObjectfile = new File(XMLFile);
			FileOutputStream fus = new FileOutputStream(dataObjectfile);
			Marshaller marshall = jtx.createMarshaller();
			marshall
					.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshall.marshal(adapterRoot, fus);
			fus.close();
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**saveWhileLoadingProject()** in class [ SaveAndOpenProject.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to save the compositeevent into the xml file and its
	 * simple event. Compositeevent within compositeevent also gets saved
	 * 
	 * @param compositeObj
	 *            This of type TreeObject contains the compositeevent
	 * @param projectType
	 *            This of type Object inturn contains either CompositeEventType
	 *            of ProjectType
	 * @throws Exception
	 *             It throws JAXBException
	 */
	private void saveCompoisteItem(TreeObject compositeObj, Object projectType)
			throws Exception {
		TreeParent compositeItem = (TreeParent) compositeObj;
		ObjectFactory objFact = new ObjectFactory();
		CompositeEventType compType = objFact.createCompositeEventType();
		compType.setGUID(compositeItem.getGUID());
		compType.setName(compositeItem.getName());
		TreeObject[] simpleObj = compositeItem.getChildren();
		for (int scount = 0; scount < simpleObj.length; scount++) {
			if (simpleObj[scount].getType().equals(ProjectData.COMPOSITE_S)) {
				saveCompoisteItem(simpleObj[scount], compType);
			} else {
				saveSimpleEvent(simpleObj[scount], compType);
			}
		}
		if (projectType instanceof ProjectType) {
			((ProjectType) projectType).getCompositeEvent().add(compType);
		} else if (projectType instanceof CompositeEventType) {
			((CompositeEventType) projectType).getCompositeEvents().add(
					compType);
		}
	}

	/**
	 * This method is used to save the simpleevent and its child nodes in the
	 * xml file it will call the corresponding method to store group metrics
	 * 
	 * @param simpleObj
	 *            This is of type TreeObject which contains the simpleevent
	 * @param compType
	 *            This is of type CompositeEventType schema object to store the
	 *            simpleevent
	 * @throws Exception
	 *             It throws JAXBException
	 */
	private void saveSimpleEvent(TreeObject simpleObj,
			CompositeEventType compType) throws Exception {
		TreeParent simpleItem = (TreeParent) simpleObj;
		ObjectFactory objFact = new ObjectFactory();
		SimpleEventType simpleType = objFact.createSimpleEventType();
		simpleType.setGUID(simpleItem.getGUID());
		simpleType.setName(simpleItem.getName());
		TreeObject[] groupObj = simpleItem.getChildren();
		for (int gcount = 0; gcount < groupObj.length; gcount++) {
			if (groupObj[gcount].getType().equals(ProjectData.ATOMICMETRIC_S)) {
				saveAtomicMetric(groupObj[gcount], simpleType);
			} else if (groupObj[gcount].getType().equals(
					ProjectData.GROUPMETRIC_S)) {
				saveGroupMetric(groupObj[gcount], simpleType);
			}
		}
		compType.getSimpleEvent().add(simpleType);
	}

	/**
	 * This method is used to save the groupmetrics and its child nodes in the
	 * xml file. Nested groupmetric will also be saved in the xml file
	 * 
	 * @param groupObj
	 *            This is of Type TreeObject which contains the groupmetric
	 * @param objType
	 *            This is of type either GroupMetricsType or SimpleEventType
	 *            schema object
	 * @throws Exception
	 *             It throws JAXBException
	 */
	private void saveGroupMetric(TreeObject groupObj, Object objType)
			throws Exception {
		TreeParent groupItem = (TreeParent) groupObj;
		ObjectFactory objFact = new ObjectFactory();
		GroupMetricsType groupType = objFact.createGroupMetricsType();
		groupType.setGUID(groupObj.getGUID());
		groupType.setName(groupObj.getName());
		TreeObject[] atomicObj = groupItem.getChildren();
		for (int acount = 0; acount < atomicObj.length; acount++) {
			if (atomicObj[acount].getType().equals(ProjectData.GROUPMETRIC_S)) {
				saveGroupMetric(atomicObj[acount], groupType);
			} else if (atomicObj[acount].getType().equals(
					ProjectData.ATOMICMETRIC_S)) {
				saveAtomicMetric(atomicObj[acount], groupType);
			}
		}
		if (objType instanceof SimpleEventType) {
			((SimpleEventType) objType).getGroupMetrics().add(groupType);
		} else if (objType instanceof GroupMetricsType) {
			((GroupMetricsType) objType).getGroupMetrics().add(groupType);
		}
	}

	/**
	 * This method is used to save the atomicmetric in the xml file and also its
	 * mapping with data object under groupmetric or simpleevent
	 * 
	 * @param atomicObj
	 *            This is of type TreeObject which contains the atomicmetric
	 * @param objType
	 *            This is of Type either SimpleEventType of GroupMetricsType
	 *            schema object
	 * @throws Exception
	 *             It throws JAXBException
	 */
	private void saveAtomicMetric(TreeObject atomicObj, Object objType)
			throws Exception {
		ObjectFactory objFact = new ObjectFactory();
		AtomicMetricList atomic = objFact.createAtomicMetricList();
		AtomicMetricBean atomicBean = atomicObj.getAtomicBean();

		com.rrs.corona.metricevent.ObjectFactory metricObjFact = new com.rrs.corona.metricevent.ObjectFactory();

		AtomicMetric atomicType = metricObjFact.createAtomicMetric();

		atomicType.setName(atomicBean.getName());
		atomicType.setCategory(atomicBean.getCategory());
		atomicType.setCorrelationID(atomicBean.getCorelationID());
		atomicType.setData(atomicBean.getData());
		atomicType.setDescription(atomicBean.getDescription());
		atomicType.setGUID(atomicBean.getGuid());
		atomicType.setID(atomicBean.getMetricID());
		atomicType.setSessionID(atomicBean.getSessionID());
		atomicType.setTransactionID(atomicBean.getTransactionID());
		atomicType.setTimeStamp(atomicBean.getTimeStamp());
		atomicType.setType(atomicBean.getType());
		TreeObject mapObj[] = ((TreeParent) atomicObj).getChildren();
		for (int acount = 0; acount < mapObj.length; acount++) {
			atomic.getAMDOMap().add(mapObj[acount].getName());
		}
		atomic.getAtomicMetric().add(atomicType);
		if (objType instanceof SimpleEventType) {
			((SimpleEventType) objType).getAtomicMetric().add(atomic);
		} else if (objType instanceof GroupMetricsType) {
			((GroupMetricsType) objType).getAtomicMetric().add(atomic);
		}
	}

	/**
	 * This method is used to delete the particulare project from the xml file
	 * 
	 * @param filePath
	 *            This string will hold the filepath of the xml file
	 * @param projectName
	 *            This string will hold which project to delete
	 */
	public void resetProjectInfo(String filePath, String projectName) {
		try {
			final JAXBContext jtx = JAXBContext.newInstance(
					SASConstants.SOLUTIONS_ADAPTER_CONTEXT, this.getClass()
							.getClassLoader());
			Unmarshaller unMarshal = jtx.createUnmarshaller();
			SolutionsAdapter solutionAdapter = (SolutionsAdapter) unMarshal
					.unmarshal(new File(filePath));
			List projectList = solutionAdapter.getProject();
			for (Iterator iter = projectList.iterator(); iter.hasNext();) {
				ProjectType projType = (ProjectType) iter.next();
				if (projType.getProjectName().equals(projectName)) {
					projectList.remove(projType);
					break;
				}
			}
			File dataObjectfile = new File(filePath);
			FileOutputStream fus = new FileOutputStream(dataObjectfile);
			Marshaller marshall = jtx.createMarshaller();
			marshall
					.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshall.marshal(solutionAdapter, fus);
			fus.close();

		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**resetProjectInfo()** in class [ SaveAndOpenProject.java ]";
			;
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to save the project after getting the latest projects
	 * from the cms server
	 * 
	 * @param filePath
	 *            This string will hold the filePath of the xml file
	 * @param projObj
	 *            This is of type TreeObject which will be saved in the xml file
	 */
	public void setProjectInfo(String filePath, TreeObject[] projObj) {
		try {
			final JAXBContext jtx = JAXBContext.newInstance(
					SASConstants.SOLUTIONS_ADAPTER_CONTEXT, this.getClass()
							.getClassLoader());
			Unmarshaller unMarshal = jtx.createUnmarshaller();
			SolutionsAdapter solutionAdapter = (SolutionsAdapter) unMarshal
					.unmarshal(new File(filePath));
			saveProjectToFile(projObj, solutionAdapter);
			File dataObjectfile = new File(filePath);
			FileOutputStream fus = new FileOutputStream(dataObjectfile);
			Marshaller marshall = jtx.createMarshaller();
			marshall
					.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshall.marshal(solutionAdapter, fus);
			fus.close();
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**setProjectInfo()** in Class [ SaveAndOpenProject ] ";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to load the project information in the treeviewer by
	 * reading the xml file. This method create a new project under the project
	 * view
	 * 
	 * @param projectNode
	 *            This is of type TreeParent which will have the projects under
	 *            the this tree
	 * @param adapterRoot
	 *            This is of type SoluitonsAdapter schema object
	 */
	public void openProjectFromFile(TreeParent projectNode,
			SolutionsAdapter adapterRoot) {
		List projectList = adapterRoot.getProject();
		for (Iterator iterList = projectList.iterator(); iterList.hasNext();) {
			ProjectType projectType = (ProjectType) iterList.next();
			TreeParent projectObj = new TreeParent(projectType.getProjectName());
			projectObj.setType(ProjectData.PROJECTNAME_S);
			List compList = projectType.getCompositeEvent();
			for (Iterator compIter = compList.iterator(); compIter.hasNext();) {
				CompositeEventType compType = (CompositeEventType) compIter
						.next();
				openCompositeEvent(projectObj, compType);
			}
			projectNode.addChild(projectObj);
		}
	}

	/**
	 * This method is sued to load the composite information into the tree and
	 * it also load nested composite event in the tree
	 * 
	 * @param projectItem
	 *            This is of type TreeParent under which compositeevent tree
	 *            will be created
	 * @param compType
	 *            This is of type CompositeEventType schema object
	 */
	private void openCompositeEvent(TreeParent projectItem,
			CompositeEventType compType) {
		TreeParent compObj = new TreeParent(compType.getName());
		compObj.setType(ProjectData.COMPOSITE_S);
		compObj.setProjectName(projectItem.getName());
		compObj.setGUID(compType.getGUID());
		List simpleList = compType.getSimpleEvent();
		for (Iterator simpIter = simpleList.iterator(); simpIter.hasNext();) {
			SimpleEventType simpleType = (SimpleEventType) simpIter.next();
			openSimpleEvent(compObj, simpleType);
		}
		List compList = compType.getCompositeEvents();
		for (Iterator compIter = compList.iterator(); compIter.hasNext();) {
			CompositeEventType csubType = (CompositeEventType) compIter.next();
			openCompositeEvent(compObj, csubType);
		}
		projectItem.addChild(compObj);
	}

	/**
	 * This method is used to load the simpleevent information into the tree and
	 * it also load the groupmetrics and atomicmetrics which are all present in
	 * the simpleevent and loaded as a child
	 * 
	 * @param compItem
	 *            This is of type TreeParent under which simpleevent tree will
	 *            be created
	 * @param simpleType
	 *            This is of type SimpleEventType schema object
	 */
	private void openSimpleEvent(TreeParent compItem, SimpleEventType simpleType) {
		TreeParent simpleObj = new TreeParent(simpleType.getName());
		simpleObj.setType(ProjectData.SIMPLE_S);
		simpleObj.setGUID(simpleType.getGUID());
		simpleObj.setProjectName(compItem.getProjectName());
		simpleObj.setCompositeevent(compItem.getName());

		List atomicList = simpleType.getAtomicMetric();
		for (Iterator atomicIter = atomicList.iterator(); atomicIter.hasNext();) {
			AtomicMetricList atomicType = (AtomicMetricList) atomicIter.next();
			openAtomicMetric(simpleObj, atomicType);
		}
		List groupList = simpleType.getGroupMetrics();
		for (Iterator groupIter = groupList.iterator(); groupIter.hasNext();) {
			GroupMetricsType groupType = (GroupMetricsType) groupIter.next();
			openGroupMetric(simpleObj, groupType);
		}
		compItem.addChild(simpleObj);
	}

	/**
	 * This method is used to load the groupmetric information into the tree and
	 * it also load the groupmetrics and atomicmetrics which are all present in
	 * the groupmetric and loaded as a child
	 * 
	 * @param simpleItem
	 *            This is of type TreeParent under which groupmetrci tree will
	 *            be created
	 * @param groupType
	 *            This is of type GroupMetrcisType schema object
	 */
	private void openGroupMetric(TreeParent simpleItem,
			GroupMetricsType groupType) {
		TreeParent groupObj = new TreeParent(groupType.getName());
		groupObj.setType(ProjectData.GROUPMETRIC_S);
		groupObj.setGUID(groupType.getGUID());
		groupObj.setProjectName(simpleItem.getProjectName());
		groupObj.setCompositeevent(simpleItem.getCompositeevent());
		groupObj.setSimpleevent(simpleItem.getName());

		List atomicList = groupType.getAtomicMetric();
		for (Iterator atomicIter = atomicList.iterator(); atomicIter.hasNext();) {
			AtomicMetricList atomicType = (AtomicMetricList) atomicIter.next();
			openAtomicMetric(groupObj, atomicType);
		}
		List groupList = groupType.getGroupMetrics();
		for (Iterator groupIter = groupList.iterator(); groupIter.hasNext();) {
			GroupMetricsType gsubType = (GroupMetricsType) groupIter.next();
			openGroupMetric(groupObj, gsubType);
		}
		simpleItem.addChild(groupObj);
	}

	/**
	 * This method is used to load the atomicmetrics information into the tree
	 * and it also load the atomicmetrics mapping present in the
	 * 
	 * @param groupItem
	 *            This is of type TreeParent under which atomicmetrics tree will
	 *            be created
	 * @param atomicList
	 *            This is of type AtomicMetricList schema object
	 */
	private void openAtomicMetric(TreeParent groupItem,
			AtomicMetricList atomicList)// AtomicMetricType atomicType)
	{
		List listAtomic = atomicList.getAtomicMetric();
		for (Iterator atomicIter = listAtomic.iterator(); atomicIter.hasNext();) {
			AtomicMetric atomicType = (AtomicMetric) atomicIter.next();
			TreeParent atomicObj = new TreeParent(atomicType.getName());
			atomicObj.setType(ProjectData.ATOMICMETRIC_S);
			atomicObj.setProjectName(groupItem.getProjectName());
			atomicObj.setCompositeevent(groupItem.getCompositeevent());
			atomicObj.setSimpleevent(groupItem.getGroupmetric());
			atomicObj.setGroupmetric(groupItem.getGroupmetric());

			AtomicMetricBean atomicBean = new AtomicMetricBean();
			atomicBean.setName(atomicType.getName());
			atomicBean.setDescription(atomicType.getDescription());
			atomicBean.setGuid(atomicType.getGUID());
			atomicBean.setCategory(atomicType.getCategory());
			atomicBean.setCorelationID(atomicType.getCorrelationID());
			atomicBean.setTimeStamp(atomicType.getTimeStamp());
			atomicBean.setSessionID(atomicType.getSessionID());
			atomicBean.setMetricID(atomicType.getID());
			atomicBean.setTransactionID(atomicType.getTransactionID());
			atomicBean.setType(atomicType.getType());
			atomicBean.setData(atomicType.getData());
			atomicObj.setAtomicBean(atomicBean);

			List mapList = atomicList.getAMDOMap();
			for (Iterator mapIter = mapList.iterator(); mapIter.hasNext();) {
				String mapName = (String) mapIter.next();
				TreeParent mapObj = new TreeParent(mapName);
				mapObj.setType(DataObjectView.field_s);
				atomicObj.addChild(mapObj);
			}
			groupItem.addChild(atomicObj);
		}
	}
}
