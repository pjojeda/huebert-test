package com.tipitap.services;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/rest")
public class MyApplication extends ResourceConfig {

    public MyApplication() {
    	register(MultiPartFeature.class);
    	packages("com.tipitap.services");
    }
}
