-- insert initial test data
-- the IDs are hardcoded to enable references between further test data
-- negative IDs are used to not interfere with user-entered data and allow clean deletion of test data


DELETE FROM sport where ID < 0;
INSERT INTO sport (ID, NAME, DESCRIPTION)
VALUES (-1, 'Polo', NULL)
       , (-2, 'Dressage', NULL)
       , (-3, 'Foxhunting', NULL)
       , (-4, 'Agility', 'Jumping over small obstacles etc.')
       , (-5, 'Walking', 'for oldies')
       , (-6, 'Trecking', NULL)
       , (-7, 'Swimming', 'not for everyone here')
       , (-8, 'Bow and Arrow', 'bring your own gear')
       , (-9, 'Children´s Programm', NULL)
       , (-10, 'Parades', NULL)
      ;

DELETE FROM horse where ID < 11;
INSERT INTO horse (id,name,sex,dateofbirth,description,favsportid, parent1id,parent2id)
VALUES (-1, 'Naruto', 'M', '2020-01-01','likes ramen', -8, null, null)
       ,(-2, 'Hansi', 'M', '2019-01-01',null, -9, null, null)
       ,(-3, 'Günter', 'M', '2017-01-01','likes carrots a lot', -10, null, null)
       ,(-4, 'Franziska', 'F', '2018-01-01',null, -7, null, null)
       ,(-5, 'Tsuna', 'F', '2006-01-01','oldie', -5, null, null)
       ,(-6, 'Hunter', 'M', '2020-01-01','fast', -3, -5, -2)
       ,(-7, 'Mina', 'F', '2014-01-01',null, null , null, null)
       ,(-8, 'Sally', 'F', '2021-01-01','wild youngling', -7, -7, -6)
       ,(-9, 'Niro', 'M', '2020-02-01','new here', -6, -3, null)
       ,(-10, 'Lexie', 'F', '2020-01-01','lady', -2, -4, null)
;
