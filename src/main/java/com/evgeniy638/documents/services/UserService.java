package com.evgeniy638.documents.services;

import com.evgeniy638.documents.dto.StudentDTO;
import com.evgeniy638.documents.modules.Group;
import com.evgeniy638.documents.modules.Role;
import com.evgeniy638.documents.modules.User;
import com.evgeniy638.documents.repository.GroupRepository;
import com.evgeniy638.documents.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Возвращает пользователя по логину
     * @param username логин
     * @return данные пользователя
     */
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Создаёт новые или обновляет старые данные об пользователе
     * у которого id равен с university.id
     * @param user
     */
    public void save(User user) {
        userRepository.save(user);
    }

    public void createStudent(StudentDTO studentDTO) {
        User user = new User();
        Group group = groupRepository.findByTitle(studentDTO.getTitleGroup());

        user.setUsername(studentDTO.getUsername());
        user.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
        user.setFullName(studentDTO.getFullName());
        user.setGroup(group);
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.STUDENT));

        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
