package ipca.utility.dailynews

import android.icu.text.SimpleDateFormat
import java.util.Date

fun String.toDate() : Date {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    return format.parse(this)
}

fun Date.toDDMMMHHMMDate() : String{
    val format = SimpleDateFormat("dd MMMM HH:mm ")
    return format.format(this)
}