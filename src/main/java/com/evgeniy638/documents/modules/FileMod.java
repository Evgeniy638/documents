package com.evgeniy638.documents.modules;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "file")
public class FileMod {
    @Id
    @GeneratedValue
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "creator")
    private String creatorUsername;

    @Column(name = "creator_connection")
    private String creatorUsernameConnection;

    @Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
    Date creationTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "files_institutions",
            joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "institution_id")
    )
    private Set<Institute> institutions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "files_groups",
            joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Group> groups;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "files_users",
            joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;
}
