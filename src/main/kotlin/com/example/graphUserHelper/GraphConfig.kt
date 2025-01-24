package com.example.graphUserHelper

import com.azure.identity.ClientSecretCredential
import com.azure.identity.ClientSecretCredentialBuilder
import com.microsoft.graph.serviceclient.GraphServiceClient
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class GraphConfig {

    @Bean
    open fun clientSecretCredential(
        @Value(value = "\${exo.appId}") clientId: String,
        @Value(value = "\${exo.secret}") secret: String,
        @Value(value = "\${exo.tenantId}") tenantId: String

    ): ClientSecretCredential {
        if(clientId == "" || secret == "" || tenantId == ""){
            throw IllegalArgumentException("Please provide valid values for exo.appId, exo.secret and exo.tenantId in the application-default.yml file!")
        }
        return ClientSecretCredentialBuilder()
            .clientId(clientId)
            .clientSecret(secret)
            .tenantId(tenantId)
            .build()
    }

    @Bean
    open fun httpClient(
        clientSecretCredential: ClientSecretCredential,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(AccessTokenInterceptor(clientSecretCredential))

        return builder.build()
    }

    @Bean
    open fun graphServiceClient(okHttpClient: OkHttpClient): GraphServiceClient {
        return GraphServiceClient(okHttpClient)
    }
}