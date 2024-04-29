package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ProfileTestSamples.*;
import static com.mycompany.myapp.domain.ProfileTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProfileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profile.class);
        Profile profile1 = getProfileSample1();
        Profile profile2 = new Profile();
        assertThat(profile1).isNotEqualTo(profile2);

        profile2.setId(profile1.getId());
        assertThat(profile1).isEqualTo(profile2);

        profile2 = getProfileSample2();
        assertThat(profile1).isNotEqualTo(profile2);
    }

    @Test
    void followerTest() throws Exception {
        Profile profile = getProfileRandomSampleGenerator();
        Profile profileBack = getProfileRandomSampleGenerator();

        profile.addFollower(profileBack);
        assertThat(profile.getFollowers()).containsOnly(profileBack);

        profile.removeFollower(profileBack);
        assertThat(profile.getFollowers()).doesNotContain(profileBack);

        profile.followers(new HashSet<>(Set.of(profileBack)));
        assertThat(profile.getFollowers()).containsOnly(profileBack);

        profile.setFollowers(new HashSet<>());
        assertThat(profile.getFollowers()).doesNotContain(profileBack);
    }

    @Test
    void followeeTest() throws Exception {
        Profile profile = getProfileRandomSampleGenerator();
        Profile profileBack = getProfileRandomSampleGenerator();

        profile.addFollowee(profileBack);
        assertThat(profile.getFollowees()).containsOnly(profileBack);
        assertThat(profileBack.getFollowers()).containsOnly(profile);

        profile.removeFollowee(profileBack);
        assertThat(profile.getFollowees()).doesNotContain(profileBack);
        assertThat(profileBack.getFollowers()).doesNotContain(profile);

        profile.followees(new HashSet<>(Set.of(profileBack)));
        assertThat(profile.getFollowees()).containsOnly(profileBack);
        assertThat(profileBack.getFollowers()).containsOnly(profile);

        profile.setFollowees(new HashSet<>());
        assertThat(profile.getFollowees()).doesNotContain(profileBack);
        assertThat(profileBack.getFollowers()).doesNotContain(profile);
    }
}
