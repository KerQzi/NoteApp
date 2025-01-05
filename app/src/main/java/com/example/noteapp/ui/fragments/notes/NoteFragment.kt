package com.example.noteapp.ui.fragments.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.data.models.NoteModel
import com.example.noteapp.databinding.FragmentNoteBinding
import com.example.noteapp.ui.intefaces.OnClickItem
import com.example.noteapp.ui.adapters.NoteAdapter
import com.example.noteapp.utils.PreferenceHelper

class NoteFragment : Fragment(), OnClickItem {

    private lateinit var binding: FragmentNoteBinding
    private val noteAdapter = NoteAdapter(this, this)
    private val sharedPref = PreferenceHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref.unit(requireContext())
        initialize()
        setUpListeners()
        getData()
    }

    private fun initialize() {
        binding.rvNotes.layoutManager = if (sharedPref.isGridLayout) {
            GridLayoutManager(requireContext(), 2)
        } else {
            LinearLayoutManager(requireContext())
        }

        binding.rvNotes.adapter = noteAdapter
    }

    private fun setUpListeners() = with(binding) {
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_noteDetailFragment)
        }
        btnRvStyle.setOnClickListener {
            rvNotes.layoutManager = if (rvNotes.layoutManager !is GridLayoutManager) {
                val gridLayoutManager = GridLayoutManager(requireContext(), 2)
                btnRvStyle.setImageResource(R.drawable.menu_1)
                sharedPref.isGridLayout = true
                gridLayoutManager
            } else {
                val linearLayoutManager = LinearLayoutManager(requireContext())
                btnRvStyle.setImageResource(R.drawable.shape)
                sharedPref.isGridLayout = false
                linearLayoutManager
            }
        }
    }

    private fun getData() {
        App.appDatabase?.noteDao()?.getAll()?.observe(viewLifecycleOwner){model ->
            noteAdapter.submitList(model)
            noteAdapter.notifyDataSetChanged()
        }
    }

    override fun onLongClick(noteModel: NoteModel) {
        val builder = AlertDialog.Builder(requireContext()).setView(view).create()
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.delete_alert_dialog, null)
        val delete = view.findViewById<TextView>(R.id.delete_ok)
        val cancel = view.findViewById<TextView>(R.id.delete_cancel)

        builder.setView(view)
        builder.window?.setBackgroundDrawableResource(android.R.color.transparent)
        builder.show()

        delete.setOnClickListener {
            App.appDatabase?.noteDao()?.deleteNote(noteModel)
            builder.dismiss()
        }

        cancel.setOnClickListener {
            builder.dismiss()
        }
    }

    override fun onClick(noteModel: NoteModel) {
        val action = NoteFragmentDirections.actionNoteFragmentToNoteDetailFragment(noteModel.id)
        findNavController().navigate(action)
    }
}