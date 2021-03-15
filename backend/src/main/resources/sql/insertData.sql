-- insert initial test data
-- the IDs are hardcoded to enable references between further test data
-- negative IDs are used to not interfere with user-entered data and allow clean deletion of test data


DELETE FROM sport where ID < 0;
INSERT INTO sport (ID, NAME, DESCRIPTION)
VALUES (-1, 'Polo', NULL)
       , (-2, 'Dressage', NULL)
       , (-3, 'Foxhunting', NULL)
      ;

