package com.chummy_backend.serverside.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chummy_backend.serverside.Model.examination.library;
import com.chummy_backend.serverside.Model.examination.library_vocabulary;


@Repository
public interface library_vocabularyRepository extends JpaRepository<library_vocabulary, Long> {
    List<library_vocabulary> findByLibrary(library library);
}
