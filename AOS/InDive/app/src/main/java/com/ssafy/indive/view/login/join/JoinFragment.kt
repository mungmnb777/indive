package com.ssafy.indive.view.login.join

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentJoinBinding
import com.ssafy.indive.model.dto.MemberJoin
import com.ssafy.indive.view.login.MemberViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


private const val TAG = "JoinFragment"

@AndroidEntryPoint
class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {

    private val memberViewModel: MemberViewModel by viewModels()
    private lateinit var key: String
    private var isEmailCheck: Boolean = false
    private var isJoinCheck: Boolean = false

    override fun init() {
        binding.apply {
            etEmail.addTextChangedListener(textChangeListener)
            etPassCheck.addTextChangedListener(passChangeListener)
        }
        initClickListener()
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        memberViewModel.emailCheck.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.tvEmailCheck.visibility = View.GONE
            } else {
                if (it) {
                    binding.tvEmailCheck.visibility = View.VISIBLE
                } else {
                    binding.tvEmailCheck.visibility = View.GONE
                    isEmailCheck = true
                }
            }
        }
    }

    private fun initClickListener() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            btnEmail.setOnClickListener {
                if (isEmailCheck) {
                    key = createKey()

                    val email = etEmail.text.toString()
                    if (isEmail(email)) {
                        GMailSender().sendEmail(email, key)
                        Toast.makeText(context, "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "이메일을 중복을 확인해주세요.", Toast.LENGTH_SHORT).show()
                }

            }

            btnEmailCode.setOnClickListener {
                if (etEmailCode.text.toString() == key) {
                    Toast.makeText(context, "인증이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    etEmailCode.isEnabled = false
                    etEmail.isEnabled = false
                    isJoinCheck = true
                } else {
                    Toast.makeText(context, "인증코드를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
            }



            btnJoin.setOnClickListener {
                if(isJoinCheck){
                    memberViewModel.memberJoin(MemberJoin(etEmail.text.toString(), etPass.text.toString(), etNickname.text.toString(), "1", "1" ))
                    findNavController().navigate(R.id.action_joinFragment_to_walletFragment)
                } else{
                    Toast.makeText(context, "가입정보를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private val textChangeListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {
            memberViewModel.memberEmailCheck(binding.etEmail.text.toString())
        }
    }

    private val passChangeListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        @SuppressLint("ResourceType")
        override fun afterTextChanged(p0: Editable?) {
            binding.tvPassMsg.visibility = View.VISIBLE
            if (binding.etPass.text.toString() == binding.etPassCheck.text.toString()) {
                binding.apply {
                    tvPassMsg.text = "비밀번호 일치"
                    tvPassMsg.setTextColor(ContextCompat.getColor(context!!, R.color.main_blue))
                    isJoinCheck = true
                }
            } else {
                binding.apply {
                    tvPassMsg.text = "비밀번호 불일치"
                    tvPassMsg.setTextColor(ContextCompat.getColor(context!!, R.color.red))
                    isJoinCheck = false
                }
            }
        }
    }

    private fun isEmail(email: String?): Boolean {
        var returnValue = false
        val regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$"
        val p: Pattern = Pattern.compile(regex)
        val m: Matcher = p.matcher(email)
        if (m.matches()) {
            returnValue = true
        }
        return returnValue
    }

    private fun createKey(): String {
        // 인증 코드 생성
        val key = StringBuffer()
        val rnd = Random()

        for (i in 0..7) { // 인증코드 8자리
            val index = rnd.nextInt(3) // 0~2 까지 랜덤
            when (index) {
                0 -> key.append((rnd.nextInt(26) + 97).toChar())
                1 -> key.append((rnd.nextInt(26) + 65).toChar())
                2 -> key.append(rnd.nextInt(10))
            }
        }
        return key.toString()
    }
}