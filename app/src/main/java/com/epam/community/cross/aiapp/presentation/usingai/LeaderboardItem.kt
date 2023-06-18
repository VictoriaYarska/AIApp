package com.epam.community.cross.aiapp.presentation.usingai

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.epam.community.cross.aiapp.R
import com.epam.community.cross.aiapp.domain.model.Movie
import com.epam.community.cross.aiapp.presentation.theme.Blue
import com.epam.community.cross.aiapp.presentation.theme.GrayD4
import com.epam.community.cross.aiapp.presentation.theme.GrayDF
import com.epam.community.cross.aiapp.presentation.theme.Green
import com.epam.community.cross.aiapp.presentation.theme.Orange

@Composable
fun MovieLeaderboard(firstMovie: Movie, secondMovie: Movie, thirdMovie: Movie) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        SmallMovie(
            secondMovie,
            modifier = Modifier.weight(1f),
            surfaceShape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp),
            primaryColor = Blue
        )
        LeaderMovie(movie = firstMovie, modifier = Modifier.weight(1f))

        SmallMovie(
            thirdMovie,
            modifier = Modifier.weight(1f),
            surfaceShape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp),
            primaryColor = Green
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SmallMovie(movie: Movie, modifier: Modifier, surfaceShape: Shape, primaryColor: Color) {
    Box(modifier = modifier) {
        val imageHeight = 70.dp
        val topSpace = imageHeight - 15.dp
        Surface(
            shape = surfaceShape,
            color = GrayDF,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(top = topSpace),
        ) {
            MovieContent(
                name = movie.name,
                username = movie.username,
                rating = movie.rate,
                primaryColor = primaryColor,
                modifier = Modifier.padding(
                    top = 30.dp,
                    bottom = 23.dp,
                    start = 28.dp,
                    end = 28.dp
                )
            )
        }

        GlideImage(
            model = movie.imageUrl,
            contentDescription = stringResource(R.string.movie_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(imageHeight)
                .border(3.dp, primaryColor, shape = CircleShape)
                .clip(CircleShape)
                .align(Alignment.TopCenter)
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LeaderMovie(movie: Movie, modifier: Modifier) {
    Box(modifier = modifier) {
        val imageHeight = 90.dp
        val iconHeight = 34.dp
        val topSpace = imageHeight + iconHeight - 20.dp
        Surface(
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            color = GrayD4,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(top = topSpace),
        ) {
            MovieContent(
                name = movie.name,
                username = movie.username,
                rating = movie.rate,
                primaryColor = Orange,
                modifier = Modifier.padding(
                    top = 60.dp,
                    bottom = 23.dp,
                    start = 28.dp,
                    end = 28.dp
                )
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_leader_crown),
            contentDescription = "Movie image",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .height(iconHeight),
            tint = Color.Unspecified
        )

        GlideImage(
            model = movie.imageUrl,
            contentDescription = stringResource(R.string.movie_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(imageHeight)
                .offset(y = iconHeight)
                .border(3.dp, Orange, shape = CircleShape)
                .clip(CircleShape)
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
fun MovieContent(
    modifier: Modifier = Modifier,
    name: String,
    username: String,
    rating: Int,
    primaryColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {

        Text(text = name, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
        Text(
            text = rating.toString(),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = primaryColor
            ),
            textAlign = TextAlign.Center
        )
        Text(
            text = username,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}