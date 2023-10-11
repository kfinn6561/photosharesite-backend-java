package com.photosharesite.backend;

import com.photosharesite.backend.db.insertfile.InsertFileAccess;
import com.photosharesite.backend.db.insertorselectuser.InsertOrSelectUserAccess;
import com.photosharesite.backend.db.selectfiles.SelectFilesAccess;
import com.photosharesite.backend.db.userexists.UserExistsAccess;
import com.photosharesite.backend.endpoints.getfiles.GetFilesResource;
import com.photosharesite.backend.endpoints.lookupuser.LookupUserResource;
import com.photosharesite.backend.endpoints.uploadfile.UploadFilesResource;
import com.photosharesite.backend.exceptions.EntityNotFoundExceptionMapper;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.jdbi.v3.core.Jdbi;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

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
        bootstrap.addBundle(new MultiPartBundle());
    }

    @Override
    public void run(final PhotoShareSiteConfiguration configuration,
                    final Environment environment) {

        // initialise JDBI database connection
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");

        // initialise Swagger
        initSwagger(configuration, environment);

        // Set up AWS client

        final S3Client s3Client = S3Client.builder()
                .region(Region.EU_WEST_1)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .forcePathStyle(true)
                .build();

        // create and register lookupUser resource
        final LookupUserResource lookupUserResource = new LookupUserResource(
                new InsertOrSelectUserAccess(jdbi)
        );
        environment.jersey().register(lookupUserResource);

        // create and register GetFilesResource
        final GetFilesResource getFilesResource = new GetFilesResource(
                new SelectFilesAccess(jdbi)
        );
        environment.jersey().register(getFilesResource);

        // create and register UploadFilesResource
        final UploadFilesResource uploadFilesResource = new UploadFilesResource(
                s3Client,
                new UserExistsAccess(jdbi),
                new InsertFileAccess(jdbi),
                configuration.getMediaFilesBucketName()
        );
        environment.jersey().register(uploadFilesResource);

        //register exception mappers
        environment.jersey().register(new EntityNotFoundExceptionMapper());
    }
    private void initSwagger(PhotoShareSiteConfiguration configuration, Environment environment) {
        // Swagger Resource
        OpenAPI oas = new OpenAPI();
        Info info = new Info()
                .title("Photo Share Site API")
                .description("API for the photo share site backend")
                .termsOfService("http://example.com/terms");

        oas.info(info);
        SwaggerConfiguration oasConfig = new SwaggerConfiguration()
                .openAPI(oas)
                .prettyPrint(true)
                .resourcePackages(Stream.of("com.photosharesite.backend.endpoints")
                        .collect(toSet()));
        environment.jersey().register(new OpenApiResource()
                .openApiConfiguration(oasConfig));
    }

}
