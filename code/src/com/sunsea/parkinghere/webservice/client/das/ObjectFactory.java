
package com.sunsea.parkinghere.webservice.client.das;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sunsea.parkinghere.webservice.client.das package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sunsea.parkinghere.webservice.client.das
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Test }
     * 
     */
    public Test createTest() {
        return new Test();
    }

    /**
     * Create an instance of {@link TestResponse }
     * 
     */
    public TestResponse createTestResponse() {
        return new TestResponse();
    }

    /**
     * Create an instance of {@link SetSwitch }
     * 
     */
    public SetSwitch createSetSwitch() {
        return new SetSwitch();
    }

    /**
     * Create an instance of {@link SetSwitchResponse }
     * 
     */
    public SetSwitchResponse createSetSwitchResponse() {
        return new SetSwitchResponse();
    }

    /**
     * Create an instance of {@link ReadSwitchStatus }
     * 
     */
    public ReadSwitchStatus createReadSwitchStatus() {
        return new ReadSwitchStatus();
    }

    /**
     * Create an instance of {@link ReadSwitchStatusResponse }
     * 
     */
    public ReadSwitchStatusResponse createReadSwitchStatusResponse() {
        return new ReadSwitchStatusResponse();
    }

}
