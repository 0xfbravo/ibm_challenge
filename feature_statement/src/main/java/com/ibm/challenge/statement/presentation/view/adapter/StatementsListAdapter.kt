package com.ibm.challenge.statement.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ibm.challenge.app.statement.R
import com.ibm.challenge.core.Formats
import com.ibm.challenge.presentation.model.statement.StatementModel

class StatementsListAdapter: RecyclerView.Adapter<StatementViewHolder>() {

    var statementsList: List<StatementModel>? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatementViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.statement_item, parent, false)
        return StatementViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return statementsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: StatementViewHolder, position: Int) {
        val statement = statementsList?.get(position)

        statement?.let {
            holder.title.text = statement.title
            holder.description.text = statement.description
            holder.date.text = statement.date.toString()
            holder.value.text = Formats.currencyFormatter.format(statement.value)
        }
    }
}