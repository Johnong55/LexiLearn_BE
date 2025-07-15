package com.chummy_backend.serverside.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chummy_backend.serverside.Exception.ResourceNotFoundException;
import com.chummy_backend.serverside.Model.examination.Vocabulary;
import com.chummy_backend.serverside.Model.examination.library;
import com.chummy_backend.serverside.Model.examination.library_vocabulary;
import com.chummy_backend.serverside.Repository.VocabularyRepository;

@Service
public class VocabularyService {

    @Autowired
    private VocabularyRepository repository;

    @Autowired
    private library_vocabularyService service;

    public List<Vocabulary> findAll() {
        return repository.findAll();
    }

    public Optional<Vocabulary> findById(Long id) {
        return repository.findById(id);
    }

    public Vocabulary save(Vocabulary entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Vocabulary> findByLibrary(library Library) {   
        List<Vocabulary> results = new ArrayList<>();
        List<library_vocabulary> vocabularies = service.findByLibraries(Library);
        vocabularies.forEach(vocab -> results.add(vocab.getVocabulary()));
        return results;
    }
  public List<Vocabulary> randomVocabularysForNewDays() {
    List<Vocabulary> allVocabularyList = findAll(); 
    List<Vocabulary> result = new ArrayList<>();
    Random random = new Random();
    
    int numberToPick  = 20;
    Set<Integer> usedIndexes = new HashSet<>();

    while (result.size() < numberToPick && usedIndexes.size() < allVocabularyList.size()) {
        int index = random.nextInt(allVocabularyList.size());
        if (!usedIndexes.contains(index)) {
            result.add(allVocabularyList.get(index));
            usedIndexes.add(index);
        }
    }

    return result;
}

    public Vocabulary findByWordAndMeaning(String word, String meaning) {
        return repository.findByWordAndMeaning(word, meaning)
            .orElseThrow(() -> new ResourceNotFoundException("Vocabulary not found"));
    }
}
