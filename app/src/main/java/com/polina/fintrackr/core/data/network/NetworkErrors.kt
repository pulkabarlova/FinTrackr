package com.polina.fintrackr.core.data.network

/**
 * Ошибки сети
 */
class AccountNotFoundException : Exception("Аккаунт не найден")
class NetworkException : Exception("Ошибка при выходе в сеть, проверьте соединение")
