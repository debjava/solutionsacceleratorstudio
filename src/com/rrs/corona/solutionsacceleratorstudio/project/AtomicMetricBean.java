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
 * AtomicMetricBean.java,v 1.3 2006/04/25 05:14:15 maha Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/project/AtomicMetricBean.java,v $
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

package com.rrs.corona.solutionsacceleratorstudio.project;

public class AtomicMetricBean
{
	/**
	 * variable that holds the name of the atomic metric
	 */
	private String	name			= null;

	/**
	 * variable that holds the description of the atomic metric
	 */
	private String	description		= null;

	/**
	 * variable that holds the id of the atomic metric
	 */
	private String	metricID		= null;

	/**
	 * variable that holds the category of the atomic metric
	 */
	private String	category		= null;

	/**
	 * variable that holds the guid of the atomic metric
	 */
	private String	guid			= null;

	/**
	 * variable that holds the time stamp of the atomic metric
	 */
	private String	timeStamp		= null;

	/**
	 * variable that holds the type of the atomic metric
	 */
	private String	type			= null;

	/**
	 * variable that holds the data of the atomic metric
	 */
	private String	data			= null;

	/**
	 * variable that holds the correlation ID of the atomic metric
	 */
	private String	corelationID	= null;

	/**
	 * variable that holds the transaction ID of the atomic metric
	 */
	private String	transactionID	= null;

	/**
	 * variable that holds the session ID of the atomic metric
	 */
	private String	sessionID		= null;
	/**
	 * variable that holds the projectName of the atomic metric
	 */
	private String	projectName		= null;

	/**
	 * This method is used to get the category of atomicmetric
	 * @return string which contains atmomicmetric category
	 */
	public String getCategory( )
	{
		return category;
	}
	/**
	 * This method is used to set the category of atomicmetric
	 * @param category This string contians the atomicmetric category
	 */
	public void setCategory( String category )
	{
		this.category = category;
	}
	/**
	 * This method is used to get the correlationID of atomicmetric
	 * @return string which contains the atomicmetric correlationID
	 */
	public String getCorelationID( )
	{
		return corelationID;
	}
	/**
	 * This method is used to set the correlationID of atomicmetric
	 * @param corelationID This string contians the correlationID
	 */
	public void setCorelationID( String corelationID )
	{
		this.corelationID = corelationID;
	}
	/**
	 * This method is used to get the data of the atomicmetric
	 * @return string contains the atomicmetric data
	 */
	public String getData( )
	{
		return data;
	}
	/**
	 * This method is used to set the data of atomicmetric
	 * @param data This string contains the atomicmetric data
	 */
	public void setData( String data )
	{
		this.data = data;
	}
	/**
	 * This method is used to get the description of the atomicmetric
	 * @return stirng which contains the atomicmetric description
	 */
	public String getDescription( )
	{
		return description;
	}
	/**
	 * This method is used to set the description of the atomicmetric
	 * @param description This string contiains the atomicmetric description
	 */
	public void setDescription( String description )
	{
		this.description = description;
	}
	/**
	 * This method is used to get the GUID of the atomicmetric
	 * @return string which contians the atomicmetric GUID
	 */
	public String getGuid( )
	{
		return guid;
	}
	/**
	 * This method is used to set the GUID of the atomicmetric
	 * @param guid This string contians the atomicmetric GUID
	 */
	public void setGuid( String guid )
	{
		this.guid = guid;
	}
	/**
	 * This method is used tog et the ID of the atomicmetric
	 * @return string which contains the atomicmetric ID
	 */
	public String getMetricID( )
	{
		return metricID;
	}
	/**
	 * This method is used to set the ID of the atomicmetric
	 * @param metricID This string contains the atomicemetric ID
	 */
	public void setMetricID( String metricID )
	{
		this.metricID = metricID;
	}
	/**
	 * This method is used to get the Name of the atomicmetric
	 * @return stirng which contians the atomicmetric Name
	 */
	public String getName( )
	{
		return name;
	}
	/**
	 * This method is used to se tht Name of the atomicmetric
	 * @param name This string contains the atomicmetric Name
	 */
	public void setName( String name )
	{
		this.name = name;
	}
	/**
	 * This method is used to get the sessionID of the atomicmetric 
	 * @return stirng which contains the atomicmetric sessionID
	 */
	public String getSessionID( )
	{
		return sessionID;
	}
	/**
	 * This method is used to set the sessionID of the atomicmetric
	 * @param sessionID This string contains the atomicmetric
	 */
	public void setSessionID( String sessionID )
	{
		this.sessionID = sessionID;
	}
	/**
	 * This method is used to get the TimeStamp of the atomicmetric
	 * @return stirng which contians the atomicmetric TimeStamp
	 */
	public String getTimeStamp( )
	{
		return timeStamp;
	}
	/**
	 * This method is used to set the TimeStamp of the atomicmetric
	 * @param timeStamp This string contains the atomicmetric TimeStamp
	 */
	public void setTimeStamp( String timeStamp )
	{
		this.timeStamp = timeStamp;
	}
	/**
	 * This method is used to get the TransactionID of the atomicmetric
	 * @return string which contains the atomicmetric TransactionID
	 */
	public String getTransactionID( )
	{
		return transactionID;
	}
	/**
	 * This method is used to set the TransactionID of the atomicmetric
	 * @param transactionID This string contains the atomicmetric TransactionID
	 */
	public void setTransactionID( String transactionID )
	{
		this.transactionID = transactionID;
	}
	/**
	 * This method is used to get the Type of the atomicmetric data
	 * @return string which contains the Type of the atomicmetric data
	 */
	public String getType( )
	{
		return type;
	}
	/**
	 * This method is used to set the Type of the atomicmetric data
	 * @param type This stirng contains the Type of the atomicmetric data
	 */
	public void setType( String type )
	{
		this.type = type;
	}
	/**
	 * This method is used to get the ProjectName
	 * @return string which contains the ProjectName
	 */
	public String getProjectName( )
	{
		return projectName;
	}
	/**
	 * This method is used to set the ProjectName
	 * @param projectName This string contains the ProjectName
	 */
	public void setProjectName( String projectName )
	{
		this.projectName = projectName;
	}

}
