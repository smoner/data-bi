
create table cpu_biprice(
id bigserial primary key not null ,
vtenant_id varchar(20) ,
vpurchase_enterprise_id varchar(200) ,
vpurchase_erp_id varchar(200) ,
vpurchase_code varchar(200) ,
vpurchase_name varchar(200) ,
jpurchase json ,
vpurchase varchar(25555) ,
vsupply_tenant_id varchar(200) ,
vsupply_enterprise_id varchar(200) ,
vsupply_erp_id varchar(200) ,
vsupply_code varchar(200) ,
vsupply_name varchar(200) ,
jsupply json ,
vsupply varchar(25555) ,
vmaterial_id varchar(200) ,
vmaterial_erp_id varchar(200) ,
vmaterial_code varchar(200) ,
vmaterial_name varchar(200) ,
jmaterial json ,
vmaterial varchar(25555) ,
nsum decimal(28,8) ,
nprice decimal(28,8) ,
nmny decimal(28,8) ,
jcondition json ,
vcondition varchar(25555) ,
jprice json ,
vprice varchar(25555) ,
vsrc varchar(500) ,
vsrc_mark varchar(500) ,
dbilldate VARCHAR ,
ts timestamp  ,
vupdate_mark varchar(500)
);


 alter table cpu_biprice add column vmaterial_spec varchar(200) ;	
 alter table cpu_biprice add column vsupply_prod_id varchar(200)  ;
 alter table cpu_biprice add column vsupply_prod_name varchar(200)  ;
 alter table cpu_biprice add column vsupply_prod_code varchar(200)  ;
 alter table cpu_biprice add column vunit_name varchar(200)  ;
 alter table cpu_biprice add column vcurrency varchar(200)  ;
 alter table cpu_biprice add column nprice_notax decimal(28,8) ;
 alter table cpu_biprice add column ntax decimal(28,8)  ;
 alter table cpu_biprice add column nmny_notax decimal(28,8)  ;
 alter table cpu_biprice add column vsrc_system varchar(200) ;
 alter table cpu_biprice add column vadd_type varchar(200) ;
 alter table cpu_biprice add column vsrc_billcode varchar(200) ;