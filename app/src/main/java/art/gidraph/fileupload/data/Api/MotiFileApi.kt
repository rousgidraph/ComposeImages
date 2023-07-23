package art.gidraph.fileupload.data.Api

import art.gidraph.fileupload.data.Dto.UploadDto
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


/**
 * <precode>
 *     this class contains the api calls we need to make to the blob api.
 * </precode>
 * **/
interface MotiFileApi {


    @Multipart
    @POST("/file/upload")
    suspend fun uploadFile(
        @Part image: MultipartBody.Part
    ): UploadDto

    companion object {
        const val BASE_URL = "https://moticdn.azurewebsites.net"
        const val imageTag = "file"
    }
}