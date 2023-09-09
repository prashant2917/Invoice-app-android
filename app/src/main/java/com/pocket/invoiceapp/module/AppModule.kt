package com.pocket.invoiceapp.module

import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pocket.invoiceapp.base.BaseUseCase
import com.pocket.invoiceapp.firebasedatabase.FirebaseDatabaseHelper
import com.pocket.invoiceapp.register.repsitory.RegisterRepository
import com.pocket.invoiceapp.register.repsitory.RegisterRepositoryImpl
import com.pocket.invoiceapp.register.usecase.RegisterUseCase
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
    fun provideValidator(): Validator {
        return Validator()
    }

    @Singleton
    @Provides
    fun provideStringResourceProvider(@ApplicationContext context: Context): StringResourcesProvider {
        return StringResourcesProvider(context)
    }

   /* @Singleton
    @Provides
    fun providesDatabaseReference():DatabaseReference {
        return Firebase.database.reference
    }*/

    @Singleton
    @Provides
    fun providesFireBaseDatabaseHelper(stringResourcesProvider: StringResourcesProvider) : FirebaseDatabaseHelper {
        return FirebaseDatabaseHelper(stringResourcesProvider)
    }

    @Singleton
    @Provides
    fun providesRegisterRepository(databaseHelper: FirebaseDatabaseHelper) : RegisterRepository {
        return RegisterRepositoryImpl(databaseHelper)
    }

    @Singleton
    @Provides
    fun providesRegisterUseCase(repository: RegisterRepository) : BaseUseCase {
        return RegisterUseCase(repository)
    }

}