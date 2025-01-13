package com.example.myapplication_prac51

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter(
    private val imageList: List<Int>,
    private val onItemClick: (Int) -> Unit //Cambiado para recibir solo la posición
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
            onItemClick(position) //Esto sigue permitiendo que se active el ActionMode al hacer clic

            //Mostrar el PopupMenu
            val popupMenu = PopupMenu(holder.itemView.context, holder.itemView)
            val inflater = popupMenu.menuInflater
            inflater.inflate(R.menu.context_menu, popupMenu.menu)

            //Obtener el color de textColorSecondary desde los recursos
            val textColorSecondary =
                ContextCompat.getColor(holder.itemView.context, R.color.textColorSecondary)

            //Cambiar el color del texto del PopupMenu
            val menu = popupMenu.menu
            for (i in 0 until menu.size()) {
                val item = menu.getItem(i)
                // Cambiar el color del texto del item
                val spannableString = SpannableString(item.title)
                spannableString.setSpan(
                    ForegroundColorSpan(textColorSecondary),
                    0,
                    spannableString.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                item.title = spannableString
            }

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.context_edit -> {
                        Toast.makeText(
                            holder.itemView.context,
                            "Editar ${position + 1}",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }

                    R.id.context_delete -> {
                        Toast.makeText(
                            holder.itemView.context,
                            "Eliminar ${position + 1}",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }

                    R.id.context_share -> {
                        Toast.makeText(
                            holder.itemView.context,
                            "Compartir ${position + 1}",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
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
