package com.onedudedesign.jetreader.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.onedudedesign.jetreader.R
import com.onedudedesign.jetreader.components.ReaderLogo
import com.onedudedesign.jetreader.navigation.ReaderScreens
import kotlinx.coroutines.delay


@Composable
fun ReaderSplashScreen(navController: NavHostController) {
    //setting a mutable scale value that gets passed top the Surface composable so it follows the animation
    val scale = remember {
        Animatable(0f)
    }
    //using launched effect to do the bounce scaling on the splash screen
    LaunchedEffect(key1 = true) {
        scale.animateTo(1.0f,
        animationSpec = tween(durationMillis = 2000,
        easing = { OvershootInterpolator(10f)
            .getInterpolation(it)}))
        delay(4000L)
        //later add if to login, for now go home
        navController.navigate(ReaderScreens.ReaderLoginScreen.name)
    }

    Surface(
        Modifier
            .padding(3.dp)
            .size(330.dp)
            .scale(scale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(4.dp, color = Color.LightGray)
    ) {
        Column(
            Modifier
                .padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ReaderLogo()
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "\"Read. Change Yourself\"",
            style = MaterialTheme.typography.h5,
            color = Color.LightGray)
        }

    }
}
