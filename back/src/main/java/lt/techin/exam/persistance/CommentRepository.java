package lt.techin.exam.persistance;

import lt.techin.exam.persistance.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByAdvertId(Long advertId);
    List<Comment> findByUserId(UUID userId);
}
