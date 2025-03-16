package com.gouthamreddy.constitutionofindia.domain.di

import com.gouthamreddy.constitutionofindia.data.ApiService
import com.gouthamreddy.constitutionofindia.domain.AppRepository
import com.gouthamreddy.constitutionofindia.domain.usecase.FetchCombinedJSONDataUseCase
import com.gouthamreddy.constitutionofindia.domain.usecase.FetchPreambleJSONDataUseCase
import com.gouthamreddy.constitutionofindia.domain.usecase.FetchSchedulesJSONDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    @IoDispatcher
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO


    @Provides
    @Singleton
    fun provideAppRepository(apiService: ApiService): AppRepository {
        return AppRepository(apiService)
    }


/*    @Provides
    @Singleton
    fun providePDFDownloaderUseCase(appRepository: AppRepository): PDFDownloaderUseCase {
        return PDFDownloaderUseCase(appRepository)
    }*/

    @Provides
    @Singleton
    fun provideFetchCombinedJSONDataUseCase(appRepository: AppRepository): FetchCombinedJSONDataUseCase {
        return FetchCombinedJSONDataUseCase(appRepository)
    }
    @Provides
    @Singleton
    fun provideFetchSchedulesJSONDataUseCase(appRepository: AppRepository): FetchSchedulesJSONDataUseCase {
        return FetchSchedulesJSONDataUseCase(appRepository)
    }
    @Provides
    @Singleton
    fun provideFetchPreambleDataUseCase(appRepository: AppRepository): FetchPreambleJSONDataUseCase {
        return FetchPreambleJSONDataUseCase(appRepository)
    }


}