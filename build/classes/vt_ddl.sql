drop trigger t_adv_applied_count on applications;
drop function change_adv_applied_count();

drop trigger t_register on users;
drop function register_check();

drop function get_applied_adv(int);
drop type applied_advertisement;

drop function filter_advertisements(bool, varchar(30), int, varchar(50), varchar(50), varchar(30));
drop type advertisement_result;

drop function get_applied_counts(int);
drop type applied_counts;

drop view active_advertisements;

--DROP TABLES
drop table education;
drop table experience;
drop table certificate;
drop table applications;
drop table advertisement;
drop table users;
drop table company;

drop sequence user_id_seq;
drop sequence company_id_seq;

--CREATE TABLES
create sequence user_id_seq start 1;
create table users(
	id int default nextval('user_id_seq') primary key not null,
	username varchar(20) not null,
	mail varchar(30) not null,
	password varchar(40) not null,
	name varchar(20) not null,
	surname varchar(20) not null
);
create sequence company_id_seq start 1;
create table company(
	id int default nextval('company_id_seq') primary key not null,
	name varchar(50) not null,
	field varchar(50) not null,
	location varchar(50) not null,
	worker_count int not null,
	constraint worker_count_bound check (worker_count >= 0)
);

create table education(
	id serial primary key not null,
	user_id int references users(id) on delete cascade not null,
	school_name varchar(50) not null,
	department varchar(50) not null,
	start_date date not null,
	finish_date date not null,
	grade numeric(3,2),
    constraint grade_bound check (grade is null or (grade >= 0 and grade <= 4.00))
);

create table experience(
	id serial primary key not null,
	user_id int references users(id) on delete cascade not null,
	company_id int references company(id) on delete cascade not null,
	job_title varchar(50) not null,
	department varchar(50) not null,
	start_date date not null,
	finish_date date
);

create table certificate(
	id serial primary key not null,
	user_id int references users(id) on delete cascade not null,
	company_id int references company(id) on delete cascade not null,
	certification_name varchar(50),
	receipt_date date not null,
	duration int,
	constraint duration_bound check (duration > 0)
);

create table advertisement(
    id serial primary key not null,
    company_id int references company(id) on delete cascade not null,
    title varchar(50) not null,
    description varchar(500),
    location varchar(50),
    open_date date not null,
    deadline_date date not null,
    applied_count int not null,
    is_active bool not null,
    is_job bool not null,
    type varchar(30),
    department varchar(50),  --for job advertisements
    working_model varchar(30)  --for job advertisements
);

create table applications(
	id serial primary key not null,
	user_id int references users(id) on delete cascade not null,
	adv_id int references advertisement(id) on delete cascade not null,
	application_date date not null
);

--Advertisement view
create view active_advertisements as
select *
from advertisement
where is_active = true;

--Register trigger
create or replace function register_check()
returns trigger as $$
begin
if(length(new.password) >= 5) then
	
	raise info 'Kullanıcı başarıyla oluşturuldu';
	return new;
else
    raise exception 'Şifre en az 5 karakterden oluşmalıdır.';
	return null;
end if;
end;
$$ language 'plpgsql';

create trigger t_register
before insert
on users
for each row execute procedure register_check();

--Advertisement count trigger
create or replace function change_adv_applied_count()
returns trigger as $$
begin
if(tg_op = 'INSERT') then
    update advertisement
    set applied_count = applied_count + 1
    where id = new.adv_id;
	return new;
else
    update advertisement
    set applied_count = applied_count - 1
    where id = old.adv_id;
	return old;
end if;
end;
$$ language 'plpgsql';

create trigger t_adv_applied_count
after insert or delete
on applications
for each row execute procedure change_adv_applied_count();

--Applied adv func with cursor and type
create type applied_advertisement as (app_id int, u_id int, app_title varchar(50), add_type bool, app_date date);
create or replace function get_applied_adv(search_id int)
returns applied_advertisement[] as $$
declare
	adv_cursor cursor for 
	select ap.id id, ap.user_id user_id, ad.title title, ad.is_job is_job, ap.application_date app_date
	from advertisement ad, applications ap
	where ad.id = ap.adv_id;
	i integer;
	applied_advs applied_advertisement[];
begin
	i := 1;
	for adv_record in adv_cursor loop
		if adv_record.user_id = search_id then
			applied_advs[i] = adv_record;
			i = i+1;
		end if;
	end loop;
	return applied_advs;
end;
$$ language 'plpgsql';

