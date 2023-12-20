package com.oauth.clientserver.service.impl;

import com.oauth.clientserver.controller.dto.MenuRequest;
import com.oauth.clientserver.repository.MenuRepository;
import com.oauth.clientserver.repository.entity.CmnMenuBcEntity;
import com.oauth.clientserver.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final MenuRepository menuRepository;


    @Override
    public CmnMenuBcEntity list(Integer id)  {
        Optional<CmnMenuBcEntity> menu = menuRepository.findById(id);
        log.info("menu  ===" + menu);



        return menu.get();
    }

    @Override
    public List<CmnMenuBcEntity> listAll() {
        // findAll() 메서드를 사용하여 모든 데이터를 가져옴

        return menuRepository.findAll();
    }

    @Override
    public String modify(Integer id, CmnMenuBcEntity menuItem) {
        CmnMenuBcEntity oldMenu = menuRepository.findById(id).get();

        menuRepository.save(oldMenu);
        return "success";

    }

    @Override
    public String delete(Integer id) {
        if (id != null) {
            menuRepository.deleteById(id);
            return "success";
        }
        return "fails";
    }

    @Override
    public String create(MenuRequest cmnMenuBcEntity) {
        CmnMenuBcEntity menu = CmnMenuBcEntity.builder().
                build();

        menuRepository.save(menu);

        return "success";
    }
}
