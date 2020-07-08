
alter user postgres rename to abhishek and password 'test123';
	
create database abhishek
\c abhishek

/*drop table expense_item cascade; 
drop table income_item cascade; 
drop table daily_budget_item cascade; 
drop table monthly_budget_item cascade;
drop table yearly_budget_item cascade;*/

create table yearly_budget_item(year int primary key , yearly_savings float , yearly_income float , yearly_expense float , yearly_expense_percentage int);
\d yearly_budget_item

create table monthly_budget_item(month varchar(10) , monthly_savings float , monthly_income float , monthly_expense float , monthly_expense_percentage int , year int references yearly_budget_item(year) on delete cascade on update set null, primary key(month , year));
\d monthly_budget_item

create table daily_budget_item(day date primary key , daily_savings float , daily_income float , daily_expense float , daily_expense_percentage int, month varchar(10) , year int references yearly_budget_item(year) on delete cascade on update set null , foreign key(month , year) references monthly_budget_item(month,year));
\d daily_budget_item

create table income_item(id bigserial primary key, income_amount float , income_source varchar(50) , day date references daily_budget_item(day) on delete cascade on update set null);
\d income_item

create table expense_item(id bigserial primary key,expense_amount float , expense_source varchar(50) , expense_percentage int , day date references daily_budget_item(day) on delete cascade on update set null);
\d expense_item

