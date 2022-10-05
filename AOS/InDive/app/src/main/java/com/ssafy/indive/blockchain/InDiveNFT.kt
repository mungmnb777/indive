package com.ssafy.indive.blockchain

import org.web3j.protocol.Web3j
import com.ssafy.indive.blockchain.InDive
import org.web3j.tx.gas.ContractGasProvider
import org.web3j.tx.TransactionManager
import org.web3j.protocol.core.RemoteFunctionCall
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.abi.datatypes.generated.Uint256
import com.ssafy.indive.blockchain.InDive.DonationEventEventResponse
import org.web3j.tx.Contract.EventValuesWithLog
import io.reactivex.Flowable
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.abi.EventEncoder
import org.web3j.tuples.generated.Tuple5
import kotlin.Throws
import org.web3j.tuples.generated.Tuple2
import org.web3j.protocol.core.methods.response.BaseEventResponse
import com.ssafy.indive.blockchain.InDiveNFT
import com.ssafy.indive.blockchain.InDiveNFT.ApprovalForAllEventResponse
import org.web3j.abi.datatypes.generated.Bytes4
import com.ssafy.indive.blockchain.InDiveToken
import io.reactivex.functions.Function
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.*
import org.web3j.abi.datatypes.generated.Uint8
import org.web3j.crypto.Credentials
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.Log
import org.web3j.tx.Contract
import java.math.BigInteger
import java.util.*

/**
 *
 * Auto generated code.
 *
 * **Do not modify!**
 *
 * Please use the [web3j command line tools](https://docs.web3j.io/command_line.html),
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * [codegen module](https://github.com/web3j/web3j/tree/master/codegen) to update.
 *
 *
 * Generated with web3j version 1.4.1.
 */
class InDiveNFT : Contract {
    @Deprecated("")
    protected constructor(
        contractAddress: String?,
        web3j: Web3j?,
        credentials: Credentials?,
        gasPrice: BigInteger?,
        gasLimit: BigInteger?
    ) : super(
        BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit
    ) {
    }

    protected constructor(
        contractAddress: String?,
        web3j: Web3j?,
        credentials: Credentials?,
        contractGasProvider: ContractGasProvider?
    ) : super(
        BINARY, contractAddress, web3j, credentials, contractGasProvider
    ) {
    }

    @Deprecated("")
    protected constructor(
        contractAddress: String?,
        web3j: Web3j?,
        transactionManager: TransactionManager?,
        gasPrice: BigInteger?,
        gasLimit: BigInteger?
    ) : super(
        BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit
    ) {
    }

    protected constructor(
        contractAddress: String?,
        web3j: Web3j?,
        transactionManager: TransactionManager?,
        contractGasProvider: ContractGasProvider?
    ) : super(
        BINARY, contractAddress, web3j, transactionManager, contractGasProvider
    ) {
    }

