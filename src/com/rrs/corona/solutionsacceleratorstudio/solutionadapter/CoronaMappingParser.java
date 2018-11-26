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
 *$Id: CoronaMappingParser.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/CoronaMappingParser.java,v $
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import com.rrs.corona.CoronaMapping.ClassObjectList;
import com.rrs.corona.CoronaMapping.CoronaMapping;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;

/**
 * @author Debadatta Mishra
 *
 */
public class CoronaMappingParser
{
	/**
	 * File Name of the Corona Mapping 
	 */
	String fileName = SASConstants.CORONA_MAPPING_FILE_NAME;
	
	/**
	 * File path of the corona mapping file
	 */
	String filePath ;
	/**
	 * Jaxb Context for corona mapping file
	 */
	String coronaMappingJaxbContext = SASConstants.CORONA_MAPPING_JAXBCONTEXT;
	/**
	 * Object of type CoronaMapping 
	 */
	CoronaMapping coronaMap = null;
	
	Logger logger = Logger.getLogger("CoronaMappingParser.class");
	/**
	 * Constructor to initialize the file path of the corona mapping file
	 */
	public CoronaMappingParser()
	{//Here the path for corona mapping file is initialized
		filePath = SASConstants.SAS_ROOT+new SolutionAdapterView().getFolderStructure()+SASConstants.BACK_SLASH_s;
	}
	
	/**
	 * Method used to obtain an Object of type CoronaMapping
	 * @return An object of type CoronaMapping
	 */
	public CoronaMapping getCoronaMapping()
	{//This method is used to obtain a CoronaMapping object for parsing the file
		try
		{
			File saFile =  new File(filePath+fileName);
			if(!saFile.exists())
			{	
				logger.info(  "File does not exist");
				//do nothing
			}
			else
			{
				JAXBContext jaxbCtx = JAXBContext.newInstance(coronaMappingJaxbContext);
				Unmarshaller unmarshal = jaxbCtx.createUnmarshaller();
				CoronaMapping coronaMapping = (CoronaMapping)unmarshal.unmarshal(new FileInputStream(filePath+fileName));
				return coronaMapping;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	/**
	 * Method used to obtain  a list of class names
	 * @return An ArrayList of class names
	 */
	public ArrayList getClassNameList()
	{//This method provides the list of class names
		ArrayList classNameList = new ArrayList();
		coronaMap = getCoronaMapping();
		List clsObjectList = coronaMap.getClassObject();
		for(int i=0 ;i<clsObjectList.size();i++)
		{
			ClassObjectList classObjList = (ClassObjectList)clsObjectList.get(i);
			String className = classObjList.getClassName();
			classNameList.add(className);
		}
		return classNameList;
	}
}
