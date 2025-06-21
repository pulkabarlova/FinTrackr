package com.polina.fintrackr.core.data.network

class AccountNotFoundException : Exception("Аккаунт не найден")
class NetworkException : Exception("Ошибка при выходе в сеть, проверьте соединение")
