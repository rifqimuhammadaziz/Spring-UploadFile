package rifqimuhammadaziz.springuploadfile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rifqimuhammadaziz.springuploadfile.BookRepository;
import rifqimuhammadaziz.springuploadfile.entity.Book;
import rifqimuhammadaziz.springuploadfile.utility.CSVHelper;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service("bookService")
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> save(MultipartFile file) {
        try {
            List<Book> books = CSVHelper.csvToBooks(file.getInputStream());
            return bookRepository.saveAll(books);
        } catch (IOException exception) {
            throw new RuntimeException("Fail to store CSV data: " + exception.getMessage());
        }
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
