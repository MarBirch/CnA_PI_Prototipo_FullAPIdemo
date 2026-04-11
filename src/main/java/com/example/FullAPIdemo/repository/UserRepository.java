package com.example.FullAPIdemo.repository;

import com.example.FullAPIdemo.model.dto.CadastroRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.FullAPIdemo.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //@Query("SELECT u.senha FROM User u WHERE u.username = :username")
    @Query("SELECT u.Id FROM User u WHERE u.username = :username")
    Long findIdByUsername(@Param("username") String username);

    void save(CadastroRequest request);

    User findByUsername(String username);

    List<User> findAll();
}
