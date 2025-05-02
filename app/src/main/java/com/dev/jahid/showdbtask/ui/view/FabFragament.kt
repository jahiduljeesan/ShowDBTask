package com.dev.jahid.showdbtask.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.jahid.showdbtask.R
import com.dev.jahid.showdbtask.databinding.FabFragamentBinding
import com.dev.jahid.showdbtask.ui.adapter.MovieAdapter
import com.dev.jahid.showdbtask.ui.viewmodel.MovieViewmodel
import com.dev.jahid.showdbtask.utils.ApiConstance


class FabFragament : Fragment() {
    private lateinit var binding: FabFragamentBinding
    private lateinit var viewmodel: MovieViewmodel
    private lateinit var adapter: MovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FabFragamentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtTopbar.text = "Favorites"
        viewmodel = ViewModelProvider(this)[MovieViewmodel::class.java]

        binding.listRecycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = MovieAdapter{
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra("movie_id", it.id)
            startActivity(intent)

        }
        binding.listRecycler.adapter = adapter

        viewmodel.getFavorites(ApiConstance.ACCOUNT_ID)
        viewmodel.favorites.observe(viewLifecycleOwner) {
            Log.d("FavoriteListSize","${it.size}")
            adapter.submitList(it)
        }

    }
}