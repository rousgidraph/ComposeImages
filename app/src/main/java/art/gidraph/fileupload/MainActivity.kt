package art.gidraph.fileupload

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.core.net.toFile
import art.gidraph.fileupload.ui.theme.FileUploadTheme
import art.gidraph.fileupload.viewmodel.ImageViewModel
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity(

) {
    private val imageViewModel: ImageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FileUploadTheme {
                var selectedImageUri by remember {
                    mutableStateOf<Uri?>(null)
                }

                var selectedImageUris by remember {
                    mutableStateOf<List<Uri>>(emptyList())
                }

                val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                    onResult = { uri -> selectedImageUri = uri }
                )


                val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickMultipleVisualMedia(),
                    onResult = { uris -> selectedImageUris = uris }
                )


                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Button(onClick = {
                                singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }) {
                                Text(text = "Pick one photo")
                            }
                            Button(onClick = {
                                multiplePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }) {
                                Text(text = "Pick multiple photos")
                            }

                        }
                    }

                    item {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val tempFile = File(cacheDir, "handsomedog.jpg")
                                    tempFile.createNewFile()
                                    tempFile
                                        .outputStream()
                                        .use {
                                            assets
                                                .open("20210812_125352.jpg")
                                                .copyTo(it)
                                        }
                                    imageViewModel.uploadFile(tempFile)
                                },
                            contentScale = ContentScale.Crop
                        )
                    }

                    items(selectedImageUris) { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val type = contentResolver.getType(uri)
                                    val tempFile = File(cacheDir, uri.pathSegments.last()+".jpg")
                                    Log.i("File type", "file name and type "+uri.pathSegments.last()+"."+type)
                                    tempFile.createNewFile()
                                    tempFile
                                        .outputStream()
                                        .use {
//                                        assets.open("20210812_125352.jpg").copyTo(it)
                                            contentResolver
                                                .openInputStream(uri)!!
                                                .copyTo(it)

                                        }

                                    imageViewModel.uploadFile(tempFile)
                                },
                            contentScale = ContentScale.Crop
                        )
                    }
                }

            }
        }
    }
}

