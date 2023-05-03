package com.kitaplik.libraryservice.service;

import com.kitaplik.libraryservice.client.BookServiceClient;

import com.kitaplik.libraryservice.exception.LibraryNotFoundException;


import com.kitaplik.libraryservice.dto.AddBookRequest;
import com.kitaplik.libraryservice.dto.LibraryDto;
import com.kitaplik.libraryservice.model.Library;
import com.kitaplik.libraryservice.repository.LibraryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final BookServiceClient bookServiceClient;

    public LibraryService(LibraryRepository libraryRepository, BookServiceClient bookServiceClient) {
        this.libraryRepository = libraryRepository;
        this.bookServiceClient = bookServiceClient;
    }
    public LibraryDto getAllBooksLibraryById(String id){
        Library library= libraryRepository.findById(id)
                .orElseThrow(()-> new LibraryNotFoundException("Library could not found by id" + id));

        LibraryDto libraryDto = new LibraryDto(library.getId(),
                library.getUserBook()
                        .stream()
                        .map(bookServiceClient::getBookById) /**feign client ile book service e geçip book'un detayını çekiyor*/
                        .map(ResponseEntity::getBody)
                        .collect(Collectors.toList()));
        return libraryDto;
    }

    public LibraryDto createLibrary(){
        Library newLibrary= libraryRepository.save(new Library());
        return new LibraryDto(newLibrary.getId());
    }

    public void addBookToLibrary(AddBookRequest request){
        String bookId = bookServiceClient.getBookByIsbn(request.getIsbn()).getBody().getBookId();
        Library library= libraryRepository.findById(request.getId())
                .orElseThrow(()-> new LibraryNotFoundException("Library could not found by id"));
        library.getUserBook()
                .add(bookId);
        libraryRepository.save(library);
    }
}
