package com.chummy_backend.serverside.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chummy_backend.serverside.Model.general.Class_Partipation;

@Repository
public interface Class_PartipationRepository extends JpaRepository<Class_Partipation, Long> {
}
