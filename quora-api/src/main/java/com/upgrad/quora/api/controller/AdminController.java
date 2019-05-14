package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.AdminBusinessService;
import com.upgrad.quora.service.exception.AdminAuthorFailedException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class AdminController {

    @Autowired
    private AdminBusinessService adminBusinessService;

    @RequestMapping(method = RequestMethod.DELETE , path = "/admin/user/{userId}")
    public ResponseEntity <UserDeleteResponse> deleteUser(@PathVariable("userId") final String userid,
                                                          @RequestHeader("authorization") final String authorization) throws AdminAuthorFailedException,AuthorizationFailedException, UserNotFoundException {

        String [] bearerToken = authorization.split("Bearer ");

        String uuid = adminBusinessService.deleteUser(userid ,bearerToken[1]);
        UserDeleteResponse userDeleteResponse=new UserDeleteResponse().id(uuid).status("USER SUCCESSFULLY DELETED");

        return new ResponseEntity<UserDeleteResponse>(userDeleteResponse,HttpStatus.OK);
    }
}
