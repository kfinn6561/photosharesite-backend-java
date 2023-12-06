package com.photosharesite.backend;

import static java.util.stream.Collectors.toSet;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import java.util.stream.Stream;
import ru.vyarus.dropwizard.guice.GuiceBundle;

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
    bootstrap.addBundle(
        GuiceBundle.builder().enableAutoConfig().modules(new InjectorModule()).build());
    // This allows you to host swagger ui on this dropwizard app's host
    final AssetsBundle assetsBundle = new AssetsBundle("/swagger-ui", "/swagger-ui", "index.html");
    bootstrap.addBundle(assetsBundle);
    bootstrap.addBundle(new MultiPartBundle());
  }

  @Override
  public void run(final PhotoShareSiteConfiguration configuration, final Environment environment) {

    // initialise Swagger
    initSwagger(environment);
  }

  private void initSwagger(Environment environment) {
    // Swagger Resource
    OpenAPI oas = new OpenAPI();
    Info info =
        new Info()
            .title("Photo Share Site API")
            .description("API for the photo share site backend")
            .termsOfService("http://example.com/terms");

    oas.info(info);
    SwaggerConfiguration oasConfig =
        new SwaggerConfiguration()
            .openAPI(oas)
            .prettyPrint(true)
            .resourcePackages(Stream.of("com.photosharesite.backend.endpoints").collect(toSet()));
    environment.jersey().register(new OpenApiResource().openApiConfiguration(oasConfig));
  }
}
