package com.amadoutirera.pokedexcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amadoutirera.pokedexcompose.ui.theme.PokedexComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "Pokemon_list_Screen"
                    ){
                        composable(route = "pokemon_list_screen"){

                        }

                        //
                        composable(
                            route = "pokemon_detail_screen/{dominantColor}/{dominantColor}",
                            arguments = listOf(
                                navArgument("dominantColor"){
                                    type = NavType.IntType
                                },

                                navArgument("pokemonName"){
                                    type = NavType.StringArrayType
                                }

                            )
                        ){

                            val dominantColor = remember(){
                                val color = it.arguments?.getInt("dominantColor")
                                color?.let { Color(it)} ?: R.color.white
                            }

                            val pokemonName = remember(){
                                it.arguments?.getString("pokemonName")
                            }

                        }
                        //
                    }

                }
            }
        }
    }
}



@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    PokedexComposeTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "Pokemon_list_Screen"
        ){
            composable(route = "pokemon_list_screen"){

            }

            //
            composable(
                route = "pokemon_detail_screen/{dominantColor}/{dominantColor}",
                arguments = listOf(
                    navArgument("dominantColor"){
                        type = NavType.IntType
                    },

                    navArgument("pokemonName"){
                        type = NavType.StringArrayType
                    }

                )
            ){

                val dominantColor = remember(){
                    val color = it.arguments?.getInt("dominantColor")
                    color?.let { Color(it)} ?: R.color.white
                }

                val pokemonName = remember(){
                    it.arguments?.getString("pokemonName")
                }

            }
            //
        }
    }
}