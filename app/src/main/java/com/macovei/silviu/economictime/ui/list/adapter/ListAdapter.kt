package com.macovei.silviu.economictime.ui.list.adapter

import android.support.constraint.ConstraintLayout
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
import com.macovei.silviu.economictime.data.entity.AdministrationItem


/**
 * Created by silviumacovei on 2/20/18.
 */
class ListAdapter : Adapter<ListAdapter.ViewHolder>() {


    private var items: Collection<AdministrationItem>? = null

    private var listener: ItemClickListener? = null

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        val item = items!!.elementAt(position)
        holder?.project?.text = item.project
        holder?.activtity?.text = item.activity
        holder?.hours?.text = item.hours.toString()
        holder?.status?.text = item.status

        holder?.body?.setOnClickListener({
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

    fun replace(items: Collection<AdministrationItem>) {
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

        @BindView(R.id.body_view)
        lateinit var body: ConstraintLayout

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
        fun deleteElement(item: AdministrationItem)
    }
}