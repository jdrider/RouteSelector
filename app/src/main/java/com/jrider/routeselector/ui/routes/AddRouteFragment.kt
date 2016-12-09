package com.jrider.routeselector.ui.routes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jrider.routeselector.R
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class AddRouteFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_add_route, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val departureTime = view?.findViewById(R.id.text_add_route_departure_time) as TextView

        departureTime.text = formatTime(LocalDateTime.now())
    }

    private fun formatTime(time: LocalDateTime) : String{
        return DateTimeFormatter.ofPattern("h:mm a").format(time)
    }
}