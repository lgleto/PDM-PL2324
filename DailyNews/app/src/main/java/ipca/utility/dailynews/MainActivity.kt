package ipca.utility.dailynews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {

    var articles = arrayListOf<Article>( )

    val articlesAdapter = ArticlesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listViewArticles = findViewById<ListView>(R.id.listViewArticles)
        listViewArticles.adapter = articlesAdapter


      Backend.fetchArticles(lifecycleScope, "health") {
          this@MainActivity.articles = it
          articlesAdapter.notifyDataSetChanged()
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
            textViewDate.text = articles[position].publishedAt?.toDDMMMHHMMDate()

            val urlToImage = articles[position].urlToImage

            urlToImage?.let { url->
                Backend.fetchImage(lifecycleScope, url) { bitmap ->
                    imageViewArticle.setImageBitmap(bitmap)
                }
            }

            rootView.setOnClickListener {
                val intent = Intent(this@MainActivity,ArticleDetailActivity::class.java )
                intent.putExtra(ArticleDetailActivity.EXTRA_URL, articles[position].url)
                startActivity(intent)
            }

            return rootView
        }
    }
}