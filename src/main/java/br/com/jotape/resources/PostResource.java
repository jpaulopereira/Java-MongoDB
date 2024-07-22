package br.com.jotape.resources;

import br.com.jotape.domain.Post;
import br.com.jotape.domain.User;
import br.com.jotape.repository.util.Url;
import br.com.jotape.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Post> findById(@PathVariable String id) {
        Post obj = postService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value = "/title-search")
    public ResponseEntity<List<Post>> findByText(@RequestParam(value = "text", defaultValue = "") String text) {
      text = Url.decodeParam(text);
      List<Post> list = postService.findByTitle(text);
      return ResponseEntity.ok().body(list);
    }
}
