package ua.ivan909020.app.web.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.ivan909020.app.domain.entities.User;
import ua.ivan909020.app.service.UserService;
import ua.ivan909020.app.service.impl.UserServiceImpl;
import ua.ivan909020.app.util.NumberUtils;

@WebServlet("/user/follower/delete")
public class UserFollowerDeleteServlet extends HttpServlet {

	private final UserService userService = UserServiceImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Integer userId = NumberUtils.parseInt(req.getParameter("id"));

		User follower = (User) req.getSession().getAttribute(User.AUTHENTICATED_USER);
		if (follower != null) {
			userService.deleteUserFollower(userId, follower.getId());
		}
		resp.sendRedirect(req.getHeader("referer"));
	}

}
