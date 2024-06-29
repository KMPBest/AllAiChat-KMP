package navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController

val LocalNavController =
  compositionLocalOf<NavController> {
    error("[No NavController found!]")
  }

object NavControllerHolder {
  lateinit var navController: NavHostController
}
