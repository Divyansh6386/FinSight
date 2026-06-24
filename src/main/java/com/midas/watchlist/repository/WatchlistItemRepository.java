package com.midas.watchlist.repository;

import com.midas.watchlist.entity.Watchlist;
import com.midas.watchlist.entity.WatchlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchlistItemRepository extends JpaRepository<WatchlistItem, Long> {

    List<WatchlistItem> findByWatchlist(Watchlist watchlist);

    Optional<WatchlistItem> findByIdAndWatchlist(Long id, Watchlist watchlist);

    Optional<WatchlistItem> findByWatchlistAndSymbol(
            Watchlist watchlist,
            String symbol
    );

    boolean existsByWatchlistAndSymbol(
            Watchlist watchlist,
            String symbol
    );
      void deleteByIdAndWatchlist(
            Long id,
            Watchlist watchlist
    );
}