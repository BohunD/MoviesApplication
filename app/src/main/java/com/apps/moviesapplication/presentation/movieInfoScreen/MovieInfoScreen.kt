package com.apps.moviesapplication.presentation.movieInfoScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import com.apps.moviesapplication.R
import com.apps.moviesapplication.data.network.TmdbApiService
import com.apps.moviesapplication.presentation.movieInfoScreen.viewmodel.MovieInfoViewModel
import com.apps.moviesapplication.presentation.moviesListScreen.viewModel.MoviesListViewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
internal fun MovieInfoRoute(id: Int) {
    val viewModel: MovieInfoViewModel = hiltViewModel()
    MovieInfoScreen(viewModel, id)
}

@Composable
fun MovieInfoScreen(
    viewModel: MovieInfoViewModel,
    id: Int,
) {
    viewModel.fetchMovieDetails(id)
    val movie = viewModel.movie.collectAsState().value
    movie?.let {
        MovieInfoDemo(movie = it)
    }
}

@Composable
fun MovieInfoDemo(movie: TmdbApiService.MovieFull) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        val imageUrl = stringResource(R.string.image_url, movie.poster_path)
        AsyncImage(
            model = imageUrl, contentDescription = null, imageLoader = ImageLoader(
                LocalContext.current
            ),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = movie.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)
        )
        Text(
            text = movie.tagline,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 2.dp)
        )
        Text(
            text = stringResource(R.string.movie_info),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 15.dp)
        )
        InfoRow(name = stringResource(R.string.release_date), data = movie.release_date)
        InfoRow(
            name = stringResource(R.string.rating),
            data = String.format(Locale.getDefault(), "%.1f", movie.vote_average)
        )
        InfoRow(name = stringResource(R.string.runtime), data = stringResource(R.string.minutes, movie.runtime))
        InfoRow(name = stringResource(R.string.budget), data = "$${formatBudget(movie.budget.toLong())}")
        InfoRow(name = stringResource(R.string.revenue), data = "$${formatBudget(movie.revenue)}")
        Text(
            text = stringResource(R.string.overview),
            fontSize = 14.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
        )

        Text(
            text = movie.overview,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 2.dp, bottom = 10.dp),
        )

    }
}

@Composable
fun InfoRow(name: String, data: String) {
    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)) {
        Text(text = "$name: ", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Text(text = data, fontSize = 14.sp)

    }
}

fun formatBudget(budget: Long): String {
    if(budget>0) {
        val formatter = DecimalFormat("#,###,###.00", DecimalFormatSymbols(Locale.US))
        return formatter.format(budget)
    }else return "0"
}
