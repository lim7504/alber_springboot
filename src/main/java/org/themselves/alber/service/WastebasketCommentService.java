package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.repository.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WastebasketCommentService {

    private final WastebasketRepository wastebasketRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


}

