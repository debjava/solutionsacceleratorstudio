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
 * $Id: SASImages.java,v 1.2 2006/07/24 12:55:17 redrabbit Exp $ $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/SASImages.java,v $
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

package com.rrs.corona.solutionsacceleratorstudio;

import org.eclipse.swt.graphics.Image;

import com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView;
import com.rrs.corona.solutionsacceleratorstudio.plugin.DatabaseViewer;
import com.rrs.corona.solutionsacceleratorstudio.project.ProjectData;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SolutionAdapterView;

/**
 * This class contains all the image files to be displayed in the tree viewer
 * 
 * @author Maharajan
 */

public class SASImages
{
	/**
	 * This string holds the path of the icon directory
	 */
	private static final String	IMG_DIR			= SASConstants.SAS_ICONS_s;		// "./plugins/com.rrs.corona.sas_1.0.0/icons/";
	/**
	 * This string holds the path of the cms image
	 */
	public static final String	CMS_s			= IMG_DIR + "cms.gif";
	/**
	 * This string holds the path of the solutions adapter image
	 */
	public static final String	SA_s			= IMG_DIR + "sadapter.gif";
	/**
	 * This string holds the path of the composteevent image
	 */
	public static final String	COMPOSITE_s		= IMG_DIR + "cenode.gif";
	/**
	 * This string holds the path of the simpleevent image
	 */
	private static final String	SIMPLEEVENT_s	= IMG_DIR + "senode.gif";
	/**
	 * This string holds the path of the groupmetric image
	 */
	private static final String	GROUPMETRIC_s	= IMG_DIR + "grpnode.gif";
	/**
	 * This string holds the path of the atomicmetric image
	 */
	private static final String	ATOMICMETRIC_s	= IMG_DIR + "atmnode.gif";
	/**
	 * This string holds the path of the class image
	 */
	private static final String	CLASS_s			= IMG_DIR + "class.gif";
	/**
	 * This string holds the path of the field image
	 */
	private static final String	FIELD_s			= IMG_DIR + "field.gif";
	/**
	 * This string holds the path of the group image
	 */
	private static final String	GROUP_s			= IMG_DIR + "group.gif";
	/**
	 * This string holds the path of the project image
	 */
	private static final String	PROJECT_s		= IMG_DIR
														+ "showchild_mode.gif";
	/**
	 * This string holds the path of the data source image
	 */
	private static final String	DATASOURCE_s	= IMG_DIR + "datasource.gif";
	/**
	 * This string holds the path of the table image
	 */
	private static final String	TABLE_s			= IMG_DIR + "attributetype.gif";//"table.gif";
	/**
	 * This string holds the path of the column image
	 */
	private static final String	TBFIELD_s		= IMG_DIR + "attribute.gif"; //"column.gif";
	/**
	 * This string holds the path of the root image
	 */
	public static final String	ROOT_s			= IMG_DIR + "root.gif";
	/**
	 * This string holds the path of the solutions adapter root image
	 */
	public static final String	SAROOT_s		= IMG_DIR + "root.gif";

	// IMAGE OBJECTS
	/**
	 * This is of type Image for group
	 */
	private static Image		GROUP_IMG;
	/**
	 * This is of type Image for class
	 */
	private static Image		CLASS_IMG;
	/**
	 * This is of type Image for field
	 */
	private static Image		FIELD_IMG;
	/**
	 * This is of type Image for atomicmetric
	 */
	private static Image		ATOMICMETRICS_IMG;
	/**
	 * This is of type Image for groupmetric
	 */
	private static Image		GROUPMETRICS_IMG;
	/**
	 * This is of type Image for simpleevent
	 */
	private static Image		SIMPLEEVENTS_IMG;
	/**
	 * This is of type Image for compositeevent
	 */
	private static Image		COMPOSITEEVENTS_IMG;
	/**
	 * This is of type Image for project
	 */
	private static Image		PROJECT_IMG;
	/**
	 * This is of type Image for root
	 */
	private static Image		ROOT_IMG;
	/**
	 * This is of type Image for data source
	 */
	private static Image		DATASOURCE_IMG;
	/**
	 * This is of type Image for table
	 */
	private static Image		TABLE_IMG;
	/**
	 * This is of type Image for table column
	 */
	private static Image		TBFIELD_IMG;
	/**
	 * This is of type Image for table column
	 */
	private static Image		CMS_IMG;
	/**
	 * This is of type Image for table column
	 */
	private static Image		SA_IMG;
	/**
	 * This is of type Image for table column
	 */
	private static Image		DATAOBJECT_IMG;

