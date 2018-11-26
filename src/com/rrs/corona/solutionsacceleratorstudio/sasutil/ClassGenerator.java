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
 *$Id: ClassGenerator.java,v 1.2 2006/07/28 13:27:40 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/sasutil/ClassGenerator.java,v $
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
package com.rrs.corona.solutionsacceleratorstudio.sasutil;

import java.io.FileOutputStream;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jboss.logging.Logger;

import com.rrs.corona.beans.FieldInfoManyOne;
import com.rrs.corona.beans.TableClassInfoBean;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SolutionAdapterView;

/**
 * @author Debadatta Mishra
 * 
 */
public class ClassGenerator {
	private transient String packageName = "package "
			+ SASConstants.DATAOBJECT_CONTEXT + "." + "hidden";

	private transient PrintWriter printWriter = null;

	private transient String className = null;

	protected Logger logger = Logger.getLogger(ClassGenerator.class);

	/**
	 * Parameterized Constructor accepting the name of the class to be generated
	 * 
	 * @param className
	 *            of type String,specifying the name of the class
	 */
	public ClassGenerator(final String className) {
		this.className = className;
	}

	/**
	 * Default constructor
	 */
	public ClassGenerator() {

	}

	/**
	 * This method is used to obtain a PrintWriter object
	 * 
	 * @return a Printwriter object
	 */
	private PrintWriter getPrintWriter() {
		try {
			SolutionAdapterView objView = new SolutionAdapterView();
			final String contextPath = SASConstants.SAS_ROOT
					+ objView.getFolderStructure();
			printWriter = new PrintWriter(new FileOutputStream(contextPath
					+ SASConstants.BACK_SLASH_s
					+ SASConstants.DATAOBJECT_CLASSPATH
					+ SASConstants.BACK_SLASH_s + className + ".java"));
		} catch (Exception e) {
			final String errMsg = "Exception occured in Method " +
					"::: getPrintWriter() in the class ::ClassGenerator.java::";
			logger.info(errMsg);
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
		return printWriter;
	}

	/**
	 * This method is used to generate the java files by passing an arraylist
	 * containg the list of class names
	 * 
	 * @param classFieldList
	 *            of type ArrayList containing the list of class names
	 */
	public void generateJavaFile(final ArrayList classFieldList,final String fileName) {
		printWriter = getPrintWriter();
		String pkgName = fileName.replace('_','.');
		generateHeaderForClass(pkgName);// Method to generate the header of the
									// class
		initializeVariables(classFieldList);// Method to initialize the
											// variables in the class
		generateMethods(classFieldList);// Method to generate the methods in the
										// java file
		generateFooter();// Method to generate the last part part of the java
							// files
	}

	/**
	 * Method used to generate the header of the class
	 */
	private void generateHeaderForClass(final String modifiedPkgName) {// This method is used to generate
											// the header of the class
		printWriter.write(packageName+"."+ modifiedPkgName + ";\n");
		printWriter.write("\n");
		printWriter.write("import java.io.Serializable;\n");
		printWriter.write("\n");
		printWriter.write("\npublic class " + className + " " + "implements"
				+ " " + "Serializable" + "\n");
		printWriter.write("\t{");
		printWriter.write("\n");
	}

	/**
	 * This method is used to initialize the variables in the java file
	 * 
	 * @param fieldInfoList
	 *            of type ArrayList containing the class names and the field
	 *            information.
	 */
	private void initializeVariables(final ArrayList fieldInfoList) {// This
																		// method
																		// is
																		// used
																		// to
																		// initialize
																		// the
																		// variables
																		// in a
																		// class
		for (int i = 0; i < fieldInfoList.size(); i++) {
			TableClassInfoBean tbclsBean = (TableClassInfoBean) fieldInfoList
					.get(i);
			final String clsFieldName = tbclsBean.getClsFieldName();
			final String clsFieldType = tbclsBean.getClsFieldType();
			generateFields(clsFieldName, clsFieldType);
		}
	}

	/**
	 * This method is used to generate the requied methods for the fields ie
	 * getter and setter methods
	 * 
	 * @param fieldInfoList
	 *            of type ArrayList containg the field Inforamation
	 */
	private void generateMethods(final ArrayList fieldInfoList) {// This
																	// method is
																	// used to
																	// generate
																	// the
																	// getter/setter
																	// methods
																	// for the
																	// fields
		for (int i = 0; i < fieldInfoList.size(); i++) {
			TableClassInfoBean tbclsBean = (TableClassInfoBean) fieldInfoList
					.get(i);
			final String clsFieldName = tbclsBean.getClsFieldName();
			final String clsFieldType = tbclsBean.getClsFieldType();
			generateGetter(clsFieldName, clsFieldType);// To generate the
														// getter methods
			generateSetter(clsFieldName, clsFieldType);// To generate the
														// setter methods
		}
	}

	/**
	 * This method is used to set the fields
	 * 
	 * @param fieldName
	 *            of type String specifying the Class field name
	 * @param fieldType
	 *            of type String specifying the class field type
	 */
	private void generateFields(final String fieldName, final String fieldType) {// Method
																					// used
																					// to
																					// generate
																					// the
																					// getter
																					// methods
																					// for
																					// the
																					// fields
		printWriter.write("\t\tprivate " + fieldType + " " + fieldName + ";");
		printWriter.write("\n");
	}

	/**
	 * This method is used to generate the getter methods for the required
	 * fields
	 * 
	 * @param fieldName
	 *            of type String specifying the Class field name
	 * @param fieldType
	 *            of type String specifying the class field type
	 */
	private void generateGetter(final String fieldName, final String fieldType) {// Method
																					// used
																					// to
																					// generate
																					// the
																					// getter
																					// methods
																					// for
																					// the
																					// fields
		printWriter.write("\n");
		printWriter.write("\t\tpublic " + fieldType + " " + "get" + fieldName
				+ "()");
		printWriter.write("\n");
		printWriter.write("\t\t{\n");
		printWriter.write("\t\t\treturn" + " " + fieldName + ";");
		printWriter.write("\n");
		printWriter.write("\t\t}");
		printWriter.write("\n");
	}

	/**
	 * This method is used to generate the setter methods for the fields
	 * 
	 * @param fieldName
	 *            of type String specifying the Class field name
	 * @param fieldType
	 *            of type String specifying the class field type
	 */
	private void generateSetter(final String fieldName, final String fieldType) {// Method
																					// to
																					// generate
																					// the
																					// setter
																					// methods
																					// for
																					// the
																					// fields
		printWriter.write("\n");
		printWriter.write("\t\tpublic void" + " " + "set" + fieldName + "("
				+ fieldType + " " + fieldName + ")\n");
		printWriter.write("\t\t{\n");
		printWriter.write("\t\t\tthis." + fieldName + " " + "=" + " "
				+ fieldName + " ;" + "\n");
		printWriter.write("\t\t}\n");
		printWriter.write("\n");
	}

	/**
	 * Method used to generate the last part of the class
	 */
	private void generateFooter() {// This method is used to flush and to close
									// the PrintWriter object
		printWriter.write("\n");
		printWriter.write("\n");
		printWriter.write("\t}");
		printWriter.flush();
		printWriter.close();
	}

	// public void generateJavaClasses(final HashMap classMap)
	// {
	// final Set set = classMap.entrySet();
	// final Iterator itr = set.iterator();
	// while(itr.hasNext())
	// {
	// final Map.Entry me = (Map.Entry)itr.next();
	// final String tableName = (String)me.getKey();
	// System.out.println( "Table Name ::: "+tableName);
	// final ArrayList fieldInfoList = (ArrayList)me.getValue();
	//			generateJavaFile(fieldInfoList);
	//			
	//		}
	//	}

}
