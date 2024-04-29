package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserExtended;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface UserExtendedRepositoryWithBagRelationships {
    Optional<UserExtended> fetchBagRelationships(Optional<UserExtended> userExtended);

    List<UserExtended> fetchBagRelationships(List<UserExtended> userExtendeds);

    Page<UserExtended> fetchBagRelationships(Page<UserExtended> userExtendeds);
}
