/******************************************************************************
*
* Created on Jan 27, 2006
*
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
package com.rrs.corona.server.sas;

public class Constants 
{
	transient public static final String   PROJECT_PATH_s                   = System.getenv("CORONA_HOME")+"/coronaprojects/currentprojects";
	transient public final static String   INITIAL_CONTEXT_FACTORY_s       	= "java.naming.factory.initial";
	transient public final static String   INITIAL_CONTEXT_FACTORY_VALUE_s 	= "org.jnp.interfaces.NamingContextFactory";
	transient public final static String   PROVIDER_URL_s                  	= "java.naming.provider.url";
	transient public final static String   URL_PKG_PREFIXES_s             	= "java.naming.factory.url.pkgs";
	transient public final static String   URL_PKG_PREFIXES_VALUE_s        	= "org.jboss.naming:org.jnp.interfaces";
	transient public final static String   UIL2_CONNECTION_FACTORY_s        = "UIL2ConnectionFactory";
	transient public final static String   HWS_AMLIST_REQUEST_s             = "INFO_REQ_HWS_AMLIST_MSG";
	transient public final static String   HWS_ALL_PROJECT_REQUEST_s        = "INFO_REQ_HWS_AMSYNC_MSG";
}
