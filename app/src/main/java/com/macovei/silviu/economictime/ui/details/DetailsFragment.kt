package com.macovei.silviu.economictime.ui.details

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.macovei.silviu.economictime.R
import com.macovei.silviu.economictime.data.entity.AdministrationItem
import com.macovei.silviu.economictime.di.Injectable
import com.macovei.silviu.economictime.presenter.DetailsPresenter
import com.macovei.silviu.economictime.utils.Constants
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


/**
 * Created by silviumacovei on 2/20/18.
 *
 */
class DetailsFragment : Fragment(), Injectable, DetailsView {


    private var calendar = Calendar.getInstance()

    var date: DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateLabel()
    }

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

    @BindView(R.id.item_status_value)
    lateinit var statusValue: EditText

    @BindView(R.id.item_save)
    lateinit var button: Button


    @OnClick(R.id.item_save, R.id.item_date_value)
    fun saveItem(view: View?) {
        when (view!!.id) {
            R.id.item_save -> {
                if (arguments == null) {
                    detailsPresenter.saveData()
                } else {
                    detailsPresenter.updateItem()
                }
            }
            R.id.item_date_value -> {
                DatePickerDialog(context, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(false)
        return inflater!!.inflate(R.layout.details_view, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view!!)
        detailsPresenter.attachView(this)
        if (arguments != null) {
            detailsPresenter.prepareItem(arguments.getLong(Constants.BUNDLE_ID_KEY, -1))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        detailsPresenter.detachView()
    }

    override fun updateUiWithoutData() {}

    override fun getDateValue(): String {
        return dateValue.text.toString()
    }

    override fun getProjectValue(): String {
        return projectValue.text.toString()
    }

    override fun getHoursValue(): Int {
        return hoursValue.text.toString().takeIf {
            it.isNotBlank()
        }?.toInt() ?: -1

    }

    override fun getActivityValue(): String {
        return activtyValue.text.toString()
    }

    override fun getStatusValue(): String {
        return statusValue.text.toString()
    }

    override fun changeToUpdate() {
        button.text = "Update"
    }


    override fun updateUiWithData(item: AdministrationItem) {
        dateValue.setText(item.date)
        projectValue.setText(item.project)
        activtyValue.setText(item.activity)
        hoursValue.setText(item.hours.toString())
        statusValue.setText(item.status)
    }

    override fun eventFinished() {
        fragmentManager.popBackStack()
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dateValue.setText(sdf.format(calendar.time))
    }

}
