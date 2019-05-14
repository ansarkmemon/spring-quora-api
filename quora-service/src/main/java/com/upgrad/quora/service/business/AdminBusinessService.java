package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AdminAuthorFailedException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;

@Service
public class AdminBusinessService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public String deleteUser(final String userid , final String accessToken) throws AuthorizationFailedException,AdminAuthorFailedException, UserNotFoundException {

        UserEntity userEntity = getUser(userid,accessToken);
        UserAuthEntity userAuthEntity = userDao.getUserAuthByToken(accessToken);

        if(userAuthEntity.getUserId().getRole().equals("admin")){
            return userDao.deleteUser(userid);
        }
        else
        {
            throw new AdminAuthorFailedException("ATHR-003","Unauthorized Access, Entered user is not an admin");
        }
    }

    @Transactional
    public UserEntity getUser(final String id , final String authorizedToken) throws AuthorizationFailedException,AdminAuthorFailedException, UserNotFoundException {

        UserAuthEntity userAuth =  userDao.getUserAuthByToken(authorizedToken);


        if(userAuth == null)
        {
            throw new AdminAuthorFailedException("ATHR-001","User has not signed in");
        }
        final ZonedDateTime signOutUserTime = userAuth.getLogoutAt();

        if(signOutUserTime!=null && userAuth!=null)
        {
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to get user details");
        }
        UserEntity user = userDao.getUserById(id);

        if(user!=null)
        {
            return user;
        }
        else {
            throw new UserNotFoundException("USR-001", "User with entered uuid does not exist .");
        }
    }
}
