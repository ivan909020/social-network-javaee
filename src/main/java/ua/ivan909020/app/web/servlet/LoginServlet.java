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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private final UserService userService = UserServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("template", "templates/login.jsp");
		req.getRequestDispatcher("/WEB-INF/main.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		User user = userService.authenticate(username, password);
		if (user != null) {
			req.getSession().setAttribute(User.AUTHENTICATED_USER, user);
			resp.sendRedirect(req.getContextPath() + "/user/" + user.getId());
		} else {
			req.setAttribute("loginError", true);
			req.setAttribute("template", "templates/login.jsp");
			req.getRequestDispatcher("/WEB-INF/main.jsp").forward(req, resp);
		}
	}

}
