/*******************************************************************************
 * @rrs_start_copyright
 * 
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved. This
 * software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Red Rabbit Software. ©
 * 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights
 * reserved. Red Rabbit Software - Development Program 5 of 15 $Id:
 * EditableTableItem.java,v 1.1.2.2 2006/04/03 15:58:27 maha Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/dataobject/Attic/EditableTableItem.java,v $
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

package com.rrs.corona.solutionsacceleratorstudio.dataobject;

import com.rrs.corona.solutionsacceleratorstudio.dataobject.*;
/**
 * This class is used to hold each row information
 * 
 * @author Maharajan
 * 
 */
public class EditableTableItem
{
	/**
	 * This string is used to hold the field name of the data object
	 */
	public String	fieldName;
	/**
	 * This string is used to hold the type of the field
	 */
	public Integer	fieldType;
	/**
	 * This string is used to hold the atomic metric property
	 */
	public String	atomicMetricProperty;
	/**
	 * This TreeParent is used to hold the selected tree item in the data object view 
	 */
	public Object	treeItem;

	/**
	 * This constructor is used to intialize the instance variables for each row
	 * in the table viewer
	 * 
	 * @param fieldName
	 *            This string contains the name of the field
	 * @param fieldType
	 *            This string contains the type of the field
	 * @param atomicMetricProperty
	 *            This string contains the atomic metric property
	 * @param treeItem
	 *             TreeParent is used to hold the selected tree item in the data object view 
	 */
	public EditableTableItem( String fieldName, Integer fieldType,
			String atomicMetricProperty, Object treeItem)
	{
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.atomicMetricProperty = atomicMetricProperty;
		this.treeItem = treeItem;
	}
}
