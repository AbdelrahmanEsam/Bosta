package com.bosta.data.mappers

import com.bosta.data.dto.profiles.AddressDto
import com.bosta.data.dto.profiles.CompanyDto
import com.bosta.data.dto.profiles.CompanyModel
import com.bosta.data.dto.profiles.UserDto
import com.bosta.domain.model.profiles.AddressModel
import com.bosta.domain.model.profiles.UserModel

fun UserDto.toUserModel(): UserModel {
    return UserModel(
        name = name,
        address = address.toAddressModel(),
        company = company.toCompanyModel(),
        email = email,
        id = id,
        phone = phone,
        username = username,
        website = website
    )
}


fun AddressDto.toAddressModel(): AddressModel {
    return AddressModel(city = city, geo = geo, street = street, suite = suite, zipcode = zipcode)
}

fun CompanyDto.toCompanyModel(): CompanyModel {
    return CompanyModel(bs = bs, catchPhrase = catchPhrase, name = name)
}


