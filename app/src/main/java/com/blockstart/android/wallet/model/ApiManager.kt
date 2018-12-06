package com.blockstart.android.wallet.model

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

// TODO Delete this whole class
object ApiManager {

    var transactions = ArrayList<Transaction>()
    var account: Account? = null

    private fun loadJSONFromAsset(input: InputStream): String? {
        var json: String? = null
        try {
            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            json = String(buffer, Charset.defaultCharset())
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json
    }

    fun getTransactionList(input: InputStream) {
        val json = loadJSONFromAsset(input)
        val gson = GsonBuilder().create()
        val txsType = object : TypeToken<ArrayList<Transaction>>() {}.type
        try {
            val txs : ArrayList<Transaction> = gson.fromJson(json, txsType)
            transactions.addAll(txs)
        } catch (e: JsonSyntaxException) {
            Log.d("JSON", "EXC:" + e.localizedMessage)
        }
    }

    fun getAccountInfo(input: InputStream) {
        val json = loadJSONFromAsset(input)
        val gson = GsonBuilder().create()
        try {
            account = gson.fromJson(json, Account::class.java)
        } catch (e: JsonSyntaxException) {
            Log.d("JSON", "EXC:" + e.localizedMessage)
        }
    }
}


