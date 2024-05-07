package portfolio.jjs.presentation.viewModel.my_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import portfolio.jjs.domain.model.AccountInfo
import portfolio.jjs.domain.usecase.AccountUseCase
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val accountUseCase: AccountUseCase
): ViewModel() {
    val accountInfo = accountUseCase.getAccountInfo()

    fun signIn(accountInfo: AccountInfo) {
        viewModelScope.launch {
            accountUseCase.signIn(accountInfo)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            accountUseCase.signOut()
        }
    }
}