package ru.xpressed.springreactservercoursework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.xpressed.springreactservercoursework.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByToken(String token);

    boolean existsUserByToken(String token);

    @Query(value = "SELECT * FROM public.user WHERE (:surname is null or surname = :surname) AND" +
            "(:name is null or name = :name) AND" +
            "(:patronymic is null or patronymic = :patronymic) AND" +
            "(:groupName is null or group_name = :groupName)", nativeQuery = true)
    List<User> findByParams(@Param("surname") String surname, @Param("name") String name, @Param("patronymic") String patronymic, @Param("groupName") String groupName);
}
