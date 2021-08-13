package eu.bbsapps.notecompose.ui.util

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.bbsapps.notecompose.ui.addeditnote.AddEditNoteScreen
import eu.bbsapps.notecompose.ui.login.LoginScreen
import eu.bbsapps.notecompose.ui.notelist.NotesScreen
import eu.bbsapps.notecompose.ui.register.RegisterScreen
import eu.bbsapps.notecompose.ui.splash.SplashScreen

@ExperimentalMaterialApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screen.NotesScreen.route) {
            NotesScreen(navController = navController)
        }
        composable(
            Screen.AddEditNoteScreen.route + "{noteId}"
        )
        { backStackEntry ->
            println(backStackEntry.arguments?.getString("noteId") ?: "NO ID")
            AddEditNoteScreen(
                navController = navController,
                noteId = backStackEntry.arguments?.getString("noteId") ?: ""
            )
        }
    }
}