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
@XmlAccessorType( XmlAccessType.FIELD)
@XmlRootElement
public class Meta {
    
    private int total;
    
    public Meta() {
    }
    
    public Meta(int total) {
        this.total = total;
    }
    
    public int getTotal() {
        return this.total;
    }
}
