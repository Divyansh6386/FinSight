package com.midas.watchlist.entity;

import com.midas.common.entity.BaseEntity;
import com.midas.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "watchlists")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Watchlist extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}