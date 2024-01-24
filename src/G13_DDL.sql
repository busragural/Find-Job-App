-- drop trigger t_adv_applied_count on applications;
-- drop function change_adv_applied_count();

-- drop trigger t_register on users;
-- drop function register_check();

-- drop trigger t_dup_app on applications;
-- drop function prevent_duplicated_application();

-- drop function get_applied_adv(int);
-- drop type applied_advertisement;

-- drop function filter_advertisements(bool, varchar(30), int, varchar(50), varchar(30));
-- drop type advertisement_result;

-- drop function get_applied_counts(int);
-- drop type applied_counts;

-- drop view active_job_advertisements;
-- drop view active_course_advertisements;

-- --DROP TABLES
-- drop table education;
-- drop table experience;
-- drop table certificate;
-- drop table applications;
-- drop table advertisement;
-- drop table users;
-- drop table company;

-- drop sequence user_id_seq;
-- drop sequence company_id_seq;

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
    constraint advertisement_bound check (applied_count >= 0)
);

create table applications(
	id serial primary key not null,
	user_id int references users(id) on delete cascade not null,
	adv_id int references advertisement(id) on delete cascade not null,
	application_date date not null
);

--Job Advertisement view
create view active_job_advertisements as
select *
from advertisement
where is_active = true and is_job = true;

--Course Advertisement view
create view active_course_advertisements as
select *
from advertisement
where is_active = true and is_job = false;

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

--Dupplicated application trigger
create or replace function prevent_duplicated_application()
returns trigger as $$
declare
is_exist int:=0;
begin
select count(*) into is_exist
from applications
where user_id = new.user_id and adv_id = new.adv_id;
if(is_exist = 0) then
	raise info 'Başvuru başarılı.';
	return new;
else
    raise exception 'Daha önce başvurdun.';
	return null;
end if;
end;
$$ language 'plpgsql';

create trigger t_dup_app
before insert
on applications
for each row execute procedure prevent_duplicated_application();

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
create type applied_advertisement as (
    adv_id int,
    app_id int,
    u_id int,
    app_title varchar(50),
    add_type bool,
    app_date date
);
create or replace function get_applied_adv(search_id int)
returns setof applied_advertisement as $$
declare
    adv_cursor cursor for 
        select ad.id as adv_id, ap.id as app_id, ap.user_id as u_id, ad.title as app_title, ad.is_job as add_type, ap.application_date as app_date
        from advertisement ad
        join applications ap on ad.id = ap.adv_id;
    adv_record applied_advertisement;
begin
    for adv_record in adv_cursor loop
        if adv_record.u_id = search_id then
            return next adv_record;
        end if;
    end loop;
    return;
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
    type varchar(30),
    department varchar(50),
    working_model varchar(30)
);
create or replace function filter_advertisements(
    p_is_job bool,
    p_type varchar(30),
    p_company_id int,
    p_location varchar(50),
    p_working_model varchar(30)
)
returns setof advertisement_result as $$
declare
    adv_record advertisement_result;
    adv_cursor cursor for
    select *
	from advertisement
    where
        (p_is_job is null or is_job = p_is_job)
        and (p_type is null or type = p_type)
        and (p_company_id = 0 or company_id = p_company_id)
        and (p_location is null or location = p_location)
        and (p_is_job is null or (p_is_job = true and (p_working_model is null or working_model = p_working_model)) or p_is_job = false)
	except 
	select *
	from advertisement
	where is_active = false;
begin
    open adv_cursor;
    loop
        fetch adv_cursor into adv_record;
        exit when not found;
        return next adv_record;
    end loop;
    close adv_cursor;
    return;
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

