# --- Sample dataset

# --- !Ups

insert into unit (id,name,symbol,conversion_ratio,system,type) values (1,'gram','g',1.0,'SI','W');
insert into unit (id,name,symbol,conversion_ratio,system,type) values (2,'kilogram','kg',1000.0,'SI','W');
insert into unit (id,name,symbol,conversion_ratio,system,type) values (3,'cubic centimeter','cm\u00b3',1.0,'SI','V');
insert into unit (id,name,symbol,conversion_ratio,system,type) values (4,'milliliter','ml',1.0,'SI','V');
insert into unit (id,name,symbol,conversion_ratio,system,type) values (5,'liter','l',1000.0,'SI','V');
insert into unit (id,name,symbol,conversion_ratio,system,type) values (6,'ounce','oz',28.3495,'US','W');
insert into unit (id,name,symbol,conversion_ratio,system,type) values (7,'pound','lb',453.5924,'US','W');
insert into unit (id,name,symbol,conversion_ratio,system,type) values (8,'teaspoon','tsp',4.9289,'US','V');
insert into unit (id,name,symbol,conversion_ratio,system,type) values (9,'tablespoon','tbsp',14.7868,'US','V');
insert into unit (id,name,symbol,conversion_ratio,system,type) values (10,'fluid ounce','fl oz',29.5735,'US','V');
insert into unit (id,name,symbol,conversion_ratio,system,type) values (11,'cup','c',236.5882,'US','V');
insert into unit (id,name,symbol,conversion_ratio,system,type) values (12,'pint','pt',473.1765,'US','V');
insert into unit (id,name,symbol,conversion_ratio,system,type) values (13,'quart','qt',946.3529,'US','V');
insert into unit (id,name,symbol,conversion_ratio,system,type) values (14,'gallon','gal',3785.4118,'US','V');


# --- !Downs

delete from unit;
