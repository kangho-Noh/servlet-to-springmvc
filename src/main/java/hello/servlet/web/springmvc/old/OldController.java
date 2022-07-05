package hello.servlet.web.springmvc.old;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

@Component("/springmvc/old-controller") //스프링 빈의 이름을 url 패턴으로 맞춤. 핸들러 매핑을 스프링 빈 이름으로 매핑하는 방식임.
public class OldController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("OldController.handleRequest");
		return new ModelAndView("new-form");
	}
}
