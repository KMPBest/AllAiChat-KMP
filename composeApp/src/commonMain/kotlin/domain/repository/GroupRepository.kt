package domain.repository

import domain.model.Group

interface GroupRepository {
  suspend fun getAllGroup(): List<Group>

  suspend fun insertGroup(
    groupId: String,
    groupName: String,
    create: String,
    icon: String,
  )
}
