package fr.ap7.mymoviedb

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import fr.ap7.mymoviedb.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    // Define data model
    // This value is wrapped to MutableLiveData instance
    // result value is mutable because the value can be  changed each time the user request it

    init {
        fetchPopularMovies()
    }

    val popularMovies: LiveData<List<Movie>>
        get() = movieRepository.movies.map { movies ->
            // select the movies released last month and sort them by title before
            // passing them to the UI observer in MainActivity.
            movies.filter {
                val cal = Calendar.getInstance()
                cal.add(Calendar.MONTH, -1)
                it.release_date.startsWith(
                    "${cal.get(Calendar.YEAR)}-${
                        cal.get(Calendar.MONTH)
                            + 1
                    }"
                )
            }
            // sorted by title using Kotlin's sortedBy function.
            movies.sortedBy { it.title }
            movies.map {
                // make uppercase the title of movie using map
                it.copy(title = it.title.toUpperCase(Locale.getDefault()))
            }
        }

    val error: LiveData<String> get() = movieRepository.error

    // define fetchPopularMovies
    private fun fetchPopularMovies() {
        // Dispatchers.Main: Used to run on Android's main thread
        // Dispatchers.IO: Used for network, file, or database operations
        // Dispatchers.Default: Used for CPU-intensive work

        //viewModelScope is canceled when the ViewModel has been destroyed
        // No need  to cancel the viewModelScope, The lifecycleScope doing the job for us
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMovies()
        }
    }
}