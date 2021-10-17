package com.amadoutirera.pokedexcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amadoutirera.pokedexcompose.pokemonlist.*
import com.amadoutirera.pokedexcompose.ui.theme.PokedexComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = POKEMON_LIST_SCREEN){

                    composable(route = POKEMON_LIST_SCREEN){
                        PokemonListScreen(navController = navController)
                    }

                    composable(
                        route = "$POKEMON_DETAIL_SCREEN/{$DOMINANT_COLOR_ARG}/{$DOMINANT_COLOR_ARG}",
                        arguments = listOf(
                            navArgument(DOMINANT_COLOR_ARG){
                                type = NavType.IntType
                            },

                            navArgument(POKEMON_LIST_SCREEN){
                                type = NavType.StringArrayType
                            }
                        )
                    ){

                        val dominantColor = remember(){
                            val color = it.arguments?.getInt(DOMINANT_COLOR_ARG)
                            color?.let { Color(it)} ?: R.color.white
                        }

                        val pokemonName = remember(){
                            it.arguments?.getString(POKEMON_NAME)
                        }

                    }
                    //
                }
            }
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    PokedexComposeTheme {
        PokemonListScreen(navController = NavController(LocalContext.current))
    }
}