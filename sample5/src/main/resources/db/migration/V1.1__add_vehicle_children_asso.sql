-- vehicles and constraints
CREATE TABLE vehicles (
	id IDENTITY NOT NULL PRIMARY KEY,
	model varchar NOT NULL
);

ALTER TABLE persons ADD COLUMN vehicle_id int;
ALTER TABLE persons ADD CONSTRAINT vehicle_fk FOREIGN KEY (vehicle_id) REFERENCES vehicles(id);

-- children and constraints
CREATE TABLE children (
	id IDENTITY NOT NULL PRIMARY KEY,
	firstname varchar NOT NULL,
	father_id int,
	mother_id int,
	CONSTRAINT father_fk FOREIGN KEY (father_id) REFERENCES persons(id),
	CONSTRAINT mother_fk FOREIGN KEY (mother_id) REFERENCES persons(id)
);

-- associations and constraints
CREATE TABLE associations (
	id IDENTITY NOT NULL PRIMARY KEY,
	name varchar NOT NULL
);

CREATE TABLE associations_persons (
	association_id int NOT NULL,
	person_id int NOT NULL,
	CONSTRAINT associations_persons_pkey PRIMARY KEY (association_id, person_id),
	CONSTRAINT association_fk FOREIGN KEY (association_id) REFERENCES associations(id),
	CONSTRAINT person_fk FOREIGN KEY (person_id) REFERENCES persons(id)
);
