package com.apps.moviesapplication.presentation.movieInfoScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import com.apps.moviesapplication.R
import com.apps.moviesapplication.data.models.MovieFull
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMovieInfoScreen() {
    val mockMovie = MovieFull(
        id = 1,
        title = "Movie Preview",
        tagline = "Your mind is the scene of the crime.",
        releaseDate = "2024-07-28",
        voteAverage = 8.8f,
        runtime = 148,
        budget = 160000000,
        revenue = 829895144,
        overview = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO."
    )

    MovieInfoDemo(movie = mockMovie, onBackPressed = {})
}

@Composable
fun MovieInfoScreen(
    viewModel: MovieInfoViewModel = hiltViewModel(),
    id: Int,
    onBackPressed: () -> Unit,
) {
    LaunchedEffect(id) {
        viewModel.fetchMovieDetails(id)
    }
    val movie = viewModel.movie.collectAsState().value
    if (movie != null)
        MovieInfoDemo(movie = movie, onBackPressed)
    else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.connection_problem))
        }
    }
}

@Composable
fun MovieInfoDemo(movie: MovieFull, onBackPressed: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            val imageUrl = stringResource(R.string.image_url, movie.posterPath)
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
            InfoRow(name = stringResource(R.string.release_date), data = movie.releaseDate)
            InfoRow(
                name = stringResource(R.string.rating),
                data = String.format(Locale.getDefault(), "%.1f", movie.voteAverage)
            )
            InfoRow(
                name = stringResource(R.string.runtime),
                data = stringResource(R.string.minutes, movie.runtime)
            )
            InfoRow(
                name = stringResource(R.string.budget),
                data = "$${formatBudget(movie.budget.toLong())}"
            )
            InfoRow(
                name = stringResource(R.string.revenue),
                data = "$${formatBudget(movie.revenue)}"
            )
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
        Box(modifier = Modifier
            .padding(15.dp)
            .size(40.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(Color.White.copy(alpha = 0.7f))
            .align(Alignment.TopStart)
            .clickable { onBackPressed() }) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Center),
                tint = Color.Black
            )
        }
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
    if (budget > 0) {
        val formatter = DecimalFormat("#,###,###.00", DecimalFormatSymbols(Locale.US))
        return formatter.format(budget)
    } else return "0"
}
