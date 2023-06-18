package com.epam.community.cross.aiapp.domain.repository

import com.epam.community.cross.aiapp.domain.model.Movie

interface MovieRepository {
    suspend fun regionMovies(): List<Movie>
    suspend fun nationalMovies(): List<Movie>
    suspend fun globalMovies(): List<Movie>
}