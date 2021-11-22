package hello.servlet.web.frontcontroller.v1.contoller;

import hello.servlet.web.frontcontroller.v1.ControllerV1;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFormControllerV1  implements ControllerV1 {


    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //MvcMemberFormServlet 복사
        String viewPath = "/WEB-INF/views/new-form.jsp"; // WEB-INF 폴더에 jsp파일을 넣으면 클라이언트가 직접 접근하는 것을 막을 수 있다. 컨트롤러를 통해서만 접근 가능하도록 강제한다.
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath); //Controller -> View ㅇㅣ동할 때 사용. 즉 서버 내부에서 서버끼리 호출
        dispatcher.forward(request, response); //디스패처로 서버 간 호출을 한다. 이것은 리다이렉트가 아니다.

    }
}
