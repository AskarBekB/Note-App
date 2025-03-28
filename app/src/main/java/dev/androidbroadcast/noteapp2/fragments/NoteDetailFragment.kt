package dev.androidbroadcast.noteapp2.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dev.androidbroadcast.noteapp2.databinding.FragmentNoteDetailBinding
import dev.androidbroadcast.noteapp2.repository.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteDetailFragment : Fragment() {

    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NoteViewModel
    private val args: NoteDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)

        viewModel.getNoteById(args.noteId).observe(viewLifecycleOwner) { note ->
            note?.let {
                binding.tvNoteName.text = it.name
                binding.tvNoteDate.text = formatDate(it.date)
                binding.tvNoteText.text = it.text
            }
        }

        binding.btnEdit.setOnClickListener {
            val action = NoteDetailFragmentDirections.actionNoteDetailFragmentToNoteEditFragment(args.noteId)
            findNavController().navigate(action)
        }

        binding.btnDelete.setOnClickListener {
            val noteId = arguments?.getInt("noteId", -1)
            if (noteId != -1) {
                viewModel.notes.observe(viewLifecycleOwner) { notes ->
                    val noteToDelete = notes.find { it.id == noteId }
                    noteToDelete?.let {
                        viewModel.delete(it)
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}
