package com.example.doubletapp_hw2

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showSystemMessage(text: String, longDuration: Boolean = false) {
    activity?.showSystemMessage(text, longDuration)
}
fun Activity.showSystemMessage(text: String, longDuration: Boolean = false) =
    Toast.makeText(this, text, if (longDuration) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
        .show()

inline fun <reified T: Class<*>> T.getId(resourceName: String): Int {
    return try {
        val idField = getDeclaredField (resourceName)
        idField.getInt(idField)
    } catch (e:Exception) {
        e.printStackTrace()
        -1
    }
}