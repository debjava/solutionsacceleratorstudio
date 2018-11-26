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
 *$Id: ReadAndWriteXML.java,v 1.2 2006/07/28 13:26:16 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/ReadAndWriteXML.java,v $
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.rrs.corona.Sas.CMSServerList;
import com.rrs.corona.Sas.ObjectFactory;
import com.rrs.corona.Sas.SolutionAdapter;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

/**
 * This class is used to create or read the solutionsadapter.xml file for
 * getting/setting the server information and the datasource information
 * @author Maharajan
 *
 */
public class ReadAndWriteXML {
	public static final String IPADDRESS_s = "ipAddress";

	public static final String SERVERNAME_s = "serverName";

	public static final String PORTNO_s = "portNo";

	public String[] getCmsServer() throws JAXBException {
		String[] serverName = null;
		File cmsServeInfo = new File(SASConstants.SAS_ROOT
				+ SASConstants.SAS_XML);
		if (cmsServeInfo.exists()) {
			final JAXBContext jtx1 = JAXBContext.newInstance(
					SASConstants.SASJAXB_CONTEXT, this.getClass()
							.getClassLoader());

			Unmarshaller unmarshallObj = jtx1.createUnmarshaller();
			SolutionAdapter adapter = (SolutionAdapter) unmarshallObj
					.unmarshal(cmsServeInfo);
			List serverInfo = adapter.getCMSServer();
			serverName = new String[serverInfo.size()];
			int i = 0;
			for (Iterator iter = serverInfo.iterator(); iter.hasNext(); i++) {
				CMSServerList cmsList = (CMSServerList) iter.next();
				serverName[i] = cmsList.getCMSName();
			}
		}
		return serverName;
	}

	public void populateCmsServerXML(String serverName, String ipAddress,
			String portNo) {
		try {
			final JAXBContext jtx1 = JAXBContext.newInstance(
					SASConstants.SASJAXB_CONTEXT, this.getClass()
							.getClassLoader());
			File cmsServeInfo = new File(SASConstants.SAS_ROOT
					+ SASConstants.SAS_XML);
			SolutionAdapter adapter;
			if (cmsServeInfo.exists()) {
				Unmarshaller unmarshallObj = jtx1.createUnmarshaller();
				adapter = (SolutionAdapter) unmarshallObj
						.unmarshal(cmsServeInfo);
			} else {
				new File(SASConstants.SAS_ROOT).mkdirs();
				ObjectFactory objCMS1 = new ObjectFactory();
				adapter = objCMS1.createSolutionAdapter();
			}
			ObjectFactory objCMS = new ObjectFactory();
			CMSServerList listofCMS = (CMSServerList) objCMS
					.createCMSServerList();
			listofCMS.setCMSName(serverName);
			listofCMS.setIPAddress(ipAddress);
			listofCMS.setPortNo(portNo);
			adapter.getCMSServer().add(listofCMS);
			FileOutputStream fus;
			fus = new FileOutputStream(cmsServeInfo);
			final JAXBContext jtx = JAXBContext.newInstance(
					SASConstants.SASJAXB_CONTEXT, this.getClass()
							.getClassLoader());
			Marshaller marshallObj = jtx.createMarshaller();
			marshallObj = jtx.createMarshaller();
			marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshallObj.marshal(adapter, fus);
			fus.close();
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method **populateCmsServerXML()** in class [ ReadAndWriteXML.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

	public HashMap getServerInfo(String serverName) {
		HashMap<String, String> serverInformation = new HashMap<String, String>();
		try {
			final JAXBContext jtx1 = JAXBContext.newInstance(
					SASConstants.SASJAXB_CONTEXT, this.getClass()
							.getClassLoader());
			File cmsServeInfo = new File(SASConstants.SAS_ROOT
					+ SASConstants.SAS_XML);
			Unmarshaller unmarshallObj = jtx1.createUnmarshaller();
			SolutionAdapter adapter = (SolutionAdapter) unmarshallObj
					.unmarshal(cmsServeInfo);
			List serverInfo = adapter.getCMSServer();

			for (Iterator iter = serverInfo.iterator(); iter.hasNext();) {
				CMSServerList cmsList = (CMSServerList) iter.next();
				if (serverName.equals(cmsList.getCMSName())) {
					serverInformation.put(ReadAndWriteXML.SERVERNAME_s,
							serverName);
					serverInformation.put(ReadAndWriteXML.IPADDRESS_s, cmsList
							.getIPAddress());
					serverInformation.put(ReadAndWriteXML.PORTNO_s, cmsList
							.getPortNo());
				}
			}
		} catch (Exception e) {
			final String errMsg = "Exception thrown " +
					"in Method **getServerInfo()** in class [ ReadAndWriteXML.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
		return serverInformation;
	}
}
