
-- Create owners
INSERT INTO Owner (Name) VALUES
  ('Mamadou Lamine DIARRA'),
  ('Ange PETE'),
  ('Toupac Transport');

-- Create contact type
INSERT INTO ContactType (Type) VALUES
  ('Manager'),
  ('Assistant'),
  ('Driver');

-- Contact info
INSERT INTO ContactInfo (PhoneNumber) VALUES
  ('+22373034603'),
  ('+22373034603'),
  ('+22373034603');

-- Create contacts drivers and owners
INSERT INTO Contact (FirstName, LastName, ContactInfoId, ContactTypeId) VALUES
  ('Mamadou', 'Diarra', 1, 3),
  ('Issa', 'Synayogo', 2, 1),
  ('Samba', 'Sidibe', 3, 1);

  -- Create Cars
INSERT INTO Car (Name, Reference, OwnerId) VALUES
  ('Car 1', 'A000000', 2), --Ange
  ('Car 2', 'A000001', 2), --Ange
  ('Car 3', 'A000002', 3), --Issa
  ('Car 4', 'A000003', 3); --Issa

-- Car driver contact
INSERT INTO CarDriverContact (CarId, ContactId) VALUES
  (1, 1),
  (2, 1),
  (3, 1),
  (4, 1);

INSERT INTO Login (Login, Password, Enabled) VALUES ('toupac','dnaqr7AnyCW9mrq3iyNAcOcCdS9iW3UuVeVbSOYH41g=','1');
INSERT INTO Login (Login, Password, Enabled) VALUES ('admin','dnaqr7AnyCW9mrq3iyNAcOcCdS9iW3UuVeVbSOYH41g=','1');
INSERT INTO ContactLogin VALUES (2,1);
INSERT INTO ContactLogin VALUES (3,2);

-- Create zone
INSERT INTO Zone (Name) VALUES
  ('Paris');

INSERT INTO Location (Latitude, Longitude) VALUES
  (17.570692, -3.996166),
  (16.570692, -3.996166);

INSERT INTO CarLocation (LocationId, CarId, Time) VALUES
  (1, 3, '2015-06-28''T''15:35:09.000Z'),
  (1, 3, '2016-07-23''T''15:35:09.000Z');

INSERT INTO Bounds (ZoneId, NorthEastId, SouthwestId) VALUES
  (1 , 1, 2); -- Zone Paris, NorthEast 1, SouthWest 2

INSERT INTO OwnerContact VALUES (3, 2);
