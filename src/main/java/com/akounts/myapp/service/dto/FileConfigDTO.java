package com.akounts.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.FileConfig} entity.
 */
public class FileConfigDTO implements Serializable {

    private Long id;

    private String filename;

    private String description;

    private Integer amount;

    private Integer amountIn;

    private Integer amountOut;

    private Integer accountNum;

    private Integer transactionDate;

    private String dateFormat;

    private String fieldSeparator;

    private Integer hasHeader;

    private String note;

    private Integer locale;

    private Integer multipleAccount;

    private String encoding;

    private Integer sign;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmountIn() {
        return amountIn;
    }

    public void setAmountIn(Integer amountIn) {
        this.amountIn = amountIn;
    }

    public Integer getAmountOut() {
        return amountOut;
    }

    public void setAmountOut(Integer amountOut) {
        this.amountOut = amountOut;
    }

    public Integer getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Integer accountNum) {
        this.accountNum = accountNum;
    }

    public Integer getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Integer transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getFieldSeparator() {
        return fieldSeparator;
    }

    public void setFieldSeparator(String fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }

    public Integer getHasHeader() {
        return hasHeader;
    }

    public void setHasHeader(Integer hasHeader) {
        this.hasHeader = hasHeader;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getLocale() {
        return locale;
    }

    public void setLocale(Integer locale) {
        this.locale = locale;
    }

    public Integer getMultipleAccount() {
        return multipleAccount;
    }

    public void setMultipleAccount(Integer multipleAccount) {
        this.multipleAccount = multipleAccount;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileConfigDTO)) {
            return false;
        }

        FileConfigDTO fileConfigDTO = (FileConfigDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fileConfigDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileConfigDTO{" +
            "id=" + getId() +
            ", filename='" + getFilename() + "'" +
            ", description='" + getDescription() + "'" +
            ", amount=" + getAmount() +
            ", amountIn=" + getAmountIn() +
            ", amountOut=" + getAmountOut() +
            ", accountNum=" + getAccountNum() +
            ", transactionDate=" + getTransactionDate() +
            ", dateFormat='" + getDateFormat() + "'" +
            ", fieldSeparator='" + getFieldSeparator() + "'" +
            ", hasHeader=" + getHasHeader() +
            ", note='" + getNote() + "'" +
            ", locale=" + getLocale() +
            ", multipleAccount=" + getMultipleAccount() +
            ", encoding='" + getEncoding() + "'" +
            ", sign=" + getSign() +
            "}";
    }
}
