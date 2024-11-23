
CREATE TABLE cities (
                        city_id BIGINT NOT NULL,
                        city_name VARCHAR(255),
                        PRIMARY KEY (city_id)
) ENGINE=InnoDB;

CREATE TABLE cities_seq (
                            next_val BIGINT
) ENGINE=InnoDB;

INSERT INTO cities_seq VALUES (1);

CREATE TABLE parcel_clients (
                                parcel_client_id BIGINT NOT NULL,
                                mail VARCHAR(255),
                                phone_number VARCHAR(255),
                                PRIMARY KEY (parcel_client_id)
) ENGINE=InnoDB;

CREATE TABLE parcel_clients_seq (
                                    next_val BIGINT
) ENGINE=InnoDB;

INSERT INTO parcel_clients_seq VALUES (1);

CREATE TABLE post_office_seq (
                                 next_val BIGINT
) ENGINE=InnoDB;

INSERT INTO post_office_seq VALUES (1);

CREATE TABLE postal_cars (
                             post_office_id BIGINT,
                             postal_car_id BIGINT NOT NULL,
                             car_number VARCHAR(255),
                             color VARCHAR(255),
                             vin_code VARCHAR(255),
                             PRIMARY KEY (postal_car_id)
) ENGINE=InnoDB;

CREATE TABLE postal_cars_seq (
                                 next_val BIGINT
) ENGINE=InnoDB;

INSERT INTO postal_cars_seq VALUES (1);

CREATE TABLE postal_parcels (
                                parcel_height DOUBLE NOT NULL,
                                parcel_length DOUBLE NOT NULL,
                                parcel_weight DOUBLE NOT NULL,
                                parcel_width DOUBLE NOT NULL,
                                city_id BIGINT,
                                parcel_client_id BIGINT,
                                post_office_id BIGINT,
                                postal_parcel_id BIGINT NOT NULL,
                                method_of_receiving ENUM('COURIER', 'PICKUP'),
                                parcel_status ENUM('ARRIVED_AT_THE_POST_OFFICE', 'ON_THE_WAY', 'RECEIVED', 'REFUSAL_TO_RECEIVED'),
                                PRIMARY KEY (postal_parcel_id)
) ENGINE=InnoDB;

CREATE TABLE postal_parcels_seq (
                                    next_val BIGINT
) ENGINE=InnoDB;

INSERT INTO postal_parcels_seq VALUES (1);

CREATE TABLE post_office (
                             latitude DOUBLE NOT NULL,
                             longitude DOUBLE NOT NULL,
                             city_id BIGINT,
                             post_office_id BIGINT NOT NULL,
                             house_number VARCHAR(255),
                             street VARCHAR(255),
                             PRIMARY KEY (post_office_id)
) ENGINE=InnoDB;

CREATE TABLE route_distances (
                                 distance DOUBLE NOT NULL,
                                 post_office_id BIGINT,
                                 postal_car_id BIGINT,
                                 route_distance_id BIGINT NOT NULL,
                                 PRIMARY KEY (route_distance_id)
) ENGINE=InnoDB;

CREATE TABLE route_distances_seq (
                                     next_val BIGINT
) ENGINE=InnoDB;

INSERT INTO route_distances_seq VALUES (1);

-- Add constraints
ALTER TABLE parcel_clients
    ADD CONSTRAINT UKbv1qnngkxfy748fnjgurumuky UNIQUE (phone_number);

ALTER TABLE postal_cars
    ADD CONSTRAINT FKa8kyvw2k8c1kerlw0yhf54h7j
        FOREIGN KEY (post_office_id)
            REFERENCES post_office (post_office_id);

ALTER TABLE postal_parcels
    ADD CONSTRAINT FKcxggmlm4ebrjhmscaary62fmc
        FOREIGN KEY (city_id)
            REFERENCES cities (city_id);

ALTER TABLE postal_parcels
    ADD CONSTRAINT FKq6hiiwlt7330pwg91ncu3t14d
        FOREIGN KEY (post_office_id)
            REFERENCES post_office (post_office_id);

ALTER TABLE postal_parcels
    ADD CONSTRAINT FK7tne0mb04s4da5sm4o92hqtfe
        FOREIGN KEY (parcel_client_id)
            REFERENCES parcel_clients (parcel_client_id);

