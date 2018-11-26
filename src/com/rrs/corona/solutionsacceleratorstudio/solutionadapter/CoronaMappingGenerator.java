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
 *$Id: CoronaMappingGenerator.java,v 1.2 2006/07/28 13:26:16 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/CoronaMappingGenerator.java,v $
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
import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import com.rrs.corona.CoronaMapping.ClassFieldListType;
import com.rrs.corona.CoronaMapping.ClassObjectList;
import com.rrs.corona.CoronaMapping.CoronaMapping;
import com.rrs.corona.CoronaMapping.FieldType;
import com.rrs.corona.CoronaMapping.MetricType;
import com.rrs.corona.CoronaMapping.ObjectFactory;
import com.rrs.corona.sasadapter.AMMapList;
import com.rrs.corona.metricevent.AtomicMetric;
import com.rrs.corona.sasadapter.ClassList;
import com.rrs.corona.sasadapter.DataObjectType;
import com.rrs.corona.sasadapter.FieldNameList;
import com.rrs.corona.sasadapter.GroupList;
import com.rrs.corona.sasadapter.SolutionsAdapter;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

/**
 * @author Debadatta Mishra
 * 
 */

public class CoronaMappingGenerator {
	/**
	 * File name of the Corona Mapping
	 */
	private transient String coronaMappingFileName = SASConstants.CORONA_MAPPING_FILE_NAME;// coronaMapping

	// file
	// name

	/**
	 * Logger to log the messages
	 */
	protected Logger logger = Logger.getLogger("CoronaMappingGenerator.class");

	/**
	 * JaxbContext for solutionadpter.xml
	 */
	private transient String sasjaxBContext = SASConstants.SOLUTIONS_ADAPTER_CONTEXT; // "com.rrs.corona.SolutionsAdapter";

	/**
	 * JaxbContext for CoronaMapping.xml
	 */
	private transient String coronaMappingJaxbContext = SASConstants.CORONA_MAPPING_JAXBCONTEXT;

	/**
	 * File path of the coronaMapping.xml file
	 */
	private transient String filePath;

	/**
	 * Constructor
	 */
	public CoronaMappingGenerator() {// Initializes the file path of the
		// solutionadapter.xml file
		filePath = SASConstants.SAS_ROOT
				+ new SolutionAdapterView().getFolderStructure()
				+ SASConstants.BACK_SLASH_s;
	}

