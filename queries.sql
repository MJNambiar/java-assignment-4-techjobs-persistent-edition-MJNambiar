-- Part 1: Test it with SQL
SELECT
COLUMN_NAME,
DATA_TYPE
FROM INFORMATION_SCHEMA.COLUMNS
where TABLE_NAME = 'job';

-- Part 2: Test it with SQL
SELECT name
FROM employer
WHERE location = "St. Louis City";

-- Part 3: Test it with SQL
DROP
TABLE
job;

-- Part 4: Test it with SQL
SELECT skill.name
FROM skill
LEFT INNER JOIN job ON (skill.id = job.skills)
WHERE job.id IS NOT NULL
ORDER BY skill.name ASC;
//need to create join table instead?