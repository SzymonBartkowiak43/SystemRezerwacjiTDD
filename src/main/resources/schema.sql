
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(100) UNIQUE,
                       name VARCHAR(100),
                       password VARCHAR(200)
);

CREATE TABLE  user_role (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) UNIQUE,
                            description VARCHAR(100)
);

CREATE TABLE  user_roles (
                             user_id BIGINT,
                             role_id BIGINT,
                             PRIMARY KEY (user_id, role_id),
                             FOREIGN KEY (user_id) REFERENCES users(id),
                             FOREIGN KEY (role_id) REFERENCES user_role(id)
);

CREATE TABLE salon (
                       id bigint auto_increment primary key,
                       salon_name varchar(250),
                       category varchar(250),
                       city varchar(250),
                       zip_code varchar(10),
                       street varchar(250),
                       number varchar(220),
                       user_id BIGINT,
                       FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE code (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      code VARCHAR(255) NOT NULL,
                      is_active BOOLEAN DEFAULT TRUE,
                      data_generated TIMESTAMP NOT NULL,
                      data_consumption TIMESTAMP,
                      user_id BIGINT,
                      CONSTRAINT fk_user
                          FOREIGN KEY (user_id) REFERENCES users(id)
);
