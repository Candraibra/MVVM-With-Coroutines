package id.candraibra.catmovie2.repository.network

import id.candraibra.catmovie2.BuildConfig
import id.candraibra.catmovie2.BuildConfig.API_KEY
import id.candraibra.catmovie2.BuildConfig.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val getRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val client: OkHttpClient by lazy {
        val interceptor = HttpLoggingInterceptor()
        val authInterceptor = Interceptor { chain ->
            val newUrl = chain.request().url
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()
            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }
        interceptor.level = if (BuildConfig.DEBUG) BODY else NONE
        OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(authInterceptor).build()
    }

    val getApi: ApiService by lazy {
        getRetrofit.create(ApiService::class.java)
    }
}