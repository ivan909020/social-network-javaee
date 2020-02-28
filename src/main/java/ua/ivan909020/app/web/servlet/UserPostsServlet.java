package ua.ivan909020.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.ivan909020.app.domain.entities.User;
import ua.ivan909020.app.domain.models.Pager;
import ua.ivan909020.app.service.PostService;
import ua.ivan909020.app.service.UserService;
import ua.ivan909020.app.service.impl.PostServiceImpl;
import ua.ivan909020.app.service.impl.UserServiceImpl;
import ua.ivan909020.app.util.NumberUtils;

@WebServlet("/user/posts/*")
public class UserPostsServlet extends HttpServlet {

	private final UserService userService = UserServiceImpl.getInstance();
	private final PostService postService = PostServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer userId = NumberUtils.parseIntFromPath(req.getPathInfo());
		int pageNumber = NumberUtils.parseInt(req.getParameter("page"));
		int pageSize = NumberUtils.parseInt(req.getParameter("size"));

		User user = userService.findById(userId);
		if (user != null) {
			String contextPath = "user/posts/" + userId + "?";
			int userPostsCount = postService.countByUserId(userId);
			Pager pager = Pager.build(userPostsCount, pageNumber, pageSize, contextPath);
			user.setPosts(postService.findByUserId(userId, pager.getPageNumber(), pager.getPageSize()));
			req.setAttribute("pager", pager);
			req.setAttribute("user", user);
			req.setAttribute("userPostsCount", userPostsCount);
			req.setAttribute("userFollowersCount", userService.countFollowersByUserId(userId));
			req.setAttribute("userFollowingCount", userService.countFollowingByUserId(userId));
		}
		req.setAttribute("template", "templates/profile.jsp");
		req.getRequestDispatcher("/WEB-INF/main.jsp").forward(req, resp);
	}

}
