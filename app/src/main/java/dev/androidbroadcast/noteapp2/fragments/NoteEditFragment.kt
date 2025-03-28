package dev.androidbroadcast.noteapp2.fragmentsi

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dev.androidbroadcast.noteapp2.R
import dev.androidbroadcast.noteapp2.data.Note
import dev.androidbroadcast.noteapp2.databinding.FragmentNoteEditBinding
import dev.androidbroadcast.noteapp2.repository.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteEditFragment : Fragment() {
    private var _binding: FragmentNoteEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NoteViewModel
    private val args: NoteEditFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNoteEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)

        if (args.noteId != -1) {

            viewModel.getNoteById(args.noteId).observe(viewLifecycleOwner) { note ->
                note?.let {
                    binding.etNoteName.setText(it.name)
                    binding.etNoteText.setText(it.text)
                    binding.tvNoteDate.text= formatDate(it.date)
                    binding.etNoteName.isEnabled = false
                }
            }
        } else {
            // Режим создания
            binding.tvNoteDate.text = getString(R.string.note_date)
        }

        binding.btnSave.setOnClickListener {
            val noteText = binding.etNoteText.text.toString()
            val noteName = binding.etNoteName.text.toString()
            val currentTime = System.currentTimeMillis()

            if (args.noteId != -1) {
                viewModel.getNoteById(args.noteId).observe(viewLifecycleOwner) { note ->
                    note?.let {
                        val updatedNote = it.copy(date = currentTime, text = noteText)
                        viewModel.update(updatedNote)
                        findNavController().navigateUp()
                    }
                }
            } else {
                val newNote = Note(
                    name = noteName.ifBlank { getString(R.string.default_note_name) },
                    date = currentTime,
                    text = noteText
                )
                viewModel.insert(newNote)
                findNavController().navigateUp()
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


