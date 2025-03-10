package com.gouthamreddy.constitutionofindia.di

import android.content.Context
import androidx.room.Room
import androidx.work.Configuration
import androidx.work.WorkManager
import com.gouthamreddy.constitutionofindia.data.models.ConstitutionDatabase
import com.gouthamreddy.constitutionofindia.utils.AppWorkerFactory
import com.gouthamreddy.constitutionofindia.utils.ConstitutionParser
import com.gouthamreddy.constitutionofindia.utils.PdfParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ConstitutionDatabase {
        return Room.databaseBuilder(
            context,
            ConstitutionDatabase::class.java,
            "constitution_database"
        ).build()
    }
    @Provides
    @Singleton
    fun providePdfParser(@ApplicationContext context: Context): PdfParser {
        return PdfParser()
    }
    @Provides
    @Singleton
    fun provideConstitutionParser(@ApplicationContext context: Context): ConstitutionParser {
        return ConstitutionParser(context)
    }

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context,
        appWorkerFactory: AppWorkerFactory
    ): WorkManager {
        val config = Configuration.Builder()
            .setWorkerFactory(appWorkerFactory)
            .build()
        WorkManager.initialize(context, config)
        return WorkManager.getInstance(context)
    }
}