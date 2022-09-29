package com.ssafy.indive.utils

import android.util.Log
import org.web3j.protocol.Web3j
import org.web3j.protocol.admin.Admin
import org.web3j.protocol.core.methods.request.Transaction
import java.math.BigInteger

fun Admin.unlockAccount(address: String) {
    val unlockAccount = personalUnlockAccount(address, ADMIN_PASSWORD).sendAsync().get()

    Log.d(TAG, "unlockAccount: $address : ${unlockAccount.accountUnlocked()}")
}

fun Web3j.sendTransaction(from: String, to: String, value: BigInteger, data: String) {
    val transaction = Transaction.createFunctionCallTransaction(
        from,
        null, null, null,
        to,
        value,
        toHex(data)
    )

    // 트랜잭션 발생 및 트랜잭션 해시 저장
    val ethSendTransaction = ethSendTransaction(transaction).sendAsync().get()
    val hash = ethSendTransaction.transactionHash

    Log.d(TAG, "endTransaction: $hash")
}

fun toHex(str: String): String {
    return String.format("%x", BigInteger(1, str.toByteArray()))
}
