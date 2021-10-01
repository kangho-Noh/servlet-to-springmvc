package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello") //name: 서블릿 이름 ulrPatterns: url 매핑
public class HelloServlet extends HttpServlet {

    @Override // HTTP 요청을 통해 매핑된 URL이 호출되면 서블릿 컨테이너는 다음 메서드를 실행한다.
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        String username = request.getParameter("username"); // query string 가져오는 함수 ?username=
        System.out.println("username = " + username);

        response.setContentType("text/plain"); //header
        response.setCharacterEncoding("utf-8"); //header
        response.getWriter().write("hello" + username); // body에 입력하는 함수 write()
    }
}
