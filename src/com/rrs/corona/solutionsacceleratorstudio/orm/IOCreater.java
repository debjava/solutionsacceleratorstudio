/*******************************************************************************
 * @rrs_start_copyright
 * 
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved. This
 * software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Red Rabbit Software. ©
 * 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights
 * reserved. Red Rabbit Software - Development Program 5 of 15 $Id: , redrabbit
 * Exp$ $Source:
 * /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/orm/IOCreater.java,v $
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

package com.rrs.corona.solutionsacceleratorstudio.orm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.rrs.corona.CoronaMapping.ClassFieldListType;
import com.rrs.corona.CoronaMapping.ClassObjectList;
import com.rrs.corona.CoronaMapping.CoronaMapping;
import com.rrs.corona.CoronaMapping.FieldType;
import com.rrs.corona.CoronaMapping.MetricType;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;
import com.rrs.corona.solutionsacceleratorstudio.sasutil.CoronaMappingParser;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.GetAtomicData;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SAReader;

/**
 * @author Debadatta Mishra
 * 
 */
public class IOCreater
{
	/**
	 * PrintWriter object
	 */
	private transient PrintWriter	printWriter			= null;
	/**
	 * Package name of the Intermediate source file
	 */
	private transient String		packageName			= SASConstants.IOPACKAGE_NAME;
	/**
	 * Path of the Composite Event of the project
	 */
	private transient String		comPath				= SASConstants.COMPOSITE_PATH_NAME;
	/**
	 * Variable to type int to track the occurance of if statements
	 */
	public int						ifCount				= 1;
	public static final String		SIMPLENAME_s		= "simple";
	public static final String		GROUPNAME_s			= "group";
	public static final String		ATOMICNAME_s		= "atomic";
	public static final String		PROJECTNAME_s		= "project";
	/**
	 * File name of the Intermediate Source file
	 */
	private transient String		IOSourceFileName	= SASConstants.IOSOURCEFILE_NAME;

	private transient String		subClassPackageName	= "com.rrs.corona.hidden.";
	/**
	 * Directory name of the Intermediate source file
	 */
	private transient String		IOSrcDirName		= SASConstants.IOSRCDIR_NAME;
	/**
	 * Directory name of the projects
	 */
	private transient String		projectDirName		= SASConstants.PROJECTDIR_NAME;
	/**
	 * Object of type CoronaMapping parser to parse the Corona Mapping file
	 */
	CoronaMappingParser				parser				= new CoronaMappingParser( );
	/**
	 * A HashMap containing the Composite Event GUID as key and atomic metric
	 * data as value
	 */
	public static HashMap			writerMap			= new HashMap( );
	protected Logger				logger				= Logger
																.getLogger( "IOCreater.class" );
	
	private transient ArrayList allClassNamesList = new ArrayList();

	/**
	 * Default Constructor
	 * 
	 */
	public IOCreater( )
	{

	}

	/**
	 * Method to obtain an Printwriter object
	 * 
	 * @param projPath
	 *            path of the IO Source file
	 * @return Object of type PrintWriter
	 */
	private PrintWriter getPrintWriter( final String projPath )
	{// Method used to obtain an object of type PrintWriter
		try
		{
			printWriter = new PrintWriter( new FileOutputStream( projPath
					+ SASConstants.BACK_SLASH_s + IOSrcDirName
					+ SASConstants.BACK_SLASH_s + IOSourceFileName ) );
		}
		catch( Exception e )
		{
			final String errMsg = "Exception occured in Method "
					+ ":::getPrintWriter() in class :::IOCreater.java:::";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			logger.info( errMsg );
			e.printStackTrace( );
		}
		return printWriter;
	}

	/**
	 * Method used to obtain the list of files in a directory
	 * 
	 * @param projPath
	 *            path of the project
	 * @return an array of files
	 */
	public File[] getFile( final String projPath )
	{// This method is used to obtain a list of files from a directory
		final File file = new File( projPath + SASConstants.BACK_SLASH_s
				+ comPath );
		return file.listFiles( );
	}

