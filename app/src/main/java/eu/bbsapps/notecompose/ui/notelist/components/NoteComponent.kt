package eu.bbsapps.notecompose.ui.notelist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.bbsapps.notecompose.data.local.entities.Note
import eu.bbsapps.notecompose.ui.theme.NoteComposeTheme
import eu.bbsapps.notecompose.ui.util.noteColors

@Composable
fun NoteComponent(note: Note, onClick: (String) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onClick(note.id)
        }
        .padding(8.dp)) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = { },
                shape = CircleShape,
                // colors = ButtonDefaults.buttonColors(backgroundColor = noteColors[viewModel.noteColorPosition.value]),
                colors = ButtonDefaults.buttonColors(backgroundColor = noteColors[note.color.toInt()]),
                modifier = Modifier.size(32.dp)
            ) {

            }
            /* Canvas(modifier = Modifier.height(20.dp)) {
                 drawCircle(
                     color = Color(101, 31, 255, 255),
                     radius = 40f
                 )
             }*/
            Spacer(modifier = Modifier.width(24.dp))
            Text(text = note.title, fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = note.content,
            fontSize = 18.sp,
            color = MaterialTheme.colors.onPrimary.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
@Preview
fun PreviewNoteComponent() {
    NoteComposeTheme() {
        Surface() {
            NoteComponent(
                note = Note(
                    title = "Title",
                    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                    date = 1111L,
                    emptyList(),
                    "5",
                    false
                ),
                {}
            )
        }
    }
}