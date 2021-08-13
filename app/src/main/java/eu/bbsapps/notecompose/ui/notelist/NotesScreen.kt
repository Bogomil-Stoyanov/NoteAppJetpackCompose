package eu.bbsapps.notecompose.ui.notelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eu.bbsapps.notecompose.data.local.entities.Note
import eu.bbsapps.notecompose.ui.notelist.components.NoteComponent
import eu.bbsapps.notecompose.ui.util.Screen
import eu.bbsapps.notecompose.ui.util.largeDp
import eu.bbsapps.notecompose.ui.util.mediumDp
import eu.bbsapps.notecompose.util.Event
import eu.bbsapps.notecompose.util.Resource

@Composable
fun NotesScreen(navController: NavController, viewModel: NotesViewModel = hiltViewModel()) {

    val loginStatus: Event<Resource<List<Note>>>? by viewModel.allNotes.observeAsState(null)

    Scaffold(Modifier.padding(mediumDp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.popBackStack()
                navController.navigate(Screen.AddEditNoteScreen.route + "NO_NOTE")
            }, backgroundColor = MaterialTheme.colors.primary) {
                Icon(Icons.Outlined.Add, contentDescription = "Add a new note")
            }
        }

    ) {
        var expanded by remember { mutableStateOf(false) }
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(largeDp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Your notes", fontSize = 32.sp)
                    Column {
                        Icon(
                            Icons.Outlined.Menu, contentDescription = "More options",
                            Modifier
                                .size(mediumDp)
                                .clickable {
                                    expanded = !expanded
                                })
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            DropdownMenuItem(onClick = {
                                viewModel.logout()
                                navController.popBackStack()
                                navController.navigate(Screen.LoginScreen.route)
                            }) {
                                Text("Logout")
                            }

                            DropdownMenuItem(onClick = {
                                viewModel.syncAllNotes()
                            }) {
                                Text("Refresh")
                            }
                        }
                    }
                }
            }

            if (loginStatus?.peekContent()?.data != null) {
                items(loginStatus?.peekContent()?.data!!) { note ->
                    NoteComponent(note = note) { id ->
                        navController.navigate(Screen.AddEditNoteScreen.route+id)
                    }
                    Divider(color = MaterialTheme.colors.onPrimary.copy(alpha = 0.2f))
                }
                item { 
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}