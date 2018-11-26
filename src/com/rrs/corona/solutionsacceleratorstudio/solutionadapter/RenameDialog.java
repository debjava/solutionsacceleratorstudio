
/******************************************************************************
 * @rrs_start_copyright
 *
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved.
 * This software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of
 * the license agreement you entered into with Red Rabbit Software.
 � 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights reserved.
 Red Rabbit Software - Development Program 5 of 15
 *$Id: RenameDialog.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/RenameDialog.java,v $
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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class RenameDialog extends Action implements IWorkbenchWindowActionDelegate
{

	/**
	 * IWorkbenchWindow is a top level window in a work bench
	 */
	static IWorkbenchWindow window = null;
	/**
	 * Method to dispos the action
	 */
	public void dispose( )
	{
		// TODO Auto-generated method stub
	}

	/**
	 * Method to initialize the action delegate
	 */
	public void init(IWorkbenchWindow window) 
	{
		this.window = window;		
	}

	public void run( IAction action )
	{
		// TODO Auto-generated method stub
		RenameCMSServerDialog addDialog = new RenameCMSServerDialog(window.getShell());
		addDialog.open();
	}

	public void selectionChanged( IAction action, ISelection selection )
	{
		// TODO Auto-generated method stub
		
	}

	
	
}
