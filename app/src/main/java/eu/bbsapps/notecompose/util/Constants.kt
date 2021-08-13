package eu.bbsapps.notecompose.util

object Constants {
    const val SPLASH_SCREEN_DURATION = 2L

    val IGNORE_AUTH_URLS = listOf("/login","/register")
    const val DATABASE_NAME = "notes_db"
    // without https: const val BASE_URL = "http://10.0.2.2:8080"
    const val BASE_URL = "https://10.0.2.2:8081"
    //const val BASE_URL = "https://gentle-dawn-45824.herokuapp.com/"
    const val ENCRYPTED_SHARED_PREF_NAME = "enc_shared_pref"
    const val KEY_LOGGED_IN_EMAIL = "KEY_LOGGED_IN_EMAIL"
    const val KEY_PASSWORD = "KEY_PASSWORD"
    const val NO_EMAIL = "NO_EMAIL"
    const val NO_PASSWORD = "NO_PASSWORD"
}