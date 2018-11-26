
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
 *$Id: TableClassInfo.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $
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
import java.util.HashMap;
import java.util.List;

import java.util.Iterator;

import java.util.Map;
import java.util.Set;

import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.rrs.corona.sasadapter.ClassList;
import com.rrs.corona.sasadapter.DSMapList;
import com.rrs.corona.sasadapter.DataObjectType;
import com.rrs.corona.sasadapter.FieldNameList;
import com.rrs.corona.sasadapter.GroupList;
import com.rrs.corona.sasadapter.SolutionsAdapter;
import com.rrs.corona.beans.TableClassInfoBean;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.sasutil.ClassGenerator;

/**
 * @author Debadatta Mishra
 *
 */
public class TableClassInfo
{
	
	/**
	 * A variable of type String to store the path of the file
	 */
	private transient String filePath;
	/**
	 * Variable of type String specifying the solutionsadapter.cml file
	 */
	private transient String saFileName = SASConstants.SOLUTIONS_ADAPTER_XML;
	/**
	 * String variable to refer the JAXB Context
	 */
	private transient String jaxBContext = SASConstants.SOLUTIONS_ADAPTER_CONTEXT ;
	/**
	 * Logger to log the messages
	 */
	private transient Logger logger = Logger.getLogger("TableClassInfo.class");
	/**
	 * Object of type SolutionsAdapter
	 */
	private transient SolutionsAdapter solnAdapter = null;
	/**
	 * A Hashmap to store the table and class field information
	 */
	private transient HashMap tableClassMap = new HashMap();
	
	/**
	 * Constructor 
	 */
	public TableClassInfo()
	{
		//this.filePath = "C:/rr/eclipse/plugins/com.rrs.corona.sas_1.0.0/corona/SAOM/ADOM"+"/";
		this.filePath = SASConstants.SAS_ROOT+new SolutionAdapterView().getFolderStructure()+SASConstants.BACK_SLASH_s;
		solnAdapter = getSA();
	}
	
	/**
	 * Method to obtain a SolutionsAdapter object
	 * @return an Object of type SolutionsAdapter
	 */
	public SolutionsAdapter getSA()
	{//This method provides an object of type SolutionsAdapter
		try
		{
			final File saFile =  new File(filePath+saFileName);
			if(!saFile.exists())
			{	
				logger.info( "File does not exist");
				//do nothing
			}
			else
			{
				JAXBContext jaxbCtx = JAXBContext.newInstance(jaxBContext);
				Unmarshaller unmarshal = jaxbCtx.createUnmarshaller();
				SolutionsAdapter solutionAdapter = (SolutionsAdapter)unmarshal.unmarshal(new FileInputStream(filePath+saFileName));
				return solutionAdapter;
			}
		}
		catch(Exception e)
		{
			logger.info("Exception thrown in method :::getSA()::: in class TableClassInfo.java:::");
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	/**
	 * @return a HashMap containg the table name 
	 */
	public HashMap getTableClassInfo()
	{
		String table_Name = null;
		final DataObjectType dataObject = solnAdapter.getDataObject();
		final List listGroup = dataObject.getGroup();
		group_Loop: for(final Iterator groupIter= listGroup.iterator(); groupIter.hasNext();)
		{
			final GroupList groupList = (GroupList) groupIter.next();
			final List classList = groupList.getClasses();
			class_Loop: for(int j=0;j<classList.size();j++)
			{
				final ClassList classesList = (ClassList)classList.get(j);
				final String className = classesList.getClassName();//Provides the class name
				final List fieldList = classesList.getFields();//Provides the list of fields present in a class
				
				field_Loop: for(int k=0;k<fieldList.size();k++)
				{
					final FieldNameList listField = (FieldNameList)fieldList.get(k);
					final String clsFieldName = listField.getFieldName();//provides class field name
					final String clsFieldType = listField.getFieldType();//provides class field type
					final List dodsMapList = listField.getDODSMap();
					doDsMap_Loop: for(int l=0;l<dodsMapList.size();l++)
					{
						final DSMapList dsType = (DSMapList)dodsMapList.get(l);
						table_Name = dsType.getTableName();//provides table name
						final String tableFieldName = dsType.getFieldName();//table field type
						TableClassInfoBean tbclsBean = new TableClassInfoBean();
						//set all the required values to the beans
						tbclsBean.setClsName(className);
						tbclsBean.setClsFieldName(clsFieldName);
						tbclsBean.setClsFieldType(clsFieldType);
						tbclsBean.setTableName(table_Name);
						tbclsBean.setTableFieldName(tableFieldName);
						
						if(!tableClassMap.containsKey(table_Name))
							tableClassMap.put( table_Name,new ArrayList());
						( ( ArrayList) tableClassMap.get(table_Name) ).add(tbclsBean);
					}	
				}
			}
		}	
		return tableClassMap;
	}
	
	public void generateJavaClasses(final HashMap classMap,final String fileName)
	{
		final Set set = classMap.entrySet();
		final Iterator itr = set.iterator();
		while(itr.hasNext())
		{
			final Map.Entry me = (Map.Entry)itr.next();
			final String tableName = (String)me.getKey();
			final ArrayList fieldInfoList = (ArrayList)me.getValue();
			ClassGenerator generator = new ClassGenerator(tableName);
			generator.generateJavaFile(fieldInfoList,fileName);
			
		}
	}
	
}
