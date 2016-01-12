/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase.movies.boundary.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JANARTHANANS
 */
@XmlAccessorType (XmlAccessType.FIELD)
@XmlRootElement
public class ValidationError {
    
    private String attribute;
    
    private String[] message;
    
    /**
     * Default Constructor
     */
    public ValidationError() {
    }
    /**
     * 
     * @param attribute
     * @param message 
     */
    public ValidationError(String attribute, String[] message) {
        this.attribute = attribute;
        this.message = message;
    }
    /**
     * 
     * @return 
     */
    public String getAttribute() {
        return attribute;
    }
    /**
     * 
     * @return 
     */
    public String[] getMessage() {
        return message;
    }
    
}
