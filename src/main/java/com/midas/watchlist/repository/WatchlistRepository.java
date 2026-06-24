package com.midas.watchlist.repository;

import com.midas.user.entity.User;
import com.midas.watchlist.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    List<Watchlist> findByUser(User user);

    Optional<Watchlist> findByIdAndUser(Long id, User user);

    boolean existsByUserAndName(User user, String name);
    void deleteByIdAndUser(
            Long id,
            User user
    );
}