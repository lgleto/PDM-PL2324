package ipca.utility.shoppinglist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ipca.utility.shoppinglist.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductDetailBinding
    var position = -1
    var qtd : Int
        get() {
            return binding.textViewQtt.text.toString().toInt()
        }
        set(value) {
            if (value >= 0)
                binding.textViewQtt.text = value.toString()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            position = it.getInt(DATA_POSITION, -1)
            qtd = it.getInt(DATA_QTT)
            val name = it.getString(DATA_PRODUCT_NAME)
            binding.editTextProductName.setText(name)
        }


        binding.buttonIncrement.setOnClickListener {
            qtd += 1
        }
        binding.buttonDecrement.setOnClickListener {
            qtd -= 1
        }

        binding.buttonDone.setOnClickListener {
            val intent = Intent()
            intent.putExtra(DATA_POSITION,position)
            intent.putExtra(DATA_QTT,qtd)
            intent.putExtra(
                DATA_PRODUCT_NAME,
                binding.editTextProductName.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    companion object {
        const val DATA_QTT = "data_qtt"
        const val DATA_PRODUCT_NAME = "data_product_name"
        const val DATA_POSITION = "data_position"
    }
}