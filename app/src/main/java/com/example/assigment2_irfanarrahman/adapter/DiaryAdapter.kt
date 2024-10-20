package com.example.assigment2_irfanarrahman.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.assigment2_irfanarrahman.DiaryListener
import com.example.assigment2_irfanarrahman.databinding.ItemDiaryBinding
import com.example.assigment2_irfanarrahman.room.DiaryEntities

class DiaryAdapter(var listDiary: List<DiaryEntities>, val listener: DiaryListener) :
    RecyclerView.Adapter<DiaryAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemDiaryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemDiaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listDiary.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listDiary[position]

        holder.binding.apply {
            tvDate.text = data.date
            tvTittle.text = data.tittle
            tvDesc.text = data.note
            ivExpression.setImageDrawable(
                ResourcesCompat.getDrawable(
                    holder.itemView.context.resources,
                    data.expressionImage,
                    null
                )
            )
        }
        holder.binding.ivDelete.setOnClickListener {
            listener.onDelete(data)
        }

        holder.binding.root.setOnClickListener {
            listener.onClick(data.id)
        }


    }

    fun updateDiary(newDiary: List<DiaryEntities>) {
        listDiary = newDiary
        notifyDataSetChanged()
    }
}