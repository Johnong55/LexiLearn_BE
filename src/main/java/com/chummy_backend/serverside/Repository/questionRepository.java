package com.chummy_backend.serverside.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chummy_backend.serverside.Model.examination.question;

@Repository
public interface questionRepository extends JpaRepository<question, Long> {
}
