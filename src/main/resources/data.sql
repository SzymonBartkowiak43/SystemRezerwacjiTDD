insert into
    users (email, name, password)
values
    ('user@wp.com','user', 'user'),
    ('employee@wp.com', 'employee', 'employee');

INSERT INTO user_role (name, description)
VALUES
    ('USER', 'People who would like to make reservations'),
    ('EMPLOYEE', 'Staff members who manage reservations and services'),
    ('ADMIN', 'Administrators with full system access and management capabilities'),
    ('OWNER', 'Salon owners with management access to their respective salons');

insert into
    user_roles (user_id, role_id)
values
    (1, 1),
    (2, 2);

INSERT INTO salon (salon_name, category, city, zip_code, street, number,user_id) VALUES
                                                                             ('Relaxing Touch', 'massage', 'Warsaw', '00-001', 'Main Street', '10',1),
                                                                             ('Glamour Nails', 'beauty', 'Krakow', '30-002', 'Flower Avenue', '15B',1);

INSERT INTO code (code, is_consumed, data_generated, data_consumption, user_id) VALUES
                                                                                  ('123e4567-e89b-12d3-a456-426614174000', FALSE, '2024-11-03 10:00:00', NULL, NULL);

INSERT INTO opening_hours (day_of_week, opening_time, closing_time, salon_id) VALUES
                                                                                  ('MONDAY', '09:00:00', '18:00:00', 2),
                                                                                  ('TUESDAY', '09:00:00', '18:00:00', 2),
                                                                                  ('WEDNESDAY', '09:00:00', '18:00:00', 2),
                                                                                  ('THURSDAY', '10:00:00', '17:00:00', 2),
                                                                                  ('FRIDAY', '09:00:00', '18:00:00', 2),
                                                                                  ('SATURDAY', '10:00:00', '15:00:00', 2),
                                                                                  ('SUNDAY', NULL, NULL, 2);

INSERT INTO employee (user_id, salon_id) VALUES (2, 1);

INSERT INTO employee_availability (day_of_week, start_time, end_time, employee_id) VALUES
                                                                                       ('MONDAY', '09:00:00', '17:00:00', 1),
                                                                                       ('TUESDAY', '09:00:00', '17:00:00', 1),
                                                                                       ('WEDNESDAY', '12:00:00', '18:00:00', 1),
                                                                                       ('THURSDAY', '12:00:00', '17:00:00', 1),
                                                                                       ('FRIDAY', '12:00:00', '14:00:00', 1),
                                                                                       ('SATURDAY', '10:00:00', '15:00:00', 1),
                                                                                       ('SUNDAY', NULL, NULL, 1);

INSERT INTO offer (name, description, price, duration, salon_id)
VALUES
    ('Haircut', 'Basic haircut service', 50.00, '00:30:00', 2),
    ('Massage', 'Full body massage', 120.00, '01:00:00', 1),
    ('Manicure', 'Professional manicure service', 70.00, '00:30:00', 2);

INSERT INTO employee_offers (employee_id, offer_id)
VALUES
    (1,1),
    (1,3);

INSERT INTO reservation (salon_id, employee_id, user_id, offer_id, reservation_date_time) VALUES
    (1, 1, 1, 1, '2024-11-18 14:30:00'),
    (1, 1, 1, 1, '2024-11-18 15:00:00'),
    (1, 1, 1, 1, '2024-11-18 15:30:00');
