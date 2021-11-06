package com.evgeniy638.documents.services;

import com.evgeniy638.documents.dto.FileDTO;
import com.evgeniy638.documents.dto.FileInfoDTO;
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
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileModRepository fileModRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final InstituteRepository instituteRepository;

    @Value("${app.path.upload.file}")
    private String uploadPath;

    private SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm dd.MM.yyyy");

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

        for(MultipartFile multipartFile: fileDTO.getFiles()) {

            FileMod fileMod = new FileMod();

            fileMod.setName(multipartFile.getOriginalFilename());
            fileMod.setCreationTime(new Date());
            fileMod.setUsers(findUsersByUsernames(fileDTO.getUsernames()));
            fileMod.setCreatorUsername(creatorUsername);
            fileMod.setCreatorUsernameConnection(creatorUsername);
            fileMod.setGroups(findGroupsByTitles(fileDTO.getGroupTitles()));
            fileMod.setInstitutions(findInstitutesByTitles(fileDTO.getInstitutionTitles()));

            FileMod savedFileMod = fileModRepository.save(fileMod);

            saveFile(
                    multipartFile,
                    savedFileMod.getId().toString() + getPostfix(fileMod.getName())
            );
        }
    }

    public List<SentFileDTO> getSentFiles(String username) {
        List<SentFileDTO> sentFileDTOS = new ArrayList<>();
        List<FileMod> sentFiles = fileModRepository.findByCreatorUsernameConnection(username);

        if (sentFiles == null) {
            return sentFileDTOS;
        }

        for (FileMod file: sentFiles) {
            SentFileDTO sentFileDTO = new SentFileDTO();

            if (file.getCreationTime() != null) {
                sentFileDTO.setDate(formatForDateNow.format(file.getCreationTime()));
            }

            sentFileDTO.setName(file.getName());
            sentFileDTO.setUuid(file.getId());
            sentFileDTO.setUrl("/upload-files/" + file.getId().toString() + getPostfix(file.getName()));
            sentFileDTO.setListUsers(getStringListUsers(file.getUsers()));
            sentFileDTO.setListInstitute(getStringListInstitutes(file.getInstitutions()));
            sentFileDTO.setListGroups(getStringListGroups(file.getGroups()));

            sentFileDTOS.add(sentFileDTO);
        }

        return sentFileDTOS;
    }

    public Set<FileInfoDTO> convertFileModToFileInfo(Set<FileMod> files) {
        Set<FileInfoDTO> fileInfoDTOS = new HashSet<>();

        for(FileMod file: files) {
            FileInfoDTO fileInfoDTO = new FileInfoDTO();

            fileInfoDTO.setUuid(file.getId());
            fileInfoDTO.setCreator(file.getCreatorUsername());
            fileInfoDTO.setName(file.getName());
            fileInfoDTO.setUrl("/upload-files/" + file.getId().toString() + getPostfix(file.getName()));

            if (file.getCreationTime() != null) {
                fileInfoDTO.setDate(formatForDateNow.format(file.getCreationTime()));
            }

            fileInfoDTOS.add(fileInfoDTO);
        }

        return fileInfoDTOS;
    }

    @Transactional
    public void deleteCreator(UUID fileId) {
        FileMod fileMod = fileModRepository.getById(fileId);
        fileMod.setCreatorUsernameConnection(null);
        fileModRepository.save(fileMod);
    }

    @Transactional
    public void deleteFromUser(int id, UUID fileId) {
        FileMod fileMod = fileModRepository.getById(fileId);
        Set<User> users = new HashSet<>();

        for (User user: fileMod.getUsers()) {
            if (user.getId() != id) {
                users.add(user);
            }
        }

        fileMod.setUsers(users);
        fileModRepository.save(fileMod);
    }

    @Transactional
    public void deleteFromGroup(int id, UUID fileId) {
        FileMod fileMod = fileModRepository.getById(fileId);
        Set<Group> groups = new HashSet<>();

        for (Group group: fileMod.getGroups()) {
            if (group.getId() != id) {
                groups.add(group);
            }
        }

        fileMod.setGroups(groups);
        fileModRepository.save(fileMod);
    }

    @Transactional
    public void deleteFromInstitute(int id, UUID fileId) {
        FileMod fileMod = fileModRepository.getById(fileId);
        Set<Institute> institutes = new HashSet<>();

        for (Institute institute: fileMod.getInstitutions()) {
            if (institute.getId() != id) {
                institutes.add(institute);
            }
        }

        fileMod.setInstitutions(institutes);
        fileModRepository.save(fileMod);
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

        if (indexPoint == -1) {
            return "";
        }

        return originalName.substring(indexPoint);
    }
}
