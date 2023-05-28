package com.intermediate.substory.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.intermediate.substory.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra(ID) as String
        val token = intent.getStringExtra(TOKEN) as String

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        detailViewModel.getStoryById(id, token).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is com.intermediate.substory.ResultCustom.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is com.intermediate.substory.ResultCustom.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvNameDetail.text = result.data.name
                        Glide.with(this@DetailActivity).load(result.data.photoUrl)
                            .into(binding.ivDetailPhoto)
                        binding.tvDetailDescription.text = result.data.description
                    }
                    is com.intermediate.substory.ResultCustom.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@DetailActivity,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }


    }

    companion object {
        const val ID = "id"
        const val TOKEN = "token"
    }
}


