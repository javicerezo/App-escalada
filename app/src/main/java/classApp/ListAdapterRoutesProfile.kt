package classApp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoilerna.R

class ListAdapterRoutesProfile (private val list: ArrayList<Comment>): RecyclerView.Adapter<ListAdapterRoutesProfile.MyViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.car_route_profile_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val routeProfile: Comment = list[position]

        holder.tvRouteProfile.setText("Vía ${(position+1).toString()}.- ${routeProfile.idNameRoute.toString()}(${routeProfile.sectorOfRoute}) - " +
                "${routeProfile.difficultyOfRoute} - ${routeProfile.mettersOfRoute} metros - ${routeProfile.date.toString()}")
    }

    override fun getItemCount(): Int {
        return list.size
    }

     class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        var tvRouteProfile = itemView.findViewById<TextView>(R.id.tv_route_profile)

    }
}