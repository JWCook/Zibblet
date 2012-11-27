# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table ingredient (
  id                        bigint not null,
  name                      varchar(255),
  density                   double,
  is_temp_item              boolean,
  ingredient_category_id    bigint,
  constraint pk_ingredient primary key (id))
;

create table ingredient_alias (
  id                        bigint not null,
  name                      varchar(255),
  ingredient_id             bigint,
  constraint pk_ingredient_alias primary key (id))
;

create table ingredient_category (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_ingredient_category primary key (id))
;

create table item_quantity (
  id                        bigint not null,
  quantity                  double,
  unit_str                  varchar(255),
  preparation               varchar(255),
  unit                      varchar(2),
  ingredient_id             bigint,
  constraint ck_item_quantity_unit check (unit in ('c','l','kg','gl','tb','ts','qt','lb','g','ml','oz','pt','fl')),
  constraint pk_item_quantity primary key (id))
;

create table recipe (
  id                        bigint not null,
  title                     varchar(255),
  times_made                integer,
  url                       varchar(255),
  prep_time                 varchar(16),
  cook_time                 varchar(16),
  directions                varchar(64000),
  notes                     varchar(64000),
  constraint pk_recipe primary key (id))
;

create table recipe_tag (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_recipe_tag primary key (id))
;

create table shopping_list (
  id                        bigint not null,
  title                     varchar(255),
  created_date              timestamp,
  constraint pk_shopping_list primary key (id))
;


create table ingredient_recipe (
  ingredient_id                  bigint not null,
  recipe_id                      bigint not null,
  constraint pk_ingredient_recipe primary key (ingredient_id, recipe_id))
;

create table recipe_tag_recipe (
  recipe_tag_id                  bigint not null,
  recipe_id                      bigint not null,
  constraint pk_recipe_tag_recipe primary key (recipe_tag_id, recipe_id))
;
create sequence ingredient_seq;

create sequence ingredient_alias_seq;

create sequence ingredient_category_seq;

create sequence item_quantity_seq;

create sequence recipe_seq;

create sequence recipe_tag_seq;

create sequence shopping_list_seq;

alter table ingredient add constraint fk_ingredient_ingredientCatego_1 foreign key (ingredient_category_id) references ingredient_category (id) on delete restrict on update restrict;
create index ix_ingredient_ingredientCatego_1 on ingredient (ingredient_category_id);
alter table ingredient_alias add constraint fk_ingredient_alias_ingredient_2 foreign key (ingredient_id) references ingredient (id) on delete restrict on update restrict;
create index ix_ingredient_alias_ingredient_2 on ingredient_alias (ingredient_id);
alter table item_quantity add constraint fk_item_quantity_ingredient_3 foreign key (ingredient_id) references ingredient (id) on delete restrict on update restrict;
create index ix_item_quantity_ingredient_3 on item_quantity (ingredient_id);



alter table ingredient_recipe add constraint fk_ingredient_recipe_ingredie_01 foreign key (ingredient_id) references ingredient (id) on delete restrict on update restrict;

alter table ingredient_recipe add constraint fk_ingredient_recipe_recipe_02 foreign key (recipe_id) references recipe (id) on delete restrict on update restrict;

alter table recipe_tag_recipe add constraint fk_recipe_tag_recipe_recipe_t_01 foreign key (recipe_tag_id) references recipe_tag (id) on delete restrict on update restrict;

alter table recipe_tag_recipe add constraint fk_recipe_tag_recipe_recipe_02 foreign key (recipe_id) references recipe (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists ingredient;

drop table if exists ingredient_recipe;

drop table if exists ingredient_alias;

drop table if exists ingredient_category;

drop table if exists item_quantity;

drop table if exists recipe;

drop table if exists recipe_tag_recipe;

drop table if exists recipe_tag;

drop table if exists shopping_list;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists ingredient_seq;

drop sequence if exists ingredient_alias_seq;

drop sequence if exists ingredient_category_seq;

drop sequence if exists item_quantity_seq;

drop sequence if exists recipe_seq;

drop sequence if exists recipe_tag_seq;

drop sequence if exists shopping_list_seq;

