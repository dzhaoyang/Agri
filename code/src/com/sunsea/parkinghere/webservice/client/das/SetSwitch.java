
package com.sunsea.parkinghere.webservice.client.das;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RWID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SwitchID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SwitchModeSequence" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "rwid",
    "switchID",
    "switchModeSequence"
})
@XmlRootElement(name = "SetSwitch")
public class SetSwitch {

    @XmlElement(name = "RWID")
    protected String rwid;
    @XmlElement(name = "SwitchID")
    protected String switchID;
    @XmlElement(name = "SwitchModeSequence")
    protected String switchModeSequence;

    /**
     * 获取rwid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRWID() {
        return rwid;
    }

    /**
     * 设置rwid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRWID(String value) {
        this.rwid = value;
    }

    /**
     * 获取switchID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSwitchID() {
        return switchID;
    }

    /**
     * 设置switchID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSwitchID(String value) {
        this.switchID = value;
    }

    /**
     * 获取switchModeSequence属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSwitchModeSequence() {
        return switchModeSequence;
    }

    /**
     * 设置switchModeSequence属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSwitchModeSequence(String value) {
        this.switchModeSequence = value;
    }

}
