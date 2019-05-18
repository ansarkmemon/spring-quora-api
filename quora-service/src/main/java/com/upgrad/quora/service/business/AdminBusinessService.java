package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AdminDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AdminBusinessService {

    @Autowired
    private UserBusinessService userBusinessService;

    @Autowired
    private AdminDao adminDao;

    private boolean confirmAdmin(final String accessToken) throws AuthorizationFailedException {
        UserAuthEntity userByToken = userBusinessService.getUserByToken(accessToken);

        if(userByToken.getUserId().getRole().equals("admin"))
            return true;
        else
            throw new AuthorizationFailedException("ATHR-003", "Unauthorized Access, Entered user is not an admin");
    }

    @Transactional
    public String deleteUser(String accessToken, String userId) throws AuthorizationFailedException, UserNotFoundException {

        UserEntity userById = userBusinessService.getUserById(userId);
        if(this.confirmAdmin(accessToken)) {
            adminDao.deleteUserByUuid(userId);
        }

        return userId;
    }
}