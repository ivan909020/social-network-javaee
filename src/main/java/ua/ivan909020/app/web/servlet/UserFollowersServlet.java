package ua.ivan909020.app.web.servlet;

import ua.ivan909020.app.domain.entities.User;
import ua.ivan909020.app.domain.models.Pager;
import ua.ivan909020.app.exception.EntityNotFoundException;
import ua.ivan909020.app.service.PostService;
import ua.ivan909020.app.service.UserService;
import ua.ivan909020.app.service.impl.PostServiceImpl;
import ua.ivan909020.app.service.impl.UserServiceImpl;
import ua.ivan909020.app.util.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/followers/*")
public class UserFollowersServlet extends HttpServlet {

	private final UserService userService = UserServiceImpl.getInstance();
	private final PostService postService = PostServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer userId = NumberUtils.parseIntFromPath(req.getPathInfo());
		int pageNumber = NumberUtils.parseInt(req.getParameter("page"));
		int pageSize = NumberUtils.parseInt(req.getParameter("size"));

		User user = userService.findById(userId);
		if (user == null) {
			throw new EntityNotFoundException("User with id " + userId + " not found");
		}
		String contextPath = "user/followers/" + userId + "?";
		int userFollowersCount = userService.countFollowersByUserId(userId);
		Pager pager = Pager.build(userFollowersCount, pageNumber, pageSize, contextPath);
		user.setFollowers(userService.findFollowersByUserId(userId, pager.getPageNumber(), pager.getPageSize()));
		req.setAttribute("pager", pager);
		req.setAttribute("user", user);
		req.setAttribute("userPostsCount", postService.countByUserId(userId));
		req.setAttribute("userFollowersCount", userFollowersCount);
		req.setAttribute("userFollowingCount", userService.countFollowingByUserId(userId));
		User viewer = (User) req.getSession().getAttribute(User.AUTHENTICATED_USER);
		if (viewer != null && !user.getId().equals(viewer.getId())) {
			req.setAttribute("hasUserFollower", userService.hasUserFollower(userId, viewer.getId()));
		}
		req.setAttribute("template", "templates/profile.jsp");
		req.getRequestDispatcher("/WEB-INF/main.jsp").forward(req, resp);
	}

}
