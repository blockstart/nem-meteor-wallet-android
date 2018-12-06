package com.blockstart.android.wallet.model

data class Wallet(
        val name: String,
        val network: Int,
        val address: Address,
        val creationDate: String,
        val encryptedPrivateKey: EncryptedPrivateKey
)

data class EncryptedPrivateKey(
        val encryptedKey: String,
        val iv: String
)