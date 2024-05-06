package com.hutapp.org.qr_hut.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hutapp.org.qr_hut.R
import com.hutapp.org.qr_hut.adapter.RecyclerAdapter
import com.hutapp.org.qr_hut.databinding.FragmentSettingBinding
import com.hutapp.org.qr_hut.utilities.replaceFragment


class FragmentSetting : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private lateinit var recyclerAdapter: RecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listSetting = getListSetting()
        recyclerAdapter = RecyclerAdapter(
            list = listSetting,
            itemLayout = R.layout.item_text_setting,
            onItemSettingListener = {
                val fragmentLanguage = FragmentLanguage()
                replaceFragment(parentFragment = this, openFragment = fragmentLanguage)
            }
        )
        binding.reVieSetting.adapter = recyclerAdapter
    }

    private fun getListSetting() = listOf(requireContext().getString(R.string.language))

}