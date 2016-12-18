package com.jrider.routeselector.features.routes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jrider.routeselector.R
import com.jrider.routeselector.RouteSelectorApplication
import kotlinx.android.synthetic.main.fragment_route_list.*
import javax.inject.Inject


class RouteListFragment : Fragment() {

    @Inject
    lateinit var presenter: RouteContract.Presenter

    companion object {
        fun newInstance(): RouteListFragment {
            return RouteListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RouteSelectorApplication.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_route_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val routeAdapter = RouteListAdapter(presenter.allRoutes())

        recyclerview_route_list.adapter = routeAdapter
        recyclerview_route_list.layoutManager = LinearLayoutManager(context)
    }
}