package com.pocket.invoiceapp.module

import android.content.Context
import com.pocket.invoiceapp.authenticator.AuthRepository
import com.pocket.invoiceapp.authenticator.FirebaseAuthenticator
import com.pocket.invoiceapp.base.BaseAuthRepository
import com.pocket.invoiceapp.base.BaseAuthenticator
import com.pocket.invoiceapp.utils.StringResourcesProvider
import com.pocket.invoiceapp.validator.Validator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAuthenticator(): BaseAuthenticator {
        return FirebaseAuthenticator()
    }

    @Singleton
    @Provides
    fun provideAuthRepository(authenticator: BaseAuthenticator): BaseAuthRepository {
        return AuthRepository(authenticator)
    }

    @Singleton
    @Provides
    fun provideValidator(): Validator {
        return Validator()
    }

    @Singleton
    @Provides
    fun provideStringResourceProvider(@ApplicationContext context: Context): StringResourcesProvider {
        return StringResourcesProvider(context)
    }

}