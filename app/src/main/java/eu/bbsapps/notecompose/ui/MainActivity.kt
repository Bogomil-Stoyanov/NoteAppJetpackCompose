package eu.bbsapps.notecompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import eu.bbsapps.notecompose.ui.theme.NoteComposeTheme
import eu.bbsapps.notecompose.ui.util.Navigation

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets {
                NoteComposeTheme {
                    Surface(
                        color = MaterialTheme.colors.background,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Navigation()
                    }
                }
            }
        }
    }
}
