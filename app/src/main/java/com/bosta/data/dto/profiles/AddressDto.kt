package com.bosta.data.dto.profiles

data class AddressDto(
    val city: String,
    val geo: GeoModel,
    val street: String,
    val suite: String,
    val zipcode: String
)