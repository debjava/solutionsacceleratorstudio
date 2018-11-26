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

package com.rrs.corona.solutionsacceleratorstudio;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewLayout;

/**
 * This is for setting the perspective for the solutions accelerator studio
 * <P>
 * This will have five different views, four TreeView and one property view.
 * </p>
 * 
 * @author Maharajan
 * 
 */
public class DatabasePerspective implements IPerspectiveFactory
{
	/**
	 * IvewLayout for database view
	 */
	transient private IViewLayout	databaseTreeLayout			= null;
	/**
	 * IviewLayout for IntermediateDataObject view
	 */
	transient private IViewLayout	dataobjectTreeLayout		= null;
	/**
	 * IviewLayout for Property window view
	 */
	transient private IViewLayout	dataobjectTreeLayout1		= null;
	/**
	 * IviewLayout for Soluitons Adapter view
	 */
	transient private IViewLayout	solutionAdapterTreeLayout	= null;

	/**
	 * This method is used to create the layout of all the views for the
	 * Solutions accelerator studio
	 */
	public void createInitialLayout( IPageLayout layout )
	{
		try
		{
			// This is for to setting the layout unfixed
			layout.setFixed( false );
			// This is for to set the layout for the Solutions Adapter view
			layout
					.addStandaloneView(
										"com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SolutionAdapterView",
										true, IPageLayout.LEFT, 0.25f, layout
												.getEditorArea( ) );
			// This is for to set the layout for the Property view
			layout
					.addStandaloneView(
										"com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectTableView",
										true, IPageLayout.BOTTOM, 0.7f, layout
												.getEditorArea( ) );
			// This is for to set the layout for the Project view
			layout
					.addStandaloneView(
										"com.rrs.corona.solutionsacceleratorstudio.project.ProjectData",
										true, IPageLayout.LEFT, 0.33f, layout
												.getEditorArea( ) );
			// This is for to set the layout for the IntermediateDataObject view
			layout
					.addStandaloneView(
										"com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView",
										true, IPageLayout.LEFT, 0.5f, layout
												.getEditorArea( ) );
			// This is to add the perspective class in the layout
			layout.setEditorAreaVisible( false );
			layout
					.addPerspectiveShortcut( "com.rrs.corona.solutionsacceleratorstudio.DatabasePerspective" );

			// set the property for Solutions Adapter view
			solutionAdapterTreeLayout = layout
					.getViewLayout( "com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectTableView" );
			solutionAdapterTreeLayout.setMoveable( true );
			solutionAdapterTreeLayout.setCloseable( true );
			// set the property for IntermediateDataobject view
			dataobjectTreeLayout = layout
					.getViewLayout( "com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectTableView" );
			dataobjectTreeLayout.setMoveable( true );
			dataobjectTreeLayout.setCloseable( true );
			// set the property for Database view
			databaseTreeLayout = layout
					.getViewLayout( "com.rrs.corona.solutionsacceleratorstudio.project.ProjectData" );
			databaseTreeLayout.setMoveable( true );
			databaseTreeLayout.setCloseable( true );
			// set the property for property window view
			dataobjectTreeLayout1 = layout
					.getViewLayout( "com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView" );
			dataobjectTreeLayout1.setMoveable( true );
			dataobjectTreeLayout1.setCloseable( true );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}

}
