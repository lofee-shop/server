package com.example.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.entity.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
