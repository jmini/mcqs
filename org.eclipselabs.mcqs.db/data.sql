INSERT INTO MCQ.QUESTIONS (QUESTION_TEXT) 
VALUES
  ('Quel est votre IDE préféré?');
 
INSERT INTO MCQ.CHOICES 
  (QUESTION_ID, CHOICE_TEXT) 
VALUES 
  (IDENTITY_VAL_LOCAL(), 'Eclipse'), 
  (IDENTITY_VAL_LOCAL(), 'NetBeans'), 
  (IDENTITY_VAL_LOCAL(), 'IntelliJ'), 
  (IDENTITY_VAL_LOCAL(), 'RAD / WSAD'), 
  (IDENTITY_VAL_LOCAL(), 'JDeveloper'), 
  (IDENTITY_VAL_LOCAL(), 'JCreator'), 
  (IDENTITY_VAL_LOCAL(), 'JBuilder'), 
  (IDENTITY_VAL_LOCAL(), 'BEA Workshop Studio'), 
  (IDENTITY_VAL_LOCAL(), 'Editeurs de texte avancés (Emacs, VI, JEdit, UltraEdit, ...)');
  
INSERT INTO MCQ.QUESTIONS (QUESTION_TEXT) 
VALUES
  ('Quel est le meilleur smartphone sous Android?');
  
INSERT INTO MCQ.CHOICES 
  (QUESTION_ID, CHOICE_TEXT) 
VALUES 
  (IDENTITY_VAL_LOCAL(), 'Google Nexus S'), 
  (IDENTITY_VAL_LOCAL(), 'HTC Desire S'), 
  (IDENTITY_VAL_LOCAL(), 'HTC Desire Z'), 
  (IDENTITY_VAL_LOCAL(), 'HTC Incredible S'), 
  (IDENTITY_VAL_LOCAL(), 'HTC Sensation'), 
  (IDENTITY_VAL_LOCAL(), 'LG Optimus One'), 
  (IDENTITY_VAL_LOCAL(), 'LG Optimus 2X LGP990'), 
  (IDENTITY_VAL_LOCAL(), 'LG Optimus Black P970'), 
  (IDENTITY_VAL_LOCAL(), 'Motorola Atrix'), 
  (IDENTITY_VAL_LOCAL(), 'Motorola Defy'), 
  (IDENTITY_VAL_LOCAL(), 'Samsung Galaxy Ace GT-S5830'), 
  (IDENTITY_VAL_LOCAL(), 'Samsung Galaxy S'), 
  (IDENTITY_VAL_LOCAL(), 'Samsung Galaxy S II'), 
  (IDENTITY_VAL_LOCAL(), 'Sony Ericsson Xperia Arc');