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
 *$Id: MappingGenerator.java,v 1.2 2006/07/28 13:26:16 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/MappingGenerator.java,v $
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
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import com.rrs.corona.sasadapter.ClassList;
import com.rrs.corona.sasadapter.DSMapList;
import com.rrs.corona.sasadapter.DataObjectType;
import com.rrs.corona.sasadapter.FieldNameList;
import com.rrs.corona.sasadapter.GroupList;
import com.rrs.corona.sasadapter.SolutionsAdapter;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.datasource.DbmsInfo;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

/**
 * 
 * @author Debadatta Mishra
 * 
 */
public class MappingGenerator {
	/**
	 * File path of the dataSource xml file
	 */
	String dataSourceFilePath = SASConstants.SAS_ROOT;// File path of the xml

	// file containing
	// database information

	/**
	 * File name of the XML file
	 */
	String dataSourceFilename = SASConstants.SAS_XML;// File name of the XML

	// file

	/**
	 * Jaxbcontext for the sasAdapter schema
	 */
	String jaxBContext = SASConstants.SASJAXB_CONTEXT;

	/**
	 * A java bean to store the database details for creating the database
	 * configuration in the mapping file
	 */
	OJBDbMappingBean ojbBean = new OJBDbMappingBean();

	/**
	 * PrintWriter object
	 */
	PrintWriter printWriter = null;

	/**
	 * Object for SAReader to parse the sasadapter.xml file
	 */
	SAReader saReader = new SAReader();

	/**
	 * File path for parsing the sasadapter.xml file
	 */
	String filePath;

	/**
	 * File name of the OJB Mapping
	 */
	String ojbMappingFileName = SASConstants.OJB_MAPPING_FILE_NAME;

	/**
	 * Logger to log
	 */
	Logger logger = Logger.getLogger("MappingGenerator.class");

	/**
	 * Default Constructor
	 * 
	 */
	public MappingGenerator() {
		this.filePath = SASConstants.SAS_ROOT
				+ new SolutionAdapterView().getFolderStructure()
				+ SASConstants.BACK_SLASH_s;
	}

