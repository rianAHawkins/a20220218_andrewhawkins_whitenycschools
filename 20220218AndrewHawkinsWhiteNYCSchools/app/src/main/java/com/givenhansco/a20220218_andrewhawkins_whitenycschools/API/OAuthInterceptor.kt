package com.givenhansco.a20220218_andrewhawkins_whitenycschools.API

import okhttp3.Interceptor

class OAuthInterceptor(private val tokenType: String, private val accessToken: String) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        if(request.header("No-Authentication")==null) {
            request =
                request.newBuilder().header("Authorization", "$tokenType $accessToken").build()
        }
        return chain.proceed(request)
    }
}