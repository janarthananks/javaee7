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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ErrorData {
    
    private String detail=null;
    private String status=null;
    private ErrorPointer source=null;
    
    public ErrorData() {
        
    }
    /**
     * 
     * @param detail
     * @param status
     * @param source 
     */
    public ErrorData(String source, String detail, String status) {
        this.status = status;
        this.source = new ErrorPointer(source);
        this.detail = detail;
    }
    /**
     * 
     * @param source
     * @param detail 
     */
    public ErrorData(String source, String detail) {
        this.source = new ErrorPointer(source);
        this.detail=detail;
    }
    /**
     * 
     * @param detail 
     */
    public ErrorData(String detail) {
        this.detail=detail;
    }
    /**
     * 
     * @return 
     */
    public String getDetail() {
        return detail;
    }

    public String getStatus() {
        return status;
    }

    public ErrorPointer getSource() {
        return source;
    }
    
}
