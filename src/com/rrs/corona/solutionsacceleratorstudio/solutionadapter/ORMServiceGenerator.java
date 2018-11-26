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
 *$Id: ORMServiceGenerator.java,v 1.2 2006/07/28 13:26:16 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/ORMServiceGenerator.java,v $
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

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

/**
 * @author Debadatta Mishra
 * 
 */
public class ORMServiceGenerator {
	/**
	 * PrintWriter object
	 */
	public PrintWriter printWriter = null;

	/**
	 * Package name of the Intermediate source file
	 */
	String packageName = SASConstants.ORMSERVICE_PKG_NAME;

	/**
	 * File Name of the ORM Service
	 */
	String ORMServiceFileName = SASConstants.ORMANAGER_FILE_NAME;

	/**
	 * ArrayList containing the list of Class Object names
	 */
	ArrayList classNameList = new CoronaMappingParser().getClassNameList();

	/**
	 * Method used to obtain an object of type PrintWriter
	 * 
	 * @param filePath
	 *            of type String refering the path of generated file
	 * @return an object of type of PrintWriter
	 */
	public PrintWriter getPrintWriter(final String filePath) {// This method
		// provides a
		// PrintWriter
		// object
		try {
			printWriter = new PrintWriter(new FileOutputStream(filePath
					+ SASConstants.BACK_SLASH_s + SASConstants.IOSRCDIR_NAME
					+ SASConstants.BACK_SLASH_s + ORMServiceFileName));
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**getPrintWriter()** in class [ ORMServiceGenerator.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
		return printWriter;
	}

	/**
	 * Method to generate the Class Header of the generated source file
	 */
	public void generateHeader() {// This method generates required import
		// libraries and class name

		printWriter.write("/* This is an auto generated source file,*/");
		printWriter.write("\n");
		printWriter.write("/* please do not modify it. */");
		printWriter.write("\n");
		printWriter.write("package " + packageName + ";\n");
		printWriter.write("\n");
		for (int i = 0; i < classNameList.size(); i++) {
			printWriter
					.write("import " + (String) classNameList.get(i) + ";\n");
		}
		printWriter.write("import org.apache.commons.logging.Log;\n");
		printWriter.write("import org.apache.commons.logging.LogFactory; \n");
		printWriter.write("import org.apache.ojb.broker.PersistenceBroker; \n");
		printWriter
				.write("import org.apache.ojb.broker.PersistenceBrokerFactory; \n");
		printWriter.write("public class ORManager\n");
		printWriter.write("{\n");
		printWriter.write("\tprotected final Log logger_ = "
				+ "LogFactory.getLog(getClass()) ;\n");
		printWriter.write("\tPersistenceBroker persistenceBroker = null ;\n");
		printWriter.write("\tpublic void storeData(Object object)\n");
		printWriter.write("\t{\n");
		printWriter.write("\t\ttry\n");
		printWriter.write("\t\t{\n");
		writeToStoreObjects();// Method to generate the lines to store the
		// object in the database
	}

	/**
	 * Method used to generate the code to store objects in the database
	 */
	public void writeToStoreObjects() {// This method generates the lines of
		// ORM to store data in the data base
		printWriter.write("\t\t\tpersistenceBroker = "
				+ "PersistenceBrokerFactory.defaultPersistenceBroker() ;\n");
		printWriter.write("\t\t\tpersistenceBroker.beginTransaction();\n");
		for (int i = 0; i < classNameList.size(); i++) {
			String tempString = classNameList.get(i).toString();
			String objectName = tempString.substring(tempString
					.lastIndexOf(".") + 1, tempString.length());
			printWriter.write("\t\t\tif( object instanceof " + objectName
					+ ")\n");
			printWriter.write("\t\t\t  {\n");
			printWriter.write("\t\t\t      " + objectName + "  "
					+ objectName.toLowerCase() + "1" + "  =  " + "("
					+ objectName + ")" + "object ;\n");
			printWriter.write("\t\t\t      persistenceBroker.store("
					+ objectName.toLowerCase() + "1" + ");\n");
			printWriter
					.write("\t\t\t      persistenceBroker.commitTransaction() ;\n");
			printWriter.write("\t\t\t  }\n");
		}
	}

	/**
	 * Method used to generate the last part of the generated class file
	 */
	public void generateFooter() {// This method is used to generate the code
		// to complete a java source file
		printWriter.write("\n");
		printWriter.write("\t\t}\n");
		printWriter.write("\t\tcatch(Exception e)\n");
		printWriter.write("\t\t{\n");
		printWriter.write("\t\t\te.printStackTrace();\n");
		printWriter.write("\t\t}\n");// end of catch block
		printWriter.write("\t\tfinally\n");
		printWriter.write("\t\t{\n");
		printWriter.write("\t\t   if(persistenceBroker != null)\n");
		printWriter.write("\t\t\t{\n");
		printWriter.write("\t\t\t  persistenceBroker.close();\n");
		printWriter.write("\t\t\t}\n");// end of if block for finally
		printWriter.write("\t\t}\n");// end of finally block
		printWriter.write("\t}\n");// end of method
		printWriter.write("\n");
		printWriter.write("}\n");
		printWriter.flush();
		printWriter.close();
	}

	/**
	 * Method used to generate the code for ORMService.java file
	 * 
	 * @param filePath
	 *            of type String which refers the path of the generated java
	 *            source file
	 */
	public void generateORMService(final String filePath) {// This method is
		// used to generate
		// the
		// ORMService.java
		// file
		// This method calls other methods
		try {
			printWriter = getPrintWriter(filePath);
			generateHeader();
			generateFooter();
		} catch (Exception e) {
			final String errMsg = "Exception thrown in "
					+ "Method **generateORMService()** in class [ ORMServiceGenerator.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

}
