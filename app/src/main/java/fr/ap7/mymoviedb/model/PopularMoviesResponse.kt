package fr.ap7.mymoviedb.model

// for the response you get from the API endpoint for
// popular movies.
data class PopularMoviesResponse(
    val page: Int,
    val results: List<Movie>,
)
