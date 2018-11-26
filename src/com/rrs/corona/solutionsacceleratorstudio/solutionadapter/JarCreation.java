/******************************************************************************
 * @rrs_start_copyright
 *
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved.
 * This software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of
 * the license agreement you entered into with Red Rabbit Software.
 © 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights reserved.
 Red Rabbit Software - Development Program 5 of 15
 *$Id: JarCreation.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/JarCreation.java,v $
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;

/**
 * @author Debadatta Mishra
 *
 */
public class JarCreation
{
	/**
	 * File object
	 */
	File file;
	/**
	 * JarOutputStream object to create a jar file
	 */
	JarOutputStream jarOutput ;
	/**
	 * File of the generated jar file
	 */
	String jarFileName = "solutionsacceleratorstudio.jar";
	
	/**
	 * Default Constructor to specify the path and
	 * name of the jar file
	 * @param destnPath of type String denoting the path of the generated jar file
	 */
	public JarCreation(String destnPath)
	{//This constructor initializes the path and file name of the jar file
		try
		{
			jarOutput = new JarOutputStream(new FileOutputStream(destnPath+SASConstants.BACK_SLASH_s+jarFileName));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to obtain the list of files present in a
	 * directory
	 * @param path of type String specifying the path of directory containing the files 
	 * @return the list of files from a particular directory
	 */
	public File[] getFiles(String path)
	{//This method is used to obtain the list of files in a directory
		try
		{
			file = new File(path);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return file.listFiles();
	}
	/**
	 * This method is used to create a jar file from a directory
	 * @param path of type String specifying the directory to make jar
	 */
	public void createJar(String path)
	{//This method is used to create a jar file from 
		// a directory. If the directory contains several nested directory
		//it will work.
		try
		{
			byte[] buff = new byte[1024];
			File[] fileList = getFiles(path);
			
			for(int i=0;i<fileList.length;i++)
			{
				if(fileList[i].isDirectory())
				{
					createJar(fileList[i].getAbsolutePath());
				}
				else
				{
					FileInputStream fin = new FileInputStream(fileList[i]);
					String temp = fileList[i].getAbsolutePath();
					String subTemp = temp.substring(temp.indexOf("bin")+4,temp.length());
					jarOutput.putNextEntry(new JarEntry(subTemp));
					int len ;
					while((len=fin.read(buff))>0)
					{
						jarOutput.write(buff,0,len);
					}
					fin.close();
				}
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	/**
	 * Method used to close the object for JarOutputStream
	 */
	public void close()
	{//This method is used to close the 
		//JarOutputStream
		try
		{
			jarOutput.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
