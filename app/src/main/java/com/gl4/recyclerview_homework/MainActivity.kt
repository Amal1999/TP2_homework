package com.gl4.recyclerview_homework

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    var etudiants: ArrayList<Etudiant> = arrayListOf<Etudiant>(
        Etudiant("Sammari", "Amal", "F"),
        Etudiant("Sammari", "Maher", "M"),
        Etudiant("Achour", "Ines", "F"),
        Etudiant("Laabidi", "Safa", "F"),
        Etudiant("Ghabi", "Nadhmi", "M"),
    )
    var appel = arrayListOf<String>("Not selected","Absents", "Présents")

    var presences = arrayListOf<Presence>()


    val spinner: Spinner by lazy { findViewById(R.id.spinner) }
    val recyclerView: RecyclerView by lazy { findViewById(R.id.recyclerView) }
    var recyclerAdapter: ListAdapter

    init {
        for(i in etudiants)
        {
            presences.add(Presence(i,false))
        }
        recyclerAdapter = ListAdapter(presences)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, appel)
        recyclerView.apply {
            this.adapter = recyclerAdapter
            this.layoutManager = LinearLayoutManager(this.context)
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                recyclerAdapter.filter.filter(position.toString())
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }

    }
}