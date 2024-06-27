package lt.techin.exam.mapper;

import lt.techin.exam.dto.request.CommentRequest;
import lt.techin.exam.dto.response.CommentResponse;
import lt.techin.exam.persistance.entity.Advert;
import lt.techin.exam.persistance.entity.Comment;
import lt.techin.exam.persistance.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comment requestToComment(CommentRequest commentRequest, User user, Advert advert) {
        return Comment.builder()
                .user(user)
                .advert(advert)
                .userComment(commentRequest.getComment())
                .build();
    }

    public CommentResponse commentToResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .userName(comment.getUser().getName())
                .advertTitle(comment.getAdvert().getTitle())
                .comment(comment.getUserComment())
                .build();
    }
}