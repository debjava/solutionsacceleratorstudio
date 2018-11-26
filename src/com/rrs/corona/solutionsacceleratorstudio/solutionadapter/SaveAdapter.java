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
 *$Id: SaveAdapter.java,v 1.4 2006/08/02 12:12:41 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/SaveAdapter.java,v $
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import com.rrs.corona.sasadapter.*;
import com.rrs.corona.common.CommonConstants;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView;
import com.rrs.corona.solutionsacceleratorstudio.orm.IOCreater;
import com.rrs.corona.solutionsacceleratorstudio.orm.OJBMappingGenerator;
import com.rrs.corona.solutionsacceleratorstudio.orm.ORMgenerator;
import com.rrs.corona.solutionsacceleratorstudio.plugin.DatabaseViewer;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;
import com.rrs.corona.solutionsacceleratorstudio.project.ProjectData;
import com.rrs.corona.solutionsacceleratorstudio.sasutil.BatchFileGenerator;
import com.rrs.corona.solutionsacceleratorstudio.sasutil.ClassGenerator;
import com.rrs.corona.solutionsacceleratorstudio.sasutil.Converter;

/**
 * This class is used to save the information of the soulution adapter in an xml
 * file This information will contaisn the mapping information between the
 * project and dataobject and also the mapping information about the datasource
 * and the dataobject
 * 
 * @author Maharajan
 * @author Debadatta Mishra
 * @see com.rrs.corona.solutionsacceleratorstudio.project.ProjectData
 * @see com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView
 * @see com.rrs.corona.solutionsacceleratorstudio.plugin.DatabaseViewer
 */
public class SaveAdapter extends SolutionAdapterView {
	protected Logger logger = Logger.getLogger("SaveAdapter.class");

