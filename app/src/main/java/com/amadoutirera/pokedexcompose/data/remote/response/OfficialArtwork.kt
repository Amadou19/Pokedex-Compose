package com.amadoutirera.pokedexcompose.data.remote.response


import com.google.gson.annotations.SerializedName

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String
)