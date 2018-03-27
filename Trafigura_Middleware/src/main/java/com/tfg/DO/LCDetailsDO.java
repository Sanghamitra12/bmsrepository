package com.tfg.DO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Date;
import java.sql.Timestamp;

public class LCDetailsDO {

    private int lc_id ;

    private int lc_version ;

    private String lcStatus ;

    private String lcDate ;

    private String groupCompany ;

    private String gcBusinessLine ;

    private String counterParty ;

    private String cpBusinessLine ;

    private String commodity ;

    private String quantity ;

    private String conractualLocation ;

    private String incoterms ;

    private String materialSpecification ;

    private String valueDate ;

    private Double price ;

    private Double amount ;

    private String placeOfPresentation ;

    private String placeOfExpiry ;

    private String dateOfIssue ;

    private String dateOfExpiry ;

    private String comments ;

    private String updateTimestamp ;

    private String docSecureHashString ;

    private String issuingBank ;

    private String issuingBankAddress ;

    private String advisingBank ;

    private String advisingBankAddress ;

    private String groupCompanyAddress ;

    private String counterPartyAddress ;

    private int tenorDays ;

    private String tenorBasisCondition ;

    private String tenorDate ;

    private String tenorBasisEvent;

    public int getLc_id() {
        return lc_id;
    }

    public void setLc_id(int lc_id) {
        this.lc_id = lc_id;
    }

    public int getLc_version() {
        return lc_version;
    }

    public void setLc_version(int lc_version) {
        this.lc_version = lc_version;
    }

    public String getLcStatus() {
        return lcStatus;
    }

    public void setLcStatus(String lcStatus) {
        this.lcStatus = lcStatus;
    }

    public String getLcDate() {
        return lcDate;
    }

    public void setLcDate(String lcDate) {
        this.lcDate = lcDate;
    }

    public String getGroupCompany() {
        return groupCompany;
    }

    public void setGroupCompany(String groupCompany) {
        this.groupCompany = groupCompany;
    }

    public String getGcBusinessLine() {
        return gcBusinessLine;
    }

    public void setGcBusinessLine(String gcBusinessLine) {
        this.gcBusinessLine = gcBusinessLine;
    }

    public String getCounterParty() {
        return counterParty;
    }

    public void setCounterParty(String counterParty) {
        this.counterParty = counterParty;
    }

    public String getCpBusinessLine() {
        return cpBusinessLine;
    }

    public void setCpBusinessLine(String cpBusinessLine) {
        this.cpBusinessLine = cpBusinessLine;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getConractualLocation() {
        return conractualLocation;
    }

    public void setConractualLocation(String conractualLocation) {
        this.conractualLocation = conractualLocation;
    }

    public String getIncoterms() {
        return incoterms;
    }

    public void setIncoterms(String incoterms) {
        this.incoterms = incoterms;
    }

    public String getMaterialSpecification() {
        return materialSpecification;
    }

    public void setMaterialSpecification(String materialSpecification) {
        this.materialSpecification = materialSpecification;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPlaceOfPresentation() {
        return placeOfPresentation;
    }

    public void setPlaceOfPresentation(String placeOfPresentation) {
        this.placeOfPresentation = placeOfPresentation;
    }

    public String getPlaceOfExpiry() {
        return placeOfExpiry;
    }

    public void setPlaceOfExpiry(String placeOfExpiry) {
        this.placeOfExpiry = placeOfExpiry;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getDateOfExpiry() {
        return dateOfExpiry;
    }

    public void setDateOfExpiry(String dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(String updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public String getDocSecureHashString() {
        return docSecureHashString;
    }

    public void setDocSecureHashString(String docSecureHashString) {
        this.docSecureHashString = docSecureHashString;
    }

    public String getIssuingBank() {
        return issuingBank;
    }

    public void setIssuingBank(String issuingBank) {
        this.issuingBank = issuingBank;
    }

    public String getIssuingBankAddress() {
        return issuingBankAddress;
    }

    public void setIssuingBankAddress(String issuingBankAddress) {
        this.issuingBankAddress = issuingBankAddress;
    }

    public String getAdvisingBank() {
        return advisingBank;
    }

    public void setAdvisingBank(String advisingBank) {
        this.advisingBank = advisingBank;
    }

    public String getAdvisingBankAddress() {
        return advisingBankAddress;
    }

    public void setAdvisingBankAddress(String advisingBankAddress) {
        this.advisingBankAddress = advisingBankAddress;
    }

    public String getGroupCompanyAddress() {
        return groupCompanyAddress;
    }

    public void setGroupCompanyAddress(String groupCompanyAddress) {
        this.groupCompanyAddress = groupCompanyAddress;
    }

    public String getCounterPartyAddress() {
        return counterPartyAddress;
    }

    public void setCounterPartyAddress(String counterPartyAddress) {
        this.counterPartyAddress = counterPartyAddress;
    }

    public int getTenorDays() {
        return tenorDays;
    }

    public void setTenorDays(int tenorDays) {
        this.tenorDays = tenorDays;
    }

    public String getTenorBasisCondition() {
        return tenorBasisCondition;
    }

    public void setTenorBasisCondition(String tenorBasisCondition) {
        this.tenorBasisCondition = tenorBasisCondition;
    }

    public String getTenorDate() {
        return tenorDate;
    }

    public void setTenorDate(String tenorDate) {
        this.tenorDate = tenorDate;
    }

    public String getTenorBasisEvent() {
        return tenorBasisEvent;
    }

    public void setTenorBasisEvent(String tenorBasisEvent) {
        this.tenorBasisEvent = tenorBasisEvent;
    }
}
