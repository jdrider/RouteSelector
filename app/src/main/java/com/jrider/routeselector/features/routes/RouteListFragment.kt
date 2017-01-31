package com.jrider.routeselector.features.routes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jrider.routeselector.R
import com.jrider.routeselector.RouteSelectorApplication
import kotlinx.android.synthetic.main.fragment_route_list.*
import javax.inject.Inject


class RouteListFragment : Fragment() {

    @Inject
    lateinit var presenter: RouteContract.Presenter

    lateinit var listAdapter: RouteListAdapter

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

        listAdapter = RouteListAdapter(presenter.allRoutes())

        listAdapter.itemClickAction = { it -> Toast.makeText(context, "Tapped $it", Toast.LENGTH_SHORT).show() }

        recyclerview_route_list.adapter = listAdapter
        recyclerview_route_list.layoutManager = LinearLayoutManager(context)

        //Adds a horizontal line between each item
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        recyclerview_route_list.addItemDecoration(dividerItemDecoration)
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to [Activity.onResume] of the containing
     * Activity's lifecycle.
     */
    override fun onResume() {
        super.onResume()

        //TODO Use RecyclerView DiffUtil?
        listAdapter.updateRoutes(presenter.allRoutes())
    }
}