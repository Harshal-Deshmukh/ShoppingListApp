package eu.tutorials.theshoppinnglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import eu.tutorials.theshoppinnglist.ui.theme.TheShoppinngListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheShoppinngListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel: LocationViewModel = viewModel()
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)

    NavHost(navController = navController, startDestination = "shoppinglistscreen") {

        // Main shopping list screen
        composable("shoppinglistscreen") {
            ShoppingListApp(
                locationUtils = locationUtils,
                viewModel = viewModel,
                navController = navController,
                context = context,
                address = viewModel.address.value
            )
        }

        // Map screen — dialog ke roop mein khulega
        dialog("locationscreen") {
            viewModel.location.value?.let { currentLocation ->
                LocationSelectionScreen(
                    location = currentLocation,
                    onLocationSelected = { selectedLocation ->
                        // Selected location ka address fetch karo
                        viewModel.fetchAddress(
                            "${selectedLocation.latitude},${selectedLocation.longitude}",
                            context
                        )
                        // Map screen band karo
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}