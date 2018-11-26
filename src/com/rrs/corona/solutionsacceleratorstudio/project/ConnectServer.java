//
//
//package com.rrs.corona.solutionsacceleratorstudio.project;
//
//import org.eclipse.jface.action.Action;
//import org.eclipse.jface.action.IAction;
//import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.jface.viewers.ISelection;
//import org.eclipse.swt.widgets.MessageBox;
//import org.eclipse.ui.IWorkbenchWindow;
//import org.eclipse.ui.IWorkbenchWindowActionDelegate;
//
//
//
//public class ConnectServer extends Action implements IWorkbenchWindowActionDelegate 
//{
//
//	static IWorkbenchWindow window = null;
//	
//	public void dispose() 
//	{
//		
//	}
//	
//	public void init(IWorkbenchWindow window) 
//	{
//		
//		this.window = window;
//	}
//	
//	public void run(IAction action) 
//	{
//		ConnectServerDialog connectDialog = new ConnectServerDialog(window.getShell());
//		connectDialog.open();
//		if(ProjectData.connectionStatus_s)
//		{
//			MessageDialog.openInformation (window.getShell(),
//				"Connection satus", "Connection successful");
//		}
//		else
//		{
//			MessageDialog dialog = new MessageDialog(window.getShell(),"Error!",null,"Connection failed",MessageDialog.ERROR,new String[]{"ok"},1);
//		}
//	}
//	
//	public void selectionChanged(IAction action, ISelection selection) 
//	{
//		
//	}
//}
