package edu.icet.coursework.dto;

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
public class User {

    @Id
    private String userId;

    private String userType;
    private String fname;
    private String lname;
    private LocalDate dob;
    private String gender;
    private String address;
    private String contactNo;
    private String email;
    private String password;
    private String passwordConfirm;

}
