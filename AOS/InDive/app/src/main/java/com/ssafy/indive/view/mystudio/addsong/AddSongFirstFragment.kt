package com.ssafy.indive.view.mystudio.addsong

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.gun0912.tedpermission.coroutine.TedPermission
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentAddSongFirstBinding
import com.ssafy.indive.utils.resizeBitmapFormUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

class AddSongFirstFragment :
    BaseFragment<FragmentAddSongFirstBinding>(R.layout.fragment_add_song_first) {

    lateinit var imgFile : MultipartBody.Part
    override fun init() {
        clickListnener()
        checkMediaPermission()

    }

    private fun checkMediaPermission() {

        CoroutineScope(Dispatchers.Main).launch {
            TedPermission.create()
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .setDeniedMessage("[설정] -> [권한] -> [파일 및 미디어] -> 허용")
                .check()

        }
    }

    private fun clickListnener() {
        binding.apply {
            btnAddsongFirst.setOnClickListener {
                findNavController().navigate(R.id.action_addSongFirstFragment_to_addSongSecondFragment)
            }
            btnSongGenre.setOnClickListener {
                GenreDialog(requireContext()).let {
                    it.show(parentFragmentManager, null)
                    it.onDismissDialogListener = object : GenreDialog.OnDismissDialogListener {
                        @SuppressLint("ResourceAsColor")
                        override fun onDismiss(Genre: String) {
                            btnSongGenre.setBackgroundResource(R.drawable.btn_round_border_main_color)
                            btnSongGenre.text = Genre
                            btnSongGenre.setTextColor(ContextCompat.getColor(context!!,R.color.main_blue))
                        }

                    }
                }
            }

            binding.tvSelectMusic.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "audio/*"

                musicResultLauncher.launch(intent)
            }

            binding.tvImageText2.setOnClickListener {

                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                intent.putExtra("crop","true")
                photoResultLauncher.launch(intent)

            }
        }
    }

    private val musicResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {

            val music = it.data?.data!!

            val filePathColumn = arrayOf(MediaStore.Audio.Media.DATA)

            val cursor = requireContext().contentResolver.query(music,filePathColumn,null,null,null)
            var format = ""
            var filePath = ""
            var fileName =""
            var saveName = ""

            var file : File? = null

            if(cursor!!.moveToFirst()){
                val index = cursor.getColumnIndex(filePathColumn[0])
                filePath = cursor.getString(index)
                file = File(filePath)

                format = filePath.substring(filePath.lastIndexOf(".") + 1)
                fileName = filePath.substring(filePath.lastIndexOf("/") +1 , filePath.lastIndexOf("."))
                saveName = String.format("%s.%s",fileName,format)

            }

            binding.tvSelectMusic.text = saveName
            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file!!)
            val multiFile = MultipartBody.Part.createFormData("audioFile",file.name,requestFile)

            Log.d("resultLauncher", "multiFile: ${multiFile}")

        }
    }

    private val photoResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {

            binding.btnAddImg.setImageURI(it.data?.data)
            binding.btnAddImg.alpha = 1.0F
            binding.tvImageText.visibility = View.INVISIBLE
            binding.tvImageText2.visibility = View.INVISIBLE

            var bitMap : Bitmap? = null

            val uri = it.data?.data
            try{
                if(uri != null){
                    bitMap = resizeBitmapFormUri(uri,requireContext())
                    createMultiPart(bitMap!!)
                }
            }catch (e : Exception){
                e.printStackTrace()
            }

        }
    }

    private fun createMultiPart(bitmap: Bitmap) {
        var imageFile: File? = null
        try {
            imageFile = createFileFromBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), imageFile!!)
        imgFile = MultipartBody.Part.createFormData("imgFile", imageFile!!.name, requestFile)
    }

    @Throws(IOException::class)
    private fun createFileFromBitmap(bitmap: Bitmap): File {
        val newFile = File(requireActivity().filesDir, "crew_${System.currentTimeMillis()}")
        val fileOutputStream = FileOutputStream(newFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, fileOutputStream)
        fileOutputStream.close()
        return newFile
    }

}