package fr.ap7.mymoviedb.api

import fr.ap7.mymoviedb.model.PopularMoviesResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Observable<PopularMoviesResponse> // source de donnée a ecouté
}