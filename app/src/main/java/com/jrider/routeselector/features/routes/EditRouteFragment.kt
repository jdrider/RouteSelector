package com.jrider.routeselector.features.routes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jrider.routeselector.R
import com.jrider.routeselector.RouteSelectorApplication
import kotlinx.android.synthetic.main.fragment_edit_route.*
import javax.inject.Inject

class EditRouteFragment : Fragment(), RouteContract.View {

    @Inject
    lateinit var presenter: RoutePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RouteSelectorApplication.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_edit_route, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)

        presenter.setRoute(-1)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.detachView()
    }

    override fun setRouteTime(routeTime: String) {
        text_add_route_departure_time.setText(routeTime)
    }
}