package id.candraibra.catmovie2.utils

import android.content.SharedPreferences
import id.candraibra.catmovie2.base.MyApplication.Companion.getInstance

class PrefHelper {
    companion object{
        private lateinit var preferences: SharedPreferences

        private fun initPref() {
            preferences = getInstance.getSharedPreferences()
        }
        fun setString(key: PrefKey, value: String?) {
            initPref()
            val editor = preferences.edit()
            editor.putString(key.toString(), value)
            editor.apply()
        }

        fun getString(key: PrefKey): String? {
            initPref()
            return preferences.getString(key.toString(), "")
        }

        fun setInt(key: PrefKey, value: Int) {
            initPref()
            val editor = preferences.edit()
            editor.putInt(key.toString(), value)
            editor.apply()
        }

        fun getInt(key: PrefKey): Int {
            initPref()
            return preferences.getInt(key.toString(), 0)
        }

        fun setBoolean(key: PrefKey, value: Boolean) {
            initPref()
            val editor = preferences.edit()
            editor.putBoolean(key.toString(), value)
            editor.apply()
        }

        fun getBoolean(key: PrefKey): Boolean {
            initPref()
            return preferences.getBoolean(key.toString(), false)
        }

        fun clearPreference(key: PrefKey) {
            initPref()
            val editor = preferences.edit()
            editor.remove(key.toString())
            editor.apply()
        }

        fun clearAllPreferences() {
            initPref()
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
        }
    }

}