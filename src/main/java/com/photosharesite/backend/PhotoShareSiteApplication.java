package com.photosharesite.backend;

import com.photosharesite.backend.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.config.ScannerFactory;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.DefaultJaxrsScanner;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
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
        // This allows you to host swagger ui on this dropwizard app's host
        final AssetsBundle assetsBundle = new AssetsBundle("/swagger-ui", "/swagger-ui", "index.html");
        bootstrap.addBundle(assetsBundle);
    }

    @Override
    public void run(final PhotoShareSiteConfiguration configuration,
                    final Environment environment) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
        // init Swagger resources
        initSwagger(configuration, environment);

        final HelloWorldResource resource = new HelloWorldResource(
                jdbi,
                "Hello %s!",
                "Stranger"
        );
        environment.jersey().register(resource);
    }
    private void initSwagger(PhotoShareSiteConfiguration configuration, Environment environment) {
        // Swagger Resource
        // The ApiListingResource creates the swagger.json file at localhost:8080/swagger.json
        environment.jersey().register(new ApiListingResource());
        environment.jersey().register(SwaggerSerializers.class);

        Package objPackage = this.getClass().getPackage();
        String version = objPackage.getImplementationVersion();

        // Swagger Scanner, which finds all the resources for @Api Annotations
        ScannerFactory.setScanner(new DefaultJaxrsScanner());

        //This is what is shown when you do "http://localhost:8080/swagger-ui/"
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion(version);
        beanConfig.setSchemes(new String[] { "http" });
        beanConfig.setHost("localhost:8080");
        beanConfig.setPrettyPrint(true);
        beanConfig.setDescription("Photo Share Site");
        beanConfig.setResourcePackage("com.photosharesite.backend.resources");
        beanConfig.setScan(true);
    }

}
