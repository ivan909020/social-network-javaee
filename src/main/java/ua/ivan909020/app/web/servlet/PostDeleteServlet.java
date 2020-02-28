package ua.ivan909020.app.web.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.ivan909020.app.domain.entities.Post;
import ua.ivan909020.app.domain.entities.User;
import ua.ivan909020.app.service.PostService;
import ua.ivan909020.app.service.impl.PostServiceImpl;
import ua.ivan909020.app.util.NumberUtils;

@WebServlet("/post/delete")
public class PostDeleteServlet extends HttpServlet {

	private final PostService postService = PostServiceImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Integer postId = NumberUtils.parseInt(req.getParameter("id"));

		Post post = postService.findById(postId);
		User user = (User) req.getSession().getAttribute(User.AUTHENTICATED_USER);
		if (post != null && user != null && post.getUserId().equals(user.getId())) {
			postService.deleteById(postId);
		}
		resp.sendRedirect(req.getHeader("referer"));
	}

}
