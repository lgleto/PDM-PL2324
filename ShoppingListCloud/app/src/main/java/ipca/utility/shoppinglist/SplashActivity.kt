package ipca.utility.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ipca.utility.shoppinglist.ui.LoginActivity
import ipca.utility.shoppinglist.ui.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        auth = Firebase.auth

        GlobalScope.launch(Dispatchers.IO) {
            sleep(1000)
            GlobalScope.launch(Dispatchers.Main) {
                if (auth.currentUser != null){
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                }

            }
        }






    }
}