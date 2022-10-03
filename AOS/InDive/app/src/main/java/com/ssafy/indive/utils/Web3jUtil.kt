package com.ssafy.indive.utils

import CHAIN_ID
import INDIVETOKEN_ADDRESS
import INDIVE_ADDRESS
import android.util.Log
import com.ssafy.indive.blockchain.InDive
import com.ssafy.indive.blockchain.InDiveToken
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.admin.Admin
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.tx.FastRawTransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.tx.response.PollingTransactionReceiptProcessor
import java.math.BigInteger

fun Admin.unlockAccount(address: String, password: String) {
    val unlockAccount = personalUnlockAccount(address, password).sendAsync().get()

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

    Log.d(TAG, "endTransaction: from : $from | to : $to | value : $value | data : $data \n| hash : $hash")
}

fun toHex(str: String): String {
    return String.format("%x", BigInteger(1, str.toByteArray()))
}

/**
 * ===========
 * InDive
 * ===========
 */

// 후원자 목록
fun Web3j.donate(privateKey: String, to: String, value: Int, message: String){
    val credential = Credentials.create(privateKey)
    val gasProvider = DefaultGasProvider()
    val manager = FastRawTransactionManager(
        this,
        credential,
        CHAIN_ID,
        PollingTransactionReceiptProcessor(this, TX_END_CHECK_DURATION, TX_END_CHECK_RETRY)
    )

    val inDive = InDive.load(INDIVE_ADDRESS, this, manager, gasProvider)

    val result = inDive.donate(to, BigInteger(value.toString()), message).sendAsync().get()

    Log.d(TAG, "donate: ${result.transactionHash}")
}

// 후원자 목록
fun Web3j.getDonatorList(privateKey: String, artistAddress: String){
    val credential = Credentials.create(privateKey)
    val gasProvider = DefaultGasProvider()
    val manager = FastRawTransactionManager(
        this,
        credential,
        PollingTransactionReceiptProcessor(this, TX_END_CHECK_DURATION, TX_END_CHECK_RETRY)
    )

    val inDive = InDive.load(INDIVE_ADDRESS, this, manager, gasProvider)

    val result = inDive.getDonatorList(artistAddress).sendAsync().get()

    Log.d(TAG, "getDonatorList: $result")
}

/**
 * ===========
 * InDiveToKen
 * ===========
 */
// Approve
fun Web3j.setTokenApprove(privateKey: String, spender: String, amount: Int){
    val credential = Credentials.create(privateKey)
    val gasProvider = DefaultGasProvider()
    val manager = FastRawTransactionManager(
        this,
        credential,
        CHAIN_ID,
        PollingTransactionReceiptProcessor(this, TX_END_CHECK_DURATION, TX_END_CHECK_RETRY)
    )

    val inDiveToken = InDiveToken.load(INDIVETOKEN_ADDRESS, this, manager, gasProvider)

    val result = inDiveToken.approve(spender, BigInteger(amount.toString())).sendAsync().get()

    Log.d(TAG, "setTokenApprove: ${result.transactionHash}")
}

// 토큰 개수 조회
fun Web3j.getTokenBalanceOf(privateKey : String, owner: String){
    val credential = Credentials.create(privateKey)
    val gasProvider = DefaultGasProvider()
    val manager = FastRawTransactionManager(
        this,
        credential,
        PollingTransactionReceiptProcessor(this, TX_END_CHECK_DURATION, TX_END_CHECK_RETRY)
    )

    val inDiveToken = InDiveToken.load(INDIVETOKEN_ADDRESS, this, manager, gasProvider)

    val balance = inDiveToken.balanceOf(owner).sendAsync().get()

    Log.d(TAG, "getTokenBalanceOf: Address = $owner | Value = $balance")
}

/**
 * ===========
 * InDiveNFT
 * ===========
 */