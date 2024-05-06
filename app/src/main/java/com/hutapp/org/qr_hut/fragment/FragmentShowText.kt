package com.hutapp.org.qr_hut.fragment

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.hutapp.org.qr_hut.Activity.VisibilityAD
import com.hutapp.org.qr_hut.R
import com.hutapp.org.qr_hut.databinding.FragmentShowTextBinding

class FragmentShowText : Fragment() {
    private val mainActivity by lazy { requireActivity() as VisibilityAD }
    private lateinit var binding: FragmentShowTextBinding
    private var text = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowTextBinding.inflate(inflater, container, false)
        mainActivity.visibilityAD(true)
        binding.tvField.movementMethod = ScrollingMovementMethod()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgument()
        setTextInTextView()
    }

    private fun getArgument() {
        text = requireArguments().getString(KEY_STRING_BUNDLE, getString(R.string.empty))
    }

    private fun setTextInTextView() {
        binding.tvField.text = text
    }

    companion object {
        fun newInstance(string: String) =
            FragmentShowText().apply {
                arguments = Bundle().apply {
                    putString(KEY_STRING_BUNDLE, string)
                }
            }

        private const val KEY_STRING_BUNDLE = "String"
    }
}