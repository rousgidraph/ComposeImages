package art.gidraph.fileupload.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import art.gidraph.fileupload.data.repository.BlobRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class ImageViewModel @Inject constructor(
    private val repo: BlobRepo
) : ViewModel() {

    fun uploadFile (file: File){
        viewModelScope.launch {
            repo.uploadImage(file)
        }
    }
}