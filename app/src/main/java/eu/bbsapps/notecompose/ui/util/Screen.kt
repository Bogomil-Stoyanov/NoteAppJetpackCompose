package eu.bbsapps.notecompose.ui.util

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object NotesScreen : Screen("notes_screen")
    object AddEditNoteScreen : Screen("notes_screen/")
   // object NoteDetailScreen : Screen("note_detail_screen/{noteId}")
}