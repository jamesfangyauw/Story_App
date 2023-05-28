package com.intermediate.substory.view.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.LoadingStateAdapter
import com.dicoding.picodiploma.loginwithanimation.db.StoryEntity
import com.dicoding.picodiploma.loginwithanimation.injection.Injection
import com.intermediate.substory.MapsActivity
import com.intermediate.substory.RecyclerViewAdapter
import com.intermediate.substory.databinding.ActivityMainBinding
import com.intermediate.substory.model.UserPreference
import com.intermediate.substory.view.add.UploadNewStory
import com.intermediate.substory.view.detail.DetailActivity
import com.intermediate.substory.view.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = RecyclerViewAdapter()

        binding.rvItem.layoutManager = LinearLayoutManager(this)

        setupView()
        setupAction()

        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(
                UserPreference.getInstance(dataStore),
                Injection.provideRepository(this)
            )
        )[MainViewModel::class.java]



        mainViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                mainViewModel.getAllStories("Bearer ${user.token}").observe(this) { result ->
                    adapter.submitData(lifecycle, result)

                    adapter.setOnItemClickCallback(object :
                        RecyclerViewAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: StoryEntity) {
                            val intentToDetail =
                                Intent(this@MainActivity, DetailActivity::class.java)
                            intentToDetail.putExtra(DetailActivity.ID, data.id)
                            intentToDetail.putExtra(DetailActivity.TOKEN, "Bearer ${user.token}")
                            startActivity(intentToDetail)
                        }
                    })

                    adapter.addLoadStateListener { loadState ->
                        when (loadState.refresh) {
                            is LoadState.Loading -> {
                                binding.apply {
                                    pbMain.visibility = View.VISIBLE
                                    rvItem.visibility = View.GONE
                                }
                            }
                            is LoadState.NotLoading -> {
                                binding.apply {
                                    Log.d("testing yy", "$result")
                                    pbMain.visibility = View.GONE
                                    rvItem.visibility = View.VISIBLE
                                }


                            }
                            is LoadState.Error -> {
                                binding.pbMain.visibility = View.GONE
                                binding.rvItem.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                binding.rvItem.adapter = adapter.withLoadStateFooter(
                    footer = LoadingStateAdapter {
                        adapter.retry()
                    }
                )
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
            binding.addStory.setOnClickListener {
                val intentToAddStory = Intent(this@MainActivity, UploadNewStory::class.java)
                intentToAddStory.putExtra(UploadNewStory.TOKEN_USER, "Bearer ${user.token}")
                startActivity(intentToAddStory)
            }

            binding.listMaps.setOnClickListener {
                val moveToMaps = Intent(this@MainActivity, MapsActivity::class.java)
                moveToMaps.putExtra(MapsActivity.DATA, "Bearer ${user.token}")
                startActivity(moveToMaps)
            }
        }


    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            mainViewModel.logout()
        }
    }

    companion object {
        val ID_USER = "ID USER"
    }
}