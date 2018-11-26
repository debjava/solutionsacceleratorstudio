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
 *$Id: FileUtility.java,v 1.2 2006/07/28 13:26:16 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/solutionadapter/FileUtility.java,v $
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
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Logger;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.plugin.SolutionsacceleratorstudioPlugin;

/**
 * @author Debadatta Mishra
 * 
 */
public class FileUtility {
	/**
	 * Object for FileInputStream
	 */
	transient private FileInputStream fin;

	/**
	 * Object for FileOutputStram
	 */
	transient private FileOutputStream fout;

	/**
	 * Object for obtaining the file Channel
	 */
	transient private FileChannel fichan, fochan;

	/**
	 * Long variable to store file size
	 */
	transient private long fsize;

	/**
	 * Object for MappedByteBuffer
	 */
	private transient MappedByteBuffer mbuff;

	/**
	 * Logger to log
	 */
	protected final Logger logger = Logger.getLogger("FileUtility.class");

	/**
	 * Default Constructor
	 */
	public FileUtility() {

	}

	/**
	 * Method to write a file from source position to destination position
	 * 
	 * @param srcFilePath
	 *            of type String refering the source file name
	 * @param destnFilePath
	 *            of type String refering the destination file path
	 */
	private void fileWrite(final String srcFilePath, final String destnFilePath) {// This
		// method
		// is
		// used
		// to
		// write
		// the
		// contents
		// of a
		// file
		// from source position to destination position
		final String dirName = destnFilePath.substring(0, destnFilePath
				.lastIndexOf(SASConstants.BACK_SLASH_s));
		final File destFile = new File(destnFilePath);
		if (!destFile.exists()) {
			new File(dirName).mkdirs();
		}
		// else
		// {
		// // do nothing
		// }

		try {
			fin = new FileInputStream(srcFilePath);
			fout = new FileOutputStream(destnFilePath);
			fichan = fin.getChannel();
			fochan = fout.getChannel();
			fsize = fichan.size();
			mbuff = fichan.map(FileChannel.MapMode.READ_ONLY, 0, fsize);
			fochan.write(mbuff);
			fichan.close();
			fochan.close();
			fin.close();
			fout.close();
		} catch (IOException ie) {
			final String errMsg = "Exception thrown in Method "
					+ "**fileWrite()** in class [ FileUtility.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, ie);
			ie.printStackTrace();
			logger.info("Some Error In Reading File");
		} catch (ArrayIndexOutOfBoundsException e1) {
			final String errMsg = "Exception thrown in Method "
					+ "**fileWrite()** in class [ FileUtility.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e1);
			e1.printStackTrace();
			logger.info("Some Other Error");
		} catch (Exception e) {
			final String errMsg = "Exception thrown in Method "
					+ "**fileWrite()** in class [ FileUtility.java ]";
			SolutionsacceleratorstudioPlugin.getDefault().logError(errMsg, e);
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to copy a directory which contains only files from
	 * one position to another
	 * 
	 * @param srcFilePath
	 *            of type String refering the source file path
	 * @param destnFilePath
	 *            of type String refering the destination file path
	 */
	private void copyDir(final String srcFilePath, final String destnFilePath) {// This
		// method
		// is
		// used
		// to
		// copy
		// the
		// directory
		// containing
		// the
		// files
		// from one position to another
		String metaDirName = srcFilePath.substring(srcFilePath
				.lastIndexOf(SASConstants.BACK_SLASH_s) + 1, srcFilePath
				.length());
		final File dirName = new File(destnFilePath + SASConstants.BACK_SLASH_s
				+ metaDirName);
		if (!dirName.exists()) 
		{
			new File(destnFilePath + SASConstants.BACK_SLASH_s + metaDirName)
					.mkdirs();
		}
		// else
		// {
		// //do nothing
		// }
		File srcMetaFile = new File(srcFilePath);
		File[] fileInMeta_inf = srcMetaFile.listFiles();
		logger.info("Total No of files :::  " + fileInMeta_inf.length);
		for (int i = 0; i < fileInMeta_inf.length; i++) {
			if (fileInMeta_inf[i].isDirectory()) {
				continue;
			} else {
				copyFile(fileInMeta_inf[i].getAbsolutePath(), destnFilePath
						+ SASConstants.BACK_SLASH_s + metaDirName
						+ SASConstants.BACK_SLASH_s
						+ fileInMeta_inf[i].getName());
			}
		}
	}

	/**
	 * Method used to copy a file from one position to another
	 * 
	 * @param fileName
	 *            of type String denoting the name of the file
	 * @param srcPath
	 *            of type string denoting the path of the source directory
	 * @param destnPath
	 *            of type String denoting the path of the destination directory
	 */
	public void copyFile(final String fileName, final String srcPath,
			final String destnPath) {
		fileWrite(srcPath + SASConstants.BACK_SLASH_s + fileName, destnPath
				+ SASConstants.BACK_SLASH_s + fileName);
	}

	/**
	 * Method used to copy a file from one position to another
	 * 
	 * @param srcFilePath
	 *            of type String refering the source path
	 * @param destnFilePath
	 *            of type String refering the destination path
	 */
	public void copyFile(final String srcFilePath, final String destnFilePath) {
		fileWrite(srcFilePath, destnFilePath);
	}

	/**
	 * Method to copy a directory
	 * 
	 * @param srcFilePath
	 *            of type String refering the Source path
	 * @param destnFilePath
	 *            of type String refering the destination path
	 */
	public void copyDirectory(final String srcFilePath,
			final String destnFilePath) 
	{
		copyDir(srcFilePath, destnFilePath);
	}
	
	public void deleteDir(final String destnDirName)
	{
		File file1 = new File(destnDirName);
		if(file1.exists())
		{
			boolean deleteFlag = deleteAllDirs(file1);
		}
		else
		{
			//do nothing
		}
	}
	
	private boolean deleteAllDirs(File dir)
	{
		if(dir.isDirectory())
		{
			String[] children = dir.list();
			for(int i=0;i<children.length;i++)
			{
				boolean success = deleteAllDirs(new File(dir,children[i]));
				if(!success)
				{
					return false;
				}
			}
		}
		return dir.delete();
	}

}
