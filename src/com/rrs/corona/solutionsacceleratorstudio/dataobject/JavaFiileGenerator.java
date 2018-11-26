/*******************************************************************************
 * @rrs_start_copyright
 * 
 * Copyright 2005 (C) Red Rabbit Software Inc. All rights reserved. This
 * software is the confidential and proprietary information of Red Rabbit
 * Software, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Red Rabbit Software. ©
 * 2004-2005, Red Rabbit Software Inc. Confidential Information. All rights
 * reserved. Red Rabbit Software - Development Program 5 of 15
 * 
 * $Id: JavaFiileGenerator.java,v 1.1.1.1 2006/07/14 05:41:56 redrabbit Exp $
 * $Source:
 * /home/cvs/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/dataobject/JavaFiileGenerator.java,v $
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

package com.rrs.corona.solutionsacceleratorstudio.dataobject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.solutionadapter.SolutionAdapterView;

/**
 * This class is used to generate a java source file with getter and setter
 * method for each fields specified in the string array
 * 
 * 
 * @author Maharajan
 */
public class JavaFiileGenerator
{
	/**
	 * This string will contain the package name without the last part
	 */
	private String		packageName		= "package "
												+ SASConstants.DATAOBJECT_CONTEXT
												+ ".";
	/**
	 * This string will contain "public class"
	 */
	private String		className		= SASConstants.DATAOBJECT_JAVACLASS;
	/**
	 * This string will contain "public"
	 */
	private String		getterMethod	= SASConstants.DATAOBJECT_JAVAPUBLIC;
	/**
	 * This string will contain "set"
	 */
	private String		setterMethod	= SASConstants.DATAOBJECT_JAVASET;
	/**
	 * This string array will hold all the fields of the class
	 */
	private String[]	fields;
	/**
	 * The string will hold the new line character
	 */
	private String		newLine			= "\n";
	/**
	 * This string will hold the tab character
	 */
	private String		intendation		= "\t";
	/**
	 * This string will hold the semi colon
	 */
	private String		semicolon		= ";";
	/**
	 * This string will hold the open curly brace
	 */
	private String		openCurlybrase	= "{";
	/**
	 * This string will hold the close curly brace
	 */
	private String		closeCurlybrase	= "}";
	/**
	 * The string will hold the open bracket
	 */
	private String		closeBracket	= ")";
	/**
	 * The string will hold the close bracket
	 */
	private String		openBracket		= "(";
	/**
	 * This string will hold "this."
	 */
	private String		thisVariable	= "this.";
	/**
	 * This string will hold "return"
	 */
	private String		returnVariable	= "return ";
	/**
	 * This string will hold the equal to character
	 */
	private String		equalTo			= " = ";

