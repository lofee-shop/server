package com.example.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.server.repository.BookmarkRepository;

@Service
public class BookmarkService {

	@Autowired
	private BookmarkRepository bookmarkRepository;
}
