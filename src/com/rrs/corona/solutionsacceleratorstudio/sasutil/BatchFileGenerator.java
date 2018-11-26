package com.rrs.corona.solutionsacceleratorstudio.sasutil;

import java.io.FileOutputStream;
import java.io.PrintWriter;

import com.rrs.corona.solutionsacceleratorstudio.SASConstants;

/**
 * This is class which is used to provide a temporary solution to the jar
 * creation. This class is used to generate a batch file depending upon certain
 * condition.
 * 
 * @author Debadatta Mishra
 * 
 */
public class BatchFileGenerator {
	private transient PrintWriter printWriter = null;

	private PrintWriter getPrintWriter(final String filePath,
			final String fileName) {
		try {
			printWriter = new PrintWriter(new FileOutputStream(filePath + "/"
					+ fileName + ".bat"));
		} catch (Exception e) {
			System.out.println("Exception is thrown in Class :::"
					+ "BatchFileGenerator::: in Method :::getPrintWrite():::");
			e.printStackTrace();
		}
		return printWriter;
	}

	private void closeWriter() {
		try {
			printWriter.flush();
			printWriter.close();
		} catch (Exception e) {
			System.out.println("Exception is thrown in Class "
					+ ":::BatchFileGenerator::: in Method :::closeWriter():::");
			e.printStackTrace();
		}
	}

	private void generateTempBatchFile(final String filePath,
			final String fileName) {
		try {
			// printWriter.write("chdir %CORONA_HOME_SAS%");
			// D:\corona\dist\coronadeployment\preconfigured\win32\client\corona\solutionsacceleratorstudio\plugins\com.rrs.corona.sas_1.0.0\corona\SA\AD
			System.out
					.println("File Path in Method :::generateTempBatchFile():::");
			printWriter.write("\n");
			printWriter.write("chdir " + filePath + "");
			printWriter.write("\n");
			printWriter.write("cd bin");
			printWriter.write("\n");
			printWriter.write("jar -cvf " + filePath + "/" + fileName
					+ ".jar *.*. ");
			printWriter.write("\n");
			printWriter.write("\n pause");
			printWriter.write("\n");
		} catch (Exception e) {
			System.out
					.println("Exception is thrown here in Class "
							+ ":::BatchFileGenerator::: in Method :::generateTempBatchFile():::");
		}
	}

	public void generateBatchFile(final String filePath, final String fileName) {
		String batchFilePath = null;
		if (filePath.endsWith("/")) {
			String path = filePath.substring(0, filePath.lastIndexOf("/"));
			batchFilePath = path;
		} else {
			batchFilePath = filePath;
		}
		final String testFilePath = SASConstants.CORONA_HOME_SAS
				+ SASConstants.BACK_SLASH_s + "solutionsacceleratorstudio"
				+ SASConstants.BACK_SLASH_s;
		// String path = filePath.substring(0,filePath.lastIndexOf("/"));
		System.out.println("path ::: " + batchFilePath);
		getPrintWriter(testFilePath, fileName);
		generateTempBatchFile(batchFilePath, fileName);
		closeWriter();
	}

}
