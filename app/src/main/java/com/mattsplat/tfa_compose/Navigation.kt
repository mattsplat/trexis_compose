package com.mattsplat.tfa_compose

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mattsplat.tfa_compose.ViewModels.AccountDetailViewModel
import com.mattsplat.tfa_compose.ViewModels.DashboardViewModel
import com.mattsplat.tfa_compose.ViewModels.LoginViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val viewModel: LoginViewModel = viewModel()
            LoginScreen(navController = navController, viewModel = viewModel)
        }

        composable("dashboard") {
            val viewModel: DashboardViewModel = viewModel()
            DashboardScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            route = "account/{account_id}",
            arguments = listOf(
                navArgument("account_id") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) {
            val viewModel: AccountDetailViewModel = viewModel()
            AccountDetailScreen(
                navController = navController,
                account_id = it.arguments!!.getInt("account_id"),
                viewModel = viewModel
            )
        }
    }
}