-- Users tablosuna 10 değer ekleme
insert into users (username, mail, password, name, surname)
values
    ('john_doe', 'john@example.com', 'pass1', 'John', 'Doe'),
    ('jane_smith', 'jane@example.com', 'pass2', 'Jane', 'Smith'),
    ('alice_johnson', 'alice@example.com', 'pass3', 'Alice', 'Johnson'),
    ('bob_williams', 'bob@example.com', 'pass4', 'Bob', 'Williams'),
    ('eva_miller', 'eva@example.com', 'pass5', 'Eva', 'Miller'),
    ('david_jones', 'david@example.com', 'pass6', 'David', 'Jones'),
    ('sophia_brown', 'sophia@example.com', 'pass7', 'Sophia', 'Brown'),
    ('matthew_taylor', 'matthew@example.com', 'pass8', 'Matthew', 'Taylor'),
    ('olivia_anderson', 'olivia@example.com', 'pass9', 'Olivia', 'Anderson'),
    ('william_white', 'william@example.com', 'pass10', 'William', 'White');

-- Company tablosuna 10 değer ekleme
insert into company (name, field, location, worker_count)
values
    ('Bilgi Teknolojileri A.Ş.', 'Bilgi Teknolojileri', 'İstanbul', 120),
    ('FinansBankası', 'Finans', 'Ankara', 85),
    ('Sağlık Grubu', 'Sağlık', 'İzmir', 150),
    ('EduTech Eğitim Sistemleri', 'Eğitim', 'Bursa', 60),
    ('AutoInnovate Otomotiv', 'Otomotiv', 'Antalya', 110),
    ('Hotel Paradise', 'Konaklama', 'Adana', 75),
    ('Telekom Devleri', 'Telekomünikasyon', 'Eskişehir', 130),
    ('Perakende Rüzgarı', 'Perakende', 'Trabzon', 95),
    ('Yeşil Enerji Şirketleri', 'Enerji', 'İzmit', 85),
    ('Medya Kreatif', 'Medya', 'Diyarbakır', 120);

-- Education tablosuna 10 değer ekleme
insert into education (user_id, school_name, department, start_date, finish_date, grade)
values
    (1, 'Boğaziçi Üniversitesi', 'Bilgisayar Mühendisliği', '2015-09-01', '2019-06-01', 3.50),
    (2, 'Hacettepe Üniversitesi', 'İktisat ve Finans', '2014-08-01', '2018-05-01', 3.75),
    (3, 'Hacettepe Üniversitesi', 'Biyomedikal Mühendislik', '2016-09-01', '2020-06-01', 3.00),
    (4, 'Gazi Üniversitesi', 'Eğitim Fakültesi', '2013-08-01', '2017-05-01', 3.90),
    (5, 'İstanbul Teknik Üniversitesi', 'Makine Mühendisliği', '2017-09-01', '2021-06-01', 3.20),
    (6, 'Turizm Otelcilik Meslek Yüksekokulu', 'Otelcilik Yönetimi', '2012-08-01', '2016-05-01', 3.80),
    (7, 'Bilkent Üniversitesi', 'Elektrik Elektronik Mühendisliği', '2018-09-01', '2022-06-01', 3.45),
    (8, 'İstanbul Üniversitesi', 'İşletme ve Yönetim', '2011-08-01', '2015-05-01', 3.60),
    (9, 'ODTÜ', 'Yenilenebilir Enerji Mühendisliği', '2019-09-01', '2023-06-01', 3.25),
    (10, 'Anadolu Üniversitesi', 'İletişim Bilimleri', '2010-08-01', '2014-05-01', 3.70);

