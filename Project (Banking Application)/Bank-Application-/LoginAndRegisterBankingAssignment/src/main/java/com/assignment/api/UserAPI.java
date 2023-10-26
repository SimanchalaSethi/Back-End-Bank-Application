
package com.assignment.api;

import com.assignment.exception.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.request.dto.AccountDetailsRequest;
import com.assignment.request.dto.CheckingBalance;
import com.assignment.request.dto.TransactionDetailsRequest;
import com.assignment.request.dto.TransactionHistory;
import com.assignment.request.dto.UserRequest;
import com.assignment.response.dto.UserResponse;
import com.assignment.service.imp.UserServiceImp;
import com.assignment.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/bank")
public class UserAPI {

    @Autowired
    private UserServiceImp userService;
    @Autowired
    private JwtUtils jwtUtils;
    UserResponse response = null;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {

        return ResponseEntity.ok().body(userService.register(request));
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody UserRequest request) {
        return userService.userLogin(request);
    }

    @PostMapping("/fundTransfer")
    public ResponseEntity<UserResponse> fundTransfer(HttpServletRequest req,
                                                     @RequestBody TransactionDetailsRequest detailsRequest) {
        String value = req.getHeader("Authorization");

        boolean check = false;
        try {
            check = jwtUtils.verify(value);
            response = userService.getTransfer(detailsRequest, value);
            return ResponseEntity.ok().body(response);
        } catch (Exception ex) {
            throw new JwtException("Please check your token once again !!");
        }

    }

    @PostMapping("/addMoney")
    public ResponseEntity<UserResponse> addMoney(@RequestBody AccountDetailsRequest accountDetailsRequest) {
        UserResponse response = userService.addFund(accountDetailsRequest);
        return ResponseEntity.ok().body(response);
    }

    // API for checking the balance
    @PostMapping("/checkingBalance")
    public ResponseEntity<UserResponse> checkBalance(HttpServletRequest req, @RequestBody CheckingBalance balance) {
        String value = req.getHeader("Authorization");

        boolean check = false;
        try {
            check = jwtUtils.verify(value);
            response = userService.getAccBalance(balance, value);
            return ResponseEntity.ok().body(response);
        } catch (Exception ex) {
            throw new JwtException("Please check your token once again !!");
        }

    }

    @PostMapping("/transaction")
    public ResponseEntity<UserResponse> getTransactionHistory(HttpServletRequest req, @RequestBody TransactionHistory history) {
        String value = req.getHeader("Authorization");

        boolean check = false;
        try {
            check = jwtUtils.verify(value);
            response = userService.getTransactionHistory(history.getAccountNo(), value);
            return ResponseEntity.ok().body(response);
        } catch (Exception ex) {
            throw new JwtException("Please check your token once again !!");
        }
    }

}
