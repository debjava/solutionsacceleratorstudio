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
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/SAReader.java,v $
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
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.rrs.corona.beans.TableClassInfoBean;
import com.rrs.corona.sasadapter.ClassList;
import com.rrs.corona.sasadapter.DSMapList;
import com.rrs.corona.sasadapter.DataObjectType;
import com.rrs.corona.sasadapter.DataSourceType;
import com.rrs.corona.sasadapter.FieldNameList;
import com.rrs.corona.sasadapter.GroupList;
import com.rrs.corona.sasadapter.SolutionsAdapter;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.datasource.DbmsInfo;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

/**
 * 
 * @author Debadatta Mishra
 * 
 */
public class SAReader {
	/**
	 * File path of the repository.xml file
	 */
	private transient String filePath;

	/**
	 * File name of the SolutionAdapter.xml
	 */
	private transient String saFileName = SASConstants.SOLUTIONS_ADAPTER_XML;

	/**
	 * Jaxb context for the sasAdapter schema
	 */
	private transient String jaxBContext = SASConstants.SOLUTIONS_ADAPTER_CONTEXT;

	/**
	 * Logger to log
	 */
	private Logger logger = Logger.getLogger("SAReader.class");

	/**
	 * Object of type SolutionsAdapter
	 */
	SolutionsAdapter solnAdapter = null;

	/**
	 * A Hashmap to store the table and class field information
	 */
	private transient HashMap tableClassMap = new HashMap();

	/**
	 * An ArrayList containing the list of class names
	 */
	private transient ArrayList classNameList = new ArrayList();

	/**
	 * HashMap containing the nested class information
	 */
	private transient HashMap nestedTabClsMap = new HashMap();

	/**
	 * Default constructor for SAReader
	 * 
	 */
	public SAReader() {
		this.filePath = SASConstants.SAS_ROOT
				+ new SolutionAdapterView().getFolderStructure()
				+ SASConstants.BACK_SLASH_s;
		solnAdapter = getSA();
	}

