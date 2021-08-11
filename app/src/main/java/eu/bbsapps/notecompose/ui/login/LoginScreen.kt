package eu.bbsapps.notecompose.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
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
import eu.bbsapps.notecompose.util.Resource
import eu.bbsapps.notecompose.util.Status

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {

    LaunchedEffect(navController) {
        if (viewModel.isLoggedIn()) {
            navController.popBackStack()
            navController.navigate(Screen.NotesScreen.route)
        } else {
            println("not logged it")
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val loginStatus: Resource<String>? by viewModel.loginStatus.observeAsState(null)
    var isProgressBarVisible by remember { mutableStateOf(false) }

    LaunchedEffect(loginStatus) {
        when (loginStatus?.status) {
            Status.SUCCESS -> {
                viewModel.successfullyLoggedIn()
                navController.popBackStack()
                navController.navigate(Screen.NotesScreen.route)
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
        Column(Modifier.padding(24.dp)) {

            Spacer(modifier = Modifier.height(48.dp))

            Text(text = "Welcome back", fontSize = 32.sp)

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 0.dp),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                label = { Text(text = "Email") },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 16.sp
                ),
                value = viewModel.emailText.value,
                onValueChange = {
                    viewModel.setEmailText(it)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))


            /*  val isValidConfirmPassword = AuthValidator.isPasswordAndConfirmPasswordSame(
                  password.text,
                  confirmPassword.text
              )*/

            PasswordTextField(
                passwordText = viewModel.passwordText.value,
                onPasswordChanged = viewModel::setPasswordText,
                isPasswordVisible = viewModel.isPasswordVisible.value,
                onPasswordVisibilityChanged = viewModel::setIsPasswordVisible,
                isError = false,
                errorMessage = "",
                hint = "Password"
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                /*navController.popBackStack()
                navController.navigate(Screen.NotesScreen.route){}*/
                viewModel.login()
                isProgressBarVisible = true


            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Login", fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            SignUpText {
                navController.navigate(Screen.RegisterScreen.route)
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
fun SignUpText(onClick: () -> Unit) {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colors.onPrimary, fontSize = 16.sp)) {
            append("Don't have an account? ")
        }
        pushStringAnnotation(
            tag = "sign_up",
            annotation = "Sign up"
        )
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.primary, fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("Sign up")
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