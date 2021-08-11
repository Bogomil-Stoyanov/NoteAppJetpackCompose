package eu.bbsapps.notecompose.ui.addeditnote.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun UnsavedChangesDialog(
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit,
    save: () -> Unit,
    dismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text("Unsaved changes")
            },
            confirmButton = {
                Button(
                    onClick = {
                        save()
                        setShowDialog(false)
                    },
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        dismiss()
                        setShowDialog(false)
                    },
                ) {
                    Text("Don't save")
                }
            },
            text = {
                Text("Your changes will be lost")
            },
        )
    }
}

@Composable
fun DeleteNoteDialog(
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit,
    delete: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text("Delete note")
            },
            confirmButton = {
                Button(
                    onClick = {
                        delete()
                        setShowDialog(false)
                    },
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        setShowDialog(false)
                    },
                ) {
                    Text("Cancel")
                }
            },
            text = {
                Text("Are you sure you want to delete this note?")
            },
        )
    }
}