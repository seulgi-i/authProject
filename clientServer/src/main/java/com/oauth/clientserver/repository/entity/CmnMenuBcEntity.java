package com.oauth.clientserver.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "cmn_menu_bc", schema = "seulgi")
public class CmnMenuBcEntity  {
    @Basic
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_SEQ")
    private Integer menuSeq;
    @Basic
    @Column(name = "MENU_TP_CD")
    private String menuTpCd;
    @Basic
    @Column(name = "MENU_ID")
    private String menuId;
    @Basic
    @Column(name = "MENU_NM")
    private String menuNm;
    @Basic
    @Column(name = "MENU_LV_RNK")
    private Integer menuLvRnk;
    @Basic
    @Column(name = "MENU_ORD_SEQ")
    private Integer menuOrdSeq;
    @Basic
    @Column(name = "PRNT_MENU_ID")
    private String prntMenuId;
    @Basic
    @Column(name = "MENU_KND_CD")
    private String menuKndCd;
    @Basic
    @Column(name = "MENU_CLAS_NM")
    private String menuClasNm;
    @Basic
    @Column(name = "MENU_TRE_FL")
    private String menuTreFl;
    @Basic
    @Column(name = "MENU_URL_DES")
    private String menuUrlDes;
    @Basic
    @Column(name = "MENU_DES")
    private String menuDes;
    @Basic
    @Column(name = "ACTV_FL")
    private String actvFl;
    @Basic
    @Column(name = "ICON_NM")
    private String iconNm;
    @Basic
    @Column(name = "C_FL")
    private String cFl;
    @Basic
    @Column(name = "R_FL")
    private String rFl;
    @Basic
    @Column(name = "U_FL")
    private String uFl;
    @Basic
    @Column(name = "D_FL")
    private String dFl;
    @Basic
    @Column(name = "CRET_USR_ID")
    private String cretUsrId;

    @Basic
    @Column(name = "UPDT_USR_ID")
    private String updtUsrId;

    @Basic
    @Column(name = "MENU_MARK_FL")
    private String menuMarkFl;

}
