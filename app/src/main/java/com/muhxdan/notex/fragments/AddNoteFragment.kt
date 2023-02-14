package com.muhxdan.notex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.muhxdan.notex.MainActivity
import com.muhxdan.notex.R
import com.muhxdan.notex.databinding.FragmentAddNoteBinding
import com.muhxdan.notex.model.NoteModel
import com.muhxdan.notex.viewmodel.NoteViewModel

class AddNoteFragment : Fragment(R.layout.fragment_add_note) {

    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel
        mView = view

        binding.apply {
            buttonSubmit.setOnClickListener {
                saveNote(it)
            }

            buttonBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun saveNote(view: View){
        val noteTitle = binding.inputTitle.text.toString().trim()
        val noteDesc = binding.inputDesc.text.toString().trim()

        if(noteTitle.isNotEmpty()){
            val note = NoteModel(0, noteTitle, noteDesc)
            noteViewModel.upsertNote(note)
            Snackbar.make(view, "Note saved successfuly", Snackbar.LENGTH_SHORT).show()

            view.findNavController().navigate(R.id.action_addNoteFragment_to_homeFragment)
        }else{
            Toast.makeText(context, "Please enter note title!", Toast.LENGTH_SHORT).show()
        }
    }
}