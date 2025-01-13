package com.example.myapplication_prac51

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GalleryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var galleryAdapter: GalleryAdapter
    private var actionMode: ActionMode? = null

    private val imageIds = listOf(
        R.drawable.image1, R.drawable.image2, R.drawable.image3,
        R.drawable.image4, R.drawable.image5, R.drawable.image6,
        R.drawable.image7, R.drawable.image8, R.drawable.image9
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewGallery)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        galleryAdapter = GalleryAdapter(imageIds) { position ->
            startSelectionMode(position) // Ahora el clic corto activa el ActionMode
        }

        recyclerView.adapter = galleryAdapter
        return view
    }

    private fun startSelectionMode(position: Int) {
        if (actionMode == null) {
            actionMode = (requireActivity() as AppCompatActivity).startSupportActionMode(actionModeCallback)
        }
        galleryAdapter.isSelectionMode = true
        galleryAdapter.toggleSelection(position)
        updateActionModeTitle()
    }

    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.action_mode_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.action_edit -> {
                    Toast.makeText(requireContext(), "Editar ${galleryAdapter.getSelectedItems()}", Toast.LENGTH_SHORT).show()
                    mode?.finish()
                    true
                }
                R.id.action_delete -> {
                    Toast.makeText(requireContext(), "Eliminar ${galleryAdapter.getSelectedItems()}", Toast.LENGTH_SHORT).show()
                    mode?.finish()
                    true
                }
                R.id.action_share -> {
                    Toast.makeText(requireContext(), "Compartir ${galleryAdapter.getSelectedItems()}", Toast.LENGTH_SHORT).show()
                    mode?.finish()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            galleryAdapter.clearSelection()
            actionMode = null
        }
    }

    private fun updateActionModeTitle() {
        actionMode?.title = "${galleryAdapter.getSelectedItems().size} seleccionados"
        if (galleryAdapter.getSelectedItems().isEmpty()) {
            actionMode?.finish()
        }
    }
}
