package fr.ap7.mymoviedb

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.ap7.mymoviedb.model.Movie
import java.util.Calendar
import java.util.Locale

// that contains the binding adapter for the RecyclerView list
// la liste de movies sera set dans l'adapter
@BindingAdapter("list") // "list" est un attribut de la RV
fun bindMovies(view: RecyclerView, movies: List<Movie>?) {
    val adapter = view.adapter as MovieAdapter
    adapter.addMovies(movies?.filter {
        // filter function to only include movies released this year.
        it.release_date.startsWith(Calendar.getInstance().get(Calendar.YEAR).toString()
        )
    }?.sortedBy {
        // sorted by title using Kotlin's sortedBy function.
        it.title
    }?.map {
        // make uppercase the title of movie using map
        it.copy(title = it.title.toUpperCase(Locale.getDefault()))
    }
        ?: emptyList())
}