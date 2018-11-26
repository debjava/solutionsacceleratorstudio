/******************************************************************************
 * @rrs_start_copyright
 *
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved.
 * This software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of
 * the license agreement you entered into with Red Rabbit Software.
 *
 * 
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
package com.rrs.corona.solutionsacceleratorstudio.plugin;
/**
 * @author Debadatta Mishra
 */
import java.util.ArrayList;
import java.util.logging.Logger;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.TreeItem;

import com.sun.org.apache.xpath.internal.axes.ChildTestIterator;

public class FindItem 
{
	/**
	 * Logger to log
	 */
	Logger logger = Logger.getLogger("FindItem.class");
	boolean dataSourceFlag = false;
	/**
	 * Method to find the String with no condition
	 * @param textToFind
	 * @param selectedItemTree
	 * @param selectedTreeObjectName
	 */	
	public void simpleSearchUp(String textToFind,TreeObject selectedItemTree,String selectedTreeObjectName)// search up the word with no condition
	{
		DatabaseViewer.treeList.clear();// It is a static variable which maintains the list of found tree object
		TreeParent invisibleParent = (TreeParent)DatabaseViewer.viewer.getInput();
		TreeObject[] mainNode_TreeObject = invisibleParent.getChildren();
		TreeParent local_treeParent = (TreeParent)mainNode_TreeObject[0];
		TreeObject[] mainChildren_treeObject = (TreeObject[]) local_treeParent.getChildren();
		int counter = getSelectedTreeItemIndex(local_treeParent,selectedTreeObjectName);
		
		for(int a=0;a<counter;a++)
		{
			String tName1 = mainChildren_treeObject[a].getName().toLowerCase();// toLowerCase
			TreeParent local3_treeParent = (TreeParent) mainChildren_treeObject[a];
			TreeObject[] localSubChildren_treeObject = (TreeObject[])local3_treeParent.getChildren();
			if(tName1.contains(textToFind))
			{
				DatabaseViewer.treeList.add(mainChildren_treeObject[a]);
			}
			else
			{
				for(int b=0;b<localSubChildren_treeObject.length;b++)
				{
					String tName2 = localSubChildren_treeObject[b].getName().toLowerCase();// toLowerCase
					if(tName2.contains(textToFind))
					{
						DatabaseViewer.treeList.add(localSubChildren_treeObject[b]);
					}
					TreeParent local4_treeParent = (TreeParent)localSubChildren_treeObject[b];
					TreeObject[] localInnerChildren_treeObject = (TreeObject[])local4_treeParent.getChildren();
					
					for(int c=0;c<localInnerChildren_treeObject.length;c++)
					{
						String tempStr = localInnerChildren_treeObject[c].getName().toLowerCase();// toLowerCase
						String tempString[] = tempStr.split("   ::   ");
						String localFirstString = tempString[0];
						if(localFirstString.contains(textToFind))
						{
							DatabaseViewer.treeList.add(localInnerChildren_treeObject[c]);
						}
					}
				}
			}	
		}// END OF first for loop
		
		TreeObject main = (TreeObject)DatabaseViewer.treeList.get(0);// first found item
		DatabaseViewer.viewer.expandToLevel(main,1);// First found item should be selected and visible to the user
	}
	
	/**
	 * Method to  search the String with MatchCase condition
	 * @param textToFind
	 * @param selectedItemTree
	 * @param selectedTreeObjectName
	 */
	
