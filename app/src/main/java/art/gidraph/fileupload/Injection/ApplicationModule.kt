package art.gidraph.fileupload.Injection

import android.util.Log
import art.gidraph.fileupload.data.Api.MotiFileApi
import art.gidraph.fileupload.data.repository.BlobRepo
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    // this is where we define the items we need to inject or auto wire
    val TAG = "HTTP LOG : "
    @Provides
    @Singleton
    fun provideFileApi():MotiFileApi {
        val loggingInterceptor = HttpLoggingInterceptor {
            Log.i(TAG, it)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val fileApi = Retrofit.Builder()
            .baseUrl(MotiFileApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(MotiFileApi::class.java)

    return fileApi
    }

    @Provides
    @Singleton
    fun provideBlobRepo(fileApi :MotiFileApi): BlobRepo = BlobRepo(fileApi)
}