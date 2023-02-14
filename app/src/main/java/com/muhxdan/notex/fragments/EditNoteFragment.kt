package com.muhxdan.notex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.muhxdan.notex.MainActivity
import com.muhxdan.notex.R
import com.muhxdan.notex.databinding.FragmentEditNoteBinding
import com.muhxdan.notex.model.NoteModel
import com.muhxdan.notex.viewmodel.NoteViewModel

class EditNoteFragment : Fragment(R.layout.fragment_edit_note) {

    private lateinit var binding: FragmentEditNoteBinding
    private val args: EditNoteFragmentArgs by navArgs()
    private lateinit var currentNote: NoteModel
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditNoteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel

        currentNote = args.note

        binding.apply {
            inputTitle.setText(currentNote.noteTitle)
            inputDesc.setText(currentNote.noteDesc)

            buttonSubmit.setOnClickListener {
                updateNote()
            }

            buttonBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun updateNote() {
        binding.apply {
            val noteTitle = inputTitle.text.toString().trim()
            val noteDesc = inputDesc.text.toString().trim()

            if(noteTitle.isNotEmpty()){
                val note = NoteModel(currentNote.id, noteTitle, noteDesc)
                noteViewModel.upsertNote(note)
                findNavController().navigate(R.id.action_editNoteFragment_to_homeFragment)
            }else{
                Toast.makeText(context, "Please enter note title!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}