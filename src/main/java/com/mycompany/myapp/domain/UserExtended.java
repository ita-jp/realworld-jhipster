package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A UserExtended.
 */
@Entity
@Table(name = "user_extended")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserExtended implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_user_extended__user",
        joinColumns = @JoinColumn(name = "user_extended_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties(value = { "users", "follows" }, allowSetters = true)
    private Set<UserExtended> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    @JsonIgnoreProperties(value = { "users", "follows" }, allowSetters = true)
    private Set<UserExtended> follows = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserExtended id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UserExtended> getUsers() {
        return this.users;
    }

    public void setUsers(Set<UserExtended> userExtendeds) {
        this.users = userExtendeds;
    }

    public UserExtended users(Set<UserExtended> userExtendeds) {
        this.setUsers(userExtendeds);
        return this;
    }

    public UserExtended addUser(UserExtended userExtended) {
        this.users.add(userExtended);
        return this;
    }

    public UserExtended removeUser(UserExtended userExtended) {
        this.users.remove(userExtended);
        return this;
    }

    public Set<UserExtended> getFollows() {
        return this.follows;
    }

    public void setFollows(Set<UserExtended> userExtendeds) {
        if (this.follows != null) {
            this.follows.forEach(i -> i.removeUser(this));
        }
        if (userExtendeds != null) {
            userExtendeds.forEach(i -> i.addUser(this));
        }
        this.follows = userExtendeds;
    }

    public UserExtended follows(Set<UserExtended> userExtendeds) {
        this.setFollows(userExtendeds);
        return this;
    }

    public UserExtended addFollow(UserExtended userExtended) {
        this.follows.add(userExtended);
        userExtended.getUsers().add(this);
        return this;
    }

    public UserExtended removeFollow(UserExtended userExtended) {
        this.follows.remove(userExtended);
        userExtended.getUsers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExtended)) {
            return false;
        }
        return getId() != null && getId().equals(((UserExtended) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExtended{" +
            "id=" + getId() +
            "}";
    }
}
