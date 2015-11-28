/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase;

import java.io.IOException;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author JANARTHANANS
 */
@Provider
@PreMatching
public class CORSResponseFilter implements ContainerResponseFilter{

    /**
     * 
     * @param requestContext
     * @param responseContext
     * @throws IOException 
     */
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (requestContext.getRequest().getMethod().equalsIgnoreCase(HttpMethod.OPTIONS)) {
            requestContext.abortWith(Response.ok().build());
        }
    }
    
}
