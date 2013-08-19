INSERT INTO MCQ.QUESTIONS (QUESTION_TEXT, MULTIPLE_CHOICES)
VALUES
  ('Where did you travel last year?', 1);

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

INSERT INTO MCQ.QUESTIONS (QUESTION_TEXT, MULTIPLE_CHOICES)
VALUES
  ('What is your favorite day in the week?', 0);

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

