
package com.sunsea.parkinghere.webservice.client.user;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.12
 * 2017-08-20T14:57:07.046+08:00
 * Generated source version: 3.1.12
 * 
 */
public final class UserService_UserServicePort_Client {

    private static final QName SERVICE_NAME = new QName("http://user.webservice.yelr.com/", "UserServiceImplService");

    private UserService_UserServicePort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = UserServiceImplService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        UserServiceImplService ss = new UserServiceImplService(wsdlURL, SERVICE_NAME);
        UserService port = ss.getUserServicePort();  
        
        {
        System.out.println("Invoking getName...");
        java.lang.Long _getName_userId = null;
        java.lang.String _getName__return = port.getName(_getName_userId);
        System.out.println("getName.result=" + _getName__return);


        }

        System.exit(0);
    }

}
