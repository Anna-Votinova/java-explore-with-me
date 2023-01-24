package ru.practicum.ewm_service.controllers.private_api;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_service.entity.dto.request.ParticipationRequestDto;
import ru.practicum.ewm_service.service.RequestService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
@Validated
public class PrivateApiRequestController {

    private final RequestService requestService;

    @PostMapping
    public ParticipationRequestDto createRequest(@Positive @PathVariable Long userId,
                                                 @Positive @NotNull @RequestParam Long eventId) {
        return requestService.createRequest(userId, eventId);
    }

    @GetMapping
    public List<ParticipationRequestDto> getAll(@Positive @PathVariable Long userId) {
        return requestService.getAll(userId);
    }

    @PatchMapping("{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@Positive @PathVariable Long userId,
                                                 @Positive @PathVariable Long requestId) {
        return requestService.cancelRequest(userId, requestId);

    }
}
