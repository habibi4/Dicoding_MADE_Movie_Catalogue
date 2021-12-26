package com.android.habibi.dicoding_made_moviecatalogue.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.habibi.core.data.Resource
import com.android.habibi.core.domain.model.Movie
import com.android.habibi.core.ui.MovieAdapter
import com.android.habibi.dicoding_made_moviecatalogue.databinding.FragmentMovieBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModel()
    private var _binding: FragmentMovieBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startObserver()
    }

    private fun startObserver(){
        viewModel.getMovie().observe(viewLifecycleOwner, {
            if (it != null){
                when(it){
                    is Resource.Loading -> {
                        onLoading()
                    }
                    is Resource.Success -> {
                        setAdapter(it.data!!)
                        onSuccess()
                    }
                    is Resource.Error -> {
                        onError()
                    }
                }
            }
        })
    }

    private fun setAdapter(list: List<Movie>){
        movieAdapter = MovieAdapter(list as ArrayList<Movie>){
        }

        with(binding.rvListMovie){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun onLoading(){
        binding.apply {
            progressCircular.visibility = View.VISIBLE
            rvListMovie.visibility = View.GONE
        }
    }

    private fun onSuccess(){
        binding.apply {
            progressCircular.visibility = View.GONE
            rvListMovie.visibility = View.VISIBLE
        }
    }

    private fun onError(){
        binding.apply {
            progressCircular.visibility = View.GONE
            rvListMovie.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}