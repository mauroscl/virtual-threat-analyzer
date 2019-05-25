CREATE TABLE IF NOT EXISTS whitelistrule(
    id BIGINT NOT NULL AUTO_INCREMENT,
    client VARCHAR(128) NULL,
    regex VARCHAR(128) NOT NULL,
    PRIMARY KEY pk_whitelistrule(ID),
    INDEX idx_client(client)
);