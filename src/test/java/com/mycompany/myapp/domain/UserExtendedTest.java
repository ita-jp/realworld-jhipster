package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.UserExtendedTestSamples.*;
import static com.mycompany.myapp.domain.UserExtendedTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UserExtendedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserExtended.class);
        UserExtended userExtended1 = getUserExtendedSample1();
        UserExtended userExtended2 = new UserExtended();
        assertThat(userExtended1).isNotEqualTo(userExtended2);

        userExtended2.setId(userExtended1.getId());
        assertThat(userExtended1).isEqualTo(userExtended2);

        userExtended2 = getUserExtendedSample2();
        assertThat(userExtended1).isNotEqualTo(userExtended2);
    }

    @Test
    void userTest() throws Exception {
        UserExtended userExtended = getUserExtendedRandomSampleGenerator();
        UserExtended userExtendedBack = getUserExtendedRandomSampleGenerator();

        userExtended.addUser(userExtendedBack);
        assertThat(userExtended.getUsers()).containsOnly(userExtendedBack);

        userExtended.removeUser(userExtendedBack);
        assertThat(userExtended.getUsers()).doesNotContain(userExtendedBack);

        userExtended.users(new HashSet<>(Set.of(userExtendedBack)));
        assertThat(userExtended.getUsers()).containsOnly(userExtendedBack);

        userExtended.setUsers(new HashSet<>());
        assertThat(userExtended.getUsers()).doesNotContain(userExtendedBack);
    }

    @Test
    void followTest() throws Exception {
        UserExtended userExtended = getUserExtendedRandomSampleGenerator();
        UserExtended userExtendedBack = getUserExtendedRandomSampleGenerator();

        userExtended.addFollow(userExtendedBack);
        assertThat(userExtended.getFollows()).containsOnly(userExtendedBack);
        assertThat(userExtendedBack.getUsers()).containsOnly(userExtended);

        userExtended.removeFollow(userExtendedBack);
        assertThat(userExtended.getFollows()).doesNotContain(userExtendedBack);
        assertThat(userExtendedBack.getUsers()).doesNotContain(userExtended);

        userExtended.follows(new HashSet<>(Set.of(userExtendedBack)));
        assertThat(userExtended.getFollows()).containsOnly(userExtendedBack);
        assertThat(userExtendedBack.getUsers()).containsOnly(userExtended);

        userExtended.setFollows(new HashSet<>());
        assertThat(userExtended.getFollows()).doesNotContain(userExtendedBack);
        assertThat(userExtendedBack.getUsers()).doesNotContain(userExtended);
    }
}
