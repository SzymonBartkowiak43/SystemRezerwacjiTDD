INSERT INTO salon (salon_name, category, city, zip_code, street, number) VALUES
                                                                             ('Relaxing Touch', 'massage', 'Warsaw', '00-001', 'Main Street', '10'),
                                                                             ('Glamour Nails', 'beauty', 'Krakow', '30-002', 'Flower Avenue', '15B');
insert into
    users (email, name, password)
values
    ('user@wp.com','user', 'user');

insert into
    user_role (name, description)
values
    ('USER', 'podstawowe uprawnienia, możliwość oddawania głosów');

insert into
    user_roles (user_id, role_id)
values
    (1, 1);