package com.chummy_backend.serverside.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chummy_backend.serverside.Model.examination.Vocabulary;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
}
