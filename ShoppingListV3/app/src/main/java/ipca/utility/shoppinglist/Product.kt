package ipca.utility.shoppinglist

import org.json.JSONObject

data class Product (
    var id          : String,
    var name        : String,
    var qtt         : Int,
    var isChecked   : Boolean = false) {


    fun toJson() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("id"       , id        )
        jsonObject.put("name"     , name      )
        jsonObject.put("qtt"      , qtt       )
        jsonObject.put("isChecked", isChecked )

        return jsonObject
    }

    companion object{
        fun fromJson(jsonObject: JSONObject) : Product {
            return Product(
                jsonObject["id"         ] as String,
                jsonObject["name"       ] as String,
                jsonObject["qtt"        ] as Int,
                jsonObject["isChecked"  ] as Boolean,
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