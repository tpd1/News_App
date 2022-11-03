package com.example.newsapp
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val imageModelArrayList: MutableList<NewsTopic>):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            var imgView = itemView.findViewById<View>(R.id.category_icon) as ImageView
            var categoryTitle = itemView.findViewById<View>(R.id.category_title) as TextView
        }

    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.row_topic, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = imageModelArrayList[position]
        holder.imgView.setImageResource(info.getImages())
        holder.categoryTitle.text = info.getNames()
    }


}
