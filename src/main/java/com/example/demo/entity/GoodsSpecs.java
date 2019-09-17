package com.example.demo.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsSpecs implements Serializable {
    private Integer id;

    private Integer goodsId;

    private static final long serialVersionUID = 1L;
    private String specsSrc;

    private BigDecimal sellPrice;

    private BigDecimal discount;

    private String discountDescribe;

    private Boolean specsStatus;
    private String specsText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getSpecsSrc() {
        return specsSrc;
    }

    public void setSpecsSrc(String specsSrc) {
        this.specsSrc = specsSrc == null ? null : specsSrc.trim();
    }

    public String getSpecsText() {
        return specsText;
    }

    public void setSpecsText(String specsText) {
        this.specsText = specsText == null ? null : specsText.trim();
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getDiscountDescribe() {
        return discountDescribe;
    }

    public void setDiscountDescribe(String discountDescribe) {
        this.discountDescribe = discountDescribe == null ? null : discountDescribe.trim();
    }

    public Boolean getSpecsStatus() {
        return specsStatus;
    }

    public void setSpecsStatus(Boolean specsStatus) {
        this.specsStatus = specsStatus;
    }
}
