package com.ssafy.wallettest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.wallettest.databinding.ActivityMainBinding
import okio.ByteString.Companion.toByteString
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.web3j.crypto.*
import org.web3j.protocol.Web3j
import org.web3j.protocol.admin.Admin
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Numeric
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.Security
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val url = "http://10.0.2.2:8545" // 네트워크 Url
    private lateinit var web3: Web3j
    private lateinit var admin: Admin
    private lateinit var accounts: List<String>

    private lateinit var privateKey: String
    private lateinit var address: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // web3j 초기화
        init()

        // 지갑 생성
        createWallet("123")

        // 계좌 정보 불러오기
        getAccount()

        // 계좌 락 해제
        unlockAccount(accounts[0])

        // 트랜잭션 전송
        //sendTransaction(accounts[0], accounts[1], BigInteger.ONE, "test")
    }

    private fun init() {
        // Provider 변경
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.insertProviderAt(BouncyCastleProvider(), 1)

        web3 = Web3j.build(HttpService(url))
        admin = Admin.build(HttpService(url))
    }

    private fun createWallet(password: String) {
        // 지갑 생성
        val ecKeyPair = Keys.createEcKeyPair()
        val privateKeyInDec = ecKeyPair.privateKey

        privateKey = privateKeyInDec.toString(16)

        val aWallet = Wallet.createLight(password, ecKeyPair)
        address = "0x" + aWallet.address

        // 메시지 서명
        signMessage(privateKey, address, 10, "data")

        binding.apply {
            tvPrivateKey.text = privateKey
            tvAddress.text = address
        }
    }

    private fun getAccount() {
        val ethAccounts = web3.ethAccounts().sendAsync().get()
        accounts = ethAccounts.accounts

        binding.apply {
            //tvAccounts.text = accounts.toString()
        }
    }

    private fun unlockAccount(address: String) {
        val unlockAccount = admin.personalUnlockAccount(address, "admin").sendAsync().get()
    }

    /**
     * from, nonce, gasPrice, gasLimit, to, value, data
     * */
    private fun sendTransaction(from: String, to: String, value: BigInteger, data: String) {
        val transaction = Transaction.createFunctionCallTransaction(
            from,
            null, null, null,
            to,
            value,
            toHex(data)
        )

        // 트랜잭션 발생 및 트랜잭션 해시 저장
        val ethSendTransaction = web3.ethSendTransaction(transaction).sendAsync().get()
        val hash = ethSendTransaction.transactionHash
    }

    private fun toHex(str: String): String {
        return String.format("%x", BigInteger(1, str.toByteArray()))
    }

//    private fun signMessage(privateKey: String, to: String, amount: Int, message: String) {
//        //keccak-256 해시
////        val hash = Hash.sha3("\u0019Ethereum Signed Message:\n32$to$amount$message".toByteArray())
//        val hash = Hash.sha3("$to$amount$message".toByteArray())
//
//        //binding.tvAccounts.text = Numeric.toHexString(hash)
//
//        val credentials = Credentials.create(privateKey)
//        val sign = Sign.signPrefixedMessage(hash, credentials.ecKeyPair)
//
//        var retval = ByteArray(65)
//
//        System.arraycopy(sign.r, 0, retval, 0, 32)
//        System.arraycopy(sign.s, 0, retval, 32, 32)
//        System.arraycopy(sign.v, 0, retval, 64, 1)
//
//        binding.tvAccounts.text = "${Numeric.toHexString(retval)}"
//        recoverSign(hash, Numeric.toHexString(retval))
//    }

//    private fun signMessage(privateKey: String, to: String, amount: Int, message: String) {
//    val credentials = Credentials.create(privateKey)
//
//    val messageByte = "$to$amount$message".toByteArray()
//
//    //binding.tvAccounts.text = Numeric.toHexString(hash)
//
//    val sign = Sign.signPrefixedMessage(messageByte, credentials.ecKeyPair)
//
//    var retval = ByteArray(65)
//
//    System.arraycopy(sign.r, 0, retval, 0, 32)
//    System.arraycopy(sign.s, 0, retval, 32, 32)
//    System.arraycopy(sign.v, 0, retval, 64, 1)
//
//    Log.d(TAG, "signMessage: hash : ${Numeric.toHexString(Sign.getEthereumMessageHash(messageByte))}")
//    Log.d(TAG, "signMessage: signature : ${Numeric.toHexString(retval)}")
//
//    //recoverSign(hash, Numeric.toHexString(retval))
//}

    //    private fun signMessage(privateKey: String, to: String, amount: Int, message: String) {
