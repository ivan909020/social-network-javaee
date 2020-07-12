package ua.ivan909020.app.web.servlet;

import ua.ivan909020.app.domain.entities.User;
import ua.ivan909020.app.exception.EntityNotFoundException;
import ua.ivan909020.app.service.UserService;
import ua.ivan909020.app.service.impl.UserServiceImpl;
import ua.ivan909020.app.util.NumberUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/follower/create")
public class UserFollowerCreateServlet extends HttpServlet {

	private final UserService userService = UserServiceImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Integer userId = NumberUtils.parseInt(req.getParameter("id"));

		User follower = (User) req.getSession().getAttribute(User.AUTHENTICATED_USER);
		if (follower == null) {
			throw new EntityNotFoundException("Authenticated user not found");
		}
		userService.createUserFollower(userId, follower.getId());
		resp.sendRedirect(req.getHeader("referer"));
	}

}
