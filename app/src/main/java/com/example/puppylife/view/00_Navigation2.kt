package com.example.puppylife.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.puppylife.R
import com.example.puppylife.viewmodel.LoggedViewModel

@Composable
fun LoggedScreen(mainNavController: NavController){
    val navController = rememberNavController()
    val loggedViewModel = hiltViewModel<LoggedViewModel>()
    Scaffold (
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        containerColor = Color(0xFF705C5C)
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DestinationLogged.Home.route,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ){
            composable(DestinationLogged.Home.route){
                HomeScreen()
            }
            composable(DestinationLogged.Forum.route){
                ForumScreen()
            }
            composable(DestinationLogged.DogWalk.route){
                DogWalkScreen()
            }
            composable(DestinationLogged.Profile.route){
                ProfileScreen(mainNavController = mainNavController, loggedViewModel = loggedViewModel)
            }
        }
    }
}

@Composable
fun ImageFromUri(uri: String?, modifier: Modifier) {
    Box(
        modifier = modifier
    ) {
        if(uri!=null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uri)
                    .build(),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                // Crop the image to a circle
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                // Crop the image to a circle
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    // Create a list of items to be displayed in the bottom navigation bar
    val items = listOf(
        BottomNavItems.Home,
        BottomNavItems.Forum,
        BottomNavItems.DogWalk,
        BottomNavItems.Profile
    )
    var selectedItem by remember { mutableIntStateOf(0) }
    // Create a bottom navigation bar
    NavigationBar(
        // Consume the system window insets on the bottom and left/right sides of the screen
        // With this the the bottoms use the full size of the NavigationBar
        modifier = Modifier
            .consumeWindowInsets(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                )
            )
            .height(48.dp),
        containerColor = Color.Black
    ){
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        modifier = Modifier.size(24.dp),
                        contentDescription = stringResource(id = item.content))
                },
                selected = selectedItem == index,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFFFFFF),
                    unselectedIconColor = Color(0xFF705C5C),
                    indicatorColor = Color(0xFF705C5C),
                ),
                onClick = {
                    if (selectedItem != index) {
                        selectedItem = index
                        navigateToWithBack(navController, item.route)
                    }
                },
            )
        }
    }
}

sealed class BottomNavItems(var content: Int, var icon: Int, var route: String){
    object Home: BottomNavItems(R.string.home, R.drawable.home, DestinationLogged.Home.route)
    object Forum: BottomNavItems(R.string.forum, R.drawable.forum, DestinationLogged.Forum.route)
    object DogWalk: BottomNavItems(R.string.dogwalk, R.drawable.dogwalk, DestinationLogged.DogWalk.route)
    object Profile: BottomNavItems(R.string.profile, R.drawable.profile, DestinationLogged.Profile.route)
}


sealed class DestinationLogged(var route: String){
    object Home: DestinationLogged("home")
    object Forum: DestinationLogged("forum")
    object DogWalk: DestinationLogged("dogwalk")
    object Profile: DestinationLogged("profile")
}