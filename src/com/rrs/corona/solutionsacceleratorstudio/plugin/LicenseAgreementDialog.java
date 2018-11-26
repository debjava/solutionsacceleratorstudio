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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import com.rrs.corona.common.EULAReader;
import com.rrs.corona.solutionsacceleratorstudio.SASConstants;
import com.rrs.corona.solutionsacceleratorstudio.license.LicenseFileIO;

/**
 * LicenseAgreementDialog concerns with creating a dialog for displaying the
 * license aggreement to the user.
 * <p>
 * The license aggrement dialog displays the license information . The dialog
 * provides the user a way to accept or decline the license agreement.
 * </p>
 * 
 * @author Debadatta Mishra
 * @version 1.0
 * @since 1.0
 * 
 */
final public class LicenseAgreementDialog
{
	/**
	 * Push Button for OK
	 */
	transient private Button	okButton;

	/**
	 * variable of type org.eclipse.swt.widgets.Button for the user for
	 * declining the license agreement.
	 */
	transient private Button	decline;

	/**
	 * variable of type org.eclipse.swt.widgets.Button for the user for
	 * accepting the license agreement.
	 */
	transient private Button	accept;

	/**
	 * variable of type org.eclipse.swt.widgets.Shell which will display the
	 * license information.
	 */
	transient private Shell		licenceShell;
	transient private Group		textGroup;
	transient private Text		licenceInf;
	transient private Group		acceptGroup;
	Group						okGroup;
	private String				licenseAgreement	= "RED RABBIT SOFTWARE "
															+ "END-USER LICENSE AGREEMENT IMPORTANT-READ CAREFULLY: "
															+ "This End-User License Agreement (\"EULA\") is a legal agreement "
															+ "between you (either an individual person or a single "
															+ "legal entity) and the Licensor for the Red Rabbit software "
															+ "technology that displays this EULA, which includes any "
															+ "associated media and Red Rabbit Software Internet-related "
															+ "services (the \"Software\").  For purposes of this EULA, "
															+ "the term \"Licensor\" refers to Red Rabbit Software Inc., "
															+ "except in the event that you acquired the Software as a "
															+ "component of a Red Rabbit Software product originally "
															+ "licensed from the manufacturer of your computer system, "
															+ "computer system component or other software, then \"Licensor\" "
															+ "refers to such manufacturer.  An amendment or addendum to this "
															+ "EULA may accompany the Software.YOU AGREE TO BE BOUND BY THE "
															+ "TERMS OF THIS EULA BY INSTALLING, COPYING, OR USING THE SOFTWARE.  "
															+ "IF YOU DO NOT AGREE, DO NOT INSTALL, COPY, OR USE THE SOFTWARE; "
															+ "YOU MAY RETURN IT TO YOUR PLACE OF PURCHASE FOR A    "
															+ "REFUND, IF APPLICABLE.NOTE: IF YOU DO NOT HAVE A VALID "
															+ "LICENSE FOR ONE OF THE FOLLOWING SOFTWARE "
															+ "PRODUCTS (EACH A \"SOFTWARE PRODUCT\"), "
															+ "YOU ARE NOT AUTHORIZED TO INSTALL,"
															+ " COPY OR OTHERWISE USE THE SOFTWARE: Corona Platform Suite, "
															+ "or any of its applications, or any product which includes"
															+ " Business Domain Modeler (BDM), Hotwiring Studio (HWS), "
															+ "Solution Adapter Studio (SAS) or Enterprise Knowledge Manager (EKM)."
															+ "1.Definitions.  As used herein:“Authorizations” means filings, "
															+ "registrations, reports, licenses, permits and authorizations."
															+ "“Client Software” means any Red Rabbit Software designated in the "
															+ "applicable Documentation to be installed on a hard drive of a "
															+ "personal computer or other memory of a device that contains a "
															+ "central processing unit (other than a computer server or "
															+ "functional equivalent thereof);“Computers” shall mean (i) "
															+ "with respect to Server Software, the specific computer servers "
															+ "with unique MAC addresses authorized by Red Rabbit pursuant to a "
															+ "valid License Key, and (ii) with respect to Client Software, "
															+ "devices (other than computer servers or functional equivalents "
															+ "thereof) that contain a central processing unit and that are owned, "
															+ "leased or otherwise controlled by you;“Documentation” means Red Rabbit "
															+ "user manuals and all other documentation, instructions or "
															+ "other similar materials accompanying the Red Rabbit Software "
															+ "covering the use thereof, including the installation, "
															+ "application and use thereof;“Intellectual Property Rights” "
															+ "means all current and future worldwide patents, patent "
															+ "applications (including, without limitation, "
															+ "all reissues, divisions, renewals, extensions, "
															+ "continuations and continuations-in-part), "
															+ "copyrights (including but not limited to rights in audiovisual "
															+ "works and moral rights), trade secrets, trademarks, "
															+ "service marks, trade names and all other intellectual "
															+ "property rights and proprietary rights, "
															+ "whether arising under the laws of the United States or any "
															+ "other country, state or jurisdiction;“License Key” "
															+ "means a software license key generated by Red Rabbit that allows "
															+ "Users to Use the Red Rabbit Software subject to the "
															+ "terms and conditions of this Agreement, such license keys to "
															+ "expire every sixty (60) days;“Output” means the output generated "
															+ "by the Red Rabbit Software classified as design-time software on "
															+ "Exhibit A;“Software” means the list of software identified by this "
															+ "Agreement and any Upgrades thereto provided by Red Rabbit to you "
															+ "pursuant to this Agreement, all only in the format delivered by"
															+ " Red Rabbit to you;“Server Software” means any Red Rabbit "
															+ "Software designated in the applicable Documentation to be "
															+ "installed on a computer server;“Upgrade” means a new version, "
															+ "update or upgrade of the Red Rabbit Software generally "
															+ "including bug fixes, error corrections, enhancements "
															+ "and new functionality and any upgrades to the associated "
															+ "Documentation;“Use” (including the correlative meanings, "
															+ "the terms “Used” and “Using”) means the use or installation "
															+ "of software on Computers,  including, without limitation, "
															+ "use and installation via network access; and“Users” means "
															+ "those of your employees that are both (i) assigned a"
															+ " unique login name and password by you to access the "
															+ "Red Rabbit Software and (ii) authorized by Red Rabbit to "
															+ "Use the Red Rabbit Software under this Agreement pursuant "
															+ "to a valid License Key.2.Red Rabbit Software License Grants "
															+ "and Third Party Software Rights.(a)Non-Commercial Use, "
															+ "Documentation and Output License.  Subject to the terms "
															+ "and conditions of this Agreement, Red Rabbit hereby grants "
															+ "to you a limited, personal, non-transferable, non-sublicenseable, "
															+ "non-exclusive license for a period of sixty (60) days per License "
															+ "Key received from Red Rabbit, under Red Rabbit’s "
															+ "Intellectual Property Rights in the Red Rabbit "
															+ "Software, to have Users Use the Red Rabbit Software in "
															+ "accordance with the Documentation solely on Computers for "
															+ "which you have received a valid License Key from Red Rabbit "
															+ "or its agents and solely for your own Non-Commercial Use.  "
															+ "Subject to the terms and conditions of this Agreement, "
															+ "Red Rabbit hereby grants to you a limited, personal, "
															+ "non-transferable, non-sublicenseable, non-exclusive license, "
															+ "under Red Rabbit’s Intellectual Property Rights in the Documentation, "
															+ "to use the Documentation.  "
															+ "You shall not make copies of the Software or printouts of the "
															+ "Documentation.  Subject to the terms and conditions of this "
															+ "Agreement, Red Rabbit hereby grants to you a limited, "
															+ "personal non-transferable, non-sublicenseable, "
															+ "non-exclusive license, under Red Rabbit’s "
															+ "Intellectual Property Rights in the Output, "
															+ "to use the Output solely in connection with the "
															+ "Corona Red Rabbit Software. "
															+ " You shall not make copies or printouts of the Output except however,"
															+ " you shall provide Red Rabbit with a copy of any Output that "
															+ "you create within five (5) days of such Output’s creation.  "
															+ "For avoidance of doubt, "
															+ "you are prohibited from using the Output in connection "
															+ "with any platform or application other than the "
															+ "Corona Red Rabbit Software.Third Party Software.  "
															+ "The Red Rabbit Software may contain third-party software "
															+ "that requires notices and/or additional terms and conditions.  "
															+ "Such required third-party software notices and/or "
															+ "additional terms and conditions are available "
															+ "by e-mailing coronasupport@redrabbitsoftware.com and are made "
															+ "a part of and are incorporated by reference into this Agreement. "
															+ " By accepting this Agreement, "
															+ "you are also accepting the additional terms and conditions, "
															+ "if any, set forth therein.3.License Restrictions.  "
															+ "EXCEPT AS EXPRESSLY PROVIDED IN THIS AGREEMENT, RED "
															+ "RABBIT AND ITS SUPPLIERS AND LICENSORS DO NOT GRANT TO YOU ANY RIGHT,"
															+ " TITLE OR INTEREST IN OR TO THE LICENSED SOFTWARE OR "
															+ "DOCUMENTATION, WHETHER BY IMPLICATION, ESTOPPEL OR OTHERWISE.  "
															+ "You may only Use the Red Rabbit Software on Computers after you "
															+ "have received a valid License Key and only for the sixty (60) day period for which such License Key is authorized.  You must obtain a new valid License Key from Red Rabbit after a License Key expires in order to continue Using that copy of the Red Rabbit Software, such License Key to be issued, if at all, in Red Rabbit’s sole discretion.  Each User shall be required to register with Red Rabbit each copy of Red Rabbit Software Used hereunder.  You shall not yourself, or through any employee, contractor, affiliate, agent, or third party: (a) decompile, disassemble, reverse engineer, or otherwise attempt to (i) derive source code or underlying ideas, algorithms, structure or organization from the Red Rabbit Software or (ii) defeat, avoid, bypass, remove, deactivate or otherwise circumvent any software protection mechanisms in the Red Rabbit Software, including without limitation any such mechanism used to restrict or control the functionality of the Red Rabbit Software (except and only to the extent that any of the foregoing prohibitions on such activity is expressly prohibited by applicable law notwithstanding this limitation); (b) sell, lease, license, sublicense, distribute or otherwise provide to any third party or any other person (excluding Users) the Red Rabbit Software, Documentation or Output, in whole or in part, except as expressly authorized pursuant to Section 1 above; (c) modify or create derivative works of the Red Rabbit Software, Documentation or Output; (d) use or reproduce the Red Rabbit Software, Documentation or Output except as specifically permitted under this Agreement; or (e) use the Red Rabbit Software to provide processing services to any third party or otherwise use the Red Rabbit Software on a service bureau basis.  You shall not remove, alter, cover or obfuscate any patent, copyright, trademark or other proprietary notices, labels or marks of Red Rabbit or its licensors on or in the Red Rabbit Software or Documentation, and you shall reproduce such notices, labels and marks on any copies of such Red Rabbit Software, Documentation or Output that you make in connection with your permitted use of the Red Rabbit Software, Documentation and Output pursuant to Section 1.  You shall immediately notify Red Rabbit of any unauthorized use, disclosure, reproduction, or distribution of the Software, Documentation or Output, that comes to your attention, or that you reasonably suspect.  You are solely responsible for obtaining all equipment, and the compatibility thereof with the Red Rabbit Software, and for paying all fees including, without limitation, all taxes and Internet access fees, necessary to use the Red Rabbit Software.  You shall indemnify and hold Red Rabbit, its subsidiaries, affiliates, directors, officers, agents, licensors, subcontractors, employees, successors and assigns harmless from and against all claims, actions, suits, settlements, damages, losses, liabilities, penalties, fines, costs and expenses (including, without limitation, attorney’s fees and experts’ fees) arising from, relating or occurring as a result of your use of the Red Rabbit Software, Documentation and Output.4.Confidential Information.  Red Rabbit Software, Documentation and Output including, without limitation, trade secrets, performance data, bugs, errors, designs, features, layouts, configurations, processes, formulae, specifications, programs, test results, technical know-how, methods and procedures of operation, and other information relating to or obtained from the Red Rabbit Software, by use, examination or otherwise, which is not generally publicly known shall be deemed to be confidential information of Red Rabbit (“Confidential Information”).  In addition, any information or materials disclosed or provided to you by Red Rabbit or its personnel and specified as confidential or proprietary or marked as confidential or proprietary shall be deemed to be Confidential Information of Red Rabbit.  You shall use the same degree of care to protect the Confidential Information from non-disclosure as you would use with respect to your own information of like importance that you do not desire to have published or disseminated, but in any event no less than reasonable care.  Without limiting the foregoing, you shall not disclose any Confidential Information to third parties or to your employees, except to your employees who have a need to know and who have signed a non-use and non-disclosure agreement containing provisions at least as protective as set forth in this Agreement with respect to the Confidential Information.  You shall not use any Confidential Information for any purpose except to exercise your rights and perform your obligations under this Agreement. 5.Proprietary Rights and Audits.  The Red Rabbit Software, Documentation and Output are proprietary products of Red Rabbit and/or its suppliers and licensors and are protected under intellectual property laws, including without limitation U.S. copyright law, patent law and international treaties.  Except for the license rights expressly granted pursuant to Section 1 above, Red Rabbit and its suppliers and licensors shall own all right, title, and interest in and to the Red Rabbit Software, Documentation and Output, including all Intellectual Property Rights therein. At Red Rabbit’s request and upon reasonable notice, you shall grant Red Rabbit or its auditors reasonable access to each facility, site or location where any copy of the Red Rabbit Software is installed (including physical access to each such facility, site or location for the purpose of examining the actual Computers present at such facility, site or location) no more frequently than once per calendar quarter for such facility, site or location for the purpose of verifying your compliance with the terms and conditions of this Agreement.6.Taxes.   When Red Rabbit has the legal obligation to collect any excise, sales, use, value added or other taxes, tariffs or duties that may be applicable to the licensing of the Software, Documentation and Output, the amount of such taxes, tariffs and duties shall be invoiced to you and you shall pay such amount unless you provide Red Rabbit with a valid tax exemption certificate authorized by the appropriate taxing authority.  Any such taxes which are otherwise imposed on payments to Red Rabbit shall be your sole responsibility.  You shall provide Red Rabbit with official receipts issued by the appropriate taxing authority or such other evidence as is reasonably requested by Red Rabbit to establish that such taxes have been paid.7.Termination.  You may terminate this Agreement at any time by returning the Red Rabbit Software,  Documentation, Output and all copies thereof to Red Rabbit.  Red Rabbit may terminate this Agreement upon the breach by you of any provision of this Agreement.  Upon any termination of this Agreement: (i) all rights and licenses granted by Red Rabbit to you under this Agreement shall immediately terminate; (ii) you shall immediately return to Red Rabbit or, at Red Rabbit’s option, destroy all copies of the Red Rabbit Software, Documentation and Output and any Confidential Information in tangible form that is in your possession or under your control, and any portion thereof; and (iii) you shall certify in writing to Red Rabbit that you have complied with subsection (ii) above.  Sections 1 (Definitions), 1 (License Restrictions), 1 (Confidential Information), 1 (Proprietary Rights and Audits), 1 (Termination), 1 (Warranty Disclaimer, 1 (Limitation on Liability), 1 (Government Approvals) and 1 (Governing Law, Forum and Arbitration) shall survive any termination of this Agreement. Termination shall not, however, relieve a party of its obligations incurred prior to the effective date of termination of the Agreement.8.Warranty Disclaimer.  THE RED RABBIT SOFTWARE, DOCUMENTATION AND OUTPUT ARE LICENSED “AS IS”, AND RED RABBIT AND ITS SUPPLIERS AND LICENSORS HEREBY DISCLAIM ALL REPRESENTATIONS, WARRANTIES AND CONDITIONS OF ANY KIND, WHETHER ORAL OR WRITTEN, WHETHER EXPRESS, IMPLIED, STATUTORY, OR ARISING BY STATUTE, CUSTOM, COURSE OF DEALING, TRADE USAGE OR OTHERWISE, WITH RESPECT TO THE SUBJECT MATTER HEREOF IN CONNECTION WITH THIS AGREEMENT.  RED RABBIT AND ITS SUPPLIERS AND LICENSORS SPECIFICALLY DISCLAIM THE IMPLIED WARRANTIES OR CONDITIONS OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, SATISFACTORY QUALITY AND NON-INFRINGEMENT.  RED RABBIT AND ITS SUPPLIERS AND LICENSORS DO NOT REPRESENT OR WARRANT THAT THE SOFTWARE PROVIDED UNDER THIS AGREEMENT WILL MEET YOUR REQUIREMENTS, THAT THE SOFTWARE WILL ASSIST YOU IN COMPLYING WITH ANY DATA PROTECTION LAWS OR REQUIREMENTS, THAT THE OPERATION OF THE SOFTWARE WILL BE UNINTERRUPTED OR ERROR FREE, OR THAT DEFECTS IN THE SOFTWARE WILL BE CORRECTED.  9.Limitation of Liability.  TO THE MAXIMUM EXTENT ALLOWED UNDER APPLICABLE LAW, IN NO EVENT WILL RED RABBIT OR ITS SUPPLIERS OR LICENSORS BE LIABLE FOR ANY INDIRECT, SPECIAL, INCIDENTAL, CONSEQUENTIAL, EXEMPLARY OR PUNITIVE DAMAGES ARISING OUT OF OR IN CONNECTION WITH THIS AGREEMENT, INCLUDING BUT NOT LIMITED TO DAMAGES FOR LOST DATA, LOST PROFITS OR COSTS OF PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES, HOWEVER CAUSED AND UNDER ANY THEORY OF LIABILITY, WHETHER BASED IN CONTRACT, TORT (INCLUDING NEGLIGENCE), STATUTE OR OTHERWISE, AND WHETHER OR NOT RED RABBIT WAS OR SHOULD HAVE BEEN AWARE OR ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  THE FOREGOING LIMITATIONS SHALL APPLY NOTWITHSTANDING THE FAILURE OF ESSENTIAL PURPOSE OF ANY LIMITED REMEDY STATED IN THIS AGREEMENT. 10.Export Laws.  You shall comply with all applicable export laws and regulations of the United States (“U.S.”) and foreign authorities.  In the event of conflict between U.S. and foreign export laws and regulations, U.S. export laws and regulations shall prevail.  You assume full responsibility for the following: (a) determining the Export Control Classification Number, if any, applicable to the Software and Documentation, (b) determining any export licensing requirements applicable to the Software and Documentation, and (c) obtaining any license or other authorization required for any exports or reexports of the Software and Documentation.  In the event Red Rabbit elects, in its sole discretion, to apply for an export license or other authorization which Red Rabbit determines is required to provide the Software or Documentation, or any information or service related to the Software or Documentation, to you, you shall reimburse Red Rabbit for expenses incurred by Red Rabbit in that regard.11.Compliance with Applicable Laws.  You shall at your own expense, make, obtain, and maintain in force at all times during the term of this Agreement, all Authorizations required for you to perform your obligations under this Agreement.  Red Rabbit shall provide you, at your expense, with such assistance as you may reasonably request in making or obtaining any such Authorizations.  In the event that the issuance of any Authorization is conditioned upon an amendment or modification to this Agreement which is unacceptable to Red Rabbit, Red Rabbit shall have the right to terminate this Agreement without further obligation whatsoever to you.12.Government Approvals.  You hereby represent and warrant that no consent, approval or authorization of or designation, declaration or filing with any governmental authority is required in connection with the valid execution, delivery and performance of this Agreement.13.Governing Law, Forum and Arbitration.  This Agreement shall be governed by, and interpreted under, the laws of the State of Illinois, without regard to conflict of laws principles.   The United Nations Convention on Contracts for the International Sales of Goods shall not apply to this Agreement. All disputes arising out of or in connection with this Agreement shall be finally settled under the Rules of Arbitration of the ICC by three (3) arbitrators selected in accordance with such Rules and the agreement of the Parties as provided below. The place of arbitration shall be Chicago, IL U.S.A. The language to be used in the arbitral proceedings shall be English.  Red Rabbit and you shall each be entitled to appoint one (1) arbitrator. The third arbitrator shall be appointed by agreement of the two party arbitrators and in the absence of agreement by the party arbitrators within fifteen (15) business days after the appointment of the second party arbitrator, by the ICC.  The Parties intend that any arbitration be conducted on an expedited basis with the aim that an arbitral award be made as soon as is reasonably practicable consistent with a fair procedure.  The dispute resolution procedures described in this Section 1 shall not apply to any dispute between the Parties under Sections 1 (License Restrictions), 1 (Confidential Information) or 1 (Proprietary rights and Audits) of this Agreement or involving an application for a temporary restraining order or other form of injunctive relief.14.Modification, Waiver and Approvals.  No modification to this Agreement, nor any waiver of any rights, shall be effective unless agreed to in writing by the Party to be charged.  The waiver of any breach or default shall not constitute a waiver of any other right hereunder or any subsequent breach or default.  Except as and to the extent otherwise expressly provided in such approval or consent, an approval or consent given by a party under this Agreement shall not relieve the other party from responsibility for complying with the requirements of this Agreement, nor shall it be construed as a waiver of any rights under this Agreement.15.Assignment and Change of Control.  You may not assign this Agreement or any rights or obligations hereunder, directly or indirectly, by operation of law or otherwise, without the prior written consent of Red Rabbit.  Red Rabbit may assign this Agreement to any successor to all or substantially all of Red Rabbit’s business or assets that relate to the subject matter of this Agreement whether by asset or stock acquisition, merger, consolidation or otherwise.  For purposes of this Agreement, a merger, consolidation, or other corporate reorganization, or a transfer or sale of a controlling interest in Licensee’s stock, or of all or substantially all of its assets shall be deemed to be an assignment.  Subject to the foregoing, this Agreement will inure to the benefit of and be binding upon the parties and their respective successors and permitted assigns.  Any attempted assignment in violation of this Section 1 shall be null and void. 16.Severability.  If any provision of this Agreement is determined to be invalid or unenforceable, it shall be deemed to be modified to the minimum extent necessary to be valid and enforceable.  If it cannot be so modified, it shall be deleted and the deletion shall not affect the validity or enforceability of any other provision.  17.Force Majeure.  Nonperformance of either party shall be excused to the extent that performance is rendered impossible by strike, fire, flood, earthquake, governmental acts or orders or restrictions, or any other reason when failure to perform is beyond the reasonable control of the nonperforming party. 18.Entire Agreement.  This Agreement, the terms and conditions referenced in Section  of this Agreement and any order forms executed by both parties with respect to this Agreement shall constitute the entire understanding and agreement with respect to its subject matter, and supersede any and all prior or contemporaneous representations, understandings and agreements whether oral or written between the parties relating to the subject matter of this Agreement, all of which are merged in this Agreement.  No terms, provisions or conditions of any purchase order, acknowledgment or other busi­ness form that you may use in connection with this Agreement will have any effect on the rights, duties or obligations of the parties under, or otherwise modify, this Agreement, regardless of any fail­ure of Red Rabbit to object to such terms, provisions or conditions.19.Counterparts. This Agreement may be executed in multiple counterparts, including by facsimile, each of which shall be deemed an original, but all of which together shall constitute one single agreement between the parties.20.Headings and Exhibits.  The headings used for the sections in this Agreement are for convenience and reference purposes only and shall in no way affect the meaning or interpretation of this Agreement. Should you have any questions regarding this Agreement, or if you desire to contact Red Rabbit Software, Inc. for any reason, please write: Red Rabbit Software, Inc., 401 N. Michigan Avenue, Suite 360, Chicago, IL  60611 or send an e-mail to coronasupport@redrabbitsoftware.com.*     *     *     *     *";

