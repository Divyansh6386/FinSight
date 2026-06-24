package com.midas.watchlist.service.impl;

import com.midas.user.entity.User;
import com.midas.user.service.UserService;
import com.midas.watchlist.dto.request.AddWatchlistItemRequest;
import com.midas.watchlist.dto.request.CreateWatchlistRequest;
import com.midas.watchlist.dto.request.UpdateWatchlistRequest;
import com.midas.watchlist.dto.response.WatchlistDetailsResponse;
import com.midas.watchlist.dto.response.WatchlistItemResponse;
import com.midas.watchlist.dto.response.WatchlistResponse;
import com.midas.watchlist.entity.Watchlist;
import com.midas.watchlist.entity.WatchlistItem;
import com.midas.watchlist.repository.WatchlistItemRepository;
import com.midas.watchlist.repository.WatchlistRepository;
import com.midas.watchlist.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WatchlistServiceImpl implements WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final WatchlistItemRepository watchlistItemRepository;
    private final UserService userService;

    @Override
    public WatchlistResponse createWatchlist(
            String email,
            CreateWatchlistRequest request) {

        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new NoSuchElementException("User not found"));

        if (watchlistRepository.existsByUserAndName(user, request.getName())) {
            throw new IllegalArgumentException("Watchlist already exists");
        }

        Watchlist watchlist = Watchlist.builder()
                .name(request.getName())
                .user(user)
                .build();

        Watchlist saved = watchlistRepository.save(watchlist);

        return mapWatchlist(saved);
    }
    @Override
public List<WatchlistResponse> getMyWatchlists(String email) {

    User user = userService.findByEmail(email)
            .orElseThrow(() ->
                    new NoSuchElementException("User not found"));

    return watchlistRepository.findByUser(user)
            .stream()
            .map(this::mapWatchlist)
            .collect(Collectors.toList());
}

@Override
public WatchlistDetailsResponse getWatchlist(
        String email,
        Long watchlistId) {

    User user = userService.findByEmail(email)
            .orElseThrow(() ->
                    new NoSuchElementException("User not found"));

    Watchlist watchlist = watchlistRepository
            .findByIdAndUser(watchlistId, user)
            .orElseThrow(() ->
                    new NoSuchElementException("Watchlist not found"));

    List<WatchlistItemResponse> items =
            watchlistItemRepository.findByWatchlist(watchlist)
                    .stream()
                    .map(this::mapItem)
                    .collect(Collectors.toList());

    return WatchlistDetailsResponse.builder()
            .id(watchlist.getId())
            .name(watchlist.getName())
            .createdAt(watchlist.getCreatedAt())
            .items(items)
            .build();
}
@Override
public WatchlistResponse updateWatchlist(
        String email,
        Long watchlistId,
        UpdateWatchlistRequest request) {

    User user = userService.findByEmail(email)
            .orElseThrow(() ->
                    new NoSuchElementException("User not found"));

    Watchlist watchlist = watchlistRepository
            .findByIdAndUser(watchlistId, user)
            .orElseThrow(() ->
                    new NoSuchElementException("Watchlist not found"));

    if (!watchlist.getName().equalsIgnoreCase(request.getName())
            && watchlistRepository.existsByUserAndName(user, request.getName())) {

        throw new IllegalArgumentException("Watchlist name already exists");
    }

    watchlist.setName(request.getName());

    Watchlist updated = watchlistRepository.save(watchlist);

    return mapWatchlist(updated);
}

@Override
public void deleteWatchlist(
        String email,
        Long watchlistId) {

    User user = userService.findByEmail(email)
            .orElseThrow(() ->
                    new NoSuchElementException("User not found"));

    Watchlist watchlist = watchlistRepository
            .findByIdAndUser(watchlistId, user)
            .orElseThrow(() ->
                    new NoSuchElementException("Watchlist not found"));

    watchlistRepository.delete(watchlist);
}
    private WatchlistResponse mapWatchlist(Watchlist watchlist) {

        return WatchlistResponse.builder()
                .id(watchlist.getId())
                .name(watchlist.getName())
                .createdAt(watchlist.getCreatedAt())
                .build();
    }
    @Override
public WatchlistItemResponse addItem(
        String email,
        Long watchlistId,
        AddWatchlistItemRequest request) {

    User user = userService.findByEmail(email)
            .orElseThrow(() ->
                    new NoSuchElementException("User not found"));

    Watchlist watchlist = watchlistRepository
            .findByIdAndUser(watchlistId, user)
            .orElseThrow(() ->
                    new NoSuchElementException("Watchlist not found"));

    String symbol = request.getSymbol().toUpperCase();

    if (watchlistItemRepository.existsByWatchlistAndSymbol(watchlist, symbol)) {
        throw new IllegalArgumentException("Stock already exists in this watchlist");
    }

    WatchlistItem item = WatchlistItem.builder()
            .watchlist(watchlist)
            .symbol(symbol)
            .companyName(request.getCompanyName())
            .build();

    WatchlistItem saved = watchlistItemRepository.save(item);

    return mapItem(saved);
}

@Override
public void removeItem(
        String email,
        Long watchlistId,
        Long itemId) {

    User user = userService.findByEmail(email)
            .orElseThrow(() ->
                    new NoSuchElementException("User not found"));

    Watchlist watchlist = watchlistRepository
            .findByIdAndUser(watchlistId, user)
            .orElseThrow(() ->
                    new NoSuchElementException("Watchlist not found"));

    WatchlistItem item = watchlistItemRepository
            .findByIdAndWatchlist(itemId, watchlist)
            .orElseThrow(() ->
                    new NoSuchElementException("Watchlist item not found"));

    watchlistItemRepository.delete(item);
}

    private WatchlistItemResponse mapItem(WatchlistItem item) {

        return WatchlistItemResponse.builder()
                .id(item.getId())
                .symbol(item.getSymbol())
                .companyName(item.getCompanyName())
                .build();
    }
}