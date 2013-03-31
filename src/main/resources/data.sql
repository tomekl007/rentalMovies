insert into cennik(rodzajFilmu, oplataZa1Dzien) values ('normalny',10.00);
insert into cennik(rodzajFilmu, oplataZa1Dzien) values ('hit',15.00);
insert into film (tytulFilmu, rokProdukcji,rodzajFilmu) values ('batman', 2009, 'normalny');
insert into film (tytulFilmu, rokProdukcji,rodzajFilmu) values ('matrix', 2010, 'normalny');
insert into film (tytulFilmu, rokProdukcji,rodzajFilmu) values ('hoobbit', 2013, 'hit');

insert into aktor (imieAktora, nazwiskoAktora) values ('christian','Bale');
insert into aktor (imieAktora, nazwiskoAktora) values ('leaonardo','dicaprio');
insert into aktor (imieAktora, nazwiskoAktora) values ('ian','mckallen');


insert into aktor_film (idFilmu, idAktora,rola) values (1,1,'bruce wayne');
insert into aktor_film (idFilmu, idAktora,rola) values (2,3,'gandalf');

insert into gatunek (gatunekFilmu) values ('dramat');
insert into gatunek (gatunekFilmu) values ('sensacyjny');
insert into gatunek (gatunekFilmu) values ('fantasy');

insert into film_gatunek(idFilmu,gatunekFilmu) values (1,'dramat');
insert into film_gatunek(idFilmu,gatunekFilmu) values (1,'sensacyjny');

insert into film_gatunek(idFilmu,gatunekFilmu) values (2,'fantasy');
