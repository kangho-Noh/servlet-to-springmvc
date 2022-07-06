package hello.servlet.web.springmvc.v1;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;

@Controller
public class SpringMemberSaveControllerV1 {

	private MemberRepository memberRepository = MemberRepository.getInstance();

	@RequestMapping("/springmvc/v1/members/save")
	public ModelAndView process(Map<String, String> paramMap) {
		String username = paramMap.get("username");
		int age = Integer.parseInt(paramMap.get("age"));

		Member member = new Member(username, age);
		memberRepository.save(member);

		ModelAndView mv = new ModelAndView("save-result");
		mv.addObject("member", member); // 저장 후 출력해야 하므로
		return mv;
	}
}
