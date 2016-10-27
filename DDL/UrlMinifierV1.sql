CREATE SCHEMA minifier;

CREATE SEQUENCE "minifier"."alias_seq"
	INCREMENT BY 1
	NO MINVALUE
	NO MAXVALUE
	START WITH 1
	CACHE 1
	NO CYCLE;
CREATE TABLE "minifier"."minimized_urls"  ( 
	"minified_alias"  	varchar(100) NOT NULL,
	"destination_url" 	varchar(1000) NULL,
	"source_ip"       	varchar(100) NULL,
	"creation_api_key"	varchar(100) NULL,
	"referrer"        	varchar NULL,
	"user_agent"      	varchar NULL,
	"created_ts"      	timestamp NULL,
	PRIMARY KEY("minified_alias")
);
COMMENT ON TABLE "minifier"."minimized_urls" IS 'Main table of minimized urls';
CREATE UNIQUE INDEX "alias_pk"
	ON "minifier"."minimized_urls" USING btree ("minified_alias");
CREATE UNIQUE INDEX "destination_url_idx"
	ON "minifier"."minimized_urls" USING btree ("destination_url");

CREATE TABLE minifier.stats_clicks  ( 
    minified_alias 	varchar(100) NOT NULL,
    click_cnt      	int8 NULL,
    last_clicked_ts	timestamp NULL,
    PRIMARY KEY(minified_alias)
)
WITHOUT OIDS 
TABLESPACE pg_default
GO
ALTER TABLE minifier.stats_clicks
    ADD CONSTRAINT stat_clicks_alias_fk
	FOREIGN KEY(minified_alias)
	REFERENCES minifier.minimized_urls(minified_alias)
	ON DELETE NO ACTION 
	ON UPDATE NO ACTION 

CREATE TABLE minifier.stats_click_log  ( 
    click_ts      	timestamp NOT NULL,
    user_agent    	varchar NULL,
    minified_alias	varchar(100) NOT NULL,
    referrer      	varchar NULL,
    source_ip     	varchar(100) NULL 
    )
WITHOUT OIDS 
TABLESPACE pg_default
GO
ALTER TABLE minifier.stats_click_log
    ADD CONSTRAINT stats_log_fk
	FOREIGN KEY(minified_alias)
	REFERENCES minifier.minimized_urls(minified_alias)
	ON DELETE NO ACTION 
	ON UPDATE NO ACTION 
CREATE INDEX stats_clicks_log_idx
    ON minifier.stats_click_log USING btree (minified_alias text_ops, click_ts timestamp_ops)
GO
