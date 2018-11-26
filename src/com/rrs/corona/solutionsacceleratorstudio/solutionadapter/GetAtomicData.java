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
 *$Id: GetAtomicData.java,v 1.2 2006/07/28 13:26:16 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/GetAtomicData.java,v $
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import com.rrs.corona.metricevent.CompositeEvent;
import com.rrs.corona.metricevent.AtomicMetric;
import com.rrs.corona.metricevent.GroupMetric;
import com.rrs.corona.metricevent.SimpleEvent;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.orm.IOCreater;
import com.rrs.corona.solutionsacceleratorstudio.orm.IOGenerator;

/**
 * @author Debadatta Mishra
 * 
 */
public class GetAtomicData {
	/**
	 * Name of the Simple Event
	 */
	private String simple_Name = null;

	/**
	 * Name of the Group Metric
	 */
	private String group_Name = null;

	/**
	 * Name of the Atomic Metric
	 */
	private String atomic_Name = null;

	/**
	 * Name of the project
	 */
	private String project_Name = null;

	/**
	 * String variable to store atomic metric data
	 */
	private StringBuilder stringData = new StringBuilder();

	/**
	 * JaxbContext for Composite event
	 */
	String CompositeJaxbContext = SASConstants.COMPOSITEJAXB_CONTEXT;

	/**
	 * String variable to hold the data from atomic metric
	 */
	String atomicValueString = new String();

	/**
	 * This method is used to get the atomic metric data from a particular
	 * Composite Event This method calls other methods to get Simple Event,
	 * Group Metric and atomic metric
	 * 
	 * @param compGUID
	 *            GUID of the Composite Event
	 * @param allInfo
	 *            a HashMap containing information of simple event name,group
	 *            metric name atomic metric name and the related project name
	 * @param fieldTypeName
	 *            Type of the object field
	 * @throws Exception
	 */
	public void getData(final String compGUID, final HashMap allInfo,
			final String fieldTypeName) throws Exception {// Method to get
															// Composite Event
															// and it calls
															// simple event
		this.simple_Name = (String) allInfo.get(IOCreater.SIMPLENAME_s);
		this.group_Name = (String) allInfo.get(IOCreater.GROUPNAME_s);
		this.atomic_Name = (String) allInfo.get(IOCreater.ATOMICNAME_s);
		this.project_Name = (String) allInfo.get(IOCreater.PROJECTNAME_s);
		final JAXBContext jaxbContext = JAXBContext
				.newInstance(CompositeJaxbContext);
		final Unmarshaller unMarshall = jaxbContext.createUnmarshaller();
		File[] compFiles = new IOCreater().getFile(this.project_Name);
		loop1: for (int i = 0; i < compFiles.length; i++) {
			CompositeEvent compositeEvent = (CompositeEvent) unMarshall
					.unmarshal(new FileInputStream(compFiles[i]));
			if (compGUID.equals(compositeEvent.getGUID())) {
				List simpleList = compositeEvent.getSimpleEvent();
				int pos = 0;
				for (Iterator simpleIter = simpleList.iterator(); simpleIter
						.hasNext(); pos++) {
					SimpleEvent simpleEvent = (SimpleEvent) simpleIter.next();
					boolean result = getSimpleEvent(simpleEvent, pos,
							fieldTypeName);// Method to get Simple Event
					if (result) {
						break loop1;
					}
				}
			}
		}
	}

	/**
	 * Method used to get the Composite Event
	 * 
	 * @param compositeEvent
	 *            Object of type Composite Event
	 * @param pos
	 *            position of the Composite Event
	 * @param compGUID
	 *            GUID of the Composite Event
	 * @param fieldTypeName
	 *            Type of the Object field
	 * @return true if GUID matches a particular Composite event GUID
	 */
	private boolean getCompoisteEvent(final CompositeEvent compositeEvent,
			final int pos, final String compGUID, final String fieldTypeName) {// This
																				// method
																				// is
																				// used
																				// to
																				// get
																				// Composite
																				// Event
																				// by
																				// passing
																				// the
																				// GUID
																				// defined
																				// in
																				// the
		// CoronaMapping.xml file
		if (compGUID.equals(compositeEvent.getGUID())) {
			stringData.insert(0, "((CompositeEvent)");
			stringData.append("ceObject.getCompoisteEvent().get(");
			stringData.append(pos);
			stringData.append("))).");
			List simpleList = compositeEvent.getSimpleEvent();
			int i = 0;
			for (Iterator simpleIter = simpleList.iterator(); simpleIter
					.hasNext(); i++) {
				SimpleEvent simpleEvent = (SimpleEvent) simpleIter.next();
				boolean result = getSimpleEvent(simpleEvent, i, fieldTypeName);// Method
																				// to
																				// get
																				// SimpleEvent
				if (result) {
					return result;
				}
			}
			List compList = compositeEvent.getCompositeEvent();
			int j = 0;
			for (Iterator comIter = compList.iterator(); comIter.hasNext(); j++) {
				CompositeEvent subComp = (CompositeEvent) comIter.next();
				boolean result = getCompoisteEvent(subComp, j, compGUID,
						fieldTypeName);// Method to get Composite Event
			}
			return true;
		}
		return false;
	}

