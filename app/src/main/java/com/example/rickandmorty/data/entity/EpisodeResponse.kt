package com.example.rickandmorty.data.entity

data class EpisodeResponse(
    val info: Info,
    val results: List<Episode>
)