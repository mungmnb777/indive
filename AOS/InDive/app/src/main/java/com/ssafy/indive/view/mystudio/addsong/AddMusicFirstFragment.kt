package com.ssafy.indive.view.mystudio.addsong

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gun0912.tedpermission.coroutine.TedPermission
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentAddSongFirstBinding
import com.ssafy.indive.utils.TAG
import com.ssafy.indive.utils.resizeBitmapFormUri
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*
import java.net.URI
import javax.inject.Inject

@AndroidEntryPoint
class AddMusicFirstFragment :
    BaseFragment<FragmentAddSongFirstBinding>(R.layout.fragment_add_song_first) {

    private val addMusicViewModel: AddMusicViewModel by activityViewModels()

    private var imgFile: MultipartBody.Part? = null
    private var musicFile: MultipartBody.Part? = null

    private var imgUri: Uri? = null
    private var musicTitleSaveName = ""
    override fun init() {

        binding.apply {

            addMusicVM = addMusicViewModel
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        initClickListener()
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

    private fun initClickListener() {
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
                            btnSongGenre.setTextColor(
                                ContextCompat.getColor(
                                    context!!,
                                    R.color.main_blue
                                )
                            )
                            addMusicViewModel.setGenre(Genre)
                        }

                    }
                }
            }

            tvSelectMusic.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "audio/*"

                musicResultLauncher.launch(intent)
            }

            tvImageText2.setOnClickListener {

                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                intent.putExtra("crop", "true")
                photoResultLauncher.launch(intent)

            }

            btnAddsongFirst.setOnClickListener {
//                addMusicViewModel.modifyMember()
                Log.d(
                    "AddMusicFirstFragment",
                    "initClickListener: ${addMusicViewModel.title.value}"
                )
                if (checkEmpty()) {
                findNavController().navigate(R.id.action_addSongFirstFragment_to_addSongSecondFragment)
                }
            }
        }
    }

    private fun checkEmpty(): Boolean {

        if (musicFile == null) {
            showToast("곡을 등록해주세요")
            return false
        }

        if (addMusicViewModel.title.value == "" || addMusicViewModel.description.value == "") {
            showToast("빈 칸을 입력해주세요")
            return false
        }

        if (addMusicViewModel.genre.value == "장르선택 +") {
            showToast("장르를 선택해주세요")
            return false
        }

        return true
    }


    private val musicResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {

            val music = it.data?.data!!

            val filePathColumn = arrayOf(MediaStore.Audio.Media.DATA)

            val type = requireContext().contentResolver.getType(music)
            Log.d(TAG, "type: $type")

            val cursor =
                requireContext().contentResolver.query(music, filePathColumn, null, null, null)
            var format = ""
            var filePath = ""
            var fileName = ""
            var saveName = ""

            var file: File? = null

            if (cursor!!.moveToFirst()) {
                val index = cursor.getColumnIndex(filePathColumn[0])
                filePath = cursor.getString(index)
                file = File(filePath)

                format = filePath.substring(filePath.lastIndexOf(".") + 1)
                fileName =
                    filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."))
                saveName = String.format("%s.%s", fileName, format)

            }
            Log.d(TAG, "file: $file")
            binding.tvSelectMusic.text = saveName
            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file!!)
            musicFile = MultipartBody.Part.createFormData("musicFile", file.name, requestFile)

            musicTitleSaveName = saveName
            addMusicViewModel.setMusic(musicFile!!)

            Log.d("resultLauncher", "multiFile: ${musicFile!!.body}")

        }
    }

    private val photoResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {

            imgUri = it.data?.data

            binding.btnAddImg.setImageURI(imgUri)
            binding.btnAddImg.alpha = 1.0F
            binding.tvImageText.visibility = View.INVISIBLE
            binding.tvImageText2.visibility = View.INVISIBLE

            var bitMap: Bitmap? = null

            val uri = it.data?.data
            Log.d(TAG, "uri: $uri")
            try {
                if (uri != null) {
                    bitMap = resizeBitmapFormUri(uri, requireContext())
                    createMultiPart(bitMap!!)
                }
            } catch (e: Exception) {
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
        imgFile = MultipartBody.Part.createFormData("image", imageFile!!.name, requestFile)
        addMusicViewModel.setCoverImg(imgFile)
    }

    @Throws(IOException::class)
    private fun createFileFromBitmap(bitmap: Bitmap): File {
        val newFile = File(requireActivity().filesDir, "crew_${System.currentTimeMillis()}.jpg")
        val fileOutputStream = FileOutputStream(newFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, fileOutputStream)
        fileOutputStream.close()
        return newFile
    }

    override fun onResume() {
        super.onResume()

        if (imgUri != null) {
            binding.apply {
                btnAddImg.setImageURI(imgUri)
                btnAddImg.alpha = 1.0F
                tvImageText.visibility = View.INVISIBLE
                tvImageText2.visibility = View.INVISIBLE
            }

        }

        if (musicTitleSaveName != "") {
            binding.tvSelectMusic.text = musicTitleSaveName
        }
    }
}