package com.federicopuy.examenfavlapp.core.updateDBManager

import android.content.Context

interface UpdateDBManager {

    /**
     * Determines if the values in the DB are already up to date, or if they should be updated from
     * an external source.
     * @param context The application context
     * @return whether the DB should be updated or not.
     */
    fun shouldUpdateDB(context: Context) : Boolean
}