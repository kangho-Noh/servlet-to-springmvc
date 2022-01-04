package hello.servlet.web.frontcontroller.v5.adapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {
	@Override
	public boolean supports(Object handler) {
		return (handler instanceof ControllerV4);
	}

	@Override
	public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		ServletException,
		IOException {
		ControllerV4 controller = (ControllerV4)handler;

		Map<String, String> paramMap = createParamMap(request);
		Map<String, Object> model = new HashMap<>(); //모델 따로 하는 v4

		String viewName = controller.process(paramMap, model);
		//문제 발생. viewName을 리턴해야 하는데 interface에 안맞는다
		//어댑터 다운 역할이 이제 나옴. ModelView로 바꿔서 반환.
		ModelView mv = new ModelView(viewName);
		mv.setModel(model);

		return mv;
	}

	private Map<String, String> createParamMap(HttpServletRequest request) {
		// 파라미터를 다 뽑아서 paramMap으로 만들어 반환. 여기서 파라미터는 Member의 name과 age같이 form을 통해 오는 것들
		Map<String, String> paramMap = new HashMap<>();
		request.getParameterNames().asIterator()
			.forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
		return paramMap;
	}
}
