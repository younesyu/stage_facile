package com.stage_facile.stage_facile.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stage_facile.stage_facile.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{}
