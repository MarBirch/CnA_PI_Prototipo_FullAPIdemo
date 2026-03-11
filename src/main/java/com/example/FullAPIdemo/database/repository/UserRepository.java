package com.example.FullAPIdemo.database.repository;

import com.example.FullAPIdemo.database.model.CadastroRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.FullAPIdemo.database.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    //@NativeQuery("select distinct u  from cl203334.user where username= ?1")
    List<User> findByUsernameIs(String username);

    void save(CadastroRequest request);
}
