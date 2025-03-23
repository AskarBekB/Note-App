package dev.androidbroadcast.noteapp2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dev.androidbroadcast.noteapp2.databinding.FragmentNoteListBinding
import dev.androidbroadcast.noteapp2.repository.NoteViewModel

class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)

        adapter = NoteAdapter { noteId ->
            val action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment(noteId)
            findNavController().navigate(action)
        }
        binding.rvNotes.adapter = adapter

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            adapter.submitList(notes)
        }

        binding.fabAddNote.setOnClickListener {
            val action = NoteListFragmentDirections.actionNoteListFragmentToNoteEditFragment(noteId = -1)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}