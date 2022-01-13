package com.akounts.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FileConfig.
 */
@Entity
@Table(name = "file_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FileConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "amount_in")
    private Integer amountIn;

    @Column(name = "amount_out")
    private Integer amountOut;

    @Column(name = "account_num")
    private Integer accountNum;

    @Column(name = "transaction_date")
    private Integer transactionDate;

    @Column(name = "date_format")
    private String dateFormat;

    @Column(name = "field_separator")
    private String fieldSeparator;

    @Column(name = "has_header")
    private Integer hasHeader;

    @Column(name = "note")
    private String note;

    @Column(name = "locale")
    private Integer locale;

    @Column(name = "multiple_account")
    private Integer multipleAccount;

    @Column(name = "encoding")
    private String encoding;

    @Column(name = "sign")
    private Integer sign;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FileConfig id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return this.filename;
    }

    public FileConfig filename(String filename) {
        this.setFilename(filename);
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDescription() {
        return this.description;
    }

    public FileConfig description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public FileConfig amount(Integer amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmountIn() {
        return this.amountIn;
    }

    public FileConfig amountIn(Integer amountIn) {
        this.setAmountIn(amountIn);
        return this;
    }

    public void setAmountIn(Integer amountIn) {
        this.amountIn = amountIn;
    }

    public Integer getAmountOut() {
        return this.amountOut;
    }

    public FileConfig amountOut(Integer amountOut) {
        this.setAmountOut(amountOut);
        return this;
    }

    public void setAmountOut(Integer amountOut) {
        this.amountOut = amountOut;
    }

    public Integer getAccountNum() {
        return this.accountNum;
    }

    public FileConfig accountNum(Integer accountNum) {
        this.setAccountNum(accountNum);
        return this;
    }

    public void setAccountNum(Integer accountNum) {
        this.accountNum = accountNum;
    }

    public Integer getTransactionDate() {
        return this.transactionDate;
    }

    public FileConfig transactionDate(Integer transactionDate) {
        this.setTransactionDate(transactionDate);
        return this;
    }

    public void setTransactionDate(Integer transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    public FileConfig dateFormat(String dateFormat) {
        this.setDateFormat(dateFormat);
        return this;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getFieldSeparator() {
        return this.fieldSeparator;
    }

    public FileConfig fieldSeparator(String fieldSeparator) {
        this.setFieldSeparator(fieldSeparator);
        return this;
    }

    public void setFieldSeparator(String fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }

    public Integer getHasHeader() {
        return this.hasHeader;
    }

    public FileConfig hasHeader(Integer hasHeader) {
        this.setHasHeader(hasHeader);
        return this;
    }

    public void setHasHeader(Integer hasHeader) {
        this.hasHeader = hasHeader;
    }

    public String getNote() {
        return this.note;
    }

    public FileConfig note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getLocale() {
        return this.locale;
    }

    public FileConfig locale(Integer locale) {
        this.setLocale(locale);
        return this;
    }

    public void setLocale(Integer locale) {
        this.locale = locale;
    }

    public Integer getMultipleAccount() {
        return this.multipleAccount;
    }

    public FileConfig multipleAccount(Integer multipleAccount) {
        this.setMultipleAccount(multipleAccount);
        return this;
    }

    public void setMultipleAccount(Integer multipleAccount) {
        this.multipleAccount = multipleAccount;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public FileConfig encoding(String encoding) {
        this.setEncoding(encoding);
        return this;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Integer getSign() {
        return this.sign;
    }

    public FileConfig sign(Integer sign) {
        this.setSign(sign);
        return this;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileConfig)) {
            return false;
        }
        return id != null && id.equals(((FileConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileConfig{" +
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
