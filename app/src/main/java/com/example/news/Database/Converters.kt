package com.example.news.Database

import androidx.room.TypeConverter
import com.example.news.DataModel.Source
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromSource(source: Source?): String?{
        return source?.let{ Gson().toJson(it)}
    }

    @TypeConverter
    fun toSource(name: String): Source?{
        return name?.let{Gson().fromJson(it, Source::class.java)}
    }


}