    fun getApprovalEvents(transactionReceipt: TransactionReceipt?): List<ApprovalEventResponse> {
        val valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt)
        val responses = ArrayList<ApprovalEventResponse>(valueList.size)
        for (eventValues in valueList) {
            val typedResponse = ApprovalEventResponse()
            typedResponse.log = eventValues.log
            typedResponse.owner = eventValues.indexedValues[0].value as String
            typedResponse.approved = eventValues.indexedValues[1].value as String
            typedResponse.tokenId = eventValues.indexedValues[2].value as BigInteger
            responses.add(typedResponse)
        }
        return responses
    }

    fun approvalEventFlowable(filter: EthFilter?): Flowable<ApprovalEventResponse> {
        return web3j.ethLogFlowable(filter).map(object : Function<Log?, ApprovalEventResponse> {
            override fun apply(log: Log): ApprovalEventResponse {
                val eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log)
                val typedResponse = ApprovalEventResponse()
                typedResponse.log = log
                typedResponse.owner = eventValues.indexedValues[0].value as String
                typedResponse.approved = eventValues.indexedValues[1].value as String
                typedResponse.tokenId = eventValues.indexedValues[2].value as BigInteger
                return typedResponse
            }
        })
    }

    fun approvalEventFlowable(
        startBlock: DefaultBlockParameter?,
        endBlock: DefaultBlockParameter?
    ): Flowable<ApprovalEventResponse> {
        val filter = EthFilter(startBlock, endBlock, getContractAddress())
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT))
        return approvalEventFlowable(filter)
    }

    fun getApprovalForAllEvents(transactionReceipt: TransactionReceipt?): List<ApprovalForAllEventResponse> {
        val valueList = extractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt)
        val responses = ArrayList<ApprovalForAllEventResponse>(valueList.size)
        for (eventValues in valueList) {
            val typedResponse = ApprovalForAllEventResponse()
            typedResponse.log = eventValues.log
            typedResponse.owner = eventValues.indexedValues[0].value as String
            typedResponse.operator = eventValues.indexedValues[1].value as String
            typedResponse.approved = eventValues.nonIndexedValues[0].value as Boolean
            responses.add(typedResponse)
        }
        return responses
    }

    fun approvalForAllEventFlowable(filter: EthFilter?): Flowable<ApprovalForAllEventResponse> {
        return web3j.ethLogFlowable(filter)
            .map(object : Function<Log?, ApprovalForAllEventResponse> {
                override fun apply(log: Log): ApprovalForAllEventResponse {
                    val eventValues = extractEventParametersWithLog(APPROVALFORALL_EVENT, log)
                    val typedResponse = ApprovalForAllEventResponse()
                    typedResponse.log = log
                    typedResponse.owner = eventValues.indexedValues[0].value as String
                    typedResponse.operator = eventValues.indexedValues[1].value as String
                    typedResponse.approved = eventValues.nonIndexedValues[0].value as Boolean
                    return typedResponse
                }
            })
    }

    fun approvalForAllEventFlowable(
        startBlock: DefaultBlockParameter?,
        endBlock: DefaultBlockParameter?
    ): Flowable<ApprovalForAllEventResponse> {
        val filter = EthFilter(startBlock, endBlock, getContractAddress())
        filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT))
        return approvalForAllEventFlowable(filter)
    }

    fun getTransferEvents(transactionReceipt: TransactionReceipt?): List<TransferEventResponse> {
        val valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt)
        val responses = ArrayList<TransferEventResponse>(valueList.size)
        for (eventValues in valueList) {
            val typedResponse = TransferEventResponse()
            typedResponse.log = eventValues.log
            typedResponse.from = eventValues.indexedValues[0].value as String
            typedResponse.to = eventValues.indexedValues[1].value as String
            typedResponse.tokenId = eventValues.indexedValues[2].value as BigInteger
            responses.add(typedResponse)
        }
        return responses
    }

    fun transferEventFlowable(filter: EthFilter?): Flowable<TransferEventResponse> {
        return web3j.ethLogFlowable(filter).map(object : Function<Log?, TransferEventResponse> {
            override fun apply(log: Log): TransferEventResponse {
                val eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log)
                val typedResponse = TransferEventResponse()
                typedResponse.log = log
                typedResponse.from = eventValues.indexedValues[0].value as String
                typedResponse.to = eventValues.indexedValues[1].value as String
                typedResponse.tokenId = eventValues.indexedValues[2].value as BigInteger
                return typedResponse
            }
        })
    }

    fun transferEventFlowable(
        startBlock: DefaultBlockParameter?,
        endBlock: DefaultBlockParameter?
    ): Flowable<TransferEventResponse> {
        val filter = EthFilter(startBlock, endBlock, getContractAddress())
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT))
        return transferEventFlowable(filter)
    }

    fun approve(to: String?, tokenId: BigInteger?): RemoteFunctionCall<TransactionReceipt> {
        val function = Function(
            FUNC_APPROVE,
            Arrays.asList<Type<*>>(
                Address(160, to),
                Uint256(tokenId)
            ), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    fun balanceOf(owner: String?): RemoteFunctionCall<BigInteger> {
        val function = Function(
            FUNC_BALANCEOF,
            Arrays.asList<Type<*>>(Address(160, owner)),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    fun getApproved(tokenId: BigInteger?): RemoteFunctionCall<String> {
        val function = Function(
            FUNC_GETAPPROVED,
            Arrays.asList<Type<*>>(Uint256(tokenId)),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Address?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, String::class.java)
    }

    fun getNFTTokens(_nftTokenOwner: String?): RemoteFunctionCall<List<*>> {
        val function = Function(
            FUNC_GETNFTTOKENS,
            Arrays.asList<Type<*>>(Address(160, _nftTokenOwner)),
            Arrays.asList<TypeReference<*>>(object : TypeReference<DynamicArray<Utf8String?>?>() {})
        )
        return RemoteFunctionCall(
            function
        ) {
            val result = executeCallSingleValueReturn<Type<*>, List<*>>(
                function,
                List::class.java
            ) as List<Type<*>>
            convertToNative<Type<*>, Any>(result)
        }
    }

    fun isApprovedForAll(owner: String?, operator: String?): RemoteFunctionCall<Boolean> {
        val function = Function(
            FUNC_ISAPPROVEDFORALL,
            Arrays.asList<Type<*>>(
                Address(160, owner),
                Address(160, operator)
            ),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Bool?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, Boolean::class.java)
    }

    fun name(): RemoteFunctionCall<String> {
        val function = Function(
            FUNC_NAME,
            Arrays.asList(),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Utf8String?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, String::class.java)
    }

    fun owner(): RemoteFunctionCall<String> {
        val function = Function(
            FUNC_OWNER,
            Arrays.asList(),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Address?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, String::class.java)
    }

    fun ownerOf(tokenId: BigInteger?): RemoteFunctionCall<String> {
        val function = Function(
            FUNC_OWNEROF,
            Arrays.asList<Type<*>>(Uint256(tokenId)),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Address?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, String::class.java)
    }

    fun safeMint(_to: String?, _tokenURI: String?): RemoteFunctionCall<TransactionReceipt> {
        val function = Function(
            FUNC_SAFEMINT,
            Arrays.asList<Type<*>>(
                Address(160, _to),
                Utf8String(_tokenURI)
            ), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    fun safeTransferFrom(
        from: String?,
        to: String?,
        tokenId: BigInteger?
    ): RemoteFunctionCall<TransactionReceipt> {
        val function = Function(
            FUNC_safeTransferFrom,
            Arrays.asList<Type<*>>(
                Address(160, from),
                Address(160, to),
                Uint256(tokenId)
            ), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    fun safeTransferFrom(
        from: String?,
        to: String?,
        tokenId: BigInteger?,
        data: ByteArray?
    ): RemoteFunctionCall<TransactionReceipt> {
        val function = Function(
            FUNC_safeTransferFrom,
            Arrays.asList<Type<*>>(
                Address(160, from),
                Address(160, to),
                Uint256(tokenId),
                DynamicBytes(data)
            ), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    fun setApprovalForAll(
        operator: String?,
        approved: Boolean?
    ): RemoteFunctionCall<TransactionReceipt> {
        val function = Function(
            FUNC_SETAPPROVALFORALL,
            Arrays.asList<Type<*>>(
                Address(160, operator),
                Bool(approved)
            ), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    fun supportsInterface(interfaceId: ByteArray?): RemoteFunctionCall<Boolean> {
        val function = Function(
            FUNC_SUPPORTSINTERFACE,
            Arrays.asList<Type<*>>(Bytes4(interfaceId)),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Bool?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, Boolean::class.java)
    }

    fun symbol(): RemoteFunctionCall<String> {
        val function = Function(
            FUNC_SYMBOL,
            Arrays.asList(),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Utf8String?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, String::class.java)
    }

    fun tokenByIndex(index: BigInteger?): RemoteFunctionCall<BigInteger> {
        val function = Function(
            FUNC_TOKENBYINDEX,
            Arrays.asList<Type<*>>(Uint256(index)),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    fun tokenOfOwnerByIndex(owner: String?, index: BigInteger?): RemoteFunctionCall<BigInteger> {
        val function = Function(
            FUNC_TOKENOFOWNERBYINDEX,
            Arrays.asList<Type<*>>(
                Address(160, owner),
                Uint256(index)
            ),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    fun tokenURI(tokenId: BigInteger?): RemoteFunctionCall<String> {
        val function = Function(
            FUNC_TOKENURI,
            Arrays.asList<Type<*>>(Uint256(tokenId)),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Utf8String?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, String::class.java)
    }

    fun totalSupply(): RemoteFunctionCall<BigInteger> {
        val function = Function(
            FUNC_TOTALSUPPLY,
            Arrays.asList(),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    fun transferFrom(
        from: String?,
        to: String?,
        tokenId: BigInteger?
    ): RemoteFunctionCall<TransactionReceipt> {
        val function = Function(
            FUNC_TRANSFERFROM,
            Arrays.asList<Type<*>>(
                Address(160, from),
                Address(160, to),
                Uint256(tokenId)
            ), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    class ApprovalEventResponse : BaseEventResponse() {
        var owner: String? = null
        var approved: String? = null
        var tokenId: BigInteger? = null
    }

    class ApprovalForAllEventResponse : BaseEventResponse() {
        var owner: String? = null
        var operator: String? = null
        var approved: Boolean? = null
    }

    class TransferEventResponse : BaseEventResponse() {
        var from: String? = null
        var to: String? = null
        var tokenId: BigInteger? = null
    }

    companion object {
        const val BINARY = "Bin file was not provided"
        const val FUNC_APPROVE = "approve"
        const val FUNC_BALANCEOF = "balanceOf"
        const val FUNC_GETAPPROVED = "getApproved"
        const val FUNC_GETNFTTOKENS = "getNFTTokens"
        const val FUNC_ISAPPROVEDFORALL = "isApprovedForAll"
        const val FUNC_NAME = "name"
        const val FUNC_OWNER = "owner"
        const val FUNC_OWNEROF = "ownerOf"
        const val FUNC_SAFEMINT = "safeMint"
        const val FUNC_safeTransferFrom = "safeTransferFrom"
        const val FUNC_SETAPPROVALFORALL = "setApprovalForAll"
        const val FUNC_SUPPORTSINTERFACE = "supportsInterface"
        const val FUNC_SYMBOL = "symbol"
        const val FUNC_TOKENBYINDEX = "tokenByIndex"
        const val FUNC_TOKENOFOWNERBYINDEX = "tokenOfOwnerByIndex"
        const val FUNC_TOKENURI = "tokenURI"
        const val FUNC_TOTALSUPPLY = "totalSupply"
        const val FUNC_TRANSFERFROM = "transferFrom"
        val APPROVAL_EVENT = Event(
            "Approval",
            Arrays.asList<TypeReference<*>>(
                object : TypeReference<Address?>(true) {},
                object : TypeReference<Address?>(true) {},
                object : TypeReference<Uint256?>(true) {})
        )
        val APPROVALFORALL_EVENT = Event(
            "ApprovalForAll",
            Arrays.asList<TypeReference<*>>(
                object : TypeReference<Address?>(true) {},
                object : TypeReference<Address?>(true) {},
                object : TypeReference<Bool?>() {})
        )
        val TRANSFER_EVENT = Event(
            "Transfer",
            Arrays.asList<TypeReference<*>>(
                object : TypeReference<Address?>(true) {},
                object : TypeReference<Address?>(true) {},
                object : TypeReference<Uint256?>(true) {})
        )

        @Deprecated("")
        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            credentials: Credentials?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): InDiveNFT {
            return InDiveNFT(contractAddress, web3j, credentials, gasPrice, gasLimit)
        }

        @Deprecated("")
        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): InDiveNFT {
            return InDiveNFT(contractAddress, web3j, transactionManager, gasPrice, gasLimit)
        }

        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            credentials: Credentials?,
            contractGasProvider: ContractGasProvider?
        ): InDiveNFT {
            return InDiveNFT(contractAddress, web3j, credentials, contractGasProvider)
        }

        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            contractGasProvider: ContractGasProvider?
        ): InDiveNFT {
            return InDiveNFT(contractAddress, web3j, transactionManager, contractGasProvider)
        }
    }
}