CREATE TABLE player(
    id BIGSERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    age INT NOT NULL,
    date_of_birth DATE NOT NULL,
    phone_number TEXT NOT NULL,
    email TEXT NOT NULL,
    gender TEXT NOT NULL,
    team TEXT NOT NULL,
    terms_accepted BOOLEAN NOT NULL
)