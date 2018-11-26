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
 * $Id: DropIntoTree.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/dataobject/DropIntoTree.java,v $
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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import com.rrs.corona.solutionsacceleratorstudio.dragdrop.DatabaseBean;
import com.rrs.corona.solutionsacceleratorstudio.project.AtomicMetricBean;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SolutionAdapterView;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;

/**
 * This class is used to populate the tree while drag and drop
 * 
 * @author Maharajan
 * @see com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView
 */

public class DropIntoTree extends DataObjectView {

	/**
	 * This method is used to drop all the tables from the datasource tree view
	 * to the dataobject tree view with the group name as the datasource name
	 * 
	 * @param rootParent
	 *            This of type TreeParent which contains the root node of the
	 *            tree
	 * @param dataBean
	 *            This of type DatabaseBean which contains all the information
	 *            about the tree
	 * @return true if added to the tree
	 */
	public boolean dropAllTableInTree(TreeParent rootParent,
			DatabaseBean dataBean) {
		boolean result = false;
		ArrayList allTab = dataBean.getAllTables();
		TreeParent superParent = new TreeParent(dataBean.getGroupName());
		if (checkForDuplicateField(rootParent, dataBean.getGroupName(), "",
				DataObjectView.group_s)) {
			for (int count = 0; count < allTab.size(); count++) {
				DatabaseBean subBean = (DatabaseBean) allTab.get(count);

				TreeParent parent = new TreeParent(subBean.getTableName());
				parent.setValue(DataObjectView.class_s);
				ArrayList fieldList = subBean.getFieldMap();
				HashMap fieldType = subBean.getFieldType();
				// this is used to get all the field information and its types
				// from the arraylist and hashmap
				for (int i = 0; i < fieldList.size(); i++) {
					String fieldname = (String) fieldList.get(i);
					fieldname = fieldname.substring(0, fieldname.indexOf(" "));

					TreeParent treeObj = new TreeParent(fieldname);
					treeObj.setValue(DataObjectView.field_s);
					String fieldtype = (String) fieldType.get(fieldList.get(i));
					// it will put the mapped java type with the sql type
					if (fieldtype
							.equalsIgnoreCase(SASConstants.SAS_SQL_NUMBER_s)) {
						fieldtype = SASConstants.DATAOBJECT_JAVATYPENUMBER;
					}// it will put the mapped java date type with sql data
					// type
					else if (fieldtype
							.equalsIgnoreCase(SASConstants.SAS_SQL_DATE_s)) {
						fieldtype = SASConstants.DATAOBJECT_JAVATYPEDATE;
					} else {
						if (checkForDateDataType(fieldtype)
								|| checkForNumberDataType(fieldtype)) {
							treeObj.setType(fieldtype);
						} else {
							fieldtype = SASConstants.DATAOBJECT_JAVATYPEVARCHAR;
						}
					}
					treeObj.setMapping("");
					treeObj.setDataSource("");
					treeObj.setTableName("");
					treeObj.setParent(parent);
					treeObj.setType(fieldtype);
					parent.addChild(treeObj);
				}
				parent.setParent(superParent);
				superParent.addChild(parent);
			}
			superParent.setValue(DataObjectView.group_s);
			superParent.setParent(rootParent);
			rootParent.addChild(superParent);
			SolutionAdapterView adapter = new SolutionAdapterView();
			adapter.populateSolutionAdapter((Object) superParent, superParent
					.getName());
			result = true;
		}
		return result;
	}

