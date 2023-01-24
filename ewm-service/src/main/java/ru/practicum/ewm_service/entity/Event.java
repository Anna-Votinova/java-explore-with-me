package ru.practicum.ewm_service.entity;

import lombok.*;
import ru.practicum.ewm_service.entity.util.State;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "events", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String annotation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    private Category category;
    @Column(name = "confirmed_requests", nullable = false)
    @Builder.Default
    private Long confirmedRequests = 0L;
    @Column(name = "created_on", nullable = false)
    private Timestamp createdOn;
    @Column(nullable = false)
    private String description;
    @Column(name = "event_date", nullable = false)
    private Timestamp eventDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false)
    @ToString.Exclude
    private User initiator;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    @ToString.Exclude
    private Location location;
    @Column(nullable = false)
    private boolean paid;
    @Column(name = "participant_limit", nullable = false)
    private int participantLimit;
    @Column(name = "published_on")
    private Timestamp publishedOn;
    @Column(name = "request_moderation", nullable = false)
    @Builder.Default
    private boolean requestModeration = true;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state = State.PENDING;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    @Builder.Default
    private Long views = 0L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        return id != null && id.equals(((Event) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
