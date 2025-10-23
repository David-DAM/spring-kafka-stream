package com.davinchicoder.spring.kafka.stream.infrastructure.event.mapper;

import com.davinchicoder.events.user.UserCreated;
import com.davinchicoder.events.user.validations.UserVerificationRequested;
import com.davinchicoder.spring.kafka.stream.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserEventMapper {

    @Mapping(target = "timestamp", source = "createdAt")
    UserVerificationRequested maptoUserVerificationRequested(User user);

    @Mapping(target = "createdAt", source = "timestamp")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    User mapToUser(UserCreated event);

    default Long mapToTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
    }

    default LocalDateTime mapToLocalDateTime(Long time) {
        return LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.UTC);
    }
}
