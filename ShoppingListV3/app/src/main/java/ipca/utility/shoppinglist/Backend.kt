package ipca.utility.shoppinglist

import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import io.swagger.client.apis.ProductApi
import io.swagger.client.infrastructure.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

object Backend {

    private const val BASE_API = "http://192.168.143.3:7105/"
    private const val PATH_PRODUCTS = "Product"

    private val client = OkHttpClient()


    fun fetchProducts2(lifecycleScope: LifecycleCoroutineScope, callback:( Array<io.swagger.client.models.Product> )->Unit ) {
        lifecycleScope.launch(Dispatchers.IO) {
            val productApi = ProductApi("http://192.168.143.3:7105/").getProducts()
                lifecycleScope.launch(Dispatchers.Main) {
                    callback(productApi)
                }
            }
    }


    fun fetchProducts(lifecycleScope: LifecycleCoroutineScope, callback:( ArrayList<Product> )->Unit ) {
        lifecycleScope.launch(Dispatchers.IO) {
            ApiClient("http://192.168.143.3:7105/")

            val request = Request.Builder()
                .url("${BASE_API}${PATH_PRODUCTS}")
                .build()

            client.newCall(request).execute().use { response ->
                val result = response.body!!.string()

                val jsonArray = JSONArray(result)
                val products = arrayListOf<Product>()
                for (index in 0..jsonArray.length()-1){
                    val jsonProduct = jsonArray.getJSONObject(index)

                    val product = Product.fromJson(jsonProduct)
                    products.add(product)

                }

                lifecycleScope.launch(Dispatchers.Main) {
                    callback(products)
                }
            }
        }
    }

    fun addProduct(lifecycleScope: LifecycleCoroutineScope, product: Product, function: () -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) {


            val payload = product.toJson().toString()
            val mediaTypeJson = "application/json; charset=utf-8".toMediaType()
            val okHttpClient = OkHttpClient()
            val requestBody = payload.toRequestBody(mediaTypeJson)
            val request = Request.Builder()
                .post(requestBody)
                .url("${BASE_API}${PATH_PRODUCTS}")
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle this
                    Log.d("shoppinglist", e.message.toString())
                    function.invoke()
                }

                override fun onResponse(call: Call, response: Response) {
                    // Handle this
                    Log.d("shoppinglist", response.toString())
                    function.invoke()
                }
            })
        }
    }


}