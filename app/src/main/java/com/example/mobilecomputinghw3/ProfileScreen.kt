package com.example.mobilecomputinghw3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    onNavigateToConversation: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()

        ) {

            IconButton(
                onClick = onNavigateToConversation,
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp)

            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go back to Conversation",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Text(text = "PROFILE (:", fontSize = 50.sp)
    }
}