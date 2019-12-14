package engineer.jacob.spigotbanners.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("author")
public class AuthorController {
    @GetMapping(value = "/{id}/banner.png", produces = "text/plain")
    public String getBanner(@PathVariable int id) {
        return "WTF, author id: " + id;
    }
}
