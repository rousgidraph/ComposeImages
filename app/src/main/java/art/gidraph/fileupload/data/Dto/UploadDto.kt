package art.gidraph.fileupload.data.Dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UploadDto(

	@field:JsonProperty("fileName")
	val fileName: String? = null,

	@field:JsonProperty("statusReason")
	val statusReason: String? = null,

	@field:JsonProperty("success")
	val success: Boolean? = null,

	@field:JsonProperty("downloadUrl")
	val downloadUrl: String? = null
)
