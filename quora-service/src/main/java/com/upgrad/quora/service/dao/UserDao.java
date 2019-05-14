package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Transactional(propagation = Propagation.REQUIRED)
  public UserEntity createUser(UserEntity userEntity) {
    entityManager.persist(userEntity);
    return userEntity;
  }

  public UserEntity getUser(final String userUuid){
    try {
      return entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter("uuid", userUuid)
              .getSingleResult();
    }catch(NoResultException ex) {
      return null;
    }
  }

  public UserEntity userByEmail(final String email) {
    try {
      return  entityManager.createNamedQuery("userByEmail", UserEntity.class).setParameter("email", email)
              .getSingleResult();
    }catch(NoResultException ex) {
      return null;
    }
  }

  public UserEntity userByUserName(final String userName) {
    try {
      return  entityManager.createNamedQuery("userByUserName", UserEntity.class).setParameter("userName", userName)
              .getSingleResult();
    }catch(NoResultException ex) {
      return null;
    }
  }

  public UserAuthTokenEntity createAuthToken(final UserAuthTokenEntity userAuthTokenEntity) {
    entityManager.persist(userAuthTokenEntity);
    return userAuthTokenEntity;
  }

  public void updateUserAuthToken(final UserAuthTokenEntity userAuthTokenEntity) {
    entityManager.merge(userAuthTokenEntity);
  }

  public UserAuthTokenEntity getAuthToken(final String accessToken) {
    try {
      return entityManager.createNamedQuery("userAuthTokenByAccessToken", UserAuthTokenEntity.class).setParameter("accessToken", accessToken).getSingleResult();
    } catch (NoResultException ex) {
      return null;
    }
  }

  public String deleteUser(final String userUuid) throws UserNotFoundException {

    UserEntity userEntity;
    try {
      userEntity = entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter("uuid", userUuid).getSingleResult();
    }
    catch(NoResultException nre) {
      userEntity = null;
    }

    if(userEntity == null) {
      throw new UserNotFoundException("USR-001", "User with entered uuid to be deleted does not exist");
    }
    else {
      entityManager.remove(userEntity);
      return userUuid;
    }
  }

  public UserEntity viewUserProfile(final String userUuid) {
    try {
      return entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter("uuid", userUuid).getSingleResult();
    } catch(NoResultException nre) {
      return null;
    }

  }
}
