package ipca.utility.shoppinglist.models

data class ShoppingList (
    var id : String?,
    var name : String ){

    fun toMap(): HashMap<String, Any?> {
        return hashMapOf(
            "id" to id,
            "name" to name,
        )
    }

    companion object{
        fun fromSnapshot(docId :String, snap : Map<String,Any?>) : ShoppingList {
            val name  = snap["name"] as String
            return  ShoppingList(
                docId,
                name,
            )
        }
    }
}

