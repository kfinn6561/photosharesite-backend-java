package com.photosharesite.backend;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PhotoShareSiteApplication extends Application<PhotoShareSiteConfiguration> {

    public static void main(final String[] args) throws Exception {
        new PhotoShareSiteApplication().run(args);
    }

    @Override
    public String getName() {
        return "PhotoShareSite";
    }

    @Override
    public void initialize(final Bootstrap<PhotoShareSiteConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final PhotoShareSiteConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
