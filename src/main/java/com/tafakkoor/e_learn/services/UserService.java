package com.tafakkoor.e_learn.services;

import com.tafakkoor.e_learn.domain.AuthUser;
import com.tafakkoor.e_learn.enums.Levels;
import com.tafakkoor.e_learn.repository.AuthUserRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final AuthUserRepository userRepository;

    public UserService(AuthUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Levels> getLevels(@NonNull Levels level) {
        List<Levels> levelsList = new ArrayList<>();
        if (level.equals(Levels.DEFAULT)) {
            return levelsList;
        }
        Levels[] levels = Levels.values();
        for (int i = 0; i < levels.length; i++) {
            if (!levels[i].equals(level)) {
                levelsList.add(levels[i]);
            } else {
                levelsList.add(levels[i]);
                break;
            }
        }
        return levelsList;
    }

    public AuthUser getUser(Long id) {
        return userRepository.findById(id);
    }
}
