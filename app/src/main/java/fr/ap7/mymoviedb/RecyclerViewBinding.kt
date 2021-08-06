package fr.ap7.mymoviedb

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.ap7.mymoviedb.model.Movie

// that contains the binding adapter for the RecyclerView list
// la liste de movies sera set dans l'adapter
@BindingAdapter("list") // "list" est un attribut de la RV
fun bindMovies(view: RecyclerView, movies: List<Movie>?) {
    val adapter = view.adapter as MovieAdapter
    adapter.addMovies(movies ?: emptyList())
}