package com.dev.jahid.showdbtask.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.jahid.showdbtask.databinding.FragmentHomeBinding
import com.dev.jahid.showdbtask.ui.adapter.MovieAdapter
import com.dev.jahid.showdbtask.ui.viewmodel.MovieViewmodel
import androidx.core.net.toUri

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var movieViewmodel: MovieViewmodel
    private lateinit var adapter: MovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewmodel = ViewModelProvider(this)[MovieViewmodel::class.java]
        binding.listRecycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = MovieAdapter{
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra("movie_id", it.id)
            startActivity(intent)

        }

        binding.listRecycler.adapter = adapter

        movieViewmodel.movieList.observe(viewLifecycleOwner) {
            Log.d("SizeOfArray","${it.size}")
            adapter.submitList(it)
        }

    }
}