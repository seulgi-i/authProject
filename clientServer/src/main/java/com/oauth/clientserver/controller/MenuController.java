package com.oauth.clientserver.controller;


import com.oauth.clientserver.controller.dto.MenuRequest;
import com.oauth.clientserver.repository.entity.CmnMenuBcEntity;
import com.oauth.clientserver.response.ListResponse;
import com.oauth.clientserver.response.SingleResponse;
import com.oauth.clientserver.service.MenuService;
import com.oauth.clientserver.service.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "MENU", description = "MENU API Document")
public class MenuController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    MenuService menuService;

    private final ResponseService responseService;

    @Operation(summary = "MENU CODE 조회", description = "MENU CODE 조회")
    @GetMapping("/list/{id}")
    public SingleResponse<CmnMenuBcEntity> list(@PathVariable("id") Integer id) {

        return responseService.getSingleResponse(menuService.list(id));
    }

    @Operation(summary = "MENU CODE LIST 조회", description = "MENU CODE LIST 조회")
    @GetMapping("/list")
    public ListResponse<CmnMenuBcEntity> listAll() {

        return responseService.getListResponse(menuService.listAll());
    }

    @Operation(summary = "MENU CODE 생성", description = "MENU CODE 생성")
    @PostMapping("/create")
    public Object create(@RequestBody MenuRequest menuRequest) {
        String result = menuService.create(menuRequest);

        if (result.equalsIgnoreCase("success")) {
            return responseService.postResponse();
        } else return responseService.failResponse();
    }

    @Operation(summary = "MENU CODE 수정", description = "MENU CODE 수정")
    @PutMapping("/modify/{id}")
    public Object modify(@PathVariable Integer id, @RequestBody CmnMenuBcEntity menuRequest) {
        String result = menuService.modify(id, menuRequest);
        if (result.equalsIgnoreCase("success")) {
            return responseService.postResponse();
        } else return responseService.failResponse();
    }

    @Operation(summary = "MENU CODE 삭제", description = "MENU CODE 삭제")
    @DeleteMapping("/delete/{id}")
    public Object delete(@PathVariable Integer id) {
        String result = menuService.delete(id);

        if (result.equalsIgnoreCase("success")) {
            return responseService.postResponse();
        } else return responseService.failResponse();
    }


}