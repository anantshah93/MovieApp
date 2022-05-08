package iostudio.`in`.topmovies.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iostudio.`in`.topmovies.BuildConfig
import iostudio.`in`.topmovies.app.isDebugMode
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


private const val BASE_URL = BuildConfig.BASE_URL
private const val TIMEOUT_IN_MILLISECOND = 10000L

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun getCertificatePinner(): CertificatePinner = CertificatePinner.Builder()
        .add(BuildConfig.HOST_NAME, BuildConfig.PIN_CERTIFICATE_1)
        .add(BuildConfig.HOST_NAME, BuildConfig.PIN_CERTIFICATE_2)
        .add(BuildConfig.HOST_NAME, BuildConfig.PIN_CERTIFICATE_3)
        .build()

    @Singleton
    @Provides
    @Named("logging")
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (isDebugMode()) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Singleton
    @Provides
    @Named("header")
    fun provideHeaderInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .method(request.method, request.body)
            .build()
        chain.proceed(newRequest)
    }

    @Provides
    fun provideOkHttpClient(
        @Named("logging") logging: HttpLoggingInterceptor,
        @Named("header") header: Interceptor,
        certificatePinner:CertificatePinner
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_IN_MILLISECOND, TimeUnit.MILLISECONDS)
        .readTimeout(TIMEOUT_IN_MILLISECOND, TimeUnit.MILLISECONDS)
        .writeTimeout(TIMEOUT_IN_MILLISECOND, TimeUnit.MILLISECONDS)
        .certificatePinner(certificatePinner)
        .addInterceptor(logging)
        .addInterceptor(header)
        .build()

    @Singleton
    @Provides
    fun provideAppRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): IApiService = retrofit.create(IApiService::class.java)

}