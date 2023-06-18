package com.epam.community.cross.aiapp.presentation.injection

import com.epam.community.cross.aiapp.data.repository.MovieRepositoryImpl
import com.epam.community.cross.aiapp.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class MovieModule {

    @Singleton
    @Binds
    abstract fun bindMovieRepository(repositoryImpl: MovieRepositoryImpl): MovieRepository

}
