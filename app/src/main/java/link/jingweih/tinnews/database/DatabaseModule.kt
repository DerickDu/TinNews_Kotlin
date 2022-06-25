package link.jingweih.tinnews.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): TinNewsDatabase {
        return Room.databaseBuilder(
            appContext,
            TinNewsDatabase::class.java,
            "tinnews_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(database: TinNewsDatabase): ArticleDao {
        return database.articleDao()
    }
}