package link.jingweih.tinnews.database

import androidx.room.Database
import androidx.room.RoomDatabase
import link.jingweih.tinnews.model.Article

@Database(entities = [Article::class], version = 1)
abstract class TinNewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}