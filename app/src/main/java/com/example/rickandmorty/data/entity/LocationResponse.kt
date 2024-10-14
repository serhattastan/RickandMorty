package com.example.rickandmorty.data.entity

data class LocationResponse(
    val info: Info,
    val results: List<Location>
)