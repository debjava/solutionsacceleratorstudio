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

package com.rrs.corona.solutionsacceleratorstudio.datasource;

/**
 * @author Debadatta Mishra
 * 
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

public class DatabaseManager {
	/**
	 * String variable for database URL
	 */
	transient String databaseURL = null; // for

	// database
	// URL
	/**
	 * String variable for database user name
	 */
	transient String databaseUserName = null; // for

	// database
	// USER
	/**
	 * String variable for database password
	 */
	transient String databasePassword = null; // for

	// database
	// PASSWORD
	/**
	 * SQL Connection
	 */
	transient Connection connection = null; // to

	// test
	// database
	// connection
	/**
	 * Boolean variable to test database connection
	 */
	transient boolean testConnection = false;

	/**
	 * Logger to Log
	 */
	protected final Log logger_ = LogFactory.getLog(getClass());

	/**
	 * SQL ResultSet
	 */
	transient ResultSet resultSet = null; // SQL

	// ResultSet
	/**
	 * SQL Statement
	 */
	transient Statement statement = null; // SQL

	// Statement
	transient ResultSet metaResult = null;

	/**
	 * Default Constructor
	 */
	public DatabaseManager() {

	}

	/**
	 * Method to get database connection
	 * 
	 * @param url
	 *            database URL of type String
	 * @param user
	 *            database username of type String
	 * @param pwd
	 *            database password of type String
	 * @return a database connection
	 */
	public Connection getDatabaseConnection(final String url,
			final String user, final String pwd) {// Method for database
													// connection by providing
													// database URL, database
													// user, database password
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			connection = DriverManager.getConnection(url, user, pwd);
		} catch (SQLException se) {
			final String errMsg = "Exception thrown in Method "
					+ "**getDatabaseConnection()** in class [ DatabaseManager.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, se);
			se.printStackTrace();
		}
		return connection;
	}

	/**
	 * Method to obtain a database connection
	 * 
	 * @param dbmsInfo
	 *            of Java bean type
	 * @return a database Connection
	 * @throws SQLException
	 */
	// Method for passing object type
	public Connection getDatabaseConnection(final DbmsInfo dbmsInfo)
			throws SQLException {// Method to obtain a database connection
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		connection = DriverManager.getConnection(dbmsInfo.getDbUrl(), dbmsInfo
				.getDbUser(), dbmsInfo.getDbPassword());
		return connection;
	}

	/**
	 * Method to check database connection
	 * 
	 * @param url
	 *            database URL of type String
	 * @param user
	 *            database username of type String
	 * @param pwd
	 *            database password of type String
	 * @return a databse connection
	 */
	public boolean checkDatabaseConnection(String url, String user, String pwd) {// This
																					// method
																					// is
																					// used
																					// to
																					// check
																					// for
																					// successful
																					// database
																					// connection
		// It will return true, if connection is successful
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			connection = DriverManager.getConnection(url, user, pwd);
			if (connection != null) {
				testConnection = true;
			} else {
				testConnection = false;
			}
		} catch (SQLException se) {
			final String errMsg = "Exception thrown in Method "
					+ "**checkDatabaseConnection()** in class [ DatabaseManager.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, se);
			testConnection = false;
			se.printStackTrace();
		}
		return testConnection;
	}

	/**
	 * Method to get database connection
	 * 
	 * @param dbInfo
	 *            of type Java bean
	 * @return a database connection
	 */
	public boolean checkDatabaseConnection(DbmsInfo dbInfo) {// Method to
																// check
																// database
																// connection by
		// passing database information in an object
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			connection = DriverManager.getConnection(dbInfo.getDbUrl(), dbInfo
					.getDbUser(), dbInfo.getDbPassword());
			if (connection != null) {
				testConnection = true;
			} else {
				testConnection = false;
			}
		} catch (SQLException se) {
			final String errMsg = "Exception thrown in Method "
					+ "**checkDatabaseConnection()** in class [ DatabaseManager.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, se);
			testConnection = false;
			se.printStackTrace();
		}
		return testConnection;
	}

	/**
	 * Method to get a list of database names
	 * 
	 * @param con
	 *            of type database Connection
	 * @return list of database names list
	 */
	public ArrayList getAllDatabaseNames(Connection con) {// This method is
															// used to get all
															// database table
															// names
		// It will return an arraylist containing the list of database names
		String query_all_databasename_str = "select * from all_users ";
		ArrayList databaseNames = new ArrayList();
		try {
			Statement statement = con.createStatement();
			final ResultSet resultSet = statement
					.executeQuery(query_all_databasename_str);
			while (resultSet.next()) {
				TableInfo tableInfoObj = new TableInfo();
				tableInfoObj.setTableName(resultSet.getString(1));
				databaseNames.add(tableInfoObj);
			}
			return databaseNames;
		} catch (SQLException se) {
			final String errMsg = "Exception thrown in Method "
					+ "**getAllDatabaseNames()** in class [ DatabaseManager.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, se);
			se.printStackTrace();
		}
		return null;
	}

	/**
	 * Method to get all table names list
	 * 
	 * @param con
	 *            of type database Connection
	 * @return list of table names
	 */
	public ArrayList getAllTableNames(Connection con) {// This method is used
														// to get all database
														// table names
		// It will return an arrayList containing the list of table names
		final String query_all_tab_str = "select * from tab ";
		ArrayList databaseTableNames = new ArrayList();
		try {
			Statement statement = con.createStatement();
			resultSet = statement.executeQuery(query_all_tab_str);
			while (resultSet.next()) {
				TableInfo tableInfoObj = new TableInfo();
				tableInfoObj.setTableName(resultSet.getString(1));// It
				// will
				// fetch
				// only
				// the
				// table
				// names
				databaseTableNames.add(tableInfoObj);// This arrayList
				// contains the list of
				// database table names
			}
			return databaseTableNames;
		} catch (SQLException se) {
			final String errMsg = "Exception thrown in Method "
					+ "**getAllTableNames()** in class [ DatabaseManager.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, se);
			se.printStackTrace();
		} finally {
			try {
				resultSet.close();
			}

			catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Method to get table information by passing one table name
	 * 
	 * @param tableName
	 *            of type String
	 * @return list of field information
	 */
	public ArrayList getEachTableInfo(final String tableName) {// Method to
																// fetch each
																// table
																// information
																// ie number of
																// fields, field
																// type,
		// field name etc by giving the table name as input
		DbmsInfo dbInfo = new DbmsInfo();// java bean containg database
		// url,password,user etc
		Connection con = getDatabaseConnection(dbInfo.getDbUrl(), dbInfo
				.getDbUser(), dbInfo.getDbPassword());
		final String queryString = "select * from " + tableName;
		ArrayList fieldInfoObjsList = new ArrayList();// ArrayList contains
		// the field
		// inforamation
		try {
			Statement statement = con.createStatement();
			resultSet = statement.executeQuery(queryString);
			ResultSetMetaData resultsetmetaData = resultSet.getMetaData();
			logger_.info("In " + tableName + " Total no of Columns "
					+ resultsetmetaData.getColumnCount());
			for (int i = 1; i < resultsetmetaData.getColumnCount() + 1; i++) {
				FieldInfo fieldIno = new FieldInfo();
				fieldIno.setFieldName(resultsetmetaData.getColumnName(i));
				fieldIno.setFieldType(resultsetmetaData.getColumnTypeName(i));
				fieldInfoObjsList.add(fieldIno);
			}
			return fieldInfoObjsList;
		} catch (SQLException se) {
			final String errMsg = "Exception thrown in Method "
					+ "**getEachTableInfo()** in class [ DatabaseManager.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, se);
			close(resultSet);
			se.printStackTrace();
		} finally {
			close(resultSet);
		}
		return null;
	}

	/**
	 * Method to get table information by passing table name
	 * 
	 * @param con
	 *            of type Connection
	 * @param tableName
	 *            of type String
	 * @return list of field information
	 */
	public synchronized ArrayList getEachTableInfo(final Connection con,
			final String tableName) {// Method to fetch each table
										// information ie number of fields,
										// field type,
		// field name and primary key by giving the table name as input
		String queryString = "select * from " + tableName;
		ArrayList fieldInfoObjsList = new ArrayList();// ArrayList containing
		String pkFieldName = new String();// variable to hold primary key name
		try {
			// to get primary key
			DatabaseMetaData dbMetaData = con.getMetaData();
			metaResult = dbMetaData.getPrimaryKeys(null, null, tableName);
			ArrayList pkList = new ArrayList();
			while (metaResult.next()) {
				pkFieldName = metaResult.getString("COLUMN_NAME");
				if (!pkList.contains(pkFieldName)) {
					pkList.add(pkFieldName);
				}
			}
			statement = con.createStatement();
			resultSet = statement.executeQuery(queryString);
			ResultSetMetaData resultsetmetaData = resultSet.getMetaData();
			logger_.info("In " + tableName + " Total no of Columns "
					+ resultsetmetaData.getColumnCount());
			for (int i = 1; i < resultsetmetaData.getColumnCount() + 1; i++) {
				FieldInfo fieldInfo = new FieldInfo();
				String fieldName = resultsetmetaData.getColumnName(i);
				fieldInfo.setFieldName(fieldName);
				fieldInfo.setFieldType(resultsetmetaData.getColumnTypeName(i));

				if (pkList.contains(fieldName)) {
					fieldInfo.setPkKeyFieldName(true);// to set the primary
														// key field
				} else {
					fieldInfo.setPkKeyFieldName(false);
				}
				fieldInfoObjsList.add(fieldInfo);
			}
			close(statement);
			close(metaResult);
			close(resultSet);
			return fieldInfoObjsList;
		} catch (SQLException se) {

			close(statement);
			close(metaResult);
			close(resultSet);
			final String errMsg = "Exception thrown in Method "
					+ "**getEachTableInfo()** in class [ DatabaseManager.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, se);
			se.printStackTrace();
		} finally {
			close(metaResult);
			close(resultSet);
		}
		return null;
	}

	/**
	 * Method to obtain each table information
	 * 
	 * @param con
	 *            of type SQL Connection
	 * @param tableNames
	 *            an arrayList containing tables
	 */
	public void getEachTableInfo(final Connection con,
			final ArrayList tableNames) {
		for (int i = 0; i < tableNames.size(); i++) {
			String tabName = (String) tableNames.get(i);
			String queryString = "select * from " + tabName;
			try {
				statement = con.createStatement();
				resultSet = statement.executeQuery(queryString);
				ResultSetMetaData resultsetmetaData = resultSet.getMetaData();
				logger_.info("In " + tabName + " Total no of Columns "
						+ resultsetmetaData.getColumnCount());
				for (int j = 1; j < resultsetmetaData.getColumnCount() + 1; j++) {
					logger_.info("Column Name : "
							+ resultsetmetaData.getColumnName(j) + "===="
							+ "Column Type  : "
							+ resultsetmetaData.getColumnTypeName(j));
				}
			} catch (SQLException se) {
				final String errMsg = "Exception thrown in Method "
						+ "**getEachTableInfo()** in class [ DatabaseManager.java ]";
				SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg,
						se);
				close(statement);
				close(resultSet);
				se.printStackTrace();
			} finally {
				close(statement);
				close(resultSet);
			}
		}
	}

	/**
	 * Method to close database connection
	 * 
	 * @param con
	 *            of type SQL Connection
	 */
	public void close(final Connection con) {// for closing database
												// connection
		if (con != null) {
			try {
				con.close();
			} catch (SQLException se) {
				final String errMsg = "Exception thrown in Method "
						+ "**close(Connection)** in class [ DatabaseManager.java ]";
				SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg,
						se);
				se.printStackTrace();
			}
		}
	}

	/**
	 * Method to close ResultSet
	 * 
	 * @param rSet
	 *            of type SQL ResultSet
	 */
	public void close(final ResultSet rSet) {// for closing resultSet
		if (rSet != null) {
			try {
				rSet.close();
			} catch (SQLException se) {
				final String errMsg = "Exception thrown in Method "
						+ "**close(ResultSet)** in class [ DatabaseManager.java ]";
				SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg,
						se);
				se.printStackTrace();
			}
		}
	}

	/**
	 * Method to close Statement
	 * 
	 * @param stmt
	 *            of type SQL Statement
	 */
	public void close(final Statement stmt) {// for closing Statement
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException se) {
				final String errMsg = "Exception thrown in Method "
						+ "**close(Statement)** in class [ DatabaseManager.java ]";
				SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg,
						se);
				se.printStackTrace();
			}
		}
	}

}
