package ua.ivan909020.app.web.servlet;

import ua.ivan909020.app.domain.entities.User;
import ua.ivan909020.app.exception.EntityNotFoundException;
import ua.ivan909020.app.service.UserService;
import ua.ivan909020.app.service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/settings/update")
public class SettingsUpdateServlet extends HttpServlet {

	private final UserService userService = UserServiceImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String password = req.getParameter("password");
		String information = req.getParameter("information");

		User user = (User) req.getSession().getAttribute(User.AUTHENTICATED_USER);
		if (user == null) {
			throw new EntityNotFoundException("Authenticated user not found");
		}
		if (password != null && !password.isEmpty()) {
			user.setPassword(password);
		}
		user.setInformation(information);
		userService.update(user);
		resp.sendRedirect(req.getContextPath() + "/settings");
	}

}
