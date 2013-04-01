insert into cennik(rodzajFilmu, oplataZa1Dzien) values ('normalny',10.00);
insert into cennik(rodzajFilmu, oplataZa1Dzien) values ('hit',15.00);
insert into film (tytulFilmu, rokProdukcji,rodzajFilmu,adresOkladka) values ('batman', 2009, 'normalny','http://images.wikia.com/batman/images/b/b0/The-dark-knight-got-milk.jpg');
insert into film (tytulFilmu, rokProdukcji,rodzajFilmu,adresOkladka) values ('matrix', 2010, 'normalny','http://1.fwcdn.pl/po/06/28/628/7495038.3.jpg?l=1350559192000');
insert into film (tytulFilmu, rokProdukcji,rodzajFilmu,adresOkladka) values ('hoobbit', 2013, 'hit','http://3.bp.blogspot.com/-flkCoivN-xM/UOw-rCCRo7I/AAAAAAAACkw/rRnfnKHcpaw/s1600/The-Hobbit-1024x1024.jpg');

insert into aktor (imieAktora, nazwiskoAktora) values ('christian','Bale');
insert into aktor (imieAktora, nazwiskoAktora) values ('leaonardo','dicaprio');
insert into aktor (imieAktora, nazwiskoAktora) values ('ian','mckallen');


insert into aktor_film (idFilmu, idAktora,rola) values (1,1,'bruce wayne');
insert into aktor_film (idFilmu, idAktora,rola) values (3,3,'gandalf');

insert into gatunek (gatunekFilmu) values ('dramat');
insert into gatunek (gatunekFilmu) values ('sensacyjny');
insert into gatunek (gatunekFilmu) values ('fantasy');

insert into film_gatunek(idFilmu,gatunekFilmu) values (1,'dramat');
insert into film_gatunek(idFilmu,gatunekFilmu) values (1,'sensacyjny');

insert into film_gatunek(idFilmu,gatunekFilmu) values (2,'fantasy');

insert into plyta(idFilmu) values(1);
insert into plyta(idFilmu) values(1);
insert into plyta(idFilmu) values(2);

insert into klient(nazwiskoKlienta,password,login) values('kowalski','1234','kow');
insert into klient(nazwiskoKlienta,password,login) values('nowak','1234','now');

insert into wypozyczenie (idPlyty,idKlienta) values (1,1);
insert into wypozyczenie (idPlyty,idKlienta) values (2,1);
insert into wypozyczenie (idPlyty,idKlienta) values (3,2);

insert into doplata (idWypozyczenia,doplata) values (1,5.00);

 


