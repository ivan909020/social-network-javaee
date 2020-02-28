package ua.ivan909020.app.web.servlet;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.ivan909020.app.domain.entities.Post;
import ua.ivan909020.app.domain.entities.User;
import ua.ivan909020.app.service.PostService;
import ua.ivan909020.app.service.impl.PostServiceImpl;

@WebServlet("/post/create")
public class PostCreateServlet extends HttpServlet {

	private final PostService postService = PostServiceImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String title = req.getParameter("title");
		String description = req.getParameter("description");

		User user = (User) req.getSession().getAttribute(User.AUTHENTICATED_USER);
		if (user != null) {
			Post post = new Post();
			post.setUserId(user.getId());
			post.setTitle(title);
			post.setDescription(description);
			post.setCreated(LocalDateTime.now());
			postService.create(post);
		}
		resp.sendRedirect(req.getContextPath() + "/user/posts/" + user.getId());
	}

}
