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
 * $Id: DataObjectOpen.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/dataobject/DataObjectOpen.java,v $
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

import java.util.Iterator;
import java.util.List;

import com.rrs.corona.metricevent.AtomicMetric;
import com.rrs.corona.sasadapter.AMMapList;
import com.rrs.corona.sasadapter.ClassList;
import com.rrs.corona.sasadapter.DSMapList;
import com.rrs.corona.sasadapter.DataObjectType;
import com.rrs.corona.sasadapter.FieldNameList;
import com.rrs.corona.sasadapter.GroupList;
import com.rrs.corona.sasadapter.ObjectFactory;
import com.rrs.corona.sasadapter.SolutionsAdapter;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.project.AtomicMetricBean;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SolutionAdapterView;

/**
 * This class is used to save the IntermediateDataObject Tree information in the
 * xml file using the xml schema obj.
 * <p>
 * And also it is used to open the xml file and populate the informations
 * present in the xml in the IntermediateDataObject view
 * </p>
 * 
 * @author Maharajan
 * @author Debadatta Mishra
 * @see com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView
 */

public class DataObjectOpen extends DataObjectView {
	/**
	 * This method is used to save the data object tree into an xml file this
	 * will set the group list in the schema object
	 * 
	 * @param groupObj
	 *            This is of type array of TreeObject will contain all the group
	 *            tree items
	 * @param dataObjectType
	 *            This is of type DataObjectType used to store the group list
	 */
	public void saveDataObjectToFile(TreeObject[] groupObj,
			DataObjectType dataObjectType) {
		try {
			for (int gcount = 0; gcount < groupObj.length; gcount++) {
				TreeParent groupItem = (TreeParent) groupObj[gcount];
				ObjectFactory objFact = new ObjectFactory();
				GroupList groupList = objFact.createGroupList();
				groupList.setGroupName(groupItem.getName());
				TreeObject[] classObj = groupItem.getChildren();
				// This will the method to save the classes present under the
				// group
				saveGroupClasses(classObj, groupList);
				// add the group list in the schema object
				dataObjectType.getGroup().add(groupList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to set the classes information under the
	 * corresponding group list in the schema object which store the information
	 * into an xml file
	 * 
	 * @param classObj
	 *            This is of type array of TreeObject which contains all the
	 *            class tree objects
	 * @param groupList
	 *            This is of type GroupList schema object which will store all
	 *            the class information
	 * @throws Exception
	 *             It throws JAXBException
	 */
	protected void saveGroupClasses(TreeObject[] classObj, GroupList groupList)
			throws Exception {
		for (int ccount = 0; ccount < classObj.length; ccount++) {
			TreeParent classItem = (TreeParent) classObj[ccount];
			ObjectFactory objFact = new ObjectFactory();
			ClassList classList = objFact.createClassList();

			String className = SASConstants.DATAOBJECT_CONTEXT + "."
					+ classItem.getParent().getName() + "."
					+ classItem.getName();
			classList.setClassName(className);
			TreeObject[] fieldObj = classItem.getChildren();
			// This will call the method to save the field information present
			// under all the classes
			saveClassFields(fieldObj, classList);
			// This will add the classes in the group list of the xml schema
			// object
			groupList.getClasses().add(classList);
		}
	}

	/**
	 * This method is used to store all the fields present under a particular
	 * class into a schema which will save the information in the xml file
	 * 
	 * @param fieldObj
	 *            This is of type array of TreeObject which contains all the
	 *            fields
	 * @param classList
	 *            This is of type ClassList schema object which will store the
	 *            information about the fields
	 * @throws Exception
	 *             It will throw JAXBException
	 */
	protected void saveClassFields(TreeObject[] fieldObj, ClassList classList)
			throws Exception {
		for (int fcount = 0; fcount < fieldObj.length; fcount++) {
			TreeParent fieldItem = (TreeParent) fieldObj[fcount];
			ObjectFactory objFact = new ObjectFactory();
			FieldNameList fieldList = objFact.createFieldNameList();

			if (fieldItem.getValue().equals("field")) 
			{
				fieldList.setFieldName(fieldItem.getName());
				fieldList.setFieldType(fieldItem.getType());
				
				//for temp
				
				if (fieldItem.hasChildren()) 
				{
					TreeObject[] fieldMapObj = fieldItem.getChildren();
					// Method to save the mapping information
					// for all the fields
					saveFieldMapping(fieldMapObj, fieldList);
				}
				// add all the field information in the class list of xml schema
				// object
				classList.getFields().add(fieldList);

				//for temp
				
				
			}
			//It will check wheather a a field is an Object type
			if (fieldItem.getValue().equals("class")) 
			{
				//get data for nested object
				TreeParent nestedParent = (TreeParent) fieldItem;
				String nestedClassName = nestedParent.getName();
				String nestedParentName = nestedParent.getParent().getParent()
						.getName();
				String nestedObjectName = SASConstants.DATAOBJECT_CONTEXT + "."
						+ nestedParentName + "." + nestedClassName;

				//for storing in xml file
				ObjectFactory objFact1 = new ObjectFactory();
				ClassList nestedClass = objFact1.createClassList();
				nestedClass.setClassName(nestedObjectName);
				//FieldNameList nestedFieldList = objFact1.createFieldNameList();
				if (nestedParent.hasChildren()) 
				{
					TreeObject[] nestedFieldsObject = (TreeObject[]) nestedParent
							.getChildren();
					for (int j = 0; j < nestedFieldsObject.length; j++) 
					{
						FieldNameList nestedFieldList = objFact1.createFieldNameList();
						TreeParent nestedChild = (TreeParent) nestedFieldsObject[j];
						nestedFieldList.setFieldName(nestedChild.getName());
						nestedFieldList.setFieldType(nestedChild.getType());
						saveFieldMapping(nestedChild.getChildren(),
								nestedFieldList);
						nestedClass.getFields().add(nestedFieldList);
					}
					classList.getNestedClasses().add(nestedClass);
					
				}
			}

		}
	}

	/**
	 * This method is used to store all the fields mapping inforamtion into the
	 * xml file. This store atomicmapping and datasource mapping seperatly.
	 * 
	 * @param fieldMapObj
	 *            This is of type array of TreeObject which contains the fields
	 *            mapping tree objects
	 * @param fieldList
	 *            This is of type FieldNameList which will store the information
	 *            about the fieldmapping
	 * @throws Exception
	 *             It will throw JAXBException
	 */
	protected void saveFieldMapping(TreeObject[] fieldMapObj,
			FieldNameList fieldList) throws Exception {
		for (int mcount = 0; mcount < fieldMapObj.length; mcount++) {
			TreeParent mapItem = (TreeParent) fieldMapObj[mcount];
			ObjectFactory objFact = new ObjectFactory();
			if (mapItem.getMapping().equals(DataObjectView.mapAtomicType_s)) {
				// This will set the atomic metric properties in a bean class
				AtomicMetricBean atomicBean = mapItem.getAtomicBean();
				AMMapList amMapList = objFact.createAMMapList();
				amMapList.setParentTags(atomicBean.getProjectName());
				
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
				// add the atomicmetric in the mapping list
				amMapList.setAtomicMetric(atomicType);
				// add the atomicmetric mapping to the corresponding field
				fieldList.setDOAMMap(amMapList);
			} else if (mapItem.getMapping().equals(
					DataObjectView.mapFieldType_s)) {
				// add the database mapping with the field
				DSMapList dsMapList = objFact.createDSMapList();
				dsMapList.setDataSourceName(mapItem.getDataSource());
				dsMapList.setFieldName(mapItem.getName());
				dsMapList.setFieldType(mapItem.getType());
				dsMapList.setTableName(mapItem.getTableName());
				//logger.info("In DataObjectOpen.java file, primaryKey ::: "+mapItem.isPrimaryKey());
				dsMapList.setPrimaryKey(String.valueOf(mapItem.isPrimaryKey()));
				// add all the database mapping to the corresponding field
				fieldList.getDODSMap().add(dsMapList);
			}
		}
	}

	/**
	 * This method is used to populate the DataObject tree by reading the file
	 * from the file system.
	 * 
	 * @param parent
	 *            This refers the root TreeParent object on which the sub trees
	 *            will be formed
	 */
	public void openDataObjectFromFile(TreeParent dataObj,
			SolutionsAdapter adapterRoot) 
	{
		DataObjectType dataObjectType = adapterRoot.getDataObject();
		List listGroup = dataObjectType.getGroup();
		for (Iterator groupIter = listGroup.iterator(); groupIter.hasNext();) {
			GroupList groupList = (GroupList) groupIter.next();
			if (null != groupList) {
				TreeParent groupParent = new TreeParent(groupList
						.getGroupName());
				groupParent.setValue(DataObjectView.group_s);
				// This will call the method to get the class information
				// from the xml file
				openClasses(groupParent, groupList);
				dataObj.addChild(groupParent);
				SolutionAdapterView adapter = new SolutionAdapterView();
				// This will call a method to populate the xml information in
				// the
				// IntermediateDataobject view
				adapter.populateSolutionAdapter((Object) groupParent,
						groupParent.getName());
			}
		}

	}

	/**
	 * This method is used to open the class from xml and populate them in the
	 * tree under the dataObject
	 * 
	 * @param groupParent
	 *            This is of type TreeParent under which group tree will be
	 *            created
	 * @param groupList
	 *            This is of type GroupList schema object which contains the
	 *            group information
	 */
	protected void openClasses(TreeParent groupParent, GroupList groupList) {
		List listClass = groupList.getClasses();
		for (Iterator classIter = listClass.iterator(); classIter.hasNext();) {
			ClassList classList = (ClassList) classIter.next();
			String className = classList.getClassName();
			className = className.substring(className.lastIndexOf(".") + 1,
					className.length());
			TreeParent classParent = new TreeParent(className);
			classParent.setValue(DataObjectView.class_s);
			// This will call the method to get the field informtaion
			// under the class from the xml file
			openField(classParent, classList);
			groupParent.addChild(classParent);
		}
	}

	/**
	 * This method is used to open the field from xml and populate them in the
	 * tree under the class
	 * 
	 * @param classParent
	 *            This is of type TreeParent under which group tree will be
	 *            created
	 * @param classList
	 *            This is of type ClassList schema object which contains the
	 *            class information
	 */
	protected void openField(TreeParent classParent, ClassList classList) 
	{
		List listField = classList.getFields();
		//For nested Object
		TreeParent fieldParent = null;
		for (Iterator fieldIter = listField.iterator(); fieldIter.hasNext();) 
		{
			FieldNameList fieldList = (FieldNameList) fieldIter.next();
			//TreeParent fieldParent = new TreeParent(fieldList.getFieldName());//temp modification 22.06.06
			fieldParent = new TreeParent(fieldList.getFieldName());
			fieldParent.setValue(DataObjectView.field_s);
			fieldParent.setType(fieldList.getFieldType());
			fieldParent.setMapping("");
			// This will call the method to get the atomicmetric information
			// under the particular field from the xml file
			openAtomicMapping(fieldParent, fieldList);
			// This will call the method to get the database information
			// under the particular field fromt he xml file
			openFieldMapping(fieldParent, fieldList);
			classParent.addChild(fieldParent);
		}
		//for nested Object
		List nestedClassList = classList.getNestedClasses();//fetching the nested Objects
		if(nestedClassList.size()>0)
		{
			openNestedObjects(nestedClassList,classParent);
		}
		
	}
	
	/**
	 * This method is used to open the nested objects and the corresponding 
	 * fields and their associated mapping information
	 * @param nestedClassList of type list specifying the list of nested objects
	 * @param classParent of type TreeParent
	 */
	private void openNestedObjects(List nestedClassList,TreeParent classParent)
	{//This method is used to open the all mapping information
		//for the nested objects and the nested objects fields
		for(int i=0 ; i<nestedClassList.size() ; i++)
		{
			final ClassList nestedObjectList = (ClassList)nestedClassList.get(i);
			final String nestedObjName = nestedObjectList.getClassName();
			final String nestedClassName = nestedObjName.substring(nestedObjName.lastIndexOf(".")+1,nestedObjName.length());
			TreeParent nestedClsParent = new TreeParent(nestedClassName);//for nested Object
			nestedClsParent.setValue(DataObjectView.class_s);
			classParent.addChild(nestedClsParent);
			
			final List nestedFieldList = nestedObjectList.getFields();
			for(int j=0 ;j<nestedFieldList.size() ; j++)
			{
				final FieldNameList nestedFieldNames = (FieldNameList)nestedFieldList.get(j);
				final String nestedFieldName = nestedFieldNames.getFieldName();
				TreeParent nestedFieldParent = new TreeParent(nestedFieldName);
				nestedFieldParent.setValue(DataObjectView.field_s);//setting the value
				nestedFieldParent.setType(nestedFieldNames.getFieldType());//setting the type
				nestedFieldParent.setMapping("");
				//to show the atomic metric mapping
				openAtomicMapping(nestedFieldParent, nestedFieldNames);
				//to show datasource mapping information
				openFieldMapping(nestedFieldParent, nestedFieldNames);
				nestedClsParent.addChild(nestedFieldParent);
			}
		}
	}

	/**
	 * This method is used to open the field mapping from xml and populate them
	 * in the tree under the field
	 * 
	 * @param fieldParent
	 *            This is of type TreeParent under which field tree will be
	 *            created
	 * @param fieldList
	 *            This is of type FieldNameList schema object which contains the
	 *            mapping information
	 */
	protected void openFieldMapping(TreeParent fieldParent,
			FieldNameList fieldList) 
	{
		List mapList = fieldList.getDODSMap();
		for (Iterator mapIter = mapList.iterator(); mapIter.hasNext();) {
			DSMapList dsMapList = (DSMapList) mapIter.next();
			TreeParent fieldMap = new TreeParent(dsMapList.getFieldName());
			fieldMap.setDataSource(dsMapList.getDataSourceName());
			//for temporary testing
			
			//for temporary testing
			fieldMap.setTableName(dsMapList.getTableName());
			fieldMap.setType(dsMapList.getFieldType());
			fieldMap.setValue(DataObjectView.field_s);
			fieldMap.setMapping(DataObjectView.mapFieldType_s);
			final String pkVal = dsMapList.getPrimaryKey();
			if(pkVal.equals("true"))
			{
				fieldMap.setPrimaryKey(true);
			}
			else
			{
				fieldMap.setPrimaryKey(false);
			}
			fieldParent.addChild(fieldMap);
		}
	}

	/**
	 * This method is used to open the atomic mapping from the xml file and
	 * populate them in the tree under the field
	 * 
	 * @param fieldParent
	 *            This is of type TreParent under which field tree will be
	 *            created
	 * @param fieldList
	 *            This is of type FieldNameList schema object which contains the
	 *            mapping information
	 */
	protected void openAtomicMapping(TreeParent fieldParent,
			FieldNameList fieldList) {
		AMMapList amMapList = fieldList.getDOAMMap();
		if (null != amMapList) {
			// This is to get the atomicmetric properties from
			// the xml file to be loaded in the atomicmetric bean class
			AtomicMetric atomicType = amMapList.getAtomicMetric();
			TreeParent atomicParent = new TreeParent(atomicType.getName());
			atomicParent.setValue(DataObjectView.field_s);
			atomicParent.setMapping(DataObjectView.mapAtomicType_s);
			AtomicMetricBean atomicBean = new AtomicMetricBean();
			atomicBean.setProjectName(amMapList.getParentTags());
			atomicBean.setName(atomicType.getName());
			atomicBean.setGuid(atomicType.getGUID());
			atomicBean.setCategory(atomicType.getCategory());
			atomicBean.setDescription(atomicType.getDescription());
			atomicBean.setMetricID(atomicType.getID());
			atomicBean.setData(atomicType.getData());
			atomicBean.setType(atomicType.getType());
			atomicBean.setCorelationID(atomicType.getCorrelationID());
			atomicBean.setTransactionID(atomicType.getTransactionID());
			atomicBean.setTimeStamp(atomicType.getTimeStamp());
			atomicBean.setSessionID(atomicType.getSessionID());
			atomicParent.setAtomicBean(atomicBean);
			fieldParent.addChild(atomicParent);
		}
	}
}
