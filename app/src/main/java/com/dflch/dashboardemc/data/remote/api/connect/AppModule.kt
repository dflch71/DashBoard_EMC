package com.dflch.dashboardemc.data.remote.api.connect

import com.dflch.dashboardemc.core.utils.Constants.MEDICIONES_URL
import com.dflch.dashboardemc.data.remote.api.connect.interceptor.AuthInterceptor
import com.dflch.dashboardemc.data.remote.api.nivelrio.NivelRioApiService
import com.dflch.dashboardemc.data.remote.api.niveltanque.NivelTanqueT1P1ApiService
import com.dflch.dashboardemc.data.remote.api.niveltanque.NivelTanqueT1P2ApiService
import com.dflch.dashboardemc.data.remote.api.niveltanque.NivelTanqueT2P2ApiService
import com.dflch.dashboardemc.data.remote.api.turbiedad.TurbiedadP1ApiService
import com.dflch.dashboardemc.data.remote.api.turbiedad.TurbiedadP2ApiService
import com.dflch.dashboardemc.domain.repository.nivelrio.NivelRioRepository
import com.dflch.dashboardemc.domain.repository.nivelrio.NivelRioRepositoryImp
import com.dflch.dashboardemc.domain.repository.niveltanque.NivelTanqueT1P1Repository
import com.dflch.dashboardemc.domain.repository.niveltanque.NivelTanqueT1P1RepositoryImp
import com.dflch.dashboardemc.domain.repository.niveltanque.NivelTanqueT1P2Repository
import com.dflch.dashboardemc.domain.repository.niveltanque.NivelTanqueT1P2RepositoryImp
import com.dflch.dashboardemc.domain.repository.niveltanque.NivelTanqueT2P2Repository
import com.dflch.dashboardemc.domain.repository.niveltanque.NivelTanqueT2P2RepositoryImp
import com.dflch.dashboardemc.domain.repository.turbiedad.TurbiedadP1Repository
import com.dflch.dashboardemc.domain.repository.turbiedad.TurbiedadP1RepositoryImp
import com.dflch.dashboardemc.domain.repository.turbiedad.TurbiedadP2Repository
import com.dflch.dashboardemc.domain.repository.turbiedad.TurbiedadP2RepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(MEDICIONES_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
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
    fun provideNivelRioRepository(apiService: NivelRioApiService): NivelRioRepository {
        return NivelRioRepositoryImp(apiService)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): NivelRioApiService = retrofit.create(NivelRioApiService::class.java)


    @Provides
    @Singleton
    fun provideTurbiedadP1Repository(apiService: TurbiedadP1ApiService): TurbiedadP1Repository {
        return TurbiedadP1RepositoryImp(apiService)
    }

    @Provides
    @Singleton
    fun provideApiServiceTurbiedadP1(retrofit: Retrofit): TurbiedadP1ApiService = retrofit.create(
        TurbiedadP1ApiService::class.java)

    @Provides
    @Singleton
    fun provideTurbiedadP2Repository(apiService: TurbiedadP2ApiService): TurbiedadP2Repository {
        return TurbiedadP2RepositoryImp(apiService)
    }

    @Provides
    @Singleton
    fun provideApiServiceTurbiedadP2(retrofit: Retrofit): TurbiedadP2ApiService = retrofit.create(
        TurbiedadP2ApiService::class.java)

    @Provides
    @Singleton
    fun provideNivelTanqueT1P1Repository(apiService: NivelTanqueT1P1ApiService): NivelTanqueT1P1Repository {
        return NivelTanqueT1P1RepositoryImp(apiService)
    }

    @Provides
    @Singleton
    fun provideApiServiceNivelTanqueT1P1(retrofit: Retrofit): NivelTanqueT1P1ApiService = retrofit.create(
        NivelTanqueT1P1ApiService::class.java)

    @Provides
    @Singleton
    fun provideNivelTanqueT1P2Repository(apiService: NivelTanqueT1P2ApiService): NivelTanqueT1P2Repository {
        return NivelTanqueT1P2RepositoryImp(apiService)
    }

    @Provides
    @Singleton
    fun provideApiServiceNivelTanqueT1P2(retrofit: Retrofit): NivelTanqueT1P2ApiService = retrofit.create(
        NivelTanqueT1P2ApiService::class.java)

    @Provides
    @Singleton
    fun provideNivelTanqueT2P2Repository(apiService: NivelTanqueT2P2ApiService): NivelTanqueT2P2Repository {
        return NivelTanqueT2P2RepositoryImp(apiService)
    }

    @Provides
    @Singleton
    fun provideApiServiceNivelTanqueT2P2(retrofit: Retrofit): NivelTanqueT2P2ApiService = retrofit.create(
        NivelTanqueT2P2ApiService::class.java)

}