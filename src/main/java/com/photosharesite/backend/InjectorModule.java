package com.photosharesite.backend;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class InjectorModule extends AbstractModule {
  @Provides
  @Singleton
  public Jdbi ProvideJDBI(PhotoShareSiteConfiguration configuration, Environment environment) {
    JdbiFactory factory = new JdbiFactory();
    return factory.build(environment, configuration.getDataSourceFactory(), "mysql");
  }

  @Provides
  @Named("BUCKET_NAME")
  public String ProvideBucketName(PhotoShareSiteConfiguration configuration) {
    return configuration.getMediaFilesBucketName();
  }

  @Provides
  public S3Client ProvideS3Client() {
    return S3Client.builder()
        .region(Region.EU_WEST_1)
        .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
        .forcePathStyle(true)
        .build();
  }
}
