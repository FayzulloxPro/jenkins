package com.tafakkoor.e_learn.controller;

import com.tafakkoor.e_learn.config.security.UserSession;
import com.tafakkoor.e_learn.domain.AuthUser;
import com.tafakkoor.e_learn.domain.Comment;
import com.tafakkoor.e_learn.domain.Content;
import com.tafakkoor.e_learn.domain.UserContent;
import com.tafakkoor.e_learn.enums.CommentType;
import com.tafakkoor.e_learn.enums.Levels;
import com.tafakkoor.e_learn.services.UserService;
import lombok.NonNull;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller

public class UserController {
    private final UserSession userSession;
    private final UserService userService;

    public UserController(UserSession userSession, UserService userService) {
        this.userSession = userSession;
        this.userService = userService;
    }

    @GetMapping("/practise")
    public String practise() {
        return "user/practise";
    }

    @GetMapping("/practise/story")
    public ModelAndView story() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userLevel", userSession.getLevel());
        modelAndView.setViewName("user/story/levelsStory");
        AuthUser user = userService.getUser(userSession.getId());
        List<Levels> levelsList = userService.getLevels(user.getLevel());
        modelAndView.addObject("levels", levelsList);
        return modelAndView;
    }

    @GetMapping("/practise/grammar")
    public ModelAndView grammar() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("levelNotFound", null);
        modelAndView.setViewName("user/grammar/levelsGrammar");
        AuthUser user = userService.getUser(userSession.getId());
        List<Levels> levelsList = userService.getLevels(user.getLevel());
        modelAndView.addObject("levels", levelsList);
        return modelAndView;
    }


    @GetMapping("/practise/story/{level}")
    public ModelAndView testStory(@PathVariable String level) {
        ModelAndView modelAndView = new ModelAndView();
        Levels levels = null;
        try {
            levels = Levels.valueOf(level.toUpperCase());
        } catch (Exception e) {
            modelAndView.addObject("flag", true);
            modelAndView.addObject("levelNotFound", "Level not found named %s".formatted(level.toUpperCase()));
            modelAndView.setViewName("user/levelNotFound");
            return modelAndView;
        }
        AuthUser user = userService.getUser(userSession.getId());
        if (!checkLevel(levels, user.getLevel())) {
            modelAndView.addObject("levelNotFound", "I think it's too early for you to try this level %s".formatted(level.toUpperCase()));
            modelAndView.setViewName("user/levelNotFound");
            return modelAndView;
        }
        try {

            List<Content> contents = userService.getContentsStories(levels, userSession.getId());
            modelAndView.addObject("flag", Objects.equals(user.getLevel(), levels));
            modelAndView.addObject("stories", contents);
            modelAndView.setViewName("user/story/Stories");
            return modelAndView;
        } catch (Exception e) {
            return userService.getInProgressPage(modelAndView, e);
        }
    }

    @GetMapping("/practise/grammar/{level}")
    public ModelAndView testGrammar(@PathVariable String level) {
        ModelAndView modelAndView = new ModelAndView();
        Levels levels = null;
        try {
            levels = Levels.valueOf(level.toUpperCase());
        } catch (Exception e) {
            modelAndView.addObject("levelNotFound", "Level not found named %s".formatted(level.toUpperCase()));
            modelAndView.setViewName("user/levelNotFound");
            return modelAndView;
        }
        AuthUser user = userService.getUser(userSession.getId());
        if (!checkLevel(levels, user.getLevel())) {
            modelAndView.addObject("levelNotFound", "I think it's too early for you to try this level %s".formatted(level.toUpperCase()));
            modelAndView.setViewName("user/levelNotFound");
            return modelAndView;
        }
        try {
            List<Content> contents = userService.getContentsGrammar(levels, userSession.getId());
            modelAndView.addObject("flag", Objects.equals(user.getLevel(), levels));
            modelAndView.addObject("grammars", contents);
            modelAndView.setViewName("user/grammar/Grammars");
            return modelAndView;
        } catch (Exception e) {
            return userService.getInProgressPage(modelAndView, e);

        }
    }


    public boolean checkLevel(@NonNull Levels level, @NonNull Levels userLevel) {
        return level.ordinal() <= userLevel.ordinal();
    }

    @GetMapping("/practise/stories/{storyId}")
    public ModelAndView story(@PathVariable String storyId) {
        ModelAndView modelAndView = new ModelAndView();
        AuthUser user = userService.getUser(userSession.getId());
        if (user == null || user.getLevel().equals(Levels.DEFAULT)) {
            modelAndView.addObject("levelNotFound", "You have not taken assessment test yet");
            modelAndView.setViewName("user/levelNotFound");
            return modelAndView;
        }
        Long id = null;
        try {
            id = Long.parseLong(storyId);
        } catch (Exception e) {
            modelAndView.addObject("levelNotFound", "Story not found ");
            modelAndView.setViewName("user/levelNotFound");
            return modelAndView;
        }
        UserContent statusContent = userService.checkUserStatus(userSession.getId());
        if (statusContent != null) {
            modelAndView.addObject("complete", "Complete this content first");
            modelAndView.addObject("content", statusContent.getContent());
            modelAndView.addObject("comments", userService.getComments(statusContent.getContent().getId()));
            modelAndView.setViewName("user/story/readingPage");
            return modelAndView;
        }
        Content content = userService.getStoryById(id);
        if (content == null) {
            modelAndView.addObject("levelNotFound", "Story not found ");
            modelAndView.setViewName("user/levelNotFound");
            return modelAndView;
        }
        List<Comment> comments = userService.getComments(content.getId());

        modelAndView.addObject("userId", userSession.getId());
        modelAndView.addObject("comments", comments);
        modelAndView.addObject("content", content);
        modelAndView.setViewName("user/story/readingPage");
        return modelAndView;
    }

    @GetMapping("/test")
    public String test() {
        return "user/story/readingPage";
    }

    @PostMapping("/practise/story/comments/add/{id}")
    public ModelAndView addComment(@PathVariable String id, @ModelAttribute("comment") Comment comment) {
        ModelAndView modelAndView = new ModelAndView();
        Long contentId = null;
        try {
            contentId = Long.parseLong(id);
        } catch (Exception e) {
            modelAndView.addObject("levelNotFound", "Story not found ");
            modelAndView.setViewName("user/levelNotFound");
            return modelAndView;
        }
        Content content = userService.getStoryById(contentId);
        if (content == null) {
            modelAndView.addObject("levelNotFound", "Story not found ");
            modelAndView.setViewName("user/levelNotFound");
            return modelAndView;
        }
        comment.setUserId(userService.getUser(userSession.getId()));
        comment.setContentId(content.getId());
        comment.setCommentType(Objects.requireNonNullElse(comment.getCommentType(), CommentType.COMMENT));
        userService.addComment(comment);
        modelAndView.setViewName("redirect:/practise/stories/" + content.getId());
        return modelAndView;
    }


    @PostMapping("/practise/story/comments/delete/{id}")
    public ModelAndView deleteComment(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        Long commentId = null;
        try {
            commentId = Long.parseLong(id);
        } catch (Exception e) {
            modelAndView.addObject("levelNotFound", " ");
            modelAndView.setViewName("user/levelNotFound");
            return modelAndView;
        }
        Optional<Comment> commentOptional = userService.getCommentById(commentId);
        if (commentOptional.isEmpty()) {
            modelAndView.addObject("levelNotFound", "Something went wrong");
            modelAndView.setViewName("user/levelNotFound");
            return modelAndView;
        }
        Comment comment = commentOptional.get();
        userService.deleteCommentById(comment.getId());
        modelAndView.setViewName("redirect:/practise/stories/" + comment.getContentId());
        return modelAndView;
    }

    @PostMapping("/practise/story/comments/update/{commentId}")
    public ModelAndView updateComment(@PathVariable String commentId, @ModelAttribute("comment") Comment comment) {
        ModelAndView modelAndView = new ModelAndView();
        Long id = null;
        try {
            id = Long.parseLong(commentId);
        } catch (Exception e) {
            modelAndView.addObject("levelNotFound", " ");
            modelAndView.setViewName("user/levelNotFound");
            return modelAndView;
        }
        Optional<Comment> commentOptional = userService.getCommentById(id);
        if (commentOptional.isEmpty()) {
            modelAndView.addObject("levelNotFound", "Something went wrong");
            modelAndView.setViewName("user/levelNotFound");
            return modelAndView;
        }
        Comment comment1 = commentOptional.get();
        comment1.setComment(comment.getComment());
        userService.updateComment(comment1);
        modelAndView.setViewName("redirect:/practise/stories/" + comment1.getContentId());
        return modelAndView;
    }

}
