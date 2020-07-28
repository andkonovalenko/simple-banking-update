package com.practice.bank.web.rest;

import com.practice.bank.domain.User;
import com.practice.bank.dto.AdminUserDto;
import com.practice.bank.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/admin/user/")
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<AdminUserDto>> getAllUsers() {
        List<User> userFromDbList = userService.getAll();

        if (userFromDbList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        List<AdminUserDto> adminUserDtoList = new ArrayList<>();
        userFromDbList.stream().forEach(user -> adminUserDtoList.add(AdminUserDto.fromUser(user)));

        return new ResponseEntity<>(adminUserDtoList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
        User userFromDb = userService.getOneById(id);

        if (userFromDb == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(AdminUserDto.fromUser(userFromDb), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<AdminUserDto> update(@PathVariable(name = "id") Long id, @RequestBody @Valid  User user) {
        User userFromDb = userService.getOneById(id);

        if (userFromDb == null)
            return new ResponseEntity(userService.create(user), HttpStatus.CREATED);

        return new ResponseEntity<>(AdminUserDto.fromUser(userService.update(id, user)), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable(name = "id") Long id) {
        User userFromDb = userService.getOneById(id);

        if (userFromDb == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
