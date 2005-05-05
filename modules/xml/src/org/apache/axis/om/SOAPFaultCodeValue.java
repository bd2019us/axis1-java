package org.apache.axis.om;

/**
 * Copyright 2001-2004 The Apache Software Foundation.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * <p/>
 */
public interface SOAPFaultCodeValue extends OMElement{
    /**
     * Eran Chinthaka (chinthaka@apache.org)
     */

    /**
     *
     * @param envFaultCodeEnum - Look in @see SOAP12Constants class for
     * valid SOAP fault codes covering high level SOAP faults. Look for
     * constants starting with SOAP_FAULT_VALUE_
     */
    public void setValue(String envFaultCodeEnum);

    /**
     * @return
     */
    public String getValue();
}