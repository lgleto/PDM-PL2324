package ipca.utility.shoppinglist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.utility.shoppinglist.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductDetailBinding
    var docId : String? = null
    var qtd : Int
        get() {
            return binding.textViewQtt.text.toString().toInt()
        }
        set(value) {
            if (value >= 0)
                binding.textViewQtt.text = value.toString()
        }

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            docId = it.getString(DATA_ID)
            db.collection("products")
                .document(docId!!)
                .get()
                .addOnSuccessListener {
                    val product = it.data?.let { it1 ->
                        Product.fromSnapshot(
                            it.id,
                            it1
                        )

                    }
                    binding.editTextProductName.setText(product?.name)
                    qtd = (product?.qtt?:0).toInt()
                }

        }


        binding.buttonIncrement.setOnClickListener {
            qtd += 1
        }
        binding.buttonDecrement.setOnClickListener {
            qtd -= 1
        }

        binding.buttonDone.setOnClickListener {
            if (docId == null) {
                val product = Product(
                    null,
                    binding.editTextProductName.text.toString(),
                    qtd.toLong(),
                    false
                )
                db.collection("products")
                    .add(product.toMap())
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                        Toast.makeText(
                            this,
                            "Erro ao criar documento", Toast.LENGTH_LONG
                        )
                            .show()
                    }
            }else{
                val product = Product(
                    docId,
                    binding.editTextProductName.text.toString(),
                    qtd.toLong(),
                    false
                )
                db.collection("products")
                    .document(docId!!)
                    .set(product.toMap())
                    .addOnSuccessListener { documentReference ->

                        finish()
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                        Toast.makeText(
                            this,
                            "Erro ao criar documento", Toast.LENGTH_LONG
                        )
                            .show()
                    }
            }

        }

    }

    companion object {
        const val DATA_ID= "data_ID"
    }
}