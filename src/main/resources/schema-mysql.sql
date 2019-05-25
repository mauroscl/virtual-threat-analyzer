CREATE TABLE IF NOT EXISTS white_list_rule(
    id BIGINT NOT NULL AUTO_INCREMENT,
    client VARCHAR(128) NULL,
    regex VARCHAR(128) NOT NULL,
    PRIMARY KEY pk_white_list_rule(ID),
    INDEX idx_client(client)
);