	/**
	 * This method is used to generate a java source file with getter and setter
	 * methods
	 * 
	 * @param className
	 *            This string contains the class name
	 * @param packageName
	 *            This string contains the last part of the package
	 * @param fields
	 *            This string array conatains the fields along with their types
	 * @throws FileNotFoundException
	 */
	public void generateCode( String className, String packageName,
			String[] fields ) throws FileNotFoundException
	{
		this.packageName = this.packageName + packageName;
		this.className = this.className + className;
		this.fields = fields;
		// get the file path of the java file by replacing the '.' with the '/'
		// to get a directory structure
		// for the package
		String filepath = this.packageName.substring( this.packageName
				.indexOf( " " ) + 1 );
		filepath = filepath.replace( '.', '/' );
		// this path where the java source file will be stored
		SolutionAdapterView objView = new SolutionAdapterView();
		String contextPath = SASConstants.SAS_ROOT+objView.getFolderStructure();
		File ff = new File( contextPath + SASConstants.BACK_SLASH_s+ SASConstants.DATAOBJECT_CLASSPATH );
		ff.mkdirs( );
		// create the java file name with the name of the class name
		PrintWriter pWriter = new PrintWriter( ff.getAbsolutePath( ) + "/"
				+ className + ".java" );
		pWriter.write( this.packageName+ this.semicolon + this.newLine + this.newLine );
		pWriter.write( this.className + this.newLine + this.openCurlybrase
				+ this.newLine );
		// write into the print writer for all the fields
		for( int i = 0; i < this.fields.length; i++ )
		{
			pWriter.write( this.intendation );
			pWriter.write( "private " + this.fields[i] + this.semicolon
					+ this.newLine );
		}
		// end loop;

		// start new loop for getter and setter
		for( int i = 0; i < this.fields.length; i++ )
		{
			pWriter.write( this.newLine );
			String actualVariable = this.fields[i]
					.substring( this.fields[i].indexOf( " " ) ).trim( );
			String actualReturnType = this.fields[i]
					.substring( 0, this.fields[i].indexOf( " " ) );
			String temp = actualVariable;
			String upper = String.valueOf( temp.charAt( 0 ) );
			upper = upper.toUpperCase( );
			temp = upper + temp.substring( 1, temp.length( ) );

			StringBuilder sBuilder = new StringBuilder( );
			// getter method
			sBuilder.append( this.intendation );
			sBuilder.append( this.getterMethod + actualReturnType );
			sBuilder.append( " " );
			sBuilder.append( SASConstants.DATAOBJECT_JAVAGET + temp );
			sBuilder.append( this.openBracket );
			sBuilder.append( this.closeBracket );
			sBuilder.append( this.newLine );
			pWriter.write( sBuilder.toString( ) );
			sBuilder.delete( 0, sBuilder.length( ) );

			sBuilder.append( this.intendation );
			sBuilder.append( this.openCurlybrase );
			sBuilder.append( this.newLine );
			pWriter.write( sBuilder.toString( ) );
			sBuilder.delete( 0, sBuilder.length( ) );

			sBuilder.append( this.intendation );
			sBuilder.append( this.intendation );
			sBuilder.append( this.returnVariable );
			sBuilder.append( actualVariable );
			sBuilder.append( this.semicolon );
			sBuilder.append( this.newLine );
			pWriter.write( sBuilder.toString( ) );
			sBuilder.delete( 0, sBuilder.length( ) );

			sBuilder.append( this.intendation );
			sBuilder.append( this.closeCurlybrase );
			sBuilder.append( this.newLine );
			pWriter.write( sBuilder.toString( ) );
			sBuilder.delete( 0, sBuilder.length( ) );

			// setter method........
			sBuilder.append( this.intendation );
			sBuilder.append( this.setterMethod );
			sBuilder.append( temp );
			sBuilder.append( this.openBracket );
			sBuilder.append( actualReturnType );
			sBuilder.append( " " );
			sBuilder.append( actualVariable );
			sBuilder.append( this.closeBracket );
			sBuilder.append( this.newLine );
			pWriter.write( sBuilder.toString( ) );
			sBuilder.delete( 0, sBuilder.length( ) );

			sBuilder.append( this.intendation );
			sBuilder.append( this.openCurlybrase );
			sBuilder.append( this.newLine );
			pWriter.write( sBuilder.toString( ) );
			sBuilder.delete( 0, sBuilder.length( ) );

			sBuilder.append( this.intendation );
			sBuilder.append( this.intendation );
			sBuilder.append( this.thisVariable );
			sBuilder.append( actualVariable );
			sBuilder.append( this.equalTo );
			sBuilder.append( actualVariable );
			sBuilder.append( this.semicolon );
			sBuilder.append( this.newLine );
			pWriter.write( sBuilder.toString( ) );
			sBuilder.delete( 0, sBuilder.length( ) );

			sBuilder.append( this.intendation );
			sBuilder.append( this.closeCurlybrase );
			sBuilder.append( this.newLine );
			pWriter.write( sBuilder.toString( ) );
			sBuilder.delete( 0, sBuilder.length( ) );

		}
		pWriter.write( closeCurlybrase );
		pWriter.flush( );

	}

	/*
	 * public static void main(String []a) { new
	 * JavaFiileGenerator().generateCode("HelloWord","World",new
	 * String[]{"String strrin","int iinnn"}); System.out.println("generated"); }
	 */
}
