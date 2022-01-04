package hello.servlet.web.frontcontroller.v5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

	private final Map<String, Object> handlerMappingMap = new HashMap<>();
	private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

	public FrontControllerServletV5() {
		initHandlerMappingMap();
		initHandlerAdapters();
	}

	private void initHandlerAdapters() {
		handlerAdapters.add(new ControllerV3HandlerAdapter());
	}

	private void initHandlerMappingMap() {
		handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//핸들러 호출
		Object handler = getHandler(request);
		if (handler == null) { // 예외처리
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		//핸들러 목록에서 찾기
		MyHandlerAdapter adapter = getHandlerAdapter(handler);

		ModelView mv = adapter.handle(request, response, handler);

		// /new-form
		String viewName = mv.getViewName();//논리이름
		MyView view = viewResolver(viewName); //논리 이름을 전체 URI로 만드는 과정. 나중에 수정이 필요하면 이것만 수정하면 되도록 바꿀 수 있다는 장점이 있다.

		view.render(mv.getModel(), request, response);
	}

	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}

	private MyHandlerAdapter getHandlerAdapter(Object handler) {
		// 어댑터 목록에서 쭉 찾아봄. 있으면 리턴 없으면 예외처리
		for (MyHandlerAdapter adapter : handlerAdapters) {
			if (adapter.supports(handler)) {
				//지원하면
				return adapter;
			}
		}
		throw new IllegalArgumentException("handler adpater를 찾을 수 없습니다. handler=" + handler);
	}

	private Object getHandler(HttpServletRequest request) {
		String requestURI = request.getRequestURI(); // URI 정보를 가져옴 /
		Object handler = handlerMappingMap.get(requestURI);
		return handler;
	}
}
