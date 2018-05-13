# --- !Ups

CREATE TABLE pedigrees(
    id UUID NOT NULL,
    name VARCHAR(32) NOT NULL
);

CREATE TABLE cats(
    id UUID NOT NULL,
    name VARCHAR(32) NOT NULL,
    pedigree_id uuid NOT NULL,
    gender VARCHAR(6) NOT NULL,
    owner_id uuid,
    date_of_birth DATE NOT NULL,
    date_of_death DATE
);

CREATE TABLE owners(
    id UUID NOT NULL,
    name VARCHAR(32)
);

ALTER TABLE pedigrees ADD CONSTRAINT pedigrees_id_pk PRIMARY KEY (id);
ALTER TABLE cats ADD CONSTRAINT cats_id_pk PRIMARY KEY (id);
ALTER TABLE owners ADD CONSTRAINT owners_id_pk PRIMARY KEY (id);

ALTER TABLE cats ADD CONSTRAINT cats_pedigrees_id_FK FOREIGN KEY (pedigree_id) REFERENCES pedigrees(id);
ALTER TABLE cats ADD CONSTRAINT cats_owners_id_FK FOREIGN KEY (owner_id) REFERENCES owners(id);

# --- !Downs

DROP TABLE cats;
DROP TABLE owners;
DROP TABLE pedigrees;