--liquibase formatted sql

-- changeset galandor:0 context:common failOnError:true
CREATE TABLE item (
id serial PRIMARY KEY,
type VARCHAR(64) NOT NULL,
text TEXT,
next_item_id LONG REFERENCES "item" ("id") ON DELETE DELETE ON UPDATE NO ACTION,
parent_item_id LONG REFERENCES "item" ("id") ON DELETE DELETE ON UPDATE NO ACTION,
created_at TIMESTAMP WITHOUT TIMEZONE NOT NULL,
updated_at TIMESTAMP WITHOUT TIMEZONE NOT NULL
);

CREATE TABLE current_item (
user_id UUID PRIMARY KEY,
item_id LONG REFERENCES "item" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
created_at TIMESTAMP WITHOUT TIMEZONE NOT NULL,
updated_at TIMESTAMP WITHOUT TIMEZONE NOT NULL
);
