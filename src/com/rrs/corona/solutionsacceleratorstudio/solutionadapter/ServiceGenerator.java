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
 *$Id: ServiceGenerator.java,v 1.2 2006/07/28 13:26:16 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/ServiceGenerator.java,v $
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
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

/**
 * @author Debadatta Mishra
 * 
 */
public class ServiceGenerator {
	/**
	 * PrintWriter object
	 */
	public PrintWriter printWriter = null;

	/**
	 * Package name of the Intermediate source file
	 */
	//String packageName = SASConstants.ORMSERVICE_PKG_NAME;
	String packageName = "com.rrs.corona";

	/**
	 * File Name of the ORM Service
	 */
	//String serviceFileName = SASConstants.MDBSERVICE_FILE_NAME;
	String serviceFileName = "_MDB" ; //SASConstants.MDBSERVICE_FILE_NAME;

	/**
	 * Method used to obtain an object of type PrintWriter
	 * 
	 * @param filePath
	 *            of type String refering the path of generated file
	 * @return an object of type of PrintWriter
	 */
	public PrintWriter getPrintWriter(final String filePath,final String mdbFileName) {// This method
		// provides a
		// PrintWriter
		// object
		try {
			printWriter = new PrintWriter(new FileOutputStream(filePath
					+ SASConstants.BACK_SLASH_s + SASConstants.IOSRCDIR_NAME
					+ SASConstants.BACK_SLASH_s + mdbFileName+serviceFileName+".java"));
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**getPrintWriter()** in class [ ServiceGenerator.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
		return printWriter;
	}

	/**
	 * Method to generate the header for generated java source file
	 */
	public void generateHeader(final String mdbPkgName,final String mdbClassName) 
	{// This method is used to generate the
		// header ie import statements and others
		// for the generated java source file
		String mdbPackageName = packageName+"."+mdbPkgName;
		
		System.out.println("MDB package Name ::: "+mdbPkgName);
		printWriter.write("/* This is an auto generated source file,*/");
		printWriter.write("\n");
		printWriter.write("/* please do not modify it. */");
		printWriter.write("\n");
		//printWriter.write("package " + packageName + ";\n");
		printWriter.write("package " + mdbPackageName + ";\n");
		printWriter.write("\n");
		String importPkgName = "com.rrs.corona.generatedobj"+"."+mdbPkgName+".* ;" ;
		
		printWriter.write("import "+importPkgName+" \n");
		
		
		printWriter.write("import com.rrs.corona.generatedobj.* ; \n");
		
		printWriter.write("import java.io.CharArrayReader;\n");
		printWriter.write("import java.util.ArrayList;\n");
		printWriter.write("import org.apache.commons.logging.Log;\n");
		printWriter.write("import org.apache.commons.logging.LogFactory;\n");
		printWriter.write("import org.xml.sax.InputSource;\n");
		printWriter
				.write("import com.rrs.corona.metricevent.CompositeEvent;\n");
		printWriter.write("import javax.ejb.CreateException;\n");
		printWriter.write("import javax.ejb.EJBException;\n");
		printWriter.write("import javax.ejb.MessageDrivenBean;\n");
		printWriter.write("import javax.ejb.MessageDrivenContext;\n");
		printWriter.write("import javax.jms.Message;\n");
		printWriter.write("import javax.jms.MessageListener;\n");
		printWriter.write("import javax.jms.TextMessage;\n");
		printWriter.write("import javax.xml.bind.JAXBContext;\n");
		printWriter.write("import javax.xml.bind.Unmarshaller;\n");
		
		String ioPkgName = "com.rrs.corona.generatedobj."+ mdbPkgName +".DataFromCompositeEvent;";
		printWriter
		.write("import "+ioPkgName+"\n");
		
		
//		printWriter
//				.write("import com.rrs.corona.generatedobj.DataFromCompositeEvent;\n");
		
//		printWriter.write("public class ServiceProvider "
//				+ "implements MessageDrivenBean,MessageListener\n");
		printWriter.write("public class "+ mdbClassName
				+ "  implements MessageDrivenBean,MessageListener\n");
		
		printWriter.write("{\n");
		printWriter.write("\tprivate TextMessage textmessage = null ;\n");
		printWriter.write("\tprivate JAXBContext jaxbCtxt = null ;\n");
		printWriter.write("\tprivate CompositeEvent compositeEvent = null ;\n");
		printWriter
				.write("\tprotected final Log logger_ = LogFactory.getLog(getClass()) ;\n");
	}

	/**
	 * Method to generate the Unimplemented methods for MDB
	 */
	public void generateEJBMethods() {// This method is used to generate the
		// unimplemented methods for MDB
		printWriter.write("\n");
		printWriter
				.write("\tpublic void "
						+ "setMessageDrivenContext( MessageDrivenContext arg0 ) throws EJBException\n");
		printWriter.write("\t{\n");
		printWriter.write("\t}\n");
		printWriter.write("\tpublic void ejbCreate() throws CreateException\n");
		printWriter.write("\t{\n");
		printWriter.write("\t}\n");
		printWriter.write("\tpublic void ejbRemove( ) throws EJBException\n");
		printWriter.write("\t{\n");
		printWriter.write("\t}\n");
		printWriter.write("\n");
	}

	/**
	 * Method to generate OnMessage() method for MDB
	 */
	public void generateONMessage(final String ormClassName) {// Method to generate OnMessage method
		// for MDB
		printWriter.write("\tpublic void onMessage( Message message)\n");
		printWriter.write("\t{\n");
		printWriter.write("\t\t try\n");
		printWriter.write("\t\t {\n");
		printWriter.write("\t\t\ttextmessage = (TextMessage)message ;\n");
		printWriter
				.write("\t\t\tfinal CharArrayReader "
						+ "caReader = new CharArrayReader(textmessage.getText( ).toCharArray( ) ) ;\n");
		printWriter
				.write("\t\t\tfinal InputSource inputSource = new InputSource(caReader) ;\n");
		printWriter.write("\t\t\tjaxbCtxt = JAXBContext.newInstance(" + "\""
				+ SASConstants.COMPOSITEJAXB_CONTEXT + "\" " + ");\n");
		printWriter
				.write("\t\t\tfinal Unmarshaller unMarshall = jaxbCtxt.createUnmarshaller( ) ;\n");
		printWriter
				.write("\t\t\tcompositeEvent = (CompositeEvent) unMarshall.unmarshal(inputSource) ;\n");
		printWriter
				.write("\t\t\tDataFromCompositeEvent dataFmComEvent = new DataFromCompositeEvent() ;\n");
		printWriter
				.write("\t\t\tArrayList list = dataFmComEvent.GetDataFromCE(compositeEvent);\n");
		printWriter.write("\t\t\tif(list.size() == 0 )\n");
		printWriter.write("\t\t\t{\n");
		printWriter.write("\t\t\t   // donothing \n");
		printWriter.write("\t\t\t}\n");// end of if statement
		printWriter.write("\t\t\telse\n");
		printWriter.write("\t\t\t{\n");
		printWriter.write("\t\t\t    \n");
		
		//printWriter.write("\t\t\t\tORManager manager = new ORManager() ;\n");
		String ormObjectName = ormClassName+" manager = new "+ormClassName+"() ;";
		printWriter.write("\t\t\t\t"+ormObjectName+"\n");
		
		printWriter.write("\t\t\t\tfor(int i =0 ; i < list.size();i++)\n");
		printWriter.write("\t\t\t\t  {\n");
		printWriter.write("\t\t\t\t\tObject obj = (Object)list.get(i);\n");
		// printWriter.write("\t\t\t\tORManager manager = new ORManager() ;\n");
		printWriter.write("\t\t\t\t\tmanager.storeData(obj) ;\n");
		printWriter.write("\t\t\t\t  }\n");// end of for loop
		printWriter.write("\t\t\t}\n");// end of else statement
		printWriter.write("\t\t }\n");// end try block
		generateEJBExceptions();// Method to generate Exceptions for EJB
		printWriter.write("\t}\n");// end of ONMessage method
	}

	/**
	 * Method to generate the Exception for try block
	 */
	public void generateEJBExceptions() {// This method is used to generate
		// the Exceptions for try block
		printWriter.write("\t\t  catch(EJBException ejbe)\n");
		printWriter.write("\t\t  {\n");
		printWriter.write("\t\t\tejbe.printStackTrace();\n");
		printWriter.write("\t\t  }\n");
		printWriter.write("\t\t  catch(Exception e)\n");
		printWriter.write("\t\t  {\n");
		printWriter.write("\t\t\te.printStackTrace();\n");
		printWriter.write("\t\t  }\n");
	}

	/**
	 * Method to generate the last part of the class
	 */
	public void generateFooter() {// This method is used to generate the
		// last part of the generated class
		printWriter.write("\n");
		printWriter.write("}\n");
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * @param filePath
	 *            of type String . It referes to the path where the java file
	 *            will be generated
	 * @param mdbFileName            
	 */
	public void generateMDBService(final String filePath,final String mdbFileName) {// This method is
		// used to generate
		// the Service class
		// for MDB, it calls
		// some other
		// methods
		printWriter = getPrintWriter(filePath,mdbFileName);
		final String pkgName = mdbFileName.replace( '_' , '.');
		final String mdbClassName = mdbFileName+serviceFileName;
		final String ormClassName = mdbFileName+"_OJB";
		String mdbPackageName = packageName+"."+pkgName;
		generateHeader(pkgName,mdbClassName);// Method to generate class header ie imports
		// statements
		generateEJBMethods();// Method to generate unimplemented methods for
		// EJB
		generateONMessage(ormClassName);// Method to generae ONMessage method for MDB
		generateFooter();// Method to generate the last part of the java file
	}



}
