/*******************************************************************************
 * @rrs_start_copyright
 * 
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved. This
 * software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Red Rabbit Software. ©
 * 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights
 * reserved. Red Rabbit Software - Development Program 5 of 15 $Id:
 * ClassCompiler.java,v 1.1.2.2 2006/05/20 06:11:30 deba Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/Attic/ClassCompiler.java,v $
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

package com.rrs.corona.solutionsacceleratorstudio.solutionadapter;

import java.io.File;
import com.rrs.corona.common.CommonConstants;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;
import com.rrs.corona.util.Compiler;

/**
 * @author Debadatta Mishra
 * 
 */
public class ClassCompiler {// The following variables are used to refer to the
	// jar files required for the time of compilation
	private transient String libPath = SASConstants.SAS_LIBS;

	File ff = new File(libPath);

	private transient String class12 = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.OJB_LIB_DIR_NAME
			+ SASConstants.BACK_SLASH_s + SASConstants.CLASSES12_JAR;

	private transient String common_cln = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.OJB_LIB_DIR_NAME
			+ SASConstants.BACK_SLASH_s + SASConstants.CMNCLN_JAR;

	private transient String common_dbcp = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.OJB_LIB_DIR_NAME
			+ SASConstants.BACK_SLASH_s + SASConstants.CMNDBCP_JAR;

	private transient String common_lang = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.OJB_LIB_DIR_NAME
			+ SASConstants.BACK_SLASH_s + SASConstants.CMNLANG_JAR;

	private transient String common_logging = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.OJB_LIB_DIR_NAME
			+ SASConstants.BACK_SLASH_s + SASConstants.CMNLOGGING_JAR;

	private transient String common_pool = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.OJB_LIB_DIR_NAME
			+ SASConstants.BACK_SLASH_s + SASConstants.CMNPOOL_JAR;

	private transient String db_ojb = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.OJB_LIB_DIR_NAME
			+ SASConstants.BACK_SLASH_s + SASConstants.DBOJB_JAR;

	private transient String log4j = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.OJB_LIB_DIR_NAME
			+ SASConstants.BACK_SLASH_s + SASConstants.LOG4J_JAR;

	private transient String xercesImpl = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.OJB_LIB_DIR_NAME
			+ SASConstants.BACK_SLASH_s + SASConstants.XERCESIMPL_JAR;

	private transient String schema = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.SCHEMA_JAR;

	private transient String jaxb_api = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.JAXBAPI_JAR;

	private transient String jaxb_impl = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.JAXBIMPL_JAR;

	// String jaxb_libs = ff.getAbsoluteFile( ) + "/jaxb-libs.jar";
	private transient String jaxb1_impl = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.JAXB1IMPL_JAR;

	private transient String jaxb_xjc = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.JAXBXJC_JAR;

	// New jar file for JAXB2.0
	private transient String activationJar = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.ACTIVATION_JAR;

	private transient String jassJar = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.JAAS_JAR;

	private transient String jta_specJar = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.JTASPEC_JAR;

	private transient String mailJar = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.MAIL_JAR;

	private transient String relaxngDatatypeJar = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.RLXNGTYPE_JAR;

	private transient String resolverJar = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.RESLOVER_JAR;

	private transient String xmlsecJar = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.XMLSEC_JAR;

	private transient String xsdLibJar = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.XSDLIB_JAR;

	private transient String jsr173_apiJar = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.JSRAPI_JAR;

	private transient String sjsxpJar = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.SJSXP_JAR;

	private transient String jboss_allclient = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.JBOSS_LIB_DIR_NAME
			+ SASConstants.BACK_SLASH_s + SASConstants.JBOSSALLCLIENT_JAR;

	private transient String jboss_j2ee = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.JBOSS_LIB_DIR_NAME
			+ SASConstants.BACK_SLASH_s + SASConstants.JBOSSJ2EE_JAR;

	private transient String toolsJar = ff.getAbsoluteFile()
			+ SASConstants.BACK_SLASH_s + SASConstants.TOOLS_JAR;

	private transient String destnDir = null;

	private transient String classPaths = null;

	/**
	 * The default constructor initializes the destination path of the compiled
	 * classes and classpath for compiling the java source files
	 * 
	 * @param destnPath
	 *            of type String specifying the path of the Compiled classes
	 */
	public ClassCompiler(final String destnPath) {// Constuctor to initialize
													// the path where
		// Compiled classes will be placed and
		// initializes the classPath for Compilation
		this.destnDir = destnPath;
		this.classPaths = destnDir + ";" + toolsJar + ";" + class12 + ";"
				+ common_cln + ";" + common_dbcp + ";" + common_lang + ";"
				+ common_logging + ";" + common_pool + ";" + db_ojb + ";"
				+ schema + ";" + jaxb_api + ";" + jaxb_impl + ";" + ";"
				+ jaxb_xjc + ";" + activationJar + ";" + jassJar + ";"
				+ jta_specJar + ";" + mailJar + ";" + relaxngDatatypeJar + ";"
				+ resolverJar + ";" + jsr173_apiJar + ";" + xmlsecJar + ";"
				+ xsdLibJar + ";" + sjsxpJar + ";" + jboss_allclient + ";"
				+ jboss_j2ee + ";" + log4j + ";" + xercesImpl;
	}

	/**
	 * This method is used to obtain the list of files present in a directory
	 * 
	 * @param srcPath
	 *            of type String specifying the path of the directory
	 * @return the list of files
	 */
	public File[] getSrcFiles(final String srcPath) {// Provides the list of
														// files in a directory
		File file = new File(srcPath);
		return file.listFiles();
	}

	/**
	 * This method is used to compile the java source files present in a
	 * directory
	 * 
	 * @param sourcepath
	 *            of type String specifying the name of the directory containing
	 *            the java files
	 */
	public void CompileSRCFiles(final String sourcepath) {// This method is
															// used to compile
															// the source
		// java files present in a directory
		try {
			File[] filesList = getSrcFiles(sourcepath);
			for (int i = 0; i < filesList.length; i++) {
				if (filesList[i].isDirectory())
					continue;
				else {
					String fileName = filesList[i].getName();
					String javaFileName = fileName.substring(0, fileName
							.length() - 5);
					int result = Compiler.compile(javaFileName, sourcepath,
							destnDir, classPaths);
				}
			}
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method " +
					"**CompileSRCFiles()** in class [ ClassCompiler.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

}
