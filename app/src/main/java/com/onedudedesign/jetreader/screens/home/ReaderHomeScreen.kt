package com.onedudedesign.jetreader.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.R
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.onedudedesign.jetreader.components.ReaderLogo
import com.onedudedesign.jetreader.navigation.ReaderScreens

@Composable
fun ReaderHomeScreen(navController: NavHostController) {
    Scaffold(topBar = {
                      ReaderAppBar(title = "Welcome Home", navController = navController )
    }, floatingActionButton = {
        FabContent {}
    }) {
        //content
        Surface(modifier = Modifier.fillMaxSize()) {
            //homecontent
            Text(text = "Home Screen")
        }
    }
}

@Composable
fun FabContent(onTap: (String) -> Unit) {
    FloatingActionButton(
        onClick = { onTap("") },
        shape = RoundedCornerShape(50.dp),
        backgroundColor = Color(0xFF92CBDF)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add a Book",
            tint = MaterialTheme.colors.onSecondary
        )

    }
}

@Composable
fun ReaderAppBar(
    title: String,
    showProfile: Boolean = true,
    navController: NavHostController
) {
    TopAppBar(title = {

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showProfile) {
                    ReaderLogo(modifier = Modifier.size(width = 40.dp, height = 40.dp))
                }
                Text(text = title, color = Color.Blue.copy(alpha = 0.7f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.size(150.dp))
                
            }


    }, actions = {
                 IconButton(onClick = { 
                     FirebaseAuth.getInstance().signOut().run {
                         navController.navigate(ReaderScreens.ReaderLoginScreen.name)
                     }
                 }) {
                     Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout")
                 }
    }, backgroundColor = Color.Transparent, elevation = 5.dp)
    
}
