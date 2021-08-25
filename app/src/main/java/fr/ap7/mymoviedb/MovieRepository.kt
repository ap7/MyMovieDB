package fr.ap7.mymoviedb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.ap7.mymoviedb.api.MovieService
import fr.ap7.mymoviedb.model.Movie

// Repository est un pattenr permettant de d'acceder aux données
// depuis un serveur distant and de le stocker de manière centralizé
// le repository permet permet de garder separer les sources de donné séparer de l'activité et la view model
// Cette centralisation offre une manière de testé plus facilement
//repository -> ROOM -> SQLITE
//repository -> NETWORK LAYER -> SERVER
//repository -> FILEMANAGER ->  FILE SYSTEM
class MovieRepository(private val movieService: MovieService) {
    private val apiKey = "a9f769407e348291f57c7d44ffceb022"

    private val movieLiveData = MutableLiveData<List<Movie>>()
    private val errorLiveData = MutableLiveData<String>()

    val movies: LiveData<List<Movie>> get() = movieLiveData

    val error: LiveData<String> get() = errorLiveData

    suspend fun fetchMovies() {
        try {
            val popularMovies = movieService.getPopularMovies(apiKey)
            movieLiveData.postValue(popularMovies.results)
        } catch (exception: Exception) {
            errorLiveData.postValue("An error is occured : ${exception.message}")
        }
    }
}