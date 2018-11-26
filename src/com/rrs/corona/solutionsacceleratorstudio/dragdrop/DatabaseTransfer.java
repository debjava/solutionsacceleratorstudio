/*******************************************************************************
 * @rrs_start_copyright
 * 
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved. This
 * software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Red Rabbit Software. ©
 * 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights
 * reserved. Red Rabbit Software - Development Program 5 of 15
 * 
 * $Id: DatabaseTransfer.java,v 1.2 2006/07/28 13:35:07 redrabbit Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/dragdrop/DatabaseTransfer.java,v $
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

package com.rrs.corona.solutionsacceleratorstudio.dragdrop;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;
import com.rrs.corona.solutionsacceleratorstudio.project.AtomicMetricBean;
import com.rrs.corona.solutionsacceleratorstudio.project.ProjectData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is a transfer class for drag and drop. This writes the information
 * into a bytearray while dragging and get back the information from the
 * bytearray while dropping
 * 
 * @author Maharajan
 * @see com.rrs.corona.solutionsacceleratorstudio.dragdrop.DatabaseBean
 */

public class DatabaseTransfer extends ByteArrayTransfer {
	private static final int Databae_ID = 
		registerType(SASConstants.DATAOBJECT_TRANSFER_ID);

	private static final DatabaseTransfer INSTANCE = new DatabaseTransfer();

	public static DatabaseTransfer getInstance() {
		return INSTANCE;
	}

	protected int[] getTypeIds() {
		return new int[] { Databae_ID };
	}

	protected String[] getTypeNames() {
		return new String[] { SASConstants.DATAOBJECT_TRANSFER_ID };
	}

