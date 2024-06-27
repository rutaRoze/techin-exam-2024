package lt.techin.exam.service;

import jakarta.persistence.EntityNotFoundException;
import lt.techin.exam.dto.request.CommentRequest;
import lt.techin.exam.dto.response.CommentResponse;
import lt.techin.exam.mapper.CommentMapper;
import lt.techin.exam.persistance.AdvertRepository;
import lt.techin.exam.persistance.CommentRepository;
import lt.techin.exam.persistance.UserRepository;
import lt.techin.exam.persistance.entity.Advert;
import lt.techin.exam.persistance.entity.Comment;
import lt.techin.exam.persistance.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private AdvertRepository advertRepository;
    private CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository,
                          AdvertRepository advertRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.advertRepository = advertRepository;
        this.commentMapper = commentMapper;
    }

    public CommentResponse saveComment(CommentRequest commentRequest) {
        User user = userRepository.findById(commentRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found by ID: " + commentRequest.getUserId()));

        Advert advert = advertRepository.findById(commentRequest.getAdvertId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found by ID: " + commentRequest.getAdvertId()));

        Comment commentToSave = commentMapper.requestToComment(commentRequest, user, advert);
        Comment savedComment = commentRepository.save(commentToSave);

        return commentMapper.commentToResponse(savedComment);
    }

    public List<CommentResponse> findCommentsByAdvert(Long id) {
        return commentRepository.findByAdvertId(id).stream()
                .map(comment -> commentMapper.commentToResponse(comment))
                .collect(Collectors.toList());
    }

    public List<CommentResponse> findCommentsByUser(UUID id) {
        return commentRepository.findByUserId(id).stream()
                .map(comment -> commentMapper.commentToResponse(comment))
                .collect(Collectors.toList());
    }

    public void deleteCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found by ID: " + id));

        commentRepository.delete(comment);
    }
}