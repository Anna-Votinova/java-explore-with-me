package ru.practicum.ewm_service.entity;


import lombok.*;
import ru.practicum.ewm_service.entity.util.Status;

import javax.persistence.*;

@Entity
@Table(name = "subscriptions", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", nullable = false)
    @ToString.Exclude
    private User friend;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription)) return false;
        return id != null && id.equals(((Subscription) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }



}
