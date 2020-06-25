package com.libqa.redis.repository;

import com.libqa.redis.entity.Point;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author edell.lee
 * @version 2020-06-23 17:33
 * @implNote
 */
@Repository
public interface PointRedisRepository extends CrudRepository<Point, String> {
}
