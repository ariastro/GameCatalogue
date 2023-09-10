package io.astronout.core.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.astronout.core.BuildConfig
import io.astronout.core.data.source.GamesDataStore
import io.astronout.core.data.source.local.LocalDataSource
import io.astronout.core.data.source.local.LocalDataSourceImpl
import io.astronout.core.data.source.local.room.GameDatabase
import io.astronout.core.data.source.remote.AuthInterceptor
import io.astronout.core.data.source.remote.RemoteDataSource
import io.astronout.core.data.source.remote.web.ApiService
import io.astronout.core.domain.repository.GamesRepository
import io.astronout.core.domain.usecase.GameInteractor
import io.astronout.core.domain.usecase.GameUsecase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context)
            .apply {
                collector(
                    ChuckerCollector(
                        context = context,
                        showNotification = BuildConfig.DEBUG,
                        retentionPeriod = RetentionManager.Period.ONE_DAY
                    )
                )
                maxContentLength(250_000L)
                alwaysReadResponseBody(false)
                if (!BuildConfig.DEBUG) {
                    redactHeaders("Authorization", "Bearer")
                    redactHeaders("Authorization", "Basic")
                }
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(chuckerInterceptor: ChuckerInterceptor): OkHttpClient {
        val hostname = "api.rawg.io"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/lwtzFdQjeD+EVzKCXKhXN6jZ1kiSkDrwxDsujuYErho=")
            .add(hostname, "sha256/81Wf12bcLlFHQAfJluxnzZ6Frg+oJ9PWY/Wrwur8viQ")
            .add(hostname, "sha256/hxqRlPTu1bMS/0DITB1SSu0vd4u/8l8TjPgfaAp63Gc=")
            .build()
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .addInterceptor(chuckerInterceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)
                .build()
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideGameDatabase(@ApplicationContext context: Context): GameDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("astronout".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context.applicationContext,
            GameDatabase::class.java,
            "game_database"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(gameDatabase: GameDatabase): LocalDataSource = LocalDataSourceImpl(gameDatabase)

    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource): GamesRepository {
        return GamesDataStore(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideGameUsecase(repo: GamesRepository): GameUsecase {
        return GameInteractor(repo)
    }

}