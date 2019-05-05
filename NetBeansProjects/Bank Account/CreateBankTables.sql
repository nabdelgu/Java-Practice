CREATE TABLE `BankAccounts` (
	`BankName`	TEXT NOT NULL,
	`RoutingNumber`	INTEGER NOT NULL,
	`AccountNumber`	INTEGER NOT NULL,
	`Balance`	INTEGER NOT NULL,
	PRIMARY KEY(`BankName`,`AccountNumber`)
);