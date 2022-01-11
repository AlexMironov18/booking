INSERT INTO flight
    (flight_id, departure, destination, departure_time, arrival_time, load_date, update_date)
VALUES
    ('fedbd17c-eae3-4b4f-9fd0-b132fdb12dee', 'London', 'Madrid', '2015-12-31 15:41:00', '2015-12-31 18:41:00', '2016-01-01 12:00:00', '2016-01-01 12:00:00');

INSERT INTO customer
    (customer_id, user_id, name, last_name, middle_name, birth_date, passport, load_date, update_date)
VALUES
    ('6dc4c8d8-7b1f-457b-9323-824a05e60987', '1', 'customer name', 'customer last name', 'customer middle name', '1970-01-01', '111112', '2016-01-01 12:00:00', '2016-01-01 12:00:00');

INSERT INTO ticket
    (ticket_id, place, price, status, flight_id, customer_id, load_date, update_date)
VALUES
    ('634bc048-c6d9-4d36-b04a-a2e8700ca0a3', '1', '100', 'BOOKED', 'fedbd17c-eae3-4b4f-9fd0-b132fdb12dee', '6dc4c8d8-7b1f-457b-9323-824a05e60987', '2016-01-01 12:00:00', '2016-01-01 12:00:00'),
    ('1da6ff0f-4ad8-4bbc-8d95-d43aa9b46e68', '2', '200', 'BOOKED', 'fedbd17c-eae3-4b4f-9fd0-b132fdb12dee', '6dc4c8d8-7b1f-457b-9323-824a05e60987', '2016-01-01 12:00:00', '2016-01-01 12:00:00');