CREATE TABLE ad(
    id IDENTITY,
    typology VARCHAR(10),
    description VARCHAR,
    pictures_url ARRAY,
    house_size INT,
    garden_size INT,
    score INT,
    irrelevant_since DATE
)

ALTER TABLE ad add PRIMARY KEY ad_primary_key(id)