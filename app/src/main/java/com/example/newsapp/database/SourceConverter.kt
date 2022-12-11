package com.example.newsapp.database

import androidx.room.TypeConverter
import com.example.newsapp.model.Source

/***
 * Class to convert an article source (created by a newsAPI call) to a String, and vice-versa.
 */
class SourceConverter {
    @TypeConverter fun convertSourceToString(s : Source): String {
        return s.name
    }

    @TypeConverter fun convertStringToSource(s: String): Source {
        return Source(name = s)
    }

}

