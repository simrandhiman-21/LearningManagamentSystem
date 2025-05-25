    package com.learning.userservice.repository;

    import com.learning.userservice.entity.UserProfile;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.Optional;

    @Repository
    public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
        Optional<UserProfile> findByUsername(String username);
    }

