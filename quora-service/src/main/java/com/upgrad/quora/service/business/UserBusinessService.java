package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserBusinessService {

  @Autowired
  private UserDao userDao;

  @Autowired
  private PasswordCryptographyProvider cryptographyProvider;

  @Transactional
  public UserEntity signup(UserEntity userEntity) {
    String[] encryptedText = cryptographyProvider.encrypt(userEntity.getPassword());
    userEntity.setSalt(encryptedText[0]);
    userEntity.setPassword(encryptedText[1]);

    return userDao.createUser(userEntity);
  }
}
