package classApp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoilerna.MainActivity.Companion.globalSector
import com.example.proyectoilerna.R
import com.example.proyectoilerna.SectorActivity

class ListAdapterSectors (private val list: ArrayList<Sector>): RecyclerView.Adapter<ListAdapterSectors.MyViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapterSectors.MyViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.car_sector_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListAdapterSectors.MyViewHolder, position: Int) {
        val sector: Sector = list[position]

        holder.tvSectorName.text = sector.name.toString()
        holder.tvSectorNumberRoutes.text = "${sector.numbersRoutes.toString()} vías,"
        holder.tvSectorStyle.text = sector.style.toString()
        holder.tvSectorOrientation.text = sector.orientation.toString()
        holder.tvSectorWalkingTime.text = "${sector.walkingTime.toString()} min"

        holder.llSector.setOnClickListener {
            holder.callSectorActivity(sector)
            globalSector = sector
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    public class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        var tvSectorName = itemView.findViewById<TextView>(R.id.tv_sector_name)
        var tvSectorNumberRoutes = itemView.findViewById<TextView>(R.id.tv_sector_number_routes)
        var tvSectorOrientation = itemView.findViewById<TextView>(R.id.tv_sector_orientation)
        var tvSectorStyle = itemView.findViewById<TextView>(R.id.tv_sector_style)
        var tvSectorWalkingTime = itemView.findViewById<TextView>(R.id.tv_sector_walking_time)

        val llSector = itemView.findViewById<RelativeLayout>(R.id.ll_sector)

        fun callSectorActivity(sector: Sector) {
            val intent = Intent(itemView.context, SectorActivity::class.java)
            itemView.context.startActivity(intent)
        }
    }
}