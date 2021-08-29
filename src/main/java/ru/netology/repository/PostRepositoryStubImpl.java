package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryStubImpl implements PostRepository {
    private final AtomicLong id = new AtomicLong(1);
    private final Map<Long, Post> concurrentHashMap = new ConcurrentHashMap<>();

    public List<Post> all() {
        return Collections.emptyList();
    }

    public Optional<Post> getById(long id) {
        return Optional.empty();
    }

    public synchronized Post save(Post post) {
        if (post.getId() == 0) {
            long newId = id.incrementAndGet();
            concurrentHashMap.put(newId, post);
        } else {
            if (!concurrentHashMap.containsKey(post.getId())) {
                concurrentHashMap.put(post.getId(), post);
            }
            concurrentHashMap.replace(post.getId(), post);
        }
        return post;
    }

    public void removeById(long id) {
        concurrentHashMap.remove(id);
    }
}