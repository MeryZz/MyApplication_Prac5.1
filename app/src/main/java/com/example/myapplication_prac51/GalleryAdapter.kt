package com.example.myapplication_prac51

import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter(
    private val imageList: List<Int>,
    private val onItemClick: (Int) -> Unit // Cambiado para recibir solo la posición
) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    private val selectedItems = mutableSetOf<Int>()
    var isSelectionMode = false

    class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val cardTitle: TextView = view.findViewById(R.id.cardTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery, parent, false)

        val layoutParams = view.layoutParams
        layoutParams.width = (parent.width / 2) - 16
        view.layoutParams = layoutParams

        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])
        holder.cardTitle.text = "Card ${position + 1}"

        holder.itemView.alpha = if (selectedItems.contains(position)) 0.5f else 1.0f

        holder.itemView.setOnClickListener {
            onItemClick(position) // Ahora el clic corto inicia la selección
        }
    }

    override fun getItemCount(): Int = imageList.size

    fun toggleSelection(position: Int) {
        if (selectedItems.contains(position)) {
            selectedItems.remove(position)
        } else {
            selectedItems.add(position)
        }
        notifyItemChanged(position)
    }

    fun clearSelection() {
        selectedItems.clear()
        isSelectionMode = false
        notifyDataSetChanged()
    }

    fun getSelectedItems(): List<Int> = selectedItems.toList()
}
