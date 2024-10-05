package io.github.abcarrell.apiproject.stubs.data

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import com.android.tools.idea.wizard.template.renderIf
import io.github.abcarrell.apiproject.RetrofitConverter

fun emptyApiService(
    packageName: String,
    className: String,
    baseUrl: String,
    converter: RetrofitConverter
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
            "${baseUrl.plus("/").trimEnd('/')}"
        
        fun create(): $className {
            ${renderIf(converter == RetrofitConverter.Gson) { "val gson = GsonBuilder().create()" }}
            ${renderIf(converter == RetrofitConverter.Moshi) { "val moshi = Moshi.Builder().build()" }}
            
            val client = OkHttpClient.Builder()
                .connectTimeout(30L, TimeUnit.SECONDS)
                .readTimeout(30L, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
            
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                ${renderIf(converter != RetrofitConverter.None) {
                    ".addConverterFactory(${
                        when (converter) {
                            RetrofitConverter.Gson -> "GsonConverterFactory.create(gson)"
                            RetrofitConverter.Moshi -> "MoshiConverterFactory.create(moshi)"
                            else -> ""
                        }
                    })"
                }}
                .build()
                .create(${className}::class.java)
        }
    }
}
"""
