package com.example.myapplication_prac51

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast

class GalleryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var galleryAdapter: GalleryAdapter

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

        galleryAdapter = GalleryAdapter(imageIds) { position, view ->
            // Abre el menú contextual al hacer click
            view.showContextMenu()
        }
        recyclerView.adapter = galleryAdapter

        return view
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.context_edit -> {
                Toast.makeText(requireContext(), "Editar", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.context_delete -> {
                Toast.makeText(requireContext(), "Eliminar", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.context_share -> {
                Toast.makeText(requireContext(), "Compartir", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

}


