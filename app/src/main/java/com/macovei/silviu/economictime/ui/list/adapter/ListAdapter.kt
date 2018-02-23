package com.macovei.silviu.economictime.ui.list.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.macovei.silviu.economictime.R
import com.macovei.silviu.economictime.data.model.ListItem


/**
 * Created by silviumacovei on 2/20/18.
 */
class ListAdapter : Adapter<ListAdapter.ViewHolder>() {


    private var items: Collection<ListItem>? = null

    private var listener: ItemClickListener? = null

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        val item = items!!.elementAt(position)
        holder?.project?.text = item.project
        holder?.activtity?.text = item.activity
        holder?.hours?.text = item.hours
        holder?.status?.text = item.status

        holder?.edit?.setOnClickListener({
            listener?.goToDetails(item.uid!!)
        })

        holder?.remove?.setOnClickListener({
            listener?.deleteElement(item)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(viewType, parent, false))
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }


    override fun getItemViewType(position: Int): Int {
        return R.layout.list_item
    }

    fun replace(items: Collection<ListItem>) {
        this.items = items
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.item_project)
        lateinit var project: TextView

        @BindView(R.id.item_activity)
        lateinit var activtity: TextView

        @BindView(R.id.item_hours)
        lateinit var hours: TextView

        @BindView(R.id.item_stauts)
        lateinit var status: TextView

        @BindView(R.id.item_edit)
        lateinit var edit: ImageView

        @BindView(R.id.item_remove)
        lateinit var remove: ImageView


        init {
            ButterKnife.bind(this, itemView)
        }

    }

    fun setItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }


    interface ItemClickListener {
        fun goToDetails(uid: Long)
        fun deleteElement(item: ListItem)
    }
}