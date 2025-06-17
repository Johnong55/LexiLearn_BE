package com.chummy_backend.serverside.Model.general;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Tự động tạo getter/setter/toString/equals/hashCode
@NoArgsConstructor // Constructor không có tham số
@AllArgsConstructor // Constructor đầy đủ tham số
@Builder // Tùy chọn để dùng pattern builder
public class Users {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String displayName;
    @Column(unique=  true)
    private String email;
    private LocalDateTime createAt;
    private String password;
    @OneToMany(mappedBy= "user")
    private List<Class_Partipation> clasList;


}
