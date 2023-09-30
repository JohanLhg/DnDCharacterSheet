package com.jlahougue.dndcharactersheet.dal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Money.TABLE_MONEY)
class Money(
    @PrimaryKey
    @ColumnInfo(name = MONEY_CID)
    var cid: Long = 0,
    @ColumnInfo(name = MONEY_COPPER_PIECES)
    var copperPieces: Int = 0,
    @ColumnInfo(name = MONEY_SILVER_PIECES)
    var silverPieces: Int = 0,
    @ColumnInfo(name = MONEY_ELECTRUM_PIECES)
    var electrumPieces: Int = 0,
    @ColumnInfo(name = MONEY_GOLD_PIECES)
    var goldPieces: Int = 0,
    @ColumnInfo(name = MONEY_PLATINUM_PIECES)
    var platinumPieces: Int = 0,
    @ColumnInfo(name = MONEY_OTHER_CURRENCIES)
    var otherCurrencies: String = ""
) {
    companion object {
        const val TABLE_MONEY = "money"
        const val MONEY_CID = "cid"
        const val MONEY_COPPER_PIECES = "copper_pieces"
        const val MONEY_SILVER_PIECES = "silver_pieces"
        const val MONEY_ELECTRUM_PIECES = "electrum_pieces"
        const val MONEY_GOLD_PIECES = "gold_pieces"
        const val MONEY_PLATINUM_PIECES = "platinum_pieces"
        const val MONEY_OTHER_CURRENCIES = "other_currencies"
    }

    override fun toString(): String {
        return """
            $cid
            $copperPieces CP
            $silverPieces SP
            $electrumPieces EP
            $goldPieces GP
            $platinumPieces PP
            $otherCurrencies
        """.trimIndent()
    }
}