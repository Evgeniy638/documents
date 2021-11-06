package com.evgeniy638.documents.modules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "groupp")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "title", unique = true)
    private String title;

    @ManyToOne
    private Institute institute;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> users;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
    private Set<FileMod> file;

    public void addUser(User user) {
        users.add(user);
        user.setGroup(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.setGroup(null);
    }
}
