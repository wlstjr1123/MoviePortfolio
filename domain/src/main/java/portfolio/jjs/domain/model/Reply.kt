package portfolio.jjs.domain.model

import java.time.ZonedDateTime

data class Reply(
    val replyId: Int?,
    val movieId: String,
    val rating: Float,
    val title: String,
    val content: String,
    val date: ZonedDateTime,
    val userName: String,
    val userId: String
)