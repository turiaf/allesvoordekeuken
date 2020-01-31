insert into artikels (naam, aankoopprijs, verkoopprijs, soort, houdbaarheid)
VALUES ('testFoodArtikel', 10, 10, 'F', 1);
insert into artikels (naam, aankoopprijs, verkoopprijs, soort, garantie)
VALUES ('testNonFoodArtikel', 10, 10, 'NF', 6);
insert into kortingen(artikelid, vanafAantal, percentage)
VALUES ((select id from artikels where naam= 'testFoodArtikel'), 2, 10);