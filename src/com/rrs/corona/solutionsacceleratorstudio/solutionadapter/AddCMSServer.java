package com.rrs.corona.solutionsacceleratorstudio.solutionadapter;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.rrs.corona.solutionsacceleratorstudio.plugin.CreateDataSourceDialog;

public class AddCMSServer extends Action implements IWorkbenchWindowActionDelegate
{
	/**
	 * IWorkbenchWindow is a top level window in a work bench
	 */
	static IWorkbenchWindow window = null;
	/**
	 * Method to dispose the action
	 */
	public void dispose( )
	{
		// TODO Auto-generated method stub
	}

	/**
	 * Method to initialize the action delegate
	 */
	public void init(IWorkbenchWindow window)// It is an auto generated method
	{
		this.window =  window;
	}

	public void run( IAction action )
	{
		// TODO Auto-generated method stub
		AddCMSServerDialog addDialog = new AddCMSServerDialog(window.getShell());
		addDialog.open();
		
	}

	public void selectionChanged( IAction action, ISelection selection )
	{
		// TODO Auto-generated method stub
		
	}

}
