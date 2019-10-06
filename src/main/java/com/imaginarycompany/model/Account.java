package com.imaginarycompany.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

@DatabaseTable(tableName = "accounts")
@Data
public class Account {

    private static final String IBAN_PREFIX = "PL";
    private static final int IBAN_LENGTH = 26;

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false)
    private String accountNo;

    @DatabaseField(canBeNull = false)
    private BigDecimal balance;

    public Account() {
        balance = new BigDecimal(0);
        accountNo = IBAN_PREFIX + RandomStringUtils.randomNumeric(IBAN_LENGTH);
    }
}