	/**
	 * This method is used to create the header for a generated java file It
	 * will create the package name and the required import statements for the
	 * required classes.
	 * 
	 * @param fieldInfoMap
	 *            of type HashMap containg the classes name as key
	 * @param filePath
	 *            of type String specifying the path of the generated file
	 */
	private void writeTop( final HashMap fieldInfoMap, final String filePath , final String fileName )
	{// This method is used to generate the header of a class files
		// ArrayList classNameList = new
		// CoronaMappingParser().getClassNameList();
		String tmpPkg = fileName.replace('_','.');
		System.out.println("Here tmpPKG ==>>> "+tmpPkg);
		packageName = packageName+"."+tmpPkg;
		
		
		printWriter.write( "/* This is an auto generated source file,*/" );
		printWriter.write( "\n" );
		printWriter.write( "/* please do not modify it. */" );
		printWriter.write( "\n" );
		printWriter.write( "package " + packageName + ";\n" );
		printWriter.write( "\n" );

		final Set set = fieldInfoMap.entrySet( );
		final Iterator itr = set.iterator( );
		while( itr.hasNext( ) )
		{// It will import the required the java classes
			final Map.Entry me = ( Map.Entry ) itr.next( );
			final String className = ( String ) me.getKey( );
			printWriter.write( "import " + subClassPackageName+tmpPkg+"."+ className
					+ ";\n" );
		}
		// printWriter.write( "import
		// com.rrs.corona.metricevent.CompositeEvent.* ;\n" );
		printWriter.write( "import com.rrs.corona.metricevent.* ;\n" );
		printWriter.write( "import java.util.*;\n" );
		printWriter.write( "\n" );
		printWriter.write( "public class DataFromCompositeEvent\n" );
		printWriter.write( "{\n" );
		printWriter
				.write( "	public ArrayList GetDataFromCE(CompositeEvent ceObject)\n" );// corection
		// here
		printWriter.write( "	{\n" );
		printWriter.write( "\t\t\tArrayList list = new ArrayList();\n" );
		// initializeObjects( fieldInfoMap );// Method to initialize the Objects
		// in the Class
	}

	/**
	 * This method is used to initialize the required classes
	 * 
	 * @param fieldInfoMap
	 *            of type HashMap containg the class names as key
	 */
	private void initializeObjects( final HashMap fieldInfoMap )
	{// This method is used to initialize all the auto generated java class
		// files
		final Set set = fieldInfoMap.entrySet( );
		final Iterator itr = set.iterator( );
		while( itr.hasNext( ) )
		{// Here it is used to initialize the required classes
			final Map.Entry me = ( Map.Entry ) itr.next( );
			final String objectName = ( String ) me.getKey( );
			printWriter.write( "\n" );
			printWriter.write( "\t\t\t" + objectName + "  "
					+ objectName.toLowerCase( ) + "_" + " " + "=" + " " + "new"
					+ " " + objectName + "()" + " " + ";" );
			printWriter.write( "\n" );
		}
		printWriter.write( "\n" );
	}

	/**
	 * Method used to write the last of a class file
	 */
	private void writeLast( final HashMap fieldInfoMap )
	{// This method is used to write the footer of the class file
		// addToList( fieldInfoMap );//Modified on 15.06.06
		printWriter.write( "\n" );
		printWriter.write( "\t\t\t return list ; \n" );
		printWriter.write( "	}\n" );
		printWriter.write( "\n" );
		printWriter.write( "}\n" );
		printWriter.flush( );
		printWriter.close( );
	}

