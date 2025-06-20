package com.polina.fintrackr.core.data.use_case

import android.content.SharedPreferences
import com.polina.fintrackr.core.data.mapper.toAccountModel
import com.polina.fintrackr.core.data.network.AccountNotFoundException
import com.polina.fintrackr.core.data.network.NetworkException
import com.polina.fintrackr.core.data.repositories.AccountRepository
import com.polina.fintrackr.features.count.domain.AccountModel

class GetAndSaveAccountUseCase(
    private val accountRepository: AccountRepository,
    private val sharedPreferences: SharedPreferences
) {

    suspend operator fun invoke(): AccountModel {
        val response = accountRepository.getAccounts()
        if (response.isSuccessful) {
            val account = response.body()?.firstOrNull()
            if (account != null) {
                sharedPreferences.edit()
                    .putInt("accountId", account.id)
                    .apply()
                return account.toAccountModel()
            } else {
                throw AccountNotFoundException()
            }
        } else {
            throw NetworkException()
        }
    }
}