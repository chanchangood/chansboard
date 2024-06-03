package org.example.chansboard.repository;

import org.example.chansboard.domain.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BoardRepository extends CrudRepository<Board, Long>, PagingAndSortingRepository<Board, Long> {
}
