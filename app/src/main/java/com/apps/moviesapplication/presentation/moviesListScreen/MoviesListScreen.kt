package com.apps.moviesapplication.presentation.moviesListScreen

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.apps.moviesapplication.R
import com.apps.moviesapplication.data.models.MovieDemo
import com.apps.moviesapplication.presentation.moviesListScreen.viewModel.MoviesListViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
internal fun MoviesListRoute(onMovieClicked: (Int) -> Unit) {
    val viewModel: MoviesListViewModel = hiltViewModel()
    MoviesListScreen(viewModel, onMovieClicked)
}

@Composable
fun MoviesListScreen(
    viewModel: MoviesListViewModel,
    onMovieClicked: (Int) -> Unit
) {
    val moviesPagingData = viewModel.trendingMovies.collectAsLazyPagingItems()

    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.fillMaxSize()) {
        items(moviesPagingData.itemCount) { index ->
            moviesPagingData[index]?.let { MovieItem(it) { id ->
                onMovieClicked(id)
            } }
        }
        moviesPagingData.apply {
            when (loadState.append) {
                is LoadState.Loading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) { LoadingNextPageItem() }
                }
                is LoadState.Error -> {
                    item(span = { GridItemSpan(maxLineSpan) }) { ErrorMessage() }
                }
                is LoadState.NotLoading -> {
                    if (loadState.append.endOfPaginationReached) {
                        item(span = { GridItemSpan(maxLineSpan) }) { NoMoreMoviesItem() }
                    }
                }
            }
        }
    }

    if (moviesPagingData.loadState.refresh is LoadState.Loading) {
        PageLoader()
    } else if (moviesPagingData.loadState.refresh is LoadState.Error) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.connection_problem))
        }
    }
}

@Composable
fun PageLoader() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoadingNextPageItem() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Color.DarkGray, modifier = Modifier.padding(10.dp))
    }
}

@Composable
fun NoMoreMoviesItem() {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(17.dp))
            .background(Color.LightGray), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(36.dp))
        Text(text = stringResource(R.string.you_have_reached_the_end), modifier = Modifier.padding(5.dp))
    }
}

@Composable
fun ErrorMessage() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(text = stringResource(R.string.something_went_wrong), modifier = Modifier.padding(10.dp))
    }
}

@Composable
fun MovieItem(movie: MovieDemo, onMovieClicked: (Int) -> Unit) {
    val imageUrl = stringResource(R.string.image_url, movie.poster_path)

    Box(
        Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(250.dp)
            .background(colorResource(id = R.color.lightLightGray))
            .clickable { onMovieClicked(movie.id) }
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.white_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
            )

            val releaseYear = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    val releaseDate = LocalDate.parse(movie.release_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    releaseDate.year.toString()
                } catch (e: Exception) {
                    e.printStackTrace()
                    ""
                }
            } else {
                try {
                    movie.release_date.substring(0, 4)
                } catch (e: Exception) {
                    e.printStackTrace()
                    ""
                }
            }

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Text(
                    text = movie.title,
                    maxLines = 1,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 5.dp)
                )

                Text(
                    text = releaseYear,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 5.dp)
                )
            }
        }
    }
}