	/**
	 * Method used to obtain an object of SolutionsAdapter
	 * 
	 * @return solutionadapter object
	 */
	public SolutionsAdapter getSA() {// This method provides an object of
										// type SolutionsAdapter
		try {
			File saFile = new File(filePath + saFileName);
			if (!saFile.exists()) {
				logger.info("File does not exist");
				// do nothing
			} else {
				JAXBContext jaxbCtx = JAXBContext.newInstance(jaxBContext);
				Unmarshaller unmarshal = jaxbCtx.createUnmarshaller();
				SolutionsAdapter solutionAdapter = (SolutionsAdapter) unmarshal
						.unmarshal(new FileInputStream(filePath + saFileName));
				return solutionAdapter;
			}
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method **getSA()** in [ SAReader.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 
	 * @return an Object storing the dataSource information
	 */
	public Object getDataSourceDetails() {// his method is used to store all
											// the dataSource information in an
											// Object
		DbmsInfo dbInfo = new DbmsInfo();
		try {
			File saFile = new File(filePath + saFileName);
			if (!saFile.exists()) {
				logger.info("File does not exist");
				// do nothing
			} else {
				JAXBContext jaxbCtx = JAXBContext.newInstance(jaxBContext);
				Unmarshaller unmarshal = jaxbCtx.createUnmarshaller();
				SolutionsAdapter solutionAdapter = (SolutionsAdapter) unmarshal
						.unmarshal(new FileInputStream(filePath + saFileName));
				DataSourceType dsType = solutionAdapter.getDataSource();
				dbInfo.setDataSourceName(dsType.getDataSource()
						.getDataSourceName());
				dbInfo.setDbUrl(dsType.getDataSource().getURL());
				dbInfo.setDbUser(dsType.getDataSource().getUserName());
				dbInfo.setDbPassword(dsType.getDataSource().getPassword());
			}
		} catch (Exception e) {
			final String errMsg = "Exception thrown in "
					+ "Method **getDataSourceDetails()** in [ SAReader.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
		return dbInfo;
	}

	/**
	 * This method is used to obtain the mapped class name by providing the
	 * class name and field name as parameters
	 * 
	 * @param param_Class_Name
	 *            of type String specifying the defined class name
	 * @param field_name
	 *            of type String specifying the class field name
	 * @return the name of the table that is mapped to a class
	 */
	// for temporary use
	/*
	 * public String getMappedTableName(final String param_Class_Name,final
	 * String field_name) {//This method is used to return the mapped table name
	 * String table_Name = null; String fieldName = null; //solnAdapter =
	 * getSA();//new modification on dt 15.06.06 DataObjectType dataObject =
	 * solnAdapter.getDataObject(); List listGroup = dataObject.getGroup();
	 * group_Loop: for(Iterator groupIter= listGroup.iterator();
	 * groupIter.hasNext();) { GroupList groupList = (GroupList)
	 * groupIter.next(); List classList = groupList.getClasses(); class_Loop:
	 * for(int j=0;j<classList.size();j++) { ClassList classesList =
	 * (ClassList)classList.get(j); String className =
	 * classesList.getClassName();//Provides the class name
	 * if(className.equals(param_Class_Name)) { List fieldList =
	 * classesList.getFields();//Provides the list of fields present in a class
	 * field_Loop: for(int k=0;k<fieldList.size();k++) { FieldNameList
	 * listField = (FieldNameList)fieldList.get(k); List dodsMapList =
	 * listField.getDODSMap(); fieldName = listField.getFieldName();
	 * if(fieldName.equals(field_name)) { doDsMap_Loop: for(int l=0;l<dodsMapList.size();l++) {
	 * DSMapList dsType = (DSMapList)dodsMapList.get(l); table_Name =
	 * dsType.getTableName(); break group_Loop; } } // doDsMap_Loop: for(int
	 * l=0;l<dodsMapList.size();l++) // { // DSMapList dsType =
	 * (DSMapList)dodsMapList.get(l); // table_Name = dsType.getTableName(); //
	 * break group_Loop; // } } } } } return table_Name; }
	 */
	/**
	 * Method used to return a HashMap containg the table name as key and
	 * required field information as value
	 * 
	 * @return a HashMap containg the table name and related information
	 */
	public HashMap getTableClassInfo() {
		String table_Name = null;
		// solnAdapter = getSA();//new modification on dt 15.06.06
		final DataObjectType dataObject = solnAdapter.getDataObject();
		final List listGroup = dataObject.getGroup();
		group_Loop: for (final Iterator groupIter = listGroup.iterator(); groupIter
				.hasNext();) {
			final GroupList groupList = (GroupList) groupIter.next();
			final List classList = groupList.getClasses();
			class_Loop: for (int j = 0; j < classList.size(); j++) {
				final ClassList classesList = (ClassList) classList.get(j);
				final String className = classesList.getClassName();// Provides
																	// the class
																	// name
				classNameList.add(className);// Storing all the class names
												// in the arrayList
				final List fieldList = classesList.getFields();// Provides the
																// list of
																// fields
																// present in a
																// class

				field_Loop: for (int k = 0; k < fieldList.size(); k++) {
					final FieldNameList listField = (FieldNameList) fieldList
							.get(k);
					final String clsFieldName = listField.getFieldName();// provides
																			// class
																			// field
																			// name
					final String clsFieldType = listField.getFieldType();// provides
																			// class
																			// field
																			// type
					final List dodsMapList = listField.getDODSMap();
					doDsMap_Loop: for (int l = 0; l < dodsMapList.size(); l++) {
						final DSMapList dsType = (DSMapList) dodsMapList.get(l);
						table_Name = dsType.getTableName();// provides table
															// name
						// temporary enhancement 26.06.06
						table_Name = table_Name + "SAS";
						// temporary enhancement 26.06.06
						final String tableFieldName = dsType.getFieldName();// table
																			// field
																			// type
						final String tableFieldPrimaryKey = dsType
								.getPrimaryKey();// true if there is a
													// primary key for field
						TableClassInfoBean tbclsBean = new TableClassInfoBean();
						// set all the required values to the beans
						tbclsBean.setClsName(className);
						tbclsBean.setClsFieldName(clsFieldName);
						tbclsBean.setClsFieldType(clsFieldType);
						tbclsBean.setTableName(table_Name);
						tbclsBean.setTableFieldName(tableFieldName);
						tbclsBean.setPrimaryKey(tableFieldPrimaryKey);

						if (!tableClassMap.containsKey(table_Name))
							tableClassMap.put(table_Name, new ArrayList());
						((ArrayList) tableClassMap.get(table_Name))
								.add(tbclsBean);
					}
				}
			}
		}
		return tableClassMap;
	}

	/**
	 * ********* New enhancement and Modification for Nested object Concept in
	 * SAS Application dt 15.06.06 *******************
	 */

	/**
	 * this method is used to obtain a HashMap which contains nested Object
	 * information
	 * 
	 * @return a HashMap containg the nested Object information
	 */
	private HashMap getNestedClassInfo() {// This method is used to get a
											// HashMap which
		// contains nested object object information,
		// It also calls some other methods
		HashMap nestedMap = new HashMap();

		for (int i = 0; i < classNameList.size(); i++) {
			final String class_name = (String) classNameList.get(i);
			// logger.info("Class Name here ::: "+class_name);

			final DataObjectType dataObject = solnAdapter.getDataObject();
			final List listGroup = dataObject.getGroup();
			group_Loop: for (final Iterator groupIter = listGroup.iterator(); groupIter
					.hasNext();) {
				final GroupList groupList = (GroupList) groupIter.next();
				final List classList = groupList.getClasses();
				class_Loop: for (int j = 0; j < classList.size(); j++) {
					final ClassList classesList = (ClassList) classList.get(j);
					final String className = classesList.getClassName();// Provides
																		// the
																		// class
																		// name
					if (className.equals(class_name)) {
						List nestedClassList = classesList.getNestedClasses();
						// logger.info("Nested class size :::
						// "+nestedClassList.size());
						if (nestedClassList.size() > 0) {
							nestedMap = getNestedTabClassInfo(nestedClassList);
						} else {
							// do nothing
						}
					}
				}
			}
		}

		return nestedMap;
	}

	/**
	 * This method is used obtain a HashMap containing the nested table class
	 * information
	 * 
	 * @param nestedClassList
	 *            of type List
	 * @return a HashMap containing the nested table class information
	 */
	private HashMap getNestedTabClassInfo(final List nestedClassList) {// This
																		// method
																		// is
																		// used
																		// to
																		// return
																		// a
																		// HashMap
																		// containg
																		// the
																		// nested
		// table class information from the solutionAdapter.xml file
		for (int l = 0; l < nestedClassList.size(); l++) {
			final ClassList nestedObjectList = (ClassList) nestedClassList
					.get(l);
			final String nestedObjName = nestedObjectList.getClassName();
			final List fieldList = nestedObjectList.getFields();
			for (int i = 0; i < fieldList.size(); i++) {
				final FieldNameList listField = (FieldNameList) fieldList
						.get(i);
				final List dodsMapList = listField.getDODSMap();
				final String nestedFieldName = listField.getFieldName();
				final String nestedFieldType = listField.getFieldType();
				for (int k = 0; k < dodsMapList.size(); k++) {
					final DSMapList dsType = (DSMapList) dodsMapList.get(k);
					String table_Name = dsType.getTableName();// provides
																// table name
					// temporary enhancement 26.06.06
					table_Name = table_Name + "SAS";
					// temporary enhancement 26.06.06
					final String tableFieldName = dsType.getFieldName();// table
																		// field
																		// type
					final String tableFieldPrimaryKey = dsType.getPrimaryKey();// true
																				// if
																				// there
																				// is a
																				// primary
																				// key
																				// for
																				// field
					TableClassInfoBean tbclsBean = new TableClassInfoBean();
					// set all the required values to the beans
					tbclsBean.setClsName(nestedObjName);
					tbclsBean.setClsFieldName(nestedFieldName);
					tbclsBean.setClsFieldType(nestedFieldType);
					tbclsBean.setTableName(table_Name);
					tbclsBean.setTableFieldName(tableFieldName);
					tbclsBean.setPrimaryKey(tableFieldPrimaryKey);

					if (!nestedTabClsMap.containsKey(table_Name))
						nestedTabClsMap.put(table_Name, new ArrayList());
					((ArrayList) nestedTabClsMap.get(table_Name))
							.add(tbclsBean);
				}
			}
		}
		return nestedTabClsMap;
	}

	/**
	 * This method is used to obtain a HashMap containing the all information
	 * relationg to the objects and nested objects
	 * 
	 * @return a Hashmap
	 */
	public HashMap getTableClsAllInfo() {// This method is used to return a
											// HashMap containing the
		// information of objects and nested objects
		// It provides two hashMap,one containg the only object informationa
		// and the other one containing the nested object information.Finally
		// the hashMaps are merged to one.
		HashMap tableClassInfoMap = new HashMap();
		HashMap firstMap = getTableClassInfo();// first hashMap with no nested
												// object
		HashMap secondNestedMap = getNestedClassInfo();// HashMap containing
														// the nested object
														// information
		if (secondNestedMap.size() > 0) {
			tableClassInfoMap = mergeTwoMapsToOne(firstMap, secondNestedMap);
		} else {
			tableClassInfoMap = firstMap;
		}
		return tableClassInfoMap;
	}

	/**
	 * This method is used to merge two Hashmaps into one
	 * 
	 * @param firstMap
	 *            of type HashMap
	 * @param secondNestedMap
	 *            of type HashMap
	 * @return a HashMap
	 */
	private HashMap mergeTwoMapsToOne(final HashMap firstMap,
			final HashMap secondNestedMap) {// This method is used to merge two
											// HashMaps into one.
		HashMap mergeMap = new HashMap();
		final Set set = secondNestedMap.entrySet();
		final Iterator itr = set.iterator();
		while (itr.hasNext()) {
			final Map.Entry me = (Map.Entry) itr.next();
			final String table_Name = (String) me.getKey();
			ArrayList tempList = (ArrayList) me.getValue();
			// Some new modifications to be done here.............
			if (firstMap.containsKey(table_Name)) {
				for (int i = 0; i < tempList.size(); i++) {
					TableClassInfoBean tempBean = (TableClassInfoBean) tempList
							.get(i);
					((ArrayList) firstMap.get(table_Name)).add(tempBean);
				}
			}
			if (!firstMap.containsKey(table_Name))
				firstMap.put(table_Name, tempList);

		}
		mergeMap = firstMap;

		return mergeMap;
	}

	/**
	 * This method is used to obtain the mapped class name by providing the
	 * class name and field name as parameters
	 * 
	 * @param param_Class_Name
	 *            of type String specifying the defined class name
	 * @param field_name
	 *            of type String specifying the class field name
	 * @return the name of the table that is mapped to a class
	 */
	public String getMappedTableName(final String param_Class_Name,
			final String field_name) {// This method is used to obtain the
										// mapped table name from the clas
		// as well as from the nested objects
		String table_Name = null;
		String fieldName = null;
		DataObjectType dataObject = solnAdapter.getDataObject();
		List listGroup = dataObject.getGroup();

		group_Loop: for (Iterator groupIter = listGroup.iterator(); groupIter
				.hasNext();) {
			final GroupList groupList = (GroupList) groupIter.next();
			final List classList = groupList.getClasses();
			class_Loop: for (int j = 0; j < classList.size(); j++) {
				final ClassList classesList = (ClassList) classList.get(j);
				final String tempTableName = getTableNameFromClass(classesList,
						param_Class_Name, field_name);
				if (tempTableName == null) {
					final List nestedClassList = classesList.getNestedClasses();
					if (nestedClassList.size() > 0) {
						final String mappedNestedTableName = getTableNameFromNestedClass(
								nestedClassList, param_Class_Name, field_name);
						if (mappedNestedTableName == null) {
							continue class_Loop;
						} else {
							table_Name = mappedNestedTableName;
						}
					}
				} else {
					table_Name = tempTableName;
					break group_Loop;
				}

			}// end of class_Loop
		}// end of group_Loop
		return table_Name;
	}

	/**
	 * This method is used to obtain the database table name from a particualar
	 * class by specifing the mapped class name and class field name
	 * 
	 * @param classesList
	 *            of type ClassList object
	 * @param param_Class_Name
	 *            of type String specifying the Class name
	 * @param field_name
	 *            of type String specifying the class field name
	 * @return a database table name
	 */
	private String getTableNameFromClass(final ClassList classesList,
			final String param_Class_Name, final String field_name) {// This
																		// method
																		// is
																		// used
																		// to
																		// obtain
																		// the
																		// table
																		// name
																		// from
																		// the
																		// list
																		// of
																		// classes
		String table_Name = null;
		final String className = classesList.getClassName();// Provides the
															// class name
		if (className.equals(param_Class_Name)) {
			final List fieldList = classesList.getFields();// Provides the list
															// of fields present
															// in a class
			field_Loop: for (int k = 0; k < fieldList.size(); k++) {
				final FieldNameList listField = (FieldNameList) fieldList
						.get(k);
				final List dodsMapList = listField.getDODSMap();
				final String fieldName = listField.getFieldName();
				if (fieldName.equals(field_name)) {
					doDsMap_Loop: for (int l = 0; l < dodsMapList.size(); l++) {
						final DSMapList dsType = (DSMapList) dodsMapList.get(l);
						table_Name = dsType.getTableName();
						break field_Loop;
					}// end of doDsMap_Loop
				}// end of if for field
			}// end of field_Loop
		}// end of if
		return table_Name;
	}

	/**
	 * This method is used to obtain the database table name from the nested
	 * object by specifying the class name and class field name
	 * 
	 * @param nestedClassList
	 *            of type List
	 * @param param_Class_Name
	 *            of type String specifying the class name
	 * @param field_name
	 *            of type String specifying the class field name
	 * @return
	 */
	private String getTableNameFromNestedClass(final List nestedClassList,
			final String param_Class_Name, final String field_name) {
		String mappedNestedTableName = null;
		class_Loop: for (int l = 0; l < nestedClassList.size(); l++) {
			final ClassList nestedObjectList = (ClassList) nestedClassList
					.get(l);
			final String nestedClassName = nestedObjectList.getClassName();

			if (nestedClassName.equals(param_Class_Name)) {
				final List fieldList = nestedObjectList.getFields();

				field_Loop: for (int i = 0; i < fieldList.size(); i++) {
					final FieldNameList listField = (FieldNameList) fieldList
							.get(i);
					final List dodsMapList = listField.getDODSMap();
					final String nestedFieldName = listField.getFieldName();
					if (nestedFieldName.equals(field_name)) {
						doDSMap_Loop: for (int k = 0; k < dodsMapList.size(); k++) {
							final DSMapList dsType = (DSMapList) dodsMapList
									.get(k);
							final String table_Name = dsType.getTableName();// provides
																			// table
																			// name
							mappedNestedTableName = table_Name;
							break class_Loop;
						}
					}

				}
			}
		}
		return mappedNestedTableName;
	}

	/**
	 * ********* New enhancement and Modification for Nested object Concept in
	 * SAS Application dt 15.06.06 ends*******************
	 */

}
