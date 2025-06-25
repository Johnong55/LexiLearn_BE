package com.chummy_backend.serverside.Model.examination;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class question {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String content;
    @ManyToOne(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private Vocabulary vocabulary;
    private List<String> choice;

    
}
