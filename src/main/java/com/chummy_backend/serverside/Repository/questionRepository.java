package com.chummy_backend.serverside.Repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chummy_backend.serverside.Model.examination.Vocabulary;
import com.chummy_backend.serverside.Model.examination.question;

@Repository
public interface questionRepository extends JpaRepository<question, Long> {
    List<question> findByVocabulary(Vocabulary vocabulary);

    List<question> findByVocabularyId(Long vocabularyId);

    @Query("SELECT q FROM question q WHERE q.vocabulary.word = :word")
    List<question> findByVocabularyWord(@Param("word") String word);
    void deleteByVocabularyId(Long vocabularyId);
}
