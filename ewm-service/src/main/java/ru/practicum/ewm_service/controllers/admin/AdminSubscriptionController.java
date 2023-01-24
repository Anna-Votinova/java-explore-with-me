package ru.practicum.ewm_service.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm_service.entity.dto.subscription.SubscriptionDto;
import ru.practicum.ewm_service.entity.util.Status;
import ru.practicum.ewm_service.service.SubscriptionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/subscription")
@Validated
public class AdminSubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public List<SubscriptionDto> getAllWithParameters(
            @RequestParam(required = false) List<Long> users,
            @RequestParam (required = false) List<Long> friends,
            @RequestParam (required = false) List<Status> statuses,
            @RequestParam(name = "from", defaultValue = "0", required = false) final int from,
            @RequestParam(name = "size", defaultValue = "10", required = false) final int size) {
        return subscriptionService.getAllWithParameters(users, friends, statuses, from, size);
    }



}
