package com.example.proyectoilerna

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classApp.Comment
import classApp.ListAdapterComments
import com.example.proyectoilerna.MainActivity.Companion.globalRoute
import com.example.proyectoilerna.MainActivity.Companion.globalSchool
import com.example.proyectoilerna.MainActivity.Companion.globalSector
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class RouteActivity : AppCompatActivity() {
    private lateinit var rvListComment: RecyclerView
    private lateinit var commentArrayList: ArrayList<Comment>
    private lateinit var myAdapterComment: ListAdapterComments

    private lateinit var tvNoComment: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_route)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initObjects()
        initCommentList()
    }
    override fun onResume() {
        super.onResume()
        loadCommentList()
    }
    override fun onPause() {
        super.onPause()
        commentArrayList.clear()
    }

    private fun initObjects() {
        val header = findViewById<TextView>(R.id.tv_header)
        header.text = globalRoute.name.toString()

        val name = findViewById<TextView>(R.id.tv_name)
        name.text = globalRoute.name.toString()

        val info = findViewById<TextView>(R.id.tv_info)
        info.text = "${globalSchool.name}, ${globalSector.name}"

        val numComments = findViewById<TextView>(R.id.tv_number_comments)
        numComments.text = "${globalRoute.numbersComments} comentarios."

        val metters = findViewById<TextView>(R.id.tv_metters)
        metters.text = "${globalRoute.metters.toString()} metros"

        tvNoComment = findViewById(R.id.tv_first_comment)
        tvNoComment.visibility = View.GONE
    }
    private fun initCommentList() {
        rvListComment = findViewById(R.id.rv_list_comment)
        rvListComment.layoutManager = LinearLayoutManager(this)
        rvListComment.setHasFixedSize(true)

        commentArrayList = arrayListOf()

        myAdapterComment = ListAdapterComments(commentArrayList)
        rvListComment.adapter = myAdapterComment
    }
    private fun loadCommentList() {
        commentArrayList.clear()

        val dbQuery = FirebaseFirestore.getInstance()
        dbQuery.collection("comments")
            .whereEqualTo("idNameRoute", globalRoute.name.toString())
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { collection ->
                if(collection.size() != 0){
                    for (doc in collection) {
                        commentArrayList.add(doc.toObject(Comment::class.java))
                    }
                    myAdapterComment.notifyDataSetChanged()
                } else {
                    tvNoComment.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting document: ", exception)
            }
    }

    /**
     * regresa a la activity_sector
     */
    fun callSectorActivity(view: View) {
        sectorActivity()
    }
    private fun sectorActivity() {
        globalRoute.difficulty = ""
        globalRoute.idSector = ""
        globalRoute.name = ""
        globalRoute.numberOfRoute = 0
        globalRoute.numbersComments = 0

        val intent = Intent(this, SectorActivity::class.java)
        startActivity(intent)
    }

    /**
     * ir a Activity_comment
     */
    fun callMakeCommentActivity(view: View) {
        makeCommentActivity()
    }
    private fun makeCommentActivity() {
        val intent = Intent(this, CommentActivity::class.java)
        startActivity(intent)
    }
}