	/**
	 * This method is used to save all the views information to and xml file. It
	 * saves the information as per the SolutionAdapter selected by the user
	 * 
	 * @param root
	 *            This is of type TreeParent which contains the selected
	 *            soulutionadapter tree object
	 * @param isDataObject
	 *            This boolean will contain true or false if data object has to
	 *            be saved or not
	 */
	public void saveAdapterAsXML(final TreeParent root,
			final boolean isDataObject) {
		try {
			final JAXBContext jtx = JAXBContext.newInstance(
					SASConstants.SOLUTIONS_ADAPTER_CONTEXT, this.getClass()
							.getClassLoader());
			Marshaller marshall = jtx.createMarshaller();
			ObjectFactory objFact = new ObjectFactory();
			SolutionsAdapter adapterRoot = objFact.createSolutionsAdapter();
			// **************adapter**********
			AdapterType adapterType = objFact.createAdapterType();
			adapterType.setCmsServer(root.getServerName());
			ReadAndWriteXML readObj = new ReadAndWriteXML();
			HashMap serverInfo = readObj.getServerInfo(root.getServerName());
			adapterType.setIPAddress((String) serverInfo
					.get(ReadAndWriteXML.IPADDRESS_s));
			adapterType.setPortNo((String) serverInfo
					.get(ReadAndWriteXML.PORTNO_s));
			adapterRoot.setAdapter(adapterType);
			// call data Object view
			if (isDataObject) {
				DataObjectType dataObjectType = objFact.createDataObjectType();
				saveDataObject(dataObjectType);// storing data object in the
				// solutionsadapter.xml file
				adapterRoot.setDataObject(dataObjectType);
			}
			// call project view
			saveProject(adapterRoot);
			// call datasource view
			DataSourceType dataSourceType = objFact.createDataSourceType();
			saveDataSource(dataSourceType);
			adapterRoot.setDataSource(dataSourceType);
			StringBuilder XMLfile = new StringBuilder();
			XMLfile.append(SASConstants.SAS_ROOT);
			XMLfile.append(root.getParent().getName());
			XMLfile.append(SASConstants.BACK_SLASH_s);
			XMLfile.append(root.getName());
			XMLfile.append(SASConstants.BACK_SLASH_s);
			XMLfile.append(SASConstants.SOLUTIONS_ADAPTER_XML);
			File dataObjectfile = new File(XMLfile.toString());
			FileOutputStream fus = new FileOutputStream(dataObjectfile);
			marshall
					.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshall.marshal(adapterRoot, fus);
			fus.close();
			// for temporary use
			generateFiles();// to generate the necessary files for running the
			// application
		} catch (Exception e) {
			final String errMsg = "Exception thrown in "
					+ "**saveAdapterAsXML()** in class [ SaveAdapter.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

	/**
	 * Method to generate required files for Solution Adapter
	 * 
	 */
	private void generateFiles() {// This method is used to generate the files
		// for solutions adapter
		SolutionsAdapter sAdapter = new SAReader().getSA();
		DataObjectType dataObject = sAdapter.getDataObject();
		String generatedFolderPath = new SolutionAdapterView()
				.getFolderStructure();// path of generated folder structure
		String filePath = new File(SASConstants.SAS_ROOT + generatedFolderPath)
				.getAbsolutePath()
				+ SASConstants.BACK_SLASH_s;// Full path
		if (dataObject != null) 
		{
			//The following method will generate the coronaMapping files
			//that provides information about the database tables and 
			//related mapped field
			new CoronaMappingGenerator().generateCoronaMapping();// Method to
			
			final String fileName = generatedFolderPath.replace('/', '_');
			
			
			TableClassInfo tbclsInfo = new TableClassInfo();
			HashMap tabClsMap = new SAReader().getTableClsAllInfo();
			tbclsInfo.generateJavaClasses(tabClsMap,fileName);// to generate the
			// hidden java classes
			// for mapping
			new IOCreater().generateIO(tabClsMap, filePath,fileName);// to generate the
			// Intermediate
			// object to set the
			// atomic metric
			// values
			new OJBMappingGenerator().generateMapping(tabClsMap,fileName);// To
			// generate
			// the OJB
			// Mapping
			// file ie
			// repository.xml
			// file
			//final String fileName = generatedFolderPath.replace('/', '_');
			new ORMgenerator().generateORMService(tabClsMap, filePath,fileName);// To
			// generate
			// the
			// ORManager
			// class
			// to
			// store
			// object
			// using
			// OJB
			// following operations are required
			new ServiceGenerator().generateMDBService(filePath,fileName);// Method to
			
			// generate MDB
			// Service class
			new EJBJarXMLGenerator().generateEJBJar(filePath,fileName);// Method to
			// generate
			// ejb-jar.xml
			// file for MDB
			new EJBJbossXMLGenerator().generateJbossXMLFile(filePath,fileName);// Method
			// to
			// generate
			// jboss.xml
			// file
			// for
			// MDB
			copyConfigFiles(filePath, generatedFolderPath);// to copy the
			// necessary files
			// to run the
			// application
		} else {
			// do not generate files
		}
	}

	/**
	 * This method is used to copy the required cofig files provided by Apache
	 * for running the OJB Application
	 * 
	 * @param filePath
	 *            of type String refering the path of the file
	 */
	private void copyConfigFiles(final String filePath,
			final String generatedFolderPath) 
	{// This method is used to copy the necessary
		//config files
		
		FileUtility fileUtil = new FileUtility();
		fileUtil.deleteDir(filePath+ SASConstants.BACK_SLASH_s + SASConstants.BIN_DIR_NAME);
		fileUtil.copyDirectory(filePath + SASConstants.BACK_SLASH_s
				+ SASConstants.METAINF_DIR_NAME, filePath
				+ SASConstants.BACK_SLASH_s + SASConstants.BIN_DIR_NAME);
		fileUtil.copyFile(SASConstants.OJB_MAPPING_FILE_NAME, filePath,
				filePath + SASConstants.BACK_SLASH_s
						+ SASConstants.BIN_DIR_NAME);
		fileUtil
				.copyFile(SASConstants.OJB_PROPERTY_NAME,
						SASConstants.PROJECT_CONFIG_PATH, filePath
								+ SASConstants.BACK_SLASH_s
								+ SASConstants.BIN_DIR_NAME);
		fileUtil
				.copyFile(SASConstants.REPOSITORY_DTD_NAME,
						SASConstants.PROJECT_CONFIG_PATH, filePath
								+ SASConstants.BACK_SLASH_s
								+ SASConstants.BIN_DIR_NAME);
		ClassCompiler clsCompiler = new ClassCompiler(filePath
				+ SASConstants.BIN_DIR_NAME);
		clsCompiler.CompileSRCFiles(filePath + "classes" + "/");
		clsCompiler.CompileSRCFiles(filePath + "classes" + "/");
		clsCompiler.CompileSRCFiles(filePath + "classes" + "/");
		logger.info("Java class Compilation sucessfull...............");
		String jarFile_Name = generatedFolderPath.replace('/', '_') + ".jar";
				
		 final String batchFileName = generatedFolderPath.replace('/', '_');
		 
		 String batchFilePath = SASConstants.CORONA_HOME_SAS+SASConstants.BACK_SLASH_s
		 + "solutionsacceleratorstudio"
		 + SASConstants.BACK_SLASH_s + "plugins"
		 + SASConstants.BACK_SLASH_s+"com.rrs.corona.sas_1.0.0"
		 +SASConstants.BACK_SLASH_s+"corona"+SASConstants.BACK_SLASH_s+generatedFolderPath;
		 new BatchFileGenerator()
		 .generateBatchFile(batchFilePath, batchFileName);

		
		// generate the batch file to create a jar file
		// for temporary testing

		// JarCreation makeJar = new JarCreation(filePath,jarFile_Name);
		// makeJar.createJar(filePath+SASConstants.BACK_SLASH_s+SASConstants.BIN_DIR_NAME);//for
		// creating jar file
		// makeJar.close();//closing the object
	}

	/**
	 * This method is used to get the information about the dataobject from the
	 * dataobject treeview to store in the xml file
	 * 
	 * @param dataObjectType
	 *            This is of type DataObjectType which will store all the
	 *            information from the dataobject tree
	 */
	private void saveDataObject(DataObjectType dataObjectType) {
		DataObjectView objView = new DataObjectView();
		objView.getDataObjectInfo(dataObjectType);
	}

	/**
	 * This method is used to get the information abourt the project from the
	 * project treeview to store in the xml file
	 * 
	 * @param projectType
	 *            This of type SolutionsAdapter schema object which will store
	 *            the information from the project tree
	 */
	private void saveProject(SolutionsAdapter projectType) {
		ProjectData objView = new ProjectData();
		objView.getProjectInfo(projectType);
	}

	/**
	 * This method is used to get the information about the datasource from the
	 * datasource treeview to store in the xml file
	 * 
	 * @param dataSourceType
	 *            This is of type DataSourceType schema object which will store
	 *            the information from the datasource tree
	 */
	private void saveDataSource(DataSourceType dataSourceType) {
		DatabaseViewer objView = new DatabaseViewer();
		objView.getDatabaseInfo(dataSourceType);
	}

	/**
	 * This method is used to return the DataSourceType object from the schema
	 * by unmarshalling the xml file
	 * 
	 * @return
	 */
	public DataSourceType getDataSourceType() {
		DataSourceType dataSourceType = null;
		try {
			final JAXBContext jtx = JAXBContext.newInstance(
					SASConstants.SOLUTIONS_ADAPTER_CONTEXT, this.getClass()
							.getClassLoader());
			Unmarshaller unMarshall = jtx.createUnmarshaller();
			SolutionAdapterView viewObj = new SolutionAdapterView();
			String XMLFile = SASConstants.SAS_ROOT
					+ viewObj.getFolderStructure() + SASConstants.BACK_SLASH_s
					+ SASConstants.SOLUTIONS_ADAPTER_XML;
			SolutionsAdapter adapterRoot = (SolutionsAdapter) unMarshall
					.unmarshal(new File(XMLFile));
			dataSourceType = adapterRoot.getDataSource();
		} catch (Exception e) {
			final String errMsg = "Exception thrown in "
					+ "**getDataSourceType()** in class [ SaveAdapter.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
		return dataSourceType;
	}

	/**
	 * This method is used to return the SolutionsAdapter object from the schema
	 * by unmarshalling the xml file
	 * 
	 * @return
	 */
	public SolutionsAdapter getSolutionsAdapterType() {
		SolutionsAdapter adapterRoot = null;
		try {
			final JAXBContext jtx = JAXBContext.newInstance(
					SASConstants.SOLUTIONS_ADAPTER_CONTEXT, this.getClass()
							.getClassLoader());
			Unmarshaller unMarshall = jtx.createUnmarshaller();
			SolutionAdapterView viewObj = new SolutionAdapterView();
			String XMLFile = SASConstants.SAS_ROOT
					+ viewObj.getFolderStructure() + SASConstants.BACK_SLASH_s
					+ SASConstants.SOLUTIONS_ADAPTER_XML;
			adapterRoot = (SolutionsAdapter) unMarshall.unmarshal(new File(
					XMLFile));
		} catch (Exception e) {
			final String errMsg = "Exception thrown in "
					+ "**getSolutionsAdapterType()** in class [ SaveAdapter.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
		return adapterRoot;
	}

	/**
	 * This method is used to open the adapter from an xml file and also
	 * populate the other views get the corresponding schema objects
	 * 
	 * @param adapterObj
	 *            This is of type TreeParent which will hold the adapter node
	 */
	public void openAdapterFromFile(TreeParent adapterObj) {
		String XMLFile = SASConstants.SAS_ROOT + adapterObj.getParent()
				+ SASConstants.BACK_SLASH_s + adapterObj.getName()
				+ SASConstants.BACK_SLASH_s
				+ SASConstants.SOLUTIONS_ADAPTER_XML;
		try {
			final JAXBContext jtx = JAXBContext.newInstance(
					SASConstants.SOLUTIONS_ADAPTER_CONTEXT, this.getClass()
							.getClassLoader());
			Unmarshaller unMarshall = jtx.createUnmarshaller();
			SolutionsAdapter adapterRoot = (SolutionsAdapter) unMarshall
					.unmarshal(new File(XMLFile));
			AdapterType adapterType = adapterRoot.getAdapter();
			adapterObj.setServerName(adapterType.getCmsServer());
			DataSourceType dataSourceType = adapterRoot.getDataSource();
			DatabaseViewer viewObj = new DatabaseViewer();
			viewObj.setDatabaseInfo(dataSourceType);
			DataObjectView dataObj = new DataObjectView();
			dataObj.setDataObjectInfo(adapterRoot);
			ProjectData projObj = new ProjectData();
			projObj.setProjectInfo(adapterRoot, adapterType.getCmsServer(),
					adapterObj.getParent().getName()
							+ SASConstants.BACK_SLASH_s + adapterObj.getName());
		} catch (Exception e) {
			final String errMsg = "Exception thrown in "
					+ "**openAdapterFromFile()** in class [ SaveAdapter.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

}
