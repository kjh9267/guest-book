package me.jun.guestbook.comment.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.comment.application.exception.CommentNotFoundException;
import me.jun.guestbook.comment.application.exception.CommentWriterMismatchException;
import me.jun.guestbook.comment.domain.Comment;
import me.jun.guestbook.comment.domain.CommentRepository;
import me.jun.guestbook.comment.presentation.dto.CommentCreateRequest;
import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import me.jun.guestbook.comment.presentation.dto.CommentUpdateRequest;
import me.jun.guestbook.comment.presentation.dto.PagedCommentsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponse createComment(CommentCreateRequest request, Long writerId) {
        Comment comment = request.toEntity();
        comment.setWriterId(writerId);
        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.from(savedComment);
    }

    public CommentResponse retrieveComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        return CommentResponse.from(comment);
    }

    public CommentResponse updateComment(CommentUpdateRequest request, Long writerId) {
        Comment comment = commentRepository.findById(request.getId())
                .orElseThrow(CommentNotFoundException::new);

        if (!isWriter(writerId, comment)) {
            throw new CommentWriterMismatchException();
        }

        comment.setContent(request.getContent());
        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.from(savedComment);
    }

    public Long deleteComment(Long id, Long writerId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        if (!isWriter(writerId, comment)) {
            throw new CommentWriterMismatchException();
        }

        commentRepository.deleteById(id);
        return id;
    }

    public void deleteCommentByPostId(Long postId) {
        commentRepository.deleteByPostId(postId);
    }

    public void deleteCommentByWriterId(Long writerId) {
        commentRepository.deleteByWriterId(writerId);
    }

    public PagedCommentsResponse queryCommentsByPostId(Long postId, PageRequest pageRequest) {
        Page<Comment> comments = commentRepository.findAllByPostId(postId, pageRequest);
        return PagedCommentsResponse.from(comments);
    }

    private boolean isWriter(Long writerId, Comment comment) {
        return writerId.equals(comment.getWriterId());
    }
}
