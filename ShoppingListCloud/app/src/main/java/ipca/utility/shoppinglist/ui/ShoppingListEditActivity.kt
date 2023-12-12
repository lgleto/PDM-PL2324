package ipca.utility.shoppinglist.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.utility.shoppinglist.databinding.ActivityListDetailBinding
import ipca.utility.shoppinglist.models.ShoppingList

class ShoppingListEditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListDetailBinding
    var docId : String? = null

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            docId = it.getString(DATA_ID)
            db.collection("shoppingList")
                .document(docId!!)
                .get()
                .addOnSuccessListener {
                    val list = it.data?.let { it1 ->
                        ShoppingList.fromSnapshot(
                            it.id,
                            it1
                        )

                    }
                    binding.editTextListName.setText(list?.name)
                }

        }


        binding.buttonDone.setOnClickListener {
            if (docId == null) {
                val list = ShoppingList(
                    null,
                    binding.editTextListName.text.toString(),
                )
                db.collection("shoppingList")
                    .add(list.toMap())
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
                val list = ShoppingList(
                    null,
                    binding.editTextListName.text.toString(),
                )
                db.collection("shoppingList")
                    .document(docId!!)
                    .set(list.toMap())
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