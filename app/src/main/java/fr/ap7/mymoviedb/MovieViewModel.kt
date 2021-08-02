package fr.ap7.mymoviedb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.ap7.mymoviedb.model.Movie
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Locale

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    // Define data model
    // This value is wrapped to MutableLiveData instance
    // result value is mutable because the value can be  changed each time the user request it

    private val popularMoviesLiveData = MutableLiveData<List<Movie>>()
    private val errorLiveData = MutableLiveData<String>()

    val popularMovies: LiveData<List<Movie>> get() = popularMoviesLiveData

    val error: LiveData<String> get() = errorLiveData

    private val disposable = CompositeDisposable()

    // define fetchPopularMovies
    fun fetchPopularMovies() {
        disposable.add(movieRepository.fetchMovies()
            .subscribeOn(Schedulers.io())
            .flatMap { Observable.fromIterable(it.results) } // transforme les items en observable
            .map { it.copy(title = it.title.toUpperCase(Locale.getDefault())) } //mappe une liste d emovie dans une autre liste dont les titres sont en majuscule
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                popularMoviesLiveData.postValue(it)
            }, { error ->
                errorLiveData.postValue("An error is happen : ${error.message}")
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}