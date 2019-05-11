package com.upgrad.quora.service.entity;

import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "userByUsername", query = "select u from UserEntity u where u.username = :username"),
        @NamedQuery(name = "userByUuid", query = "select u from UserEntity u where u.uuid = :uuid"),
        @NamedQuery(name = "userByEmail", query = "select u from UserEntity u where u.email = :email")
})


public class UserEntity implements Serializable {

  private static final long serialVersionUID = 7850692963715351424L;

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "UUID")
  @NotNull
  private String uuid;

  @Column(name = "FIRSTNAME")
  @NotNull
  @Size(max = 30)
  private String firstname;

  @Column(name = "LASTNAME")
  @NotNull
  @Size(max = 30)
  private String lastname;

  @Column(name = "USERNAME")
  @NotNull
  @Size(max = 30)
  private String username;

  @Column(name = "EMAIL")
  @NotNull
  @Size(max = 50)
  private String email;

  @ToStringExclude
  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "SALT")
  @NotNull
  @Size(max = 200)
  @ToStringExclude
  private String salt;

  @Column(name = "COUNTRY")
  @NotNull
  @Size(max = 30)
  private String country;

  @Column(name = "ABOUTME")
  @Size(max = 50)
  private String aboutme;

  @Column(name = "DOB")
  @Size(max = 30)
  private String dob;

  @Column(name = "ROLE")
  @Size(max = 30)
  private String role;

  @Column(name = "CONTACTNUMBER")
  @Size(max = 30)
  private String contactnumber;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getAboutme() {
    return aboutme;
  }

  public void setAboutme(String aboutme) {
    this.aboutme = aboutme;
  }

  public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getContactnumber() {
    return contactnumber;
  }

  public void setContactnumber(String contactnumber) {
    this.contactnumber = contactnumber;
  }
}