	/**
	 * This method will return Image object for atomic metric
	 * 
	 * @return Image object
	 */
	public static Image getATOMICMETRICS_IMG( )
	{
		if( null == ATOMICMETRICS_IMG )
		{
			ATOMICMETRICS_IMG = new Image( ProjectData.viewer_s.getTree( )
					.getShell( ).getDisplay( ), ATOMICMETRIC_s );
		}
		return ATOMICMETRICS_IMG;
	}

	/**
	 * This method will return Image object for class
	 * 
	 * @return Image object
	 */
	public static Image getCLASS_IMG( )
	{
		if( null == CLASS_IMG )
		{
			CLASS_IMG = new Image( DataObjectView.viewer_s.getTree( )
					.getShell( ).getDisplay( ), CLASS_s );
		}
		return CLASS_IMG;
	}

	/**
	 * This method will return Image object for composite event
	 * 
	 * @return Image object
	 */
	public static Image getCOMPOSITEEVENTS_IMG( )
	{
		if( null == COMPOSITEEVENTS_IMG )
		{
			COMPOSITEEVENTS_IMG = new Image( ProjectData.viewer_s.getTree( )
					.getShell( ).getDisplay( ), COMPOSITE_s );
		}
		return COMPOSITEEVENTS_IMG;
	}

	/**
	 * This method will return Image object for field
	 * 
	 * @return Image object
	 */
	public static Image getFIELD_IMG( )
	{
		if( null == FIELD_IMG )
		{
			FIELD_IMG = new Image( DataObjectView.viewer_s.getTree( )
					.getShell( ).getDisplay( ), FIELD_s );
		}
		return FIELD_IMG;
	}

	/**
	 * This method will return Image object for group
	 * 
	 * @return Image object
	 */
	public static Image getGROUP_IMG( )
	{
		if( null == GROUP_IMG )
		{
			GROUP_IMG = new Image( ProjectData.viewer_s.getTree( ).getShell( )
					.getDisplay( ), GROUP_s );
		}
		return GROUP_IMG;
	}

	/**
	 * This method will return Image object for group metric
	 * 
	 * @return Image object
	 */
	public static Image getGROUPMETRICS_IMG( )
	{
		if( null == GROUPMETRICS_IMG )
		{
			GROUPMETRICS_IMG = new Image( ProjectData.viewer_s.getTree( )
					.getShell( ).getDisplay( ), GROUPMETRIC_s );
		}
		return GROUPMETRICS_IMG;
	}

	/**
	 * This method will return Image object for simple event
	 * 
	 * @return Image object
	 */
	public static Image getSIMPLEEVENTS_IMG( )
	{
		if( null == SIMPLEEVENTS_IMG )
		{
			SIMPLEEVENTS_IMG = new Image( ProjectData.viewer_s.getTree( )
					.getShell( ).getDisplay( ), SIMPLEEVENT_s );
		}
		return SIMPLEEVENTS_IMG;
	}

	/**
	 * This method will return Image object for project
	 * 
	 * @return Image object
	 */
	public static Image getPROJECT_IMG( )
	{
		if( null == PROJECT_IMG )
		{
			PROJECT_IMG = new Image( ProjectData.viewer_s.getTree( ).getShell( )
					.getDisplay( ), PROJECT_s );
		}
		return PROJECT_IMG;
	}

