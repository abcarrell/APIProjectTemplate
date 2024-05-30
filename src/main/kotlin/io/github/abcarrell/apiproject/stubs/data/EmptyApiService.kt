package io.github.abcarrell.apiproject.stubs.data

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun emptyApiService(
    packageName: String,
    className: String,
    baseUrl: String
) = """
package ${escapeKotlinIdentifier(packageName)}

import com.google.gson.GsonBuilder
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface $className {
    
    companion object {
        private const val BASE_URL = 
            "$baseUrl"
        
        fun create(): $className {
            val gson = GsonBuilder().create()
            
            val client = OkHttpClient.Builder()
                .connectTimeout(30L, TimeUnit.SECONDS)
                .readTimeout(30L, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
            
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create($className::class.java)
        }
    }
}
"""
