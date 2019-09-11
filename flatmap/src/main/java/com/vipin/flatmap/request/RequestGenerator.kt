package com.vipin.flatmap.request

import com.vipin.flatmap.utils.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by vipin.c on 11/09/2019
 */
class RequestGenerator {

    companion object {

        fun create(): RequestAPi {

            val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(RequestAPi::class.java)
        }
    }
}