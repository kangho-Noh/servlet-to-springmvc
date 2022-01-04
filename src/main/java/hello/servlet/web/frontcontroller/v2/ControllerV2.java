package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV2 {

    //V1과 차이점은 return type이 MyView라는
    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