	/**
	 * This method will return Image object for root in Solutions Adapter view
	 * 
	 * @return Image object
	 */
	public static Image getSAROOT_IMG( )
	{
		if( null == ROOT_IMG )
		{
			ROOT_IMG = new Image( SolutionAdapterView.viewer_s.getTree( )
					.getShell( ).getDisplay( ), ROOT_s );
		}
		return ROOT_IMG;
	}

	/**
	 * This method will return Image object for root in Project view
	 * 
	 * @return Image object
	 */
	public static Image getPROJROOT_IMG( )
	{
		if( null == ROOT_IMG )
		{
			ROOT_IMG = new Image( ProjectData.viewer_s.getTree( ).getShell( )
					.getDisplay( ), ROOT_s );
		}
		return ROOT_IMG;
	}

	/**
	 * This method will return Image object for root in Data Object view
	 * 
	 * @return Image object
	 */
	public static Image getDOROOT_IMG( )
	{
		if( null == ROOT_IMG )
		{
			ROOT_IMG = new Image( DataObjectView.viewer_s.getTree( ).getShell( )
					.getDisplay( ), ROOT_s );
		}
		return ROOT_IMG;
	}

	/**
	 * This method will return Image object for root in Data Object view
	 * 
	 * @return Image object
	 */
	public static Image getDSROOT_IMG( )
	{
		if( null == ROOT_IMG )
		{
			ROOT_IMG = new Image( DatabaseViewer.viewer.getTree( ).getShell( )
					.getDisplay( ), ROOT_s );
		}
		return ROOT_IMG;
	}

	/**
	 * This method will return Image object for data source
	 * 
	 * @return Image object
	 */
	public static Image getDATASOURCE_IMG( )
	{
		if( null == DATASOURCE_IMG )
		{
			DATASOURCE_IMG = new Image( DatabaseViewer.viewer.getTree( )
					.getShell( ).getDisplay( ), DATASOURCE_s );
		}
		return DATASOURCE_IMG;
	}

	/**
	 * This method will return Image object for table
	 * 
	 * @return Image object
	 */
	public static Image getTABLE_IMG( )
	{
		if( null == TABLE_IMG )
		{
			TABLE_IMG = new Image( DatabaseViewer.viewer.getTree( ).getShell( )
					.getDisplay( ), TABLE_s );
		}
		return TABLE_IMG;
	}

	/**
	 * This method will return Image object for table columns
	 * 
	 * @return Image object
	 */
	public static Image getTBFIELD_IMG( )
	{
		if( null == TBFIELD_IMG )
		{
			TBFIELD_IMG = new Image( DatabaseViewer.viewer.getTree( )
					.getShell( ).getDisplay( ), TBFIELD_s );
		}
		return TBFIELD_IMG;
	}

	/**
	 * This method will return Image object for CMS node in the tree
	 * 
	 * @return Image object
	 */
	public static Image getCMS_IMG( )
	{
		if( null == CMS_IMG )
		{
			CMS_IMG = new Image( SolutionAdapterView.viewer_s.getTree( )
					.getShell( ).getDisplay( ), CMS_s );
		}
		return CMS_IMG;
	}

	/**
	 * This method will return Image object for Dataobject node in the tree
	 * 
	 * @return Image object
	 */
	public static Image getDATAOBJECT_IMG( )
	{
		if( null == DATAOBJECT_IMG )
		{
			DATAOBJECT_IMG = new Image( DataObjectView.viewer_s.getTree( )
					.getShell( ).getDisplay( ), GROUP_s );
		}
		return DATAOBJECT_IMG;
	}

	/**
	 * This method will return Image object for Solutions Adapter node in the
	 * tree
	 * 
	 * @return Image object
	 */
	public static Image getSA_IMG( )
	{
		if( null == SA_IMG )
		{
			SA_IMG = new Image( SolutionAdapterView.viewer_s.getTree( )
					.getShell( ).getDisplay( ), SA_s );
		}
		return SA_IMG;
	}

}
