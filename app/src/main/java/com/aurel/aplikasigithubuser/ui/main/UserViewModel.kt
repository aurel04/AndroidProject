package com.aurel.aplikasigithubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.aurel.aplikasigithubuser.api.RetrofitClient
import com.aurel.aplikasigithubuser.data.local.SettingPreferences
import com.aurel.aplikasigithubuser.data.model.User
import com.aurel.aplikasigithubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val preferences: SettingPreferences): ViewModel() {
    val listOfUser = MutableLiveData<ArrayList<User>>()
    fun getTheme() = preferences.getThemeSetting().asLiveData()

    fun setSearchUsers(query: String){
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        listOfUser.postValue(response.body()?.items)
                    }
                }
                // in case ada error biar tau problemnya apa, kyk throw catch
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }
    fun getSearchUsers(): LiveData<ArrayList<User>>{
        return listOfUser
    }

    class Factory(private val preferences: SettingPreferences) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modeClass: Class<T>): T =
            UserViewModel(preferences) as T
    }
}