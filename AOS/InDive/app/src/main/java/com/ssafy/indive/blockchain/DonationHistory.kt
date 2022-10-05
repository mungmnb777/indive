package com.ssafy.indive.blockchain

data class DonationHistory (
    val seq : Int,
    val address : String,
    val state : String,
    val value : Int,
    val message : String,
    val time : Long
    )