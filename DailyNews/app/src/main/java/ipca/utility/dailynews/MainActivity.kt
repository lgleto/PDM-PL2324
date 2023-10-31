package ipca.utility.dailynews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.util.Date

class MainActivity : AppCompatActivity() {

    var articles = arrayListOf<Article>(
        Article("aaaaaaaa", "sdkjhsdkfksdjhf","","", Date()),
        Article("bbbbbbbb", "sdfsdfsdfsdfsdf","","", Date()),
    )

    val articlesAdapter = ArticlesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listViewArticles = findViewById<ListView>(R.id.listViewArticles)
        listViewArticles.adapter = articlesAdapter

        GlobalScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=1765f87e4ebc40229e80fd0f75b6416c")
                .build()

            client.newCall(request).execute().use { response ->
                val result = response.body!!.string()

                val jsonResult = JSONObject(result)
                val status = jsonResult["status"] as String
                if (status == "ok"){
                    val jsonArray = jsonResult["articles"] as JSONArray
                    val articles = arrayListOf<Article>()
                    for (index in 0..jsonArray.length()-1){
                        val jsonArticle = jsonArray.getJSONObject(index)

                        val article = Article.fromJson(jsonArticle)
                        articles.add(article)

                    }
                    this@MainActivity.articles = articles
                    GlobalScope.launch(Dispatchers.Main) {
                        articlesAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

    }

    inner class ArticlesAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return articles.size
        }

        override fun getItem(position: Int): Any {
            return articles[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_article,parent, false)
            val textViewTitle = rootView.findViewById<TextView>(R.id.textViewTitle)
            val textViewDescription = rootView.findViewById<TextView>(R.id.textViewDescription)
            val textViewDate = rootView.findViewById<TextView>(R.id.textViewDate)
            val imageViewArticle = rootView.findViewById<ImageView>(R.id.imageViewArticle)

            textViewTitle.text = articles[position].title
            textViewDescription.text = articles[position].description
            textViewDate.text = articles[position].publishedAt.toString()

            rootView.setOnClickListener {

            }

            return rootView
        }
    }
}