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
@Table(name = "institute")
public class Institute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "title", unique = true)
    private String title;

    @OneToMany(mappedBy = "institute", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Group> groups;

    @ManyToMany(mappedBy = "institutions")
    private Set<FileMod> file;

    public void addGroup(Group group) {
        groups.add(group);
        group.setInstitute(this);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
        group.setInstitute(null);
    }
}
