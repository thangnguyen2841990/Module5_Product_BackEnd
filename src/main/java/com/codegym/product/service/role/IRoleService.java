package com.codegym.product.service.role;


import com.codegym.product.model.entity.Role;
import com.codegym.product.service.IGeneralService;

public interface IRoleService extends IGeneralService<Role> {
    Iterable<Role> findAll();
}
