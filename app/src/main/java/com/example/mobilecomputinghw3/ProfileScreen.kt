package com.example.mobilecomputinghw3

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.registerForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import java.io.File

@Composable
fun ProfileScreen(
    onNavigateToConversation: () -> Unit,
) {
    val context = LocalContext.current
    val resolver = context.contentResolver
    val file = File(context.filesDir, "profile_picture")
    val usernameFile = File(context.filesDir, "username")
    val iconSpacerSize = 48.dp

    Column {
        Row(
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Used to center Profile Text to center


            IconButton(
                onClick = onNavigateToConversation,
                modifier = Modifier.size(iconSpacerSize)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go back to Conversation",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Text(
                text = "Profile",
                fontSize = 40.sp,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(iconSpacerSize))
        }





            var pickedImage by remember {
                mutableStateOf<Uri?>(null)
            }

            val pickMedia =
                rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                    pickedImage = uri

                    if (uri != null) {
                        resolver.openInputStream(uri).use { stream ->
                            val outputStream = file.outputStream()
                            stream?.copyTo(outputStream)

                            outputStream.close()
                            stream?.close()
                        }
                    }

                }


        Column(modifier = Modifier.padding(8.dp)) {


//            // VARAS ALKU
            var imageUri by remember { mutableStateOf<Uri?>(Uri.parse(file.toURI().toString())) }
            val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri->
                imageUri = uri

                if (uri != null) {
                    resolver.openInputStream(uri).use { stream ->
                        val outputStream = file.outputStream()
                        Log.d("NewTag", "Selected URI: $uri")
                        stream?.copyTo(outputStream)

                        outputStream.close()
                        stream?.close()
                    }
                }
            }
//
//            Column {
//                Button(onClick = { launcher.launch("image/*") }) {
//                    Text(text = "Load Image")
//                }
//                Image(
//                    painter = rememberAsyncImagePainter(imageUri),
//                    contentDescription = "My Image",
//                    modifier = Modifier
//                        .clip(CircleShape)
//                        .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)
//                        .size(80.dp)
//                )
//            }
//            // VARAS LOPPU





            AsyncImage(

                model = file.toURI().toString() + "?timestamp=${System.currentTimeMillis()}",
                contentDescription = "profile picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                    .size(100.dp)
                    .clickable { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
//                    .clickable {launcher.launch("image/*") }

            )

            Spacer(modifier = Modifier.size(8.dp))

            var text by remember {
                mutableStateOf<String>(
                    usernameFile.readBytes().decodeToString()
                )
            }

            TextField(
                value = text,
                onValueChange = {
                    usernameFile.writeBytes(it.toByteArray())
                    text = it
                },
                label = { Text("Username") },
                singleLine = true
            )


        }


    }
}

@Composable
fun StoreImage(pickedImage: Uri?, filename: String = "profile_picture") {
    if (pickedImage != null) {
        val context = LocalContext.current

        // Open a specific media item using InputStream.
        val resolver = context.contentResolver
        resolver.openInputStream(pickedImage).use { stream ->

            val file = File(context.filesDir, filename)
            val outputStream = file.outputStream()

            stream?.copyTo(outputStream)
        }
    }


}


    @Composable
    fun PickMedia() {
        // Registers a photo picker activity launcher in single-select mode.
        val pickMedia =
            rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        // Launch the photo picker and let the user choose only images.
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }



@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreen { }
}
