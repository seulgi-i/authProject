package com.oauth.clientserver.service;



import com.oauth.clientserver.controller.dto.MenuRequest;
import com.oauth.clientserver.repository.entity.CmnMenuBcEntity;

import java.util.List;


public interface MenuService {

    CmnMenuBcEntity list(Integer id);

    List<CmnMenuBcEntity> listAll();

    String create(MenuRequest menuRequest);

    String modify(Integer id, CmnMenuBcEntity menuItem);

    String delete(Integer id);
}