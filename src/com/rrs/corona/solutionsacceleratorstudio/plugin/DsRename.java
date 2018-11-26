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

/**
 * @author Debadatta Mishra
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import com.rrs.corona.Sas.DataSourceList;
import com.rrs.corona.Sas.FieldList;
import com.rrs.corona.Sas.ObjectFactory;
import com.rrs.corona.Sas.SolutionAdapter;
import com.rrs.corona.Sas.TableList;
import com.rrs.corona.common.CommonConstants;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;

public class DsRename
{
	/**
	 * File path of the XML file
	 */
	private transient String	dataSourceFilePath	= SASConstants.SAS_ROOT;		// File
	// path
	// of
	// the
	// XML
	// file
	// containing
	// database
	// information
	/**
	 * File name of the XML file
	 */
	private transient String	dataSourceFilename	= SASConstants.SAS_XML;		// File
	// name
	// of
	// the
	// XML
	// file
	/**
	 * Sas JaxbContext
	 */
	private transient String	jaxBContext			= SASConstants.SASJAXB_CONTEXT;

	/**
	 * Method to rename a dataSource
	 * 
	 * @param oldDsName
	 *            of type String
	 * @param newDsName
	 *            of type String
	 */
	public void renameDataSourcename( final String oldDsName,
			final String newDsName )
	{// Method to rename a dataSource name
		try
		{
			JAXBContext jaxbCtx = JAXBContext.newInstance( jaxBContext );
			Unmarshaller unmarshal = jaxbCtx.createUnmarshaller( );
			SolutionAdapter solutionAdapter = ( SolutionAdapter ) unmarshal
					.unmarshal( new FileInputStream( dataSourceFilePath
							+ dataSourceFilename ) );
			List dataSourceList = solutionAdapter.getDataSource( );

			for( int i = 0; i < dataSourceList.size( ); i++ )
			{
				DataSourceList dataSource = ( DataSourceList ) dataSourceList
						.get( i );
				final String currentDsName = dataSource.getDataSourceName( );
				if( currentDsName.equalsIgnoreCase( oldDsName ) )
				{
					ObjectFactory objectFactory = new ObjectFactory( );
					dataSource.setDataSourceName( newDsName ); // writing data
					// source to the
					// xml file
					populateDbInfo( objectFactory, solutionAdapter );
				}
				else
				{
					// do nothing
				}
			}
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in method "
					+ "**renameDataSourcename()** in class [DsRename.java]";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			e.printStackTrace( );
		}
	}

	/**
	 * Method to populate the New DataSource Information
	 * 
	 * @param objectFactory
	 *            of type JAXB ObjectFactory
	 * @param solutionAdapter
	 *            of type SolutionAdapter
	 * 
	 */
	public void populateDbInfo( ObjectFactory objectFactory,
			SolutionAdapter solutionAdapter )
	{// Method to populate the database information to the XML file
		try
		{
			DataSourceList dataSourceList = objectFactory
					.createDataSourceList( ); // to get first tag
			TableList tabList = objectFactory.createTableList( ); // get a
			// table tag
			FieldList fieldList = objectFactory.createFieldList( ); // to get
			// field tag
			final JAXBContext jaxbContext = JAXBContext
					.newInstance( jaxBContext );
			final FileOutputStream fileOutputStream = new FileOutputStream(
																			dataSourceFilePath
																					+ dataSourceFilename );
			final Marshaller marshaller = jaxbContext.createMarshaller( );
			marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT,
									Boolean.TRUE );
			marshaller.marshal( solutionAdapter, fileOutputStream );
			fileOutputStream.close( );
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in Method "
					+ "**populateDbInfo()** in class [DsRename.java]";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			e.printStackTrace( );
		}
	}

	/**
	 * Method used to set the Visibility of the DataSource Name
	 * 
	 * @param DsName
	 *            fo type String
	 * @param visibility
	 *            of type String
	 */
	public void setDataSourceVisible( final String DsName,
			final String visibility )
	{// Method to set the visibility of a dataSource name
		try
		{
			JAXBContext jaxbCtx = JAXBContext.newInstance( jaxBContext );
			Unmarshaller unmarshal = jaxbCtx.createUnmarshaller( );
			SolutionAdapter solutionAdapter = ( SolutionAdapter ) unmarshal
					.unmarshal( new FileInputStream( dataSourceFilePath
							+ dataSourceFilename ) );
			List dataSourceList = solutionAdapter.getDataSource( );

			for( int i = 0; i < dataSourceList.size( ); i++ )
			{
				DataSourceList dataSource = ( DataSourceList ) dataSourceList
						.get( i );
				final String currentDsName = dataSource.getDataSourceName( );
				if( currentDsName.equalsIgnoreCase( DsName ) )
				{
					ObjectFactory objectFactory = new ObjectFactory( );
					dataSource.setSelectedDataSource( visibility );// here
					// visibility
					// is set
					populateDbInfo( objectFactory, solutionAdapter );
				}
				else
				{
					// do nothing
				}
			}
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in Method "
					+ "**setDataSourceVisible()** in Class [DsRename.java]";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			e.printStackTrace( );
		}
	}

}
