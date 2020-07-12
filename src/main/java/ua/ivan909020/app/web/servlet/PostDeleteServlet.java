package ua.ivan909020.app.web.servlet;

import ua.ivan909020.app.domain.entities.Post;
import ua.ivan909020.app.domain.entities.User;
import ua.ivan909020.app.exception.EntityNotFoundException;
import ua.ivan909020.app.exception.ValidationException;
import ua.ivan909020.app.service.PostService;
import ua.ivan909020.app.service.impl.PostServiceImpl;
import ua.ivan909020.app.util.NumberUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/post/delete")
public class PostDeleteServlet extends HttpServlet {

	private final PostService postService = PostServiceImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Integer postId = NumberUtils.parseInt(req.getParameter("id"));

		Post post = postService.findById(postId);
		User user = (User) req.getSession().getAttribute(User.AUTHENTICATED_USER);
		if (post == null) {
			throw new EntityNotFoundException("Post with id " + postId + " not found");
		}
		if (user == null) {
			throw new EntityNotFoundException("Authenticated user not found");
		}
		if (!post.getUserId().equals(user.getId())) {
			throw new ValidationException("Post with id " + postId + " not belongs user with id " + user.getId());
		}
		postService.deleteById(postId);
		resp.sendRedirect(req.getHeader("referer"));
	}

}
