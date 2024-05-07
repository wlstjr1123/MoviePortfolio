package portfolio.jjs.domain.model

data class AccountInfo(val tokenId: String, val name: String, val type: Type, val profileImageUrl: String, val userId: String) {
    enum class Type {
        GOOGLE,
        KAKAO
    }
}
