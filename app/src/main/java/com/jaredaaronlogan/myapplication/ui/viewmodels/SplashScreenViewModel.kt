package com.jaredaaronlogan.myapplication.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.jaredaaronlogan.myapplication.ui.repositories.UserRepository

class SplashScreenViewModel(application: Application): AndroidViewModel(application) {
    fun isUserLoggedIn() = UserRepository.isUserLoggedIn()
}