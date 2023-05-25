package com.example.demo.entity;

import com.example.demo.enumeration.UserAuthority;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority {
    @Id
    @Enumerated(EnumType.STRING)
    private UserAuthority role;
}
