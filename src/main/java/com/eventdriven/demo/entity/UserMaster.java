package com.eventdriven.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
public class UserMaster implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "user_name", unique = true)
    private String username;

    @OneToMany(mappedBy = "userMaster",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Post> posts;
}