	public void searchUpWithCase(String textToFind,TreeObject selectedItemTree,String selectedTreeObjectName)// search up the string with match case condition
	{
		DatabaseViewer.treeList.clear();
		TreeParent invisibleParent = (TreeParent)DatabaseViewer.viewer.getInput();
		TreeObject[] mainNode_treeObject = invisibleParent.getChildren();
		logger.info(" MAIN NODE --------------   "+mainNode_treeObject[0].getName());// it is dataSources
		TreeParent local2_treeParent = (TreeParent)mainNode_treeObject[0];
		TreeObject[] mainChildren_treeObject = (TreeObject[]) local2_treeParent.getChildren();
		int counter = getSelectedTreeItemIndex(local2_treeParent,selectedTreeObjectName);
		//for(int a=counter-1;a<counter;a++)
		for(int a=0;a<counter;a++)
		{
			String tempName_String = mainChildren_treeObject[a].getName();
			TreeParent local3_treeParent = (TreeParent) mainChildren_treeObject[a];
			TreeObject[] localSubChildren_treeObject = (TreeObject[])local3_treeParent.getChildren();
			
			if(tempName_String.contains(textToFind))
			{
				DatabaseViewer.treeList.add(mainChildren_treeObject[a]);
			}
			else
			{
				for(int b=0;b<localSubChildren_treeObject.length;b++)
				{
					String tempName2_String = localSubChildren_treeObject[b].getName();
					if(tempName2_String.contains(textToFind))
					{
						DatabaseViewer.treeList.add(localSubChildren_treeObject[b]);
					}
					TreeParent local4_treeParent = (TreeParent)localSubChildren_treeObject[b];
					TreeObject[] localInnerChildren_treeObject = (TreeObject[])local4_treeParent.getChildren();
					
					for(int c=0;c<localInnerChildren_treeObject.length;c++)
					{
						String tempStr = localInnerChildren_treeObject[c].getName();
						String tempString[] = tempStr.split("   ::   ");
						String localFirstString = tempString[0];
						
						if(localFirstString.contains(textToFind))
						{
							DatabaseViewer.treeList.add(localInnerChildren_treeObject[c]);
						}
					}
				}
			}	
		}// End of first for loop
		TreeObject main_treeObject = (TreeObject)DatabaseViewer.treeList.get(0);// First found item
		DatabaseViewer.viewer.expandToLevel(main_treeObject,1);// First found item should be selected and visible to the user
		
	}
	/**
	 * Method to search the String with MatchWholeWord condition
	 * @param textToFind
	 * @param selectedItemTree
	 * @param selectedTreeObjectName
	 */
	
	public void searchUpWithWholeWord(String textToFind,TreeObject selectedItemTree,String selectedTreeObjectName)// search up the word with wholeword condition
	{
		DatabaseViewer.treeList.clear();
		TreeParent invisibleParent = (TreeParent)DatabaseViewer.viewer.getInput();
		TreeObject[] mainNode_treeObject = invisibleParent.getChildren();
		TreeParent local2_treeParent = (TreeParent)mainNode_treeObject[0];
		TreeObject[] mainChildren_treeObject = (TreeObject[]) local2_treeParent.getChildren();
		int counter = getSelectedTreeItemIndex(local2_treeParent,selectedTreeObjectName);
		
		for(int a=0;a<counter;a++)
		{
			String temp_Name_String = mainChildren_treeObject[a].getName();
			TreeParent local3_treeParent = (TreeParent) mainChildren_treeObject[a];
			TreeObject[] localSubChildren_treeObject = (TreeObject[])local3_treeParent.getChildren();
			
			if(temp_Name_String.equalsIgnoreCase(textToFind))
			{
				DatabaseViewer.treeList.add(mainChildren_treeObject[a]);
			}
			else
			{
				for(int b=0;b<localSubChildren_treeObject.length;b++)
				{
					String tempName2 = localSubChildren_treeObject[b].getName();
					if(tempName2.equalsIgnoreCase(textToFind))
					{
						DatabaseViewer.treeList.add(localSubChildren_treeObject[b]);
					}
					TreeParent local4_treeParent = (TreeParent)localSubChildren_treeObject[b];
					TreeObject[] localInnerChildren_treeObject = (TreeObject[])local4_treeParent.getChildren();
					
					for(int c=0;c<localInnerChildren_treeObject.length;c++)
					{
						String tempStr = localInnerChildren_treeObject[c].getName();
						String tempString[] = tempStr.split("   ::   ");
						String localFirstString = tempString[0];
						
						if(localFirstString.equalsIgnoreCase(textToFind))
						{
							DatabaseViewer.treeList.add(localInnerChildren_treeObject[c]);
						}
					}
				}
			}	
		}// End of first for loop
		TreeObject main_treeObject = (TreeObject)DatabaseViewer.treeList.get(0);// First Item
		DatabaseViewer.viewer.expandToLevel(main_treeObject,1);// First found item should be selected
		
	}
	
	/**
	 * Method to  serachup the tree item the String if both MatchCase and MatchWholeWord are selected
	 * @param textToFind
	 * @param selectedItemTree
	 * @param selectedTreeObjectName
	 */
	
