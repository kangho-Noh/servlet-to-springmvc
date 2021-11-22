package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.contoller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.contoller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.contoller.MemberSaveControllerV1;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*") //얘가 핵심. *이기 때문에 무조건 호출됨
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    //생성자로 controller mapping 정보를 저장
    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI(); // URI 정보를 가져옴 /

        //URI를 키로 삼아 객체인스턴스를 받아옴. 다형성에 의해 인터페이스로 객체를 받아올 수 있음 : 부모는 자식을 담을 수 있다
        ControllerV1 controller = controllerMap.get(requestURI);

        if(controller == null){ // 예외처리
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 해당 컨트롤러로 넘겨줌
        controller.process(request, response);
    }
}
