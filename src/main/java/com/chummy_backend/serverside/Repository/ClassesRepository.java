package com.chummy_backend.serverside.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chummy_backend.serverside.Model.general.Classes;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Long> {
}
