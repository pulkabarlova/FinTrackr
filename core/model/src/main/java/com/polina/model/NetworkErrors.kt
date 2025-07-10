package com.polina.model

/**
 * Ошибки сети
 */
class AccountNotFoundException : Exception("Аккаунт не найден")
class NetworkException : Exception("Ошибка при выходе в сеть, проверьте соединение")
