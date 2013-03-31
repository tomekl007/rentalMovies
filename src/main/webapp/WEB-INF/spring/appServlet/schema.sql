drop table if exists film;
drop table if exists aktor;
drop table if exists aktor_film;
drop table if exists rodzajRoli;

create table film (
  idFilmu identity,
  tytulFilmu varchar(50) not null,
  rokProdukcji integer not null,
  rodzajFilmu varchar(20) not null
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
