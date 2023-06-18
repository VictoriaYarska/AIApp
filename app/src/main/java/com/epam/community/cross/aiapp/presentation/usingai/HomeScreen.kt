package com.epam.community.cross.aiapp.presentation.usingai

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.epam.community.cross.aiapp.R
import com.epam.community.cross.aiapp.domain.model.Movie
import com.epam.community.cross.aiapp.presentation.HomeViewModel
import com.epam.community.cross.aiapp.presentation.MovieTabUiState
import com.epam.community.cross.aiapp.presentation.TabItem
import com.epam.community.cross.aiapp.presentation.theme.DividerColor
import com.epam.community.cross.aiapp.presentation.theme.GrayD4
import com.epam.community.cross.aiapp.presentation.theme.GrayDF
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val tabRowItems = listOf(//List of tabs to use later
        TabItem(
            text = stringResource(id = uiState.regionTabUIState.title),
            screen = {
                MovieTab(
                    uiState.regionTabUIState
                )
            }
        ),

        TabItem(
            text = stringResource(id = uiState.nationalTabUIState.title),
            screen = {
                MovieTab(
                    uiState.nationalTabUIState
                )
            }
        ),
        TabItem(
            text = stringResource(id = uiState.globalTabUIState.title),
            screen = {
                MovieTab(
                    uiState.globalTabUIState
                )
            }
        )
    )

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.onTabClick(page)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.home_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 20.dp)
        )
        TabRow(
            selectedTabIndex = uiState.selectedTab.index,
            containerColor = GrayD4,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 30.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            tabRowItems.forEachIndexed { index, item ->
                Tab(
                    text = {
                        Text(
                            text = item.text,
                            style = MaterialTheme.typography.titleMedium.copy(color = Color.Black)
                        )
                    },
                    selected = uiState.selectedTab.index == index,
                    onClick = {
                        coroutineScope.launch {
                            viewModel.onTabClick(index)
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            count = tabRowItems.size,
            state = pagerState,
        ) {
            tabRowItems[pagerState.currentPage].screen()
        }
    }
}

@Composable
fun MovieTab(uiState: MovieTabUiState) {
    if (uiState is MovieTabUiState.MovieUIState) {
        Column {
            MovieLeaderboard(
                firstMovie = uiState.firstMovie,
                secondMovie = uiState.secondMovie,
                thirdMovie = uiState.thirdMovie
            )
            MovieList(movieItems = uiState.movies)
        }
    } else {
        AppProgressBar()
    }
}


@Composable
fun MovieList(movieItems: List<Movie>) {
    Surface(
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        color = GrayDF,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 5.dp, horizontal = 25.dp)
        ) {
            itemsIndexed(movieItems, key = { _, item -> item.id }) { index, movie ->
                MovieItem(
                    name = movie.name,
                    rating = movie.rate.toString(),
                    username = movie.username,
                    imageUrl = movie.imageUrl,
                    rateIcon = movie.iconRate,
                )
                if (index < movieItems.lastIndex)
                    Divider(color = DividerColor, thickness = 1.dp)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(
    name: String, rating: String, username: String, imageUrl: String,
    @DrawableRes
    rateIcon: Int
) {
    Row(
        modifier = Modifier
            .padding(vertical = 15.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = imageUrl,
            contentDescription = stringResource(R.string.movie_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .padding(start = 20.dp)
                .weight(1f)
        ) {
            Text(text = name, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = username,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 20.dp)
                .wrapContentWidth(align = Alignment.End),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = rating,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Icon(
                painterResource(id = rateIcon),
                contentDescription = "Rate icon",
                tint = Color.Unspecified
            )
        }
    }
}




