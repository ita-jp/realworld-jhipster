package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserExtended;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class UserExtendedRepositoryWithBagRelationshipsImpl implements UserExtendedRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String USEREXTENDEDS_PARAMETER = "userExtendeds";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserExtended> fetchBagRelationships(Optional<UserExtended> userExtended) {
        return userExtended.map(this::fetchUsers);
    }

    @Override
    public Page<UserExtended> fetchBagRelationships(Page<UserExtended> userExtendeds) {
        return new PageImpl<>(
            fetchBagRelationships(userExtendeds.getContent()),
            userExtendeds.getPageable(),
            userExtendeds.getTotalElements()
        );
    }

    @Override
    public List<UserExtended> fetchBagRelationships(List<UserExtended> userExtendeds) {
        return Optional.of(userExtendeds).map(this::fetchUsers).orElse(Collections.emptyList());
    }

    UserExtended fetchUsers(UserExtended result) {
        return entityManager
            .createQuery(
                "select userExtended from UserExtended userExtended left join fetch userExtended.users where userExtended.id = :id",
                UserExtended.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<UserExtended> fetchUsers(List<UserExtended> userExtendeds) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, userExtendeds.size()).forEach(index -> order.put(userExtendeds.get(index).getId(), index));
        List<UserExtended> result = entityManager
            .createQuery(
                "select userExtended from UserExtended userExtended left join fetch userExtended.users where userExtended in :userExtendeds",
                UserExtended.class
            )
            .setParameter(USEREXTENDEDS_PARAMETER, userExtendeds)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
