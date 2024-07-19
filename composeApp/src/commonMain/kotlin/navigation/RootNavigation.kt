package navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.mp.KoinPlatform
import screens.chatDetail.ChatDetailScreen
import screens.chatDetail.ChatDetailScreenViewModel
import screens.home.HomeScreen
import screens.home.HomeViewModel
import screens.main.MainViewModel
import viewModels.FilePickerModel

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */

@Composable
fun RootNavigation(mainViewModel: MainViewModel) {
  NavControllerHolder.navController = rememberNavController()
  // Get current back stack entry
  val backStackEntry by NavControllerHolder.navController.currentBackStackEntryAsState()
  // Get the name of the current screen
  val currentScreen = backStackEntry?.destination?.route ?: Screens.Home

  Scaffold(
    contentColor = Color.White,
    containerColor = Color.White,
  ) { innerPadding ->
    NavHost(
      navController = NavControllerHolder.navController,
      startDestination = Screens.Home,
      modifier =
        Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState()).background(Color.Black),
      enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
      exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn() },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut() },
    ) {
      composable(Screens.Home) { backStackEntry ->
        val homeViewModel: HomeViewModel = KoinPlatform.getKoin().get()
        HomeScreen(homeViewModel, mainViewModel)
      }
      composable(route = Screens.ChatDetail) {
          backStackEntry ->
        val groupId = backStackEntry.arguments?.getString("groupId") ?: ""
        val chatViewModel: ChatDetailScreenViewModel = KoinPlatform.getKoin().get()
        val homeViewModel: HomeViewModel = KoinPlatform.getKoin().get()
        val filePickerModel: FilePickerModel = KoinPlatform.getKoin().get()
        ChatDetailScreen(chatViewModel, homeViewModel, filePickerModel, groupId)
      }
    }
  }
}
