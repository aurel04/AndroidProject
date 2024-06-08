package com.aurel.aplikasigithubuser.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aurel.aplikasigithubuser.databinding.ActivityUserDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: UserDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        showLoading(true)

        viewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)

        viewModel.setUserDetail(username.toString())

        viewModel.getUserDetail().observe(this, {
            if (it != null){
                showLoading(false)
                binding.apply {
                    tvNameDetail.text = it.name
                    tvUserNameDetail.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Followers"
                    Glide.with(this@UserDetailActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivUserDetail)
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            //ngeset ui ga bisa di bg, makanya perlu dipindah ke main
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0){
                        binding.tbFavorite.isChecked = true
                        _isChecked = true
                    }else{
                        binding.tbFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.tbFavorite.setOnClickListener{
            //value jdi kebalika, yg kelike jdi ga, yang ga like jdi like
            _isChecked = !_isChecked
            if (_isChecked){
                viewModel.addFavorite(username.toString(), id, avatarUrl.toString())
            } else{
                viewModel.removeFavorite(id)
            }
            binding.tbFavorite.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            vpFollow.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(vpFollow)
        }
    }
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}