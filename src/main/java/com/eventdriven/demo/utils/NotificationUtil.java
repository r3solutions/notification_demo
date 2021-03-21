package com.eventdriven.demo.utils;

import com.eventdriven.demo.entity.Notification;
import com.eventdriven.demo.entity.Post;
import com.eventdriven.demo.rabbitmq.MessagingConfig;
import com.eventdriven.demo.repository.NotificationRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationUtil {


    @Autowired
    NotificationRepository notificationRepository;

    public Notification createNotification(Object object, String sourceUserFullName){
        Notification notification = new Notification();
        if(object.getClass() == Post.class){
            Post post = (Post) object;
            notification.setSourceUserName(sourceUserFullName);
            notification.setDestinationUserId(post.getUserMaster().getUserId());
            notification.setEvent(Event.LIKE_EVENT.name());
            notification.setNotificationTemplate(getLikeTemplate(sourceUserFullName));
            notification.setSourceUrl("");
        }else{
            //todo: for other entities
        }
        return notificationRepository.save(notification);
    }

    public String getLikeTemplate(String sourceUserFullName) {
        String body = sourceUserFullName + " like your post.";
        return body;
    }
}
