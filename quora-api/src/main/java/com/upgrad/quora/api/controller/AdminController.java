package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDeleteResponse;
import com.upgrad.quora.service.business.AdminBusinessService;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminBusinessService adminBusinessService;

    @RequestMapping(method = RequestMethod.DELETE, path = "user/{userId}")
    public ResponseEntity<UserDeleteResponse> deleteUser(
            @PathVariable("userId") String userId,
            @RequestHeader("authorization") String accessToken
    ) throws AuthorizationFailedException, UserNotFoundException {

        String deletedUserId = adminBusinessService.deleteUser(accessToken, userId);
        UserDeleteResponse userDeleteResponse = new UserDeleteResponse().id(deletedUserId).status("USER SUCCESSFULLY DELETED");
        return new ResponseEntity<UserDeleteResponse>(userDeleteResponse, HttpStatus.OK);
    }

}