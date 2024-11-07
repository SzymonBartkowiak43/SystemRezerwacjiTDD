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
                      is_consumed BOOLEAN DEFAULT FALSE,
                      data_generated TIMESTAMP NOT NULL,
                      data_consumption TIMESTAMP,
                      user_id BIGINT,
                      CONSTRAINT fk_user
                          FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE opening_hours (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               day_of_week VARCHAR(20) NOT NULL,
                               opening_time TIME,
                               closing_time TIME,
                               salon_id BIGINT,
                               FOREIGN KEY (salon_id) REFERENCES salon(id) ON DELETE CASCADE
);

CREATE TABLE employee (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          user_id BIGINT NOT NULL,
                          salon_id BIGINT,
                          FOREIGN KEY (salon_id) REFERENCES salon(id),
                          FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE employee_availability (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       day_of_week VARCHAR(10) NOT NULL,
                                       start_time TIME,
                                       end_time TIME,
                                       employee_id BIGINT,
                                       FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE TABLE offer (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         description TEXT,
                         price DECIMAL(10, 2),
                         duration TIME,
                         salon_id BIGINT,
                         FOREIGN KEY (salon_id) REFERENCES salon(id) ON DELETE CASCADE
);

CREATE TABLE employee_offers (
                                 employee_id BIGINT,
                                 offer_id BIGINT,
                                 PRIMARY KEY (employee_id, offer_id),
                                 FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE,
                                 FOREIGN KEY (offer_id) REFERENCES offer(id) ON DELETE CASCADE
);