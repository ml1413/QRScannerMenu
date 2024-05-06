package com.hutapp.org.qr_hut.fragment

import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hutapp.org.qr_hut.R
import com.hutapp.org.qr_hut.Activity.VisibilityAD
import com.hutapp.org.qr_hut.databinding.FragmentBarcodeBinding

class FragmentBarcode : Fragment() {
    private lateinit var binding: FragmentBarcodeBinding
    private val mainActivity by lazy { requireActivity() as VisibilityAD }
    private var barcode: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBarcodeBinding.inflate(layoutInflater, container, false)
        getArgument()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.visibilityAD(true)
        setTextOnTextView()
        binding.fabCopy.setOnClickListener {
            ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
                ?.let { it.text = barcode }
            Toast.makeText(requireContext(), getString(R.string.copy), Toast.LENGTH_SHORT).show()
        }
    }

    private fun getArgument() {
        barcode = requireArguments().getString(ARG_PARAM_NUMBER, getString(R.string.argument_empty))
    }

    private fun setTextOnTextView() {
        binding.tvBarcode.text = barcode
    }

    companion object {
        fun newInstance(string: String) =
            FragmentBarcode().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_NUMBER, string)
                }
            }

        const val ARG_PARAM_NUMBER = "number"
    }
}