package com.macovei.silviu.economictime.ui.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.macovei.silviu.economictime.R
import com.macovei.silviu.economictime.data.entity.ListItem
import com.macovei.silviu.economictime.di.Injectable
import com.macovei.silviu.economictime.presenter.DetailsPresenter
import javax.inject.Inject

/**
 * Created by silviumacovei on 2/20/18.
 *
 */
class DetailsFragment : Fragment(), Injectable, DetailsView {



    @Inject
    lateinit var detailsPresenter: DetailsPresenter


    @BindView(R.id.item_date_value)
    lateinit var dateValue: EditText

    @BindView(R.id.item_project_value)
    lateinit var projectValue: EditText

    @BindView(R.id.item_activity_value)
    lateinit var activtyValue: EditText

    @BindView(R.id.item_hours_value)
    lateinit var hoursValue: EditText


    @OnClick(R.id.item_save)
    fun saveItem() {
        detailsPresenter.saveData(ListItem(null,
                dateValue.text.toString(),
                projectValue.text.toString(),
                activtyValue.text.toString(),
                hoursValue.text.toString(),
                ""))
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.details_view, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view!!)
        detailsPresenter.attachView(this)
        if (arguments != null) {
            detailsPresenter.prepareItem(arguments!!.getLong("item", 0))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        detailsPresenter.detachView()
    }

    override fun updateUiWithoutData() {}

    override fun updateUiWithData(item: ListItem) {
        dateValue.setText(item.date)
        projectValue.setText(item.project)
        activtyValue.setText(item.activity)
        hoursValue.setText(item.hours)
    }


    override fun eventFinished() {
        if(detailsPresenter == null){

        }
    }

}
