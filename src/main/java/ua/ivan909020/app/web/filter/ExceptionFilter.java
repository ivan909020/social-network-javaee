package ua.ivan909020.app.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("*")
public class ExceptionFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		try {
			chain.doFilter(req, resp);
		} catch (Throwable e) {
			req.setAttribute("template", "templates/error.jsp");
			req.getRequestDispatcher("/WEB-INF/main.jsp").forward(req, resp);
		}
	}

}
