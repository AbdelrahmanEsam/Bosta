package com.bosta.presentation.profile

sealed interface ProfileIntents{
    data object GetUserProfileAndAlbumsData : ProfileIntents
}