package mk.ukim.finki.wpaud.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wpaud.service.CategoryService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "category-servlet", urlPatterns = "/servlet/category")
public class CategoryServlet extends HttpServlet {

    private final CategoryService categoryService;

    public CategoryServlet(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ipAddress = req.getRemoteAddr();
        String clientAgent = req.getHeader("User-Agent");
        PrintWriter pw = resp.getWriter();
        pw.println("<html>");
        pw.println("<head>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<h3>User Info</h3>");
        pw.format("IP Address: %s<br />", ipAddress);
        pw.format("Client Agent: %s", clientAgent);
        pw.println("<h3>Category List</h3>");
        pw.println("<ul>");
        categoryService.listCategories().forEach(i -> pw.printf("<li>%s (%s)</li>", i.getName(), i.getDescription()));
        pw.println("</ul>");
        pw.println("<h3>Add Category</h3>");
        pw.println("<form method='post' action='/servlet/category'>");
        pw.println("<label for='name'>Name:</label>");
        pw.println("<input id='name' type='text' name='name' />");
        pw.println("<label for='description'>Description:</label>");
        pw.println("<input id='description' type='text' name='description' />");
        pw.println("<input type='submit' value='Submit' />");
        pw.println("</form>");
        pw.println("</body>");
        pw.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryName = req.getParameter("name");
        String categoryDescription = req.getParameter("description");
        categoryService.create(categoryName, categoryDescription);
        resp.sendRedirect("/servlet/category");
    }


}
