package com.photosharesite.backend;

import com.photosharesite.backend.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

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
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");

        final HelloWorldResource resource = new HelloWorldResource(
                jdbi,
                "Hello %s!",
                "Stranger"
        );
        environment.jersey().register(resource);
    }

}
