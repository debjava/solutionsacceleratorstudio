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
package com.rrs.corona.server.sas;

import java.io.File;

import org.jboss.logging.Logger;

import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
/**
 * Created on December 1, 2005
 * @author Sreehari
 * This Class <code>BridgeHelper</code> takes the projects which are published by
 * <code>BDM</code> and publishes those projects to HotwireStudio. </br>
 *  
 */
public final class BridgeHelper 
{
	/* **************************** Instance fields *********************************** */
	transient private static final   String  BACK_SLASH_s  = "/";
	/**
	 * Constant contains folder name
	 */
	transient private static final   String  COMPOSITEEVENT_s  ="composite";
	
	/* **************************************Logger************************************ */
	/**
	 * Logger for this Class for displaying messages and Errors
	 */
	transient private final Logger			logger_			= Logger.getLogger(BridgeHelper.class );
	BridgePublisher bPublisher_ = null;
	/**
	 * Constructor
	 * @param bridge
	 */
	public BridgeHelper() 
	{
		logger_.info("Default Constructor");
		bPublisher_ = new BridgePublisher();
	}
	/**
	 *  This method is used to get the project files which are published by BDM
	 *  and publishes to HotwireStudio.
	 *
	 */
	public void publishProjects()
	{
		// This method will be called after receiving the
		// the Request from HWS for all projects
		logger_.info("Getting projects from " + Constants.PROJECT_PATH_s);
		final File allProjects = new File(Constants.PROJECT_PATH_s);
		final String[] subDir = allProjects.list();
		if(subDir.length ==0 )
		{
			logger_.info("No projects in  :"+ Constants.PROJECT_PATH_s);
		}
		else
		{
			logger_.info("publishing " + subDir.length + " projects to Solution Accelarator Studio : ");
		}
		for(int prjCount = 0 ;prjCount < subDir.length;prjCount++)
		{
			final File atomicProj = new File(Constants.PROJECT_PATH_s + BACK_SLASH_s + subDir[prjCount]);
			logger_.info((prjCount+1) +": "+subDir[prjCount]);
			if(atomicProj.isDirectory())
			{
				File atomicProjfiles = new File(Constants.PROJECT_PATH_s + BACK_SLASH_s 
						                        + subDir[prjCount] + SASConstants.PROJECT_COMPOSITE);
				final File[] atmcfiles = atomicProjfiles.listFiles(  );
				//new BridgePublisher(atmcfiles ,subDir[prjCount],ATOMIC_s); 
				bPublisher_.publishCompositeEvents(atmcfiles ,subDir[prjCount],COMPOSITEEVENT_s);
			}
		}
	}
	
	/**
	 *  This method is used to get the project files which are published by BDM
	 *  and publishes to HotwireStudio.
	 *
	 */
	public void publishProject(String projectName)
	{
		// This method will be called after receiving the
		// the request from HWS for a project
		logger_.info("Getting projects from " + Constants.PROJECT_PATH_s);
		final File allProjects = new File(Constants.PROJECT_PATH_s);
		final String[] subDir = allProjects.list();
		for(int prjCount = 0 ;prjCount < subDir.length;prjCount++)
		{
			final File atomicProj = new File(Constants.PROJECT_PATH_s + BACK_SLASH_s + subDir[prjCount]);
			if(subDir[prjCount].equalsIgnoreCase(projectName))
			{
				logger_.info("publishing the project ["+subDir[prjCount]+"]");
				if(atomicProj.isDirectory())
				{
					final File atomicProjfiles = new File(Constants.PROJECT_PATH_s + BACK_SLASH_s 
							                             + subDir[prjCount] + SASConstants.PROJECT_COMPOSITE);
					final File[] atmcfiles = atomicProjfiles.listFiles(  );
					//new BridgePublisher(atmcfiles ,subDir[prjCount],ATOMIC_s);   
					bPublisher_.publishCompositeEvents(atmcfiles ,subDir[prjCount],COMPOSITEEVENT_s);
				}
			}
		}
	}
}
