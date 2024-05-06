package com.hutapp.org.qr_hut.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hutapp.org.qr_hut.R
import com.hutapp.org.qr_hut.databinding.ItemTextLanguageBinding
import com.hutapp.org.qr_hut.databinding.ItemTextSettingBinding
import com.hutapp.org.qr_hut.model.ModelLanguuage

class RecyclerAdapter(
    private val list: List<Any>,
    private val itemLayout: Int,
    val onItemLanguageListener: (ModelLanguuage) -> Unit = {},
    val onItemSettingListener: () -> Unit = {}
) : RecyclerView.Adapter<AllHolders>() {

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllHolders {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_text_language, parent, false)

        return when (itemLayout) {
            R.layout.item_text_language -> AllHolders.HoldersLanguage(view = view)
            R.layout.item_text_setting -> AllHolders.HoldersSetting(view)
            else -> throw Exception()
        }
    }

    override fun onBindViewHolder(holder: AllHolders, position: Int) {
        val item = list[position]

        when (holder) {
            is AllHolders.HoldersLanguage -> holder.initView(
                item = list[position],
                onItemClickListener = onItemLanguageListener
            )

            is AllHolders.HoldersSetting -> holder.initView(
                item = list[position],
                onItemClickListener = onItemSettingListener
            )
        }
    }
}



