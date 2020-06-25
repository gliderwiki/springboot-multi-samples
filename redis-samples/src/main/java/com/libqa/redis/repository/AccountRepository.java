package com.libqa.redis.repository;

import com.libqa.redis.entity.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * @author edell.lee
 * @version 2020-06-24 12:24
 * @implNote
 */
public interface AccountRepository extends CrudRepository<Account, String> {

}
