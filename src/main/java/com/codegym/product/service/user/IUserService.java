package com.codegym.product.service.user;

import com.codegym.product.model.entity.User;
import com.codegym.product.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    User findByUsername(String username);

    Iterable<User> findAll();

    User saveAdmin(User user);

}
