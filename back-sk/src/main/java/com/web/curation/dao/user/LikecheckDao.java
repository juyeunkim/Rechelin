package com.web.curation.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.curation.model.user.Likecheck;
import com.web.curation.model.user.Review;
import com.web.curation.model.user.User;

public interface LikecheckDao extends JpaRepository<Likecheck, Long> {
	List<Likecheck> findAllByReviewAndUser(Review review, User user);
}
