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

