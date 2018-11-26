/******************************************************************************
 * @rrs_start_copyright
 *
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved.
 * This software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of
 * the license agreement you entered into with Red Rabbit Software.
 *
 * 
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
package com.rrs.corona.solutionsacceleratorstudio.plugin;
/**
 * 
 * @author Debadatta Mishra
 *
 */
public class TreeFieldBean 
{
	/**
	 * Table field name
	 */
	String fieldName;
	/**
	 * Data type of the table field
	 */
	String fieldDataType;
	/**
	 * Type of the field
	 */
	String fieldType;
	boolean isPrimaryKey;
	/**
	 * 
	 * @return data type of the field
	 */
	public String getFieldDataType() 
	{
		return fieldDataType;
	}
	/**
	 * 
	 * @param fieldDataType
	 */
	public void setFieldDataType(String fieldDataType) 
	{
		this.fieldDataType = fieldDataType;
	}
	/**
	 * 
	 * @return field name
	 */
	public String getFieldName() 
	{
		return fieldName;
	}
	/**
	 * 
	 * @param fieldName
	 */
	public void setFieldName(String fieldName) 
	{
		this.fieldName = fieldName;
	}
	/**
	 * 
	 * @return field type
	 */
	public String getFieldType() 
	{
		return fieldType;
	}
	/**
	 * 
	 * @param fieldType
	 */
	public void setFieldType(String fieldType) 
	{
		this.fieldType = fieldType;
	}
	/**
	 * @return Returns the isPrimaryKey.
	 */
	public boolean isPrimaryKey( )
	{
		return isPrimaryKey;
	}
	/**
	 * @param isPrimaryKey The isPrimaryKey to set.
	 */
	public void setPrimaryKey( boolean isPrimaryKey )
	{
		this.isPrimaryKey = isPrimaryKey;
	}

}
