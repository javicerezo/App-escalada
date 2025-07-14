package classApp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoilerna.MainActivity.Companion.globalRoute
import com.example.proyectoilerna.R
import com.example.proyectoilerna.RouteActivity

class ListAdapterRoutes (private val list: ArrayList<Route>): RecyclerView.Adapter<ListAdapterRoutes.MyViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapterRoutes.MyViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.car_route_list, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListAdapterRoutes.MyViewHolder, position: Int) {
        val route: Route = list[position]

        //holder.ivSchoolImage.setImageResource(school.image.toString())
        holder.tvRouteName.text = "${route.numberOfRoute.toString()} .- ${route.name.toString()}"
        holder.tvRouteNumberComments.text = "${route.numbersComments.toString()} comentarios"
        holder.tvRouteMetters.text = "${route.metters.toString()} metros"
        holder.tvRouteDifficulty.text = route.difficulty.toString()

        holder.rlRoute.setOnClickListener {
            holder.callRoutesActivity(route)
            globalRoute = route
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    public class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvRouteName = itemView.findViewById<TextView>(R.id.tv_route_name)
        var tvRouteNumberComments = itemView.findViewById<TextView>(R.id.tv_route_number_comments)
        var tvRouteMetters = itemView.findViewById<TextView>(R.id.tv_route_metters)
        var tvRouteDifficulty = itemView.findViewById<TextView>(R.id.tv_route_difficulty)

        val rlRoute = itemView.findViewById<RelativeLayout>(R.id.rl_route)

        fun callRoutesActivity(route: Route) {
            val intent = Intent(itemView.context, RouteActivity::class.java)
            itemView.context.startActivity(intent)
        }
    }
}