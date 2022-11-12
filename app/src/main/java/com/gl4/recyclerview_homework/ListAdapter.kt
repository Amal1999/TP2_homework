package com.gl4.recyclerview_homework

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private val data: ArrayList<Presence>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>(), Filterable {
    var dataFilterList: ArrayList<Presence>
    var lastChoice = "0"

    
    init {
        dataFilterList = data
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView
        val text: TextView
        val checkbox: CheckBox

        init {
            image = itemView.findViewById(R.id.image)
            text = itemView.findViewById(R.id.text)
            checkbox = itemView.findViewById(R.id.checkBox)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.presence_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text =
            dataFilterList[position].etudiant.nom + " " + dataFilterList[position].etudiant.prenom

        holder.checkbox.isChecked = dataFilterList[position].presence

        if (dataFilterList[position].etudiant.genre == "M") holder.image.setImageResource(R.drawable.man) else
            holder.image.setImageResource(R.drawable.woman)

        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            dataFilterList[position].presence = isChecked
            Handler().postDelayed({
                filter.filter(lastChoice)
            }, 400)

        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var present = constraint.toString() == "2"
                var absent = constraint.toString() == "1"

                lastChoice = constraint.toString()

                dataFilterList = if (!present && !absent) {
                    data
                } else {
                    var resultList = arrayListOf<Presence>()
                    if(present){
                        for(i in data) {
                            if(i.presence)
                            {
                                resultList.add(i)
                            }
                        }
                        Log.d("Present " , resultList.size.toString() )
                    }
                    if(absent)
                    {
                        for(i in data) {
                            if(!i.presence)
                            {
                                resultList.add(i)
                            }
                        }
                        Log.d("Present " , resultList.size.toString() )
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFilterList = results?.values as ArrayList<Presence>
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }
}

