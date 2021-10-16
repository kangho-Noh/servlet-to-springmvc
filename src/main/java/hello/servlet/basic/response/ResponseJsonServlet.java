package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper(); //jackson

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Content-Type
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //스프링 쓰면 이 밑에 세줄만 쓰면 끝임
        HelloData helloData = new HelloData();
        helloData.setAge(20);
        helloData.setUsername("kim");

        //json으로 변환
        String result = objectMapper.writeValueAsString(helloData);
        response.getWriter().write(result);

    }
}
