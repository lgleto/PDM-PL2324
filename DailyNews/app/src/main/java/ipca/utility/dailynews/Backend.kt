package ipca.utility.dailynews

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

object Backend {

    private const val BASE_API = "https://newsapi.org/v2/"
    private const val PATH_TOP_HEADLINES = "top-headlines"
    private const val API_KEY = "&apiKey=1765f87e4ebc40229e80fd0f75b6416c"

    private val client = OkHttpClient()

    fun fetchArticles(lifecycleScope: LifecycleCoroutineScope, category: String, callback:( ArrayList<Article> )->Unit ) {
        lifecycleScope.launch(Dispatchers.IO) {

            val request = Request.Builder()
                .url("${BASE_API}${PATH_TOP_HEADLINES}?country=us&category=${category}${API_KEY}")
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

                    lifecycleScope.launch(Dispatchers.Main) {
                        callback(articles)
                    }
                }
            }
        }
    }

    fun fetchImage(lifecycleScope: LifecycleCoroutineScope, url: String, callback: (Bitmap) -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) {
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).execute().use { response ->
                response.body?.let {
                    val input = it.byteStream()
                    val bitmap = BitmapFactory.decodeStream(input)
                    lifecycleScope.launch(Dispatchers.Main) {
                        callback(bitmap)
                    }
                }
            }
        }
    }

}