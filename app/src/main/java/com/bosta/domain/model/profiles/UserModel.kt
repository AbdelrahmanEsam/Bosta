package com.bosta.domain.model.profiles

import com.bosta.data.dto.profiles.CompanyModel

data class UserModel(
    val address: AddressModel,
    val company: CompanyModel,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)