-- Experience tablosuna 10 değer ekleme
insert into experience (user_id, company_id, job_title, department, start_date, finish_date)
values
    (1, 1, 'Yazılım Mühendisi', 'Bilgi Teknolojileri', '2015-07-01', '2021-02-01'),
    (2, 2, 'Finans Analisti', 'Finans', '2014-06-01', '2020-03-01'),
    (3, 3, 'Hemşire', 'Sağlık', '2016-08-01', '2022-04-01'),
    (4, 4, 'Öğretmen', 'Eğitim', '2013-07-01', '2019-01-01'),
    (5, 5, 'Makine Mühendisi', 'Otomotiv', '2017-06-01', '2023-03-01'),
    (6, 6, 'Otel Müdürü', 'Konaklama', '2012-05-01', '2018-11-01'),
    (7, 7, 'Ağ Mühendisi', 'Telekomünikasyon', '2018-07-01', '2024-01-01'),
    (8, 8, 'Mağaza Müdürü', 'Perakende', '2011-06-01', '2017-12-01'),
    (9, 9, 'Elektrik Mühendisi', 'Enerji', '2019-07-01', NULL),
    (10, 10, 'Gazeteci', 'Medya', '2010-05-01', '2016-11-01');

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
insert into advertisement (company_id, title, description, location, open_date, deadline_date, applied_count, is_active, is_job, type, department, working_model)
values
    (1, 'Yazılım Mühendisi İş İlanı', 'Bilgi Teknolojileri alanında deneyimli yazılım mühendisi aranmaktadır.', 'Ankara', '2024-01-11', '2024-02-01', 0, true, true, 'Tam Zamanlı', 'Mühendislik', 'Hibrit'),
    (2, 'Finans Analisti İş İlanı', 'Finans alanında uzman finans analisti aranmaktadır.', 'İstanbul', '2024-01-12', '2024-02-02', 0, true, true, 'Yarı Zamanlı', 'Finans', 'Remote'),
    (3, 'İnsan Kaynakları Uzmanı İş İlanı', 'İnsan kaynakları alanında deneyimli uzman aranmaktadır.', 'İzmir', '2024-01-13', '2024-02-03', 0, true, true, 'Tam Zamanlı', 'İnsan Kaynakları', 'Yüzyüze'),
    (4, 'Satış ve Pazarlama Temsilcisi İş İlanı', 'Satış ve pazarlama alanında tecrübeli temsilci aranmaktadır.', 'Ankara', '2024-01-14', '2024-02-04', 0, true, true, 'Yarı Zamanlı', 'Satış ve Pazarlama', 'Remote'),
    (5, 'Bilgi Teknolojileri Stajyer İş İlanı', 'Bilgi teknolojileri alanında staj yapmak isteyen öğrenciler aranmaktadır.', 'İstanbul', '2024-01-15', '2024-02-05', 0, true, true, 'Stajyer', 'Bilgi Teknolojileri', 'Hibrit'),
    (6, 'Müşteri Hizmetleri Stajyer İş İlanı', 'Müşteri hizmetleri alanında staj yapmak isteyen öğrenciler aranmaktadır.', 'İzmir', '2024-01-16', '2024-02-06', 0, true, true, 'Stajyer', 'Müşteri Hizmetleri', 'Yüzyüze'),
    (7, 'Web Geliştirme Kursu', 'Web geliştirme konusunda temel ve ileri düzey bilgiler içeren kurs başlamıştır.', 'Ankara', '2024-01-17', '2024-02-07', 0, true, false, 'Yüzyüze', 'Bilgi Teknolojileri', null),
    (8, 'Veri Analizi Kursu', 'Veri analizi ve iş zekası konularında online kurs başlamıştır.', 'İstanbul', '2024-01-18', '2024-02-08', 0, true, false, 'Online', 'İstatistik ve Veri Bilimi', null),
    (9, 'Yabancı Dil Kursu', 'İngilizce dilinde temel ve ileri düzey dil kursları başlamıştır.', 'İzmir', '2024-01-19', '2024-02-09', 0, true, false, 'Yüzyüze', 'Yabancı Dil', null),
    (10, 'Dijital Pazarlama Kursu', 'Dijital pazarlama stratejileri ve uygulamalarını içeren online kurs başlamıştır.', 'Ankara', '2024-01-20', '2024-02-10', 0, true, false, 'Online', 'Pazarlama', null);

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

select get_applied_adv(1);
select get_applied_counts(1);
select filter_advertisements(true, 'Yarı Zamanlı', 4, 'Ankara', 'Remote');