	/**
	 * This is a callback that will allow us to create License Agreement Dialog
	 * box.
	 */
	public void createSeperatorWindow( final Shell shell )
	{
		try
		{
			licenceShell = new Shell( shell, SWT.APPLICATION_MODAL );
			licenceShell.setText( "End-User License Agreement(\"EULA\")" );
			textGroup = new Group( licenceShell, SWT.NONE );
			textGroup.setText( "End-User License Agreement(\"EULA\")" );

			licenceInf = new Text( textGroup, SWT.BORDER | SWT.MULTI
					| SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY );
			licenceInf.setText( EULAReader.getEULA( ) );

			GridData gData = new GridData( GridData.FILL_BOTH );
			licenceInf.setLayoutData( gData );
			textGroup.setLayout( new GridLayout( 1, false ) );
			textGroup.setBounds( 5, 5, 400, 300 );

			acceptGroup = new Group( licenceShell, SWT.NONE );
			acceptGroup.setText( "Select Accept or Decline" );

			acceptGroup.setLayout( new GridLayout( 1, false ) );
			accept = new Button( acceptGroup, SWT.RADIO );
			accept.setText( "I Accept the Terms and Conditions" );
			GridData gd1 = new GridData( GridData.BEGINNING );
			accept.setLayoutData( gd1 );
			decline = new Button( acceptGroup, SWT.RADIO );
			decline.setText( "I Do Not Accept the Terms and Conditions" );
			decline.setSelection( true );
			gd1 = new GridData( GridData.BEGINNING );
			decline.setLayoutData( gd1 );
			acceptGroup.setBounds( 5, 310, 400, 60 );
			okGroup = new Group( licenceShell, SWT.NO_FOCUS );
			okGroup.setLayout( new GridLayout( 2, false ) );
			okButton = new Button( okGroup, SWT.PUSH | SWT.LEFT_TO_RIGHT );
			okButton.setText( SASConstants.DIALOG_OK_BTN );
			okGroup.setBounds( 5, 380, 400, 50 );
			licenceShell.setBounds( 200, 50, 420, 500 );
			licenceShell.pack( );
			licenceShell.open( );
			setListener( okButton, shell, licenceShell );
		}
		catch( Exception ex )
		{
			final String errMsg = "Exception thrown in Method "
					+ "**createSeperatorWindow()** in Method [LicenseAgreemnetDialog.java]";
			SolutionsacceleratorstudioPlugin.getDefault( )
					.logError( errMsg, ex );
			ex.printStackTrace( );
		}
	}

