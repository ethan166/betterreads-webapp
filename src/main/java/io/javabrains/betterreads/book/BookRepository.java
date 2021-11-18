package io.javabrains.betterreads.book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CassandraRepository<Book, String> {
    @Query("select * from book_by_id where author_names Contains :authorName ALLOW FILTERING")
    public Optional<List<Book>> getBookByAuthor(@Param("authorName") String authorId);
}
