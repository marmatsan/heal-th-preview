package com.marmatsan.health

import android.os.Bundle
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.marmatsan.core_domain.navigation.Route
import com.marmatsan.core_domain.preferences.Preferences
import com.marmatsan.core_domain.preferences.PreferencesData
import com.marmatsan.health.navigation.navigate
import com.marmatsan.health.navigation.navigateBack
import com.marmatsan.health.ui.theme.HealthTheme
import com.marmatsan.onboarding_ui.activity.ActivityScreen
import com.marmatsan.onboarding_ui.age.AgeScreen
import com.marmatsan.onboarding_ui.gender.GenderScreen
import com.marmatsan.onboarding_ui.height.HeightScreen
import com.marmatsan.onboarding_ui.nutrient_goal.NutrientGoalScreen
import com.marmatsan.onboarding_ui.weight.WeightScreen
import com.marmatsan.onboarding_ui.weight_goal.GoalScreen
import com.marmatsan.onboarding_ui.welcome.WelcomeScreen
import com.marmatsan.tracker_ui.search.SearchScreen
import com.marmatsan.tracker_ui.tracker_overview.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.Clock
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.math.hypot

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setOnExitAnimationListener { splashScreenView ->

            val centerX = splashScreenView.view.width / 2
            val centerY = splashScreenView.view.height / 2
            val startRadius = hypot(centerX.toDouble(), centerY.toDouble()).toFloat()
            val endRadius = 0f

            val splashScreenAnimationEndTime =
                Instant.ofEpochMilli(
                    splashScreenView.iconAnimationStartMillis + splashScreenView.iconAnimationDurationMillis
                )

            val delay = Instant.now(Clock.systemUTC()).until(
                splashScreenAnimationEndTime,
                ChronoUnit.MILLIS
            )

            val circularReveal = ViewAnimationUtils.createCircularReveal(
                splashScreenView.view, centerX, centerY, startRadius, endRadius
            ).apply {
                startDelay = if (delay > 0) delay else 0
                duration = 300L
                interpolator = AccelerateDecelerateInterpolator()
                doOnEnd { splashScreenView.remove() }
            }

            circularReveal.start()
        }

        setContent {
            HealthTheme {

                // TODO: Sometimes, a weird visual effect with the splash screen happens
                val showOnboarding =
                    preferences.loadPreferencesData().collectAsState(initial = PreferencesData()).value.showOnboarding

                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { paddingValues ->
                    val navController = rememberNavController() // TODO: Improve navigation
                    NavHost(
                        navController = navController,
                        startDestination = if (showOnboarding) Route.OnBoarding.WELCOME else Route.Tracker.OVERVIEW
                    ) {
                        composable(Route.OnBoarding.WELCOME) {
                            WelcomeScreen(
                                modifier = Modifier.padding(paddingValues),
                                onNavigate = navController::navigate,
                            )
                        }
                        composable(Route.OnBoarding.GENDER) {
                            GenderScreen(
                                modifier = Modifier.padding(paddingValues),
                                snackbarHostState = snackbarHostState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.OnBoarding.AGE) {
                            AgeScreen(
                                modifier = Modifier.padding(paddingValues),
                                snackbarHostState = snackbarHostState,
                                onNavigate = navController::navigate,
                                onNavigateBack = navController::navigateBack
                            )
                        }
                        composable(Route.OnBoarding.HEIGHT) {
                            HeightScreen(
                                modifier = Modifier.padding(paddingValues),
                                snackbarHostState = snackbarHostState,
                                onNavigate = navController::navigate,
                                onNavigateBack = navController::navigateBack
                            )
                        }
                        composable(Route.OnBoarding.WEIGHT) {
                            WeightScreen(
                                modifier = Modifier.padding(paddingValues),
                                snackbarHostState = snackbarHostState,
                                onNavigate = navController::navigate,
                                onNavigateBack = navController::navigateBack
                            )
                        }
                        composable(Route.OnBoarding.ACTIVITY) {
                            ActivityScreen(
                                modifier = Modifier.padding(paddingValues),
                                onNavigate = navController::navigate,
                                onNavigateBack = navController::navigateBack
                            )
                        }
                        composable(Route.OnBoarding.WEIGHT_GOAL) {
                            GoalScreen(
                                modifier = Modifier.padding(paddingValues),
                                onNavigate = navController::navigate,
                                onNavigateBack = navController::navigateBack
                            )
                        }
                        composable(Route.OnBoarding.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                modifier = Modifier.padding(paddingValues),
                                snackbarHostState = snackbarHostState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.Tracker.OVERVIEW) {
                            TrackerOverviewScreen(
                                modifier = Modifier.padding(paddingValues),
                                onNavigate = navController::navigate
                            )
                        }
                        composable(
                            route = Route.Tracker.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = NavType.IntType
                                },
                                navArgument("month") {
                                    type = NavType.IntType
                                },
                                navArgument("year") {
                                    type = NavType.IntType
                                },
                            )
                        ) {

                            val mealName = it.arguments?.getString("mealName")!!
                            val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
                            val month = it.arguments?.getInt("month")!!
                            val year = it.arguments?.getInt("year")!!

                            SearchScreen(
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                snackbarHostState = snackbarHostState,
                                onNavigateUp = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}