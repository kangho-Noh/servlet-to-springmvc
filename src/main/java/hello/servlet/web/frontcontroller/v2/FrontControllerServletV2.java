package hello.servlet.web.frontcontroller.v2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*") //얘가 핵심. *이기 때문에 무조건 호출됨
public class FrontControllerServletV2 extends HttpServlet {

	private Map<String, ControllerV2> controllerMap = new HashMap<>();

	//생성자로 controller mapping 정보를 저장
	public FrontControllerServletV2() {
		controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
		controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
		controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws
		ServletException,
		IOException {

		String requestURI = request.getRequestURI(); // URI 정보를 가져옴 /

		//URI를 키로 삼아 객체인스턴스를 받아옴. 다형성에 의해 인터페이스로 객체를 받아올 수 있음 : 부모는 자식을 담을 수 있다
		ControllerV2 controller = controllerMap.get(requestURI);

		if (controller == null) { // 예외처리
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// 해당 컨트롤러로 넘겨줌
		MyView view = controller.process(request, response);
		view.render(request, response);
	}
}
