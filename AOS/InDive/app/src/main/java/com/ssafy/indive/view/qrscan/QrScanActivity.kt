package com.ssafy.indive.view.qrscan

import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.google.zxing.integration.android.IntentIntegrator
import com.ssafy.indive.MainActivity
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseActivity
import com.ssafy.indive.databinding.ActivityQrScanBinding
import com.ssafy.indive.utils.TAG

class QrScanActivity : BaseActivity<ActivityQrScanBinding>(R.layout.activity_qr_scan) {

    override fun init() {
        initQrScan()
    }

    private fun initQrScan(){
        val intentIntegrator = IntentIntegrator(this@QrScanActivity)
        intentIntegrator.setPrompt("안내선 안에 QR코드를 맞추면 자동으로 인식됩니다.") //QR코드 스캔 액티비티 하단에 띄울 텍스트 설정
        intentIntegrator.setOrientationLocked(false)                       //화면회전을 막을 것인지 설정 (default 세로모드)
        intentIntegrator.setBeepEnabled(false)                             //QR코드 스캔 시 소리를 낼 지 설정
        zxingActivityResultLauncher.launch(intentIntegrator.createScanIntent())
    }


    private val zxingActivityResultLauncher  = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        val intentResult = IntentIntegrator.parseActivityResult(it.resultCode, it.data)

        if(intentResult.contents != null) {
            MainActivity.successQRScanMsg = intentResult.contents
            finish()
        }else {
            showToast("QR 스캔 실패했습니다")
            finish()
        }
    }

}