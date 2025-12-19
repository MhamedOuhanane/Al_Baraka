package com.albaraka.albaraka.service.interfaces;

import com.albaraka.albaraka.model.dto.user.LoginDTO;
import com.albaraka.albaraka.model.dto.user.LoginRequestDTO;
import com.albaraka.albaraka.model.dto.user.RegisterDTO;
import com.albaraka.albaraka.model.dto.user.UserDTO;

public interface UserService {
    UserDTO register(RegisterDTO dto);
    LoginDTO login(LoginRequestDTO dto);
}
