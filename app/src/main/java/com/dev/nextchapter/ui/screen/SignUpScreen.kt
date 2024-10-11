package com.dev.nextchapter.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.onFocusedBoundsChanged
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dev.nextchapter.R
import com.dev.nextchapter.viewmodel.UserViewModel

@Composable
fun SignupScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var singupError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
//    val linear = Brush.linearGradient(listOf(
//        Color.hsl(300f, 0.26f, 0.71f),
//        Color.hsl(299f, 0.9f, 0.1f,),
//        Color.hsl(300f, 0.26f, 0.71f),
//        Color.hsl(299f, 0.9f, 0.1f,)
//    ))
    val imageModifier = Modifier
        .border(BorderStroke(5.dp, Color.hsl(0.7f, 0.77f, 0.38f)))
        .background(Color.hsl(0.19f, 0.57f, 0.33f))

    Image(
        painter = painterResource(id = R.drawable.dragonlibrarian),
        contentDescription = stringResource(id = R.string.app_name),
        contentScale = ContentScale.Crop,
        modifier = imageModifier.fillMaxSize()
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )

    {

        Text(
            "The Visitors' Log",
            fontSize = 48.sp,
            fontWeight = FontWeight.Thin,
            fontStyle = FontStyle.Italic,
            color = Color.White,
            textDecoration = TextDecoration.Underline
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .background(color = Color.LightGray),
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") })
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .background(color = Color.LightGray),
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .background(color = Color.LightGray),
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Already have an account? Login",
                color = Color.White,
                modifier = Modifier.clickable {
                    navController.navigate("login")
                }
            )
            Button(
                onClick = {
                    if (password != confirmPassword) {
                        passwordError = true
                    } else {
                        userViewModel.signUp(username, password).observeForever() { success ->
                            if (success) {
                                navController.navigate("login")
                            } else {
                                singupError = true
                            }

                        }
                    }
                },
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = Color.Black
                )
            ) {
                Text("Sign up")
            }

        }
        if (singupError) {
            Text("Username already exists. Please choose another.", color = Color.Red)
        }
        if (passwordError) {
            Text("Passwords do not match.", color = Color.Red)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    SignupScreen(navController = rememberNavController())
}