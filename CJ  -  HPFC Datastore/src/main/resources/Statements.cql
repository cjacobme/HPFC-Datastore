CREATE KEYSPACE HPFCDS WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};
USE HPFCDS;

// Q1:
CREATE TABLE directory_finished (
	bucket INT, 
	directory_name TEXT, 
	directory_finished BOOLEAN, 
	PRIMARY KEY ((bucket), directory_name)) 
WITH CLUSTERING ORDER BY (directory_name ASC);

/* SELECT directory_finished FROM directory_finished WHERE directory_name IN (?,?); */

// Q2:
CREATE TABLE files_finished (
	directory_name TEXT, 
	file_name TEXT, 
	file_finished BOOLEAN, 
	PRIMARY KEY ((directory_name),file_name)
) WITH CLUSTERING ORDER BY (file_name ASC);

/* SELECT file_finished FROM files_finished WHERE directory_name=? and file_name IN (?,?); */

CREATE TABLE weather_values (
	lokation_bezeichnung TEXT,
	meteo_measure TEXT,
	prognosezeitpunkt TIMESTAMP,
	zeitpunkt TIMESTAMP,
	geogr_breite FLOAT static,
	geogr_laenge FLOAT static,
	wert DOUBLE,
	PRIMARY KEY((lokation_bezeichnung, meteo_measure, prognosezeitpunkt), zeitpunkt)
);

CREATE TABLE lokation (
	bucket INT,
	bezeichnung TEXT,
	geogr_breite FLOAT,
	geogr_laenge FLOAT,
	PRIMARY KEY((bucket), bezeichnung)
);