	/**
	 * This method is used to set the atomic metric values in the intermediate
	 * java object
	 * 
	 * @param fieldInfoMap
	 *            of type HashMap containg the class names as key and field
	 *            information as values
	 * @param printWriter
	 *            of type Printwriter object
	 * @param filePath
	 *            of type String specifying the path of the auto generated file
	 */
	private synchronized void setAtomicMetricValues(
			final HashMap fieldInfoMap, final PrintWriter printWriter,
			final String filePath )
	{// This method is the most significant method,
		// it fetches the required atomic metric data and write to the
		// auto generated java source file
		String className = null;
		String fieldName = null;
		String fieldTypeName = null;
		String projectName = null;
		String ceGUID = null;
		HashMap hashMap = new HashMap( );
		try
		{
			final CoronaMapping crnMapping = parser.getCoronaMapping( );
			final List clsObjectList = crnMapping.getClassObject( );// list of
			// Classes
			for( int i = 0; i < clsObjectList.size( ); i++ )
			{
				final ClassObjectList classObjList = ( ClassObjectList ) clsObjectList
						.get( i );
				final String mainClassName = classObjList.getClassName( );
				final ClassFieldListType fieldType = classObjList
						.getClassFieldList( );
				final List clsFieldList = fieldType.getField( );// list of

				clsField_Loop: for( int j = 0; j < clsFieldList.size( ); j++ )
				{
					final FieldType typeField = ( FieldType ) clsFieldList
							.get( j );
					fieldName = typeField.getFieldName( );
					fieldTypeName = typeField.getFieldType( );
					projectName = typeField.getProjectName( );

					final List metricList = typeField.getMetricList( );
					// The following if statement is for autogeneration of
					// primary key.
					if( projectName == null || metricList == null )
					{
						continue clsField_Loop;
					}

					final MetricType metric = ( MetricType ) metricList.get( 0 );
					ceGUID = metric.getMetricName( );
					StringBuilder builder = new StringBuilder( );

					for( int k = 0; k < metricList.size( ); k++ )
					{
						final MetricType typeMetric = ( MetricType ) metricList
								.get( k );
						final String metricTypeName = typeMetric
								.getMetricType( );
						if( metricTypeName.equalsIgnoreCase( "SE" ) )
						{
							hashMap.put( SIMPLENAME_s, typeMetric
									.getMetricName( ) );
						}
						else if( metricTypeName.equalsIgnoreCase( "GM" ) )
						{
							hashMap.put( GROUPNAME_s, typeMetric
									.getMetricName( ) );
						}
						else if( metricTypeName.equalsIgnoreCase( "AM" ) )
						{
							hashMap.put( ATOMICNAME_s, typeMetric
									.getMetricName( ) );
						}
					}
					hashMap.put( PROJECTNAME_s, filePath
							+ SASConstants.BACK_SLASH_s + projectDirName
							+ SASConstants.BACK_SLASH_s + projectName );
					final GetAtomicData atomicData = new GetAtomicData( );
					atomicData.getData( ceGUID, hashMap, fieldTypeName );// This
					// method
					// ontains
					// the
					// values
					// from
					// the
					// atomic
					// metric
					// data
					final String passedString = atomicData
							.getAtomicValueString( );

					// provide class Name and field Name and get mapped table
					// name
					className = new SAReader( )
							.getMappedTableName( mainClassName, fieldName );
					// for temporary enhancement 26.06.06
					className = className + "SAS";
					// for temporary enhancement 26.06.06

					if( writerMap.containsKey( ceGUID ) )
					{
						StringBuilder tmpBuilder = ( StringBuilder ) writerMap
								.get( ceGUID );

						tmpBuilder.append( "\t\t\t" + className.toLowerCase( )
								+ "_" + ".set" + fieldName + "(  "
								+ passedString );
						tmpBuilder.append( "\n" );

						// writerMap.put( ceGUID, builder );
					}
					else
					// if( writerMap.containsKey( ceGUID ) )
					{

						StringBuilder newBuilder = new StringBuilder( );
						newBuilder.append( "\t\t\t" + className.toLowerCase( )
								+ "_" + ".set" + fieldName + "(  "
								+ passedString );

						newBuilder.append( "\n" );

						writerMap.put( ceGUID, newBuilder );
					}
				}
			}
			writeData( writerMap, className, fieldInfoMap );
			writerMap.clear( );
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in method "
					+ ":::setAtomicMetricValues() in class :::IOCreater.java:::";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			logger.info( errMsg );
			e.printStackTrace( );
		}
	}

