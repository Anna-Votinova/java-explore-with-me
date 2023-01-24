package ru.practicum.ewm_service.entity;

import lombok.*;
import ru.practicum.ewm_service.entity.util.Status;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "requests", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Timestamp created;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @ToString.Exclude
    private Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    @ToString.Exclude
    private User requester;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;
        return id != null && id.equals(((Request) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
