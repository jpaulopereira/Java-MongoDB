package br.com.jotape.config;

import br.com.jotape.Repository.PostRepository;
import br.com.jotape.Repository.UserRepository;
import br.com.jotape.domain.Post;
import br.com.jotape.domain.User;
import br.com.jotape.dto.AuthorDTO;
import br.com.jotape.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        userRepository.deleteAll();
        postRepository.deleteAll();

        User joao = new User(null, "João Paulo", "jotape@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");
        userRepository.saveAll(Arrays.asList(joao, alex, bob));

        Post postOne = new Post(null,
                sdf.parse("18/07/2024"), "Partiu viagem", "Vou viajar. Abraços!",
                new AuthorDTO(joao));

        Post postTwo= new Post(null,
                sdf.parse("18/07/2024"), "Partiu viagem", "Vou viajar. Abraços!",
                new AuthorDTO(joao));

        CommentDTO commentOne = new CommentDTO("Boa Viagem!",  sdf.parse("18/07/2024"), new AuthorDTO(bob));
        CommentDTO commentTwo = new CommentDTO("Boa Viagem mano!",  sdf.parse("18/07/2024"), new AuthorDTO(alex));

        postOne.getComment().addAll(Arrays.asList(commentOne, commentTwo));

        postRepository.saveAll(Arrays.asList(postOne, postTwo));

        joao.getPosts().addAll(Arrays.asList(postOne, postTwo));
        userRepository.save(joao);
    }
}
