package ipca.utility.shoppinglist.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.Manifest
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.messaging.FirebaseMessaging
import ipca.utility.shoppinglist.R
import ipca.utility.shoppinglist.models.ShoppingList

val TAG = "shoppinglist"

class MainActivity : AppCompatActivity() {

    val db = Firebase.firestore
    val shoppingLists = arrayListOf<ShoppingList>()
    val shoppingListAdapter = ShoppingListAdapter()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                Firebase.auth.signOut()
                val intent = Intent(this@MainActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else-> {
                false
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listViewProducts = findViewById<ListView>(R.id.listViewProducts)
        listViewProducts.adapter = shoppingListAdapter

        val buttonAdd = findViewById<Button>(R.id.buttonAddProduct)
        buttonAdd.setOnClickListener {
            val intent = Intent(this, ShoppingListEditActivity::class.java )
            startActivity(intent)
        }



        db.collection("shoppingList")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    return@addSnapshotListener
                }
                shoppingLists.clear()

                for (snapshot in snapshots?.documents!!) {

                    snapshot.data?.let {
                        val sList = ShoppingList.fromSnapshot(
                            snapshot.id,
                            it
                        )
                        shoppingLists.add(sList)
                    }


                }
                shoppingListAdapter.notifyDataSetChanged()
            }
        askNotificationPermission()

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



    inner class ShoppingListAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return shoppingLists.size
        }

        override fun getItem(position: Int): Any {
            return shoppingLists[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_list,parent, false)
            val textViewListName = rootView.findViewById<TextView>(R.id.textViewListName)


            textViewListName.text = shoppingLists[position].name

            rootView.setOnClickListener {
                val intent = Intent(this@MainActivity, ProductsActivity::class.java )
                intent.putExtra(ProductDetailActivity.DATA_ID, shoppingLists[position].id)
                startActivity(intent)
            }

            return rootView
        }

    }

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}