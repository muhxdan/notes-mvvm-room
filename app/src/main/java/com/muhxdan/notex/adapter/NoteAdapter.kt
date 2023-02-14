package com.muhxdan.notex.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.muhxdan.notex.databinding.NoteItemBinding
import com.muhxdan.notex.fragments.EditNoteFragmentDirections
import com.muhxdan.notex.fragments.HomeFragmentDirections
import com.muhxdan.notex.model.NoteModel
import java.util.Random

class NoteAdapter(private val onDeleteClickListener: OnDeleteClickListener) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<NoteModel>(){
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        val random = Random()
        val color = Color.argb(
            255,
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        )

        holder.binding.apply {
            noteTitleTv.text = currentNote.noteTitle
            noteDescTv.text = currentNote.noteDesc
            iconTitle.imageTintList = ColorStateList.valueOf(color)

            buttonDelete.setOnClickListener {
                onDeleteClickListener.onDeleteClick(currentNote)
            }

            buttonEdit.setOnClickListener {
                val directions = HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote)
                it.findNavController().navigate(directions)
            }

        }

        holder.itemView.setOnClickListener {
            if(holder.binding.showDetail.visibility == View.VISIBLE){
                holder.binding.showDetail.visibility = View.GONE
            }else{
                val initalWidth = holder.itemView.layoutParams.width
                holder.itemView.layoutParams.width = (initalWidth * 1.5).toInt()
                holder.binding.showDetail.visibility = View.VISIBLE
                holder.itemView.requestLayout()
            }
        }
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(note: NoteModel)
    }

}