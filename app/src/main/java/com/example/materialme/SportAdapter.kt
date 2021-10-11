package com.example.materialme

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.util.ArrayList

internal class SportsAdapter(
    private val mContext: Context,
    private val mSportsData: ArrayList<Sport>,
    private val onItemClicked: (item: Sport) -> Unit
) : RecyclerView.Adapter<SportsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false)
        ) {
            onItemClicked(mSportsData.elementAt(it))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentSport = mSportsData[position]
        holder.bindTo(currentSport)
        holder.itemView.setOnClickListener {
            onItemClicked(currentSport)
        }

        Glide.with(mContext).load(currentSport.imageResource).into(holder.mImageView)
    }

    override fun getItemCount(): Int {
        return mSportsData.size
    }

    internal inner class ViewHolder(
        itemView: View,
        private val onItemClicked: (position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {
        //Member Variables for the TextViews
        private val mTitleText = itemView.findViewById<TextView>(R.id.title)
        private val mInfoText = itemView.findViewById<TextView>(R.id.subTitle)
        val mImageView: ImageView = itemView.findViewById<ImageView>(R.id.image)

        fun bindTo(currentSport: Sport) {
            //Populate the textviews with data
            mTitleText.text = currentSport.title
            mInfoText.text = currentSport.info
        }

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }
    }
}