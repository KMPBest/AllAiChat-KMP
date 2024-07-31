package domain.useCases

import domain.model.Group
import domain.repository.GroupRepository
import org.koin.core.component.KoinComponent

class GroupUseCases(
  private val groupRepository: GroupRepository,
) : KoinComponent {
  suspend fun getAllGroup(): List<Group> = groupRepository.getAllGroup()

  suspend fun insertGroup(
    groupId: String,
    groupName: String,
    create: String,
    icon: String,
  ) {
    groupRepository.insertGroup(
      groupId,
      groupName,
      create,
      icon,
    )
  }

  suspend fun getGroupDetail(groupId: String): Group = groupRepository.getDetailGroup(groupId)
}
