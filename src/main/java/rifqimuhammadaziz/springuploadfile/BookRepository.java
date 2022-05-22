package rifqimuhammadaziz.springuploadfile;

import org.springframework.data.jpa.repository.JpaRepository;
import rifqimuhammadaziz.springuploadfile.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
