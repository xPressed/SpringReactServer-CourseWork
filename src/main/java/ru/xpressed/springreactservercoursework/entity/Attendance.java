package ru.xpressed.springreactservercoursework.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder()
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String date;

    private String enterTime;

    private String exitTime;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private User user;
}
