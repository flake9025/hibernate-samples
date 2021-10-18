-- employers and constraints
CREATE TABLE employers (
	id IDENTITY NOT NULL PRIMARY KEY,
	name varchar NOT NULL
);

CREATE TABLE employees (
	employer_id int NOT NULL,
	person_id int NOT NULL,
	badge varchar NOT NULL,
	CONSTRAINT employees_pkey PRIMARY KEY (employer_id, person_id),
	CONSTRAINT employees_employer_fk FOREIGN KEY (employer_id) REFERENCES employers(id),
	CONSTRAINT employees_person_fk FOREIGN KEY (person_id) REFERENCES persons(id)
);
