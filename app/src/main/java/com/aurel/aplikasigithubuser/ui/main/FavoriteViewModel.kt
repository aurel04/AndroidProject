package com.aurel.aplikasigithubuser.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.aurel.aplikasigithubuser.data.local.UserDatabase
import com.aurel.aplikasigithubuser.data.local.UserFavorite
import com.aurel.aplikasigithubuser.data.local.UserFavoriteDao

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: UserFavoriteDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.userFavoriteDao()
    }

    fun getFavorite(): LiveData<List<UserFavorite>>? {
        return userDao?.getFavorite()
    }
}