package com.muhxdan.notex.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.fragment.navArgs
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhxdan.notex.MainActivity
import com.muhxdan.notex.R
import com.muhxdan.notex.adapter.NoteAdapter
import com.muhxdan.notex.databinding.FragmentHomeBinding
import com.muhxdan.notex.model.NoteModel
import com.muhxdan.notex.viewmodel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home), NoteAdapter.OnDeleteClickListener{
    private lateinit var binding: FragmentHomeBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var currentNote: NoteModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel

        setUpRecyclerView()

        binding.floatingButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }

        binding.searchNote.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(query != null){
                    searchNote(query.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

    }

    private fun setUpRecyclerView() {
        noteAdapter = NoteAdapter(this)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            setHasFixedSize(true)
            adapter = noteAdapter
        }

        activity?.let {
            noteViewModel.getAllNotes().observe(viewLifecycleOwner, { note ->
                noteAdapter.differ.submitList(note)
            })
        }
    }

    private fun searchNote(query: String?){
        val searchQuery = "$query%"
        noteViewModel.searchNote(searchQuery).observe(this, { notes ->
            noteAdapter.differ.submitList(notes)
        })
    }

    private fun deletNote(note: NoteModel){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure you want to delete this note?")
            setPositiveButton("Yes"){_, _ ->
                noteViewModel.deleteNote(note)
            }
            setNegativeButton("No"){_, _ ->}
        }.create().show()
    }

    override fun onDeleteClick(note: NoteModel) {
        deletNote(note)
    }
}