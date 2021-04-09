package com.federicopuy.examenfavlapp.core.updateDBManager

import android.content.Context
import com.federicopuy.examenfavlapp.core.FIRST_TIME
import com.federicopuy.examenfavlapp.core.SHARED_PREFS
import javax.inject.Inject

class DefaultUpdateDBManager @Inject constructor() : UpdateDBManager {

    /**
     * Default implementation for the DB Manager. If it is the first time that the user opens the
     * app,the DB should be populated for the first time, so the method returns true. This behaviour
     * is also triggered when reinstalling the app.
     */
    override fun shouldUpdateDB(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFS, 0)

        if (sharedPreferences.getBoolean(FIRST_TIME, true)) {
            sharedPreferences.edit().putBoolean(FIRST_TIME, false).apply()
            return true
        }
        return false
    }
}