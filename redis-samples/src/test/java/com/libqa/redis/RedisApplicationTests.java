package com.libqa.redis;

import com.libqa.redis.entity.Point;
import com.libqa.redis.repository.PointRedisRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;


/**
 * @author edell.lee
 * @version 2020-06-23 17:34
 * @implNote
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisApplicationTests {
    @Autowired
    private PointRedisRepository pointRedisRepository;

    @After
    public void tearDown() throws Exception {
        pointRedisRepository.deleteAll();
    }

    @Test
    public void 기본_등록_조회기능() {
        String id = "edell";
        LocalDateTime refreshTime = LocalDateTime.of(2020, 5, 30, 0, 0);

        Point point = Point.builder()
                .id(id)
                .amount(100000L)
                .refreshTime(refreshTime).build();

        pointRedisRepository.save(point);
        Point savedPoint = pointRedisRepository.findById(id).get();

        assertEquals(savedPoint.getAmount(), 100000L);
        assertEquals(savedPoint.getRefreshTime(), refreshTime);

    }
}
