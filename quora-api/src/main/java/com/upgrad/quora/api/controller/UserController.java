package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.SignupUserRequest;
import com.upgrad.quora.api.model.SignupUserResponse;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.business.UserBusinessService;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserBusinessService userBusinessService;

  @GetMapping
  public String getUser() {
    return "Hello User";
  }


  @RequestMapping(method = RequestMethod.POST, path = "signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SignupUserResponse> signup(@RequestBody SignupUserRequest signupUserRequest) throws SignUpRestrictedException {


    final UserEntity userEntity = new UserEntity();

    userEntity.setUuid(UUID.randomUUID().toString());
    userEntity.setFirstname(signupUserRequest.getFirstName());
    userEntity.setLastname(signupUserRequest.getLastName());
    userEntity.setUsername(signupUserRequest.getUserName());
    userEntity.setEmail(signupUserRequest.getEmailAddress());
    userEntity.setPassword(signupUserRequest.getPassword());
    userEntity.setCountry(signupUserRequest.getCountry());
    userEntity.setAboutme(signupUserRequest.getAboutMe());
    userEntity.setDob(signupUserRequest.getDob());
    userEntity.setRole("nonadmin");
    userEntity.setContactnumber(signupUserRequest.getContactNumber());

    final UserEntity createdUserEntity = userBusinessService.signup(userEntity);

    SignupUserResponse userResponse = new SignupUserResponse().id(createdUserEntity.getUuid()).status("USER REGISTERED SUCCESSFULLY");

    return new ResponseEntity<SignupUserResponse>(userResponse, HttpStatus.CREATED);

  }
}
