package portfolio.jjs.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import portfolio.jjs.domain.model.AccountInfo
import portfolio.jjs.domain.repository.AccountRepository
import javax.inject.Inject

class AccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    fun getAccountInfo(): StateFlow<AccountInfo?> {
        return accountRepository.getAccountInfo()
    }

    suspend fun signIn(accountInfo: AccountInfo) {
        accountRepository.signIn(accountInfo)
    }

    suspend fun signOut() {
        accountRepository.signOut()
    }
}