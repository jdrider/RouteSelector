package com.jrider.routeselector.features.routes

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.jrider.routeselector.R
import com.jrider.routeselector.RouteSelectorApplication
import kotlinx.android.synthetic.main.fragment_edit_route.*
import timber.log.Timber
import javax.inject.Inject


class EditRouteFragment : Fragment(), RouteContract.View {

    @Inject
    lateinit var presenter: RouteContract.Presenter

    companion object {

        private const val START_POINT_PLACE_REQUEST = 45

        private const val END_POINT_PLACE_REQUEST = 46

        private const val ROUTE_ID_KEY: String = "EditRouteFragment.RouteId"

        fun newInstance(routeId: String): EditRouteFragment {

            val editRouteFragment = EditRouteFragment()

            val editArguments = Bundle()

            editArguments.putString(ROUTE_ID_KEY, routeId)

            editRouteFragment.arguments = editArguments

            return editRouteFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RouteSelectorApplication.appComponent.inject(this)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_edit_route, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)

        if (arguments != null && arguments.containsKey(ROUTE_ID_KEY)) {
            val routeIdToEdit = arguments.getString(ROUTE_ID_KEY)

            presenter.setRoute(routeIdToEdit)
        } else {
            presenter.setRoute()
        }

        setupArrayAdapter()

        setupClickListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.edit_route, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.edit_route_save) {
            saveRoute()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Receive the result from a previous call to
     * [.startActivityForResult].  This follows the
     * related Activity API as described there in
     * [Activity.onActivityResult].

     * @param requestCode The integer request code originally supplied to
     * *                    startActivityForResult(), allowing you to identify who this
     * *                    result came from.
     * *
     * @param resultCode The integer result code returned by the child activity
     * *                   through its setResult().
     * *
     * @param data An Intent, which can return result data to the caller
     * *               (various data can be attached to Intent "extras").
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == START_POINT_PLACE_REQUEST) {
                val startPlace = PlaceAutocomplete.getPlace(context, data)

                presenter.updateRouteStartPoint(startPlace.address.toString(), startPlace.latLng.latitude, startPlace.latLng.longitude)

                setStartpoint(startPlace.address.toString())

            } else if (requestCode == END_POINT_PLACE_REQUEST) {
                val endPlace = PlaceAutocomplete.getPlace(context, data)

                presenter.updateRouteEndpoint(endPlace.address.toString(), endPlace.latLng.latitude, endPlace.latLng.longitude)

                setEndpoint(endPlace.address.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.detachView()
    }

    override fun setRouteDepartureTime(routeTime: String) {
        text_add_route_departure_time.setText(routeTime)
    }

    override fun setRouteNickname(routeNickname: String) {
        text_add_route_nickname.setText(routeNickname)
    }

    override fun setStartpoint(startPointName: String) {
        text_add_route_start_point.setText(startPointName)
    }

    override fun setEndpoint(endPointName: String) {
        text_add_route_end_point.setText(endPointName)
    }

    override fun setNotificationTime(notificationTime: Int) {

        val notificationTimeIndex = when (notificationTime) {
            15 -> 1
            30 -> 2
            else -> 0
        }

        spinner_add_route_notification_time.setSelection(notificationTimeIndex)
    }

    private fun setupClickListeners() {

        text_add_route_departure_time.setOnClickListener {
            TimePickerDialog(context,
                    timePickerListener,
                    presenter.departureTimeHour(),
                    presenter.departureTimeMinute(),
                    false).show()
        }

        button_map_start_point.setOnClickListener {
            openPlacePredictor(START_POINT_PLACE_REQUEST)
        }

        button_map_end_point.setOnClickListener {
            openPlacePredictor(END_POINT_PLACE_REQUEST)
        }

        text_add_route_start_point.setOnClickListener { openPlacePredictor(START_POINT_PLACE_REQUEST) }

        text_add_route_end_point.setOnClickListener { openPlacePredictor(END_POINT_PLACE_REQUEST) }
    }

    private fun setupArrayAdapter() {
        val notificationTimeAdapter = ArrayAdapter.createFromResource(context,
                R.array.notification_time_options,
                android.R.layout.simple_spinner_dropdown_item)

        spinner_add_route_notification_time.adapter = notificationTimeAdapter

        spinner_add_route_notification_time.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //Purposefully left blank
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, selectedIndex: Int, p3: Long) {
                presenter.updateRouteNotificationTime(getNotificationTimeMinutes(selectedIndex))
            }
        }
    }

    private fun saveRoute() {

        val routeName = text_add_route_nickname.text.toString().trim()

        presenter.updateRouteName(routeName)

        activity.finish()
    }

    private fun getNotificationTimeMinutes(notificationIndex: Int): Int {

        val notificationMinutes = when (notificationIndex) {
            1 -> 15
            2 -> 30
            else -> 60
        }

        return notificationMinutes
    }

    private val timePickerListener: TimePickerDialog.OnTimeSetListener =
            TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minuteOfHour ->
                presenter.routeDepartureTimeUpdated(hourOfDay, minuteOfHour)
            }

    private fun openPlacePredictor(requestCode: Int) {

        try {

            val autoCompleteFilter = AutocompleteFilter.Builder().setCountry("US")
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                    .build()

            val placePredictorIntent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .setFilter(autoCompleteFilter)
                    .build(activity)

            startActivityForResult(placePredictorIntent, requestCode)
        } catch (servicesRepairableException: GooglePlayServicesRepairableException) {
            Timber.e(servicesRepairableException)
        } catch(servicesNotAvailableException: GooglePlayServicesNotAvailableException) {
            Timber.e(servicesNotAvailableException)
        }
    }
}