//        //keccak-256 해시
////        val hash = Hash.sha3("\u0019Ethereum Signed Message:\n32$to$amount$message".toByteArray())
//        val hash = Hash.sha3("$to$amount$message".toByteArray())
//
//        //binding.tvAccounts.text = Numeric.toHexString(hash)
//
//        val credentials = Credentials.create(privateKey)
//        val sign = Sign.signPrefixedMessage(hash, credentials.ecKeyPair)
//
//        var retval = ByteArray(65)
//
//        System.arraycopy(sign.r, 0, retval, 0, 32)
//        System.arraycopy(sign.s, 0, retval, 32, 32)
//        System.arraycopy(sign.v, 0, retval, 64, 1)
//
//        binding.tvAccounts.text = "${Numeric.toHexString(retval)}"
//        recoverSign(hash, Numeric.toHexString(retval))
//    }

//    private fun signMessage(privateKey: String, to: String, amount: Int, message: String) {
//    val credentials = Credentials.create(privateKey)
//
//    val messageByte = "$to$amount$message".toByteArray()
//
//    //binding.tvAccounts.text = Numeric.toHexString(hash)
//
//    val sign = Sign.signPrefixedMessage(messageByte, credentials.ecKeyPair)
//
//    var retval = ByteArray(65)
//
//    System.arraycopy(sign.r, 0, retval, 0, 32)
//    System.arraycopy(sign.s, 0, retval, 32, 32)
//    System.arraycopy(sign.v, 0, retval, 64, 1)
//
//    Log.d(TAG, "signMessage: hash : ${Numeric.toHexString(Sign.getEthereumMessageHash(messageByte))}")
//    Log.d(TAG, "signMessage: signature : ${Numeric.toHexString(retval)}")
//
//    //recoverSign(hash, Numeric.toHexString(retval))
//}
    //    private fun signMessage(privateKey: String, to: String, amount: Int, message: String) {
//        //keccak-256 해시
////        val hash = Hash.sha3("\u0019Ethereum Signed Message:\n32$to$amount$message".toByteArray())
//        val hash = Hash.sha3("$to$amount$message".toByteArray())
//
//        //binding.tvAccounts.text = Numeric.toHexString(hash)
//
//        val credentials = Credentials.create(privateKey)
//        val sign = Sign.signPrefixedMessage(hash, credentials.ecKeyPair)
//
//        var retval = ByteArray(65)
//
//        System.arraycopy(sign.r, 0, retval, 0, 32)
//        System.arraycopy(sign.s, 0, retval, 32, 32)
//        System.arraycopy(sign.v, 0, retval, 64, 1)
//
//        binding.tvAccounts.text = "${Numeric.toHexString(retval)}"
//        recoverSign(hash, Numeric.toHexString(retval))
//    }
//
//    val MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";
//
//    private fun signMessage(privateKey: String, to: String, amount: Int, message: String) {
//        val data = "$to$amount$message".toByteArray()
//
//        val prefix = (MESSAGE_PREFIX + data.size).toByteArray()
//
//        val result = ByteArray(prefix.size + data.size)
//
//        System.arraycopy(prefix, 0, result, 0, prefix.size)
//        System.arraycopy(data, 0, result, prefix.size, data.size)
//
//        val signature = Hash.sha3(result)
//
//        val signMessage =
//    }

//    private fun recoverSign(hash: ByteArray, signature: String) {
//
//        val signatureBytes = Numeric.hexStringToByteArray(signature)
//
//        var v = signatureBytes[64]
//
//        if (v < 27) {
//            v = (v.toInt() + 27).toByte()
//        }
//
//        val sd = Sign.SignatureData(
//            v,
//            Arrays.copyOfRange(signatureBytes, 0, 32),
//            Arrays.copyOfRange(signatureBytes, 32, 64)
//        )
//
//        for (i in 0..3) {
//            val publicKey = Sign.recoverFromSignature(
//                i,
//                ECDSASignature(BigInteger(1, sd.r), BigInteger(1, sd.s)),
//                hash
//            )
//
//            if (publicKey != null) {
//                val recoveredAddress = "0x" + Keys.getAddress(publicKey)
//                Log.d(TAG, "recoverSign: ${recoveredAddress}")
//
//                if (recoveredAddress == address) {
//                    binding.tvRecovered.text = recoveredAddress
//                    break
//                }
//
//            }
//        }
//    }
}