ALTER TABLE post_office
    ADD CONSTRAINT FKjpb5i49fub69rjvpxljecjhn7
        FOREIGN KEY (city_id)
            REFERENCES cities (city_id);

ALTER TABLE route_distances
    ADD CONSTRAINT FKscp5vwbp7p1vylyc519s3okcy
        FOREIGN KEY (postal_car_id)
            REFERENCES postal_cars (postal_car_id);

ALTER TABLE route_distances
    ADD CONSTRAINT FKt3bu1kthyvq2e24dyq30ts248
        FOREIGN KEY (post_office_id)
            REFERENCES post_office (post_office_id);




# /////////////////////////////////////////////



# CREATE TABLE cities (
#                         city_id BIGINT AUTO_INCREMENT PRIMARY KEY,
#                         city_name VARCHAR(255) NOT NULL
# );
#
# CREATE TABLE parcel_clients (
#                                 parcel_client_id BIGINT AUTO_INCREMENT PRIMARY KEY,
#                                 phone_number VARCHAR(255) UNIQUE NOT NULL,
#                                 mail VARCHAR(255)
# );
#
# CREATE TABLE post_offices (
#                               post_office_id BIGINT AUTO_INCREMENT PRIMARY KEY,
#                               city_id BIGINT,
#                               street VARCHAR(255),
#                               house_number VARCHAR(50),
#                               FOREIGN KEY (city_id) REFERENCES cities(city_id) ON DELETE CASCADE
# );
#
# CREATE TABLE postal_cars (
#                              postal_car_id BIGINT AUTO_INCREMENT PRIMARY KEY,
#                              color VARCHAR(255),
#                              vin_code VARCHAR(255),
#                              car_number VARCHAR(50),
#                              post_office_id BIGINT,
#                              FOREIGN KEY (post_office_id) REFERENCES post_offices(post_office_id) ON DELETE CASCADE
# );
#
# CREATE TABLE route_distances (
#                                  route_distance_id BIGINT AUTO_INCREMENT PRIMARY KEY,
#                                  distance DOUBLE NOT NULL,
#                                  post_office_id BIGINT,
#                                  postal_car_id BIGINT,
#                                  FOREIGN KEY (post_office_id) REFERENCES post_offices(post_office_id) ON DELETE CASCADE,
#                                  FOREIGN KEY (postal_car_id) REFERENCES postal_cars(postal_car_id) ON DELETE CASCADE
# );
#
# CREATE TABLE postal_parcels (
#                                 postal_parcel_id BIGINT AUTO_INCREMENT PRIMARY KEY,
#                                 parcel_status VARCHAR(255) NOT NULL,
#                                 parcel_sender_id BIGINT,
#                                 parcel_recipient_id BIGINT,
#                                 city_id BIGINT,
#                                 start_post_office_id BIGINT,
#                                 end_post_office_id BIGINT,
#                                 method_of_receiving VARCHAR(255) NOT NULL,
#                                 parcel_width DOUBLE NOT NULL,
#                                 parcel_height DOUBLE NOT NULL,
#                                 parcel_length DOUBLE NOT NULL,
#                                 parcel_weight DOUBLE NOT NULL,
#                                 FOREIGN KEY (parcel_sender_id) REFERENCES parcel_clients(parcel_client_id) ON DELETE CASCADE,
#                                 FOREIGN KEY (parcel_recipient_id) REFERENCES parcel_clients(parcel_client_id) ON DELETE CASCADE,
#                                 FOREIGN KEY (city_id) REFERENCES cities(city_id) ON DELETE CASCADE,
#                                 FOREIGN KEY (start_post_office_id) REFERENCES post_offices(post_office_id) ON DELETE CASCADE,
#                                 FOREIGN KEY (end_post_office_id) REFERENCES post_offices(post_office_id) ON DELETE CASCADE
# );
#
#
# -- Relationships for one-to-many:
# ALTER TABLE postal_parcels ADD CONSTRAINT fk_parcel_sender
#     FOREIGN KEY (parcel_sender_id) REFERENCES parcel_clients(parcel_client_id) ON DELETE SET NULL;
#
# ALTER TABLE postal_parcels ADD CONSTRAINT fk_parcel_recipient
#     FOREIGN KEY (parcel_recipient_id) REFERENCES parcel_clients(parcel_client_id) ON DELETE SET NULL;




