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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dev.nextchapter.R
import com.dev.nextchapter.viewmodel.UserViewModel

@Composable
fun LoginScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }
    //val linear = Brush.linearGradient(listOf(
//        Color.hsl(300f, 0.26f, 0.71f),
//        Color.hsl(299f, 0.9f, 0.1f,),
//        Color.hsl(300f, 0.26f, 0.71f),
//        Color.hsl(299f, 0.9f, 0.1f, )
//    ), tileMode = TileMode.Repeated)
    val imageModifier = Modifier
       .border(BorderStroke(5.dp , Color.hsl(0.7f, 0.77f, 0.38f)))
       .background(Color.hsl(0.19f, 0.57f, 0.33f))

    Image(
        painter = painterResource(id = R.drawable.libraryfrontdoor),
        contentDescription = stringResource(id = R.string.app_name),
        contentScale = ContentScale.Crop,
        modifier = imageModifier.fillMaxSize()
           // .background(color = Color.Transparent, shape = RoundedCornerShape(size = 15.dp)))
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent, shape = RoundedCornerShape(size = 5.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text("The Next Chapter 📚", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.hsl(300f, 0.26f, 0.71f), shape = RoundedCornerShape(size = 15.dp)),
            value = username,
            onValueChange = { username = it },
            label = { Text("Username")},)
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.hsl(300f, 0.26f, 0.71f), shape = RoundedCornerShape(size = 15.dp)),
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Don't haven an account? Sign Up",
                color = Color.hsl(300f, 0.26f, 0.71f),
                modifier = Modifier.clickable {
                    navController.navigate("signup")
                }
            )
            Button(
                onClick = {
                    userViewModel.login(username, password).observeForever() { user ->
                        if (user != null) {
                            userViewModel.setCurrentUser(user)
                            navController.navigate("home")
                        } else {
                            loginError = true
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = Color.hsl(267f, 0.98f, 0.49f)
                )
            ) {
                Text("Login")
            }

        }

        if (loginError) {
            Text("Invalid credentials. Please try again.", color = Color.Red)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}