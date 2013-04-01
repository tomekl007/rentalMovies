drop table if exists film;
drop table if exists aktor;
drop table if exists aktor_film;
drop table if exists rodzajRoli;
drop table if exists gatunek;
drop table if exists film_gatunek;
drop table if exists doplata;


create table cennik (
	rodzajFilmu varchar(30) primary key,
	oplataZa1Dzien double
	
);



create table film (
  idFilmu identity,
  tytulFilmu varchar(50) not null,
  rokProdukcji integer not null,
  rodzajFilmu varchar(30),
  adresOkladka varchar(300),
  foreign key (rodzajFilmu) references cennik(rodzajFilmu)
  
 );

create table aktor(
	idAktora identity,
	imieAktora varchar(50),
	nazwiskoAktora varchar(50)
);

create table aktor_film(
	idFilmu integer not null,
	idAktora integer not null,
	rola varchar(20),
	primary key(idFilmu, idAktora),
 	foreign key (idFilmu) references film(idFilmu),
	foreign key (idAktora) references aktor(idAktora)
);

create table rodzajRoli(
	rola varchar(20) primary key,
	foreign key (rola) references aktor_film(rola)
);

create table gatunek(
     gatunekFilmu varchar(30) primary key
);

create table film_gatunek(
 	idFilmu integer not null,
 	gatunekFilmu varchar(20),
 	primary key(gatunekFilmu, idFilmu),
 	foreign key (idFilmu) references film(idFilmu),
 	foreign key (gatunekFilmu) references gatunek(gatunekFilmu)
);

create table plyta (
	idPlyty identity,
    idFilmu integer not null,
    foreign key (idFilmu) references film(idFilmu)

);

create table klient(
	idKlienta identity,
	nazwiskoKlienta varchar(30),
	imieKlienta varchar(30),
	plec varchar(1),
	dataWprowadzenia Date,
	password varchar(30) not null,
	login varchar(30) not null
	

);

create table wypozyczenie(
	idWypozyczenia identity,
	idPlyty integer not null,
	idKlienta integer not null,
	dataWypozyczenia Date,
	dataZwrotu Date,
	foreign key (idPlyty) references plyta(idPlyty),
	foreign key (idKlienta) references klient(idKlienta)
);


create table doplata(
	idWypozyczenia integer not null,
	doplata double,
	foreign key (idWypozyczenia) references wypozyczenie(idWypozyczenia)

);