	/**
	 * This method is used to write the atomic metric data to the intermediate
	 * object file
	 * 
	 * @param writerMap
	 *            of type HashMap containing the CompositeEvent GUID as key and
	 *            Atomic metric data as value
	 * @param className
	 *            of type String which specifies the name of the mapped class
	 * 
	 */
	private void writeData( final HashMap writerMap, final String className,
			HashMap fieldInfoMap )
	{// This method is used to write the atomic
		// metric data to the intermediate object
		
		final Set set = writerMap.entrySet( );
		final Iterator iter = set.iterator( );
		while( iter.hasNext( ) )
		{
			final Map.Entry me = ( Map.Entry ) iter.next( );
			printWriter.write( "\t	if (ceObject.getGUID().equals(" + "\""
					+ me.getKey( ) + "\"" + "))\n" );
			printWriter.write( "\t	{\n" );
			printWriter.write( "\n" );
			// initializeObjects(fieldInfoMap);
			printWriter.write( "\n" );
			//System.out.println("me.getValue() :::* "+me.getValue());
			StringBuilder strBuilder = (StringBuilder)me.getValue();
			
			
			System.out.println("strBuilder :::* "+strBuilder);
			final String valueString = strBuilder.toString();
			
			final String classValue = valueString.substring(0,valueString.indexOf(".")).replaceAll("\t","");
			//classValue = classValue.replaceAll("\t","");
			//System.out.println("Actual Instance Value ::: "+classValue);
			printWriter.write( "\t\t" + me.getValue( ) );
			printWriter.write( "\n" );
			final String instanceValue = classValue.trim();
			HashSet classSet = new HashSet();

			String allEntries[] = strBuilder.toString().split("\n");
			for( int i = 0 ; i < allEntries.length ; i++)
			{
				System.out.println("*** Here   -->"+ allEntries[i]);
				final String classValue1 = allEntries[i].substring(0,allEntries[i].indexOf(".")).replaceAll("\t","");
				classSet.add(classValue1.trim());
				
//				printWriter.write("\n");
//				printWriter.write("\t\t\tlist.add( " + classValue1.trim() + " );"  );
//				printWriter.write("\n");
			}
			Iterator itr = classSet.iterator();
			while(itr.hasNext())
			{
				printWriter.write("\n");
				printWriter.write("\t\t\tlist.add( " + itr.next() + " );"  );
				printWriter.write("\n");
			}
			
			
//			printWriter.write( "\t\t\tlist.add( " + className.toLowerCase( )
//					+ "_" + " );" );
			//printWriter.write("\t\t\tlist.add( " + instanceValue + " );"  );
			printWriter.write( "\n" );

			printWriter.write( "\t	}\n" );// for testing
		}
		printWriter.write( "\n" );
		// addToList( fieldInfoMap );//Modified on 15.06.06
		// printWriter.write( "\t }*\n" );

	}
	
	
	/**
	 * This method is used to add the class names to an arraylist
	 * 
	 * @param fieldInfoMap
	 *            of type HashMap containing the class names as key
	 */
	private void addToList( final HashMap fieldInfoMap )
	{// This method is used to add the class names to an arraylist
		final Set set = fieldInfoMap.entrySet( );
		final Iterator itr = set.iterator( );
		while( itr.hasNext( ) )
		{// Here all the classes are added to an arraylist
			final Map.Entry me = ( Map.Entry ) itr.next( );
			final String objectName = ( String ) me.getKey( );
			printWriter.write( "\n" );
			printWriter.write( "\t\t\tlist.add( " + objectName.toLowerCase( )
					+ "_" + " );" );
			printWriter.write( "\n" );
		}
	}

	/**
	 * This method is used to generate the Intermediate Object
	 * 
	 * @param fieldInfoMap
	 *            of type HashMap containg the class names as key and field
	 *            information as values.
	 * @param filePath
	 *            of type String specifying the path of the auto generated java
	 *            files.
	 */
	public void generateIO( final HashMap fieldInfoMap, final String filePath,final String fileName )
	{// This is the main method which is used to generate the Intermediate
		// object file
		try
		{
			printWriter = getPrintWriter( filePath );
			writeTop( fieldInfoMap, filePath , fileName);// Method to write the header of
												// the class
			initializeObjects( fieldInfoMap );
			setAtomicMetricValues( fieldInfoMap, printWriter, filePath );// Method
																			// to
																			// set
																			// the
																			// atomic
																			// metric
																			// values.
			writeLast( fieldInfoMap );// Method to write the last part of the
										// class,ie to close the braces etc.
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in Method "
					+ ":::generateIO()::: in class :::IOCreater.java:::";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			logger.info( errMsg );
			e.printStackTrace( );
		}
	}

}