INSERT INTO cities (city_id, city_name) VALUES (1000, 'Киев');
INSERT INTO cities (city_id, city_name) VALUES (1001, 'Львов');
INSERT INTO cities (city_id, city_name) VALUES (1002, 'Одесса');
INSERT INTO cities (city_id, city_name) VALUES (1003, 'Харьков');
INSERT INTO cities (city_id, city_name) VALUES (1004, 'Днепр');

INSERT INTO post_office (post_office_id, latitude, longitude, city_id, house_number, street) VALUES (1000, 50.4501, 30.5234, 1000, '1', 'Хрещатик');
INSERT INTO post_office (post_office_id, latitude, longitude, city_id, house_number, street) VALUES (1001, 49.8397, 24.0297, 1001, '2', 'Проспект Свободы');
INSERT INTO post_office (post_office_id, latitude, longitude, city_id, house_number, street) VALUES (1002, 46.4825, 30.7233, 1002, '3', 'Дерибасовская');
INSERT INTO post_office (post_office_id, latitude, longitude, city_id, house_number, street) VALUES (1003, 50.0000, 36.2500, 1003, '4', 'Сумская');
INSERT INTO post_office (post_office_id, latitude, longitude, city_id, house_number, street) VALUES (1004, 48.4647, 35.0462, 1004, '5', 'Соборная');
INSERT INTO post_office (post_office_id, latitude, longitude, city_id, house_number, street) VALUES (1005, 50.4501, 30.5234, 1000, '6', 'Владимирская');
INSERT INTO post_office (post_office_id, latitude, longitude, city_id, house_number, street) VALUES (1006, 49.8397, 24.0297, 1001, '7', 'Железная');

INSERT INTO postal_cars (postal_car_id, post_office_id, car_number, color, vin_code) VALUES (1000, 1000, 'A123BC', 'Белый', '1HGCM82633A004352');
INSERT INTO postal_cars (postal_car_id, post_office_id, car_number, color, vin_code) VALUES (1001, 1000, 'B456DE', 'Синий', '2G1WB58K481156789');
INSERT INTO postal_cars (postal_car_id, post_office_id, car_number, color, vin_code) VALUES (1002, 1001, 'C789FG', 'Красный', '3VWLL7AJ9BM012345');
INSERT INTO postal_cars (postal_car_id, post_office_id, car_number, color, vin_code) VALUES (1003, 1001, 'D012HI', 'Черный', '4T1BE32K45U567890');
INSERT INTO postal_cars (postal_car_id, post_office_id, car_number, color, vin_code) VALUES (1004, 1002, 'E345JK', 'Зеленый', '5FNRL38487B012345');
INSERT INTO postal_cars (postal_car_id, post_office_id, car_number, color, vin_code) VALUES (1005, 1002, 'F678LM', 'Желтый', '6G2EC57Y13K123456');
INSERT INTO postal_cars (postal_car_id, post_office_id, car_number, color, vin_code) VALUES (1006, 1003, 'G901NO', 'Серый', '7A4RX62819D012345');
INSERT INTO postal_cars (postal_car_id, post_office_id, car_number, color, vin_code) VALUES (1007, 1003, 'H234PQ', 'Оранжевый', '8T3AZ54126F012345');
INSERT INTO postal_cars (postal_car_id, post_office_id, car_number, color, vin_code) VALUES (1008, 1004, 'I567RS', 'Фиолетовый', '9B7GT63584H012345');
INSERT INTO postal_cars (postal_car_id, post_office_id, car_number, color, vin_code) VALUES (1009, 1004, 'J890TU', 'Голубой', '1C4RJFAG0FC678901');
INSERT INTO postal_cars (postal_car_id, post_office_id, car_number, color, vin_code) VALUES (1010, 1005, 'K123VW', 'Розовый', '2D4RN4DG0BR123456');
INSERT INTO postal_cars (postal_car_id, post_office_id, car_number, color, vin_code) VALUES (1011, 1005, 'L456XY', 'Коричневый', '3FAHP0JG4BR234567');
INSERT INTO postal_cars (postal_car_id, post_office_id, car_number, color, vin_code) VALUES (1012, 1006, 'M789ZA', 'Серебристый', '4S4BRBCC8D3234567');


