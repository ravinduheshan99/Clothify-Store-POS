package edu.icet.coursework.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity {

    @Id
    private String userId;

    private String userType;
    private String fname;
    private String lname;
    private LocalDate dob;
    private Integer age;
    private String gender;
    private String adress;
    private String contactNo;
    private String email;
    private String password;
}
