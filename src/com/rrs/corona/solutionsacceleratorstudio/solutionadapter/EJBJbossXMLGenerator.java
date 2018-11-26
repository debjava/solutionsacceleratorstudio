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
 *$Id: EJBJbossXMLGenerator.java,v 1.2 2006/07/28 13:26:16 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/EJBJbossXMLGenerator.java,v $
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
import java.io.FileOutputStream;
import java.io.PrintWriter;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

/**
 * this class is used to generate the Jboss.xml file
 * 
 * @author Debadatta Mishra
 * 
 */
public class EJBJbossXMLGenerator {
	/**
	 * Object of type PrintWriter
	 */
	private PrintWriter printWriter = null;

	/**
	 * 
	 */
	private String meta_inf_dirName = "META-INF";

	/**
	 * 
	 */
	private String ejbJbossXMLfile_Name = "jboss.xml";

	/**
	 * String variable specifying the name of the MDB
	 */
	private String mdbServiceName = SASConstants.MDBSERVICE_FILE_NAME;

	/**
	 * String variable containing the name of the topic where Composite Events
	 * are fired
	 */
	private String topicName = "Corona_CEResult_CEM_Topic";

	/**
	 * @param filePath
	 *            of type String specifying the path of the ejb-jar.xml file
	 * @return an object of type PrintWriter
	 */
	private PrintWriter getPrintWriter(final String filePath) {// This method
		// is used to
		// obtain a
		// PrintWriter
		// object
		// The ejb-jar.xml file will be present inside the
		// directory called "META-INF"
		// This method first creates the directory and then obtains the
		// PrintWriter object
		try {
			File metaDir = new File(meta_inf_dirName);
			if (!metaDir.exists()) {
				new File(filePath + SASConstants.BACK_SLASH_s
						+ meta_inf_dirName).mkdirs();
			} else {
				// do nothing
			}
			printWriter = new PrintWriter(new FileOutputStream(filePath
					+ SASConstants.BACK_SLASH_s + meta_inf_dirName
					+ SASConstants.BACK_SLASH_s + ejbJbossXMLfile_Name));
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**getPrintWriter()** in class [ EJBJbossXMLGenerator.java ] ";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
		return printWriter;
	}

	/**
	 * Method used to generate the xml contents for jboss.xml file
	 */
	private void generateXMLFile(final String mdbClassName) {// This method is used to generate the
		// contents of the xml file
		mdbServiceName = mdbServiceName.substring(0, mdbServiceName
				.indexOf("."));
		printWriter.write("<?xml version=" + "\"" + "1.0" + "\"" + " encoding="
				+ "\"" + "UTF-8" + "\"" + "?>\n");
		printWriter.write("<!DOCTYPE jboss PUBLIC " + "\""
				+ "-//JBoss//DTD JBOSS 3.0//EN" + "\"" + " " + "\""
				+ "http://www.jboss.org/j2ee/dtd/jboss_3_0.dtd" + "\"" + ">\n");
		printWriter.write("\n");
		printWriter.write("<jboss>\n");
		printWriter.write("\t  <enterprise-beans>\n");
		printWriter.write("\t\t\t<message-driven>\n");
		
		
//		printWriter.write("\t\t\t\t<ejb-name>" + mdbServiceName
//				+ "</ejb-name>\n");
		
		printWriter.write("\t\t\t\t<ejb-name>" + mdbClassName
				+ "</ejb-name>\n");

		
		printWriter.write("\t\t\t\t<destination-jndi-name>topic/" + topicName
				+ "</destination-jndi-name>\n");
		printWriter.write("\t\t\t</message-driven>\n");
		printWriter.write("\t  </enterprise-beans>\n");
		printWriter.write("</jboss>\n");
	}

	/**
	 * This method is used to generate the last part of the generated file It is
	 * used to flush and to close the PrintWriter object
	 */
	private void generateFooter() {// This method is used to flush and to close
		// the PrintWriter object
		printWriter.flush();
		printWriter.close();
	}

	/**
	 * Method used to generate the jboss.xml file for MDB
	 * 
	 * @param filePath
	 *            of type String specifying the path of the ejb-jar.xml file
	 */
	public void generateJbossXMLFile(final String filePath,final String fileName) {// This method is used
		// to generate the xml
		// file for MDB
		// It calls other methods
		printWriter = getPrintWriter(filePath);
		String mdbClassName = fileName+"_MDB";
		generateXMLFile(mdbClassName);
		generateFooter();
	}

}
