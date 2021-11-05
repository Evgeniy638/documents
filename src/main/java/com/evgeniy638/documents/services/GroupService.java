package com.evgeniy638.documents.services;

import com.evgeniy638.documents.dto.GroupDTO;
import com.evgeniy638.documents.modules.Group;
import com.evgeniy638.documents.modules.Institute;
import com.evgeniy638.documents.repository.GroupRepository;
import com.evgeniy638.documents.repository.InstituteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final InstituteRepository instituteRepository;

    public void createGroup(GroupDTO groupDTO) {
        Group group = new Group();
        Institute institute = instituteRepository.findByTitle(groupDTO.getTitleInstitute());

        group.setTitle(groupDTO.getTitleGroup());
        group.setInstitute(institute);

        groupRepository.save(group);
    }

    public List<Group> getGroups() {
        return groupRepository.findAll();
    }
}
