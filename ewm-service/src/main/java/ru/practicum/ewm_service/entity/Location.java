package ru.practicum.ewm_service.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "locations", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private float lat;

    @Column(nullable = false)
    private float lon;

}
