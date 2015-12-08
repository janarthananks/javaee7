/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase;

import java.io.IOException;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author JANARTHANANS
 */
@Provider
@PreMatching
public class CORSRequestFilter implements ContainerRequestFilter{

    /**
     * 
     * @param requestContext
     * @throws IOException 
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (requestContext.getRequest().getMethod().equalsIgnoreCase(HttpMethod.OPTIONS)) {
            requestContext.abortWith(Response.ok().build());
        }
    }
    
}
