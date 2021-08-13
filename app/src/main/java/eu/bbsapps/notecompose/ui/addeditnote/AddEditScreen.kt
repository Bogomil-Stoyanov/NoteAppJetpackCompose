package eu.bbsapps.notecompose.ui.addeditnote

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eu.bbsapps.notecompose.ui.addeditnote.components.DeleteNoteDialog
import eu.bbsapps.notecompose.ui.addeditnote.components.NoteField
import eu.bbsapps.notecompose.ui.addeditnote.components.NoteTitleTextField
import eu.bbsapps.notecompose.ui.addeditnote.components.UnsavedChangesDialog
import eu.bbsapps.notecompose.ui.util.Screen
import eu.bbsapps.notecompose.ui.util.largeDp
import eu.bbsapps.notecompose.ui.util.mediumDp
import eu.bbsapps.notecompose.ui.util.noteColors
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteId: String,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {

    if(noteId!="NO_NOTE")
    LaunchedEffect(key1 = noteId) {
        viewModel.getNoteById(noteId)
    }

    viewModel.noteColorPosition

    val noteColor = remember { mutableStateOf(viewModel.noteColorPosition.value) }

    LaunchedEffect(key1 = viewModel.note.value?.peekContent()?.data?.color) {
           noteColor.value = viewModel.note.value?.peekContent()?.data?.color?.toInt() ?: 0
    }


    LaunchedEffect(key1 = noteColor.value, key2 = viewModel.note.value?.peekContent()?.data) {
        viewModel.setNoteColorPosition(noteColor.value)
    }

    val (showUnsavedChangesDialog, setShowUnsavedChangesDialog) = remember { mutableStateOf(false) }
    val (showDeleteNoteDialog, setShowDeleteNoteDialog) = remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(top = largeDp)
    ) {
        PositionsBottomSheet(noteColor = noteColor) {
            Column(Modifier.padding(mediumDp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.ArrowBack,
                        contentDescription = "Back",
                        Modifier
                            .size(mediumDp)
                            .clickable {
                                if (viewModel.hasNoteChanged()) {
                                    setShowUnsavedChangesDialog(true)

                                } else {
                                    navController.popBackStack()
                                    navController.navigate(Screen.NotesScreen.route)
                                }
                            })

                    if (viewModel.note.value?.peekContent()?.data?.id != null) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = "Delete note",
                            Modifier
                                .size(mediumDp)
                                .clickable {
                                    println("clicked delete")
                                    setShowDeleteNoteDialog(true)
                                    /*if (viewModel.hasNoteChanged()) {
                                    setShowDialog(true)

                                } else {
                                    navController.popBackStack()
                                    navController.navigate(Screen.NotesScreen.route)
                                }*/
                                })
                    }
                }
                DeleteNoteDialog(
                    showDialog = showDeleteNoteDialog,
                    setShowDialog = setShowDeleteNoteDialog,
                    delete = {
                       viewModel.deleteNote()
                        navController.popBackStack()
                        navController.navigate(Screen.NotesScreen.route)
                    },
                )

                UnsavedChangesDialog(
                    showDialog = showUnsavedChangesDialog,
                    setShowDialog = setShowUnsavedChangesDialog,
                    save = {
                        viewModel.save()
                        navController.popBackStack()
                        navController.navigate(Screen.NotesScreen.route)
                    },
                    dismiss = {
                        navController.popBackStack()
                        navController.navigate(Screen.NotesScreen.route)
                    }
                )


                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = { },
                        shape = CircleShape,
                        // colors = ButtonDefaults.buttonColors(backgroundColor = noteColors[viewModel.noteColorPosition.value]),
                        colors = ButtonDefaults.buttonColors(backgroundColor = noteColors[noteColor.value]),
                        modifier = Modifier.size(mediumDp)
                    ) {

                    }
                    NoteTitleTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp, 16.dp, 0.dp)
                            .background(MaterialTheme.colors.background),
                        value = viewModel.noteTitle.value,
                        onTextChange = { viewModel.setNoteTitle(it) },
                    )
                }

                NoteField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .padding(16.dp, 8.dp, 16.dp, 0.dp)
                        .background(MaterialTheme.colors.background),
                    value = viewModel.noteContent.value,
                    onTextChange = { viewModel.setNoteContent(it) }
                )

                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            viewModel.save()
                            navController.popBackStack()
                            navController.navigate(Screen.NotesScreen.route)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                        shape = CircleShape,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(Icons.Outlined.Save, contentDescription = "Save a note")
                    }

                }
            }
        }
    }

}

@ExperimentalMaterialApi
@Composable
fun PositionsBottomSheet(
    noteColor: MutableState<Int>,
    content: @Composable (PaddingValues) -> Unit
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(0.dp),
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                } else {
                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                }
                            }
                        }) {

                    val image: ImageVector =
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            Icons.Outlined.KeyboardArrowUp
                        } else {
                            Icons.Outlined.KeyboardArrowDown
                        }

                    Icon(image, contentDescription = "")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Pick a note color", fontSize = 18.sp)
                    Icon(image, contentDescription = "")
                    Spacer(modifier = Modifier.width(4.dp))

                }

                Spacer(modifier = Modifier.height(48.dp))


                Column {
                    val noteIds = (0..15).toList()
                    noteIds.chunked(4).forEach { list ->
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            for (id in list) {
                                Button(
                                    onClick = {
                                        noteColor.value = id
                                        coroutineScope.launch {
                                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                                bottomSheetScaffoldState.bottomSheetState.expand()
                                            } else {
                                                bottomSheetScaffoldState.bottomSheetState.collapse()
                                            }
                                        }
                                    },
                                    shape = CircleShape,
                                    colors = ButtonDefaults.buttonColors(backgroundColor = noteColors[id]),
                                    modifier = Modifier.size(72.dp)
                                ) {}
                            }

                        }
                        Spacer(modifier = Modifier.height(mediumDp))
                    }
                }
            }
        },
        sheetPeekHeight = 84.dp,
        content = content
    )
}




