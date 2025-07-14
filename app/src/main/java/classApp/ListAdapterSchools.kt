package classApp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoilerna.MainActivity.Companion.globalSchool
import com.example.proyectoilerna.R
import com.example.proyectoilerna.SchoolActivity

class ListAdapterSchools (private val list: ArrayList<School>): RecyclerView.Adapter<ListAdapterSchools.MyViewHolder>() {
    private lateinit var context: Context

    // Creamos la vista donde le decimos el xml que tiene que coger
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapterSchools.MyViewHolder {
        // el contexto que recibe esta función será el de su contenedor padre (dado por parámetro), por lo que podremos reutilizar esta función en varias activities
        context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.car_school_list, parent, false)

        return MyViewHolder(itemView)
    }

    // la conexión de cada elemento del array con la vista
    override fun onBindViewHolder(holder: ListAdapterSchools.MyViewHolder, position: Int) {
        val school: School = list[position]

        holder.ivSchoolImage.setImageResource(R.drawable.ic_climber2)
        holder.tvSchoolName.text = school.name.toString()
        holder.tvSchoolInformation.text = "${school.numbersSectors.toString()} sectores, ${school.numbersRoutes.toString()} vías de escalada"

        holder.llSchool.setOnClickListener {
            holder.callSchoolActivity(school)
            globalSchool = school
        }

    }

    // los elementos totales que tiene el adapter
    override fun getItemCount(): Int {
        return list.size
    }

    // con esta clase interna manerajemos cada item que se muestra dentro del listado
    public class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        var ivSchoolImage = itemView.findViewById<ImageView>(R.id.iv_school_image)
        var tvSchoolName = itemView.findViewById<TextView>(R.id.tv_school_name)
        var tvSchoolInformation = itemView.findViewById<TextView>(R.id.tv_school_information)

        val llSchool = itemView.findViewById<LinearLayout>(R.id.ll_school)

        fun callSchoolActivity(school: School) {
            val intent = Intent(itemView.context, SchoolActivity::class.java)
            itemView.context.startActivity(intent)
        }
    }

}