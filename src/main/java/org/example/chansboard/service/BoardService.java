package org.example.chansboard.service;

import lombok.RequiredArgsConstructor;
import org.example.chansboard.domain.Board;
import org.example.chansboard.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    //전체 게시글 페이징 보기
    @Transactional(readOnly = true)
    public Page<Board> findPaginated(Pageable pageable) {
        Pageable sortedByDescId = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
        return boardRepository.findAll(sortedByDescId);
    }

    //id에 해당하는 게시글 보기
    @Transactional(readOnly = true)
    public Board findById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    //게시글 추가
    @Transactional
    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }

    //게시글 삭제
    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }




}
