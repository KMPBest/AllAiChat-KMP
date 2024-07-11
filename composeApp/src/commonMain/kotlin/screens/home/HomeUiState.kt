package screens.home

import domain.model.Group

data class HomeUiState(
  val group: List<Group> = emptyList(),
)