	/**
	 * Method used to get Simple Event Object
	 * 
	 * @param simpleEvent
	 *            Object of type SimpleEvent
	 * @param pos
	 *            position of the Simple Event
	 * @param fieldTypeName
	 *            Type of the Object field
	 * @return true if Simple event is found
	 */
	private boolean getSimpleEvent(final SimpleEvent simpleEvent,
			final int pos, final String fieldTypeName) {// This method is used
														// to find a Simple
														// Event object and its
														// position
		if (this.simple_Name.equals(simpleEvent.getName())) {
			stringData.insert(0, "((SimpleEvent)");
			stringData.append("ceObject.getSimpleEvent().get(");
			stringData.append(pos);
			stringData.append(")).");
			List groupList = simpleEvent.getGroupMetric();
			int i = 0;
			for (Iterator groupIter = groupList.iterator(); groupIter.hasNext(); i++) {
				GroupMetric groupMetric = (GroupMetric) groupIter.next();
				boolean result = getGroupMetric(groupMetric, i, fieldTypeName);// Method
																				// to
																				// get
																				// GroupMetric
				if (result) {
					return result;
				}
			}
		}
		return false;
	}

	/**
	 * Method used to obtain an Object of type GroupMetric
	 * 
	 * @param groupMetric
	 *            Object of type GroupMetric
	 * @param pos
	 *            position of the GroupMetric
	 * @param fieldTypeName
	 *            Type of the Object field
	 * @return true if GroupMetric is found
	 */
	private boolean getGroupMetric(final GroupMetric groupMetric,
			final int pos, final String fieldTypeName) {// This method is used
														// to find the Group
														// metric and its
														// position
		if (this.group_Name.equals(groupMetric.getName())) {
			stringData.insert(0, "((GroupMetric)");
			stringData.append("getGroupMetric().get(");
			stringData.append(pos);
			stringData.append(")) .");
			List atomicList = groupMetric.getAtomicMetric();
			int i = 0;
			for (Iterator atomicIter = atomicList.iterator(); atomicIter
					.hasNext(); i++) {
				AtomicMetric atomicMetric = (AtomicMetric) atomicIter.next();
				boolean result = getAtomicMetric(atomicMetric, i, fieldTypeName);// Method
																					// to
																					// get
																					// Atomic
																					// Metric
				if (result) {
					return true;
				}
			}
			List groupList = groupMetric.getGroupMetric();
			int j = 0;
			for (Iterator groupIter = groupList.iterator(); groupIter.hasNext(); j++) {
				GroupMetric subMetric = (GroupMetric) groupIter.next();
				boolean result = getGroupMetric(subMetric, j, fieldTypeName);// Method
																				// to
																				// get
																				// Group
																				// Metric
				if (result) {
					return result;
				}
			}
		}
		return false;
	}

	/**
	 * Method used to get an Object of type Atomic Metric
	 * 
	 * @param atomicMetric
	 *            Object of type Atomic Metric
	 * @param pos
	 *            position of the Atomic Metric
	 * @param fieldTypeName
	 *            Type of the Object Field
	 * @return true if Atomic Metric is found
	 */
	private boolean getAtomicMetric(final AtomicMetric atomicMetric,
			final int pos, final String fieldTypeName) {// This method is used
														// to find the Aomic
														// metric data and its
														// position
		if (this.atomic_Name.equals(atomicMetric.getName())) {
			stringData.insert(0, "((AtomicMetric)");
			stringData.append("getAtomicMetric().get(");
			stringData.append(pos);
			stringData.append(")).getData() ");
			String testString = stringData.toString();
			setAtomicValue(testString, fieldTypeName);// Method used to write
														// to the file
			return true;
		}
		return false;
	}

	/**
	 * Method used to set the atomic metric data to the string for generating
	 * Intermediate Object
	 * 
	 * @param stringValue
	 *            String variable to store atomic metric data
	 * @param fieldTypeName
	 *            Type of the Object Field
	 */
	public void setAtomicValue(final String stringValue,
			final String fieldTypeName) {// This method is used to set the
											// atomic metric data to the string
											// variable for writing to the
											// intermediate object
		// It nullifies the StringBuilder
		if (stringData != null) {
			stringData.delete(0, stringData.length());
		}
		if (fieldTypeName.equalsIgnoreCase("Double")) {
			stringData.insert(0, "Double.parseDouble( ");
			stringData.append(stringValue + " )");
			stringData.append(" ) ;");
		} else if (fieldTypeName.equalsIgnoreCase("String")) {
			stringData.append(stringValue);
			stringData.append(" );");
		} else if (fieldTypeName.equalsIgnoreCase("Integer")) {
			stringData.insert(0, "Integer.parseInt( ");
			stringData.append(stringValue + " )");
			stringData.append(" ) ;");
		} else if (fieldTypeName.equalsIgnoreCase("Long")) {
			stringData.append("Long.parseLong( ");
			stringData.append(stringValue + " )");
			stringData.append(" ) ;");
		} else if (fieldTypeName.equalsIgnoreCase("float")) {
			stringData.append("Float.parseFloat( ");
			stringData.append(stringValue + " )");
			stringData.append(" ) ;");
		}

		atomicValueString = stringData.toString();
	}

	/**
	 * @return Returns the passedString.
	 */
	public String getAtomicValueString() {
		return atomicValueString;
	}

	/**
	 * @param passedString
	 *            The passedString to set.
	 */
	public void setAtomicValueString(String passedString) {
		this.atomicValueString = passedString;
	}
}
