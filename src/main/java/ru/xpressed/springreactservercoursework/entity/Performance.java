package ru.xpressed.springreactservercoursework.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder()
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String mark;

    private String year;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private User user;
}