	/**
	 * Method used to obtain a SolutionsAdapter object
	 * 
	 * @return an object of type SolutionsAdapter
	 */
	public SolutionsAdapter getSA() {// This method provides an object of
		// type SolutionsAdapter
		try {
			File saFile = new File(filePath
					+ SASConstants.SOLUTIONS_ADAPTER_XML);
			if (!saFile.exists()) {
				logger.info("File does not exist");
				// do nothing
			} else {
				JAXBContext jaxbCtx = JAXBContext.newInstance(sasjaxBContext);
				Unmarshaller unmarshal = jaxbCtx.createUnmarshaller();
				SolutionsAdapter solutionAdapter = (SolutionsAdapter) unmarshal
						.unmarshal(new FileInputStream(filePath
								+ SASConstants.SOLUTIONS_ADAPTER_XML));
				return solutionAdapter;
			}
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**getSA()** in class [ CoronaMapppingGenerator.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * Method used to generate the CoronaMapping
	 */
	public void generateCoronaMapping() {
		writeToCoronaMapping();// write to XML file
	}

	/**
	 * Method used to write Project-Class mapping contents to the XML file
	 */
	private void writeToCoronaMapping() {// This method is used to write
		// project-class mapping
		try {
			ObjectFactory objectFactory = new ObjectFactory();
			CoronaMapping coronaMapping = objectFactory.createCoronaMapping();
			generateMapping(objectFactory, coronaMapping);
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**writeToCoronaMapping()** in class [ CoronaMapppingGenerator.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

	// Commented out for temporary use
	// /**
	// * Method used to generate the actual CoronaMapping.xml file
	// * @param objectFactory of type ObjectFactory
	// * @param coronaMapping of type CoronaMapping
	// */
	// public void generateMapping(ObjectFactory objectFactory,CoronaMapping
	// coronaMapping)
	// {//This method is used to generate CoronaMapping.xml file
	// ClassList classesList = null;
	// ClassObjectList clsObjectList = null;
	// try
	// {
	// SolutionsAdapter sAdapter = getSA();//Obtains the SolutionAdapter object
	// DataObjectType dataObject = sAdapter.getDataObject();
	// List listGroup = dataObject.getGroup();
	// for(int i=0;i<listGroup.size();i++)
	// {
	// GroupList groupList = (GroupList) listGroup.get(i) ;//Obtaining the
	// GroupList
	// List classList = groupList.getClasses();
	// ClassFieldListType clsFieldListType = null;
	// for(int j=0;j<classList.size();j++)
	// {
	// classesList = (ClassList)classList.get(j);//Obtaining the ClassList
	// clsObjectList = objectFactory.createClassObjectList();//class object node
	// clsObjectList.setClassName(classesList.getClassName());//setting the
	// fully qualified class name
	// List fieldList = classesList.getFields();
	// clsFieldListType = objectFactory.createClassFieldListType();//class
	// fieldList node
	// for(int k=0;k<fieldList.size();k++)
	// {
	// FieldNameList listField = (FieldNameList)fieldList.get(k);//Obtaining the
	// FieldList
	// FieldType field = null;
	// field = objectFactory.createFieldType();
	// field.setFieldName(listField.getFieldName());//setting class field name
	// field.setFieldType(listField.getFieldType());//setting class field type
	// //clsFieldListType.getField().add(field);//adding to class
	// AMMapList amList =listField.getDOAMMap();
	// AtomicMetric atmType = (AtomicMetric) amList.getAtomicMetric();
	// MetricType metricType = objectFactory.createMetricType();
	// String[] eventsName = (amList.getParentTags()).split("[|]");
	//						
	// metricType.setMetricName(eventsName[1].substring(eventsName[1].indexOf("=")+1,eventsName[1].length()));
	// metricType.setMetricType(eventsName[1].substring(0,eventsName[1].indexOf("=")));
	// MetricType metricType1 = objectFactory.createMetricType();
	// MetricType metricType2 = objectFactory.createMetricType();
	//						
	// metricType2.setMetricName(eventsName[2].substring(eventsName[2].indexOf("=")+1,eventsName[2].length()));
	// metricType2.setMetricType(eventsName[2].substring(0,eventsName[2].indexOf("=")));
	// MetricType metricType3 = objectFactory.createMetricType();
	//						
	// metricType3.setMetricName(eventsName[3].substring(eventsName[3].indexOf("=")+1,eventsName[3].length()));
	// metricType3.setMetricType(eventsName[3].substring(0,eventsName[3].indexOf("=")));
	// MetricType metricType4 = objectFactory.createMetricType();
	// metricType4.setMetricName(atmType.getName());
	// metricType4.setMetricType("AM");
	// field.getMetricList().add(metricType);
	// field.getMetricList().add(metricType2);
	// field.getMetricList().add(metricType3);
	// field.getMetricList().add(metricType4);
	//						
	// field.setProjectName(eventsName[0]);
	//						
	// clsFieldListType.getField().add(field);//adding to class for testing
	// }
	// clsObjectList.setClassFieldList(clsFieldListType);
	// coronaMapping.getClassObject().add(clsObjectList);
	// }
	// }
	// final JAXBContext coronaJaxbContext =
	// JAXBContext.newInstance(coronaMappingJaxbContext);
	// final FileOutputStream fileOutputStream = new
	// FileOutputStream(filePath+coronaMappingFileName);
	// final Marshaller marshaller = coronaJaxbContext.createMarshaller( );
	// marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
	// marshaller.marshal( coronaMapping, fileOutputStream );
	// fileOutputStream.close( );
	// }
	// catch(Exception e)
	// {
	// e.printStackTrace();
	// }
	//		
	// }

	/**
	 * Method used to generate the CoronaMapping ie CoronaMapping.xml file This
	 * method calls some other methods
	 * 
	 * @param objectFactory
	 *            of type ObjectFactory object
	 * @param coronaMapping
	 *            of type CoronaMapping object
	 */
	private void generateMapping(final ObjectFactory objectFactory,
			final CoronaMapping coronaMapping) {// This method is used to
		// generate CoronaMapping.xml
		// file
		ClassList classesList = null;
		ClassObjectList clsObjectList = null;
		try {
			final SolutionsAdapter sAdapter = getSA();// Obtains the
			// SolutionAdapter
			// object
			final DataObjectType dataObject = sAdapter.getDataObject();
			final List listGroup = dataObject.getGroup();
			for (int i = 0; i < listGroup.size(); i++) {
				final GroupList groupList = (GroupList) listGroup.get(i);// Obtaining
				// the
				// GroupList
				final List classList = groupList.getClasses();
				final ClassFieldListType clsFieldListType = null;
				for (int j = 0; j < classList.size(); j++) {
					classesList = (ClassList) classList.get(j);// Obtaining the
					// ClassList
					clsObjectList = getClassObjectList(objectFactory,
							coronaMapping, classesList);
					coronaMapping.getClassObject().add(clsObjectList);
					// for nested objects
					final List nestedClassList = classesList.getNestedClasses();
					if (nestedClassList.size() > 0) {
						ClassList nestedClassesList = null;
						ClassObjectList nestedClsObjectList = null;
						for (int k = 0; k < nestedClassList.size(); k++) {
							nestedClassesList = (ClassList) nestedClassList
									.get(k);
							final String nestedClassName = nestedClassesList
									.getClassName();
							nestedClsObjectList = getClassObjectList(
									objectFactory, coronaMapping,
									nestedClassesList);
							coronaMapping.getClassObject().add(
									nestedClsObjectList);
						}
					} else {
						// do nothing
					}
				}
			}
			final JAXBContext coronaJaxbContext = JAXBContext
					.newInstance(coronaMappingJaxbContext);
			final FileOutputStream fileOutputStream = new FileOutputStream(
					filePath + coronaMappingFileName);
			final Marshaller marshaller = coronaJaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.marshal(coronaMapping, fileOutputStream);
			fileOutputStream.close();
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**generateMapping()** in class [ CoronaMapppingGenerator.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to obtain a ClassObjectList which contains all the
	 * related mapped fields information
	 * 
	 * @param objectFactory
	 *            of type ObjectFactory object
	 * @param coronaMapping
	 *            of type CoronaMapping object
	 * @param classesList
	 *            of type ClassList object
	 * @return an object of type ClassObjectList
	 */
	private ClassObjectList getClassObjectList(
			final ObjectFactory objectFactory,
			final CoronaMapping coronaMapping, final ClassList classesList) {// This
		// method
		// is
		// used
		// to
		// obtain
		// a
		// ClassObject
		// list
		ClassObjectList clsObjectList = null;
		ClassFieldListType clsFieldListType = null;
		try {
			clsObjectList = objectFactory.createClassObjectList();// class
			// object
			// node
			clsObjectList.setClassName(classesList.getClassName());// setting
			// the fully
			// qualified
			// class
			// name
			final List fieldList = classesList.getFields();
			clsFieldListType = objectFactory.createClassFieldListType();// class
			// fieldList
			// node
			for (int k = 0; k < fieldList.size(); k++) {
				final FieldNameList listField = (FieldNameList) fieldList
						.get(k);// Obtaining the FieldList
				FieldType field = null;
				field = objectFactory.createFieldType();
				field.setFieldName(listField.getFieldName());// setting class
				// field name
				field.setFieldType(listField.getFieldType());// setting class
				// field type
				// clsFieldListType.getField().add(field);//adding to class
				final AMMapList amList = listField.getDOAMMap();
				// The following if statement is given for the reason that
				// primary key should not be mapped with the atomic metric
				// it should be auto generated
				if (amList == null) {
					clsFieldListType.getField().add(field);// adding to class
					// for testing
				}
				// The following else condition is for autogeneration of primary
				// key
				else {
					final AtomicMetric atmType = (AtomicMetric) amList
							.getAtomicMetric();
					final MetricType metricType = objectFactory
							.createMetricType();
					String[] eventsName = (amList.getParentTags()).split("[|]");

					metricType.setMetricName(eventsName[1].substring(
							eventsName[1].indexOf("=") + 1, eventsName[1]
									.length()));
					metricType.setMetricType(eventsName[1].substring(0,
							eventsName[1].indexOf("=")));
					MetricType metricType1 = objectFactory.createMetricType();
					MetricType metricType2 = objectFactory.createMetricType();

					metricType2.setMetricName(eventsName[2].substring(
							eventsName[2].indexOf("=") + 1, eventsName[2]
									.length()));
					metricType2.setMetricType(eventsName[2].substring(0,
							eventsName[2].indexOf("=")));
					MetricType metricType3 = objectFactory.createMetricType();

					metricType3.setMetricName(eventsName[3].substring(
							eventsName[3].indexOf("=") + 1, eventsName[3]
									.length()));
					metricType3.setMetricType(eventsName[3].substring(0,
							eventsName[3].indexOf("=")));
					MetricType metricType4 = objectFactory.createMetricType();
					metricType4.setMetricName(atmType.getName());
					metricType4.setMetricType("AM");
					field.getMetricList().add(metricType);
					field.getMetricList().add(metricType2);
					field.getMetricList().add(metricType3);
					field.getMetricList().add(metricType4);

					field.setProjectName(eventsName[0]);

					clsFieldListType.getField().add(field);// adding to class
					// for testing

				}

				// final AtomicMetric atmType = (AtomicMetric)
				// amList.getAtomicMetric();
				// final MetricType metricType =
				// objectFactory.createMetricType();
				// String[] eventsName = (amList.getParentTags()).split("[|]");
				//				
				// metricType.setMetricName(eventsName[1].substring(eventsName[1].indexOf("=")+1,eventsName[1].length()));
				// metricType.setMetricType(eventsName[1].substring(0,eventsName[1].indexOf("=")));
				// MetricType metricType1 = objectFactory.createMetricType();
				// MetricType metricType2 = objectFactory.createMetricType();
				//				
				// metricType2.setMetricName(eventsName[2].substring(eventsName[2].indexOf("=")+1,eventsName[2].length()));
				// metricType2.setMetricType(eventsName[2].substring(0,eventsName[2].indexOf("=")));
				// MetricType metricType3 = objectFactory.createMetricType();
				//				
				// metricType3.setMetricName(eventsName[3].substring(eventsName[3].indexOf("=")+1,eventsName[3].length()));
				// metricType3.setMetricType(eventsName[3].substring(0,eventsName[3].indexOf("=")));
				// MetricType metricType4 = objectFactory.createMetricType();
				// metricType4.setMetricName(atmType.getName());
				// metricType4.setMetricType("AM");
				// field.getMetricList().add(metricType);
				// field.getMetricList().add(metricType2);
				// field.getMetricList().add(metricType3);
				// field.getMetricList().add(metricType4);
				//				
				// field.setProjectName(eventsName[0]);
				//				
				// clsFieldListType.getField().add(field);//adding to class for
				// testing
			}
			clsObjectList.setClassFieldList(clsFieldListType);

		} catch (Exception e) {
			final String errMsg = "Exception thrown from the method "
					+ ":::generateMapingForClass()::: in class :::CoronaMappingGenerator.java:::";

			logger.info(errMsg);
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
		return clsObjectList;

	}

}
