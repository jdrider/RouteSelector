package com.jrider.routeselector.features.routes

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.jrider.routeselector.R

class RouteListAdapter(private var routeList: List<Route>) : RecyclerView.Adapter<RouteListAdapter.RouteListItemViewHolder>() {

    var itemClickAction = { routeId: String -> Unit }

    override fun onBindViewHolder(holder: RouteListItemViewHolder?, position: Int) {

        val routeForPosition = routeList[position]

        holder?.bindRoute(routeForPosition)
    }

    override fun getItemCount(): Int {
        return routeList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RouteListItemViewHolder {

        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.route_list_item, parent, false)

        return RouteListItemViewHolder(itemView)
    }

    fun updateRoutes(updatedRouteList: List<Route>) {
        routeList = updatedRouteList

        notifyDataSetChanged()
    }

    inner class RouteListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindRoute(route: Route) {

            val routeNameTextView = itemView.findViewById(R.id.text_route_list_item_name) as TextView
            val routeEnabledSwitch = itemView.findViewById(R.id.switch_route_list_item_enabled) as Switch

            routeNameTextView.text = route.name
            routeEnabledSwitch.isChecked = route.enabled

            routeNameTextView.tag = route.id.toString()

            routeNameTextView.setOnClickListener { it -> this@RouteListAdapter.itemClickAction(it.tag as String) }
        }
    }
}