INSERT INTO route_distances (route_distance_id, distance, post_office_id, postal_car_id) VALUES (1, 10.5, 1, 1);
INSERT INTO route_distances (route_distance_id, distance, post_office_id, postal_car_id) VALUES (2, 15.2, 1, 2);
INSERT INTO route_distances (route_distance_id, distance, post_office_id, postal_car_id) VALUES (3, 20.7, 2, 3);
INSERT INTO route_distances (route_distance_id, distance, post_office_id, postal_car_id) VALUES (4, 25.3, 2, 4);
INSERT INTO route_distances (route_distance_id, distance, post_office_id, postal_car_id) VALUES (5, 30.8, 3, 5);
INSERT INTO route_distances (route_distance_id, distance, post_office_id, postal_car_id) VALUES (6, 35.4, 3, 6);
INSERT INTO route_distances (route_distance_id, distance, post_office_id, postal_car_id) VALUES (7, 40.9, 4, 7);
INSERT INTO route_distances (route_distance_id, distance, post_office_id, postal_car_id) VALUES (8, 45.5, 4, 8);
INSERT INTO route_distances (route_distance_id, distance, post_office_id, postal_car_id) VALUES (9, 50.1, 5, 9);
INSERT INTO route_distances (route_distance_id, distance, post_office_id, postal_car_id) VALUES (10, 55.6, 5, 10);
INSERT INTO route_distances (route_distance_id, distance, post_office_id, postal_car_id) VALUES (11, 60.2, 6, 11);
INSERT INTO route_distances (route_distance_id, distance, post_office_id, postal_car_id) VALUES (12, 65.7, 6, 12);
INSERT INTO route_distances (route_distance_id, distance, post_office_id, postal_car_id) VALUES (13, 70.3, 7, 13);


INSERT INTO parcel_clients (parcel_client_id, mail, phone_number) VALUES (1, 'client1@example.com', '+380912345678');
INSERT INTO parcel_clients (parcel_client_id, mail, phone_number) VALUES (2, 'client2@example.com', '+380923456789');
INSERT INTO parcel_clients (parcel_client_id, mail, phone_number) VALUES (3, 'client3@example.com', '+380934567890');
INSERT INTO parcel_clients (parcel_client_id, mail, phone_number) VALUES (4, 'client4@example.com', '+380945678901');
INSERT INTO parcel_clients (parcel_client_id, mail, phone_number) VALUES (5, 'client5@example.com', '+380956789012');


INSERT INTO postal_parcels (postal_parcel_id, parcel_height, parcel_length, parcel_weight, parcel_width, city_id, parcel_client_id, post_office_id, method_of_receiving, parcel_status) VALUES (1, 10.0, 20.0, 5.0, 15.0, 1, 1, 1, 'COURIER', 'ON_THE_WAY');
INSERT INTO postal_parcels (postal_parcel_id, parcel_height, parcel_length, parcel_weight, parcel_width, city_id, parcel_client_id, post_office_id, method_of_receiving, parcel_status) VALUES (2, 12.0, 22.0, 6.0, 16.0, 2, 2, 2, 'PICKUP', 'ARRIVED_AT_THE_POST_OFFICE');
INSERT INTO postal_parcels (postal_parcel_id, parcel_height, parcel_length, parcel_weight, parcel_width, city_id, parcel_client_id, post_office_id, method_of_receiving, parcel_status) VALUES (3, 14.0, 24.0, 7.0, 17.0, 3, 3, 3, 'COURIER', 'RECEIVED');
INSERT INTO postal_parcels (postal_parcel_id, parcel_height, parcel_length, parcel_weight, parcel_width, city_id, parcel_client_id, post_office_id, method_of_receiving, parcel_status) VALUES (4, 16.0, 26.0, 8.0, 18.0, 4, 4, 4, 'PICKUP', 'REFUSAL_TO_RECEIVED');
INSERT INTO postal_parcels (postal_parcel_id, parcel_height, parcel_length, parcel_weight, parcel_width, city_id, parcel_client_id, post_office_id, method_of_receiving, parcel_status) VALUES (5, 18.0, 28.0, 9.0, 19.0, 5, 5, 5, 'COURIER', 'ON_THE_WAY');



UPDATE cities_seq SET next_val = 6;
UPDATE parcel_clients_seq SET next_val = 6;
UPDATE post_office_seq SET next_val = 8;
UPDATE postal_cars_seq SET next_val = 14;
UPDATE postal_parcels_seq SET next_val = 6;
UPDATE route_distances_seq SET next_val = 14;

