package com.example.server.service;

import com.example.server.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;
}
