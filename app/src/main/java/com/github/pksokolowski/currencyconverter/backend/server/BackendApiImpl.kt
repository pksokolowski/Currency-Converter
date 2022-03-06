package com.github.pksokolowski.currencyconverter.backend.server

import com.github.pksokolowski.currencyconverter.backend.BackendApi
import com.github.pksokolowski.currencyconverter.backend.server.model.UserWallet
import com.github.pksokolowski.currencyconverter.backend.server.repository.UserDataRepository
import javax.inject.Inject

class BackendApiImpl @Inject constructor(
    private val userDataRepository: UserDataRepository
) : BackendApi {
    override suspend fun getUserWallet(): UserWallet? {
        return UserWallet(userDataRepository.getUserData())
    }


}