	public void searchUpBothCases(String textToFind,TreeObject selectedItemTree,String selectedTreeObjectName)// search up the word when both match case and matchwholeword are selected
	{
		DatabaseViewer.treeList.clear();
		
		TreeParent invisibleParent = (TreeParent)DatabaseViewer.viewer.getInput();
		TreeObject[] mainNode_treeObject = invisibleParent.getChildren();
		TreeParent local2_treeParent = (TreeParent)mainNode_treeObject[0];
		TreeObject[] mainChildren_treeObject = (TreeObject[]) local2_treeParent.getChildren();
		int counter = getSelectedTreeItemIndex(local2_treeParent,selectedTreeObjectName);
		
		for(int a=0;a<counter;a++)
		{
			String tempName1_String = mainChildren_treeObject[a].getName();
			TreeParent local3_treeParent = (TreeParent) mainChildren_treeObject[a];
			TreeObject[] localSubChildren_treeObject = (TreeObject[])local3_treeParent.getChildren();
			
			if(tempName1_String.equals(textToFind))
			{
				DatabaseViewer.treeList.add(mainChildren_treeObject[a]);
			}
			else
			{
				for(int b=0;b<localSubChildren_treeObject.length;b++)
				{
					String tempName2_String = localSubChildren_treeObject[b].getName();
					if(tempName2_String.equals(textToFind))
					{
						DatabaseViewer.treeList.add(localSubChildren_treeObject[b]);
					}
					
					TreeParent local4_treeParent = (TreeParent)localSubChildren_treeObject[b];
					TreeObject[] localInnerChildren_treeObject = (TreeObject[])local4_treeParent.getChildren();
					
					for(int c=0;c<localInnerChildren_treeObject.length;c++)
					{
						String tempStr = localInnerChildren_treeObject[c].getName();
						String tempString[] = tempStr.split("   ::   ");
						String localFirstString = tempString[0];
						
						if(localFirstString.equals(textToFind))
						{
							DatabaseViewer.treeList.add(localInnerChildren_treeObject[c]);
						}
					}
				}
			}	
		}// End of first for loop
		TreeObject main_treeobject = (TreeObject)DatabaseViewer.treeList.get(0);// first found Item
		DatabaseViewer.viewer.expandToLevel(main_treeobject,1);// First found item should be selected and visible to the user
		
	}
	/**
	 * Method to search down the tree item when no condition is specified
	 * @param textToFind
	 * @param selectedItemTree
	 * @param selectedTreeObjectName
	 */
	public void simpleSearchDown(String textToFind,TreeObject selectedItemTree,String selectedTreeObjectName)// sear down the word when nothing is selected
	{
		DatabaseViewer.treeList.clear();
		TreeParent invisibleParent = (TreeParent)DatabaseViewer.viewer.getInput();
		TreeObject[] mainNode_treeObject = invisibleParent.getChildren();
		TreeParent local2_treeParent = (TreeParent)mainNode_treeObject[0];
		TreeObject[] mainChildren_treeObject = (TreeObject[]) local2_treeParent.getChildren();
		int counter = getSelectedTreeItemIndex(local2_treeParent,selectedTreeObjectName);
		
		for(int a=counter;a<mainChildren_treeObject.length;a++)
		{
			String tempName1_String = mainChildren_treeObject[a].getName().toLowerCase();
			TreeParent local3_treeParent = (TreeParent) mainChildren_treeObject[a];
			TreeObject[] localSubChildren_treeObject = (TreeObject[])local3_treeParent.getChildren();
			
			if(tempName1_String.contains(textToFind))
			{
				DatabaseViewer.treeList.add(mainChildren_treeObject[a]);
			}
			else
			{
				for(int b=0;b<localSubChildren_treeObject.length;b++)
				{
					String tempName2 = localSubChildren_treeObject[b].getName().toLowerCase();
					if(tempName2.contains(textToFind))
					{
						DatabaseViewer.treeList.add(localSubChildren_treeObject[b]);
					}
					TreeParent local4_treeParent = (TreeParent)localSubChildren_treeObject[b];
					TreeObject[] localInnerChildren_treeObject = (TreeObject[])local4_treeParent.getChildren();
					
					for(int c=0;c<localInnerChildren_treeObject.length;c++)
					{
						String tempStr = localInnerChildren_treeObject[c].getName().toLowerCase();
						String tempString[] = tempStr.split("   ::   ");
						String localFirstString = tempString[0];
						
						if(localFirstString.contains(textToFind))
						{
							DatabaseViewer.treeList.add(localInnerChildren_treeObject[c]);
						}
					}
				}
			}	
		}// End of first for loop
		TreeObject main = (TreeObject)DatabaseViewer.treeList.get(0);// First found item
		DatabaseViewer.viewer.expandToLevel(main,1);// first found item should be selected and should be visible to the user
		
	}
	
