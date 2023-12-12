package ipca.utility.shoppinglist

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.getString

class Preferences {

    var sharedPref : SharedPreferences
    constructor(activity : Activity) {
        sharedPref = activity.getSharedPreferences(
            "ipca.utility.shoppinglist.PREFERENCE_FILE_KEY",
            Context.MODE_PRIVATE
        )
    }

    fun saveEmail(email :String) {
        val edit = sharedPref.edit()
        edit.putString("email", email)
        edit.apply()

    }

    fun readEmail():String{
        return sharedPref.getString("email", "")?:""
    }
}