package fr.ap7.mymoviedb

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import fr.ap7.mymoviedb.model.Movie
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val movieAdapter by lazy {
        MovieAdapter(object : MovieAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                openMovieDetails(movie)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.movie_list)
        recyclerView.adapter = movieAdapter

        val movieRepository = (application as MovieApplication).movieRepository

        val movieViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MovieViewModel(movieRepository) as T
            }
        }).get(MovieViewModel::class.java)

        movieViewModel.popularMovies.observe(this, { popularMovies ->
            movieAdapter.addMovies(popularMovies
                // filter function to only include movies released
                // this year.
                .filter {
                    it.release_date.startsWith(
                        Calendar.getInstance().get(Calendar.YEAR).toString()
                    )
                }
                // sorted by title using Kotlin's sortedBy function.
                .sortedBy { it.title }
                // make uppercase the title of movie using map
                .map { it.copy(title = it.title.toUpperCase(Locale.getDefault())) }
            )
        })
        movieViewModel.error.observe(this, { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })
    }

    private fun openMovieDetails(movie: Movie) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.EXTRA_MOVIE, movie)
        }
        startActivity(intent)
    }
}