package ru.practicum.ewm_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_service.entity.User;
import ru.practicum.ewm_service.entity.dto.user.NewUserRequest;
import ru.practicum.ewm_service.entity.dto.user.UserDto;
import ru.practicum.ewm_service.entity.mapper.UserMapper;
import ru.practicum.ewm_service.repository.UserRepository;
import ru.practicum.ewm_service.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createNewUser(NewUserRequest dto) {
        return UserMapper.toDto(userRepository.save(UserMapper.fromDto(dto)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {

        Pageable pageable = PageRequest.of(from, size);
        Page<User> users;

        if (ids == null) {
            users = userRepository.findAll(pageable);
        } else {
            users = userRepository.findByIdIn(ids, pageable);
        }

        return users
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
