package com.eventdriven.demo.controller;

import com.eventdriven.demo.constants.Constants;
import com.eventdriven.demo.entity.Notification;
import com.eventdriven.demo.entity.Post;
import com.eventdriven.demo.rabbitmq.MessagingConfig;
import com.eventdriven.demo.repository.PostRepository;
import com.eventdriven.demo.utils.NotificationUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    NotificationUtil notificationUtil;

    @PostMapping("/add")
    public ResponseEntity<?> createPost(@RequestBody Post post){


        Post savedPost =  postRepository.save(post);
        if(savedPost!=null){


            return new ResponseEntity<>(savedPost, HttpStatus.OK);
        }

        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPost(){
        List<Post> allPost = postRepository.findAll();
        if(!allPost.isEmpty()){
            return new ResponseEntity<>(allPost, HttpStatus.OK);
        }

        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable("id") long postId){
        Post post = postRepository.getOne(postId);
        if(post!=null){
            return new ResponseEntity<>(post, HttpStatus.OK);
        }

        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/like")
    public ResponseEntity<?> likeEvent(@RequestHeader("userFullName") String userFullName, @RequestBody Post post){
        Post updatedPost = postRepository.save(post);
        if(updatedPost!=null){
            Notification notification = notificationUtil.createNotification(post,userFullName);
            rabbitTemplate.convertAndSend(MessagingConfig.EXCHANGE,MessagingConfig.ROUTING_KEY,notification);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        }

        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
