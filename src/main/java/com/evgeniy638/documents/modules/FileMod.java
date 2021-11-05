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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
