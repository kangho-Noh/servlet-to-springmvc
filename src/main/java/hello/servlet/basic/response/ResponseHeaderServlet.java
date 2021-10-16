package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //[status-line]
        response.setStatus(HttpServletResponse.SC_OK); //200으로 하는 것보다 읽을 수 있도록 이렇게 하는것 권장

        //[response-headers]
        //response.setHeader("Content-Type", "text/plain;charset=utf-8"); //charset utf-8지정해야 한글 안깨짐
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); //캐시 완전 무효화
        response.setHeader("Pragma", "no-cache"); // 캐시 완전 무효화(이전버전)
        response.setHeader("my-header", "hello"); //임의의 헤더

        //[Header 편의 메서드]
        //content(response);
        cookie(response);
        redirect(response);

        PrintWriter writer = response.getWriter();
        writer.println("ok오케이");

    }

    private void content(HttpServletResponse response) {
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2
        //response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //content length는 (생략시 자동 생성) 그래서 보통 생략함
    }

    private void cookie(HttpServletResponse response) {
        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie); // 헤더에 쿠키 추가. 2번째 줄과 똑같은 작업
    }

    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html

        //response.setStatus(HttpServletResponse.SC_FOUND); //302
        //response.setHeader("Location", "/basic/hello-form.html");
        response.sendRedirect("/basic/hello-form.html");
    }
}
