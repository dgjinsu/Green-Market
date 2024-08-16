package user_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user_service.common.Response;
import user_service.dto.request.LoginRequest;
import user_service.dto.request.UserSaveRequest;
import user_service.service.LoginService;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @Operation(summary = "회원 가입")
    @PostMapping("/join")
    public ResponseEntity<Response> join(@RequestBody UserSaveRequest request) {
        return ResponseEntity.ok(new Response(loginService.joinUser(request), "회원가입 완료"));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(new Response(loginService.login(request), "로그인 완료"));
    }

}
