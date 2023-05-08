package ru.xpressed.springreactservercoursework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.xpressed.springreactservercoursework.entity.Attendance;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    @Query(value = "SELECT * FROM public.attendance WHERE (:date is null or date = :date) AND" +
            "(:enterTime is null or enter_time = :enterTime) AND" +
            "(:exitTime is null or exit_time = :exitTime) AND" +
            "(:username is null or user_username = :username)", nativeQuery = true)
    List<Attendance> findByParams(@Param("date") String date, @Param("enterTime") String enterTime, @Param("exitTime") String exitTime, @Param("username") String username);

    @Modifying
    @Query(value = "DELETE FROM public.attendance WHERE id = :id", nativeQuery = true)
    void removeById(@Param("id") Integer id);
}
