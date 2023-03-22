package sg.edu.nus.iss.lovecalculator.repository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.lovecalculator.model.Result;

@Repository
public class ResultRepository {
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void saveResult(Result result) {
        redisTemplate.opsForValue().set(result.getId(), result.toJsonObject().toString());
    }

    public List<Result> getAllResults() throws IOException {
        Set<String> allKeys = redisTemplate.keys("*"); // get all redis keys into a java set
        List<Result> result = new LinkedList<>();

        for (String k : allKeys) {
            String json = (String) redisTemplate.opsForValue().get(k);

            Result r = Result.jsonStringToJavaObject(json);
            r.setId(k);
            result.add(r);
        }

        return result;
    }
}
