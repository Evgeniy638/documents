package com.evgeniy638.documents.services;

import com.evgeniy638.documents.dto.FileDTO;
import com.evgeniy638.documents.dto.SentFileDTO;
import com.evgeniy638.documents.modules.FileMod;
import com.evgeniy638.documents.modules.Group;
import com.evgeniy638.documents.modules.Institute;
import com.evgeniy638.documents.modules.User;
import com.evgeniy638.documents.repository.FileModRepository;
import com.evgeniy638.documents.repository.GroupRepository;
import com.evgeniy638.documents.repository.InstituteRepository;
import com.evgeniy638.documents.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileModRepository fileModRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final InstituteRepository instituteRepository;

    @Value("${app.path.upload.file}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    public void save(FileDTO fileDTO, String creatorUsername) {
        if (fileDTO.getInstitutionTitles() == null) {
            fileDTO.setInstitutionTitles(new ArrayList<>());
        }

        if (fileDTO.getGroupTitles() == null) {
            fileDTO.setGroupTitles(new ArrayList<>());
        }

        if (fileDTO.getUsernames() == null) {
            fileDTO.setUsernames(new ArrayList<>());
        }

        FileMod fileMod = new FileMod();

        List<String> usernames = fileDTO.getUsernames();
        usernames.add(creatorUsername);

        fileMod.setName(fileDTO.getFile().getOriginalFilename());
        fileMod.setUsers(findUsersByUsernames(usernames));
        fileMod.setGroups(findGroupsByTitles(fileDTO.getGroupTitles()));
        fileMod.setInstitutions(findInstitutesByTitles(fileDTO.getInstitutionTitles()));

        FileMod savedFileMod = fileModRepository.save(fileMod);

        saveFile(
                fileDTO.getFile(),
                savedFileMod.getId().toString() + getPostfix(fileMod.getName())
        );
    }

    public List<SentFileDTO> getSentFiles(String username) {
        List<SentFileDTO> sentFileDTOS = new ArrayList<>();
        Set<FileMod> sentFiles = userRepository.findByUsername(username).getFile();

        for (FileMod file: sentFiles) {
            SentFileDTO sentFileDTO = new SentFileDTO();

            sentFileDTO.setName(file.getName());
            sentFileDTO.setUrl("/upload-files/" + file.getId().toString() + getPostfix(file.getName()));
            sentFileDTO.setListUsers(getStringListUsers(file.getUsers()));
            sentFileDTO.setListInstitute(getStringListInstitutes(file.getInstitutions()));
            sentFileDTO.setListGroups(getStringListGroups(file.getGroups()));

            sentFileDTOS.add(sentFileDTO);
        }

        return sentFileDTOS;
    }

    private String getStringListUsers(Set<User> users) {
        List<String> names = new ArrayList<>();

        for (User user: users) {
            names.add(user.getUsername());
        }

        return String.join(", ", names);
    }

    private String getStringListInstitutes(Set<Institute> institutes) {
        List<String> titles = new ArrayList<>();

        for (Institute institute: institutes) {
            titles.add(institute.getTitle());
        }

        return String.join(", ", titles);
    }

    private String getStringListGroups(Set<Group> groups) {
        List<String> titles = new ArrayList<>();

        for (Group group: groups) {
            titles.add(group.getTitle());
        }

        return String.join(", ", titles);
    }

    private Set<User> findUsersByUsernames(List<String> usernames) {
        Set<String> setUsernames = new HashSet<>(usernames);
        Set<User> users = new HashSet<>();

        for(String username: setUsernames) {
            users.add(userRepository.findByUsername(username));
        }

        return users;
    }

    private Set<Group> findGroupsByTitles(List<String> titles) {
        Set<String> setTitles = new HashSet<>(titles);
        Set<Group> groups = new HashSet<>();

        for(String title: setTitles) {
            groups.add(groupRepository.findByTitle(title));
        }

        return groups;
    }

    private Set<Institute> findInstitutesByTitles(List<String> titles) {
        Set<String> setTitles = new HashSet<>(titles);
        Set<Institute> institutes = new HashSet<>();

        for(String title: setTitles) {
            institutes.add(instituteRepository.findByTitle(title));
        }

        return institutes;
    }

    private void saveFile(MultipartFile file, String fileName) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                init();
            }
            Files.copy(file.getInputStream(), root.resolve(fileName));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    private String getPostfix(String originalName) {
        int indexPoint = originalName.lastIndexOf(".");
        return originalName.substring(indexPoint);
    }
}
