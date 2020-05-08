package com.ibm.challenge.statement.presentation.view.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.statement_item.view.*

class StatementViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val title: TextView = itemView.title
    val description: TextView = itemView.description
    val date: TextView = itemView.date
    val value: TextView = itemView.value

}