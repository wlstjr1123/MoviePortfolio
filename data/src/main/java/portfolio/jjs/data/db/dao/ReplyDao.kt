package portfolio.jjs.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import portfolio.jjs.data.db.entity.ReplyEntity

@Dao
interface ReplyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ReplyEntity)

    @Query("DELETE FROM reply WHERE id=:id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM reply WHERE movieId=:movieId")
    fun getMovieReplies(movieId: String): Flow<List<ReplyEntity>>
}