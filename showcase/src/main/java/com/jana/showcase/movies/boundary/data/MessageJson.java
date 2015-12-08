/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase.movies.boundary.data;

import com.jana.showcase.movies.entity.Message;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JANARTHANANS
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class MessageJson {
    
    private Message[] messages;
    
    /**
     * 
     */
    public MessageJson() {
        
    }
    
    /**
     * 
     * @param messages 
     */
    public MessageJson(Message[] messages) {
        this.messages = messages;
    }
    
}
