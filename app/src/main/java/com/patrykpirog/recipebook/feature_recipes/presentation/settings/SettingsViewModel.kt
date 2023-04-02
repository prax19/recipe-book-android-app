package com.patrykpirog.recipebook.feature_recipes.presentation.settings

import androidx.lifecycle.ViewModel
import com.patrykpirog.recipebook.feature_authentication.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    var authRepository: AuthRepository
): ViewModel() {

    fun signOut(){
        authRepository.logOutUser()
    }

}