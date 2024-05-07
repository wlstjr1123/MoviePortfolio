package portfolio.jjs.movie.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import portfolio.jjs.data.db.ApplicationDatabase
import portfolio.jjs.data.db.dao.ReplyDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ApplicationDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ApplicationDatabase::class.java,
            ApplicationDatabase.DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideReplyDao(database: ApplicationDatabase): ReplyDao {
        return database.replyDao()
    }
}