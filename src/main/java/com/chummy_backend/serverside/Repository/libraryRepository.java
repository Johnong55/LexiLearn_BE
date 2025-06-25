package com.chummy_backend.serverside.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chummy_backend.serverside.Model.examination.library;
import com.chummy_backend.serverside.Model.general.Users;


@Repository
public interface libraryRepository extends JpaRepository<library, Long> {
    List<library> findByOwnerID(Users ownerID);
}
