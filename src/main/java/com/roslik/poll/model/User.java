package com.roslik.poll.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @NotEmpty
    @Column(name = "ip", length = 20, unique = true, nullable = false)
    private String ip;

    @ManyToMany(mappedBy = "users")
    private List<Option> options;

    @Valid
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Poll> polls;

}
