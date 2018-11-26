/*******************************************************************************
 * @rrs_start_copyright
 * 
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved. This
 * software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Red Rabbit Software.
 * 
 * 
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

package com.rrs.corona.solutionsacceleratorstudio.license;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.rrs.corona.License.License;
import com.rrs.corona.License.ObjectFactory;
import com.rrs.corona.License.TimeType;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;


/**
 * This class write and read into License.xml information such as the user name,
 * number of time SAS is used and total usage time of SAS.
 * 
 * @author Debadatta Mishra
 * @version 1.0
 * @since 1.0
 * 
 */

public class LicenseFileIO {
	/**
	 * instance is an Static object of LicensFileIo
	 */
	private static LicenseFileIO instance = null;

	/**
	 * license is an Jaxb static object, which hold the information such as User
	 * name, User Accepted the License Aggrement or not, Total number of time
	 * the user used the SAS and Total Usage time.
	 */
	public static License license = null;

	/**
	 * Method used to obtain an instance of LicenseFileIO object
	 * 
	 * @return an object of type LicenseFileIO
	 */
	public static LicenseFileIO getInstance() {// This method is used to obtain an instance of the object LicenseFileIO
		if (instance == null) {
			instance = new LicenseFileIO();
		}
		return instance;
	}

	/**
	 * This calss read the content of the License.xml file and set in the
	 * license static object. Then this static object can be used, when we are
	 * checking that user is accepted the license or not, to calculate total
	 * time of usage and number of time used informations in other part of
	 * program.
	 * 
	 * @param licenseFile
	 *            File path of License.xml file
	 */
	public void read(File licenseFile) {// This method is used to read the license.xml file
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance("com.rrs.corona.License");
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			license = (License) unMarshaller.unmarshal(new FileInputStream(
					licenseFile));
		} catch (Exception ex) {
			final String errMsg = "Exception thrown in Method **read()** in class [ LicenseFileIO.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, ex);
			ex.printStackTrace();
		}
	}

	/**
	 * This method recievs the File path of License.xml file. This method
	 * updates the License.xml file by a) creating an new Time tag enclosing the
	 * start and end time of this session. b) updating TotalUsage tag with value
	 * obtained by addition of previous value present in the TotalUsage tag,
	 * with total usage time of this session. *
	 * 
	 * @param licenseFile
	 *            File path of License.xml file.
	 */
	public void save(File licenseFile) {// This method is used to store the required information in the
		// license.xml file
		try {
			ObjectFactory objFact = new ObjectFactory();
			TimeType timeType = objFact.createTimeType();

			if (license.getAccepted().equals(SASConstants.TRUE)) {
				Long startUp = new Long(
						SolutionsacceleratorstudioPlugin.startUpTime);
				Long shutDown = new Long(
						SolutionsacceleratorstudioPlugin.shutDownTime);
				timeType.setStartupTime(startUp.toString());
				timeType.setShutdownTime(shutDown.toString());
				license.getUsageHistory().getTime().add(timeType);
				license.setTotalUsageTime(calculaeUsagTime().toString());
			}
			final JAXBContext jaxCtx = JAXBContext
					.newInstance("com.rrs.corona.License");
			final FileOutputStream fus = new FileOutputStream(licenseFile);
			final Marshaller marsh = jaxCtx.createMarshaller();
			marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marsh.marshal(license, fus);
			fus.close();
		} catch (Exception ex) {
			final String errMsg = "Exception thrown in Method **save()** in class [ LicenseFileIO.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, ex);
			ex.printStackTrace();
		}
	}

	/**
	 * This method calculate the Total usage of this session and return the
	 * calculated total usage time to the called function. This method clculate
	 * the Total usage time from the Session start and End time from license
	 * static object.
	 * 
	 * @return returns the total usage of the session.
	 */

	public Long calculaeUsagTime() {// This method is used to calculate the usage time
		long usage = 0;
		List timeList = license.getUsageHistory().getTime();
		for (Iterator iterator = timeList.iterator(); iterator.hasNext();) {
			TimeType timeType = (TimeType) iterator.next();
			Long startUp = Long.parseLong(timeType.getStartupTime());
			Long shutDown = Long.parseLong(timeType.getShutdownTime());

			usage = usage + (shutDown - startUp);
		}
		return Long.valueOf(usage);
	}

}
