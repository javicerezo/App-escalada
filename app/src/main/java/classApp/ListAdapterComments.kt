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
import com.example.proyectoilerna.MainActivity
import com.example.proyectoilerna.MainActivity.Companion.globalUser
import com.example.proyectoilerna.R
import com.example.proyectoilerna.SectorActivity

class ListAdapterComments  (private val list: ArrayList<Comment>): RecyclerView.Adapter<ListAdapterComments.MyViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapterComments.MyViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.car_comment_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListAdapterComments.MyViewHolder, position: Int) {
        val comment: Comment = list[position]

        holder.tvCommentName.text = comment.nameUser.toString()
        holder.tvCommentDate.text = comment.date.toString()
        holder.tvCommentComment.text = comment.comment.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    public class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        var tvCommentName = itemView.findViewById<TextView>(R.id.tv_comment_name)
        var tvCommentDate = itemView.findViewById<TextView>(R.id.tv_comment_date)
        var tvCommentComment = itemView.findViewById<TextView>(R.id.tv_comment_comment)

    }
}