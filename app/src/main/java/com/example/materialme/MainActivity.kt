package com.example.materialme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSportsData: ArrayList<Sport>
    private lateinit var mAdapter: SportsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize the RecyclerView
        mRecyclerView = findViewById<View>(R.id.recycleView) as RecyclerView

        //Set the Layout Manager
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        //Initialize the ArrayLIst that will contain the data
        mSportsData = ArrayList<Sport>()

        //Initialize the adapter and set it ot the RecyclerView
        mAdapter = SportsAdapter(this, mSportsData, ::onItemClicked)
        mRecyclerView.adapter = mAdapter

        //Get the data
        initializeData()

        initializeTouchHelper()

        findViewById<FloatingActionButton>(R.id.reset).setOnClickListener {
            initializeData()
        }
    }

    private fun initializeData() {
        //Get the resources from the XML file
        val sportsList = resources.getStringArray(R.array.sports_titles)
        val sportsInfo = resources.getStringArray(R.array.sports_info)
        val sportsImage = resources.obtainTypedArray(R.array.sports_image)

        //Clear the existing data (to avoid duplication)
        mSportsData.clear()

        //Create the ArrayList of Sports objects with the titles and information about each sport
        for (i in sportsList.indices) {
            mSportsData.add(Sport(sportsList[i], sportsInfo[i], sportsImage.getResourceId(i, 0)))
        }

        //Notify the adapter of the change
        mAdapter.notifyDataSetChanged()
        sportsImage.recycle()
    }

    private fun initializeTouchHelper() {
        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val from = viewHolder.adapterPosition
                    val to = target.adapterPosition

                    Collections.swap(mSportsData, from, to)
                    mAdapter.notifyItemMoved(from, to)
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    mSportsData.removeAt(viewHolder.adapterPosition)
                    mAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                }

            }
        ).also {
            it.attachToRecyclerView(mRecyclerView)
        }
    }

    private fun onItemClicked(item: Sport) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("title", item.title)
            putExtra("image_resource", item.imageResource)
        }
        startActivity(intent)
    }
}