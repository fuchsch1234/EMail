package de.fuchsch.email.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {

    private val gson = Gson()

    @TypeConverter
    fun stringToStringList(data: String?): List<String> =
        gson.fromJson(data, object:TypeToken<List<String>>(){}.type)

    @TypeConverter
    fun stringListToString(data: List<String>): String = gson.toJson(data)

}