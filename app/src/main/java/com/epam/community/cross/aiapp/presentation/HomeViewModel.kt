package com.epam.community.cross.aiapp.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epam.community.cross.aiapp.R
import com.epam.community.cross.aiapp.domain.model.Movie
import com.epam.community.cross.aiapp.domain.repository.MovieRepository
import com.epam.community.cross.aiapp.data.utils.second
import com.epam.community.cross.aiapp.data.utils.third
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(
        HomeUiState()
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadListRegionMovies()
    }

    fun onTabClick(index: Int) {
        when (index) {
            HomeTab.Global.index -> loadListGlobalMovies()
            HomeTab.Region.index -> loadListRegionMovies()
            HomeTab.National.index -> loadListNationalMovies()
        }
    }

    private fun loadListGlobalMovies() {
        if (_uiState.value.globalTabUIState is MovieTabUiState.Loading) {
            viewModelScope.launch(Dispatchers.IO) {
                val movies = movieRepository.globalMovies()
                val uiStateValue = uiState.value.copy(
                    selectedTab = HomeTab.Global,
                    globalTabUIState = MovieTabUiState.MovieUIState(
                        title = R.string.global_tab_title,
                        movies = movies.subList(4, movies.lastIndex),
                        firstMovie = movies.first(),
                        secondMovie = movies.second(),
                        thirdMovie = movies.third(),
                        tabIndex = HomeTab.Global
                    )
                )
                _uiState.update { uiStateValue }
            }
        } else {
            _uiState.update { uiState.value.copy(selectedTab = HomeTab.Global) }
        }
    }

    private fun loadListRegionMovies() {
        if (_uiState.value.regionTabUIState is MovieTabUiState.Loading) {
            viewModelScope.launch(Dispatchers.IO) {
                val movies = movieRepository.regionMovies()
                val uiStateValue = uiState.value.copy(
                    selectedTab = HomeTab.Region,
                    regionTabUIState = MovieTabUiState.MovieUIState(
                        title = R.string.region_tab_title,
                        movies = movies.subList(4, movies.lastIndex),
                        firstMovie = movies.first(),
                        secondMovie = movies.second(),
                        thirdMovie = movies.third(),
                        tabIndex = HomeTab.Region
                    )
                )
                _uiState.update { uiStateValue }
            }
        } else {
            _uiState.update { uiState.value.copy(selectedTab = HomeTab.Region) }
        }
    }

    private fun loadListNationalMovies() {
        if (_uiState.value.nationalTabUIState is MovieTabUiState.Loading) {
            viewModelScope.launch(Dispatchers.IO) {
                val movies = movieRepository.nationalMovies()
                val uiStateValue = uiState.value.copy(
                    selectedTab = HomeTab.National,
                    nationalTabUIState = MovieTabUiState.MovieUIState(
                        title = R.string.national_tab_title,
                        movies = movies.subList(4, movies.lastIndex),
                        firstMovie = movies.first(),
                        secondMovie = movies.second(),
                        thirdMovie = movies.third(),
                        tabIndex = HomeTab.National
                    )
                )
                _uiState.update { uiStateValue }
            }
        } else {
            _uiState.update { uiState.value.copy(selectedTab = HomeTab.National) }
        }
    }

}

enum class HomeTab(val index: Int) {
    Region(0), National(1), Global(2);
}

@Stable
data class HomeUiState(
    val selectedTab: HomeTab = HomeTab.Region,
    val regionTabUIState: MovieTabUiState = MovieTabUiState.Loading(R.string.region_tab_title),
    val nationalTabUIState: MovieTabUiState = MovieTabUiState.Loading(R.string.national_tab_title),
    val globalTabUIState: MovieTabUiState = MovieTabUiState.Loading(R.string.global_tab_title)
)

@Stable
sealed interface MovieTabUiState {
    val title: Int

    data class MovieUIState(
        @StringRes
        override val title: Int,
        val tabIndex: HomeTab,
        val movies: List<Movie>,
        val firstMovie: Movie,
        val secondMovie: Movie,
        val thirdMovie: Movie
    ) : MovieTabUiState

    data class Loading(
        @StringRes
        override val title: Int
    ) : MovieTabUiState
}

data class TabItem(
    val text: String,
    val screen: @Composable () -> Unit
)
