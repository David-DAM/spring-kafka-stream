package com.davinchicoder.spring.kafka.stream.infrastructure.event;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserEventMapper {

//    UserVerificationRequested toUserVerificationRequested(User user);

}
