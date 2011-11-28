INSERT INTO MCQ.QUESTIONS (QUESTION_TEXT)
VALUES
  ('Where did you travel last year?');

INSERT INTO MCQ.CHOICES
  (QUESTION_ID, CHOICE_TEXT)
VALUES
  (IDENTITY_VAL_LOCAL(), 'Asia'),
  (IDENTITY_VAL_LOCAL(), 'Africa'),
  (IDENTITY_VAL_LOCAL(), 'North America'),
  (IDENTITY_VAL_LOCAL(), 'South America'),
  (IDENTITY_VAL_LOCAL(), 'Antarctica'),
  (IDENTITY_VAL_LOCAL(), 'Europe'),
  (IDENTITY_VAL_LOCAL(), 'Australia');

INSERT INTO MCQ.QUESTIONS (QUESTION_TEXT)
VALUES
  ('What are your favorite days?');

INSERT INTO MCQ.CHOICES
  (QUESTION_ID, CHOICE_TEXT)
VALUES
  (IDENTITY_VAL_LOCAL(), 'Monday'),
  (IDENTITY_VAL_LOCAL(), 'Tuesday'),
  (IDENTITY_VAL_LOCAL(), 'Wednesday'),
  (IDENTITY_VAL_LOCAL(), 'Thursday'),
  (IDENTITY_VAL_LOCAL(), 'Friday'),
  (IDENTITY_VAL_LOCAL(), 'Saturday'),
  (IDENTITY_VAL_LOCAL(), 'Sunday');

