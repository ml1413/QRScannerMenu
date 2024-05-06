package com.hutapp.org.qr_hut.fragment

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hutapp.org.qr_hut.Activity.MainActivity
import com.hutapp.org.qr_hut.R
import com.hutapp.org.qr_hut.adapter.RecyclerAdapter
import com.hutapp.org.qr_hut.databinding.FragmentLanguageBinding
import com.hutapp.org.qr_hut.model.ModelLanguuage
import com.hutapp.org.qr_hut.utilities.MySharedPreferences

class FragmentLanguage : Fragment() {
    private lateinit var binding: FragmentLanguageBinding
    private val sharedPreferences by lazy { MySharedPreferences(requireActivity().application) }
    private lateinit var recyclerAdapter: RecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listLanguage = getListLanguage()
        recyclerAdapter = RecyclerAdapter(
            list = listLanguage,
            itemLayout = R.layout.item_text_language,
            onItemLanguageListener = { modelLanguage ->
                val locale = modelLanguage.locale
                val currentLanguage = resources.configuration.locale.language

                if (locale.uppercase() != currentLanguage.uppercase()) {
                    sharedPreferences.save(
                        string = locale,
                        key = MySharedPreferences.KEY_LANGUAGE_LOCALE
                    )
                    reloadActivity()
                }

            })
        binding.recView.adapter = recyclerAdapter
    }

    private fun reloadActivity() {
        startActivity(
            Intent(requireActivity(), MainActivity::class.java).setFlags(
                FLAG_ACTIVITY_CLEAR_TOP
            )
        )
        requireActivity().finish()
    }

    private fun getListLanguage(): List<ModelLanguuage> {
        return listOf(
            ModelLanguuage(
                language = "English",
                locale = "EN"
            ),
            ModelLanguuage(
                language = "Русский",
                locale = "RU"
            ),
        )
    }

}