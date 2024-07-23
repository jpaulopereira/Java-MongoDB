package br.com.jotape.service;

import br.com.jotape.repository.PostRepository;
import br.com.jotape.domain.Post;
import br.com.jotape.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Query;



import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    public Post findById(String id) {
        Optional<Post> obj = postRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public List<Post> findByTitle(String text) {
        return postRepository.findByTitleContainingIgnoreCase(text);
    }

    public List<Post> findByTitleParam(String text) {
        return postRepository.seachTitle(text);
    }

    public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
        maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
        return postRepository.fullSearch(text, minDate, maxDate);
    }


    public Post findByTitleAndBody(String title, String body, String name) {

        Query query = new Query();

        if (title != null && !title.isEmpty()) {
            query.addCriteria(Criteria.where("title").is(title));
        }
        if (body != null && !body.isEmpty()) {
            query.addCriteria(Criteria.where("body").is(body));
        }

        if (name != null && !name.isEmpty()) {
            query.addCriteria(Criteria.where("author.name").is(name));
        }

        return mongoTemplate.findOne(query, Post.class);
    }
}
