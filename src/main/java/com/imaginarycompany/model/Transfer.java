package com.imaginarycompany.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@DatabaseTable(tableName = "transfers")
@NoArgsConstructor
@Getter
public class Transfer {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Account from;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Account to;

    @DatabaseField(canBeNull = false)
    private BigDecimal amount;

    @DatabaseField(canBeNull = false)
    private Date sent;

    public Transfer(Account from, Account to, BigDecimal amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        sent = new Date();
    }
}
