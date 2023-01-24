package ru.practicum.ewm_service.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_service.entity.dto.user.NewUserRequest;
import ru.practicum.ewm_service.entity.dto.user.UserDto;
import ru.practicum.ewm_service.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
@Validated
public class AdminUserController {

    private final UserService userService;


    @PostMapping
    public UserDto create(@Valid @RequestBody NewUserRequest dto) {
        return userService.createNewUser(dto);
    }

    @GetMapping
    public List<UserDto> getAllOrOneUser(
            @RequestParam(name = "ids", required = false) final List<Long> ids,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") final int from,
            @Positive @RequestParam(name = "size", defaultValue = "10") final int size) {
        return userService.getUsers(ids, from, size);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@Positive @PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
