DROP TABLE if EXISTS library_users;

CREATE TABLE library_users(
   id SERIAL PRIMARY KEY,
   full_name VARCHAR(45) NOT NULL,
   email VARCHAR(45) NOT NULL UNIQUE,
   date_of_birth DATE NOT NULL,
   address VARCHAR(255),
   phone_number VARCHAR(45),
   registration_date TIMESTAMP NOT NULL,
   user_role VARCHAR(10) NOT NULL
);