	/**
	 * Method to search down the tree item when a Match case is specified
	 * @param textToFind
	 * @param selectedItemTree
	 * @param selectedTreeObjectName
	 */
	public void searchDownWithCase(String textToFind,TreeObject selectedItemTree,String selectedTreeObjectName)// Method to serach down the word when match case is selected
	{
		DatabaseViewer.treeList.clear();
		TreeParent invisibleParent = (TreeParent)DatabaseViewer.viewer.getInput();
		TreeObject[] mainNode_treeObject = invisibleParent.getChildren();
		TreeParent local2_treeParent = (TreeParent)mainNode_treeObject[0];
		TreeObject[] mainChildren_treeObject = (TreeObject[]) local2_treeParent.getChildren();
		int counter = getSelectedTreeItemIndex(local2_treeParent,selectedTreeObjectName);
		
		for(int a=counter;a<mainChildren_treeObject.length;a++)
		{
			String tempName1_String = mainChildren_treeObject[a].getName();
			TreeParent local3_treeParent = (TreeParent) mainChildren_treeObject[a];
			TreeObject[] localSubChildren_treeObject = (TreeObject[])local3_treeParent.getChildren();
			
			if(tempName1_String.contains(textToFind))
			{
				DatabaseViewer.treeList.add(mainChildren_treeObject[a]);
			}
			else
			{
				for(int b=0;b<localSubChildren_treeObject.length;b++)
				{
					String tempName2_String = localSubChildren_treeObject[b].getName();
					if(tempName2_String.contains(textToFind))
					{
						DatabaseViewer.treeList.add(localSubChildren_treeObject[b]);
					}
					TreeParent local4_treeParent = (TreeParent)localSubChildren_treeObject[b];
					TreeObject[] localInnerChildren_treeObject = (TreeObject[])local4_treeParent.getChildren();
					
					for(int c=0;c<localInnerChildren_treeObject.length;c++)
					{
						String tempStr = localInnerChildren_treeObject[c].getName();
						String tempString[] = tempStr.split("   ::   ");
						String localFirstString = tempString[0];
						
						if(localFirstString.contains(textToFind))
						{
							DatabaseViewer.treeList.add(localInnerChildren_treeObject[c]);
						}
					}
				}
			}	
		}// End of first for loop
		TreeObject main_treeObject = (TreeObject)DatabaseViewer.treeList.get(0);// First found item
		DatabaseViewer.viewer.expandToLevel(main_treeObject,1);// first found item should be selected and should be visible to the user
	}
	
