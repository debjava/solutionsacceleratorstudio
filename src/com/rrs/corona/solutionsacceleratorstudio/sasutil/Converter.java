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
 *$Id: Converter.java,v 1.2 2006/07/28 13:27:40 redrabbit Exp $
 *$Source: /home/redrabbit/cvs_july_2006/corona/sources/main/solutionsacceleratorstudio/src/com/rrs/corona/solutionsacceleratorstudio/sasutil/Converter.java,v $
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
package com.rrs.corona.solutionsacceleratorstudio.sasutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * This is a utility class
 * which is useful for conversion
 * depending upon the
 * requirements of the Sas application
 * @author Debadatta Mishra
 *
 */
public class Converter {
	/**
	 * This method is used to 
	 * convert a HashSet to an
	 * ArrayList
	 * @param hashSet of type HashSet containing the elements of type String
	 * @return an ArrayList
	 */
	public ArrayList convertToArrayList(final HashSet hashSet) {
		final ArrayList aList = new ArrayList();
		final Iterator itr = hashSet.iterator();
		while (itr.hasNext()) {
			final String sName = (String) itr.next();
			aList.add(sName);
		}
		return aList;
	}

}
