insert into artikels (naam, aankoopprijs, verkoopprijs, soort, houdbaarheid, artikelgroepid)
VALUES ('testFoodArtikel', 10, 10, 'F', 1, (select id from artikelgroepen where naam = 'test'));
insert into artikels (naam, aankoopprijs, verkoopprijs, soort, garantie, artikelgroepid)
VALUES ('testNonFoodArtikel', 10, 10, 'NF', 6, (select id from artikelgroepen where naam = 'test'));
insert into kortingen(artikelid, vanafAantal, percentage)
VALUES ((select id from artikels where naam= 'testFoodArtikel'), 2, 10);