package com.hutapp.org.qr_hut.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hutapp.org.qr_hut.Activity.VisibilityAD
import com.hutapp.org.qr_hut.R
import com.hutapp.org.qr_hut.databinding.FragmentAddQrCodeBinding
import com.hutapp.org.qr_hut.utilities.GenerateQRCode
import com.hutapp.org.qr_hut.utilities.replaceFragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FragmentAddQrCode : Fragment() {
    private lateinit var binding: FragmentAddQrCodeBinding
    private val generateQRCode by lazy { GenerateQRCode(requireContext().applicationContext) }
    private val mainActivity by lazy { requireActivity() as VisibilityAD }
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddQrCodeBinding.inflate(inflater, container, false)
        requireActivity().window.setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        mainActivity.visibilityAD(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btGenerate.setOnClickListener {
            if (binding.etName.text.isBlank() || binding.etField.text.isBlank()) {
                showToast()
            } else {
                val data = getCurrentDate()
                val name = "${binding.etName.text} $data"
                val textForQrGenerator = binding.etField.text.toString()
                //todo нужно взять ширину и высоту из view на другоя лайоуте
                val bitmap = generateQRCode.generateQRCode(
                    width = 500,
                    height = 500,
                    text = textForQrGenerator
                )
                if (bitmap != null) {
                    hideKeyboard()
                    handler.postDelayed({
                        val fragment = FragmentShowQrCode.newInstance(name, bitmap)
                        replaceFragment(parentFragment = this, openFragment = fragment)
                    }, 100)

                }
            }
        }
    }

    override fun onPause() {
        handler.removeCallbacksAndMessages(null)
        super.onPause()
    }

    private fun hideKeyboard() {
        /** спрятать  клавиатуру */
        requireActivity().currentFocus?.let {
            (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .apply { hideSoftInputFromWindow(it.windowToken, 0) }
        }
    }

    private fun getCurrentDate(): String {
        return SimpleDateFormat(
            "yyyy-MM-dd HH-mm-ss",
            Locale.getDefault()
        ).format(Date(System.currentTimeMillis()))
    }


    private fun showToast() {
        Toast.makeText(requireContext(), getString(R.string.empty_field), Toast.LENGTH_SHORT).show()
    }
}