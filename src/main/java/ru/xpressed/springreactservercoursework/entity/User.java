package ru.xpressed.springreactservercoursework.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder()
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails {
    @Id
    private String username;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_username")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Role> roles;

    private String surname;

    private String name;

    private String patronymic;

    private String groupName;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Performance> performances;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Attendance> attendances;

    private String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !roles.contains(Role.ROLE_DEFAULT);
    }
}
