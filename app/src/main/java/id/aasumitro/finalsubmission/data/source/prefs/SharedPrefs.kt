package id.aasumitro.finalsubmission.data.source.prefs

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object SharedPrefs {

    const val KEY_FIRST_INFO = "KEY_FIRST_INFO"
    const val KEY_FIRST_OPEN = "KEY_FIRST_OPEN"

    const val STATUS_EVER = "STATUS_EVER"
    const val STATUS_NEVER = "STATUS_NEVER"

    const val KEY_LEAGUE_NAME = "KEY_LEAGUE_NAME"
    const val KEY_LEAGUE_ALT_NAME = "KEY_LEAGUE_ALT_NAME"
    const val KEY_LEAGUE_ID = "KEY_LEAGUE_ID"

    fun defaultPrefs(context: Context): SharedPreferences
            = PreferenceManager.getDefaultSharedPreferences(context)

    private inline fun SharedPreferences
            .edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

}