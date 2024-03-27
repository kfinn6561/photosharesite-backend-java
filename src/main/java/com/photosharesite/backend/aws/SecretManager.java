package com.photosharesite.backend.aws;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class SecretManager {
    private final SecretsManagerClient secretsClient;
    private final Map<String,String> cache=new HashMap<>();

    @Inject
    public SecretManager(SecretsManagerClient secretsClient) {
        this.secretsClient = secretsClient;
    }

    public String getValue(String secretName) {
        String cachedValue=cache.get(secretName);
        if (nonNull( cachedValue )){
            return cachedValue;
        }

            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            GetSecretValueResponse valueResponse = secretsClient.getSecretValue(valueRequest);
            String secret=  valueResponse.secretString();
            if (nonNull(secret)){
                cache.put(secretName,secret);
            }
            return secret;
    }
}
