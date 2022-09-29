package com.ssafy.indive.view.mystudio.editprofile

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentEditProfileBinding
import com.ssafy.indive.utils.USER
import com.ssafy.indive.utils.resizeBitmapFormUri
import com.ssafy.indive.view.login.MemberViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

private const val TAG = "EditProfileFragment"

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {
    private val memberViewModel: MemberViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var imgUri: Uri? = null
    private var backgroundUri: Uri? = null
    private var imgFile: MultipartBody.Part? = null
    private var backgroundFile: MultipartBody.Part? = null

    override fun init() {
        binding.apply {
            memberVM = memberViewModel
        }
        initViewModel()
        initViewModelCallback()
        initClickListener()
    }

    private fun initViewModelCallback() {
        memberViewModel.modifySuccess.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
                Toast.makeText(context, "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViewModel() {
        memberViewModel.memberDetail(sharedPreferences.getLong(USER, 0))
    }

    private fun initClickListener() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            btnEditBackground.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                intent.putExtra("crop", "true")
                BackgroundLauncher.launch(intent)
            }
            btnEditImage.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                intent.putExtra("crop", "true")
                ImageLauncher.launch(intent)
            }
            btnEditProfile.setOnClickListener {
                Log.d(TAG, "initClickListener: ${sharedPreferences.getLong(USER, 0)}")
                Log.d(TAG, "initClickListener: ${etNickname.text.toString()}")
                Log.d(TAG, "initClickListener: ${imgFile}")
                Log.d(TAG, "initClickListener: ${backgroundFile}")
                Log.d(TAG, "initClickListener: ${etIntroduce.text}")
                if (etNickname.text.isNotEmpty() && etIntroduce.text.isNotEmpty()
                    && imgFile != null && backgroundFile != null
                ) {
                    memberViewModel.memberModify(
                        sharedPreferences.getLong(USER, 0),
                        etNickname.text.toString(),
                        imgFile,
                        backgroundFile,
                        etIntroduce.text.toString()
                    )
                } else {
                    Toast.makeText(context, "정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val ImageLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {

            imgUri = it.data?.data

            binding.civProfile.setImageURI(imgUri)
            binding.civProfile.alpha = 1.0F

            var bitMap: Bitmap?

            val uri = it.data?.data
            try {
                if (uri != null) {
                    bitMap = resizeBitmapFormUri(uri, requireContext())
                    createMultiPart(bitMap!!, "img")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val BackgroundLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {

            backgroundUri = it.data?.data

            binding.imgBackground.setImageURI(backgroundUri)
            binding.imgBackground.alpha = 1.0F

            var bitMap: Bitmap?

            val uri = it.data?.data
            try {
                if (uri != null) {
                    bitMap = resizeBitmapFormUri(uri, requireContext())
                    createMultiPart(bitMap!!, "background")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun createMultiPart(bitmap: Bitmap, flag: String) {
        var imageFile: File? = null
        try {
            imageFile = createFileFromBitmap(bitmap)

        } catch (e: IOException) {
            e.printStackTrace()
        }

        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), imageFile!!)
        if (flag == "img") {
            imgFile = MultipartBody.Part.createFormData("image", imageFile!!.name, requestFile)
        } else {
            backgroundFile =
                MultipartBody.Part.createFormData("background", imageFile!!.name, requestFile)
        }
    }

    @Throws(IOException::class)
    private fun createFileFromBitmap(bitmap: Bitmap): File {
        val newFile = File(requireActivity().filesDir, "crew_${System.currentTimeMillis()}.jpg")
        val fileOutputStream = FileOutputStream(newFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, fileOutputStream)
        fileOutputStream.close()
        return newFile
    }
}