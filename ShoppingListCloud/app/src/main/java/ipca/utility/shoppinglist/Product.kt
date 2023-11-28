package ipca.utility.shoppinglist

data class Product (
    var id : String?,
    var name : String,
    var qtt : Long,
    var isChecked : Boolean = false){

    fun toMap(): HashMap<String, Any?> {
        return hashMapOf(
            "id" to id,
            "name" to name,
            "qtt" to qtt,
            "isChecked" to isChecked,
        )
    }

    companion object{
        fun fromSnapshot(docId :String, snap : Map<String,Any?>) : Product{
            val name  = snap["name"] as String
            val qtt  = snap["qtt"] as Long
            val isChecked  = snap["isChecked"] as Boolean
            return  Product(
                docId,
                name,
                qtt,
                isChecked
            )
        }
    }
}


/*
class Product {

    var name :var qtt : Int String
    var qtt : Int
    var isChecked : Boolean

    constructor(name: String, qtt: Int, isChecked: Boolean) {
        this.name = name
        this.qtt = qtt
        this.isChecked = isChecked
    }
}
*/