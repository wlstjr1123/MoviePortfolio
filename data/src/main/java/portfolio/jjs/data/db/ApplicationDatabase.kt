package portfolio.jjs.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import portfolio.jjs.data.db.dao.ReplyDao
import portfolio.jjs.data.db.entity.ReplyEntity

@Database(
    entities = [
        ReplyEntity::class
    ],
    version = 1
)
abstract class ApplicationDatabase: RoomDatabase() {
    companion object {
        const val DB_NAME = "moviePortfolioDatabase.db"
    }

    abstract fun replyDao(): ReplyDao
}