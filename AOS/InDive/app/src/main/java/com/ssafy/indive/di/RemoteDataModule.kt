//
//import android.content.Context
//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
//import com.ssafy.indive.api.UserApi
//import com.ssafy.indive.utils.XAccessTokenInterceptor
//import com.ssafy.runwithme.api.*
//import com.ssafy.runwithme.utils.BASE_URL
//import com.ssafy.runwithme.utils.WEATHER_BASE_URL
//import com.ssafy.runwithme.utils.XAccessTokenInterceptor
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import javax.inject.Named
//import javax.inject.Singleton
//import javax.net.ssl.HostnameVerifier
//import javax.net.ssl.HttpsURLConnection
//import javax.net.ssl.SSLSocketFactory
//import javax.net.ssl.TrustManagerFactory
//import javax.net.ssl.X509TrustManager
//
//@Module
//@InstallIn(SingletonComponent::class)
//object RemoteDataModule {
//
//    // TrustManagerFactory DI
//    @Provides
//    @Singleton
//    fun provideTrustManagerFactory(@ApplicationContext context : Context): TrustManagerFactory{
//        return ApplicationClass.getTrustManagerFactory(context)
//    }
//
//    // SSLSocketFactory DI
//    @Provides
//    @Singleton
//    fun provideSSLSocketFactory(tmf: TrustManagerFactory): SSLSocketFactory{
//        return tmf.let {
//            ApplicationClass.getSSLSocketFactory(it)
//        }
//    }
//
//    // HostnameVerifier DI
//    @Provides
//    @Singleton
//    fun provideHostnameVerifier(): HostnameVerifier{
//        var VERIFY_DOMAIN = "i7d101.p.ssafy.io"
//        return HostnameVerifier { _, session ->
//            HttpsURLConnection.getDefaultHostnameVerifier().run {
//                verify(VERIFY_DOMAIN, session)
//            }
//        }
//    }
//
//    // HttpLoggingInterceptor DI
//    @Provides
//    @Singleton
//    fun provideInterceptor(): HttpLoggingInterceptor {
//        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//    }
//
//    // OkHttpClient DI
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(xAccessTokenInterceptor: XAccessTokenInterceptor, tmf: TrustManagerFactory,
//                            sslSocket: SSLSocketFactory, hostnameVerifier: HostnameVerifier): OkHttpClient {
//        return OkHttpClient.Builder()
//            .hostnameVerifier(hostnameVerifier)
//            .sslSocketFactory(sslSocket, tmf.trustManagers?.get(0) as X509TrustManager)
//            .addNetworkInterceptor(xAccessTokenInterceptor)
//            .build()
//    }
//
//    // Gson DI
//    @Provides
//    @Singleton
//    fun provideGson(): Gson {
//        return GsonBuilder().setLenient().create()
//    }
//
//    // Retrofit DI
//    @Provides
//    @Singleton
//    @Named("mainRetrofit")
//    fun provideRetrofitInstance(gson: Gson, client: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .client(client)
//            .build()
//    }
//
//    // WeatherRetrofit DI
//    @Provides
//    @Singleton
//    @Named("weatherRetrofit")
//    fun provideWeatherRetrofitInstance(gson: Gson): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(WEATHER_BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideWeatherApi(@Named("weatherRetrofit") retrofit : Retrofit) : WeatherApi {
//        return retrofit.create(WeatherApi::class.java)
//    }
//
//    // Oauth2Api DI
//    @Provides
//    @Singleton
//    fun provideOauth2Api(@Named("mainRetrofit") retrofit: Retrofit): Oauth2Api {
//        return retrofit.create(Oauth2Api::class.java)
//    }
//
//
//}