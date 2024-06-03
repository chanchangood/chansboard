package org.example.chansboard.controller;

import lombok.RequiredArgsConstructor;
import org.example.chansboard.domain.Board;
import org.example.chansboard.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public String boards(Model model, @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Board> boards = boardService.findPaginated(pageable);
        model.addAttribute("boards", boards);
        model.addAttribute("currentPage", page);
        return "list";
    }

    @GetMapping("/view/{id}")
    public String boardView(@PathVariable Long id, Model model) {
        Board board = boardService.findById(id);
        model.addAttribute("board", board);

        return "view";
    }

    @GetMapping("/writeForm")
    public String writeForm(Model model) {
        model.addAttribute("board", new Board());
        return "writeForm";
    }

    @PostMapping("/writeForm")
    public String writeBoard(@ModelAttribute Board board, RedirectAttributes redirectAttributes) {
        boardService.saveBoard(board);

        redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 작성되었습니다.");
        return "redirect:/list";
    }

    @GetMapping("/deleteForm/{id}")
    public String deleteForm(@PathVariable Long id, Model model) {
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "deleteForm";
    }

    public boolean verifyPassword(@PathVariable Long id, @PathVariable String password) {
        Board board = boardService.findById(id);
        return password.equals(board.getPassword());
    }

    @PostMapping("/deleteForm")
    public String deleteBoard(@ModelAttribute Board board, Model model, RedirectAttributes redirectAttributes) {
        if (!verifyPassword(board.getId(), board.getPassword())) {
            model.addAttribute("message", "잘못된 비밀번호 입니다.");
            return "deleteForm";
        }
        boardService.deleteById(board.getId());
        redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 삭제되었습니다");
        return "redirect:/list";
    }

    @GetMapping("/updateForm/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "/updateForm";
    }

    @PostMapping("/updateForm")
    public String updateBoard(@ModelAttribute Board board, Model model) {
        if (!verifyPassword(board.getId(), board.getPassword())) {
            model.addAttribute("message", "잘못된 비밀번호 입니다.");
            return "updateForm";
        }

        long id = board.getId();
        boardService.saveBoard(board);
        return "redirect:/view/" + id;
    }


}
