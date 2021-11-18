package io.javabrains.betterreads.author;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import io.javabrains.betterreads.book.Book;
import io.javabrains.betterreads.book.BookRepository;

@Controller
public class AuthorController {

    private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";
    @Autowired
    BookRepository bookRepository;
    
    @GetMapping(value = "/authors/{authorName}")
    public String getBookByAuthor(@PathVariable String authorName, Model model) {
    	Optional<List<Book>> optionalBooks = bookRepository.getBookByAuthor(authorName);
       //Optional<Book> optionalBook = bookRepository.findById("OL10013972W");
        if(optionalBooks.isPresent()) {
            List<Book> books = optionalBooks.get();
            for(int i = 0; i < books.size(); i++) {
                if (books.get(i).getCoverIds() != null && books.get(i).getCoverIds().size() > 0) {
                    books.get(i).setCoverUrl(COVER_IMAGE_ROOT + books.get(i).getCoverIds().get(0) + "-L.jpg");
                } 
            }
            // work around to get author name
            // String authorName = books.get(0).getAuthorNames().get(0);
          //  model.addAttribute("coverImage", coverImageUrl);         
            model.addAttribute("books", books); 
            model.addAttribute("authorName", authorName);
            return "author";
        }
        return "author-not-found";
    }
}
