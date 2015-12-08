/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase.movies.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JANARTHANANS
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Message {

    /**
     * 
     */
    protected enum Type {
        INFO,
        ERROR;
    }
    
    /**
     * 
     */
    public enum Code {
        CREATION_FAILED("1001","Creation failed, server error", Type.ERROR),
        CREATION_SUCCESS("1002","created",Type.INFO),
        READ_FAILED("2001","Read failed", Type.ERROR),
        UPDATE_FAILED("3001","Updation failed, server error", Type.ERROR),
        DELETION_FAILED("4001","Deletion failed", Type.ERROR),
        DELETION_SUCCESS("4002","Movie deleted",Type.INFO),
        RECORD_DUPLICATE("6001","A movie with same id exist", Type.ERROR),
        RECORD_NOT_FOUND("6002","Movie with specified id does not exist", Type.ERROR),
        DATA_MISMATCH("6003","Id in path does not match with Id in data", Type.ERROR),
        CONSTRAINT_VIOLATION("5001","constraint violated", Type.ERROR);
        
        private final String id;
        private final String content;
        private final Type type;
        
        Code(String id, String content, Type type) {
            this.id = id;
            this.content = content;
            this.type = type;
        }
    }
    
    private String content;
    private String id;
    private Type type;

    /**
     * 
     */
    public Message() {
    }

    /**
     * 
     * @param code 
     */
    public Message(Code code) {
        this.id = code.id;
        this.content = code.content;
        this.type = code.type;
    }
            
}
