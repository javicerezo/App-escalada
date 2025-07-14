package classApp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoilerna.R

class ListAdapterLearnMountain (private val list: ArrayList<LearnMountain>): RecyclerView.Adapter<ListAdapterLearnMountain.MyViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapterLearnMountain.MyViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.car_learn_mountain_list, parent, false)
        return ListAdapterLearnMountain.MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ListAdapterLearnMountain.MyViewHolder, position: Int) {
        val learnMountain: LearnMountain = list[position]

        when(learnMountain.image) {
            "alex_megos_tuareg" -> holder.ivLearnImage.setImageResource(R.drawable.learn_alex_megos_tuareg)
            "cuidado_de_cuerda" -> holder.ivLearnImage.setImageResource(R.drawable.learn_cuidado_de_cuerda)
            "gobierno_nepal" -> holder.ivLearnImage.setImageResource(R.drawable.learn_gobierno_nepal)
            "pies_de_gato" -> holder.ivLearnImage.setImageResource(R.drawable.learn_pies_de_gato)
            "seguridad_escalada_deportiva" -> holder.ivLearnImage.setImageResource(R.drawable.learn_seguridad_escalada_deportiva)
        }

        holder.tvLearnSubtitle.text = learnMountain.subtitle.toString()
        holder.tvLearnTitle.text = learnMountain.title.toString()
        holder.tvLearnDescription.text = learnMountain.description.toString()

        holder.tvLearnTitle.setOnClickListener {
            Toast.makeText(context, "activando URL ${learnMountain.url}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        var ivLearnImage = itemView.findViewById<ImageView>(R.id.iv_learn_image)
        var tvLearnSubtitle = itemView.findViewById<TextView>(R.id.tv_learn_subtitle)
        var tvLearnTitle = itemView.findViewById<TextView>(R.id.tv_learn_title)
        var tvLearnDescription = itemView.findViewById<TextView>(R.id.tv_learn_description)

    }

}