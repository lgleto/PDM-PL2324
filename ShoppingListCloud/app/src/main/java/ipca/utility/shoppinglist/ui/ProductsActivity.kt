package ipca.utility.shoppinglist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.messaging.FirebaseMessaging
import ipca.utility.shoppinglist.R
import ipca.utility.shoppinglist.models.Product


class ProductsActivity : AppCompatActivity() {

    val db = Firebase.firestore
    val products = arrayListOf<Product>()
    val productAdapter = ProductAdapter()
    var listId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val listViewProducts = findViewById<ListView>(R.id.listViewProducts)
        listViewProducts.adapter = productAdapter

        val buttonAdd = findViewById<Button>(R.id.buttonAddProduct)
        buttonAdd.setOnClickListener {
            val intent = Intent(this, ProductDetailActivity::class.java )
            intent.putExtra(ProductDetailActivity.DATA_LIST_ID, listId)
            startActivity(intent)
        }

        intent.extras?.let {
            listId = it.getString(ShoppingListEditActivity.DATA_ID)
            db.collection("shoppingList")
                .document(listId!!)
                .collection("products")
                .addSnapshotListener { snapshots, e ->
                    if (e != null) {
                        Log.w(TAG, "listen:error", e)
                        return@addSnapshotListener
                    }
                    products.clear()

                    for (snapshot in snapshots?.documents!!) {

                        snapshot.data?.let {
                            val product = Product.fromSnapshot(
                                snapshot.id,
                                it
                            )
                            products.add(product)
                        }


                    }
                    productAdapter.notifyDataSetChanged()
                }

        }




        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = token
            Log.d(TAG, msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            val auth = Firebase.auth
            db.collection("users")
                .document(auth.currentUser!!.uid)
                .set(hashMapOf("fcm_token" to token ) )
                .addOnSuccessListener { documentReference ->

                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    Toast.makeText(
                        this,
                        "Erro ao criar documento", Toast.LENGTH_LONG
                    )
                        .show()
                }
        })
    }



    inner class ProductAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return products.size
        }

        override fun getItem(position: Int): Any {
            return products[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_product,parent, false)
            val textViewProductName = rootView.findViewById<TextView>(R.id.textViewProductName)
            val textViewProductQtt = rootView.findViewById<TextView>(R.id.textViewProductQtt)
            val checkBox = rootView.findViewById<CheckBox>(R.id.checkBox)

            textViewProductName.text = products[position].name
            textViewProductQtt.text = products[position].qtt.toString()
            checkBox.isChecked = products[position].isChecked

            checkBox.setOnClickListener {
                products[position].isChecked = checkBox.isChecked
                db.collection("shoppingList")
                    .document(listId!!)
                    .collection("products")
                    .document(products[position].id!!)
                    .set(products[position].toMap())
            }

            rootView.setOnClickListener {
                val intent = Intent(this@ProductsActivity, ProductDetailActivity::class.java )
                intent.putExtra(ProductDetailActivity.DATA_ID, products[position].id)
                intent.putExtra(ProductDetailActivity.DATA_LIST_ID, listId)
                startActivity(intent)
            }

            return rootView
        }

    }


}