	/**
	 * Method used to set the dataSource details to a java bean
	 * 
	 */
	public void setDataSourceDetails() {// This method is used to set database
		// configuration details to
		// a plain java bean
		try {
			Object object1 = saReader.getDataSourceDetails();
			DbmsInfo dbInfoBean = (DbmsInfo) object1;
			// setting the OJBBean for for database mapping
			ojbBean.setUserName(dbInfoBean.getDbUser());
			ojbBean.setPassword(dbInfoBean.getDbPassword());
			String dbURL = dbInfoBean.getDbUrl();
			String[] jdbcInfo = dbURL.split(":");
			String protocol = jdbcInfo[0];
			String subProtocol = jdbcInfo[1] + ":" + jdbcInfo[2];
			String dbAlias = jdbcInfo[3] + ":" + jdbcInfo[4] + ":"
					+ jdbcInfo[5];
			ojbBean.setProtocol(protocol);
			ojbBean.setSubProtocol(subProtocol);
			ojbBean.setDbAlias(dbAlias);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to obtain an Object of type PrintWriter
	 * 
	 * @return an object of type PrintWriter
	 */
	public PrintWriter getPrintWriter() {// This method is used to obtain a
		// PrintWriter object
		try {
			// PrintWriter writer = new PrintWriter(new
			// FileOutputStream(filePath+"/repository.xml"));
			PrintWriter writer = new PrintWriter(new FileOutputStream(filePath
					+ SASConstants.BACK_SLASH_s + ojbMappingFileName));
			return writer;
		} catch (Exception e) {
			final String errMsg = "Exception thrown "
					+ "in Method **getPrintWriter()** in class [ MappingGenerator.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method used to generate the Header portion of the OJB mapping file
	 * 
	 */
	public void generateHeader() {// It generates the header for
		// repository.dtd
		// The repository.dtd must be in the current path for running the
		// application
		printWriter = getPrintWriter();
		printWriter.write("<?xml ");
		printWriter.write("version=" + "\"1.0\"" + " " + "encoding="
				+ "\"UTF-8\"");
		printWriter.write("?>");
		printWriter.write("\n");
		printWriter.write("<!DOCTYPE descriptor-repository PUBLIC");
		printWriter.write("\n");
		printWriter.write("\t\t"
				+ "\"-//Apache Software Foundation//DTD OJB Repository//EN\" ");
		printWriter.write("\n");
		printWriter.write("\t\t" + "\"repository.dtd\" ");
		printWriter.write("\n");
		printWriter.write("[");
		printWriter.write("\n");
		printWriter.write("\n");
		printWriter.write("]>");
		printWriter.write("\n");
		printWriter.write("\n");
	}

	/**
	 * Method used to generate the Database configuration in the OJB Mapping
	 * file
	 * 
	 */
	public void generateDbConfigMapping() {// This method is used to generate
		// the Database configuration for
		// the mapping file
		printWriter.write("<descriptor-repository version=" + "\"1.0\" ");
		printWriter.write("\n");
		printWriter.write("\t\t\t\t\t\t isolation-level="
				+ "\"read-uncommitted\"");
		printWriter.write("\n");
		printWriter.write("\t\t\t\t\t\t proxy-prefetching-limit=" + "\"50\"> ");
		printWriter.write("\n");
		printWriter.write("\n");
		generateJdbcConDescriptor(printWriter);// Method to generate the JDBC
		// connection descriptor
	}

	/**
	 * Method used to generate JDBCConnection descriptor in the mapping file
	 * 
	 * @param printWriter
	 *            of type PrintWriter
	 */
	public void generateJdbcConDescriptor(final PrintWriter printWriter) {// This
		// method
		// is
		// used
		// to
		// generate
		// the
		// Jdbc
		// Connection
		// descriptor
		// for
		// the
		// mapping
		// file
		printWriter.write("\n");
		printWriter
				.write("<!-- *********************OJB DATABASE "
						+ "SPECIFIC CONFIGURATION BEGINS HERE**************************** -->");
		printWriter.write("\n");
		printWriter.write("<jdbc-connection-descriptor");
		printWriter.write("\n");
		printWriter.write("\t\tjcd-alias=" + ojbBean.getJcd_alias());
		printWriter.write("\n");
		printWriter.write("\t\tdefault-connection="
				+ ojbBean.getDefault_connection());
		printWriter.write("\n");
		printWriter.write("\t\tplatform=" + ojbBean.getPlatform());
		printWriter.write("\n");
		printWriter.write("\t\tjdbc-level=" + ojbBean.getJdbc_level());
		printWriter.write("\n");
		printWriter.write("\t\tdriver=" + ojbBean.getDriver());
		printWriter.write("\n");
		printWriter
				.write("\t\tprotocol=" + "\"" + ojbBean.getProtocol() + "\"");
		printWriter.write("\n");
		printWriter.write("\t\tsubprotocol=" + "\"" + ojbBean.getSubProtocol()
				+ "\"");
		printWriter.write("\n");
		printWriter.write("\t\tdbalias=" + "\"" + ojbBean.getDbAlias() + "\"");
		printWriter.write("\n");
		printWriter
				.write("\t\tusername=" + "\"" + ojbBean.getUserName() + "\"");
		printWriter.write("\n");
		printWriter
				.write("\t\tpassword=" + "\"" + ojbBean.getPassword() + "\"");
		printWriter.write("\n");
		printWriter.write("\t\tbatch-mode=" + ojbBean.getBatch_mode());
		printWriter.write("\n");
		printWriter.write("\t\tuseAutoCommit=" + ojbBean.getUseAutoCommit());
		printWriter.write("\n");
		printWriter.write("\t\tignoreAutoCommitExceptions="
				+ ojbBean.getIgnoreAutoCommitException());
		printWriter.write("\n");
		printWriter.write("\t>");
		printWriter.write("\n");
		createObjectCache(printWriter);
		printWriter.write("</jdbc-connection-descriptor>");
	}

	/**
	 * Method used to define object cache in the OJB mapping file
	 * 
	 * @param printWriter
	 *            of type Printwriter
	 */
	public void createObjectCache(final PrintWriter printWriter) {// Method to
		// generate
		// the
		// Object
		// Cache for
		// OJB
		// mapping
		// file
		printWriter.write("\n");
		printWriter.write("\t\t<attribute attribute-name="
				+ "\"initializationCheck\"" + " attribute-value=" + "\"false\""
				+ "/>");
		printWriter.write("\n");
		printWriter.write("\t\t<object-cache class="
				+ "\"org.apache.ojb.broker.cache.ObjectCacheTwoLevelImpl\""
				+ ">");
		printWriter.write("\n");
		printWriter.write("\t\t\t\t<attribute attribute-name="
				+ "\"cacheExcludes\"" + " attribute-value=" + "\"" + "\""
				+ "/>");
		printWriter.write("\n");
		printWriter.write("\t\t\t\t<attribute attribute-name="
				+ "\"applicationCache\"" + " attribute-value="
				+ "\"org.apache.ojb.broker.cache.ObjectCacheDefaultImpl\""
				+ "/>");
		printWriter.write("\n");
		printWriter.write("\t\t\t\t<attribute attribute-name="
				+ "\"copyStrategy\"" + " ");
		printWriter.write("\n");
		printWriter
				.write("\t\t\t\tattribute-value="
						+ "\"org.apache.ojb.broker.cache.ObjectCacheTwoLevelImpl$CopyStrategyImpl\""
						+ "/>");
		printWriter.write("\n");
		printWriter
				.write("\t\t\t\t<attribute attribute-name="
						+ "\"forceProxies\"" + " attribute-value="
						+ "\"false\"" + "/>");
		printWriter.write("\n");
		printWriter.write("\t\t\t\t<attribute attribute-name=" + "\"timeout\""
				+ " attribute-value=" + "\"900\"" + "/>");
		printWriter.write("\n");
		printWriter.write("\t\t\t\t<attribute attribute-name=" + "\"autoSync\""
				+ " attribute-value=" + "\"true\"" + "/>");
		printWriter.write("\n");
		printWriter.write("\t\t\t\t<attribute attribute-name="
				+ "\"cachingKeyType\"" + " attribute-value=" + "\"0\"" + "/>");
		printWriter.write("\n");
		printWriter.write("\t\t\t\t<attribute attribute-name="
				+ "\"useSoftReferences\"" + " attribute-value=" + "\"true\""
				+ "/>");
		printWriter.write("\n");
		printWriter.write("\t\t</object-cache>");
		printWriter.write("\n");
		generateConnectionPool(printWriter);
	}

	/**
	 * Method to generate the ConnectionPool in the OJB Mapping file
	 * 
	 * @param printWriter
	 *            of type PrintWriter
	 */
	public void generateConnectionPool(final PrintWriter printWriter) {// This
		// method
		// is
		// used
		// to
		// generate
		// the
		// OJB
		// connection
		// pool
		printWriter.write("\t\t<connection-pool");
		printWriter.write("\n");
		printWriter.write("\t\t\tmaxActive=" + "\"30\"");
		printWriter.write("\n");
		printWriter.write("\t\t\tvalidationQuery=" + "\"\"");
		printWriter.write("\n");
		printWriter.write("\t\t\ttestOnBorrow=" + "\"true\"");
		printWriter.write("\n");
		printWriter.write("\t\t\ttestOnReturn=" + "\"false\"");
		printWriter.write("\n");
		printWriter.write("\t\t\twhenExhaustedAction=" + "\"0\"");
		printWriter.write("\n");
		printWriter.write("\t\t\tmaxWait=" + "\"10000\"" + ">");
		printWriter.write("\n");
		printWriter.write("\t\t\t<attribute attribute-name=" + "\"fetchSize\""
				+ " attribute-value=" + "\"0\"" + "/>");
		printWriter.write("\n");
		printWriter.write("\t\t\t<attribute attribute-name="
				+ "\"jdbc.defaultBatchValue\"" + " attribute-value=" + "\"5\""
				+ "/>");
		printWriter.write("\n");
		printWriter.write("\t\t\t<attribute attribute-name="
				+ "\"dbcp.poolPreparedStatements\"" + " attribute-value="
				+ "\"false\"" + "/>");
		printWriter.write("\n");
		printWriter.write("\t\t\t<attribute attribute-name="
				+ "\"dbcp.maxOpenPreparedStatements\"" + " attribute-value="
				+ "\"10\"" + "/>");
		printWriter.write("\n");
		printWriter.write("\t\t\t<attribute attribute-name="
				+ "\"dbcp.accessToUnderlyingConnectionAllowed\""
				+ " attribute-value=" + "\"false\"" + "/>");
		printWriter.write("\n");
		printWriter.write("\t\t</connection-pool>");
		printWriter.write("\n");
		printWriter
				.write("\t\t<sequence-manager className="
						+ "\"org.apache.ojb.broker.util.sequence.SequenceManagerHighLowImpl\""
						+ ">");
		printWriter.write("\n");
		printWriter.write("\t\t\t <attribute attribute-name=" + "\"seq.start\""
				+ " attribute-value=" + "\"0\"" + "/>");
		printWriter.write("\n");
		printWriter.write("\t\t\t<attribute attribute-name=" + "\"autoNaming\""
				+ " attribute-value=" + "\"true\"" + "/>");
		printWriter.write("\n");
		printWriter.write("\t\t\t<attribute attribute-name=" + "\"grabSize\""
				+ " attribute-value=" + "\"20\"" + "/>");
		printWriter.write("\n");
		printWriter.write("\t\t</sequence-manager>");
		printWriter.write("\n");
		printWriter.write("");
		printWriter.write("\n");
		printWriter.write("");
		printWriter.write("\n");
	}

	/**
	 * Method used to define the internal mapping for OJB Mapping file
	 * 
	 */
	public void generateInternalMapping() {// This method is used to generate
		// OJB Internal mapping
		printWriter.write("\n");
		printWriter
				.write("<!-- *******************"
						+ "******* OJB INTERNAL MAPPING BEGINS HERE  ****************** -->");
		printWriter.write("\n");
		printWriter.write("\t\t<class-descriptor");
		printWriter.write("\n");
		printWriter.write("\t\tclass="
				+ "\"org.apache.ojb.broker.util.sequence.HighLowSequence\"");
		printWriter.write("\n");
		printWriter.write("\t\ttable=" + "\"OJB_HL_SEQ\"");
		printWriter.write("\n");
		printWriter.write("\t>");
		printWriter.write("\n");
		printWriter.write("\t\t<object-cache class="
				+ "\"org.apache.ojb.broker.cache.ObjectCacheEmptyImpl\"" + ">");
		printWriter.write("\n");
		printWriter.write("\t\t</object-cache>");
		printWriter.write("\n");
		printWriter.write("\t\t<field-descriptor");
		printWriter.write("\n");
		printWriter.write("\t\t\tname=" + "\"name\"");
		printWriter.write("\n");
		printWriter.write("\t\t\tcolumn=" + "\"tablename\"");
		printWriter.write("\n");
		printWriter.write("\t\t\tjdbc-type=" + "\"VARCHAR\"");
		printWriter.write("\n");
		printWriter.write("\t\t\tprimarykey=" + "\"true\"");
		printWriter.write("\n");
		printWriter.write("\t/>");
		printWriter.write("\n");
		printWriter.write("\t<field-descriptor");
		printWriter.write("\n");
		printWriter.write("\t\t\tname=" + "\"maxKey\"");
		printWriter.write("\n");
		printWriter.write("\t\t\tcolumn=" + "\"MAX_KEY\"");
		printWriter.write("\n");
		printWriter.write("\t\t\tjdbc-type=" + "\"BIGINT\"");
		printWriter.write("\n");
		printWriter.write("\t/>");
		printWriter.write("\n");
		printWriter.write("\t<field-descriptor");
		printWriter.write("\n");
		printWriter.write("\t\t\tname=" + "\"grabSize\"");
		printWriter.write("\n");
		printWriter.write("\t\t\tcolumn=" + "\"GRAB_SIZE\"");
		printWriter.write("\n");
		printWriter.write("\t\t\tjdbc-type=" + "\"INTEGER\"");
		printWriter.write("\n");
		printWriter.write("\t/>");
		printWriter.write("\n");
		printWriter.write("\t<field-descriptor");
		printWriter.write("\n");
		printWriter.write("\t\t\t");
		printWriter.write("\n");
		printWriter.write("\t\t\tname=" + "\"version\"");
		printWriter.write("\n");
		printWriter.write("\t\t\tcolumn=" + "\"VERSION\"");
		printWriter.write("\n");
		printWriter.write("\t\t\tjdbc-type=" + "\"INTEGER\"");
		printWriter.write("\n");
		printWriter.write("\t\t\tlocking=" + "\"true\"");
		printWriter.write("\n");
		printWriter.write("\t/>");
		printWriter.write("\n");
		printWriter.write("\t\t</class-descriptor>");
		printWriter.write("\n");
	}

	/**
	 * Method used to generate the user defined mapping in OJB Mapping file
	 * 
	 */
	public void generateUserDefinedMapping() {// This method is used to
		// generate the user defined
		// mapping for OJB
		printWriter
				.write("<!--********************"
						+ "*********OJB USER DEFINED MAPPING BEGINS HERE *********************************-->");
		SolutionsAdapter sAdapter = saReader.getSA();
		DataObjectType dataObject = sAdapter.getDataObject();
		List listGroup = dataObject.getGroup();
		for (Iterator groupIter = listGroup.iterator(); groupIter.hasNext();) {
			GroupList groupList = (GroupList) groupIter.next();
			List classList = groupList.getClasses();
			for (int j = 0; j < classList.size(); j++) {
				printWriter.write("\n");
				printWriter
						.write("<!--**************************** "
								+ (j + 1)
								+ " Object-Table Mapping *******************************************-->");
				printWriter.write("\n");
				ClassList classesList = (ClassList) classList.get(j);
				String className = classesList.getClassName();
				printWriter.write("\t\t<class-descriptor ");
				printWriter.write("\n");
				printWriter.write("\t\t\tclass=" + "\"" + className + "\"");// java
				// class
				// name
				// for
				// mapping
				printWriter.write("\n");
				// Specifies the table name for the class
				String onlyTableName = getTableName(className);
				// String onlyTableName =
				// className.substring(className.lastIndexOf(".")+1,className.length());
				printWriter.write("\t\t\ttable=" + "\"" + onlyTableName + "\"");// table
				// name
				// for
				// mapping
				printWriter.write("\n");
				printWriter.write("\t\t>");// closing > of class descriptor
				printWriter.write("\n");
				List fieldList = classesList.getFields();
				for (int k = 0; k < fieldList.size(); k++) {
					printWriter.write("\t\t <field-descriptor");
					printWriter.write("\n");
					FieldNameList listField = (FieldNameList) fieldList.get(k);
					printWriter.write("\t\t\tname=" + "\""
							+ listField.getFieldName() + "\"");
					printWriter.write("\n");
					List dodsMapList = listField.getDODSMap();
					for (int l = 0; l < dodsMapList.size(); l++) {
						DSMapList dsType = (DSMapList) dodsMapList.get(l);
						printWriter.write("\t\t\tcolumn=" + "\""
								+ dsType.getFieldName() + "\"");
						printWriter.write("\n");
						String jdbcType = getJDBCType(listField.getFieldType());
						printWriter.write("\t\t\tjdbc-type=" + "\"" + jdbcType
								+ "\"");// jdbc type
						printWriter.write("\n");
						if (dsType.getPrimaryKey().equals("true")) {
							printWriter
									.write("\t\t\tprimarykey=" + "\"true\" ");
							printWriter.write("\n");
							printWriter.write("\t\t\tautoincrement="
									+ "\"false\" ");
							printWriter.write("\n");
						} else {
							// do nothing
						}
						printWriter.write("\t\t/>");// end tag for field
						// descriptor
						printWriter.write("\n");
					}
				}
				printWriter.write("\t\t</class-descriptor>");// end tag for
				// class
				// descriptor
			}
		}
	}

	/**
	 * This method is used to obtain the table name by providing the class name.
	 * 
	 * @param param_class_Name
	 *            of type String.
	 * @return the name of the table that is mapped to a particular class
	 */
	private String getTableName(final String param_class_Name) {// This method
		// is used to
		// know the
		// table name
		// that is
		// mapped with the class name
		String table_Name = null;
		SolutionsAdapter sAdapter = saReader.getSA();
		DataObjectType dataObject = sAdapter.getDataObject();
		List listGroup = dataObject.getGroup();
		group_Loop: for (Iterator groupIter = listGroup.iterator(); groupIter
				.hasNext();) {
			GroupList groupList = (GroupList) groupIter.next();
			List classList = groupList.getClasses();
			class_Loop: for (int j = 0; j < classList.size(); j++) {
				ClassList classesList = (ClassList) classList.get(j);
				final String className = classesList.getClassName();// Provides
				// the class
				// name
				if (className.equals(param_class_Name)) {
					List fieldList = classesList.getFields();// Provides the
					// list of
					// fields
					// present in a
					// class
					field_Loop: for (int k = 0; k < fieldList.size(); k++) {
						FieldNameList listField = (FieldNameList) fieldList
								.get(k);
						List dodsMapList = listField.getDODSMap();
						doDsMap_Loop: for (int l = 0; l < dodsMapList.size(); l++) {
							DSMapList dsType = (DSMapList) dodsMapList.get(l);
							table_Name = dsType.getTableName();
							break group_Loop;
						}
					}
				}
			}
		}
		return table_Name;
	}

	/**
	 * Method to set the appropriate JDBC type in the class descriptor for OJB
	 * mapping file
	 * 
	 * @param jdbcName
	 * @return
	 */
	public String getJDBCType(final String jdbcName) {// This method is used
		// to return the actual
		// OJB specific
		// JDBC-TYPE
		// String jdbcTypeName ;
		if (jdbcName.equalsIgnoreCase("double")) {
			String jdbcTypeName = "DOUBLE";
			return jdbcTypeName;
		} else if (jdbcName.equalsIgnoreCase("String")) {
			String jdbcTypeName = "VARCHAR";
			return jdbcTypeName;
		} else if (jdbcName.equalsIgnoreCase("boolean")) {
			String jdbcTypeName = "BOOLEAN";
			return jdbcTypeName;
		} else if (jdbcName.equalsIgnoreCase("int")) {
			String jdbcTypeName = "INTEGER";
			return jdbcTypeName;
		} else if (jdbcName.equalsIgnoreCase("Date")) {
			String jdbcTypeName = "DATE";
			return jdbcTypeName;
		} else if (jdbcName.equalsIgnoreCase("Time")) {
			String jdbcTypeName = "TIME";
			return jdbcTypeName;
		} else if (jdbcName.equalsIgnoreCase("Clob")) {
			String jdbcTypeName = "CLOB";
			return jdbcTypeName;
		} else if (jdbcName.equalsIgnoreCase("Blob")) {
			String jdbcTypeName = "BLOB";
			return jdbcTypeName;
		}

		return null;

	}

	/**
	 * Method to close the Printwriter Object
	 * 
	 */
	public void generateFooter() {// This method is used to close file
		// operation
		printWriter.write("\n");
		printWriter.write("</descriptor-repository>");
		printWriter.flush();
		printWriter.close();
	}

	/**
	 * Method to generate the OJB Mapping file
	 *
	 */
	public void generateMapping() {//This method is used to generate the OJB mapping file
		setDataSourceDetails();//for testing now
		generateHeader();//Method to generate the header of the repository.xml file
		generateDbConfigMapping();//method to generate OJB specific database mapping
		generateInternalMapping();//method to generate the OJB internal mapping
		generateUserDefinedMapping();
		generateFooter();
	}
}
