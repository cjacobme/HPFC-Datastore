// Q1:
CREATE TABLE directory_finished (
	bucket INT, 
	directory_name TEXT, 
	directory_finished BOOLEAN, 
	PRIMARY KEY ((bucket), directory_name)) 
WITH CLUSTERING ORDER BY (directory_name ASC);

// Q2:
CREATE TABLE files_finished (
	directory_name TEXT, 
	file_name TEXT, 
	file_finished BOOLEAN, 
	PRIMARY KEY ((directory_name),file_name)
) WITH CLUSTERING ORDER BY (file_name ASC);

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

//Q: List imported wind speeds in U direction that were not calculated to total speed
CREATE TABLE tmp_wind_u (
	lokation_bezeichnung TEXT,
	prognosezeitpunkt TIMESTAMP,
	zeitpunkt TIMESTAMP,
	geogr_breite FLOAT,
	geogr_laenge FLOAT,
	wert DOUBLE,
	PRIMARY KEY ((lokation_bezeichnung, prognosezeitpunkt, zeitpunkt))
);

//Q: List imported wind speeds in V direction that were not calculated to total speed
CREATE TABLE tmp_wind_v (
	lokation_bezeichnung TEXT,
	prognosezeitpunkt TIMESTAMP,
	zeitpunkt TIMESTAMP,
	geogr_breite FLOAT,
	geogr_laenge FLOAT,
	wert DOUBLE,
	PRIMARY KEY ((lokation_bezeichnung, prognosezeitpunkt, zeitpunkt))
);

