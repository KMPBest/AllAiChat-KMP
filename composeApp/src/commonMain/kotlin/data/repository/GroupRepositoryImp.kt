package data.repository

import app.cash.sqldelight.async.coroutines.awaitAsList
import com.ngdang.outcome.GroupChat
import data.database.SharedDatabase
import domain.model.Group
import domain.repository.GroupRepository

class GroupRepositoryImp(
  private val sharedDatabase: SharedDatabase,
) : GroupRepository {
  override suspend fun getAllGroup(): List<Group> {
    val groupList = arrayListOf<Group>()
    sharedDatabase { appDatabase ->
      groupList.addAll(
        appDatabase.appDatabaseQueries.getAllGroup().awaitAsList().map {
          Group(
            it.groupId,
            it.title,
            it.image,
            it.date,
          )
        },
      )
    }
    return groupList
  }

  override suspend fun insertGroup(
    groupId: String,
    groupName: String,
    create: String,
    icon: String,
  ) {
    sharedDatabase { appDatabase ->
      appDatabase.appDatabaseQueries.insertGroup(
        GroupChat(groupId, groupName, create, icon),
      )
    }
  }
}
