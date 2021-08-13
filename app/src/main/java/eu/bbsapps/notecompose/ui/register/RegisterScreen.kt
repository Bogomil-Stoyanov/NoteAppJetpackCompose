package eu.bbsapps.notecompose.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eu.bbsapps.notecompose.ui.components.PasswordTextField
import eu.bbsapps.notecompose.ui.util.Screen
import eu.bbsapps.notecompose.ui.util.mediumDp
import eu.bbsapps.notecompose.ui.util.smallDp
import eu.bbsapps.notecompose.util.Resource
import eu.bbsapps.notecompose.util.Status

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = hiltViewModel()) {

    val snackbarHostState = remember { SnackbarHostState() }
    val loginStatus: Resource<String>? by viewModel.registerStatus.observeAsState(null)
    var isProgressBarVisible by remember { mutableStateOf(false) }

    LaunchedEffect(loginStatus) {
        when (loginStatus?.status) {
            Status.SUCCESS -> {
                isProgressBarVisible = false
                snackbarHostState.showSnackbar(loginStatus?.message?:"Successfully created account!")
            }
            Status.ERROR -> {
                isProgressBarVisible = false

                snackbarHostState.showSnackbar(loginStatus?.message!!)

            }
            Status.LOADING -> {

            }
        }
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)
    ) {
        Column(Modifier.padding(mediumDp)) {

            Spacer(modifier = Modifier.height(mediumDp))

            IconButton(onClick = {
                navController.popBackStack(Screen.LoginScreen.route, true)
                navController.navigate(Screen.LoginScreen.route)
            }) {
                Icon(Icons.Outlined.ArrowBack, "Go back")
            }

            Text(text = "Create an account", fontSize = 32.sp)

            Spacer(modifier = Modifier.height(mediumDp))


            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                label = { Text(text = "Email") },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 16.sp
                ),
                value = viewModel.emailText.value,
                onValueChange = {
                    viewModel.setEmailText(it)
                },
            )

            Spacer(modifier = Modifier.height(smallDp))

            PasswordTextField(
                passwordText = viewModel.passwordText.value,
                onPasswordChanged = viewModel::setPasswordText,
                isPasswordVisible = viewModel.isPasswordVisible.value,
                onPasswordVisibilityChanged = viewModel::setIsPasswordVisible,
                isError = false,
                errorMessage = "",
                hint = "Password"
            )

            PasswordTextField(
                passwordText = viewModel.confirmPasswordText.value,
                onPasswordChanged = viewModel::setConfirmPasswordText,
                isPasswordVisible = viewModel.isConfirmPasswordVisible.value,
                onPasswordVisibilityChanged = viewModel::setIsConfirmPasswordVisible,
                isError = false,
                errorMessage = "",
                hint = "Confirm Password"
            )

            Spacer(modifier = Modifier.height(mediumDp))

            Button(onClick = {
                viewModel.register()
                isProgressBarVisible = true
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Create an account", fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.height(smallDp))

            SignInText {
                navController.popBackStack(Screen.LoginScreen.route, true)
                navController.navigate(Screen.LoginScreen.route)
            }
            if (isProgressBarVisible) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    CircularProgressIndicator()
                }
            }

        }
    }

}


@Composable
fun SignInText(onClick: () -> Unit) {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colors.onPrimary, fontSize = 16.sp)) {
            append("Already have an account? ")
        }
        pushStringAnnotation(
            tag = "sign_up",
            annotation = "Sign in"
        )
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.primary, fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("Sign in")
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = "sign_up", start = offset,
                end = offset
            )
                .firstOrNull()?.let {
                    println("click")
                    onClick()
                }
        }
    )
}