package com.example.graphUserHelper

import com.azure.core.credential.TokenRequestContext
import com.azure.identity.ClientSecretCredential
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AccessTokenInterceptor (
    private val clientSecretCredential: ClientSecretCredential,
) : Interceptor {

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER = "Bearer "
        private const val SCOPE = "https://graph.microsoft.com/.default"
    }

    @Synchronized
    private fun getAccessToken(): String? {
        val tokenRequestContext = TokenRequestContext().apply {
            addScopes(SCOPE)
        }
        return clientSecretCredential.getTokenSync(tokenRequestContext).token
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = getAccessToken()
        val originalRequest = chain.request()
        val modifiedRequest: Request = if (accessToken != null) {
            originalRequest.newBuilder()
                .addHeader(AUTHORIZATION_HEADER, BEARER + accessToken)
                .build()
        } else {
            originalRequest
        }
        return chain.proceed(modifiedRequest)
    }
}