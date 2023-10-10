package ipca.utility.calculator

class CalculatorBrain {

    enum class Operation( op:String) {
        SUM("+"),
        SUB("-"),
        MUL("*"),
        DIV("/")
    }

    var operation : Operation? = null
    var accumulator : Double = 0.0
    fun doOperation(current : Double) : Double {

        var result = when (operation){
            Operation.SUM -> accumulator + current
            Operation.DIV -> accumulator / current
            Operation.SUB -> accumulator - current
            Operation.MUL -> accumulator * current
            else -> 0.0
        }
        return result
    }
}