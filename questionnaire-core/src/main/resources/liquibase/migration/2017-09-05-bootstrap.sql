--liquibase formatted sql

-- changeset galandor:0 context:common failOnError:true
CREATE TABLE item (
id bigserial PRIMARY KEY,
type VARCHAR(64) NOT NULL,
text TEXT,
next_item_id bigint REFERENCES "item" ("id") ON DELETE CASCADE ON UPDATE NO ACTION,
parent_item_id bigint REFERENCES "item" ("id") ON DELETE CASCADE ON UPDATE NO ACTION,
created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE current_item (
user_id UUID PRIMARY KEY,
item_id bigint REFERENCES "item" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);
