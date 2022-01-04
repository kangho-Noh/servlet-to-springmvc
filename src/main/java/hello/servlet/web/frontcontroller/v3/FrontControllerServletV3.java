package hello.servlet.web.frontcontroller.v3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*") //얘가 핵심. *이기 때문에 무조건 호출됨
public class FrontControllerServletV3 extends HttpServlet {

	private Map<String, ControllerV3> controllerMap = new HashMap<>();

	//생성자로 controller mapping 정보를 저장
	public FrontControllerServletV3() {
		controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
		controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
		controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws
		ServletException,
		IOException {

		String requestURI = request.getRequestURI(); // URI 정보를 가져옴 /

		//URI를 키로 삼아 객체인스턴스를 받아옴. 다형성에 의해 인터페이스로 객체를 받아올 수 있음 : 부모는 자식을 담을 수 있다
		ControllerV3 controller = controllerMap.get(requestURI);
		if (controller == null) { // 예외처리
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		//paramMap. form으로 들어온 정보들을 담은 Map
		Map<String, String> paramMap = createParamMap(request);
		//백엔드 로직을 처리 후 View로 출력할 것들을 URI, model을 함께 ModelView로 전달받음.
		ModelView mv = controller.process(paramMap);

		// /new-form
		String viewName = mv.getViewName();//논리이름
		MyView view = viewResolver(viewName); //논리 이름을 전체 URI로 만드는 과정. 나중에 수정이 필요하면 이것만 수정하면 되도록 바꿀 수 있다는 장점이 있다.

		view.render(mv.getModel(), request, response);
	}

	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}

	private Map<String, String> createParamMap(HttpServletRequest request) {
		// 파라미터를 다 뽑아서 paramMap으로 만들어 반환. 여기서 파라미터는 Member의 name과 age같이 form을 통해 오는 것들
		Map<String, String> paramMap = new HashMap<>();
		request.getParameterNames().asIterator()
			.forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
		return paramMap;
	}
}
