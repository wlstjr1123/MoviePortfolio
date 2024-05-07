package portfolio.jjs.presentation.arg_model

import portfolio.jjs.domain.model.AccountInfo

data class MovieDetailAddReplyArg(
    val movieId: String,
    val accountInfo: AccountInfo,
)