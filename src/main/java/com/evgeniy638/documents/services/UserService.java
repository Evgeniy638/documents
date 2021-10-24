package com.evgeniy638.documents.services;

import com.evgeniy638.documents.modules.User;
import com.evgeniy638.documents.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
}
