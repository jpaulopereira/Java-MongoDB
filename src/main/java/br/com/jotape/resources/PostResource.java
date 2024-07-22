package br.com.jotape.resources;

import br.com.jotape.domain.Post;
import br.com.jotape.repository.util.Utils;
import br.com.jotape.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
        text = Utils.decodeParam(text);
        List<Post> list = postService.findByTitle(text);
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/title-search-param")
    public ResponseEntity<List<Post>> findByTextParam(@RequestParam(value = "text", defaultValue = "") String text) {
        text = Utils.decodeParam(text);
        List<Post> list = postService.findByTitleParam(text);
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/title-searchfull")
    public ResponseEntity<List<Post>>
    fullSearch(@RequestParam(value = "text", defaultValue = "") String text,
               @RequestParam(value = "minDate", defaultValue = "") String minDate,
               @RequestParam(value = "maxDate", defaultValue = "") String maxDate) {
        text = Utils.decodeParam(text);
        Date min = Utils.convertData(minDate, new Date(0L));
        Date max = Utils.convertData(maxDate, new Date());
        List<Post> list = postService.fullSearch(text, min, max);
        return ResponseEntity.ok().body(list);
    }
}
