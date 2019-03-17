# --- !Ups
INSERT INTO users (id, name) VALUES (1, 'Mike');
INSERT INTO users (id, name) VALUES (2, 'Alice');
INSERT INTO users (id, name) VALUES (3, 'Mary');

# --- !Downs
DROP TABLE IF EXISTS users;
