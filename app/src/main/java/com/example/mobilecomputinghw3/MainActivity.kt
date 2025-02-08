package com.example.mobilecomputinghw3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import java.io.File


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val context = LocalContext.current
            val usernameFile = File(context.filesDir, "username")


            if (!usernameFile.exists()) {
                usernameFile.writeBytes("Hullu".toByteArray())
            }

            AppNavigation(modifier = Modifier.systemBarsPadding())
        }
    }
}

@Serializable
object Conversation

@Serializable
object Profile


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Conversation
    ) {
        composable<Conversation> {
            ConversationScreen(
                onNavigateToProfile = {
                    navController.navigate(route = Profile)
                }
            )
        }
        composable<Profile> {
            ProfileScreen(
                onNavigateToConversation = {
                    navController.navigate(route = Conversation) {
                        popUpTo(Conversation) {
                            inclusive = true
                        }
                    }
                }
            )

        }
    }
}
