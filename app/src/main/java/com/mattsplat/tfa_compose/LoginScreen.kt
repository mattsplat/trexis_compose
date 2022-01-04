package com.mattsplat.tfa_compose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mattsplat.tfa_compose.ViewModels.LoginViewModel
import androidx.compose.ui.platform.LocalLifecycleOwner

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel) {
    val username by viewModel.username.observeAsState("")
    val password by viewModel.password.observeAsState("")

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    //    When api response comes back successful send to dashboard
    viewModel.loginSuccessful.observe(LocalLifecycleOwner.current) {
        if(it) {
            navController.navigate("dashboard")
        }
    }

    // Let user know if error occurs with login
    viewModel.apiError.observe(LocalLifecycleOwner.current) {
        if(!it.isNullOrEmpty()) {
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        Image(
            painter = painterResource(id = R.drawable.owl_icon),
            contentDescription = "Owl Icon",
            Modifier.padding(
                bottom = 150.dp,
                top = 50.dp
            )
        )

        Box(
            modifier = Modifier
                .padding(start = 32.dp, end = 32.dp, bottom = 10.dp)
        ) {
            OutlinedTextField(
                value = username,
                placeholder = { Text(text = "username") },
                onValueChange = { viewModel.setUsername(it) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.White, RoundedCornerShape(20)),
                shape = RoundedCornerShape(20),
                maxLines = 1,
                singleLine = true
            )
        }

        Box(
            modifier = Modifier
                .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
        ) {
            OutlinedTextField(
                value = password,
                placeholder = { Text(text = "password") },
                onValueChange = { viewModel.setPassword(it) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .background(Color.White, RoundedCornerShape(20)),
                shape = RoundedCornerShape(20),
                maxLines = 1,
                singleLine = true
            )
        }

        Button(
            modifier = Modifier
                .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
                .fillMaxWidth()
                .height(50.dp)
                .align(Alignment.CenterHorizontally)
                .background(Color.White, RoundedCornerShape(20)),
            onClick = {
                if (!username.isBlank() && !password.isBlank()) {
                    viewModel.submitLogin()
                } else {
                    Toast.makeText(
                        context,
                        "Please enter an email and password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            shape = RoundedCornerShape(20),

            ) {
            Text("Login")
        }
    }
}