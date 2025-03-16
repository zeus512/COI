package com.gouthamreddy.constitutionofindia.ui

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.gouthamreddy.constitutionofindia.data.models.SearchResult
import com.gouthamreddy.constitutionofindia.ui.composables.ArticleDetailScreen
import com.gouthamreddy.constitutionofindia.ui.composables.ArticlesScreen
import com.gouthamreddy.constitutionofindia.ui.composables.MainScreen
import com.gouthamreddy.constitutionofindia.ui.composables.PartsScreen
import com.gouthamreddy.constitutionofindia.ui.composables.PreambleScreen
import com.gouthamreddy.constitutionofindia.ui.composables.SchedulesScreen
import com.gouthamreddy.constitutionofindia.ui.composables.SearchResultsScreen
import com.gouthamreddy.constitutionofindia.ui.composables.TopBar
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var currentScreenTitle by remember { mutableStateOf("Constitution of India") }
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    BackHandler {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        } else {
            onBackPressedDispatcher?.onBackPressed()
        }
    }
    Scaffold(
        topBar = {
            TopBar(
                title = { currentScreenTitle },
                navigateToSearchScreen = {
                    navController.navigate(ScreenState.Search)
                },
                showSearch = true,
                onBack = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    } else {
                        onBackPressedDispatcher?.onBackPressed()
                    }
                }
            )
        }
    ) {
        val gradientColors = listOf(Color(0xFFFF9933), Color.White, Color(0xFF138808))

        NavHost(
            modifier = Modifier
                .padding(it)
                .background(Brush.verticalGradient(gradientColors)),
            navController = navController,
            startDestination = ScreenState.Main
        ) {
            composable<ScreenState.Main>(
                enterTransition = { slideInHorizontally(animationSpec = tween(500)) { if (targetState.id > initialState.id) -it else it } },
                exitTransition = { slideOutHorizontally(animationSpec = tween(500)) { if (targetState.id > initialState.id) it else -it } }
            ) {
                currentScreenTitle = "Constitution of India"
                MainScreen(
                    navigateTo = { navController.navigate(it) }
                )
            }
            composable<ScreenState.Preamble>(
                enterTransition = { slideInHorizontally(animationSpec = tween(500)) { if (targetState.id > initialState.id) -it else it } },
                exitTransition = { slideOutHorizontally(animationSpec = tween(500)) { if (targetState.id > initialState.id) it else -it } }
            ) { backStackEntry ->
                currentScreenTitle = "Preamble of India"
                PreambleScreen()
            }
            composable<ScreenState.Schedules>(
                enterTransition = { slideInHorizontally(animationSpec = tween(500)) { if (targetState.id > initialState.id) -it else it } },
                exitTransition = { slideOutHorizontally(animationSpec = tween(500)) { if (targetState.id > initialState.id) it else -it } }
            ) { backStackEntry ->
                currentScreenTitle = "Schedules"
                SchedulesScreen()
            }
            composable<ScreenState.Parts>(
                enterTransition = { slideInHorizontally(animationSpec = tween(500)) { if (targetState.id > initialState.id) -it else it } },
                exitTransition = { slideOutHorizontally(animationSpec = tween(500)) { if (targetState.id > initialState.id) it else -it } }
            ) {
                currentScreenTitle = "Parts of Constitution"
                PartsScreen(
                    navigateTo = { navController.navigate(it) }
                )
            }
            composable<ScreenState.Articles>(
                enterTransition = { slideInHorizontally(animationSpec = tween(500)) { if (targetState.id > initialState.id) -it else it } },
                exitTransition = { slideOutHorizontally(animationSpec = tween(500)) { if (targetState.id > initialState.id) it else -it } }
            ) { backStackEntry ->
                val part = backStackEntry.toRoute<ScreenState.Articles>().part
                currentScreenTitle = part
                ArticlesScreen(part = part, navigateTo = { navController.navigate(it) })
            }
            composable<ScreenState.ArticleDetail>(
                enterTransition = { slideInHorizontally(animationSpec = tween(500)) { if (targetState.id > initialState.id) -it else it } },
                exitTransition = { slideOutHorizontally(animationSpec = tween(500)) { if (targetState.id > initialState.id) it else -it } }
            ) { backStackEntry ->
                val articleNumber =
                    backStackEntry.toRoute<ScreenState.ArticleDetail>().articleNumber
                currentScreenTitle = "Article $articleNumber"
                ArticleDetailScreen(articleNumber, navigateTo = { navController.navigate(it) })
            }

            composable<ScreenState.Search> {
                currentScreenTitle = "Search"
                SearchResultsScreen(navigateToDetail = { result ->
                    when (result) {
                        is SearchResult.ArticleResult -> navController.navigate(
                            ScreenState.ArticleDetail(
                                result.article.articleNumber
                            )
                        )

                        is SearchResult.ScheduleResult -> navController.navigate(ScreenState.Schedules)
                        is SearchResult.AmendmentResult -> navController.navigate(ScreenState.Amendments)
                    }
                })
            }

        }
    }
}

sealed interface ScreenState {
    @Serializable
    data object Main : ScreenState

    @Serializable
    data object Parts : ScreenState

    @Serializable
    data object Schedules : ScreenState

    @Serializable
    data object Preamble : ScreenState

    @Serializable
    data object Amendments : ScreenState


    @Serializable
    data class Articles(val part: String) : ScreenState

    @Serializable
    data class ArticleDetail(val articleNumber: String) : ScreenState


    @Serializable
    data object Search : ScreenState


}