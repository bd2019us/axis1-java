/*
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Ant", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
package test.wsdl;

import org.apache.axis.wsdl.fromJava.Emitter;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import java.util.HashMap;
import java.io.StringWriter;
import java.io.PrintWriter;

/**
 * Simple Ant task for running Java2Wsdl utility. 
 *
 * @author Rich Scheuerle (scheu@us.ibm.com)
 */
public class Java2WsdlAntTask extends Task
{
    private String namespace = "";
    private HashMap namespaceMap = new HashMap();
    private String location = "";
    private String output = "." ;
    private String className = "." ;
    private String service = null ;
    private String implClass = null;
    private String factory   = null;
    private boolean useInheritedMethods = false;
    private String exclude = null;
    private String stopClasses = null;

    // The method executing the task
    public void execute() throws BuildException {
        try {
            log("Running Java2WsdlAntTask with parameters:", Project.MSG_VERBOSE);
            log("\tnamespace:" + namespace, Project.MSG_VERBOSE);
            log("\tPkgtoNS:" + namespaceMap, Project.MSG_VERBOSE);
            log("\tlocation:" + location, Project.MSG_VERBOSE);
            log("\toutput:" + output, Project.MSG_VERBOSE);
            log("\tclassName:" + className, Project.MSG_VERBOSE);
            log("\timplClass:" + implClass, Project.MSG_VERBOSE);
            log("\tfactory:"   + factory,   Project.MSG_VERBOSE);
            log("\tinheritance:" + useInheritedMethods, Project.MSG_VERBOSE);
            log("\texcluded:" + exclude, Project.MSG_VERBOSE);
            log("\tstopClasses:" + stopClasses, Project.MSG_VERBOSE);
            
            // Instantiate the emitter
            Emitter emitter = new Emitter();

            if (!namespaceMap.isEmpty()) {
                emitter.setNamespaceMap(namespaceMap);
            }
            if (service != null)
                emitter.setServiceName(service);
            log("Java2WSDL " + className, Project.MSG_INFO);
            emitter.setCls(className);
            if (implClass != null)
                emitter.setImplCls(implClass);
            if (factory != null)
                emitter.setFactory(factory);
            if (exclude != null)
                emitter.setDisallowedMethods(exclude);
            if (stopClasses != null)
                emitter.setStopClasses(stopClasses);
            emitter.setIntfNamespace(namespace);
            emitter.setLocationUrl(location);
            emitter.setUseInheritedMethods(useInheritedMethods);
            emitter.emit(output, Emitter.MODE_ALL);
        } catch (Throwable t) {
            StringWriter writer = new StringWriter();
            t.printStackTrace(new PrintWriter(writer));
            log(writer.getBuffer().toString(), Project.MSG_ERR);
            throw new BuildException("Error while running " + getClass().getName(), t);
        }
    }

    // The setter for the "output" attribute
    public void setOutput(String parameter) {
        this.output = parameter;
    }

    // The setter for the "location" attribute
    public void setLocation(String parameter) {
        this.location = parameter;
    }

    // The setter for the "className" attribute
    public void setClassName(String parameter) {
        this.className = parameter;
    }

    // The setter for the "implClass" attribute
    public void setImplClass(String parameter) {
        this.implClass = parameter;
    }

    // The setter for the "factory" attribute
    public void setFactory(String parameter) {
        this.factory = parameter;
    }

    // The setter for the "service" attribute
    public void setService(String parameter) {
        this.service = parameter;
    }

    // The setter for the "namespace" attribute
    public void setNamespace(String parameter) {
        this.namespace = parameter;
    }

    // The setter for the "useInheritedMethods" attribute
    public void setUseInheritedMethods(boolean parameter) {
        this.useInheritedMethods = parameter;
    }

    // The setter for the "exclude" attribute
    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    // The setter for the "stopClasses" attribute
    public void setStopClasses(String stopClasses) {
        this.stopClasses = stopClasses;
    }

    /** the command arguments */
    public Mapping createMapping() {
        Mapping pkg = new Mapping();
        return pkg;
    }

    /**
     * Used for nested package definitions.
     */
    public class Mapping {
        private String namespace = null;
        private String packageName = null;

        public void setNamespace(String value) {
            namespace = value;
            if(namespace != null && packageName != null) {
                namespaceMap.put(packageName,namespace);
                namespace = null; 
                packageName = null;
            }
        }

        public void setPackage(String value) {
            packageName = value;
            if(namespace != null && packageName != null) {
                namespaceMap.put(packageName,namespace);
                namespace = null;
                packageName = null;
            }
        }
    }
}


