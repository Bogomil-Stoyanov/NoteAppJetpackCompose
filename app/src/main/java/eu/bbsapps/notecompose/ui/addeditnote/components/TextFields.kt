package eu.bbsapps.notecompose.ui.addeditnote.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BasicTextField(
    modifier: Modifier = Modifier,
    value: String = "",

    textStyle: TextStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
    onTextChange: (String) -> Unit,
    maxLines: Int = Int.MAX_VALUE
) {
    Box(modifier = modifier.padding(4.dp)) {
        BasicTextField(
            value = value,
            onValueChange = onTextChange,
            textStyle = textStyle.copy(color = MaterialTheme.colors.onPrimary),
            maxLines = maxLines,
            cursorBrush = SolidColor(MaterialTheme.colors.primary),
        )
    }
}

@Composable
fun NoteTitleTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onTextChange: (String) -> Unit
) {
    BasicTextField(
        modifier,
        value = value,
        onTextChange = onTextChange,
        textStyle = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Light),
        maxLines = 2
    )
}

@Composable
fun NoteField(
    modifier: Modifier = Modifier,
    value: String = "",
    onTextChange: (String) -> Unit
) {
    BasicTextField(
        modifier,
        value = value,
        onTextChange = onTextChange,
        textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Light)
    )
}