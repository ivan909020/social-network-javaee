package ua.ivan909020.app.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.ivan909020.app.domain.entities.User;
import ua.ivan909020.app.domain.models.Pager;
import ua.ivan909020.app.service.UserService;
import ua.ivan909020.app.service.impl.UserServiceImpl;
import ua.ivan909020.app.util.NumberUtils;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

	private final UserService userService = UserServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		int pageNumber = NumberUtils.parseInt(req.getParameter("page"));
		int pageSize = NumberUtils.parseInt(req.getParameter("size"));

		if (username != null) {
			String contextPath = "search?username=" + username + "&";
			int usersCount = userService.countByContainsUsername(username);
			Pager pager = Pager.build(usersCount, pageNumber, pageSize, contextPath);
			List<User> users = userService.findContainsUsername(username, pager.getPageNumber(), pager.getPageSize());
			req.setAttribute("pager", pager);
			req.setAttribute("users", users);
			req.setAttribute("usersCount", usersCount);
		}
		req.setAttribute("template", "templates/search.jsp");
		req.getRequestDispatcher("/WEB-INF/main.jsp").forward(req, resp);
	}

}