	/**
	 * Method to  search down the tree item with the condition whole word 
	 * @param textToFind
	 * @param selectedItemTree
	 * @param selectedTreeObjectName
	 */
	public void searchDownWithWholeWord(String textToFind,TreeObject selectedItemTree,String selectedTreeObjectName)//Method to  search down the tree item with the condition whole word
	{
		DatabaseViewer.treeList.clear();
		TreeParent invisibleParent = (TreeParent)DatabaseViewer.viewer.getInput();
		TreeObject[] mainNode_treeObject = invisibleParent.getChildren();
		TreeParent local2_treeParent = (TreeParent)mainNode_treeObject[0];
		TreeObject[] mainChildren_treeObject = (TreeObject[]) local2_treeParent.getChildren();
		int counter = getSelectedTreeItemIndex(local2_treeParent,selectedTreeObjectName);
		
		for(int a=counter;a<mainChildren_treeObject.length;a++)
		{
			String tempName1_String = mainChildren_treeObject[a].getName();
			TreeParent local3_treeparent = (TreeParent) mainChildren_treeObject[a];
			TreeObject[] localSubChildren_treeObject = (TreeObject[])local3_treeparent.getChildren();
			
			if(tempName1_String.equalsIgnoreCase(textToFind))
			{
				DatabaseViewer.treeList.add(mainChildren_treeObject[a]);
			}
			else
			{
				for(int b=0;b<localSubChildren_treeObject.length;b++)
				{
					String tempName2_String = localSubChildren_treeObject[b].getName();
					if(tempName2_String.equalsIgnoreCase(textToFind))
					{
						DatabaseViewer.treeList.add(localSubChildren_treeObject[b]);
					}
					TreeParent local4_treeParent = (TreeParent)localSubChildren_treeObject[b];
					TreeObject[] localInnerChildren_treeObject = (TreeObject[])local4_treeParent.getChildren();
					
					for(int c=0;c<localInnerChildren_treeObject.length;c++)
					{
						String tempStr = localInnerChildren_treeObject[c].getName();
						String tempString[] = tempStr.split("   ::   ");
						String localFirstString = tempString[0];
						
						if(localFirstString.equalsIgnoreCase(textToFind))
						{
							DatabaseViewer.treeList.add(localInnerChildren_treeObject[c]);
						}
					}
				}
			}	
		}// End of first for loop
		TreeObject main_treeObject = (TreeObject)DatabaseViewer.treeList.get(0);// First found item
		DatabaseViewer.viewer.expandToLevel(main_treeObject,1);// first found item should be selected and should be visible to the user
		
	}
	/**
	 * Method to search down when both cases are selected
	 * @param textToFind
	 * @param selectedItemTree
	 * @param selectedTreeObjectName
	 */
	public void searchDownBothCases(String textToFind,TreeObject selectedItemTree,String selectedTreeObjectName)//Method to search down when both cases are selected
	{
		DatabaseViewer.treeList.clear();// It is a static arraylist which maintatins the list of all match cases.
		TreeParent invisibleParent = (TreeParent)DatabaseViewer.viewer.getInput();
		TreeObject[] mainNode_treeObject = invisibleParent.getChildren();
		TreeParent local2_treeParent = (TreeParent)mainNode_treeObject[0];
		TreeObject[] mainChildren_treeObject = (TreeObject[]) local2_treeParent.getChildren();
		int counter = getSelectedTreeItemIndex(local2_treeParent,selectedTreeObjectName);
		for(int a=counter;a<mainChildren_treeObject.length;a++)
		{
			String tempName1_String = mainChildren_treeObject[a].getName();
			TreeParent local3_treeparent = (TreeParent) mainChildren_treeObject[a];
			TreeObject[] localSubChildren_treeObject = (TreeObject[])local3_treeparent.getChildren();
			
			if(tempName1_String.equals(textToFind))
			{
				DatabaseViewer.treeList.add(mainChildren_treeObject[a]);
			}
			else
			{
				for(int b=0;b<localSubChildren_treeObject.length;b++)
				{
					String tempName2_String = localSubChildren_treeObject[b].getName();
					
					if(tempName2_String.equals(textToFind))
					{
						DatabaseViewer.treeList.add(localSubChildren_treeObject[b]);
					}
					TreeParent local4_treeParent = (TreeParent)localSubChildren_treeObject[b];
					TreeObject[] localInnerChildren_treeObject = (TreeObject[])local4_treeParent.getChildren();
					
					for(int c=0;c<localInnerChildren_treeObject.length;c++)
					{
						String tempStr = localInnerChildren_treeObject[c].getName();
						String tempString[] = tempStr.split("   ::   ");
						String localFirstString = tempString[0];
						
						if(localFirstString.equals(textToFind))
						{
							DatabaseViewer.treeList.add(localInnerChildren_treeObject[c]);
						}
					}
				}
			}	
		}// End of first for loop
		TreeObject main = (TreeObject)DatabaseViewer.treeList.get(0);//First found item
		DatabaseViewer.viewer.expandToLevel(main,1);// First found should be visible and selected
	}
	
	/**
	 *  Method to provide the index value of the selected tree item
	 * @param local2
	 * @param selectedTreeObjectName
	 * @return counter value of the index of the selected tree item
	 */
	public int getSelectedTreeItemIndex(TreeParent local2,String selectedTreeObjectName)// provides tthe information of the value which user has selected the tree item
	{
		TreeObject[] mainChildren_treeObject = (TreeObject[]) local2.getChildren();
		int counter =0;
		for(int j=0;j<mainChildren_treeObject.length;j++)
		{
			String tempVar_String = mainChildren_treeObject[j].getName();
			TreeParent local33_treeParent = (TreeParent) mainChildren_treeObject[j];
			TreeObject[] localSubChildren11_treeObject = (TreeObject[])local33_treeParent.getChildren();
			
			if(tempVar_String.equalsIgnoreCase(selectedTreeObjectName))
			{
				break;
			}
			else
			{
				counter++;
			}
		}
		
		return counter;
	}
	
}	
