package rifqimuhammadaziz.springuploadfile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rifqimuhammadaziz.springuploadfile.dto.ResponseData;
import rifqimuhammadaziz.springuploadfile.entity.Book;
import rifqimuhammadaziz.springuploadfile.service.BookService;
import rifqimuhammadaziz.springuploadfile.utility.CSVHelper;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        ResponseData response = new ResponseData();
        try {
            List<Book> books = bookService.findAll();
            response.setStatus(true);
            response.getMessages().add("Success get all books");
            response.setPayload(books);
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            response.setStatus(false);
            response.getMessages().add("Error get books: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        ResponseData response = new ResponseData();
        if (!CSVHelper.isCSVFormat(file)) {
            response.setStatus(false);
            response.getMessages().add("Please upload CSV file");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            List<Book> books = bookService.save(file);
            response.setStatus(true);
            response.getMessages().add("Successfully upload file: " + file.getOriginalFilename());
            response.setPayload(books);
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            response.setStatus(false);
            response.getMessages().add("Error upload file: " + file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
        }
    }
}
