package com.agritech.pejantaraapp.di

import android.content.Context
import androidx.room.Room
import com.agritech.pejantaraapp.R
import com.agritech.pejantaraapp.data.database.Database
import com.agritech.pejantaraapp.data.database.LaporanDao
import com.agritech.pejantaraapp.data.database.TrashDao
import com.agritech.pejantaraapp.data.repository.EdukasiRepository
import com.agritech.pejantaraapp.data.repository.TrashRepository
import com.agritech.pejantaraapp.data.repository.TutorialRepository
import com.agritech.pejantaraapp.data.retrofit.ApiService
import com.agritech.pejantaraapp.data.retrofit.RetrofitFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApi(): ApiService {
        return RetrofitFactory.makeRetrofitService()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context, Database::class.java, "ecodo_db",
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideLaporanDao(database: Database): LaporanDao {
        return database.laporanDao()
    }

    @Provides
    @Singleton
    fun provideTrashDao(database: Database): TrashDao {
        return database.trashDao()
    }

    @Provides
    @Singleton
    fun provideTrashRepository(
        apiService: ApiService,
        database: Database,
        tutorialRepository: TutorialRepository
    ): TrashRepository {
        return TrashRepository(
            trashDao = database.trashDao(),
            apiService = apiService,
            tutorialRepository = tutorialRepository
        )
    }

    @Provides
    @Singleton
    fun provideVideoRepository(apiService: ApiService): TutorialRepository {
        return TutorialRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideArticleRepository(apiService: ApiService): EdukasiRepository {
        return EdukasiRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideGoogleSignInClient(@ApplicationContext context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id)) // default_web_client_id dari google-services.json
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}


