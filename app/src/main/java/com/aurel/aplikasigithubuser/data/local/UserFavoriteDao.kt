package com.aurel.aplikasigithubuser.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserFavoriteDao {
    @Insert
    fun addFavorite(userFavorite: UserFavorite)
    
    @Query("SELECT * FROM user_favorite")
    fun getFavorite(): LiveData<List<UserFavorite>>

    @Query("SELECT count(*) FROM user_favorite WHERE user_favorite.id = :id")
    fun checkUser(id: Int): Int

    @Query("DELETE FROM user_favorite WHERE user_favorite.id = :id")
    fun removeFavorite(id: Int): Int
}