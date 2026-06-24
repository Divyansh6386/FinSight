package com.midas.watchlist.entity;

import com.midas.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "watchlist_items",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"watchlist_id", "symbol"}
        )
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchlistItem extends BaseEntity {

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private String companyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "watchlist_id", nullable = false)
    private Watchlist watchlist;
}