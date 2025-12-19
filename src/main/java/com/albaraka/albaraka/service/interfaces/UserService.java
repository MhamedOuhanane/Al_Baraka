package com.albaraka.albaraka.service.interfaces;

import com.albaraka.albaraka.model.dto.user.RegisterDTO;
import com.albaraka.albaraka.model.dto.user.UserDTO;

public interface UserService {
    UserDTO register(RegisterDTO dto);
}
