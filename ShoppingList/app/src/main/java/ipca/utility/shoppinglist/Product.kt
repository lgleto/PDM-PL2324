package ipca.utility.shoppinglist

data class Product (var name : String,
                    var qtt : Int,
                    var isChecked : Boolean = false)


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