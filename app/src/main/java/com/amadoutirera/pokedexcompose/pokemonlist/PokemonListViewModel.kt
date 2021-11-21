package com.amadoutirera.pokedexcompose.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.amadoutirera.pokedexcompose.Util.Constants.PAGE_SIZE
import com.amadoutirera.pokedexcompose.Util.Resource
import com.amadoutirera.pokedexcompose.data.models.PokedexListEntry
import com.amadoutirera.pokedexcompose.repository.PokemonRepository
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class PokemonListViewModel @Inject constructor( private val repository: PokemonRepository): ViewModel() {
    private var curPage = 0
    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    fun loadPokemonPaginated(){
        viewModelScope.launch {
            val result = repository.getPokemonList(PAGE_SIZE, curPage * PAGE_SIZE)
            when(result){
                is Resource.Error -> {
                    endReached.value = curPage * PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->
                        val number = if (entry.url.endsWith("/")){
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        }else{
                            entry.url.takeLastWhile { it.isDigit() }
                        }

                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"

                        PokedexListEntry(
                            pokemonName = entry.name.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(Locale.ROOT)
                                else it.toString()
                            },
                            imageUrl = url,
                            number = number.toInt()
                        )
                    }
                    curPage++

                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokedexEntries

                }
                is Resource.Loading -> {
                    loadError.value = result.message!!
                }
                is Resource.Success -> {

                }
            }
        }
    }


    fun calDominantColor(drawable: Drawable, onFinish: (Color) -> Unit){
        val bmp =  (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

}