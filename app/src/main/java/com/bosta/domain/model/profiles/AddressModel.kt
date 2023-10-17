package com.bosta.domain.model.profiles

import com.bosta.data.dto.profiles.GeoModel

data class AddressModel(
    val city: String,
    val geo: GeoModel,
    val street: String,
    val suite: String,
    val zipcode: String
)