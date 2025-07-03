package com.chummy_backend.serverside.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chummy_backend.serverside.Model.examination.Vocabulary;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
     Optional<Vocabulary> findByWordAndMeaning(String word, String meaning);

     Optional<Vocabulary> findByWord(String word);

     boolean existsByWordAndMeaning(String word, String meaning);
}
