package com.example.myapplication_prac51

import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter(
    private val imageList: List<Int>,
    private val onItemClick: (Int, View) -> Unit
) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    private val selectedItems = mutableSetOf<Int>()

    class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val cardTitle: TextView = view.findViewById(R.id.cardTitle)
        val btnAccept: Button = view.findViewById(R.id.btnAccept)
        val btnCancel: Button = view.findViewById(R.id.btnCancel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery, parent, false)

        val layoutParams = view.layoutParams
        layoutParams.width = (parent.width / 2) - 16 //para 2 columnas con margen
        view.layoutParams = layoutParams

        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])
        holder.cardTitle.text = "Card ${position + 1}"

        holder.itemView.setOnClickListener {
            onItemClick(position, it)
        }

        // Registra el menú contextual
        holder.itemView.setOnCreateContextMenuListener { menu, _, _ ->
            menu.add(Menu.NONE, R.id.context_edit, Menu.NONE, "Editar")
            menu.add(Menu.NONE, R.id.context_delete, Menu.NONE, "Eliminar")
            menu.add(Menu.NONE, R.id.context_share, Menu.NONE, "Compartir")
        }
    }

    override fun getItemCount(): Int = imageList.size
}

