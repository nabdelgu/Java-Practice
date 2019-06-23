CREATE TABLE "BankAccounts" ( `BankName` TEXT NOT NULL, `RoutingNumber` INTEGER NOT NULL, `AccountNumber` INTEGER NOT NULL, `Balance` INTEGER NOT NULL, PRIMARY KEY(`BankName`,`AccountNumber`) )

CREATE TABLE "BankTransaction" (
	"BankName"	TEXT NOT NULL,
	"AccountNumber"	INTEGER NOT NULL,
	"TransactionID"	INTEGER NOT NULL,
	PRIMARY KEY("BankName","AccountNumber","TransactionID")
)

CREATE TABLE "TransactionDetails" (
	"TransactionID"	INTEGER NOT NULL UNIQUE,
	"TransactionType"	TEXT NOT NULL,
	"TransactionAmount"	INTEGER NOT NULL,
	"Balance"	INTEGER NOT NULL,
	PRIMARY KEY("TransactionID")
)