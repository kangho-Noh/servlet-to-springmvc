package hello.servlet.web.springmvc.v2;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {

	private MemberRepository memberRepository = MemberRepository.getInstance();

	@RequestMapping("/new-form")
	public ModelAndView form() {
		return new ModelAndView("new-form");
	}

	@RequestMapping
	public ModelAndView showAll(Map<String, String> paramMap) {
		List<Member> members = memberRepository.findAll();
		ModelAndView mv = new ModelAndView("members");
		mv.addObject("members", members); // 출력에 사용할 Model

		return mv;
	}

	@RequestMapping("/save")
	public ModelAndView save(Map<String, String> paramMap) {
		String username = paramMap.get("username");
		int age = Integer.parseInt(paramMap.get("age"));

		Member member = new Member(username, age);
		memberRepository.save(member);

		ModelAndView mv = new ModelAndView("save-result");
		mv.addObject("member", member); // 저장 후 출력해야 하므로
		return mv;
	}
}
