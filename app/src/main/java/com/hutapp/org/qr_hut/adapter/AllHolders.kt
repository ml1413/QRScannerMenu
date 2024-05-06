package com.hutapp.org.qr_hut.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hutapp.org.qr_hut.databinding.ItemTextLanguageBinding
import com.hutapp.org.qr_hut.databinding.ItemTextSettingBinding
import com.hutapp.org.qr_hut.model.ModelLanguuage

sealed class AllHolders(view: View) : RecyclerView.ViewHolder(view) {
    class HoldersLanguage(val view: View) : AllHolders(view) {
        private val binging = ItemTextLanguageBinding.bind(view)
        fun initView(item: Any, onItemClickListener: (ModelLanguuage) -> Unit) {
            val model = item as ModelLanguuage
            binging.textView.text = model.language
            itemView.setOnClickListener { onItemClickListener(item) }
        }
    }

    class HoldersSetting(view: View) : AllHolders(view) {
        private val binding = ItemTextSettingBinding.bind(view)
        fun initView(item: Any, onItemClickListener: () -> Unit) {
            val string = item as String
            binding.textView.text = string
            itemView.setOnClickListener { onItemClickListener() }
        }
    }

}