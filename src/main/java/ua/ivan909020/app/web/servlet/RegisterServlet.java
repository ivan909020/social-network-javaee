package ua.ivan909020.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.ivan909020.app.domain.entities.User;
import ua.ivan909020.app.service.UserService;
import ua.ivan909020.app.service.impl.UserServiceImpl;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	private final UserService userService = UserServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("template", "templates/register.jsp");
		req.getRequestDispatcher("/WEB-INF/main.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		if (!userService.isUsernameExists(username)) {
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user = userService.create(user);
			req.getSession().setAttribute(User.AUTHENTICATED_USER, user);
			resp.sendRedirect(req.getContextPath() + "/user/" + user.getId());
		} else {
			req.setAttribute("registerError", true);
			req.setAttribute("template", "templates/register.jsp");
			req.getRequestDispatcher("/WEB-INF/main.jsp").forward(req, resp);
		}
	}

}
