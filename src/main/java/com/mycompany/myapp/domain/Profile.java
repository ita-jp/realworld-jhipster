package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bio")
    private String bio;

    @Column(name = "image")
    private String image;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_profile__follower",
        joinColumns = @JoinColumn(name = "profile_id"),
        inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    @JsonIgnoreProperties(value = { "user", "followers", "followees" }, allowSetters = true)
    private Set<Profile> followers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "followers")
    @JsonIgnoreProperties(value = { "user", "followers", "followees" }, allowSetters = true)
    private Set<Profile> followees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Profile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return this.bio;
    }

    public Profile bio(String bio) {
        this.setBio(bio);
        return this;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return this.image;
    }

    public Profile image(String image) {
        this.setImage(image);
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Profile user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Profile> getFollowers() {
        return this.followers;
    }

    public void setFollowers(Set<Profile> profiles) {
        this.followers = profiles;
    }

    public Profile followers(Set<Profile> profiles) {
        this.setFollowers(profiles);
        return this;
    }

    public Profile addFollower(Profile profile) {
        this.followers.add(profile);
        return this;
    }

    public Profile removeFollower(Profile profile) {
        this.followers.remove(profile);
        return this;
    }

    public Set<Profile> getFollowees() {
        return this.followees;
    }

    public void setFollowees(Set<Profile> profiles) {
        if (this.followees != null) {
            this.followees.forEach(i -> i.removeFollower(this));
        }
        if (profiles != null) {
            profiles.forEach(i -> i.addFollower(this));
        }
        this.followees = profiles;
    }

    public Profile followees(Set<Profile> profiles) {
        this.setFollowees(profiles);
        return this;
    }

    public Profile addFollowee(Profile profile) {
        this.followees.add(profile);
        profile.getFollowers().add(this);
        return this;
    }

    public Profile removeFollowee(Profile profile) {
        this.followees.remove(profile);
        profile.getFollowers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return getId() != null && getId().equals(((Profile) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", bio='" + getBio() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}
