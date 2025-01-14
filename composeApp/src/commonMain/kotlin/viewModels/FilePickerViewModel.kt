package viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import di.downloadDirectoryPath
import io.github.vinceglb.filekit.core.FileKit
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType
import io.github.vinceglb.filekit.core.PlatformDirectory
import io.github.vinceglb.filekit.core.PlatformFile
import io.github.vinceglb.filekit.core.baseName
import io.github.vinceglb.filekit.core.extension
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FilePickerViewModel : ViewModel() {
  private val _uiState = MutableStateFlow(FilePickerUiState())
  val uiState = _uiState.asStateFlow()

  fun clearAllImages() {
    _uiState.update {
      it.copy(
        imageUris = emptyList(),
      )
    }
  }

  fun removeImage(imageUri: ByteArray) {
    _uiState.update {
      it.copy(
        imageUris =
          it.imageUris.toMutableList().apply {
            remove(imageUri)
          },
      )
    }
  }

  fun addImage(image: ByteArray) {
    _uiState.update {
      it.copy(
        imageUris = it.imageUris + image,
      )
    }
  }

  fun pickImage() =
    executeWithLoading {
      // Pick a file
      val file =
        FileKit.pickFile(
          type = PickerType.Image,
          title = "Custom title here",
          initialDirectory = downloadDirectoryPath(),
          mode = PickerMode.Single,
        )

      // Add file to the state
      if (file != null) {
        addImage(file.readBytes())
      }
    }

  fun pickImages() =
    executeWithLoading {
      // Pick files
      val files =
        FileKit.pickFile(
          type = PickerType.Image,
          mode = PickerMode.Multiple,
        )

      // Add files to the state
      if (files != null) {
        // Add files to the state
        val newFiles = _uiState.value.files + files
        _uiState.update { it.copy(files = newFiles) }
      }
    }

  fun pickFile() =
    executeWithLoading {
      // Pick a file
      val file =
        FileKit.pickFile(
          type = PickerType.File(extensions = listOf("png")),
          mode = PickerMode.Single,
        )

      // Add file to the state
      if (file != null) {
        val newFiles = _uiState.value.files + file
        _uiState.update { it.copy(files = newFiles) }
      }
    }

  fun pickFiles() =
    executeWithLoading {
      // Pick files
      val files =
        FileKit.pickFile(
          type = PickerType.File(extensions = listOf("png")),
          mode = PickerMode.Multiple,
        )

      // Add files to the state
      if (files != null) {
        val newFiles = _uiState.value.files + files
        _uiState.update { it.copy(files = newFiles) }
      }
    }

  fun pickDirectory() =
    executeWithLoading {
      // Pick a directory
      val directory = FileKit.pickDirectory()

      // Update the state
      if (directory != null) {
        _uiState.update { it.copy(directory = directory) }
      }
    }

  fun saveFile(file: PlatformFile) =
    executeWithLoading {
      // Save a file
      val newFile =
        FileKit.saveFile(
          bytes = file.readBytes(),
          baseName = file.baseName,
          extension = file.extension,
        )

      // Add file to the state
      if (newFile != null) {
        val newFiles = _uiState.value.files + newFile
        _uiState.update { it.copy(files = newFiles) }
      }
    }

  private fun executeWithLoading(block: suspend () -> Unit) {
    viewModelScope.launch {
      _uiState.update { it.copy(loading = true) }
      block()
      _uiState.update { it.copy(loading = false) }
    }
  }
}

data class FilePickerUiState(
  val files: Set<PlatformFile> = emptySet(), // Set instead of List to avoid duplicates
  val directory: PlatformDirectory? = null,
  val loading: Boolean = false,
  val imageUris: List<ByteArray> = emptyList(),
) {
  // Used by SwiftUI code
  constructor() : this(emptySet(), null, false, emptyList())
}
