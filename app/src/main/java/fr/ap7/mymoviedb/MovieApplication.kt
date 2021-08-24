package fr.ap7.mymoviedb

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import fr.ap7.mymoviedb.api.MovieService
import fr.ap7.mymoviedb.database.MovieDatabase
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class MovieApplication : Application() {
    lateinit var movieRepository: MovieRepository

    override fun onCreate() {
        super.onCreate()

        // Init movieRepository
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val movieService = retrofit.create(MovieService::class.java)
        val movieDatabase = MovieDatabase.getInstance(applicationContext)

        movieRepository = MovieRepository(movieService, movieDatabase)

        // schedule MovieWorker to retrieve and save the movies

        // This schedules MovieWorker to run every hour when the device is connected
        // to the network. MovieWorker will fetch the list of movies from the network and
        // save it to the local database.
        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest = PeriodicWorkRequest
            .Builder(MovieWorker::class.java, 1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .addTag("movie-work")
            .build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
}