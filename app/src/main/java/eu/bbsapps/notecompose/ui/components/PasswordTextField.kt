package eu.bbsapps.notecompose.ui.components

import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.bbsapps.notecompose.ui.theme.errorColor

@Composable
fun PasswordTextField(
    passwordText: String,
    onPasswordChanged: (String) -> Unit,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChanged: (Boolean) -> Unit,
    isError:Boolean,
    errorMessage:String,
    hint:String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            label = { Text(text = hint) },
            trailingIcon = {
                IconButton({ onPasswordVisibilityChanged(!isPasswordVisible) }) {
                    if (isPasswordVisible) {
                        Icon(Icons.Outlined.VisibilityOff, contentDescription = "Password shown")
                    } else {
                        Icon(Icons.Outlined.Visibility, contentDescription = "Password hidden")
                    }
                }
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.onPrimary,
                fontSize = 16.sp
            ),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            value = passwordText,
            onValueChange = {
                onPasswordChanged(it)
            },
            isError = isError
        )
        Text(text = errorMessage, fontSize = 16.sp, color = errorColor, modifier = Modifier.padding(16.dp,0.dp,0.dp,0.dp))
    }
}