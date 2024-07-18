package br.com.jotape.service;

import br.com.jotape.Repository.PostRepository;
import br.com.jotape.Repository.UserRepository;
import br.com.jotape.domain.Post;
import br.com.jotape.domain.User;
import br.com.jotape.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post findById(String id) {
        Optional<Post> obj = postRepository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado"));
    }
}
