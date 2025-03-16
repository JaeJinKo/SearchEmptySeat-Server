package com.BubbleWrap.SearchEmptySeat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_store_views")
public class StoreViews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long viewsPK;

    @OneToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private int viewCount = 0; // 기본값 0

    @Column(nullable = false)
    private LocalDateTime lastViewedDate = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedDate = LocalDateTime.now();

    public StoreViews(Store store) {
        this.store = store;
        this.viewCount = 0;
        this.lastViewedDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    public void increaseViewCount() {
        this.viewCount++;
        this.updatedDate = LocalDateTime.now();
    }
}
