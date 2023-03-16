package com.tafakkoor.e_learn.services;

import com.tafakkoor.e_learn.domain.AuthUser;
import com.tafakkoor.e_learn.domain.Content;
import com.tafakkoor.e_learn.domain.Image;
import com.tafakkoor.e_learn.domain.UserContent;
import com.tafakkoor.e_learn.dto.UserRegisterDTO;
import com.tafakkoor.e_learn.enums.ContentType;
import com.tafakkoor.e_learn.enums.Levels;
import com.tafakkoor.e_learn.enums.Progress;
import com.tafakkoor.e_learn.enums.Status;
import com.tafakkoor.e_learn.repository.AuthUserRepository;
import com.tafakkoor.e_learn.repository.ContentRepository;
import com.tafakkoor.e_learn.repository.TokenRepository;
import com.tafakkoor.e_learn.repository.UserContentRepository;
import com.tafakkoor.e_learn.utils.Util;
import com.tafakkoor.e_learn.utils.mail.EmailService;
import lombok.NonNull;
import org.aspectj.lang.annotation.Before;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {
    private final AuthUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final ImageService imageService;
    private final UserContentRepository userContentRepository;
    private final ContentRepository contentRepository;

    public UserService(AuthUserRepository userRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository, TokenService tokenService , ImageService imageService, UserContentRepository userContentRepository, ContentRepository contentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.imageService = imageService;
        this.userContentRepository = userContentRepository;
        this.contentRepository = contentRepository;
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

    public void saveUserAndSendEmail(UserRegisterDTO dto) {
        AuthUser user = AuthUser.builder()
                .username(dto.getUsername().toLowerCase())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail().toLowerCase())
                .build();
        userRepository.save(user);
        sendActivationEmail(user);
    }

    public void sendActivationEmail(AuthUser authUser) {
        Util util = Util.getInstance();
        String token = tokenService.generateToken();  // TODO: 3/12/23 encrypt token
        String email = authUser.getEmail();
        String body = util.generateBody(authUser.getUsername(), token);
        tokenService.save(util.buildToken(token, authUser));
        CompletableFuture.runAsync(() -> EmailService.getInstance().sendEmail(email, body, "Activate Email"));
    }

    // Dilshod's code below


    public List<Content> getContentsStories(Levels level, Long id) {
        if (checkUserStatus(id)) {
            return null;
        }
        return contentRepository.findByLevelAndContentTypeAndDeleted(level, ContentType.STORY, false);
    }


    private boolean checkUserStatus(Long id) {
        List<UserContent> userContents = userContentRepository.findByUserIdAndProgress(id, Progress.IN_PROGRESS);
        return userContents.size() > 0;
    }

    public List<Content> getContentsGrammar(Levels level, Long id) {
        if (checkUserStatus(id)) {
            return null;
        }
        return contentRepository.findByLevelAndContentTypeAndDeleted(level, ContentType.GRAMMAR, false);
    }
    public List<AuthUser> getAllUsers() {
        return userRepository.findByDeleted(false);
    }

    public void updateStatus(Long id) {
        AuthUser byId = userRepository.findById(id);
        boolean blocked = byId.getStatus().equals(Status.BLOCKED);
        if(blocked){
            byId.setStatus(Status.ACTIVE);
        }
        else{
            byId.setStatus(Status.BLOCKED);
        }
        userRepository.save(byId);
    }

    // Abdullo's code below that




    public boolean userExist(String username) {
        AuthUser user = userRepository.findByUsername(username);
        return user != null;
    }

    public void saveGoogleUser(Map<String, Object> attributes) {
        String picture = (String) attributes.get("picture");
        Image imageBuild = Image.builder()
                .mimeType("image/jpg")
                .generatedFileName("google.jpg")
                .originalFileName("google.jpg")
                .filePath(picture)
                .build();
        String firstname = (String) attributes.get("given_name");
        String lastname = (String) attributes.get("family_name");
        String email = (String) attributes.get("email");
        String username = (String) attributes.get("sub");

        Image image = imageService.saveImage(imageBuild);
//        Image finalImage = imageService.findById(image.getId());
        String password = "the.Strongest.Password@Ever9";
        AuthUser user = AuthUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .firstName(firstname)
                .lastName(lastname)
                .image(image)
                .status(Status.ACTIVE)
                .build();
        userRepository.save(user);
    }

    public void saveFacebookUser(Map<String, Object> attributes) {
        String fullName = (String) attributes.get("name");
        String email = (String) attributes.get("email");
        String username = (String) attributes.get("id");
        String password = "the.Strongest.Password@Ever9";
        String firstName = null;
        String lastName = null;
        String[] strings = fullName.split(" ");

        if (strings.length == 2) {
            firstName = strings[0];
            lastName = strings[1];
        } else firstName = strings[0];

        AuthUser user = AuthUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .status(Status.ACTIVE)
                .build();
        userRepository.save(user);

    }

    public void saveLinkedinUser(Map<String, Object> attributes) {
        attributes.entrySet().forEach(System.out::println);
    }

    /*public void saveGithubUser(Map<String, Object> attributes) {
        String id = attributes.get("id").toString();
        BigInteger username = Util.getInstance().convertToBigInteger(id);
        System.out.println(username);
//        String email = (String) attributes.get("email");
//        String password = "the.Strongest.Password@Ever9";
//        String firstName = (String) attributes.get("name");
//        String lastName = null;
//        String[] strings = firstName.split(" ");

        for (Map.Entry<String, Object> stringObjectEntry : attributes.entrySet()) {
            System.out.println(stringObjectEntry.getKey() + " : " + stringObjectEntry.getValue());
        }

//        if (strings.length == 2) {
//            firstName = strings[0];
//            lastName = strings[1];
//        } else firstName = strings[0];

//        AuthUser user = AuthUser.builder()
//                .username(username)
//                .password(passwordEncoder.encode(password))
//                .email(email)
//                .firstName(firstName)
//                .lastName(lastName)
//                .status(Status.ACTIVE)
//                .build();
//        userRepository.save(user);
    }*/

}
