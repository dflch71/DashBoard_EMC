package com.dflch.dashboardemc.data.remote.api.connect

import com.dflch.dashboardemc.core.utils.Constants.MEDICIONES_IRCA
import com.dflch.dashboardemc.data.remote.api.connect.interceptor.AuthInterceptor
import com.dflch.dashboardemc.data.remote.api.irca.IrcaApiService
import com.dflch.dashboardemc.domain.repository.irca.IrcaRepository
import com.dflch.dashboardemc.domain.repository.irca.IrcaRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IRCAApiModule {

    @Singleton
    @Provides
    @Named("irca")
    fun provideIRCARetrofit(@Named("irca") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(MEDICIONES_IRCA)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("irca")
    fun provideOkHttpclient(authInterceptor: AuthInterceptor): OkHttpClient {

        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideIRCAApiService(@Named("irca") retrofit: Retrofit): IrcaApiService =
        retrofit.create(IrcaApiService::class.java)

    @Provides
    @Singleton
    fun provideIRCARepository(apiService: IrcaApiService): IrcaRepository =
        IrcaRepositoryImp(apiService)
}
