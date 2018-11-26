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
 *$Id: IOGenerator.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/IOGenerator.java,v $
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rrs.corona.CoronaMapping.ClassFieldListType;
import com.rrs.corona.CoronaMapping.ClassObjectList;
import com.rrs.corona.CoronaMapping.CoronaMapping;
import com.rrs.corona.CoronaMapping.FieldType;
import com.rrs.corona.CoronaMapping.MetricType;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;

/**
 * @author Debadatta Mishra
 *
 */
public class IOGenerator
{
	
	/**
	 * PrintWriter object
	 */
	public PrintWriter printWriter = null;
	/**
	 * Package name of the Intermediate source file
	 */
	String packageName =SASConstants.IOPACKAGE_NAME;
	/**
	 * Path of the Composite Event of the project
	 */
	String comPath = SASConstants.COMPOSITE_PATH_NAME;
	/**
	 * Variable to type int to track the occurance of if statements
	 */
	public int ifCount = 1;
	public static final String SIMPLENAME_s = "simple";
	public static final String GROUPNAME_s = "group";
	public static final String ATOMICNAME_s = "atomic";
	public static final String PROJECTNAME_s = "project";
	/**
	 * File name of the Intermediate Source file
	 */
	String IOSourceFileName = SASConstants.IOSOURCEFILE_NAME;
	/**
	 * Directory name of the Intermediate source file
	 */
	String IOSrcDirName = SASConstants.IOSRCDIR_NAME;
	/**
	 * Directory name of the projects
	 */
	String projectDirName = SASConstants.PROJECTDIR_NAME;
	/**
	 * Object of type CoronaMapping parser to parse the Corona Mapping file
	 */
	CoronaMappingParser parser = new CoronaMappingParser();
	/**
	 * A HashMap containing the Composite Event GUID as key and atomic metric data as value
	 */
	HashMap writerMap = new HashMap();
	/**
	 * Default Constructor
	 *
	 */
	public IOGenerator()
	{
		
	}
	