	/**
	 * This method is called while drag
	 */
	protected void javaToNative(Object object, TransferData transferData) {
		if ((object == null) || !(object instanceof DatabaseBean)) {
			return;
		}

		final DatabaseBean dataBean = (DatabaseBean) object;

		if (isSupportedType(transferData)) {
			try {
				final ByteArrayOutputStream stream = new ByteArrayOutputStream();
				final DataOutputStream out = new DataOutputStream(stream);
				out.writeUTF(dataBean.getTypeofData());
				// transfer the data for group and its sub children
				if (dataBean.getTypeofData().equals(DataObjectView.group_s)
						&& (dataBean.getGroupName() != null)) {
					ArrayList allTable = dataBean.getAllTables();
					out.writeUTF(dataBean.getGroupName());
					out.writeUTF(String.valueOf(allTable.size()));
					for (int count = 0; count < allTable.size(); count++) {
						DatabaseBean subBean = (DatabaseBean) allTable
								.get(count);
						out.writeUTF(subBean.getTableName());
						ArrayList fieldList = subBean.getFieldMap();
						HashMap fieldType = subBean.getFieldType();
						out.writeUTF(String.valueOf(fieldList.size()));

						for (int i = 0; i < fieldList.size(); i++) {
							out.writeUTF((String) fieldList.get(i));
							out.writeUTF((String) fieldType.get(fieldList
									.get(i)));
						}
					}
				}// transfer the data for table and its children
				else if (dataBean.getTypeofData()
						.equals(DataObjectView.table_s)
						&& (dataBean.getTableName() != null)) {
					out.writeUTF(dataBean.getTableName());

					ArrayList fieldList = dataBean.getFieldMap();
					HashMap fieldType = dataBean.getFieldType();
					out.writeUTF(String.valueOf(fieldList.size()));

					for (int i = 0; i < fieldList.size(); i++) {
						out.writeUTF((String) fieldList.get(i));
						out.writeUTF((String) fieldType.get(fieldList.get(i)));
					}
				}// transfer the data for field
				else if (dataBean.getTypeofData()
						.equals(DataObjectView.field_s)
						&& (dataBean.getFieldName() != null)) {
					out.writeUTF(dataBean.getFieldName());
					out.writeUTF(dataBean.getMappingType());
					out.writeUTF(dataBean.getDataSourceName());
					out.writeUTF(dataBean.getTableName());

					ArrayList fieldList = dataBean.getFieldMap();
					HashMap fieldType = dataBean.getFieldType();
					out.writeUTF(String.valueOf(fieldList.size()));

					for (int i = 0; i < fieldList.size(); i++) {
						out.writeUTF((String) fieldList.get(i));
						out.writeUTF((String) fieldType.get(fieldList.get(i)));
						out.writeUTF(String.valueOf(dataBean.isPrimaryKey()));
					}
				} else if (dataBean.getTypeofData().equals(
						DataObjectView.mapAtomicType_s)) {
					AtomicMetricBean atomicBean = (AtomicMetricBean) dataBean
							.getAtomicBean();
					out.writeUTF(atomicBean.getName());
					out.writeUTF(atomicBean.getDescription());
					out.writeUTF(atomicBean.getGuid());
					out.writeUTF(atomicBean.getCategory());
					out.writeUTF(atomicBean.getCorelationID());
					out.writeUTF(atomicBean.getTimeStamp());
					out.writeUTF(atomicBean.getSessionID());
					out.writeUTF(atomicBean.getMetricID());
					out.writeUTF(atomicBean.getTransactionID());
					out.writeUTF(atomicBean.getType());
					out.writeUTF(atomicBean.getData());
					out.writeUTF(atomicBean.getProjectName());
				}

				out.close();
				super.javaToNative(stream.toByteArray(), transferData);
			} catch (Exception e) {
				final String errMsg = "Exception thrown in Method " +
						"**javaToNative()** in class [ DatabaseTransfer.java ]";
				SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg,
						e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method is called while drop
	 */
	protected Object nativeToJava(TransferData transferData) {
		DatabaseBean dataBean = null;

		if (isSupportedType(transferData)) {
			final byte[] raw = (byte[]) super.nativeToJava(transferData);

			if (raw == null) {
				return null;
			}

			dataBean = new DatabaseBean();

			try {
				final ByteArrayInputStream stream = new ByteArrayInputStream(
						raw);
				final DataInputStream inPut = new DataInputStream(stream);

				String typeOfdata = inPut.readUTF();
				dataBean.setTypeofData(typeOfdata);
				// get the data from the transfer class for group and its sub
				// children
				if (typeOfdata.equals(DataObjectView.group_s)) {
					ArrayList listAllTab = new ArrayList();
					dataBean.setGroupName(inPut.readUTF());
					int tableSize = Integer.parseInt(inPut.readUTF());
					for (int count = 0; count < tableSize; count++) {
						ArrayList fieldList = new ArrayList();
						HashMap fieldType = new HashMap();
						DatabaseBean subBean = new DatabaseBean();
						subBean.setTableName(inPut.readUTF());

						int size = Integer.parseInt(inPut.readUTF());

						for (int i = 0; i < size; i++) {
							String field = inPut.readUTF();
							fieldList.add(field);
							fieldType.put(field, inPut.readUTF());
						}
						subBean.setFieldMap(fieldList);
						subBean.setFieldType(fieldType);
						listAllTab.add(subBean);
					}
					dataBean.setAllTables(listAllTab);
				}// get the data from the transfet class for table and its
				// children
				else if (typeOfdata.equals(DataObjectView.table_s)) {
					dataBean.setTableName(inPut.readUTF());
					ArrayList fieldList = new ArrayList();
					HashMap fieldType = new HashMap();
					int size = Integer.parseInt(inPut.readUTF());

					for (int i = 0; i < size; i++) {
						String field = inPut.readUTF();
						fieldList.add(field);
						fieldType.put(field, inPut.readUTF());
					}
					dataBean.setFieldMap(fieldList);
					dataBean.setFieldType(fieldType);
				}// get the data from the transfer class for field
				else if (typeOfdata.equals(DataObjectView.field_s)) {
					dataBean.setFieldName(inPut.readUTF());
					String mapping = inPut.readUTF();
					dataBean.setMappingType(mapping);
					dataBean.setDataSourceName(inPut.readUTF());
					dataBean.setTableName(inPut.readUTF());

					ArrayList fieldList = new ArrayList();
					HashMap fieldType = new HashMap();
					int size = Integer.parseInt(inPut.readUTF());

					for (int i = 0; i < size; i++) {
						String field = inPut.readUTF();
						fieldList.add(field);
						fieldType.put(field, inPut.readUTF());
						String prime = inPut.readUTF();
						dataBean.setPrimaryKey(prime.equals("true"));
					}
					dataBean.setFieldMap(fieldList);
					dataBean.setFieldType(fieldType);
				} else if (typeOfdata.equals(DataObjectView.mapAtomicType_s)) {
					AtomicMetricBean atomicBean = new AtomicMetricBean();
					atomicBean.setName(inPut.readUTF());
					atomicBean.setDescription(inPut.readUTF());
					atomicBean.setGuid(inPut.readUTF());
					atomicBean.setCategory(inPut.readUTF());
					atomicBean.setCorelationID(inPut.readUTF());
					atomicBean.setTimeStamp(inPut.readUTF());
					atomicBean.setSessionID(inPut.readUTF());
					atomicBean.setMetricID(inPut.readUTF());
					atomicBean.setTransactionID(inPut.readUTF());
					atomicBean.setType(inPut.readUTF());
					atomicBean.setData(inPut.readUTF());
					atomicBean.setProjectName(inPut.readUTF());
					dataBean.setAtomicBean(atomicBean);
				}
				inPut.close();
			} catch (IOException e) {
				final String errMsg = "Exception thrown in Method " +
						"**nativeToJava()** in class [ DatabaseTransfer.java ]";
				SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg,
						e);
				e.printStackTrace();
				return null;
			}
		}
		return dataBean;
	}
}
