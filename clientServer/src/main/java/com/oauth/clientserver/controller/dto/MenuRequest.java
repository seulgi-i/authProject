package com.oauth.clientserver.controller.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data

public class MenuRequest {
    private Integer menuSeq;
    private String menuTpCd;
    private String menuId;
    private String menuNm;
    private Integer menuLvRnk;
    private Integer menuOrdSeq;
    private String prntMenuId;
    private String menuKndCd;
    private String menuClasNm;
    private String menuTreFl;
    private String menuUrlDes;
    private String menuDes;
    private String actvFl;
    private String iconNm;
    private String cFl;
    private String rFl;
    private String uFl;
    private String dFl;
    private String cretUsrId;
    private Timestamp cretDtc;
    private String updtUsrId;
    private Timestamp updtDtu;
    private String menuMarkFl;

}