	/**
	 * This method is used to drop the field item from the datasource tree to
	 * dataobject tree under class
	 * 
	 * @param parent
	 *            This of type TreeParent which contains the class tree object
	 * @param dataBean
	 *            This of type DatabaseBean which contains all the information
	 *            about the tree
	 * @param fieldList
	 *            This ArrayList contains all the field information
	 * @param fieldType
	 *            This HashMap contains all the field types
	 * @return true if added to the tree
	 */
	public boolean dropFieldInTree(TreeParent parent, DatabaseBean dataBean,
			ArrayList fieldList, HashMap fieldType) {
		boolean result = false;
		fieldList = dataBean.getFieldMap();
		fieldType = dataBean.getFieldType();

		try {
			String fieldname = (String) dataBean.getFieldName();
			fieldname = fieldname.substring(0, fieldname.indexOf(" "));
			if (checkForDuplicateField(parent, fieldname, (String) fieldType
					.get(dataBean.getFieldName()), "field")) {
				TreeParent treeObj = new TreeParent(fieldname);
				treeObj.setValue(DataObjectView.field_s);

				String fieldtype = (String) fieldType.get(fieldList.get(0));
				// put the mapped java type with the sql type
				if (fieldtype.equalsIgnoreCase(SASConstants.SAS_SQL_NUMBER_s)) {
					fieldtype = SASConstants.DATAOBJECT_JAVATYPENUMBER;
				}// pu the mapped date jave type with the sql type
				else if (fieldtype
						.equalsIgnoreCase(SASConstants.SAS_SQL_DATE_s)) {
					fieldtype = SASConstants.DATAOBJECT_JAVATYPEDATE;
				} else {
					if (checkForDateDataType(fieldtype)
							|| checkForNumberDataType(fieldtype)) {
						treeObj.setType(fieldtype);
					} else {
						fieldtype = SASConstants.DATAOBJECT_JAVATYPEVARCHAR;
					}
				}
				treeObj.setParent(parent);
				treeObj.setMapping("");
				treeObj.setDataSource("");
				treeObj.setTableName("");
				treeObj.setType(fieldtype);
				parent.addChild(treeObj);
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * This method is used to drop the mapping element into the field. It is
	 * responsible for mapping the datasource field with the dataobject field
	 * and none other.
	 * 
	 * @param parent
	 *            This of type TreeParent which contains the field tree object
	 * @param dataBean
	 *            This of type DatabaseBean which contains all the information
	 *            about the tree
	 * @param fieldList
	 *            This ArrayList contains all the field information
	 * @param fieldType
	 *            This HashMap contains all the field types
	 * @return true if added to the tree
	 */
	public boolean dropFieldInFieldTree(TreeParent parent,
			DatabaseBean dataBean, ArrayList fieldList, HashMap fieldType) {
		boolean result = false;
		
		try {
			boolean flag = false;
			fieldList = dataBean.getFieldMap();
			fieldType = dataBean.getFieldType();
			String fieldname = (String) dataBean.getFieldName();
			fieldname = fieldname.substring(0, fieldname.indexOf(" "));

			TreeParent treeObj = new TreeParent(fieldname);
			treeObj.setValue(DataObjectView.field_s);
			// has to be changed
			treeObj.setMapping(dataBean.getMappingType());
			treeObj.setDataSource(dataBean.getDataSourceName());
			treeObj.setTableName(dataBean.getTableName());
			String fieldtype = (String) fieldType.get(fieldList.get(0));
			treeObj.setType(fieldtype);
			// put the mapped jave type wiht the sql type
			if (fieldtype.equalsIgnoreCase(SASConstants.SAS_SQL_NUMBER_s)) {
				flag = checkForNumberDataType(parent.getType());
			}// put the mapped date jave type with the sql type
			else if (fieldtype.equalsIgnoreCase(SASConstants.SAS_SQL_DATE_s)) {
				flag = checkForDateDataType(parent.getType());
			} else {
				flag = true;
			}
			treeObj.setType(fieldtype);
			if (flag) {
				if (parent.hasChildren()) {
					TreeObject objTree[] = parent.getChildren();
					if (treeObj.getMapping().equals(
							DataObjectView.mapFieldType_s)) {
						if (!fieldname.contains("::")) {
							fieldname = fieldname;
						}
						if (checkForDuplicateField(
								parent,
								fieldname,
								(String) fieldType.get(dataBean.getFieldName()),
								DataObjectView.field_s)) {
							treeObj.setName(fieldname);
							treeObj.setPrimaryKey(dataBean.isPrimaryKey());
							treeObj.setDataSource(dataBean.getDataSourceName());
							treeObj.setTableName(dataBean.getTableName());
							treeObj.setParent(parent);
							parent.addChild(treeObj);
							result = true;
						}
					}
				} else {
					if (treeObj.getMapping().equals(
							DataObjectView.mapFieldType_s)) {
						if (!fieldname.contains("::")) {
							fieldname = fieldname;
						}
						treeObj.setName(fieldname);
						treeObj.setPrimaryKey(dataBean.isPrimaryKey());
						treeObj.setDataSource(dataBean.getDataSourceName());
						treeObj.setTableName(dataBean.getTableName());

						if (!parent.getMapping().equals(
								DataObjectView.mapFieldType_s)) {
							parent.addChild(treeObj);
							treeObj.setParent(parent);
							result = true;
						}
					}
				}
			} else {
				result = false;
				MessageDialog.openWarning(DataObjectView.parentComposite
						.getShell(), "Warning", "Datatype incompatible");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * This method is used to drop the table and their fields in the dataobject
	 * class tree
	 * 
	 * @param superParent
	 *            This of type TreeParent contains the class tree object
	 * @param dataBean
	 *            This of type DatabaseBean which contains all the information
	 *            about the tree
	 * @param fieldList
	 *            This ArrayList contains all the fields
	 * @param fieldType
	 *            This HashMap contains all the field types
	 * @return true if added to the tree
	 */
	public boolean dropTableAndFieldInTree(TreeParent superParent,
			DatabaseBean dataBean, ArrayList fieldList, HashMap fieldType) {
		boolean result = false;
		if (checkForDuplicateField(superParent, dataBean.getTableName(), "",
				DataObjectView.table_s)) {
			TreeParent parent = new TreeParent(dataBean.getTableName());
			parent.setValue(DataObjectView.class_s);
			fieldList = dataBean.getFieldMap();
			fieldType = dataBean.getFieldType();
			for (int i = 0; i < fieldList.size(); i++) {
				String fieldname = (String) fieldList.get(i);
				fieldname = fieldname.substring(0, fieldname.indexOf(" "));

				TreeParent treeObj = new TreeParent(fieldname);
				treeObj.setValue(DataObjectView.field_s);
				String fieldtype = (String) fieldType.get(fieldList.get(i));
				// pu the mapped java type with the sql type
				if (fieldtype.equalsIgnoreCase(SASConstants.SAS_SQL_NUMBER_s)) {
					fieldtype = SASConstants.DATAOBJECT_JAVATYPENUMBER;
				}// put the mapped date jave type with the sql type
				else if (fieldtype
						.equalsIgnoreCase(SASConstants.SAS_SQL_DATE_s)) {
					fieldtype = SASConstants.DATAOBJECT_JAVATYPEDATE;
				} else {
					if (checkForDateDataType(fieldtype)
							|| checkForNumberDataType(fieldtype)) {
						treeObj.setType(fieldtype);
					} else {
						fieldtype = SASConstants.DATAOBJECT_JAVATYPEVARCHAR;
					}
				}
				treeObj.setMapping("");
				treeObj.setDataSource("");
				treeObj.setTableName("");
				treeObj.setParent(parent);
				treeObj.setType(fieldtype);
				parent.addChild(treeObj);
			}
			parent.setParent(superParent);
			superParent.addChild(parent);
			result = true;
		}
		return result;
	}

	/**
	 * This method is used to drop the atomicmetric from the Project view to the
	 * dataobject view under class field. One atomic metric can be placed under
	 * one field
	 * 
	 * @param parent
	 *            This is of type TreeParent on which atomicmetric child will be
	 *            placed
	 * @param atomicBean
	 *            This of type AtomicMetricBean which contains the information
	 *            about the atomicmetric
	 * @param projectName
	 *            This will contain the projectname,compositeevent
	 *            name,simpleevent name,groupmetrci name etc.,
	 */
	public boolean dropAtomicIntoTree(TreeParent parent,
			AtomicMetricBean atomicBean) {
		boolean result = false;
		boolean isMapping = false;
		isMapping = parent.getMapping().equals(DataObjectView.mapFieldType_s)
				|| parent.getMapping().equals(DataObjectView.mapAtomicType_s);
		if (!isMapping) {
			TreeParent newChild = new TreeParent(atomicBean.getName());
			// if no children add the atomicmetric
			if (parent.hasChildren()) {
				boolean atomicPresent = true;
				TreeObject objTree[] = parent.getChildren();
				for (int i = 0; i < objTree.length; i++) {// check for
					// atomicmetric
					// mapping if
					// already present
					// warning message will come to override
					if (objTree[i].getMapping().equals(
							DataObjectView.mapAtomicType_s)) {
						atomicPresent = false;
						MessageDialog dialog = new MessageDialog(
								DataObjectView.parentComposite.getShell(),
								"Warning!",
								null,
								"Atomicmetric already exist do you want to replace?",
								MessageDialog.QUESTION, new String[] {
										SASConstants.UI_OK_BUTTON_s,
										SASConstants.UI_CANCEL_BUTTON_s }, 2);
						int answer = dialog.open();
						boolean flag = false;
						switch (answer) {
						case 0:
							flag = true;
							break;
						case 1:
							flag = false;
							break;
						}// this is for replace the already exisiting one
						if (flag) {
							if (checkForAtomicType(atomicBean.getType(), parent
									.getType())) {
								parent.removeChild(objTree[i]);
								newChild.setParent(parent);
								newChild.setValue(DataObjectView.field_s);
								newChild
										.setMapping(DataObjectView.mapAtomicType_s);
								newChild.setDataSource("");
								newChild.setTableName("");
								newChild.setAtomicBean(atomicBean);
								parent.addChild(newChild);
								result = true;
							} else {
								MessageDialog.openWarning(
										DataObjectView.parentComposite
												.getShell(), "Warning!",
										"Datatype incompatible");
							}
							break;
						}
					}
				}// if no atomicmetric is present
				if (atomicPresent) {
					if (checkForAtomicType(atomicBean.getType(), parent
							.getType())) {
						newChild.setParent(parent);
						newChild.setParent(parent);
						newChild.setValue(DataObjectView.field_s);
						newChild.setMapping(DataObjectView.mapAtomicType_s);
						newChild.setDataSource("");
						newChild.setTableName("");
						newChild.setAtomicBean(atomicBean);
						parent.addChild(newChild);
						result = true;
					} else {
						MessageDialog.openWarning(
								DataObjectView.parentComposite.getShell(),
								"Warning!", "Datatype incompatible");
					}
				}
			}// if no child is present
			else {
				if (checkForAtomicType(atomicBean.getType(), parent.getType())) {
					newChild.setParent(parent);
					newChild.setValue(DataObjectView.field_s);
					newChild.setMapping(DataObjectView.mapAtomicType_s);
					newChild.setDataSource("");
					newChild.setTableName("");
					newChild.setAtomicBean(atomicBean);
					parent.addChild(newChild);
					result = true;
				} else {
					MessageDialog.openWarning(DataObjectView.parentComposite
							.getShell(), "Warning!", "Datatype incompatible");
				}
			}
		}
		return result;
	}

	/**
	 * This method is used to check for the duplicates present in the current
	 * tree item and remove the old one by placing the new one by asking the
	 * user ok or cancel
	 * 
	 * @param parent
	 *            This is of type TreeParent on which duplicate checking will
	 *            take part
	 * @param fieldName
	 *            This string contains the name for checking
	 * @param fieldType
	 *            This string contains type of the field
	 * @param message
	 *            This string will contains either group or class or field
	 * @return true if user clicks OK button or else false
	 */
	public boolean checkForDuplicateField(TreeParent parent, String fieldName,
			String fieldType, String message) {
		TreeObject objTree[] = parent.getChildren();
		boolean flag = true;

		for (int i = 0; i < objTree.length; i++) {
			String presentField = objTree[i].getName();
			if (presentField.equals(fieldName)) {
				Shell shell = DataObjectView.parentComposite.getShell();
				MessageDialog dialog = new MessageDialog(shell, "Warning!",
						null, "Duplicate " + message
								+ " name exist do you want to replace?",
						MessageDialog.QUESTION, new String[] {
								SASConstants.UI_OK_BUTTON_s,
								SASConstants.UI_CANCEL_BUTTON_s }, 2);
				int answer = dialog.open();
				flag = false;
				switch (answer) {
				case 0:
					flag = true;
					parent.removeChild(objTree[i]);
					break;
				case 1:
					flag = false;
					break;
				}

			}
		}
		return flag;
	}

	/**
	 * This method is used to check for the sql number compatible with java data
	 * types
	 * 
	 * @param sqlDataType
	 *            Which contains the sql number type
	 * @return true if it is compatible else false
	 */
	protected boolean checkForNumberDataType(String sqlDataType) {
		if (SASConstants.SAS_SQL_MAPPING_NUMBER_s.contains(sqlDataType)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * This method is used to check for the sql Date compatible with java data
	 * types
	 * 
	 * @param sqlDataType
	 *            Which contains the sql date type
	 * @return true if it is compatible else false
	 */
	protected boolean checkForDateDataType(String sqlDataType) {
		if (SASConstants.SAS_SQL_MAPPING_DATE_s.contains(sqlDataType)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * This method is used to check the atomicmetric type with the
	 * IntermediateDataObject datatype
	 * 
	 * @param dataType
	 *            This string will hold the atomicmetric datatype
	 * @param parentType
	 *            This string will hold the IntermediateDataObject datatype
	 * @return It returns true if match is present or else false
	 */
	protected boolean checkForAtomicType(String dataType, String parentType) {
		boolean flag = false;
		if (dataType.equals(SASConstants.SAS_JAVA_STR_OBJ_s)
				&& parentType.equals(SASConstants.SAS_JAVA_STR_OBJ_s)) {
			flag = true;
		} else if (dataType.equals(SASConstants.SAS_JAVA_SHORT_OBJ_s)
				&& checkForshort(parentType)) {
			flag = true;
		} else if (dataType.equals(SASConstants.SAS_JAVA_INT_OBJ_s)
				&& checkForint(parentType)) {
			flag = true;
		} else if (dataType.equals(SASConstants.SAS_JAVA_LONG_OBJ_s)
				&& checkForlong(parentType)) {
			flag = true;
		} else if (dataType.equals(SASConstants.SAS_JAVA_FLOAT_OBJ_s)
				&& checkForfloat(parentType)) {
			flag = true;
		} else if (dataType.equals(SASConstants.SAS_JAVA_DOUBLE_OBJ_s)
				&& checkFordouble(parentType)) {
			flag = true;
		} else if (dataType.equals(SASConstants.SAS_JAVA_UTIL_DATE_OBJ_s)
				&& checkFordate(parentType)) {
			flag = true;
		} else if (dataType.equals(SASConstants.SAS_JAVA_BOOL_OBJ_s)
				&& parentType.equals(SASConstants.SAS_JAVA_BOOL_s)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * This method is used to check for the atomicmetric datatype Short object
	 * with short,int,float,long,double in the IntermediateDataObject datatype
	 * 
	 * @param parentType
	 *            This string holds the IntermediateDataObject datatype
	 * @return It returns true if match present or else false
	 */
	protected boolean checkForshort(String parentType) {

		if (parentType.equals(SASConstants.SAS_JAVA_SHORT_s)) {
			return true;
		} else if (parentType.equals(SASConstants.SAS_JAVA_INT_s)) {
			return true;
		} else if (parentType.equals(SASConstants.SAS_JAVA_FLOAT_s)) {
			return true;
		} else if (parentType.equals(SASConstants.SAS_JAVA_LONG_s)) {
			return true;
		} else if (parentType.equals(SASConstants.SAS_JAVA_DOUBLE_s)) {
			return true;
		}
		return false;
	}

	/**
	 * This method is used to check for the atomicmetric datatype Integer object
	 * with int,float,long,double in the IntermediateDataObject datatype
	 * 
	 * @param parentType
	 *            This string holds the IntermediateDataObject datatype
	 * @return It returns true if match present or else false
	 */
	protected boolean checkForint(String parentType) {
		if (parentType.equals(SASConstants.SAS_JAVA_INT_s)) {
			return true;
		} else if (parentType.equals(SASConstants.SAS_JAVA_FLOAT_s)) {
			return true;
		} else if (parentType.equals(SASConstants.SAS_JAVA_LONG_s)) {
			return true;
		} else if (parentType.equals(SASConstants.SAS_JAVA_DOUBLE_s)) {
			return true;
		}
		return false;
	}

	/**
	 * This method is used to check for the atomicmetric datatype Float object
	 * with float,long,double in the IntermediateDataObject datatype
	 * 
	 * @param parentType
	 *            This string will hold the IntermediateDataObject datatype
	 * @return It returns true if match is present or else false
	 */
	protected boolean checkForfloat(String parentType) {
		if (parentType.equals(SASConstants.SAS_JAVA_FLOAT_s)) {
			return true;
		} else if (parentType.equals(SASConstants.SAS_JAVA_DOUBLE_s)) {
			return true;
		}
		return false;
	}

	/**
	 * This method is used to check for the atomicmetric datatype Long object
	 * wiht long,double in the IntermediateDataObject datatype
	 * 
	 * @param parentType
	 *            This string will hold the IntermediateDataObject datatype
	 * @return It returns true if match is present or else false
	 */
	protected boolean checkForlong(String parentType) {
		if (parentType.equals(SASConstants.SAS_JAVA_LONG_s)) {
			return true;
		} else if (parentType.equals(SASConstants.SAS_JAVA_DOUBLE_s)) {
			return true;
		}
		return false;
	}

	/**
	 * This method is used to check for the atomicmetric datatype Double object
	 * with double in the IntermediateDataObject datatype
	 * 
	 * @param parentType
	 *            This string will hold the IntermediateDataObject datatype
	 * @return It returns true if match is present or else false
	 */
	protected boolean checkFordouble(String parentType) {
		if (parentType.equals(SASConstants.SAS_JAVA_DOUBLE_s)) {
			return true;
		}
		return false;
	}

	/**
	 * This method is used to check for the atomicemtric datatype Date object
	 * with the Date object in the IntermediateDataObject datatype
	 * 
	 * @param parentType
	 *            This string will hold the IntermediateDataObject datatype
	 * @return It returns true if match is present or else false
	 */
	protected boolean checkFordate(String parentType) {
		if (parentType.equals(SASConstants.SAS_JAVA_UTIL_DATE_OBJ_s)) {
			return true;
		} else if (parentType.equals(SASConstants.SAS_JAVA_SQL_DATE_OBJ_s)) {
			return true;
		}
		return false;
	}
}
