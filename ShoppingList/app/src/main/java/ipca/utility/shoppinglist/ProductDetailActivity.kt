package ipca.utility.shoppinglist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ipca.utility.shoppinglist.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonIncrement.setOnClickListener {
            binding.textViewQtt.text = 1.toString()
        }

    }
}