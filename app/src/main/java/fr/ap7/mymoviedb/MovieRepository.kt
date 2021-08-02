package fr.ap7.mymoviedb

import fr.ap7.mymoviedb.api.MovieService

// Repository est un pattenr permettant de d'acceder aux données
// depuis un serveur distant and de le stocker de manière centralizé
// le repository permet permet de garder separer les sources de donné séparer de l'activité et la view model
// Cette centralisation offre une manière de testé plus facilement
//repository -> ROOM -> SQLITE
//repository -> NETWORK LAYER -> SERVER
//repository -> FILEMANAGER ->  FILE SYSTEM
class MovieRepository(private val movieService: MovieService) {
    private val apiKey = "a9f769407e348291f57c7d44ffceb022"

    fun fetchMovies() = movieService.getPopularMovies(apiKey)
}