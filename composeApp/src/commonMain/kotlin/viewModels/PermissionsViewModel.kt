package viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import components.global.alert.AlertViewModel
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.launch

class PermissionsViewModel(
  private val permissionsController: PermissionsController,
  private val alertViewModel: AlertViewModel,
) : ViewModel() {
  fun requestPermission(
    permissionType: Permission,
    onGranted: () -> Unit?,
    errorMsg: String = "",
    errorTitle: String = "",
  ) {
    viewModelScope.launch {
      try {
        permissionsController.providePermission(permissionType)
        onGranted()
      } catch (deniedAlways: DeniedAlwaysException) {
        alertViewModel.onOpenAlert(
          errorTitle,
          errorMsg,
          confirmLabel = "Open setting",
          onConfirm = { permissionsController.openAppSettings() },
        )
      } catch (denied: DeniedException) {
        alertViewModel.onOpenAlert(
          errorTitle,
          errorMsg,
          confirmLabel = "Open setting",
          onConfirm = { permissionsController.openAppSettings() },
        )
      }
    }
  }

  fun getPermissionsController(): PermissionsController = permissionsController
}