	/**
	 * Method to obtain an Printwriter object
	 * @param projPath path of the IO Source file
	 * @return Object of type PrintWriter
	 */
	public PrintWriter getPrintWriter(String projPath)
	{//Method used to obtain an object of type PrintWriter
		try
		{
			printWriter = new PrintWriter(new FileOutputStream(projPath+SASConstants.BACK_SLASH_s+IOSrcDirName+SASConstants.BACK_SLASH_s+IOSourceFileName));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return printWriter;
	}
	
	/**
	 * Method used to obtain the list of files in a directory
	 * @param projPath path of the project
	 * @return an array of files
	 */
	public File[] getFile(String projPath) 
	{//This method is used to obtain a list of files from a directory
		final File file = new File(projPath+SASConstants.BACK_SLASH_s+comPath);
		return file.listFiles();
	}
	/**
	 * Method used to write header of a Class file
	 * @param filePath path of the IO source file
	 */
	private void writeTop(String filePath) // Header of the Intermediate Object which contains package and import statement
	{//This method is used to generate the header of a class files
		ArrayList classNameList = new CoronaMappingParser().getClassNameList();
		printWriter.write("/* This is an auto generated source file,*/");
		printWriter.write("\n");
		printWriter.write("/* please do not modify it. */");
		printWriter.write("\n");
		printWriter.write("package "+packageName+";\n");
		printWriter.write("\n");
		
		for(int i=0; i<classNameList.size();i++)
		{
			printWriter.write("import "+(String)classNameList.get(i)+";\n");
		}
		printWriter.write("import com.rrs.corona.metricevent.CompositeEvent.* ;\n");
		printWriter.write("import java.util.*;\n");
		printWriter.write("\n");
		printWriter.write("public class DataFromCompositeEvent\n");
		printWriter.write("{\n");
		printWriter.write("	public ArrayList GetDataFromCE(CompositeEvents ceObject)\n");// corection here
		printWriter.write("	{\n");
		printWriter.write("\t\t\tArrayList list = new ArrayList();\n");
		initializeObjects(classNameList,filePath);//Method to initialize the Objects in the Class 
	}
	
	/**
	 * Method used to initialize all the required objects in the IO source file
	 * @param classNameList ArrayList containing the list of classNames
	 * @param filePath file of the IO
	 */
	public void initializeObjects(ArrayList classNameList,String filePath)
	{//This method is used to initialize all the auto generated java class files
		for(int i=0;i<classNameList.size();i++)
		{
			String tempString = (String)classNameList.get(i);
			String objectName = tempString.substring(tempString.lastIndexOf(".")+1,tempString.length());
			printWriter.write("\n");
			printWriter.write("\t\t\t"+objectName+"  "+objectName.toLowerCase()+"_"+" "+"="+" "+"new"+" "+objectName+"()"+" "+";");
			printWriter.write("\n");
		}
		printWriter.write("\n");
	}
	
	/**
	 * Method used to write the last of a class file
	 */
	public void writeLast() 
	{//This method is used to write the footer of the class file
		printWriter.write("\n");
		printWriter.write("\t\t\t return list ; \n");
		printWriter.write("	}\n");
		printWriter.write("\n");
		printWriter.write("}\n");
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * Method used to generate the Intermediate java source file
	 * @param filePath file path of the IO Source file
	 */
	public void generateIO(String filePath)
	{//This is the main method which is used to generate the Intermediate object file
		try
		{
			printWriter = getPrintWriter(filePath);
			writeTop(filePath);
			setAtomicMetricValues(printWriter,filePath);
			writeLast();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Method used to set the atomic metric data in the auto generated java source file
	 * @param printWriter Object of type PrintWriter
	 * @param filePath file path of the IO Source file
	 */
	public void setAtomicMetricValues(PrintWriter printWriter,String filePath)
	{//This method is the most significant method,
		//it fetches the required atomic metric data and write to the 
		//auto generated java source file
		String className = null;
		String fieldName = null;
		String fieldTypeName = null;
		String projectName = null;
		String ceGUID = null;
		HashMap hashMap = new HashMap();
		try
		{
			CoronaMapping crnMapping = parser.getCoronaMapping();
			List clsObjectList = crnMapping.getClassObject();//list of Classes
			for(int i=0 ;i<clsObjectList.size();i++)
			{
				ClassObjectList classObjList = (ClassObjectList)clsObjectList.get(i);
				String tempStr = classObjList.getClassName();
				className = tempStr.substring(tempStr.lastIndexOf(".")+1);
				ClassFieldListType fieldType = classObjList.getClassFieldList();
				List clsFieldList = fieldType.getField();//list of fields
				for(int j=0;j<clsFieldList.size();j++)
				{
					FieldType typeField = (FieldType) clsFieldList.get(j);
					fieldName = typeField.getFieldName();
					fieldTypeName = typeField.getFieldType();
					projectName = typeField.getProjectName();
					List metricList = typeField.getMetricList();
					MetricType metric = (MetricType)metricList.get(0);
					ceGUID = metric.getMetricName();
					
					StringBuilder builder = new StringBuilder();
					
					for(int k=0;k<metricList.size();k++)
					{
						MetricType typeMetric = (MetricType)metricList.get(k);
						String metricTypeName = typeMetric.getMetricType();
						if(metricTypeName.equalsIgnoreCase("SE"))
						{
							hashMap.put(SIMPLENAME_s,typeMetric.getMetricName());
						}
						else if(metricTypeName.equalsIgnoreCase("GM"))
						{
							hashMap.put(GROUPNAME_s,typeMetric.getMetricName());
						}
						else if(metricTypeName.equalsIgnoreCase("AM"))
						{
							hashMap.put(ATOMICNAME_s,typeMetric.getMetricName());
						}
					}
					hashMap.put(PROJECTNAME_s,filePath+SASConstants.BACK_SLASH_s+projectDirName+SASConstants.BACK_SLASH_s+projectName);
					GetAtomicData atomicData = new GetAtomicData();
					atomicData.getData(ceGUID,hashMap,fieldTypeName);//This method ontains the values from the atomic metric data
					String passedString = atomicData.getAtomicValueString();
					
					if(!writerMap.containsKey(ceGUID))
					{
						builder.append("\t"+className.toLowerCase()+"_"+".set"+fieldName+"(  "+passedString);
						builder.append("\n");
						writerMap.put(ceGUID,builder);
					}
					else if(writerMap.containsKey(ceGUID))
					{
						StringBuilder newBuilder = (StringBuilder)writerMap.get(ceGUID);
						newBuilder.append("\t\t\t"+className.toLowerCase()+"_"+".set"+fieldName+"(  "+passedString);
						newBuilder.append("\n");
						writerMap.put(ceGUID,newBuilder);
					}
				}
				writeData(writerMap,className);
				writerMap.clear();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to write the atomic metric data to the intermediate object file
	 * @param writerMap of type HashMap containing the CompositeEvent GUID as key
	 * and Atomic metric data as value
	 * @param className of type String which specifies the name of the mapped class
	 */
	public void writeData(HashMap writerMap,String className)
	{//This method is used to write the atomic 
		//metric data to the intermediate object 
		Set set = writerMap.entrySet();
		Iterator iter = set.iterator();
		while(iter.hasNext())
		{
			Map.Entry me = (Map.Entry)iter.next(); 
			printWriter.write("\t	if (ceObject.getGUID().equals(" + "\"" + me.getKey() + "\"" + "))\n");
			printWriter.write("\t	{\n");
			printWriter.write("\t\t"+me.getValue());
			printWriter.write("\n");
		}
		printWriter.write("\t\t\tlist.add( "+className.toLowerCase()+"_"+" );");
		printWriter.write("\n");
		printWriter.write("\t	}\n");
	}
	
}
