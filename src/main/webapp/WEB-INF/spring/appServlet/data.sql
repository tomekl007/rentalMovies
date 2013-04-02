insert into cennik(rodzajFilmu, oplataZa1Dzien) values ('normalny',10.00);
insert into cennik(rodzajFilmu, oplataZa1Dzien) values ('hit',15.00);
insert into cennik(rodzajFilmu, oplataZa1Dzien) values ('nowosc',12.00);

insert into film (tytulFilmu, rokProdukcji,rodzajFilmu,adresOkladka,linkDoFilmweb) values ('batman', 2009, 'normalny','http://images.wikia.com/batman/images/b/b0/The-dark-knight-got-milk.jpg','http://www.filmweb.pl/film/Mroczny+Rycerz+powstaje-2012-506756');
insert into film (tytulFilmu, rokProdukcji,rodzajFilmu,adresOkladka,linkDoFilmweb) values ('matrix', 2010, 'normalny','http://1.fwcdn.pl/po/06/28/628/7495038.3.jpg?l=1350559192000','http://www.filmweb.pl/Matrix');
insert into film (tytulFilmu, rokProdukcji,rodzajFilmu,adresOkladka,linkDoFilmweb) values ('hoobbit', 2013, 'hit','http://3.bp.blogspot.com/-flkCoivN-xM/UOw-rCCRo7I/AAAAAAAACkw/rRnfnKHcpaw/s1600/The-Hobbit-1024x1024.jpg','http://www.filmweb.pl/film/Hobbit%3A+Niezwyk%C5%82a+podr%C3%B3%C5%BC-2012-343217');
insert into film (tytulFilmu, rokProdukcji,rodzajFilmu,adresOkladka,linkDoFilmweb) values ('atlas chmur', 2013, 'nowosc','http://ecsmedia.pl/c/cloud-atlas-atlas-chmur-b-iext12978565.jpg','http://www.filmweb.pl/film/Atlas+chmur-2012-580801');
insert into film (tytulFilmu, rokProdukcji,rodzajFilmu,adresOkladka,linkDoFilmweb) values ('djanngo', 2013, 'nowosc','http://4.bp.blogspot.com/-lr_tfZRP8qY/UPvNAZnYsKI/AAAAAAAAGCs/T51lLmuzGUc/s1600/django-unchained-poster3.jpg','http://www.filmweb.pl/film/Django-2012-620541');
insert into film (tytulFilmu, rokProdukcji,rodzajFilmu,adresOkladka,linkDoFilmweb) values ('operacja argo', 2013, 'nowosc','http://1.fwcdn.pl/po/41/65/614165/7496968.3.jpg?l=1351310746000','http://www.filmweb.pl/film/Operacja+Argo-2012-614165');
insert into film (tytulFilmu, rokProdukcji,rodzajFilmu,adresOkladka,idFilmu,linkDoFilmweb) values ('szeregowiec ryan', 2000, 'normalny','/resources/images/ryan.jpg',99,'http://www.filmweb.pl/Szeregowiec.Ryan');



insert into aktor (imieAktora, nazwiskoAktora) values ('christian','Bale');
insert into aktor (imieAktora, nazwiskoAktora) values ('leaonardo','dicaprio');
insert into aktor (imieAktora, nazwiskoAktora) values ('ian','mckallen');
insert into aktor (imieAktora, nazwiskoAktora) values ('Tom','Hanks');
insert into aktor (imieAktora, nazwiskoAktora) values ('BEn','Afflec');
insert into aktor (imieAktora, nazwiskoAktora) values ('Matt','Damon');
insert into aktor (imieAktora, nazwiskoAktora) values ('Gary','Oldman');



insert into aktor_film (idFilmu, idAktora,rola) values (1,1,'bruce wayne');
insert into aktor_film (idFilmu, idAktora,rola) values (3,3,'gandalf');
insert into aktor_film (idFilmu, idAktora,rola) values (4,4,'wedrowiec');
insert into aktor_film (idFilmu, idAktora,rola) values (6,2,'clyde');
insert into aktor_film (idFilmu, idAktora,rola) values (2,5,'agent CIA');
insert into aktor_film (idFilmu, idAktora,rola) values (1,7,'jim gordon');
insert into aktor_film (idFilmu, idAktora,rola) values (99,6,'szeregowiec Ryan');

insert into gatunek (gatunekFilmu) values ('dramat');
insert into gatunek (gatunekFilmu) values ('sensacyjny');
insert into gatunek (gatunekFilmu) values ('fantasy');
insert into gatunek (gatunekFilmu) values ('akcja');
insert into gatunek (gatunekFilmu) values ('bajka');
insert into gatunek (gatunekFilmu) values ('komedia');

insert into film_gatunek(idFilmu,gatunekFilmu) values (1,'dramat');
insert into film_gatunek(idFilmu,gatunekFilmu) values (1,'sensacyjny');
insert into film_gatunek(idFilmu,gatunekFilmu) values (2,'fantasy');
insert into film_gatunek(idFilmu,gatunekFilmu) values (2,'akcja');
insert into film_gatunek(idFilmu,gatunekFilmu) values (3,'fantasy');
insert into film_gatunek(idFilmu,gatunekFilmu) values (4,'fantasy');
insert into film_gatunek(idFilmu,gatunekFilmu) values (5,'dramat');
insert into film_gatunek(idFilmu,gatunekFilmu) values (5,'komedia');
insert into film_gatunek(idFilmu,gatunekFilmu) values (5,'akcja');
insert into film_gatunek(idFilmu,gatunekFilmu) values (6,'akcja');

insert into plyta(idFilmu) values(1);
insert into plyta(idFilmu) values(1);
insert into plyta(idFilmu) values(2);
insert into plyta(idFilmu) values(3);
insert into plyta(idFilmu) values(4);
insert into plyta(idFilmu) values(5);
insert into plyta(idFilmu) values(6);
insert into plyta(idFilmu) values(4);
insert into plyta(idFilmu) values(5);
insert into plyta(idFilmu) values(6);



insert into klient(nazwiskoKlienta,password,login) values('kowalski','1234','kow');
insert into klient(nazwiskoKlienta,password,login) values('nowak','1234','now');
insert into klient(nazwiskoKlienta,password,login) values('admin','admin','admin');

insert into wypozyczenie (idPlyty,idKlienta,dataWypozyczenia,dataZwrotu) values (1,1,'2013-01-03','2013-01-12');
insert into wypozyczenie (idPlyty,idKlienta,dataWypozyczenia) values (2,1,'2013-01-03');
insert into wypozyczenie (idPlyty,idKlienta,dataWypozyczenia) values (3,2,'2013-03-29');

insert into doplata (idWypozyczenia,doplata) values (1,5.00);

 


