INSERT INTO MCQ.QUESTIONS (QUESTION_TEXT, MULTIPLE_CHOICES)
VALUES
  ('Quels packages d''Eclipse Indigo avez-vous téléchargés ?', 1);

INSERT INTO MCQ.CHOICES
  (QUESTION_ID, CHOICE_TEXT)
VALUES
  (IDENTITY_VAL_LOCAL(), 'Java Developers'),
  (IDENTITY_VAL_LOCAL(), 'Java EE Developers'),
  (IDENTITY_VAL_LOCAL(), 'Classic'),
  (IDENTITY_VAL_LOCAL(), 'C/C++ Developers'),
  (IDENTITY_VAL_LOCAL(), 'JavaScript Web Developers'),
  (IDENTITY_VAL_LOCAL(), 'Modeling Tools'),
  (IDENTITY_VAL_LOCAL(), 'Java and Report Developers'),
  (IDENTITY_VAL_LOCAL(), 'RCP and RAP Developers'),
  (IDENTITY_VAL_LOCAL(), 'Testers'),
  (IDENTITY_VAL_LOCAL(), 'Parallel Application Developers'),
  (IDENTITY_VAL_LOCAL(), 'Scout Developers');

INSERT INTO MCQ.QUESTIONS (QUESTION_TEXT, MULTIPLE_CHOICES)
VALUES
  ('Quels nouveaux projets d''Eclipse Indigo vous intéressent le plus ?', 1);

INSERT INTO MCQ.CHOICES
  (QUESTION_ID, CHOICE_TEXT)
VALUES
  (IDENTITY_VAL_LOCAL(), 'Object Teams'),
  (IDENTITY_VAL_LOCAL(), 'WindowBuilder'),
  (IDENTITY_VAL_LOCAL(), 'Scout'),
  (IDENTITY_VAL_LOCAL(), 'RTP Project'),
  (IDENTITY_VAL_LOCAL(), 'Jubula'),
  (IDENTITY_VAL_LOCAL(), 'Agent Modeling Platform'),
  (IDENTITY_VAL_LOCAL(), 'Eclipse Generation Factories'),
  (IDENTITY_VAL_LOCAL(), 'Gyrex'),
  (IDENTITY_VAL_LOCAL(), 'EMF Facet'),
  (IDENTITY_VAL_LOCAL(), 'Graphiti'),
  (IDENTITY_VAL_LOCAL(), 'm2eclipse');

