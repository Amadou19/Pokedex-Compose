package com.amadoutirera.pokedexcompose.repository

import com.amadoutirera.pokedexcompose.Util.Resource
import com.amadoutirera.pokedexcompose.data.remote.PokeApi
import com.amadoutirera.pokedexcompose.data.remote.response.Pokemon
import com.amadoutirera.pokedexcompose.data.remote.response.PokemonList
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository  @Inject constructor(private val api: PokeApi){

    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList>{
        val response = try {
            api.getPokemonList(limit,offset)
        }catch (e: Exception){
            return Resource.Error( e.localizedMessage)
        }
        return Resource.Success(response)
    }


    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon>{
        val response = try {
            api.getPokemonInfo(pokemonName)
        }catch (e: Exception){
            return Resource.Error(e.localizedMessage)
        }
        return Resource.Success(response)
    }
}