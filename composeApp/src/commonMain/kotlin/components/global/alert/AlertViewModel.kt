package components.global.alert

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AlertViewModel {
  var title by mutableStateOf("")
  var message by mutableStateOf("")
  var confirmLabel by mutableStateOf("")
  var cancelLabel by mutableStateOf("")
  var visible by mutableStateOf(false)

  var onConfirm: (() -> Unit)? = null

  fun onOpenAlert(
    title: String,
    message: String,
    confirmLabel: String = "OK",
    cancelLabel: String = "Cancel",
    onConfirm: (() -> Unit)? = null,
  ) {
    this.visible = true
    this.title = title
    this.message = message
    this.confirmLabel = confirmLabel
    this.cancelLabel = cancelLabel
    this.onConfirm = onConfirm
  }

  fun onHideAlert() {
    this.visible = false
    this.title = ""
    this.message = ""
    this.confirmLabel = ""
    this.cancelLabel = ""
  }

  fun onConfirm()  {
    onHideAlert()
    onConfirm?.invoke()
  }
}
