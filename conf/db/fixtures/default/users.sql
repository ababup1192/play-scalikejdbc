# --- !Ups
INSERT INTO users (name) VALUES ('Mike');
INSERT INTO users (name) VALUES ('Alice');
INSERT INTO users (name) VALUES ('Mary');

# --- !Downs
DROP TABLE IF EXISTS users;
