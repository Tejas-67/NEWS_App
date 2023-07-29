package com.example.news.Database

import android.content.Context
import androidx.room.*
import com.example.news.DataModel.Article
import com.example.news.DataModel.NewsResponse
import com.example.news.DataModel.Source

@Database(entities = [Article::class, Source::class], version=1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase(){

    abstract fun getDao(): ArticleDao

    companion object{

        @Volatile
        private var INSTANCE: ArticleDatabase? =null

        fun getDatabase(context: Context): ArticleDatabase{
            val tempInstance= INSTANCE
            if(tempInstance!=null) return tempInstance

            val instance = Room.databaseBuilder(context.applicationContext, ArticleDatabase::class.java, "article.db")
                .allowMainThreadQueries().build()

            INSTANCE=instance
            return instance

        }
    }
}
//        private var instance: ArticleDatabase? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//            instance ?: createDatabase(context).also { instance = it }
//        }
//
//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                ArticleDatabase::class.java,
//                "article_db.db"
//            ).build()

