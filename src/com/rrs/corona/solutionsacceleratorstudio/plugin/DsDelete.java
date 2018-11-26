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

public class DsDelete
{
	/**
	 * File Path of the XML file
	 */
	private transient String	dataSourceFilePath	= SASConstants.SAS_ROOT;		// File
																					// path
																					// of
																					// the
																					// XML
																					// file
																					// containing
																					// the
																					// database
																					// information
	/**
	 * File Name of the XML File
	 */
	private transient String	dataSourceFilename	= SASConstants.SAS_XML;			// file
																					// name
																					// of
																					// the
																					// XML
																					// file
																					// containing
																					// database
																					// information
	/**
	 * SAS Jaxb Context
	 */
	private transient String	jaxBContext			= SASConstants.SASJAXB_CONTEXT;

	/**
	 * Method to delete the dataSource Info
	 * 
	 * @param OldDsName
	 *            of type String
	 */
	public void deleteDataSource( final String OldDsName )
	{// Method to delete the dataSource name
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
				if( currentDsName.equalsIgnoreCase( OldDsName ) )
				{
					solutionAdapter.getDataSource( ).remove( i );
					ObjectFactory objectFactory = new ObjectFactory( );
					populateDbInfo( objectFactory, solutionAdapter );// Method
																		// to
																		// populate
																		// Database
																		// Information
				}
				else
				{

				}
			}
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in Method " +
					"**deleteDataSource()** in class **DsDelete.java**";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			e.printStackTrace( );
		}
	}

	/**
	 * Method to populate the database Information in an XML file
	 * 
	 * @param objectFactory
	 *            of type JAXB ObjectFactory
	 * @param solutionAdapter
	 *            of type object SolutionAdapter
	 */
	public void populateDbInfo( final ObjectFactory objectFactory,
			final SolutionAdapter solutionAdapter )
	{// Method to populate database information to the XML file
		try
		{
			DataSourceList dataSourceList = objectFactory
					.createDataSourceList( );
			TableList tabList = objectFactory.createTableList( ); // get a
																	// table tag
			FieldList fieldList = objectFactory.createFieldList( ); // to get
																	// field tag
			final JAXBContext jaxbContext = JAXBContext
					.newInstance( jaxBContext );
			final FileOutputStream fileOutputStream = new FileOutputStream(
																			dataSourceFilePath
																					+ dataSourceFilename ); // writing
																											// to a
																											// file
			final Marshaller marshaller = jaxbContext.createMarshaller( );
			marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT,
									Boolean.TRUE );
			marshaller.marshal( solutionAdapter, fileOutputStream );
			fileOutputStream.close( );
		}
		catch( Exception e )
		{
			final String errMsg = "Exception thrown in Method " +
					"**populateDbInfo()** in class **DsDelete.java**";
			SolutionsacceleratorstudioPlugin.getDefault( ).logError( errMsg, e );
			e.printStackTrace( );
		}
	}

}
