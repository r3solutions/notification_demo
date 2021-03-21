package com.eventdriven.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private long notificationId;

    @Column(name = "source_user_name")
    private String sourceUserName;

    @Column(name = "destination_user_id")
    private long destinationUserId;

    @Column(name = "event_action")
    private String event;

    @Column(name = "source_url")
    private String sourceUrl;

    @Column(name = "notification_template")
    private String notificationTemplate;
}
