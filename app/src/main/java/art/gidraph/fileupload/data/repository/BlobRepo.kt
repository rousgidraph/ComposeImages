package art.gidraph.fileupload.data.repository

import android.util.Log
import art.gidraph.fileupload.data.Api.MotiFileApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

class BlobRepo(
    private val fileApi : MotiFileApi
) {


    suspend fun uploadImage(file: File): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                fileApi.uploadFile(
                    MultipartBody.Part.createFormData(MotiFileApi.imageTag, file.name, file.asRequestBody())
                )
                Log.i("", "uploadImage: ")
                true
            } catch (e: HttpException) {
                false
            } catch (e: IOException) {
                false
            }
        }
    }
}