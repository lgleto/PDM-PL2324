package ipca.utility.shoppinglist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData

import io.swagger.client.apis.ProductApi
import io.swagger.client.models.Product
import kotlinx.coroutines.Dispatchers.IO
import java.io.IOException


sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val code: Int? = null, val error: String? = null) :
        ResultWrapper<Nothing>()

    object NetworkError : ResultWrapper<Nothing>()

    inline fun onSuccess(action: (value: T) -> Unit): ResultWrapper<T> {
        if (this is Success) action(value)
        return this
    }

    inline fun onError(action: (error: Error) -> Unit): ResultWrapper<T> {
        if (this is Error) action(this)
        return this
    }

    inline fun onNetworkError(action: () -> Unit): ResultWrapper<T> {
        if (this is NetworkError) action()
        return this
    }
}

object Backend {

    private const val BASE_API = "http://172.20.10.8:7105/"

    suspend fun <T> wrap(apiCall: suspend () -> T): ResultWrapper<T> {
        return try {
            ResultWrapper.Success(apiCall())
        } catch (throwable: Throwable) {
            Log.e("Repository", throwable.toString())
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                else -> {
                    ResultWrapper.Error(0, throwable.message)
                }
            }
        }
    }

    fun fetchProducts(): LiveData<ResultWrapper<Array<Product>>> =
        liveData(IO) {
           emit( wrap { ProductApi(BASE_API).getProducts() })
        }

    fun addProduct(product : Product) : LiveData<ResultWrapper<String>> =
        liveData(IO) {
            emit( wrap { ProductApi(BASE_API).productPost(product)})
        }

    fun fetchProduct(id:String): LiveData<ResultWrapper<Product>> =
        liveData(IO) {
            emit( wrap { ProductApi(BASE_API).productIdGet(id) })
        }

    fun deleteProduct(id:String): LiveData<ResultWrapper<String>> =
        liveData(IO) {
            emit( wrap { ProductApi(BASE_API).productIdDelete(id) })
        }




}