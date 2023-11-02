package ipca.utility.dailynews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class ArticleDetailActivity : AppCompatActivity() {

    var url : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        intent.extras?.let {
            url = it.getString(EXTRA_URL)?:""
        }

        val webView = findViewById<WebView>(R.id.webView)
        webView.loadUrl(url)

        val webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                super.shouldOverrideUrlLoading(view, url)
                webView.loadUrl(url!!)
                return true
            }
        }

        webView.webViewClient = webViewClient
    }

    companion object {
        const val EXTRA_URL = "url"
    }
}