package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;


@Repository
public class UserDao {

  @PersistenceContext
  private EntityManager entityManager;

  public UserEntity getUserById(final String uuid) {
    try {
      return entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter("uuid", uuid).getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }

  public UserEntity createUser(UserEntity userEntity) {
    try {
      entityManager.persist(userEntity);
      return userEntity;
    } catch (Exception e) {
      return null;
    }
  }

  public UserEntity getUserByUsername(final String username) {
    try {
      return entityManager.createNamedQuery("userByUsername", UserEntity.class).setParameter("username", username).getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }

  public UserAuthEntity createAuth(final UserAuthEntity userAuthEntity) {
    entityManager.persist(userAuthEntity);
    return userAuthEntity;
  }

  public Boolean isUsernameExists(final String username) {
    try {
      UserEntity singleResult = entityManager.createNamedQuery("userByUsername", UserEntity.class).setParameter("username", username).getSingleResult();
      return true;
    } catch (NoResultException nre) {
      return false;
    }
  }

  public Boolean isEmailExists(final String email) {
    try {
      UserEntity singleResult = entityManager.createNamedQuery("userByEmail", UserEntity.class).setParameter("email", email).getSingleResult();
      return true;
    } catch (NoResultException nre) {
      return false;
    }
  }
}
