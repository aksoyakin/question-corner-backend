package com.aksoyakin.questioncornerbackend.repos;

import com.aksoyakin.questioncornerbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
