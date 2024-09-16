package com.javaMailServerForOtp.controller;

import com.javaMailServerForOtp.dto.NewCommonResponse;
import com.javaMailServerForOtp.dto.user.UpdateUserDto;
import com.javaMailServerForOtp.dto.user.User;
import com.javaMailServerForOtp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/api/user")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('SCOPE_READ')")
    @PutMapping("/update-user/{userId}")
    public ResponseEntity<NewCommonResponse<Boolean>> updateUser(@PathVariable int userId,
                                                                 @Valid @RequestBody UpdateUserDto updateUser,
                                                                 BindingResult result, HttpServletRequest req) {
        try {
            if (result.hasErrors()) {
                throw new Exception("Error updating");
            }
            boolean user = userService.updateUser(userId, updateUser);
            if (!user){
                throw new Exception("User not Updated");
            }
            NewCommonResponse<Boolean> response = NewCommonResponse.<Boolean>builder()
                    .statusCode(HttpStatus.OK.value())
                    .status("success")
                    .data(true)
                    .message("User updated successfully")
                    .error(null)
                    .path(req.getRequestURI())
                    .requestId(UUID.randomUUID().toString())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            FieldError fieldError = result.getFieldError();
            return new ResponseEntity<>(NewCommonResponse.<Boolean>builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status("error")
                    .success(false)
                    .message(fieldError != null ? fieldError.getDefaultMessage() : "Unknown Error")
                    .data(null)
                    .path(req.getRequestURI())
                    .requestId(UUID.randomUUID().toString())
                    .error(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
