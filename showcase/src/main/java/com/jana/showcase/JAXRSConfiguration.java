/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase;

import com.jana.showcase.movies.boundary.MoviesResource;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author JANARTHANANS
 */
@ApplicationPath("/resources")
public class JAXRSConfiguration extends Application{
    
    /**
     * 
     * @return 
     */
    @Override
     public Set<Class<?>> getClasses() {
        HashSet<Class<?>> set = new HashSet<>();
        set.add(MoviesResource.class);
        set.add(CORSRequestFilter.class);
        set.add(CORSResponseFilter.class);
        return set;
     }
}