--Filtering
create type advertisement_result as (
    id int,
    company_id int,
    title varchar(50),
    description varchar(500),
    location varchar(50),
    open_date date,
    deadline_date date,
    applied_count int,
    is_active bool,
    is_job bool,
    department varchar(50),
    working_model varchar(30),
    type varchar(30)
);
create or replace function filter_advertisements(
    p_is_job bool,
    p_type varchar(30),
    p_company_id int,
    p_location varchar(50),
	p_department varchar(50),
    p_working_model varchar(30)
)
returns advertisement_result[] as $$
declare
	adv_cursor cursor for 
	select *
	from advertisement
	where
        (p_is_job is null or is_job = p_is_job)
        and (p_type is null or type = p_type)
        and (p_company_id is null or company_id = p_company_id)
        and (p_location is null or location = p_location)
	except
	select *
	from advertisement
	where is_active = false;
	i integer;
	results advertisement_result[];
begin
	i := 1;
	if p_is_job = true then
		for adv_record in adv_cursor loop
			if (p_working_model IS NULL OR adv_record.working_model = p_working_model)
			and (p_department IS NULL OR adv_record.department = p_department) then
				results[i] = adv_record;
				i = i+1;
			end if;
		end loop;
	else
		for adv_record in adv_cursor loop
			results[i] = adv_record;
			i = i+1;
		end loop;	
	end if;
    return results;
end;
$$ language 'plpgsql';

--Get applied counts
create type applied_counts as (job_count int, course_count int);
create or replace function get_applied_counts(u_id int)
returns applied_counts as $$
declare
	res applied_counts;
begin
	select count(*) into res.job_count
	from advertisement ad, applications ap
	where ad.id = ap.adv_id and ap.user_id = u_id
	group by ad.is_job
	having ad.is_job = true;
	
	select count(*) into res.course_count
	from advertisement ad, applications ap
	where ad.id = ap.adv_id and ap.user_id = u_id
	group by ad.is_job
	having ad.is_job = false;
	
	return res;
end;
$$ language 'plpgsql';

--Search advs
-- select *
-- from advertisement
-- where is_active=true and title like '%' || query || '%';

-- Users tablosuna 10 değer ekleme
insert into users (username, mail, password, name, surname)
values
    ('user1', 'user1@example.com', 'pass1', 'John', 'Doe'),
    ('user2', 'user2@example.com', 'pass2', 'Jane', 'Smith'),
    ('user3', 'user3@example.com', 'pass3', 'Alice', 'Johnson'),
    ('user4', 'user4@example.com', 'pass4', 'Bob', 'Williams'),
    ('user5', 'user5@example.com', 'pass5', 'Eva', 'Miller'),
    ('user6', 'user6@example.com', 'pass6', 'David', 'Jones'),
    ('user7', 'user7@example.com', 'pass7', 'Sophia', 'Brown'),
    ('user8', 'user8@example.com', 'pass8', 'Matthew', 'Taylor'),
    ('user9', 'user9@example.com', 'pass9', 'Olivia', 'Anderson'),
    ('user10', 'user10@example.com', 'pass10', 'William', 'White');

-- Company tablosuna 10 değer ekleme
insert into company (name, field, location, worker_count)
values
    ('Company1', 'IT', 'City1', 50),
    ('Company2', 'Finance', 'City2', 30),
    ('Company3', 'Healthcare', 'City3', 80),
    ('Company4', 'Education', 'City4', 25),
    ('Company5', 'Automotive', 'City5', 60),
    ('Company6', 'Hospitality', 'City6', 40),
    ('Company7', 'Telecommunications', 'City7', 70),
    ('Company8', 'Retail', 'City8', 55),
    ('Company9', 'Energy', 'City9', 45),
    ('Company10', 'Media', 'City10', 65);

-- Education tablosuna 10 değer ekleme
insert into education (user_id, school_name, department, start_date, finish_date, grade)
values
    (1, 'School1', 'Department1', '2015-09-01', '2019-06-01', 3.50),
    (2, 'School2', 'Department2', '2014-08-01', '2018-05-01', 3.75),
    (3, 'School3', 'Department3', '2016-09-01', '2020-06-01', 3.00),
    (4, 'School4', 'Department4', '2013-08-01', '2017-05-01', 3.90),
    (5, 'School5', 'Department5', '2017-09-01', '2021-06-01', 3.20),
    (6, 'School6', 'Department6', '2012-08-01', '2016-05-01', 3.80),
    (7, 'School7', 'Department7', '2018-09-01', '2022-06-01', 3.45),
    (8, 'School8', 'Department8', '2011-08-01', '2015-05-01', 3.60),
    (9, 'School9', 'Department9', '2019-09-01', '2023-06-01', 3.25),
    (10, 'School10', 'Department10', '2010-08-01', '2014-05-01', 3.70);

