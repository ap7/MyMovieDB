package fr.ap7.mymoviedb.api

import fr.ap7.mymoviedb.model.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    // une methode suspend peut etre mettre en pause le thread sans le blocqué quand il es appelé
    // quand la methode suspend à fini sont travail le thread courant va reprendre ou resume son exécution
    suspend fun getPopularMovies(@Query("api_key") apiKey: String):
        PopularMoviesResponse // source de donnée a ecouté
}