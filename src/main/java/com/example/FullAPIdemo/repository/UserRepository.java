package com.example.FullAPIdemo.repository;

import com.example.FullAPIdemo.model.dto.CadastroRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.FullAPIdemo.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    //@NativeQuery("select distinct u from cl203334.user where username= ?1")
    List<User> findByUsernameIs(String username);
    @Query("SELECT u.Id FROM User u WHERE u.username = :username")
    Long findIdByUsername(@Param("username") String username);
    void save(CadastroRequest request);
}
