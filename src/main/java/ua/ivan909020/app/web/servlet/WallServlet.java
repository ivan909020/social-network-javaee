package ua.ivan909020.app.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.ivan909020.app.domain.entities.Post;
import ua.ivan909020.app.domain.models.Pager;
import ua.ivan909020.app.service.PostService;
import ua.ivan909020.app.service.impl.PostServiceImpl;
import ua.ivan909020.app.util.NumberUtils;

@WebServlet("")
public class WallServlet extends HttpServlet {

	private final PostService postService = PostServiceImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pageNumber = NumberUtils.parseInt(req.getParameter("page"));
		int pageSize = NumberUtils.parseInt(req.getParameter("size"));

		int postsCount = postService.countAll();
		Pager pager = Pager.build(postsCount, pageNumber, pageSize, "?");
		List<Post> posts = postService.findAll(pager.getPageNumber(), pager.getPageSize());
		req.setAttribute("pager", pager);
		req.setAttribute("posts", posts);
		req.setAttribute("postsCount", postsCount);
		req.setAttribute("template", "templates/wall.jsp");
		req.getRequestDispatcher("/WEB-INF/main.jsp").forward(req, resp);
	}

}
