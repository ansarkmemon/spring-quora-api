package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.business.UserBusinessService;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CommonController {

  @Autowired
  private UserBusinessService userBusinessService;

  @RequestMapping(method = RequestMethod.GET, path = "/userprofile/{userId}")
  public ResponseEntity<UserDetailsResponse> getUserById(@PathVariable("userId") final String userUuid, @RequestHeader("authorization") final String authorization)
          throws UserNotFoundException, AuthorizationFailedException {


    UserEntity userById = userBusinessService.getUserProfile(userUuid, authorization);

    UserDetailsResponse userDetailsResponse = new UserDetailsResponse();

    userDetailsResponse.setFirstName(userById.getFirstname());
    userDetailsResponse.setLastName(userById.getLastname());
    userDetailsResponse.setUserName(userById.getUsername());
    userDetailsResponse.setEmailAddress(userById.getEmail());
    userDetailsResponse.setCountry(userById.getCountry());
    userDetailsResponse.setAboutMe(userById.getAboutme());
    userDetailsResponse.setContactNumber(userById.getContactnumber());
    userDetailsResponse.setDob(userById.getDob());

    return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK) ;

  }

}