-- Experience tablosuna 10 değer ekleme
insert into experience (user_id, company_id, job_title, department, start_date, finish_date)
values
    (1, 1, 'Software Engineer', 'IT', '2015-07-01', '2021-02-01'),
    (2, 2, 'Financial Analyst', 'Finance', '2014-06-01', '2020-03-01'),
    (3, 3, 'Nurse', 'Healthcare', '2016-08-01', '2022-04-01'),
    (4, 4, 'Teacher', 'Education', '2013-07-01', '2019-01-01'),
    (5, 5, 'Mechanical Engineer', 'Automotive', '2017-06-01', '2023-03-01'),
    (6, 6, 'Hotel Manager', 'Hospitality', '2012-05-01', '2018-11-01'),
    (7, 7, 'Network Engineer', 'Telecommunications', '2018-07-01', '2024-01-01'),
    (8, 8, 'Store Manager', 'Retail', '2011-06-01', '2017-12-01'),
    (9, 9, 'Electrical Engineer', 'Energy', '2019-07-01', NULL),
    (10, 10, 'Journalist', 'Media', '2010-05-01', '2016-11-01');

-- Certificate tablosuna 10 değer ekleme
insert into certificate (user_id, company_id, certification_name, receipt_date, duration)
values
    (1, 1, 'Java Certification', '2020-03-01', 24),
    (2, 2, 'CFA Level 1', '2019-02-01', 18),
    (3, 3, 'Nursing License', '2021-04-01', 36),
    (4, 4, 'Teaching Certificate', '2018-01-01', 12),
    (5, 5, 'Mechanical Engineering License', '2022-05-01', 30),
    (6, 6, 'Hotel Management Certificate', '2017-11-01', 24),
    (7, 7, 'Cisco Certified Network Professional', '2023-01-01', 36),
    (8, 8, 'Retail Management Certificate', '2016-12-01', 18),
    (9, 9, 'Electrical Engineering License', '2024-02-01', 24),
    (10, 10, 'Journalism Diploma', '2016-10-01', 12);

-- Advertisement tablosuna 10 değer ekleme
insert into advertisement (company_id, title, description, location, open_date, deadline_date, applied_count, is_active, is_job, department, working_model, type)
values
    (1, 'Software Engineer Position', 'Exciting opportunity for a skilled software engineer!', 'City1', '2022-03-01', '2022-05-01', 0, true, true, 'IT', 'Full-time', 'Developer'),
    (2, 'Financial Analyst Job Opening', 'Join our finance team and make a difference!', 'City2', '2022-02-01', '2022-04-01', 0, true, true, 'Finance', 'Part-time', 'Analyst'),
    (3, 'Nursing Course Announcement', 'Enroll in our nursing course and start your career in healthcare!', 'City3', '2022-04-01', '2022-06-01', 0, true, false, NULL, NULL, 'Nursing'),
    (4, 'Teaching Position Available', 'We are hiring passionate teachers for various subjects!', 'City4', '2022-01-01', '2022-03-01', 0, true, true, 'Education', 'Full-time', 'Teacher'),
    (5, 'Mechanical Engineer Needed', 'Exciting opportunity for a mechanical engineer to join our team!', 'City5', '2022-05-01', '2022-07-01', 0, true, true, 'Automotive', 'Full-time', 'Mechanical Engineer'),
    (6, 'Hotel Management Course', 'Learn hotel management skills and kickstart your career!', 'City6', '2022-06-01', '2022-08-01', 0, true, false, NULL, NULL, 'Hotel Management'),
    (7, 'Network Engineer Position', 'Join our team as a network engineer and advance your career!', 'City7', '2022-03-01', '2022-05-01', 0, true, true, 'Telecommunications', 'Full-time', 'Network Engineer'),
    (8, 'Retail Manager Job Opening', 'Exciting opportunity for an experienced retail manager!', 'City8', '2022-04-01', '2022-06-01', 0, true, true, 'Retail', 'Full-time', 'Store Manager'),
    (9, 'Electrical Engineering Course', 'Enroll in our electrical engineering course and gain valuable skills!', 'City9', '2022-07-01', '2022-09-01', 0, true, false, NULL, NULL, 'Electrical Engineering'),
    (10, 'Journalism Internship', 'Apply for our journalism internship and kickstart your career in media!', 'City10', '2022-02-01', '2022-04-01', 0, true, true, 'Media', 'Internship', 'Journalist');
  
-- Applications tablosuna 10 değer ekleme
insert into applications (user_id, adv_id, application_date)
values
    (1, 1, '2022-04-15'),
    (1, 2, '2022-03-20'),
    (1, 3, '2022-05-10'),
    (4, 4, '2022-02-25'),
    (5, 5, '2022-06-05'),
    (6, 6, '2022-07-15'),
    (7, 7, '2022-04-01'),
    (8, 8, '2022-05-12'),
    (9, 9, '2022-08-03'),
    (10, 10, '2022-03-10');
	
--SELECT TABLES
select * from users;
select * from company;
select * from education;
select * from experience;
select * from certificate;
select * from applications;
select * from advertisement;

select filter_advertisements(true, null, null, null, null, 'Full-time');
select get_applied_adv(1);
select get_applied_counts(1);