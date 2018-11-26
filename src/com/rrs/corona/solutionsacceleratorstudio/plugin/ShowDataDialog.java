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

package com.rrs.corona.solutionsacceleratorstudio.plugin;

import java.util.ArrayList;
import java.util.logging.Logger;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

/**
 * 
 * @author Debadatta Mishra
 * 
 */
public class ShowDataDialog extends TitleAreaDialog
{
	private transient ArrayList	dataList	= new ArrayList( );

	private transient String	labelName	= null;

	protected Logger			logger		= Logger
													.getLogger( "ShowDataDialog.class" );

	public ShowDataDialog( final Shell parentShell,
			final ArrayList displayList, final String dataName )
	{
		super( parentShell );
		dataList = displayList;
		labelName = dataName;
	}

	public void create( )
	{
		super.create( );
		setTitle( "Solution Accelerator Studio" );
		setMessage( labelName );
	}

	public Control createDialogArea( Composite parent )
	{
		parent.getShell( ).setText( "Display data " );
		try
		{
			createWorkArea( parent );
			return parent;
		}
		catch( Exception ex )
		{
			final String errMsg = "Exception thrown in Method " +
					"**createDialogArea()** in Class [ ShowDataDialog.java ]";
			SolutionsacceleratorstudioPlugin.getDefault( )
					.logError( errMsg, ex );
			ex.printStackTrace( );
		}
		return parent;
	}

	private void createWorkArea( final Composite parent )
	{
		Composite area = new Composite( parent, SWT.NULL );
		Composite firstGroup = new Composite( area, SWT.NONE ); // 1st group
		GridData gdData1 = new GridData( GridData.FILL_HORIZONTAL );// It should
																	// be
																	// initialized
																	// first
		firstGroup.setLayout( new GridLayout( 3, true ) ); // lay out
		GridData gridData = new GridData( );
		createLabel( firstGroup, "", gdData1 );
		createLabel( firstGroup, "", gdData1 );// empty label
		createLabel( firstGroup, "", gdData1 );// empty label
		createDisplayDataList( firstGroup, gdData1 );
		firstGroup.setBounds( 25, 25, 380, 120 );
	}

	private void createLabel( final Composite firstGroup, final String text,
			GridData gridData )
	{
		final Label labelName = new Label( firstGroup, SWT.LEFT ); // It should
																	// be
																	// initialized
																	// first
		labelName.setText( text ); // Label Name of the test field to be
									// displayed
		labelName.setLayoutData( gridData );
	}

	private void createDisplayDataList( final Composite firstGroup,
			GridData gdData1 )
	{
		List listOfData = new List( firstGroup, SWT.BORDER | SWT.V_SCROLL ); // for
																				// showing
																				// data
																				// in a
																				// list
		listOfData.setBounds( 25, 25, 380, 120 );
		gdData1 = new GridData( GridData.FILL_HORIZONTAL );// It should be
															// initialized first
		// gdData1.horizontalSpan = 2;
		gdData1.horizontalSpan = 3;

		GridData gridData = new GridData( GridData.FILL_HORIZONTAL );
		gridData.verticalSpan = 3;

		for( int i = 0; i < dataList.size( ); i++ )
		{
			listOfData.add( ( String ) dataList.get( i ) );
			logger.info( "Data Names =======>>>>>>  " + dataList.get( i ) );
		}
		//listOfData.setBounds(25, 25, 380, 120);	
		listOfData.setLayoutData( gdData1 );
	}

}
