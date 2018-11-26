///** @rrs_start_copyright
// *
// * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved.
// * This software is the confidential and proprietary information of Red Rabbit
// * Software, Inc. ("Confidential Information"). You shall not disclose such
// * Confidential Information and shall use it only in accordance with the terms of
// * the license agreement you entered into with Red Rabbit Software.
// © 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights reserved.
// Red Rabbit Software - Development Program 5 of 15
// *
// * $Id: ConnectServerDialog.java,v 1.2 2006/08/03 08:30:56 redrabbit Exp $
// * $Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/project/ConnectServerDialog.java,v $
// *
// * @rrs_end_copyright
// ******************************************************************************/
///******************************************************************************
// * @rrs_start_disclaimer
// *
// * The contents of this file are subject to the Red Rabbit Software Inc. Corona License
// * ("License"); You may not use this file except in compliance with the License.
// * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
// * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
// * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// * DISCLAIMED. IN NO EVENT SHALL THE RED RABBIT SOFTWARE INC. OR
// * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
// * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
// * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
// * SUCH DAMAGE.
// *
// * @rrs_end_disclaimer
// ******************************************************************************/
//
//
//package com.rrs.corona.solutionsacceleratorstudio.project;
//
//import java.util.StringTokenizer;
//
//import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.jface.dialogs.TitleAreaDialog;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.ModifyEvent;
//import org.eclipse.swt.events.ModifyListener;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.layout.RowLayout;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Combo;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Control;
//import org.eclipse.swt.widgets.Group;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.widgets.Text;
//
//import com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView;
//import com.rrs.corona.solutionsacceleratorstudio.messagehandler.BDMSASMessageHandler;
//import com.rrs.corona.solutionsacceleratorstudio.messagehandler.ConfigReader;
//import com.rrs.corona.solutionsacceleratorstudio.messagehandler.SASServerInforReader;
//
//public class ConnectServerDialog extends TitleAreaDialog
//{
//
//	private Button okButton;
//	private Button cancelButton;
//	private Label serverLabel ;
//	private Label serverIP ;
//	private Text serverIPText ;
//	private Label serverPort ;
//	private Text serverPortText ;
//	private Combo serverCombo;
//	private Shell shell;
//	public static BDMSASMessageHandler msgHandler;
//	public ConnectServerDialog(Shell parentShell)
//	{
//		super(parentShell);
//		shell = parentShell;
//		
//	}
//	/**
//	 * This method is used to call the create() method in the super class
//	 * and set the title for the dialog box and set the message for the dialog box.It overrides the supper class method
//	 */
//	public void create()
//	{
//		super.create();
//		setTitle("Server Dialog");
//		setMessage("Dialog box to connect remote server");
//	}
//	/**
//	 * This method is also overrides the super class method used to create 
//	 * UI with the help of the Composite from the parameter
//	 * 
//	 * @param parent this of type Composite
//	 * @return Control which contains the Composite type 
//	 */
//	public Control createDialogArea(Composite parent)
//	{
//		parent.getShell().setText("Server Dialog");
//		Composite child = new Composite(parent,SWT.NULL);
//		//first group
//		Group grp = new Group(child,SWT.NONE);
//		grp.setLayout( new GridLayout( 3, true ) );		
//		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
//		createLabelG1(grp,"Server Name",gridData);
//		createComboG1(grp,gridData);
//		grp.setBounds(30,10,370,50);	
//		//secong group
//		final Group grp1 = new Group(child,SWT.NONE);
//		grp1.setLayout( new GridLayout( 3, true ) );
//		grp1.setText("Server information");
//		GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
//		createLabelIPG2(grp1,"IP",gridData1);
//		createTextlIPG2(grp1,gridData1);
//		createLabelPortG2(grp1,"PORT",gridData1);
//		createTextlPortG2(grp1,gridData1);
//		grp1.setBounds(30,70,370,80);
//		return parent;
//	}
//	
//	public void createLabelG1(Composite parent,String label,GridData gridData)
//	{
//		serverLabel= new Label(parent,SWT.NULL);
//		serverLabel.setLayoutData(gridData);
//		serverLabel.setText(label);
//	}
//	public void createComboG1(Composite parent,GridData gridData)
//	{
//		serverCombo = new Combo(parent,SWT.BORDER | SWT.READ_ONLY);
//		gridData = new GridData(GridData.FILL_HORIZONTAL);
//		gridData.horizontalSpan = 2;
//		serverCombo.setLayoutData(gridData);
//		SASServerInforReader read = new SASServerInforReader();
//		String []data = read.sasReaderServername();
//		if(null != data)
//		{
//			serverCombo.setItems(data);
//			//serverCombo.setLayoutData(gridData);
//		}
//	}
//	public void createLabelIPG2(Composite parent,String label,GridData gridData)
//	{
//		serverIP= new Label(parent,SWT.NULL);
//		serverIP.setLayoutData(gridData);
//		serverIP.setText(label);
//	}
//	public void createTextlIPG2(Composite parent,GridData gridData)
//	{
//		serverIPText= new Text(parent,SWT.BORDER | SWT.READ_ONLY);
//		gridData = new GridData(GridData.FILL_HORIZONTAL);
//		gridData.horizontalSpan = 2;
//		serverIPText.setLayoutData(gridData);
//	}
//	public void createLabelPortG2(Composite parent,String label,GridData gridData)
//	{
//		serverPort= new Label(parent,SWT.NULL);
//		serverPort.setLayoutData(gridData);
//		serverPort.setText(label);
//	}
//	public void createTextlPortG2(Composite parent,GridData gridData)
//	{
//		serverPortText= new Text(parent,SWT.BORDER | SWT.READ_ONLY);
//		gridData = new GridData(GridData.FILL_HORIZONTAL);
//		gridData.horizontalSpan = 2;
//		serverPortText.setLayoutData(gridData);
//		setListener();
//	}
//	/**
//	 * 
//	 * This method is used to create a buttons for the UI. It is override from the super class 
//	 */
//	protected void createButtonsForButtonBar(final Composite parent)
//	{
//		
//		try
//		{
//			okButton = createButton(parent,999,"OK",true);
//			okButton.setEnabled(false);			
//			cancelButton = createButton(parent,999,"Cancel",true);
//			okButton.addSelectionListener(new SelectionAdapter()
//					{
//				public void widgetSelected(SelectionEvent e) 
//				{
//					ProjectData.serverName_s = serverCombo.getText(  ).toString(  );
//					connectServer(serverCombo.getText(  ).toString(  ));
//					parent.getShell().close();
//				}
//				
//					});
//			cancelButton.addSelectionListener(new SelectionAdapter()
//					{
//				public void widgetSelected(SelectionEvent e) 
//				{
//					parent.getShell().close();	
//				}		
//				
//					});
//			
//		}catch(Exception e)
//		{
//			
//			e.printStackTrace();
//		}
//	}
//
//	
//	public void setListener()
//	{
//		serverCombo.addModifyListener(new ModifyListener(  )
//				{
//			public void modifyText( ModifyEvent event )
//			{
//				if(serverCombo.getText(  ).toString(  ).length(  ) != 0 )
//				{
//					okButton.setEnabled(true);
//					connectServerIP(serverCombo.getText(  ).toString(  ));
//				}
//				else
//				{
//					okButton.setEnabled(false);
//				}
//			}	
//			});
//	}
//	
//	
//	public void connectServer(String serverName)
//	{
//		String serverIP = new SASServerInforReader().sasConfigReader(serverName);
//		try{
//		if(null != serverIP)
//		{
//			StringTokenizer tokenize = new StringTokenizer(serverIP,":");
//			serverIP = tokenize.nextToken();
//			String serverPort = tokenize.nextToken();
//			String tokenize[] = serverIP.split("[:]");
//			serverIP = tokenize[0];
//			String serverPort = tokenize[1];
//			ConfigReader readerConfig = new ConfigReader();
//			readerConfig.init();
//			readerConfig.startParsing();
//			//BDMSASMessageHandler handler = new BDMSASMessageHandler(serverIP,serverPort,"");
//			//msgHandler = handler;
//			ProjectData.connectionStatus_s  = true;
//		}
//		}catch(Exception e)
//		{
//			ProjectData.connectionStatus_s  = false;
//			MessageDialog.openError(shell,"Connection Status","Connectin failed");
//		}
//	}
//	
//	
//
//	
//	public void connectServerIP(String serverName)
//	{
//		String serverIP = new SASServerInforReader().sasConfigReader(serverName);
//		if(null != serverIP)
//		{
//			String tokenize[] = serverIP.split("[:]");
//			serverIP = tokenize[0];
//			String serverPort = tokenize[1];
//			serverIPText.setText(serverIP);
//			serverPortText.setText(serverPort);
//		}
//	}
//	
//}