	/**
	 * setListener method register Selection Listener for ok button, if decline
	 * radio button is selected and ok button is clicked Application will be
	 * close immediately, if accept radio button is selected and ok button is
	 * clicked user is allowed to do further opperations.
	 * 
	 * @param okButton
	 * @param parentShell
	 * @param licenseShell
	 */
	private void setListener( final Button okButton, final Shell parentShell,
			final Shell licenseShell )
	{
		okButton.addSelectionListener( new SelectionAdapter( ) {
			public void widgetSelected( SelectionEvent e )
			{
				try
				{
					if( accept.getSelection( ) )
					{
						licenseShell.close( );
						LicenseFileIO.license.setAccepted( "true" );
						return;
					}
					else
					{
						LicenseFileIO.license.setAccepted( "false" );
						System.exit( 0 );
						// parentShell.close();
					}
				}
				catch( Exception ex )
				{
					final String errMsg = "Exception thrown in Method "
							+ "**setListener()** in Method [LicenseAgreemnetDialog.java]";
					SolutionsacceleratorstudioPlugin.getDefault( )
							.logError( errMsg, ex );
					ex.printStackTrace( );
				}
			}

		} );

	}

	public Button getAccept( )
	{
		return accept;
	}

	public void setAccept( Button accept )
	{
		this.accept = accept;
	}

	public Group getAcceptGroup( )
	{
		return acceptGroup;
	}

	public void setAcceptGroup( Group acceptGroup )
	{
		this.acceptGroup = acceptGroup;
	}

	public Button getDecline( )
	{
		return decline;
	}

	public void setDecline( Button decline )
	{
		this.decline = decline;
	}

	public Text getLicenceInf( )
	{
		return licenceInf;
	}

	public void setLicenceInf( Text licenceInf )
	{
		this.licenceInf = licenceInf;
	}

	public Shell getLicenceShell( )
	{
		return licenceShell;
	}

	public void setLicenceShell( Shell licenceShell )
	{
		this.licenceShell = licenceShell;
	}

	public Button getOkButton( )
	{
		return okButton;
	}

	public void setOkButton( Button okButton )
	{
		this.okButton = okButton;
	}

	public Group getTextGroup( )
	{
		return textGroup;
	}

	public void setTextGroup( Group textGroup )
	{
		this.textGroup = textGroup;
	}

	public Group getOkGroup( )
	{
		return okGroup;
	}

	public void setOkGroup( Group okGroup )
	{
		this.okGroup = okGroup;
	}

}
