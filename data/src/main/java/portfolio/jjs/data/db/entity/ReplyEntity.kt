package portfolio.jjs.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import portfolio.jjs.data.db.converter.ReplyConverter
import portfolio.jjs.domain.model.Reply
import java.time.ZonedDateTime

@Entity(tableName = "reply")
@TypeConverters(ReplyConverter::class)
data class ReplyEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    val movieId: String,
    val rating: Float,
    val title: String,
    val content: String,
    val date: ZonedDateTime,
    val userName: String,
    val userId: String
)

fun ReplyEntity.toDomainModel(): Reply {
    return Reply(
        replyId = id,
        movieId = movieId,
        rating = rating,
        title = title,
        content = content,
        date = date,
        userName = userName,
        userId = userId
    )
}

fun Reply.toReplyEntiy(): ReplyEntity {
    return ReplyEntity(
        id = replyId,
        movieId = movieId,
        rating = rating,
        title = title,
        content = content,
        date = date,
        userName = userName,
        userId = userId
    )
}